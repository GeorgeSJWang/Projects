#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/socket.h>
#include <sys/types.h>
#include <time.h>

#define MAX_LENGTH 1050

//argv: port, sender IP, sender port, receiver IP, receiver port, loss rate
int main(int argc, char* argv[])
{
    if (argc < 7) {
        fprintf(stderr, "arg required\n");
        exit(0);
    }
    struct sockaddr_in agent_addr, sender_addr, receiver_addr;
    socklen_t sender_addrlen, receiver_addrlen;
    int sock_fd, n;
    int fwd_finack = 0;
    int seq_num;
    char msg[MAX_LENGTH];
    double loss_cnt = 0.0;
    int get_cnt = 0;
    int loss_rate = 0;
    //init socket attr
    sock_fd = socket(AF_INET, SOCK_DGRAM, 0);
    bzero(&agent_addr, sizeof(agent_addr));
    agent_addr.sin_family = AF_INET;
    agent_addr.sin_addr.s_addr = htonl(INADDR_ANY);
    agent_addr.sin_port = htons(atoi(argv[1]));
    bind(sock_fd, (struct sockaddr*)&agent_addr, sizeof(agent_addr));
    //sender attr
    bzero(&sender_addr, sizeof(sender_addr));
    sender_addr.sin_family = AF_INET;
    sender_addr.sin_port = htons(atoi(argv[3]));
    inet_pton(AF_INET, argv[2], &sender_addr.sin_addr);
    sender_addrlen = sizeof(sender_addr);
    //receiver attr
    bzero(&receiver_addr, sizeof(receiver_addr));
    receiver_addr.sin_family = AF_INET;
    receiver_addr.sin_port = htons(atoi(argv[5]));
    inet_pton(AF_INET, argv[4], &receiver_addr.sin_addr);
    receiver_addrlen = sizeof(receiver_addr);
    //srand
    srand(time(NULL));
    //loss rate
    loss_rate = atoi(argv[6]);
    if (loss_rate < 0 || loss_rate > 100) {
        fprintf(stderr, "loss rate out of range\n");
        exit(0);
    }
     
    while (!fwd_finack) {
        n = recv(sock_fd, &msg, MAX_LENGTH, 0);
        if (msg[0] == 's') {
            //header sender-
            //data
            if (msg[7] == 'd') {
                sscanf(msg, "sender-data:%d\n", &seq_num);
                printf("get data #%d\n", seq_num);
                get_cnt++;
                if ((rand() % 100) < loss_rate) {
                    loss_cnt++;
                    printf("drop data #%d, loss rate=%lf\n", seq_num, loss_cnt / get_cnt);
                } else {
                    sendto(sock_fd, msg, n, 0, (struct sockaddr *)&receiver_addr, receiver_addrlen);
                    printf("fwd data #%d, loss rate=%lf\n", seq_num, loss_cnt / get_cnt);
                }
                //fin
            } else if (msg[7] == 'f') {
                printf("get fin\n");
                sendto(sock_fd, msg, n, 0, (struct sockaddr *)&receiver_addr, receiver_addrlen);
                printf("fwd fin\n");
            }
        } else if (msg[0] == 'r') {
            //header receiver-
            if (msg[9] == 'a') {
                sscanf(msg, "receiver-ack:%d\n", &seq_num);
                printf("get ack #%d\n", seq_num);
                sendto(sock_fd, msg, n, 0, (struct sockaddr *)&sender_addr, sender_addrlen);
                printf("fwd ack#%d\n", seq_num);
                //header receiver-finack
            } else if (msg[9] == 'f') {
                printf("get finack\n");
                sendto(sock_fd, msg, n, 0, (struct sockaddr *)&sender_addr, sender_addrlen);
                printf("fwd finack\n");
                fwd_finack = 1;
            }
        }
    }
    return 0;
}
