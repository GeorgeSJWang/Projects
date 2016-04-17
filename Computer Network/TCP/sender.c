#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <sys/stat.h>

#define PKT_SIZE 1000
#define MAX_LENGTH 1050
#define HEADER_LEN 40

int min(int a, int b)
{
    if (a >= b)
        return b;
    else 
        return a;
}

int max(int a, int b)
{
    if (a >= b)
        return a;
    else 
        return b;
}

//bubble sort
void sort(int a[], int n)
{
    int i, j, tmp;
    for (i = n - 1; i > 0; i--) {
        for (j = 0; j < i; j++) {
            if (a[j] > a[j + 1]) {
                tmp = a[j];
                a[j] = a[j + 1];
                a[j + 1] = tmp;
            }
        }
    }
    return;
}

int update_unacked(int get_ack[], int n, int unacked)
{
    int i, updated_value = unacked;
    sort(get_ack, n);
    for (i = 0; i < n; i++) {
        if (get_ack[i] == unacked + i) 
            updated_value = unacked + i + 1;
        else if (get_ack[i] == updated_value) {
            //printf("strange\n");
            continue;
        }
        else 
            break;
    }
    return updated_value;   
}

//argv: port, path, agent IP, agent port
int main(int argc, char* argv[])
{
    if (argc < 5) {
        fprintf(stderr, "arg required\n");
        exit(0);
    }
    FILE *fp;
    int n, threshold = 16, win_size = 1;
    int sock_fd;
    char msg[MAX_LENGTH];
    int num_pkt;
    int pkt_loss = 0;
    int unacked = 0;
    int get_ack[100];
    int max_have_sent = -1;
    int i = 0, j = 0;
    struct sockaddr_in sender_addr, agent_addr;
    struct timeval timeout;
    struct stat st;
    socklen_t addrlen;
    //init socket attr
    sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    bzero(&sender_addr, sizeof(sender_addr));
    sender_addr.sin_family = AF_INET;
    sender_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    sender_addr.sin_port = htons(atoi(argv[1]));
    bind(sock_fd, (struct sockaddr*)&sender_addr, sizeof(sender_addr));
    bzero(&agent_addr, sizeof(agent_addr));
    agent_addr.sin_family = AF_INET;
    agent_addr.sin_port = htons(atoi(argv[4]));
    inet_pton(AF_INET, argv[3], &agent_addr.sin_addr);
    addrlen = sizeof(agent_addr);
    timeout.tv_sec = 1;
    timeout.tv_usec = 0;
    //open file
    fp = fopen(argv[2], "rb");
    //count num of pkt
    stat(argv[2], &st);
    if (st.st_size % PKT_SIZE == 0)
        num_pkt = st.st_size / PKT_SIZE;
    else
        num_pkt = st.st_size / PKT_SIZE + 1;
    while (unacked < num_pkt) {
        //sent pkt
        for (i = unacked; i < min(unacked + win_size, num_pkt); i++) {
            bzero(msg, sizeof(msg));
            //add header
            sprintf(msg, "sender-data:%d\n", i + 1);
            fseek(fp, i * PKT_SIZE, SEEK_SET);
            n = fread(msg + HEADER_LEN, sizeof(char), PKT_SIZE, fp);
            sendto(sock_fd, msg, n + HEADER_LEN, 0, (struct sockaddr *)&agent_addr, addrlen);
            if (i <= max_have_sent) {
                printf("resnd data #%d, winSize = %d\n", i + 1, win_size);
            } else {
                printf("send data #%d, winSize = %d\n", i + 1, win_size);
                max_have_sent++;
            }
        }
        for (i = unacked, j = 0; i < min(unacked + win_size, num_pkt); i++) {
            setsockopt(sock_fd, SOL_SOCKET, SO_RCVTIMEO, (char *)&timeout, sizeof(timeout));
            pkt_loss = 0;
            if (recvfrom(sock_fd, msg, MAX_LENGTH, 0, (struct sockaddr*)&agent_addr, &addrlen) < 0) {
                //timeout    
                pkt_loss = 1;
                printf("time out, threshold = %d\n", threshold);
            } else {
                //not timeout
                //printf("msg:%s", msg);
                sscanf(msg, "receiver-ack:%d\n", &get_ack[j]);
                get_ack[j]--;
                printf("recv ack #%d\n", get_ack[j] + 1);
                j++;
            }
        }
        unacked = update_unacked(get_ack, j, unacked);
        if (!pkt_loss) {
            if (win_size < threshold)
                win_size *= 2;
            else
                win_size++;
        }else {
            threshold = max(win_size / 2, 1);
            win_size = 1;
        }
    }
    bzero(msg, sizeof(msg));
    //add header
    sprintf(msg, "sender-fin\n");
    n = strlen(msg);
    sendto(sock_fd, msg, n, 0, (struct sockaddr *)&agent_addr, addrlen);
    printf("send fin\n");
    recvfrom(sock_fd, msg, MAX_LENGTH, 0, (struct sockaddr*)&agent_addr, &addrlen);
    if (strncmp(msg, "receiver-finack", 15) == 0)
        printf("recv finack\n");
    fclose(fp);
    return 0;
}
