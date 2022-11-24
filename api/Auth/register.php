<?php 
	require "connect.php";
	$query = "SELECT * FROM `User`";
	$data = mysqli_query($con, $query);
    while($row = mysqli_fetch_assoc($data)){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        $name = $_POST["name"];
        $address = $_POST["idAddress"];
        if($email == $row["email"]){
            echo json_encode([
                "status" => false
            ]);
        }
        else{
            $sql = "INSERT INTO `User` (`name`, `email`, `password`, `idAddress`) VALUES ('$name', '$email', '$pass', '$address')";
            if (mysqli_query($con, $sql)) {
                echo json_encode([
                    "status" => true
                ]);
            } else {
                echo json_encode([
                    "status" => false
                ]);
            }
            mysqli_close($con);
        }
    }
 ?>
 <!-- DONE -->