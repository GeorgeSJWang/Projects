#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>   
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <time.h>
#include <pthread.h>
#include <fcntl.h>
#include <sys/time.h>
#include <sys/select.h>

#define PORT 2015
#define BUF_SIZE 256
#define CLADDR_LEN 100


int gb = 0;
int draw = -1;
int bl = 0;
int gn_flag = 0;
int gb_flag = 0;
pthread_t tid[100];
int card_index = 0;
int card[52];
typedef struct node {
	int newsockfd;
	struct sockaddr_in cl_addr;
	int index;
	int start;
} Node;
int player[100][4] = {{0}};
int client_count = 0;
int gameStart = 0;
int finished = 0;
int all = 0;
int num[100] = {0};
int masterHand[5] = {0};
int mastervalue = 0;
int random_number[4] = {0};
int keepcount[100] = {0};
int min = 10000000, min_index;
int windex = 0;


void* cardGame(void *arg) {

	int ret, i = 0, splits = 0, si = 0, k, j;
	int final = 0;
	int a_flag = 0, b_flag = 0;

	Node element = *(Node*)arg;
	char buffer[BUF_SIZE];
	struct sockaddr_in cl_addr = element.cl_addr;
	int newsockfd = element.newsockfd;
	int player_index = element.index;
	char clientAddr[CLADDR_LEN];
	inet_ntop (AF_INET, &(cl_addr.sin_addr), clientAddr, CLADDR_LEN);
	int mastercard = element.start;

	while (1) {
		if (gameStart == 2015)
			break;
	}

	memset (buffer, 0, BUF_SIZE);
	ret = sendto (newsockfd, "Game Start", BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
	memset (buffer, 0, BUF_SIZE);
	ret = read (newsockfd, buffer, BUF_SIZE);
	if (ret < 0) {
		printf ("Error receiving data!\n");
		exit(1);
	}
	if (buffer[0] == 'G') {
		printf("Guess Number:\n");
		gb = 1;
		while(1) {
			memset (buffer, 0, BUF_SIZE);
			ret = read (newsockfd, buffer, 255);
			if (ret < 0) {
				printf ("ERROR reading from socket.\n");
				exit (1);
			}
			for (k = 0; k < 4; ++k) {
				char c = buffer[k];
				int tempv = atoi(&c);
				tempv /= 1000;		
				if (tempv == random_number[k]) {
					a_flag++;
				}
				else {
					for (j = 0; j < 4; ++j) {
						char d = buffer[j];
						int tempv = atoi(&d);
						tempv /= 10000;	
						if (tempv == random_number[k]) {
							b_flag++;
							break;
						}
					}
				}
			}
			if (a_flag == 4) {
				char guess_result[10];
				sprintf (guess_result, "%d A %d B", a_flag, b_flag);
				a_flag = 0, b_flag = 0;
				printf ("The client gets it right!\n");
				ret = write (newsockfd, guess_result, strlen(guess_result));
				if (ret < 0) {
					printf ("ERROR writing to socket.\n");
					exit (1);
				}
				memset (buffer, 0, BUF_SIZE);
				ret = read (newsockfd, buffer, 255);
				if (buffer[0] == 'W') {
					char c[5];
					if (strlen(buffer) > 3) {
						c[0] = buffer[2];
						c[1] = buffer[3];
					}
					else {
						c[0] = buffer[2];
					}
					keepcount[player_index] = atoi(c);
					printf("Player %d got %d\n", player_index, atoi(c));
					client_count--;
					while (1) {
						if (gn_flag == 5)
							break;
					}
					if (keepcount[player_index] == min) {
						char guess_result[10];
						if (windex > 1) {
							sprintf (guess_result, "DD");
						}
						else
							sprintf (guess_result, "WWWW");
						ret = write (newsockfd, guess_result, strlen(guess_result));
					}
					else {
						sprintf (guess_result, "QQQQQ");
						ret = write (newsockfd, guess_result, strlen(guess_result));
					}
					if (ret < 0)
						printf ("FUCKKKKKKK\n");
					break;
				}
			}
			else {
				char guess_result[10];
				sprintf (guess_result, "%d A %d B", a_flag, b_flag);
				a_flag = 0, b_flag = 0;
				ret = write (newsockfd, guess_result, strlen(guess_result));
				if (ret < 0) {
					printf ("ERROR writing to socket.\n");
					exit (1);
				}
				memset (buffer, 0, BUF_SIZE);
				ret = read (newsockfd, buffer, 255);
				if (ret < 0) {
					printf ("ERROR reading from socket.\n");
					exit (1);
				}
			}
		}
	}
	else {
		bl = 1;
		while(1) {
			memset (buffer, 0, BUF_SIZE);
			ret = read(newsockfd, buffer, BUF_SIZE);
			if (ret < 0) {
				printf ("Error receiving data!\n");
				exit(1);
			}
			if (buffer[0] == 'B' && buffer[1] == 'U') {		//BUST
				char c[3];
				c[0] = buffer[5];
				c[1] = buffer[6];
				c[2] = ' ';
				player[player_index][si] = atoi(c);
				si++;
				splits--;
				if (!(splits < 1))
					continue;
				client_count--;
			}
			if (buffer[0] == 'B' && buffer[1] == 'l') {		//Blackjack
				player[player_index][si] = 21;
				si++;
				if (!(splits < 1))
					continue;
				client_count--;
				close (newsockfd);
				break;
			}

			if (buffer[0] == 'D') {
				memset (buffer, 0, BUF_SIZE);
				if (splits == 0) {
					sprintf (buffer, "%d %d %d", card[card_index], card[card_index+1], mastercard);
				}
				else {
					sprintf (buffer, "%d %d", card[card_index], card[card_index+1]);
				}
				card_index += 2;
				ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
				if (ret < 0) {
					printf("Error sending data!\n");
					exit (1);
				}
			}
			else if (buffer[0] == 'H') {
				memset (buffer, 0, BUF_SIZE);
				sprintf (buffer, "%d", card[card_index]);
				card_index++;
				ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
				if (ret < 0) {
					printf ("Error sending data!\n");
					exit (1);
				}
				memset (buffer, 0 , BUF_SIZE);

			}
			else if (buffer[0] == 'S' && buffer[1] == 'T') {
				if (buffer[5] != 'G') {
					memset (buffer, 0, BUF_SIZE);
					ret = read (newsockfd, buffer, BUF_SIZE);
					if (ret < 0) {
						printf ("Error receiving data!\n");
						exit(1);
					}
					player[player_index][si] = atoi(buffer);
					si++;
					splits--;
				}
				if (!(splits < 0))
					continue;
				client_count--;

				while (1) {
					if (finished == 5)
						break;
				}
				int k, j;
				for (j = 0; j <= final; ++j) {
					memset (buffer, 0, BUF_SIZE);
					if (player[player_index][j] == 21) {
						if (mastervalue != 21) {
							sprintf (buffer, "BLACKJACK!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
							printf ("Player %d split %d got BLACKJACK.\n", player_index, j);
						}
						else
							sprintf (buffer, "YOU DRAW!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
						printf ("Player %d split %d DRAWS.\n", player_index, j);
					}
					else if (player[player_index][j] > 21) {
						sprintf (buffer, "BUST!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
						printf ("Player %d split %d BUSTS.\n", player_index, j);
					}
					else if (mastervalue > 21) {
						sprintf (buffer, "YOU WIN!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						printf ("Player %d split %d WINS.\n", player_index, j);
					}
					else if (player[player_index][j] > mastervalue) {
						sprintf (buffer, "YOU WIN!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
						printf ("Player %d split %d WINS.\n", player_index, j);
					}
					else if (player[player_index][j] == mastervalue) {
						sprintf (buffer, "YOU DRAW!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
						printf ("Player %d split %d DRAWS.\n", player_index, j);
					}
					else {
						sprintf (buffer, "YOU LOSE!\nYour hand: %d\nDealer hand: %d\n", player[player_index][j], mastervalue);
						sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
						printf ("Player %d split %d LOSES.\n", player_index, j);
					}
					if (ret < 0) {
						printf ("Error receiving data!\n");
						exit(1);
					}
				}
				memset (buffer, 0, BUF_SIZE);
				char output[BUF_SIZE];
				memset (output, 0, BUF_SIZE);
				ret = read (newsockfd, output, BUF_SIZE);

				printf ("Player %d now has $%s.\n", player_index, output);
				close (newsockfd);
				gb_flag = 5;
				break;
			}
			else if (buffer[0] == 'S' && buffer[1] == 'P') {
				num[player_index]++;
				splits++;
				final++;
				memset (buffer, 0, BUF_SIZE);
				sprintf (buffer, "%d %d", card[card_index], card[card_index+1]);
				card_index += 2;
				ret = sendto (newsockfd, buffer, BUF_SIZE, 0, (struct sockaddr *)&cl_addr, sizeof(cl_addr));
				if (ret < 0) {
					printf("Error sending data!\n");
					exit (1);
				}
			}
		}
	}
	return 0;
}


int main (int argc, char* argv[]) {
	struct sockaddr_in addr, cl_addr;
	int sockfd, ret, newsockfd = 0, i;
	socklen_t len;
	char clientAddr[CLADDR_LEN];
	sockfd = socket (AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		printf ("Error creating socket!\n");
		exit(1);
	}
	memset (&addr, 0, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_addr.s_addr = INADDR_ANY;
	addr.sin_port = PORT;
	ret = bind (sockfd, (struct sockaddr *)&addr, sizeof(addr));
	if (ret < 0) {
		printf ("Error binding!\n");
		exit (1);
	}
	len = sizeof(cl_addr);
	listen (sockfd, 5);
	char buffer[BUF_SIZE];
	for (i = 0; i < 52; ++i) {
		card[i] = i+1;
	}
	int c, j, temp;
	srand (time(NULL));

	for (i = 0; i < 52; ++i) {
		c = rand() % 52;
		if (c != i) {
			temp = card[i];
			card[i] = card[c];
			card[c] = temp;
		}
	}
	int a_flag, b_flag;
	int list[10];
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
	}                             
	printf("The random numbers are %d%d%d%d\n", random_number[0], random_number[1], random_number[2], random_number[3]);


	int nfds = 0, mindex = 0;
	masterHand[mindex] = card[card_index];
	temp = card[card_index];
	int shape = temp;
	temp %= 13;
	if (temp > 10 || temp == 0) {
		temp = 10;
	}
	else if (temp == 1)
		temp = 11;
	mastervalue += temp;
	card_index++;
	mindex++;
	masterHand[mindex] = card[card_index];
	temp = card[card_index];
	temp %= 13;
	if (temp > 10 || temp == 0) {
		temp = 10;
	}
	else if (temp == 1)
		temp = 11;
	mastervalue += temp;
	card_index++;
	mindex++;
	client_count = 0;
	i = 0;

	while (1) {
		newsockfd = 0;
		fd_set rfds;
		FD_ZERO (&rfds);
		FD_SET (sockfd, &rfds);
		struct timeval tv;
		tv.tv_sec = 4;
		tv.tv_usec = 0;
		int ready = 0;
		ready = select (sockfd+1, &rfds, NULL, NULL, &tv);
		if (ready == 0)
			break;
		newsockfd = accept (sockfd, (struct sockaddr *)&cl_addr, &len);
		if (newsockfd < 0) {
			printf ("Error accepting connection!\n");
			exit (1);
		}
		client_count++;
		all++;

		Node pass;
		pass.cl_addr = cl_addr;
		pass.newsockfd = newsockfd;
		pass.index = i;
		pass.start = shape;


		inet_ntop (AF_INET, &(cl_addr.sin_addr), clientAddr, CLADDR_LEN);
		ret = pthread_create (&tid[i], NULL, cardGame, (void*) &pass);
		if (ret != 0)
			printf ("Error...\n");
		i++;
		newsockfd = 0;
	}

	gameStart = 2015;
	while (1) {
		if (client_count == 0)
			break;
	}
	if (bl) {
		while (1) {
			if (mastervalue >= 17)
				break;
			temp = card[card_index];
			temp %= 13;
			if (temp > 10 || temp == 0) {
				temp = 10;
			}
			else if (temp == 1)
				temp = 11;
			mastervalue += temp;
			masterHand[mindex] = temp;
			mindex++;
			if (mastervalue > 21) {
				for (i = 0; i < mindex; ++i) {
					if (masterHand[i] == 11) {
						masterHand[i] = 1;
						mastervalue -= 10;
					}
				}
			}
		}
		printf("Dealer hand: %d\n", mastervalue);
		finished = 5;
	}

	int winners[100];
	//SPLIT
	if (gb) {
		for (i = 0; i < all; ++i) {
			if (keepcount[i] < min) {
				min = keepcount[i];
				min_index = i;
				windex = 0;
				winners[windex] = i;
				windex++;
			}
			if (keepcount[i] == min) {
				winners[windex] = i;
				windex++;
			}
		}
		gn_flag = 5;
		if (windex > 1) {
			printf ("Final winners are:");
			for (i = 0; i < windex; ++i) {
				printf (" %d", winners[i]);
			}
			printf("\n");
		}
		else {
			printf ("Final winner is: %d!\n", min_index);
		}
	}
	for (i = 0; i < all; ++i)
		pthread_join (tid[i], NULL);
	return 0;
}
