<?php 
	require "connect.php";
	$query_user = "SELECT * FROM `User`";
    $data_user = mysqli_query($con, $query_user);
    while($row = mysqli_fetch_assoc($data_user)){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        if($email == $row["email"] && $pass == $row["password"]){
			class Address {
		
				function Address($idAddress, $name)
				{
					$this->id  = $idAddress;
					$this->name  = $name;
				}
			}
			$query_dress = "SELECT * FROM `Address`";
    		$data_dress = mysqli_query($con, $query_dress);
			$arrayAddress = array();
            while($row1 = mysqli_fetch_assoc($data_dress)){
                array_push($arrayAddress , new Address(
					$row1['id'],
					$row1['name'])
			  	);
            }
			echo json_encode($arrayAddress);
        }
        else{            
            $result = ["status" => false];
            echo json_encode($result);
        }
    }
 ?>
 <!-- DONE -->