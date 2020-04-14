<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$orderID = $_REQUEST['orderID'];
$pizzaCategory = $_REQUEST['pizzaCategory'];
$pizzaSize = $_REQUEST['pizzaSize'];
$quantity = $_REQUEST['quantity'];
$price = $_REQUEST['price'];

$qry = "insert into tblorderdetail values(0,'$orderID','$pizzaCategory','$pizzaSize','$quantity','$price')";
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