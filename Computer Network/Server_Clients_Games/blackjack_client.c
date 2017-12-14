#include <stdio.h>  
#include <stdlib.h>
#include <unistd.h>
#include <string.h>
#include <netdb.h>
#include <sys/types.h>   
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

#define PORT 2015
#define BUF_SIZE 256

int rank;
char suite;

void conversion (int num) {
	rank = num % 13;
	if (rank == 0)
		rank = 13;
	if (num == 10)
		num = 1;
	if ((num/10) == 0) {
		suite = 'S';
	}
	if ((num/10) == 1) {
		suite = 'H';
	}
	if ((num/10) == 2) {
		suite = 'D';
	}
	if ((num/10) == 3) {
		suite = 'C';
	}
}

int main (int argc, char**argv) {
	int cash = 1000, betAmount;
	int index = 0, i = 0;
	struct sockaddr_in addr;
	int sockfd, ret;
	char buffer[BUF_SIZE];
	char clientAddr[50];
	int done= 0;
	struct hostent * serverAddr;
	char convert[4];
	if (argc < 2) {
		printf("usage: client < ip address >\n");
		exit(1);
	}
	serverAddr = gethostbyname(argv[1]);
	sockfd = socket(AF_INET, SOCK_STREAM, 0);
	if (sockfd < 0) {
		printf("Error creating socket!\n");
		exit(1);
	}
	memset(&addr, 0, sizeof(addr));
	addr.sin_family = AF_INET;
	addr.sin_port = PORT;
	bcopy ((char *)serverAddr->h_addr, (char *)&addr.sin_addr.s_addr, serverAddr->h_length);
	ret = connect (sockfd, (struct sockaddr *) &addr, sizeof(addr));
	if (ret < 0) {
		printf("Error connecting to the server!\n");
		exit(1);
	}
	inet_ntop (AF_INET, &(addr.sin_addr), clientAddr, 50);

	//SOCEKT SET

	memset (buffer, 0, BUF_SIZE);
	ret = read (sockfd, buffer, BUF_SIZE);
	if (ret < 0) {
		printf ("Error receiving data!\n");
		exit(1);
	}
	if (buffer[0] == 'G') {
		printf ("Welcome to Blackjack!\nYou have $1000 now.\n");
	}

		int card[4][20] = {0};
		memset (buffer, 0, BUF_SIZE);
		printf ("Enter your bet: ");
		fgets (buffer, BUF_SIZE, stdin);
		betAmount = atoi(buffer);
		cash -= betAmount;
		memset (buffer, 0, BUF_SIZE);	
		printf ("Enter your move: ");
		int value = 0;
		int original = 0, original2 = 0;
		char bbbbbuffer[BUF_SIZE];
		int total[4] = {0};
		int count = 0;
		int splits = 0 ,final = 0;


	while (fgets (buffer, BUF_SIZE, stdin)) {
		ret = sendto (sockfd, buffer, BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
		if (ret < 0) {
			printf ("Error sending data!\n");
		}

		if (buffer[0] == 'S' && buffer[1] == 'T') {			//STAND
			memset (buffer, 0, BUF_SIZE);
			sprintf (buffer, "%d", total[count]);
			ret = sendto (sockfd, buffer, BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
			if (ret < 0) {
				printf ("Error sending data!\n");
			}
			splits--;
			count++;
			if (!(splits == -1)) {
				printf ("Enter your move: ");
				continue;
			}
			int a;
			memset (buffer, 0, BUF_SIZE);
			for (a = 0; a <= final; ++a) {
				ret = read (sockfd, buffer, BUF_SIZE);
				if (ret < 0) {
					printf ("Error receiving data!\n");
				}
				printf("%s\n", buffer);
				if (buffer[1] == 'U' || buffer[4] == 'L') {
					cash = cash;
				}
				else {
					cash += betAmount*2;
				}
				printf ("You have $%d dollars now.\n", cash);
			}
			betAmount = 0;
			memset (buffer, 0, BUF_SIZE);
			sprintf (buffer, "%d", cash);
			ret = sendto (sockfd, buffer, BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
			break;
		}

		else if (buffer[0] == 'S' && buffer[1] == 'P') {
			splits++;
			final++;
			card[count+1][0] = card[count][0];
			card[count][1] = ' ';
			total[count] /= 2;
			total[count+1] = total[count];
			memset (bbbbbuffer, 0, BUF_SIZE);
			ret = read (sockfd, bbbbbuffer, BUF_SIZE);
			if (ret < 0) {
				printf ("Error sending data!\n");
			}
			if (buffer[1] != ' ') {
				convert[0] = bbbbbuffer[0];
				convert[1] = bbbbbuffer[1];
				convert[2] = ' ';
				convert[3] = ' ';
				value = atoi(convert);
				value %= 13;
				if (value > 10 || value == 0)
					value = 10;
				else if (value == 1)
					value = 11;
				card[0][1] = value;
				total[0] += value;
//				printf("A 1 VALUE %d %d\n", card[index], total);
				if (buffer[4] != ' ') {
					convert[0] = bbbbbuffer[3];
					convert[1] = bbbbbuffer[4];
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					value %= 13;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[1][1] = value;
					total[1] += value;
//					printf("B 2 VALUE %d %d\n", card[index+1], total);	
				}
				else {
					convert[0] = bbbbbuffer[3];
					convert[1] = ' ';
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					if (value == 1)
						value = 11;
					card[1][1] = value;
					total[1] += value;
//					printf("C VALUE %d %d\n", card[index+1], total);	
				}		
			}
			else {
				char c3 = bbbbbuffer[0];
				value = atoi(&c3);
				if (value == 1)
					value = 11;
				card[0][1] = value;
				total[0] += value;
				if (bbbbbuffer[3] != ' ') {
					convert[0] = bbbbbuffer[2];
					convert[1] = bbbbbuffer[3];
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi (convert);
					value %= 13;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[1][1] = value;	
					total[1] += value;
				}
				else {
					convert[0] = bbbbbuffer[2];
					convert[1] = ' ';
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					original = value;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[1][1] = value;
					total[1] += value;
				}
			}
			count = 0;
			printf ("AFTER SPLITS %d %d\n", total[0], total[1]);
			printf ("Enter your move: ");
			continue;
		}


			memset (bbbbbuffer, 0, BUF_SIZE);
			ret = read (sockfd, bbbbbuffer, BUF_SIZE);
			if (ret < 0) {
				printf ("Error receiving data!\n");
			}



		if (buffer[0] == 'H') {
			if (strlen(bbbbbuffer) > 1) {
				convert[0] = bbbbbuffer[0];
				convert[1] = bbbbbuffer[1];
				convert[2] = ' ';
				convert[3] = ' ';
				value = atoi(convert);
				original = value;
				value %= 13;
				if (value > 10 || value == 0)
					value = 10;
				total[count] += value;
				card[count][index] = value;
			}
			else {
				char c = bbbbbuffer[0];
				value = atoi(&c);
				original = value;
				if (value == 1)
					value = 11;
				card[count][index] = value;
				total[count] += value;
			}
			index++;


				conversion(original);
				printf ("The Card You Received: %c%d\n", suite, rank);

			if (total[count] > 21) {
				for (i = 0; i < index; ++i) {
					if (card[count][i] == 11) {
						card[count][i] = 1;
						total[count] -= 10;
					}
				}
			}
			printf ("VALUE: %d\n", total[count]);

			if (total[count] > 21) {
				memset (buffer, 0, BUF_SIZE);
				sprintf (buffer, "BUSTS%d", total[count]);
				ret = sendto (sockfd, buffer, BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
				if (ret < 0) {
					printf ("Error sending data!\n");
				}

				splits--;
				count++;
				if (!(splits == -1)) {
					printf ("\nBUSTS!\nEnter your move: ");
					continue;
				}
					ret = sendto (sockfd, "STANDG", BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
					int a;
					memset (buffer, 0, BUF_SIZE);
					for (a = 0; a <= final; ++a) {
						ret = read (sockfd, buffer, BUF_SIZE);
						printf ("%s\n", buffer);
						if (ret < 0) {
							printf ("Error sending data!\n");
						}
						if (buffer[1] == 'U' || buffer[4] == 'L') {
							cash = cash;
						}
						else {
							cash += betAmount*2;
							betAmount = 0;
						}
						printf ("You have $%d dollars now.\n", cash);
					}
					memset (buffer, 0, BUF_SIZE);
					sprintf (buffer, "%d", cash);
					ret = sendto (sockfd, buffer, BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
					break;
			}
		}
		else if (buffer[0] == 'D') {
			if (bbbbbuffer[1] != ' ') {
				convert[0] = bbbbbuffer[0];
				convert[1] = bbbbbuffer[1];
				convert[2] = ' ';
				convert[3] = ' ';
				value = atoi(convert);
				original = value;
				value %= 13;
				if (value > 10 || value == 0)
					value = 10;
				else if (value == 1)
					value = 11;
				card[count][index] = value;
				total[count] += value;
//				printf("A 1 VALUE %d %d\n", card[count][index], total[count]);
				if (bbbbbuffer[4] != ' ') {
					convert[0] = bbbbbuffer[3];
					convert[1] = bbbbbuffer[4];
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					original2 = value;
					value %= 13;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[count][index+1] = value;
					total[count] += value;
//					printf("B 2 VALUE %d %d\n", card[count][index+1], total[count]);	
				}
				else {
					convert[0] = bbbbbuffer[3];
					convert[1] = ' ';
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					original2 = value;
					if (value == 1)
						value = 11;
					card[count][index+1] = value;
					total[count] += value;
//					printf("C VALUE %d %d\n", card[count][index+1], total[count]);	
				}		
			}
			else {
				char c3 = bbbbbuffer[0];
				value = atoi(&c3);
				original = value;
				if (value == 1)
					value = 11;
				card[count][index] = value;
				total[count] += value;
//				printf("D VALUE %d %d\n", card[count][index], total[count]);	
				if (bbbbbuffer[3] != ' ') {
					convert[0] = bbbbbuffer[2];
					convert[1] = bbbbbuffer[3];
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi (convert);
					original2 = value;
					value %= 13;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[count][index+1] = value;	
					total[count] += value;
//					printf("E VALUE %d %d\n", card[count][index+1], total[count]);				
				}
				else {
					convert[0] = bbbbbuffer[2];
					convert[1] = ' ';
					convert[2] = ' ';
					convert[3] = ' ';
					value = atoi(convert);
					original2 = value;
					if (value > 10 || value == 0)
						value = 10;
					else if (value == 1)
						value = 11;
					card[count][index+1] = value;
					total[count] += value;
//					printf("F VALUE %d %d\n", card[count][index+1], total[count]);	
				}
			}





				printf ("Current hand: ");// card[count][0], card[count][1]);
				conversion (original);
				printf ("%c%d ", suite, rank);
				conversion (original2);
				printf ("%c%d\n", suite, rank);
				
				printf ("Current value: %d\n", total[count]);






			if (bbbbbuffer[strlen(bbbbbuffer)] != ' ') {
					printf("Dealer card: ");
					char master[4];
					if (bbbbbuffer[strlen(bbbbbuffer)-1] != ' ') {
						master[1] = bbbbbuffer[strlen(bbbbbuffer)-1];
						master[0] = bbbbbuffer[strlen(bbbbbuffer)];
						master[2] = ' ';
						master[3] = ' ';
					}
					else {
						master[1] = ' ';
						master[0] = bbbbbuffer[strlen(bbbbbuffer)];
						master[2] = ' ';
						master[3] = ' ';
					}
					conversion (atoi(master));
					printf("%c%d\n", suite, rank);
			}
			index += 2;
			if (total[count] == 21) {
				printf("Blackjack\n");
				ret = sendto (sockfd, "Blackjack", BUF_SIZE, 0, (struct sockaddr *) &addr, sizeof(addr));
				if (ret < 0) {
					printf ("Error sending data!\n");
				}
				close (sockfd);
				return 0;
			}
		}
		memset (buffer, 0, BUF_SIZE);
		printf ("\nEnter your move: ");
	}
	close (sockfd);
	return 0;      
}