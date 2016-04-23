<?php

/**
*	LAST MODIFIED : 29-06-2015 04:02 PM
*/

defined('BASEPATH') OR exit('No direct script access allowed');

class Admin extends CI_Controller {
	  
	 public function __construct()
     {
          parent::__construct();
		  
		  /**
		  *	Load Libraries , Models and Helpers
		  */
		  
          $this->load->library('session');
          $this->load->helper('form');
          $this->load->helper('url');
          $this->load->helper('html');
		  $this->load->library('form_validation');
		  
		if(!isset($_SESSION["admin_name"]))
		{
			redirect('/home', 'refresh');
		}
		  
		//load models
		$this->load->model('admin_model');
		//$this->load->model('post_model');
		//$data['current_nav']='home';
		//$this->load->view('templates/header',$data);
		
		$this->load->view('templates/header2');
     }
	 
	/**
	*	[ADMIN HOME PAGE]
	*/
	
	public function index()			
	{
		$this->load->view('getuser');
		redirect('admin/home','refresh');
	}
	
	
	
	/**
	*	Admin Logs out
	*/
	
	public function logout()	
	{
		$this->session->sess_destroy();	//!Stop Session 
		
		/**
		*Redirect To Homepage
		*/
		
		redirect('/home', 'refresh');
	}
	/**
	neamul
	*/
	public function home()
	{
		$distress = $this->admin_model->get_distress_call();
		$data['distress'] = $distress;
		$this->load->view('adminhome',$data);
	}

	public function updateData()
	{
		$this->load->view('updateData');
	}

	public function dataTest()
	{
		$this->load->view('dataTest');
	}

	public function getUser($a)
	{
		$data['a'] = $a;
		$this->load->view('getuser');
	}

	public function process()
	{
		echo '\n\n\nn\n\n\n\n\n\n';
		//$temp = ($_POST['dat']);
		//$t = explode(",", $temp);

		$p = array('as', 'df', 'gh');
		$q = json_encode($p);
        //$result = exec( "D:/xampp/htdocs/SeaIceApp/UC_Admin/application/controllers/linearRegression.py" );
		
		$json = `python "D:/xampp/htdocs/SeaIceApp/UC_Admin/application/controllers/linearRegression.py"`;
        echo $json;
        // $args = explode(" ",$result);
       	// echo $args[
        // echo $args[4];
        // echo $args[7];
        echo "\n\n";
        $jsonIterator = new RecursiveIteratorIterator(
		    new RecursiveArrayIterator(json_decode($json, TRUE)),
		    RecursiveIteratorIterator::SELF_FIRST);
        $i=0;
		foreach ($jsonIterator as $key => $val) {
		    if(is_array($val)) {
		        echo "$key:\n";
		    } 
		    else {
		        //echo "$key => $val\n";
		        echo $val;
		        echo "\n";
		        $param[$i++]=$val;
		    }
		   }
		   //print_r($param);
		   $this->admin_model->insert_regression($param);

	}

	public function process2()
	{
		$json = `python "D:/xampp/htdocs/SeaIceApp/UC_Admin/application/controllers/linearRegression.py"`;
        echo $json;
        // $args = explode(" ",$result);
       	// echo $args[
        // echo $args[4];
        // echo $args[7];
        echo "\n\n";
        $jsonIterator = new RecursiveIteratorIterator(
		    new RecursiveArrayIterator(json_decode($json, TRUE)),
		    RecursiveIteratorIterator::SELF_FIRST);
        $i=0;
		foreach ($jsonIterator as $key => $val) {
		    if(is_array($val)) {
		        echo "$key:\n";
		    } 
		    else {
		        //echo "$key => $val\n";
		        echo $val;
		        echo "\n";
		        $param[$i++]=$val;
		    }
		   }
		   //print_r($param);
		   $this->admin_model->insert_regression($param);

		echo '\n\n\nn\n\n\n\n\n\n';
		
		$myfile1 = fopen("D:/xampp/htdocs/SeaIceApp/UC_Admin/application/wind1.json", "w") or die("Unable to open file!");
		fwrite($myfile1,  $_POST['wnd1']);
		fclose($myfile1);
		
		$myfile2 = fopen("D:/xampp/htdocs/SeaIceApp/UC_Admin/application/wind2.json", "w") or die("Unable to open file!");
		fwrite($myfile2,  $_POST['wnd2']);
		fclose($myfile2);

		$myfile3 = fopen("D:/xampp/htdocs/SeaIceApp/UC_Admin/application/sif1.json", "w") or die("Unable to open file!");
		fwrite($myfile3,  $_POST['sif1']);
		fclose($myfile3);

		$myfile = fopen("D:/xampp/htdocs/SeaIceApp/UC_Admin/application/sst1.json", "w") or die("Unable to open file!");
		fwrite($myfile,  $_POST['sst1']);
		fclose($myfile);

		

	}

	public function process3()
	{
		echo "everything is working fine";
	}

	/**
	neamul 2
	*/

	public function showImg()
	{
		$posts = $this->admin_model->get_all_posts();
		for($i=0;$i<sizeof($posts);$i++)
		{
			$name[$i] = $this->admin_model->get_username($posts[$i]['user_id']);
			$posts[$i]['name'] = $name[$i];
			$param['lat'] = $posts[$i]['lat'];
			$param['lon'] = $posts[$i]['lon'];
			$posts[$i]['wspd'] = $this->admin_model->get_wind_speed($param);
			$posts[$i]['wdirec'] = $this->admin_model->get_wind_direction($param);
			$posts[$i]['sst'] = $this->admin_model->get_sst($param);
			$posts[$i]['sif'] = ($this->admin_model->get_sif($param)) * 100;
		}
		$data['posts'] = $posts;
		$this->load->view('showimageAudio',$data);
	}

	public function getPosts()
	{
		$result = $this->admin_model->get_all_posts();
		$data['posts'] = $result;
	}

}
