<?php 
	require "connect.php";
	$query = "SELECT * FROM `User`";
	$data = mysqli_query($con, $query);
    while($row = mysqli_fetch_assoc($data)){
        $user = $row["editText_email"];
        $pass = $row["editText_password"];
        if($user == $row["email"] && $pass == $row["password"]){
            echo json_encode(true);
        }
        else{
            echo json_encode(false);
        }
    }
 ?>