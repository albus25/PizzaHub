<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$orderID = $_REQUEST['orderID'];

$qry = "update tblorderdetail set status = 0 where orderID = '$orderID'";
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