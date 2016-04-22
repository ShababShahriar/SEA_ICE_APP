
import sys
import base64
import json

# Open a file
fo = open("foo.txt", "wb")

# Load the data that PHP sent us
try:
	fo.write( "Python is a great language.\nYeah its great!!\n" + sys.argv[1]);
	json_input = sys.argv[1]
	data = json.loads(json_input)
except:
    print "ERROR1"
    sys.exit(1)

# Generate some data to send to PHP
result = {'status': 'Yes!'}

# Send it to stdout (to PHP)
print (json.dumps(data))
