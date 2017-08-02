<?php 
require "conn.php";

$mysql_qry = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";
$result = mysqli_query($conn, $mysql_qry);

if(mysqli_num_rows($result)>0){
	echo "Question selected";
}
$row = mysqli_fetch_row($result);
	echo "<br>";
	echo $row[0]."      ".$row[1]."       ".$row[2]."      ".$row[3];


?>