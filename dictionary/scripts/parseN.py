import re

#only for verbs!

rfilename = "data.noun"
wfilename = "" + rfilename + "_MOD"

rf = open(rfilename, 'r')
wf = open(wfilename, 'w')

dict = {}

for line in rf:
    parsed = re.search("([\d]{8}) [\d\w]{2} n [\d\w]{2} ([-\w']*) [^|]* ([\d]{8}) n[^|]*[|] ([-\w \(\),']*)", line)
    if parsed != None:
        res = parsed.group(1) + " :[ " + parsed.group(2) + " : " + parsed.group(3) + " : " + parsed.group(4) + " }"
        #print(res)
        dict[parsed.group(1)] = [parsed.group(2), parsed.group(3), parsed.group(4)]
        

for key in dict:

    if dict.get(key) != None and dict.get(key)[1] != None and dict.get(dict.get(key)[1]):
        dict[key][1] = dict[dict[key][1]][0]
        arr = dict[key]
        print(key + " | " + arr[0] + " | " + arr[1] + " | " + arr[2] )
        wf.write(arr[0] + " | " + arr[2] + "\n")


rf.close
wf.close
