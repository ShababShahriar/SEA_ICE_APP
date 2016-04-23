<!DOCTYPE html>
<html>
<head>
  <script
    src="https://ajax.googleapis.com/ajax/libs/angularjs/1.2.5/angular.min.js"></script>
<script>




function simpleHttpRequest() {
  var today = new Date();
  console.log(today);
  
  today.setDate(today.getDate() - 2);
  console.log(today);
  var dd = today.getDate();
  var mm = today.getMonth()+1; //January is 0!

  var yyyy = today.getFullYear();
  if(dd<10){
        dd='0'+dd
    } 
    if(mm<10){
        mm='0'+mm
    } 
  console.log(dd,mm,yyyy);

                      /**wspeed**/
  var url = "http://coastwatch.pfeg.noaa.gov/erddap/tabledap/";
  var id="cwwcNDBCMet";
  var others1=".json?longitude,latitude,time,wspd&time>="
  var others2=yyyy+"-"+mm+"-"+dd+"T12:00:00Z&latitude>=50.411196&latitude<=73.925901&longitude<=-142.461345&longitude>=-178.541792";
  url=url+id+others1+others2;
  console.log(url);
  var request;
  if (window.XMLHttpRequest) {
    // code for IE7+, Firefox, Chrome, Opera, Safari
    request=new XMLHttpRequest();
  } else { // code for IE6, IE5
    request=new ActiveXObject("Microsoft.XMLHTTP");
  }
  // request.open("GET", chrome.extension.getURL('/config_resources/config.json'), true);
  request.open("GET", url, true);
  request.send(null);
  request.onreadystatechange = function() {
    if (request.readyState == 4) {
      if (request.status == 200)
        document.getElementById("1wnd").value = request.responseText;
        console.log(request.responseText);
      
    }
  }
                           /**wind direction**/
  var url2 = "http://coastwatch.pfeg.noaa.gov/erddap/tabledap/";
  var id2="cwwcNDBCMet";
  var others11=".json?longitude,latitude,time,wd&time>="
  var others22=yyyy+"-"+mm+"-"+dd+"T12:00:00Z&latitude>=50.411196&latitude<=73.925901&longitude<=-142.461345&longitude>=-178.541792";
  url2=url2+id2+others11+others22;
  console.log(url2);
  var request1;
  if (window.XMLHttpRequest) {
    // code for IE7+, Firefox, Chrome, Opera, Safari
    request1=new XMLHttpRequest();
  } else { // code for IE6, IE5
    request1=new ActiveXObject("Microsoft.XMLHTTP");
  }
  // request.open("GET", chrome.extension.getURL('/config_resources/config.json'), true);
  request1.open("GET", url2, true);
  request1.send(null);
  request1.onreadystatechange = function() {
    if (request1.readyState == 4) {
      if (request1.status == 200)
        document.getElementById("2wnd").value = request1.responseText;
        console.log(request1.responseText);
      
    }
  }

                      /**sst1**/
  var req;
  var url1 = "http://coastwatch.pfeg.noaa.gov/erddap/tabledap/";
  var id1="cwwcNDBCMet";
  var otherss1=".json?longitude,latitude,time,wtmp&time>="
  var otherss2=yyyy+"-"+mm+"-"+dd+"T12:00:00Z&latitude>=50.411196&latitude<=73.925901&longitude<=-142.461345&longitude>=-178.541792";
  url1=url1+id1+otherss1+otherss2;
  console.log(url1);
  if (window.XMLHttpRequest) {
    // code for IE7+, Firefox, Chrome, Opera, Safari
    req=new XMLHttpRequest();
  } else { // code for IE6, IE5
    req=new ActiveXObject("Microsoft.XMLHTTP");
  }
  // request.open("GET", chrome.extension.getURL('/config_resources/config.json'), true);
  req.open("GET", url1, true);
  req.send(null);
  req.onreadystatechange = function() {
    if (req.readyState == 4) {
      if (req.status == 200)
        document.getElementById("1sst").value = req.responseText;
        console.log(req.responseText);
      
    }
  }

                    /**sea ice fraction  */
  var r;
  var u = "http://coastwatch.pfeg.noaa.gov/erddap/griddap/jplMURSST.json?sea_ice_fraction[(";
  var other2=yyyy+"-"+mm+"-"+dd+"T12:00:00Z)][(50.9945):10:(73.9945)][(-173.995):10:(-142.995)]";
  u=u+other2;
  console.log(u);
  if (window.XMLHttpRequest) {
    // code for IE7+, Firefox, Chrome, Opera, Safari
    r=new XMLHttpRequest();
  } else { // code for IE6, IE5
    r=new ActiveXObject("Microsoft.XMLHTTP");
  }
  // request.open("GET", chrome.extension.getURL('/config_resources/config.json'), true);
  r.open("GET", u, true);
  r.send(null);
  r.onreadystatechange = function() {
    if (r.readyState == 4) {
      if (r.status == 200)
        document.getElementById("1sif").value = r.responseText;
        console.log(r.responseText);
      
    }
  }
};
/*
window.setInterval(function(){
  
}, 120000);
*/

</script>
</head>
<body>
   <br><br><br><br><br>
<div>
  <div class="col-md-3"><span class="label label-success">Press the button to get data from data center</span></div>
  <div class="col-md-6">
    <button class="btn btn-success" onclick="simpleHttpRequest()">Get Data</button>
  </div>
    <div class="col-md-3"></div>
</div>

<br><br><br><br><br>
<br>
<span >
<div>
  <div class="col-md-3"><span class="label label-primary">Press the button to send data</span></div>
  <div class="col-md-6">
    <form method="post" action="process2">
        <input type="hidden" name="sst1" id="1sst" />
        <input type="hidden" name="sif1" id="1sif" />
        <input type="hidden" name="wnd1" id="1wnd" />
        <input type="hidden" name="wnd2" id="2wnd" />
        <button class="btn btn-warning" type="submit">Send Data</button>
      </form>
    </div>
    <div class="col-md-3"></div>
</div>
  <br><br><br><br><br>



</body>
</html>


