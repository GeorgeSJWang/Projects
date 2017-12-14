#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/types.h> 
#include <sys/socket.h>
#include <netinet/in.h>
#include <time.h>

#define PORT 2015
#define BUF_SIZE 50

int main (int argc, char *argv[]) {
  printf ("Welcome to Guess Number! I am the server.\n");
  int random_number[4];

  int i, c, temp;
  int list[10];
  srand (time(NULL));          //set up random numbers 
  for (i = 0; i < 10; ++i) {
    list[i] = i;
  }
  for (i = 0; i < 4; ++i) {
    c = rand() % 9;
    if (c != i) {
      temp = list[i];
      list[i] = list[c];
      list[c] = temp;
    }
  }
  for (i = 0; i < 4; ++i) {
    random_number[i] = list[i];
  }                             //random numbers set
  for (i = 0; i < 4; ++i) {     //sort the numbers
    for (c = i; c < 4; ++c) {
      if (random_number[i] > random_number[i+1]) {
        temp = random_number[i];
        random_number[i] = random_number[i+1];
        random_number[i+1] = temp;
      }
    }
  }

  int sockfd, newsockfd, portno;
  socklen_t clilen;
  char buffer[BUF_SIZE];
  struct sockaddr_in serv_addr, cli_addr;
  sockfd = socket(AF_INET, SOCK_STREAM, 0);

  if (sockfd < 0) {
    printf ("ERROR opening socket.\n");
    exit (1);
  }

  memset (&serv_addr, 0, sizeof(serv_addr));
  serv_addr.sin_family = AF_INET;
  serv_addr.sin_addr.s_addr = INADDR_ANY;
  serv_addr.sin_port = PORT;

  if (bind(sockfd, (struct sockaddr *) &serv_addr, sizeof(serv_addr)) < 0) {
    printf ("ERROR on binding.\n");
    exit (1);
  }

  listen (sockfd, 5);
  clilen = sizeof (cli_addr);
  newsockfd = accept (sockfd, (struct sockaddr *) &cli_addr, &clilen);
  if (newsockfd < 0) {
    printf ("ERROR on accept.\n");
    exit (1);
  }

  printf("The random numbers are %d%d%d%d\n", random_number[0], random_number[1], random_number[2], random_number[3]);

  int n, k, j, a_flag = 0, b_flag = 0;
  while (1) {

    memset (buffer, 0, BUF_SIZE);
    n = read (newsockfd, buffer, 255);
    if (n < 0) {
      printf ("ERROR reading from socket.\n");
      exit (1);
    }
    for (k = 0; k < 4; ++k) {
      char c = buffer[k];
      if (atoi(&c) == random_number[k]) {
        a_flag++;
      }
      else
        for (j = 0; j < 4; ++j) {
          c = buffer[j];
          if (atoi(&c) == random_number[k]) {
            b_flag++;
            break;
          }
        }
    }
    if (a_flag == 4) {
      char guess_result[10];
      sprintf (guess_result, "%d A %d B", a_flag, b_flag);
      a_flag = 0, b_flag = 0;
      n = write (newsockfd, guess_result, strlen(guess_result));
      if (n < 0) {
        printf ("ERROR writing to socket.\n");
        exit (1);
      }
      printf ("The client wins!\n");
      close (newsockfd);
      close (sockfd);
      return 0;
    }
    else {
      char guess_result[10];
      sprintf (guess_result, "%d A %d B", a_flag, b_flag);
      a_flag = 0, b_flag = 0;
      n = write (newsockfd, guess_result, strlen(guess_result));
      if (n < 0) {
        printf ("ERROR writing to socket.\n");
        exit (1);
      }
      memset (buffer, 0, BUF_SIZE);
      n = read (newsockfd, buffer, 255);
      if (n < 0) {
        printf ("ERROR reading from socket.\n");
        exit (1);
      }
      if (buffer[0] == 'W') {
        break; 
      }
      if (buffer[0] == 'L') {
        printf ("The client loses.\n");
        break;
      }
    }
  }
  close (newsockfd);
  close (sockfd);
  return 0;
}