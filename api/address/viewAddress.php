<?php 
	require "connect.php";
	class Address {
		
		function Address($idAddress, $name)
		{
			$this->idAddress  = $idAddress;
			$this->name  = $name;
		}
	}
	$query = "SELECT * FROM `Address`";
	$data = mysqli_query($con, $query);
	$arrayAddress = array();
	while ($row = mysqli_fetch_assoc($data)) {
	 	array_push($arrayAddress , new Address(
	 		$row['idAddress'],
	 		$row['name'])
		);
	}
	echo json_encode($arrayAddress);
 ?>