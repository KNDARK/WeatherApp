<?php 
	require "connect.php";
	$query = "SELECT * FROM `User`";
	$data = mysqli_query($con, $query);
    while($row = mysqli_fetch_assoc($data)){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        if($email == $row["email"] && $pass == $row["password"]){
            $result = ["Email" => $user, "Password" => $pass, "status" => true];
            echo json_encode($result);
        }
        else{            
            $result = ["Email" => "", "Password" => "", "status" => false];
            echo json_encode($result);
        }
    }
 ?> 
 <!-- DONE -->