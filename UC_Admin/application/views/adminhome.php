    <br><br>
    <h2 style="text-align:center"><b> Distress Calls</b></h2>  

    <table>
        <?php 
        foreach ($distress as $d){
        echo'
        <tr>
            <div class="alert alert-danger " role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" center></span>
                <span class="sr-only">Error:</span>
                    Distress Call by user: '.$d['name'].' ! ! ! <b>Wind: </b>'.$d['wspd'].' m/s '.$d['wdirec'].' NE  <b>SeaTemp: </b> '.$d['sst'].' C <b>IceFrac: </b> '.$d['sif'].'% <b>at Time: </b>'.$d['time'].'
                     <button class="btn btn-danger" type="submit" onclick="myMap('.$d['lat'].', '.$d['lon'].')">Show On Map</button>
            </div>';}
            ?>
            
            <!--<div class="alert alert-danger " role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" center></span>
                <span class="sr-only">Error:</span>
                    Distress Call by user: Wasif ! ! ! <b>Wind: /b>11 m/s 121 NE <b>SeaTemp: </b> 9 C <b>IceFrac: </b> 34%
                     <button class="btn btn-danger" type="submit" onclick="myMap(21, 85)">Show On Map</button>
            </div>
        <div class="alert alert-danger " role="alert">
                <span class="glyphicon glyphicon-exclamation-sign" ></span>
                <span class="sr-only">Error:</span>
                    Some Tourist is in Danger!
                     <button class="btn btn-danger" type="submit" onclick="myMap(23.7257349, 90.3906119)">Show On Map</button>
            </div>-->
        
            
        
            <div id="map" ></div>
        
        </tr>
        
    </table>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyC4LGcxa0bb_eUxxLYVv1sO6NRZ6UbPGsc&callback=myMap">
    </script>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

    <script src="<?php echo base_url("assets/js/bootstrap.min.js"); ?>"></script>
    
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<?php echo base_url("assets/js/ie10-viewport-bug-workaround.js"); ?>"></script>
  </body>
</html>