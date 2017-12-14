#include <stdio.h>
#include <stdlib.h>
#include "hmm.h"

int main(int argc, char **argv) {
	HMM hmm[5];
	int    i, j, k, mindex, model_max;
	char   seq[52];
	double pmax, tmp, delta[50][6];
	load_models(argv[1], hmm, 5);
	FILE *fp1 = fopen(argv[2], "r");
	FILE *fp2 = fopen(argv[3], "w");
	while (fscanf(fp1, "%s", seq) != EOF) {
		for (pmax=0, model_max=0, mindex=0; mindex<5; mindex++) {
			for (i=0; i<6; i++)
				delta[0][i] = hmm[mindex].initial[i] * hmm[mindex].observation[seq[0]-'A'][i];
			for (i=1; i<50; i++) {
				for (j=0; j<6; j++) {
					for (delta[i][j]=0, k=0; k<6; k++) {
						double tmp = delta[i-1][k] * hmm[mindex].transition[k][j]* hmm[mindex].observation[seq[i]-'A'][j];
						if (tmp > delta[i][j])
							delta[i][j] = tmp;
					}
				}
			}
			for (tmp=0, i=0; i<6; i++)
				if (delta[49][i] > tmp)
					tmp = delta[49][i];
			if (tmp > pmax) {
				model_max = mindex;
				pmax = tmp;
			}
		}
		fprintf (fp2, "model_0%d.txt %e\n", model_max+1, pmax);
	}
	fclose (fp1);
	fclose (fp2);
	return 0;
}