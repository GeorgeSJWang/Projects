#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>

#define PKT_SIZE 1000
#define MAX_LENGTH 1050
#define BUF_SIZE 32
#define HEADER_LEN 40

void copy(char* src, char* des, int n)
{
    int i;
    for (i = 0; i < n; i++)
        des[i] = src[i];
    return;
}

//num of non-empty buffer
int non_empty_buf(int len[])
{
    int i = 0;
    int cnt = 0;
    for (i = 0; i < BUF_SIZE; i++) {
        if (len[i] == 0)
            break;
        else 
            cnt++;
    }
    return cnt;
}

//flush buffer
void flush_buf(FILE* fp, char buf[][MAX_LENGTH], int buf_len[], int num)
{
   int i;
   for (i = 0; i < num; i++) {
       fwrite(buf[i], sizeof(char), buf_len[i], fp);
       fflush(fp);
   }
   //empty buffers
   for (i = 0; i < BUF_SIZE; i++)
       buf_len[i] = 0;
   return;
}

//argv: port, path, agent IP, agent port
int main(int argc, char* argv[])
{
    if (argc < 5) {
        fprintf(stderr, "arg required\n");
        exit(0);
    }
    FILE *fp;
    int n;
    int sock_fd;
    int get_fin = 0;
    //int stop;
    char buf[BUF_SIZE][MAX_LENGTH], msg[MAX_LENGTH];
    //char header[100];
    int buf_len[BUF_SIZE] = {0}, seq_num = 0, idx = 0, get_full_buf = 0;
    struct sockaddr_in receiver_addr, agent_addr;
    socklen_t addrlen;
    //init socket attr
    sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    bzero(&receiver_addr, sizeof(receiver_addr));
    receiver_addr.sin_family = AF_INET;
    receiver_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    receiver_addr.sin_port = htons(atoi(argv[1]));
    bind(sock_fd, (struct sockaddr*)&receiver_addr, sizeof(receiver_addr));
    bzero(&agent_addr, sizeof(agent_addr));
    agent_addr.sin_family = AF_INET;
    agent_addr.sin_port = htons(atoi(argv[4]));
    inet_pton(AF_INET, argv[3], &agent_addr.sin_addr);
    addrlen = sizeof(agent_addr);
    //open file pointer
    fp = fopen(argv[2], "wb");
    while (!get_fin) {
        n = recvfrom(sock_fd, msg, MAX_LENGTH, 0, (struct sockaddr*)&agent_addr, &addrlen);
        /*
        stop = strchr(msg, '\n') - msg;
        strncpy(header, msg, stop);
        header[stop] = '\0';
        printf("header:%s\n", header);
        printf("get_full_buf=%d\n", get_full_buf);
        */
        //header sender-
        if (msg[7] == 'd') {
            sscanf(msg, "sender-data:%d\n", &seq_num);
            //printf("seq_num=%d\n", seq_num);
            idx = (seq_num - 1) - get_full_buf * BUF_SIZE;
            if (idx >= BUF_SIZE) {
                printf("drop data #%d\n", seq_num);
                //buffer overflow
                if (non_empty_buf(buf_len) == BUF_SIZE) {
                    //printf("buf full\n");
                    flush_buf(fp, buf, buf_len, BUF_SIZE);
                    get_full_buf++;
                    printf("flush\n");
                    //printf("non-empty:%d\n", non_empty_buf(buf_len));
                }
            } else if (idx >= 0 && idx < 32){
                //copy to buffer
                if (buf_len[idx] == 0) {
                    printf("recv data #%d\n", seq_num);
                    buf_len[idx] = n - HEADER_LEN;
                    //printf("buf_len[%d]=%d\n", idx, buf_len[idx]);
                    copy(msg + HEADER_LEN, buf[idx], buf_len[idx]);
                } else {
                    printf("ignr data #%d\n", seq_num);
                }
                //send ack
                sprintf(msg, "receiver-ack:%d\n", seq_num);
                sendto(sock_fd, msg, strlen(msg), 0, (struct sockaddr *)&agent_addr, addrlen);
                printf("send ack #%d\n", seq_num);
            } else {
                //printf("cheat\n");
                printf("recv data #%d\n", seq_num);
                //send ack
                sprintf(msg, "receiver-ack:%d\n", seq_num);
                sendto(sock_fd, msg, strlen(msg), 0, (struct sockaddr *)&agent_addr, addrlen);
                printf("send ack #%d\n", seq_num);
            }
        } else if (msg[7] == 'f') {
            //get fin
            get_fin = 1;
            printf("recv fin\n");
            sprintf(msg, "receiver-finack\n");
            sendto(sock_fd, msg, strlen(msg), 0, (struct sockaddr *)&agent_addr, addrlen);
            printf("send finack\n");
            flush_buf(fp, buf, buf_len, non_empty_buf(buf_len));
            printf("flush\n");
        } else {
            printf("error\n");
        }
    }
    fclose(fp);
    return 0;
}
