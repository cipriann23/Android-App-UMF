<?php 
require "conn.php";

$mysql_qry = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";
$result = mysqli_query($conn, $mysql_qry);

if(mysqli_num_rows($result)>0){
	//echo "Question selected";
}
$row = mysqli_fetch_row($result);
	echo $row[3]."      ".$row[4]."      ".$row[5]."      ".$row[6]."      ".$row[7]."      ".$row[8]."      ".base64_encode( $row[9] );
	
#echo '<img src="data:image/jpeg;base64,'.base64_encode( $row[9] ).'"/>';

?>