#include <stdio.h>
#include <string.h>
#include "hmm.h"

int main(int argc, char* argv[]) {
	int  seq[10000][50];
	char tmp[52];
	int  i=0, j=0, k=0;
	FILE *fp = fopen(argv[3], "r");
	while (fscanf(fp, "%s", tmp) != EOF) {
		for (i=0; i<50; i++)
			seq[j][i] = tmp[i]-'A';
		j++;
	}
	fclose (fp);
	int iter = atoi(argv[1]);
	HMM hmm;
	loadHMM (&hmm, argv[2]);
	double alpha[50][hmm.state_num], beta[50][hmm.state_num], gamma[50][hmm.state_num], gamma_sum[hmm.observ_num][hmm.state_num], epsilon[49][hmm.state_num][hmm.state_num];
	while (iter--) {
		//initialize
		memset (gamma, 0, sizeof(gamma[0][0])*50*hmm.state_num);
		memset (gamma_sum, 0, sizeof(gamma_sum[0][0])*hmm.observ_num*hmm.state_num);
		memset (epsilon, 0, sizeof(epsilon[0][0][0])*49*hmm.state_num*hmm.state_num);
		for (int it=0; it<10000; it++) {
			//alpha
			for (i=0; i<hmm.state_num; i++)
				alpha[0][i] = hmm.initial[i]*hmm.observation[seq[it][0]][i];
			for (i=0; i<49; i++) {
				for (j=0; j<hmm.state_num; j++) {
					double sum=0;
					for (k=0; k<hmm.state_num; k++) 
						sum += alpha[i][k]*hmm.transition[k][j];
					alpha[i+1][j] = sum*hmm.observation[seq[it][i+1]][j];
				}
			}
			//beta
			for (i=0; i<hmm.state_num; i++)
				beta[49][i] = 1;
			for (i=48; i>=0; i--) {
				for (j=0; j<hmm.state_num; j++)
					for (beta[i][j]=0, k=0; k<hmm.state_num; k++)
						beta[i][j] += hmm.transition[j][k] * hmm.observation[seq[it][i+1]][k]*beta[i+1][k];
			}
			//gamma
			for (i=0; i<50; i++) {
				double sum=0;
				for (j=0; j<hmm.state_num; j++) 
					sum += alpha[i][j]*beta[i][j];
				for (j=0; j<hmm.state_num; j++) {
					gamma[i][j] += alpha[i][j]*beta[i][j]/sum;
					gamma_sum[seq[it][i]][j] += alpha[i][j]*beta[i][j]/sum;
				}
			}
			//epsilon
			for (j=0; j<49; j++) {
				double sum=0;
				for (k=0; k<hmm.state_num; k++)
					for (i=0; i<hmm.state_num; i++)
						sum += alpha[j][i]*hmm.transition[i][k]*hmm.observation[seq[it][j+1]][k]*beta[j+1][k];
				for (k=0; k<hmm.state_num; k++)
					for (i=0; i<hmm.state_num; i++)
						epsilon[j][k][i] += alpha[j][k]*hmm.transition[k][i]*hmm.observation[seq[it][j+1]][i]*beta[j+1][i]/sum;
			}
		}
		//pi
		for (i=0; i<hmm.state_num; i++)
			hmm.initial[i] = gamma[0][i]/10000;
		//a
		for (i=0; i<hmm.state_num; i++) {
			double sum=0;
			for (j=0; j<49; j++)
				sum += gamma[j][i];
			for (j=0; j<hmm.state_num; j++) {
				double tmp=0;
				for (k=0; k<49; k++)
					tmp += epsilon[k][i][j];
				hmm.transition[i][j] = tmp/sum;
			}
		}
		//b
		for (i=0; i<hmm.observ_num; i++) {
			for (j=0; j<hmm.state_num; j++) {
				double sum=0;
				for (k=0; k<50; k++)
					sum += gamma[k][j];
				hmm.observation[i][j] = gamma_sum[i][j]/sum;
			}
		}
	}
	dumpHMM (fopen(argv[4], "w"), &hmm);
	return 0;
}