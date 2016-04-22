<!DOCTYPE HTML>
<html>
<head>
	
    
	<script type="text/javascript" src="<?php echo base_url("assets/js/canvasjs.min.js"); ?>"> </script>
  	<script type="text/javascript" src="<?php echo base_url("assets/js/jquery-1.11.2.min.js"); ?>"></script>

	<script type="text/javascript" src="<?php echo base_url("assets/js/bootstrap.js"); ?>"></script>

	<style>
      html, body {
        height: 80%;
        margin: 0;
        padding: 0;
      }
      #map {
        height: 100%;
      }
    </style>
    <script>

      function myMap() {
        var jArray = [];
        jArray[0] =23.8103; jArray[1] = 90.4125;
        jArray[2] = 22.3475; jArray[3] = 91.8123;
        jArray[4] = 24.9045; jArray[5] = 91.8611;
        jArray[6] = 24.3636; jArray[7] = 88.6241;
        jArray[8] = 22.7029; jArray[9] = 90.3466;
        jArray[10] = 25.7439 ; jArray[11] = 89.2572;
        jArray[12] = 22.8456; jArray[13] = 89.5403;
          var myLatLng = {lat: jArray[0] , lng: jArray[1] };
         // console.log(a);
          var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 8,
            center: myLatLng
          });
          myLatLng = {lat: jArray[0], lng: jArray[1] };
          var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
          }); 

        for(var i=0;i<6;i++)
        {
          myLatLng = {lat: jArray[i*2] , lng: jArray[i*2+1] };

          // var map = new google.maps.Map(document.getElementById('map'), {
          //   zoom: 10,
          //   center: myLatLng
          // });

          var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
          }); 
        }
      }
    </script>
  </head>
  <body>
    <div id="map" ></div>
    <button onclick="myMap()">try it</button>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4LGcxa0bb_eUxxLYVv1sO6NRZ6UbPGsc&callback=myMap">
    </script>

          <p>Click the button to trigger a function that will output "Hello World" in a p element with id="demo".</p>

      

      <p id="demo"></p>

      <script>
      
      window.setInterval(function() {
          var jArray = [];
        jArray[0] =23.8103; jArray[1] = 90.4125;
        jArray[2] = 22.3475; jArray[3] = 91.8123;
        jArray[4] = 24.9045; jArray[5] = 91.8611;
        jArray[6] = 24.3636; jArray[7] = 88.6241;
        jArray[8] = 22.7029; jArray[9] = 90.3466;
        jArray[10] = 25.7439 ; jArray[11] = 89.2572;
        jArray[12] = 22.8456; jArray[13] = 89.5403;
          var myLatLng = {lat: jArray[0] , lng: jArray[1] };
         // console.log(a);
          var map = new google.maps.Map(document.getElementById('map'), {
            zoom: 8,
            center: myLatLng
          });
          myLatLng = {lat: jArray[0], lng: jArray[1] };
          var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
          }); 

        for(var i=0;i<6;i++)
        {
          myLatLng = {lat: jArray[i*2] , lng: jArray[i*2+1] };

          // var map = new google.maps.Map(document.getElementById('map'), {
          //   zoom: 10,
          //   center: myLatLng
          // });

          var marker = new google.maps.Marker({
            position: myLatLng,
            map: map,
            title: 'Hello World!'
          }); 
        }
      },);
      </script>

  </body>

	
</html>