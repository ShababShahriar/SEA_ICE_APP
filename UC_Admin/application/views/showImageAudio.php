	<br><br>
	<h2 style="text-align:center"><b>Show Images & Audios</b></h2> 

	<div>
		<div class="col-md-1"></div>
		<div class="col-md-10">
			<table class="table table-hover">
				<thead>
					<tr>
						<td><b>Serial No<b></td>
						<td><b>Images</b></td>
						<td><b>Audios</b></td>
						<td><b>Wind (Speed & Direc)</b></td>
						<td><b>SeaTemp</b></td>
						<td><b>IceFraction</b></td>
						<td><b>CurrentVelocity</b></td>
						<td><b>Posted by</b></td>
					</tr>
				</thead>
				<tbody><!--
					<?php 
					for($i=0;$i<sizeof($posts);$i++){
					echo'
					<tr>
						<td>1.</td>
						<td><img src="data:image/jpg;base64,' . base64_encode($posts[$i]['image']) . '" style="height:100px"/></td>
						<td><audio src="data:audio/wav;base64,' . base64_encode($posts[$i]['audio']) . '" controls loop></audio></td>
						<td>'.$posts[$i]['wspd'].' m/s '.$posts[0]['wdirec'].' NE</td>
						<td>'.$posts[$i]['sst'].' C</td>
						<td>'.$posts[$i]['sif'].'%</td>
						<td>12 m/s</td>
						<td>'.$posts[$i]['name'].'</td>
					</tr>';
					}
					?>-->
					<tr>
						<td>1.</td>
						<td><img src="<?php echo base_url("assets/img/Capture2.png"); ?>" style="height:100px"></td>
						<td><audio src="<?php echo base_url("assets/audio/MashAllah.mp3"); ?>" controls loop></audio></td>
						<td>8.464 m/s 90 NE</td>
						<td>4.35 C</td>
						<td>86.21%</td>
						<td>Unknown</td>
						<td>Shabab</td>
					</tr>
					<tr>
						<td>2.</td>
						<td><img src="<?php echo base_url("assets/img/IP.png"); ?>" style="height:100px"></td>
						<td><audio src="<?php echo base_url("assets/audio/MashAllah.mp3"); ?>" controls loop></audio></td>
						<td>9.11 m/s 198 NE</td>
						<td>6.35 C</td>
						<td>98.124%</td>
						<td>Unknown</td>
						<td>Onix</td>
					</tr>
					<tr>
						<td>3.</td>
						<td><img src="<?php echo base_url("assets/img/Capture2.png"); ?>" style="height:100px"></td>
						<td><audio src="<?php echo base_url("assets/audio/MashAllah.mp3"); ?>" controls loop></audio></td>
						<td>11 m/s 124 NE</td>
						<td>9.5968 C</td>
						<td>99.9234%</td>
						<td>Unknown</td>
						<td>Wasif</td>
					</tr>
			</table>
		</div>
		<div class="col-md-1"></div>
	</div>



	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>

    <script src="<?php echo base_url("assets/js/bootstrap.min.js"); ?>"></script>
    
    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <script src="<?php echo base_url("assets/js/ie10-viewport-bug-workaround.js"); ?>"></script>
  </body>
</html>