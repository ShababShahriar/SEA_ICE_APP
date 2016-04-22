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
	
}

?>