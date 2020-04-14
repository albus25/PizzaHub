<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$customerName = $_REQUEST['customerName'];

$qry = "select orderID from tblordermaster where customerName = '$customerName'";
$res = mysqli_query($con,$qry);

$result['orderID'] = array();

while($row = mysqli_fetch_array($res))
{
	$orderID = array();
	$orderID['orderID'] = $row[0];

	array_push($result['orderID'],$orderID);
}
echo json_encode($result);
?>