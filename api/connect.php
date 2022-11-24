<?php 
	$hostname = "localhost";
	$username = "id19889615_triscode";
	$password = "KhuongVietNghia@123";
	$database = "id19889615_weatherct";

	$con = mysqli_connect($hostname, $username, $password, $database);
	if (!$con) {
		die("Connection failed: " . mysqli_connect_error());
	}
 ?>