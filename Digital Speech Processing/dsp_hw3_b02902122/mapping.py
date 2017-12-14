import sys

_input = open(sys.argv[1], "rt")
output = open(sys.argv[2], "wt")

mapping = {}
for line in _input:
    char  = line[:line.find(" ")]
    _list = line[line.find(" ") + 1:].strip().split("/")
    for zu in _list:
        key = zu[:2]
        if key not in mapping:
            mapping[key] = set()
        mapping[key].add(char)

for key in mapping:
    output.write(key + " ")
    for char in mapping[key]:
        output.write(char + " ")
    output.write("\n")
    for char in mapping[key]:
        output.write(char + " " + char + "\n")