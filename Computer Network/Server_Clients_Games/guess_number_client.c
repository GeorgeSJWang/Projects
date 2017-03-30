#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 


#define PORT 2015
#define BUF_SIZE 50

int main (int argc, char *argv[]) {
    int sockfd, n;
    struct sockaddr_in serv_addr;
    struct hostent *server;
    char buffer[256];
    if (argc < 2) {
        printf ("usage: client < ip address >\n");
        exit(1);
    }
    sockfd = socket (AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) {
        printf ("ERROR opening socket.\n");
        exit (1);
    }
    server = gethostbyname (argv[1]);
    if (server == NULL) {
        printf ("ERROR, no such host.\n");
        exit (1);
    }
    memset (&serv_addr, 0, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy ((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = PORT;
    if (connect (sockfd,(struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
        printf ("ERROR connecting to the server.\n");
        exit (1);
    }
    //SOCKET ALL SET
    printf("Welcome to Guess Number.\n");



    int round_count, win_flag = 0, ret;
    for (round_count = 0; round_count < 10; ++round_count) {
        memset (buffer, 0, BUF_SIZE);
        printf ("Please enter guess no. %d: ", round_count+1);
        scanf ("%s", buffer);
        ret = write (sockfd, buffer, strlen(buffer));
        if (ret < 0) {
            printf ("ERROR writing to socket.\n");
            exit (1);
        }
        memset (buffer, 0, BUF_SIZE);
        ret = read (sockfd, buffer, BUF_SIZE);
        if (ret < 0) {
            printf ("ERROR reading from socket.\n");
            exit (1);
        }
        printf ("Guess result is %s\n", buffer);

        if (atoi(&buffer[0]) == 4) {
            printf ("The client gets it right!\n");
            win_flag = 1;
            memset (buffer, 0, BUF_SIZE);
            sprintf (buffer, "WW%d", round_count+1);
            ret = write (sockfd, buffer, BUF_SIZE);
            if (ret < 0) {
                printf ("ERROR writing to socket.\n");
                exit (1);
            }
            memset (buffer, 0, BUF_SIZE);
            ret = read (sockfd, buffer, BUF_SIZE);
            int value = atoi(buffer);
            break;
        }
        else {
            if (round_count == 9)
                ret = write (sockfd, "LOSE", 3);
            else
                ret = write (sockfd, "Keep", 3);
            if (ret < 0) {
                printf ("ERROR writing to socket.\n");
                exit (1);
            }

        }
    }
    if (win_flag != 1)
        printf ("The client loses.\n");

    write (sockfd, "LOSE", 10);
    close (sockfd);
    return 0;
}