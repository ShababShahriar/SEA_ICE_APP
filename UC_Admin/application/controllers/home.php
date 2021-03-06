<?php
defined('BASEPATH') OR exit('No direct script access allowed');

class Home extends CI_Controller {
	 
	 public function __construct()		//DONE
     {
          parent::__construct();
		  
		  //Load Necessary Libraries and helpers
          
		  $this->load->library('session');
          $this->load->helper('form');
          $this->load->helper('url');
          $this->load->helper('html');
		  $this->load->library('form_validation');
		  
		  // <Load Admin Model>
		  $this->load->model('admin_model');
     }
	 
	public function index()
	{
		
		if(isset($_SESSION["admin_name"]))
		{
			redirect('/admin', 'refresh');
		}
		else
		{
			$data = array(
               'login_error' => false
			);
			$this->load->view('newlogin',$data);
			//$this->load->view('admin-login',$data);
		}
	}
	
	public function login()		
	{
		if(isset($_POST['admin_name']) && isset($_POST['password']))
		{
			$data = array('admin_name'=>trim($_POST['admin_name']),'password'=>($_POST["password"]));
			
			$query= $this->admin_model->get_loginInfo($data);
			//print_r($query->row_array());
			
			if($query->num_rows()==1)
			{
				$loginInfo=$query->row_array();
				$_SESSION["admin_name"]=$loginInfo['admin_name'];
				
				//Load User Admin Page
				redirect('/admin', 'refresh');
			}
			else
			{
				$data = array(
				   'login_error' => true
				);
				$this->load->view('newlogin',$data);
			}
			
		}
		else
		{
			$data = array(
				   'login_error' => false
				);
			$this->load->view('newlogin',$data);
		}
		
	}
}
