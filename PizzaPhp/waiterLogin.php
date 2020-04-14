<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$mobileNo = $_REQUEST['mobileNo'];
$password = $_REQUEST['password'];

$qry = "select * from tblwaiter where mobileNo = '$mobileNo' and password = '$password'";
$res = mysqli_query($con,$qry);

if($res)
{
	$response['success'] = 1;
	echo json_encode($response);
}
else
{
	$response['success'] = 0;
	echo json_encode($response);
}
?>