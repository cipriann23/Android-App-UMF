<?php 
require "conn.php";

#$category = strtolower($_GET["category"]);
#echo $category;
#$category = "metabolism";
#$mysql_qry = "SELECT * FROM questions where subcategory=\"".$category."\" ORDER BY RAND() LIMIT 1";

$mysql_qry = "SELECT * FROM questions ORDER BY RAND() LIMIT 1";

$result = mysqli_query($conn, $mysql_qry);

if(mysqli_num_rows($result)>0){
	//echo "Question selected";
}
$row = mysqli_fetch_row($result);
	echo base64_encode($row[4])."      ".base64_encode($row[5])."      ".base64_encode($row[6])."      ".base64_encode($row[7])."      ".base64_encode($row[8])."      ".base64_encode($row[9])."      ".base64_encode($row[10])."      ".base64_encode( $row[11] );

#echo '<img src="data:image/jpeg;base64,'.base64_encode( $row[9] ).'"/>';
#  54 biologie
#  36 chimie fizica
?>