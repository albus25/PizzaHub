<?php

$response = array();
$con = mysqli_connect("localhost","root","toor","pizzahubdb") or die(mysqli_error());

$qry = "select om.*,od.* from tblordermaster om,tblorderdetail od where om.orderID = od.orderID";

$res = mysqli_query($con,$qry);
$result['orders'] = array();

while($row = mysqli_fetch_array($res))
{
	$orders = array();
	$orders['orderID'] = $row[0];
	$orders['tableNo'] = $row[1];
	$orders['customerName'] = $row[2];
	$orders['pizzaCategory'] = $row[7];
	$orders['pizzaSize'] = $row[8];
	$orders['quantity'] = $row[9];
	$orders['price'] = $row[10];
	$orders['status'] = $row[11];

	array_push($result['orders'],$orders);
}
echo json_encode($result);
?>