<?php
class Admin_model extends CI_Model 
{
	
    public function __construct()	//DONE
	{
        $this->load->database();
	}
		
	public function get_loginInfo($data)	//DONE
	{
		$sql='SELECT * FROM admin where `admin_name` = ? and `password` = ?';
		$query = $this->db->query($sql,array($data['admin_name'],$data['password']));
		return $query;
	}

	/**
	neamul
	*/

	public function insert_regression($param)
	{
		
		$sq = "Delete from regression where reg_id=1";
		$query = $this->db->query($sq);
		$sq = "Delete from regression where reg_id=2";
		$query = $this->db->query($sq);
		$sq = "Delete from regression where reg_id=3";
		$query = $this->db->query($sq);
		$sq = "Delete from regression where reg_id=4";
		$query = $this->db->query($sq);

		$sql = "Insert into regression values('',4,?,?,?)";
		$temp['a'] = $param[0];
		$temp['b'] = $param[1];
		$temp['c'] = $param[2];
		$query = $this->db->query($sql, $temp);
		echo "query executed successfully wspd";

		$sql = "Insert into regression values('',2,?,?,?)";
		$temp['a'] = $param[3];
		$temp['b'] = $param[4];
		$temp['c'] = $param[5];
		$query = $this->db->query($sql, $temp);
		echo "query executed successfully wd";

		$sql = "Insert into regression values('',3,?,?,?)";
		$temp['a'] = $param[6];
		$temp['b'] = $param[7];
		$temp['c'] = $param[8];
		$query = $this->db->query($sql, $temp);
		echo "query executed successfully sst";

		$sql = "Insert into regression values('',1,?,?,?)";
		$temp['a'] = $param[9];
		$temp['b'] = $param[10];
		$temp['c'] = $param[11];
		$query = $this->db->query($sql, $temp);
		echo "query executed successfully sif";
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
		$wind_speed = $a + $b*$param['lon'] + $c*$param['lat'];
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
		$wind_direc = $a + $b*$param['lon'] + $c*$param['lat'];
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
		$sst = $a + $b*$param['lon'] + $c*$param['lat'];
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
		$sif = $a + $b*$param['lon'] + $c*$param['lat'];
		return $sif;
	}

	public function get_all_posts()
	{
		$sql = "SELECT * FROM POST";
		$query = $this->db->query($sql)->result_array();
		return $query;
	}

	public function get_username($id)
	{
		$sql = "SELECT user_name FROM user WHERE user_id=?";
		$t['user_id'] = $id;
		$query = $this->db->query($sql,$t)->row_array();
		return $query['user_name'];
	}

	public function get_distress_call()
	{
		$sql = "SELECT * FROM DISTRESS";
		$query = $this->db->query($sql)->result_array();
		for($i=0;$i<sizeof($query);$i++)
		{
			$query[$i]['name'] = $this->get_username($query[$i]['user_id']);
			$sql1 = "Select * from distress_situation where distress_id=?";
			$temp['distress_id'] = $query[$i]['distress_id'];
			$result = $this->db->query($sql1, $temp)->row_array();
			$param['lat'] = $query[$i]['lat'];
			$query[$i]['lon'] = $query[$i]['long'];
			$query[$i]['wspd'] = $result['wind_speed'];
			$query[$i]['wdirec'] = $result['wind_direction'];
			$query[$i]['sst'] = $result['sea_surface_temp'];
			$query[$i]['sif'] = $result['sea_ice_frac'];
		}
		return $query;

	}
	
}

?>