<?php 
	require "connect.php";
	$query = "SELECT * FROM `User`";
	$data = mysqli_query($con, $query);
    while($row = mysqli_fetch_assoc($data)){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        if($email == $row["email"] && $pass == $row["password"]){
            echo json_encode([
                "Email" => $user, 
                "Password" => $pass, 
                "status" => true
            ]);
        }
        else{            
            echo json_encode([
                "Email" => "", 
                "Password" => "", 
                "status" => false
            ]);
        }
    }
 ?> 
 <!-- DONE -->