<?php 
	require "connect.php";
	$query_user = "SELECT * FROM `User`";
    $data_user = mysqli_query($con, $query_user);
    $query_dress = "SELECT * FROM `Address`";
    $data_dress = mysqli_query($con, $query_dress);
    while($row = mysqli_fetch_assoc($data_user)){
        $email = $_POST["email"];
        $pass = $_POST["password"];
        if($email == $row["email"] && $pass == $row["password"]){
            while($row1 = mysqli_fetch_assoc($data_dress)){
                $address = $_POST["address"];
                if($address == $row1["name"]){
                    echo json_encode([
                        "status" => false
                    ]);
                }
                else{
                    $sql = "INSERT INTO `Address` (`name`) VALUES ('$address')";
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
        }
        else{            
            echo json_encode([
                "status" => false
            ]);
        }
    }
 ?>
 <!-- DONE -->