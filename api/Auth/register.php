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
            $result = ["status" => false];
            echo json_encode($result);
        }
        else{
            $sql = "INSERT INTO `User` (`name`, `email`, `password`, `idAddress`) VALUES ('$name', '$email', '$pass', '$address')";
            if (mysqli_query($con, $sql)) {
                $result = ["status" => true];
                echo json_encode($result);
            } else {
                $result = ["status" => false];
                echo json_encode($result);
            }
            mysqli_close($con);
        }
    }
 ?>
 <!-- DONE -->