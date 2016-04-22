<?php
class info_model extends CI_Model 
{
	public function __construct()	
	{
        $this->load->database();
	}	

	public function get_wind_speed($param)
	{
		$sql = "SELECT * FROM REGRESSION WHERE REG_ID=1";
		$query = $this->db->query($sql)->row_array();
		//print_r($query);
		//echo $query['a'];
		$a = $query['a'];
		$b = $query['b'];
		$c = $query['c'];
		$wind_speed = $a + $b*$param['long'] + $c*$param['lat'];
		return $wind_speed;
	}

	public function get_wind_direction($param)
	{
		$sql = "SELECT * FROM REGRESSION WHERE REG_ID=2";
		$query = $this->db->query($sql)->row_array();
		//print_r($query);
		//echo $query['a'];
		$a = $query['a'];
		$b = $query['b'];
		$c = $query['c'];
		$wind_direc = $a + $b*$param['long'] + $c*$param['lat'];
		return $wind_direc;
	}

	public function get_sst($param)
	{
		$sql = "SELECT * FROM REGRESSION WHERE REG_ID=3";
		$query = $this->db->query($sql)->row_array();
		//print_r($query);
		//echo $query['a'];
		$a = $query['a'];
		$b = $query['b'];
		$c = $query['c'];
		$sst = $a + $b*$param['long'] + $c*$param['lat'];
		return $sst;
	}

	public function get_sif($param)
	{
		$sql = "SELECT * FROM REGRESSION WHERE REG_ID=1";
		$query = $this->db->query($sql)->row_array();
		//print_r($query);
		//echo $query['a'];
		$a = $query['a'];
		$b = $query['b'];
		$c = $query['c'];
		$sif = $a + $b*$param['long'] + $c*$param['lat'];
		return $sif;
	}


}