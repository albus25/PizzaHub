<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$customerName = $_REQUEST['customerName'];

$qry = "select om.*,od.* from tblordermaster om,tblorderdetail od where om.orderID = od.orderID and om.customerName = '$customerName'";

$res = mysqli_query($con,$qry);
$result['bill'] = array();

while($row = mysqli_fetch_array($res))
{
	$bill = array();
	$bill['orderID'] = $row[0];
	$bill['customerName'] = $row[2];
	$bill['mobileNo'] = $row[3];
	$bill['orderdatetime'] = $row[4];
	$bill['pizzaCategory'] = $row[7];
	$bill['pizzaSize'] = $row[8];
	$bill['quantity'] = $row[9];
	$bill['price'] = $row[10];

	array_push($result['bill'],$bill);
}
echo json_encode($result);
?>