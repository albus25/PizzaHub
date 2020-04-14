<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$tableNo = $_REQUEST['tableNo'];
$customerName = $_REQUEST['customerName'];
$mobileNo = $_REQUEST['mobileNo'];
$orderdatetime = $_REQUEST['orderdatetime'];

$qry = "insert into tblordermaster values(0,'$tableNo','$customerName','$mobileNo','$orderdatetime')";
$res = mysqli_query($con,$qry);

if($res)
{
	$response["success"] = 1;
	echo json_encode($response);
}
else
{
	$response["success"] = 0;
	echo json_encode($response);
}
?>