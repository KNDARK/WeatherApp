<?php 
	$hostname = "localhost";
	$username = "id19885245_triscode";
	$password = "KhuongVietNghia@123";
	$database = "id19885245_weatherct";

	$con = mysqli_connect($hostname, $username, $password, $database);
	mysqli_query($con, "SET NAMES 'utf8'");
 ?>