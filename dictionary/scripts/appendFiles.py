import sys

origfilename = sys.argv[1]
appfilename = sys.argv[2]

of = open(origfilename, 'a')
af = open(appfilename, 'r')

dict = {}

for line in af:
    print(line)
    of.write(line)

of.close
af.close
