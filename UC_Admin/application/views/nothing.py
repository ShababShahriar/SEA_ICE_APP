import sys
import os
import json

def main():
    print "before opening file"

    try:
        # Open a file
        with open("da_email.txt", "w") as f:
            f.write(pretty_email)
        fo = open("foo.txt", "wb")
        fo.seek(0)
        fo.write( "Python is a great language.\nYeah its great!!\n");
        print "Trying to open and write to file..."
        
    except Exception as e:
        fo.exception(e)
        print "Sorry, couldn't open new file"

    else:
        

        # Close opend file
        fo.close()
    
    print "after closing file"
    return json.dumps([1,2,3])


if __name__ == "__main__":
    main()
