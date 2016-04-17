#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <algorithm>
#include <stdlib.h>
#include <string.h>
#include <assert.h>
#include <vector>
#include <map>

using namespace std;

typedef struct data {
	string date;
	string plate;
	short int camera_id;
	string camera;
} Data;

typedef map<string, vector<Data> > myMap;

bool compare(const Data &a, const Data &b) {
	return (a.date < b.date);
}

int main (int argc, char *argv[]) {
	char open[50];
	strcpy(open, argv[1]);
	vector<Data> myVector, possible;
	myMap record, recordA;
	string temp, idName;
	ifstream myFile;

	for (int i = 1; i < 101; i++) {
		vector<Data> myVector;
		std::ostringstream x;
		x << i;
		string name = open;
		name += x.str();
		name += "NS.txt";
		myFile.open(name.c_str());
		assert(myFile != NULL);
		while(getline(myFile, temp)) {
			idName = temp.substr(20, 6);
			Data newData;
			newData.date = temp.substr(0, 19);
			newData.plate = temp.substr(20, 6);
			newData.camera_id = i;
			newData.camera = "NS";
			recordA[idName].push_back(newData);
			for (int i = 0; i < 5; i++)
				for (int k = i+1; k < 6; k++) {
					string tempString = idName;
					tempString[i] =  '*';
					tempString[k] =  '*';
					record[tempString].push_back(newData);
				}
		}
		myFile.close();
		name = open;
		name += x.str();
		name += "SN.txt";
		myFile.open(name.c_str());
		                assert(myFile != NULL);
		while(getline(myFile, temp)) {
			idName = temp.substr(20, 6);
			Data newData;
			newData.date = temp.substr(0, 19);
			newData.plate = temp.substr(20, 6);
			newData.camera_id = i;
			newData.camera = "SN";
			recordA[idName].push_back(newData);
			for (int i = 0; i < 5; i++)
				for (int k = i+1; k < 6; k++) {
					string tempString = idName;
					tempString[i] =  '*';
					tempString[k] =  '*';
					record[tempString].push_back(newData);
				}
		}
		myFile.close();
	}

	myFile.close();
	string tempfile = open;
	tempfile += "query.txt";
	myFile.open(tempfile.c_str());
	                assert(myFile != NULL);

	while(getline(myFile, temp)) {
		if (temp[0] == 'p') {
			string id_query = temp.substr(9, 6);
			string date1 = temp.substr(17, 19);
			string date2 = temp.substr(39, 19);
			Data struct1, struct2;
			struct1.date = date1;
			struct2.date = date2;
			std::vector<Data>::iterator start;
			for (int i = 0; i < 5; i++)
				for (int k = i+1; k < 6; k++) {
					string tempString = id_query;
					tempString[i] =  '*';
					tempString[k] =  '*';
					for(start = record[tempString].begin(); start != record[tempString].end(); ++start) {
						if ((*start).date >= date1 && (*start).date <= date2)
							possible.push_back(*start);
					}
				}
			if (possible.size() != 0) {
				sort(possible.begin(), possible.end(), compare);
				cout << possible[0].date << ' ' << possible[0].plate << ' ' << possible[0].camera_id << possible[0].camera << endl;
				for (int i = 1; i < possible.size(); ++i)
					if (possible[i].date != possible[i-1].date)
						cout << possible[i].date << ' ' << possible[i].plate << ' ' << possible[i].camera_id << possible[i].camera << endl;
			}
			possible.clear();
		}

		else if (temp[0] == 'a') {
			idName = temp.substr(8, 6);
			sort(recordA[idName].begin(), recordA[idName].end(), compare);
			if (!record[idName].empty()) {
				for (vector<Data>::iterator iter = recordA[idName].begin() + 1; iter != recordA[idName].end(); ++iter) {
					if ((iter -> camera) != ((iter-1) -> camera)) {
						if ((iter -> camera_id) != ((iter-1) -> camera_id))
							cout << (iter-1) -> date << ' '  << (iter-1) -> camera_id << (iter-1) -> camera << " - " << iter -> date << ' ' << iter-> camera_id << iter->camera << endl;
					}
					else {
						if ((abs((iter -> camera_id) - ((iter-1) -> camera_id))) != 1)
							cout << (iter-1) -> date << ' '  << (iter-1) -> camera_id << (iter-1) -> camera << " - " << iter -> date << ' ' << iter-> camera_id << iter->camera << endl;
					}
				}
			}
		}
		cout << "--\n";
	}
	return 0;
}
