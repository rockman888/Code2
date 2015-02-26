<?php
	// khi goi duong dan nay
	// http://localhost/cdkey.php?user=vilh&&pass=123456	
	// $_GET
	// echo $_GET['name']. "</br>";
	// echo $_GET['email'];
	
	// POST
	// http://localhost/cdkey.php
	// $user = isset($_POST['user']);
	// $pass = isset($_POST['pass']);
	
	// $user = isset($_GET['user']);
	// $pass = isset($_GET['pass']);
	
	// if ( $user && $pass )	// ton tai
	// {
		// $user = $_GET['user'];
		// $pass = $_GET['pass'];
		
		// echo "CD Key cua ban la: 123456789 - " . $user ; 
	// }	
		
	// http://localhost/cdkey.php
	$user = isset($_POST['user']);
	$pass = isset($_POST['pass']);
	
	if ($user && $pass)
	{
		$user = $_POST['user'];
		$pass = $_POST['pass'];
		
		echo "CD Key cua ban la: 1688888 - " . $user . " - " . $pass;
	}
?>

