<?php
    require "connect.php";
    $name = $row["editText_name"];
    $query = "INSERT INTO `Address` (`name`) VALUES ('$name')";
    if($name != '') {
        $result = mysqli_query($con, $query);
        if ($result) {
            echo json_encode($result);
            mysqli_close($con);
        }
        else{
            echo mysqli_error($con);
        }
    }
?>