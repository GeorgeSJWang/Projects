#include "Ngram.h"
#include <stdio.h>
#include <string>
#include <map>
#include <vector>

using namespace std;
const int ngram_order = 2;
Vocab voc;
Ngram lm(voc, ngram_order);

void init_map(map<string, vector<string> > &Map, char *fn) {
    FILE *fp = fopen(fn, "r");
    char line[10000];
    while (fgets(line, 10000, fp) != NULL) {
    	int length = strlen(line) - 1;
        if (line[length] == '\n')
            line[length] = '\0';
        char*  tok = strtok(line, " ");
        string key = tok;
        tok = strtok(NULL, " ");
        while (tok != NULL) {
            Map[key].push_back(tok);
            tok = strtok(NULL, " ");
        }
    }
    Map["<s>"].push_back("<s>");
    Map["</s>"].push_back("</s>");
    return;
}

void init_character(vector<string> &v, char line[]) {
    char* tok = strtok(line, " ");
    v.push_back("<s>");
    while (tok != NULL) {
        v.push_back(tok);
        tok = strtok(NULL, " ");
    }
    v.push_back("</s>");
    return;
}

double _Bigram(const char *w1, const char *w2) {
    VocabIndex w1 = voc.getIndex(w1);
    VocabIndex w2 = voc.getIndex(w2);
    if (w1 == Vocab_None)
        w1 = voc.getIndex(Vocab_Unknown);
    if (w2 == Vocab_None)
        w2 = voc.getIndex(Vocab_Unknown);
    VocabIndex context[] = {w1, Vocab_None};
    return lm.wordProb(w2, context);
}

string viterbi(vector<string> &v, map<string, vector<string> >Map) {
    double table[v.size()][1500] = {0};
    double max, prob;
    int prev[v.size()][1500] = {0};
    for (int i=1; i<v.size(); i++) {
        for (int j=0; j<Map[v[i]].size(); j++) {
            prob = table[i-1][0] + _Bigram(Map[v[i-1]][0].c_str(), Map[v[i]][j].c_str());
            max = prob;
            prev[i][j]  = 0;
            table[i][j] = prob;
            for (int k=1; k<Map[v[i-1]].size(); k++) {
                if ((prob = table[i-1][k] + _Bigram(Map[v[i-1]][k].c_str(), Map[v[i]][j].c_str())) > max) {
                    table[i][j] = prob;
                    max = prob;
                    prev[i][j] = k;
                }
            }
        }
    }
    vector<string> ans_vec(v.size());
    int next = 0, pt = v.size()-1;
    ans_vec[pt] = "</s>";
    for (int i=pt; i>0; i--) {
        ans_vec[i-1] = Map[v[i-1]][prev[i][next]];
        next = prev[i][next];
    }
    string ret = "";
    for (int i=0; i<pt; i++) {
        ret += ans_vec[i];
        ret += " ";
    }
    ret += ans_vec[pt];
    ret += "\n";
    return ret;
}

int main(int argc, char** argv) {
	FILE *fp = fopen(argv[1], "r");
    map<string, vector<string> > zmap;
    File lmFile(argv[2], "r");
    lm.read(lmFile);
    lmFile.close();
    init_map(zmap, argv[3]);
    char line[5000];
    while (fgets(line, 5000, fp) != NULL) {
    	int length = strlen(line)-1;
        if (line[length] == '\n')
            line[length] = '\0';
        vector<string> _characters;
        init_character(_characters, line);
        string tmp  = viterbi(_characters, zmap);
        printf("%s", tmp.c_str());
    }
    return 0;
}
