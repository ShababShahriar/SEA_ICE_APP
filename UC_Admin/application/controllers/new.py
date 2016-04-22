from scipy.optimize import curve_fit
import scipy
import json



def fn(x, a, b, c):
    return a + b*x[0] + c*x[1]

# y(x0,x1) data:
#    x0=0 1 2
# ___________
# x1=0 |0 1 2
# x1=1 |1 2 3
# x1=2 |2 3 4
''' 
x = scipy.array([[0,1,2,0,1,2,0,1,2,],[0,0,0,1,1,1,2,2,2]])
y = scipy.array([0,1,2,1,2,3,2,3,4])
popt, pcov = curve_fit(fn, x, y)
print popt
'''




with open("newfile.json") as json_file:
    json_data = json.load(json_file)
   
allDataRows = json_data["table"]["rows"]

diction={}
    
firstParams=[]
secondParams=[]
values=[]
allDataRows.reverse()
for i in range(len(allDataRows)):
    #print(i)
    #print (allDataRows[i])
    tempKey = allDataRows[i][0:2]
    key = tuple(tempKey)
    val = allDataRows[i][3]
    if (diction.has_key(key))==False:
        diction[key] = val
    #print(key,val)
        
#print diction
for k in diction.keys():
    firstParams.append(k[0])
    secondParams.append(k[1])
    values.append(diction[k])
    #print(firstParams)
    
    #print(secondParams)
    #print(values)
ans = reg(firstParams,secondParams,values)
print "ans"

def reg(first,second,val):
	
	x = scipy.array([first,second])
	y = scipy.array(val)
	popt, pcov = curve_fit(fn, x, y)
	return popt
def fn(x, a, b, c):
    return a + b*x[0] + c*x[1]
