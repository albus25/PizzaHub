<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$qry = "select customerName from tblordermaster";
$res = mysqli_query($con,$qry);

$result['customerName'] = array();
while($row = mysqli_fetch_array($res))
{
	$customerName = array();
	$customerName['customerName'] = $row[0];
	array_push($result['customerName'],$customerName);
}
echo json_encode($result);
?>