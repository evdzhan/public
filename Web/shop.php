<?php 
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
$message="Not logged in. <a href='index.php'> Login </a>"; // default message for non logged people
if(isset($_SESSION['username'])) {
$message=$_SESSION['message'];  // if person is logged we override the above message 
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />
<title>Welcome to PhoneShop</title>
<link rel="stylesheet" type="text/css" href="css2.css"/>
<?php 
if(isset($_SESSION['username'])) {
echo "<script type='text/javascript'>
<!--
function validate() {
/* Regular expresion for numbers */
var validNumber= /^[0-9]*$/;

/* Test to see if the criteria entered by the user is valid */ 
if(!(validNumber.test(tosort.elements['maximumPrice'].value)) || !(validNumber.test(tosort.elements['minimumPrice'].value))) {
alert('Bad criteria! Only numbers allowed.');
return false;
} else {
return true;
}

}
--> </script>
"; } ?>


</head>

<body>
<div class ="container">
               <div class="userInfo" >
                                 	<?php 
				       echo $message;
			                    ?>	                      	
                </div>
	     <div class="banner" > 
		 <img  src="banner.png" alt="wrong" title="Not impressed? Check our phones ..."  width="960" height="150"/>
		 </div>
		  

 <?php
           if(isset($_SESSION['username'])) {
echo "
<div class='menu'>

<a href='about.php'>About page
<img src='about.png' alt='About' width='32' height='32' title='About'/>
</a> 
&nbsp;  &nbsp;
<a href='basket.php'>Basket
<img src='cart.png' alt='Basket' width='32' height='32' title='Go to basket'/>
</a>

</div>

<div class='content'>";
 error_reporting(~E_ALL);

      $conn = pg_connect("host=db.dcs.aber.ac.uk port=5432
      dbname=teaching user=csguest password= ommited ") ;

    /*Test to see if the http request has GET values for maxium price and minimum price   */
if(isset($_GET['minimumPrice']) && isset($_GET['maximumPrice'])  ) {

$min = preg_replace("/[^\d.]/", "",$_GET['minimumPrice']); // leave nothing but digits
$max= preg_replace("/[^\d.]/", "", $_GET['maximumPrice']);   // leave nothing but digits

/* Both min and max are valid digits , Sort phones by those two numbers */
if(strlen($min) >0 && $min!="" && $max!="" && strlen($max)>0){

$res = pg_query ($conn, "select * from phones where price >= '".$min."' and price <='".$max."'"  );
}

/* Only minimum is correctly specified , sort only by minimum price*/
else if(strlen($min)>0 && $min!="") {
$min = preg_replace("/[^\d.]/", "",$_GET['minimumPrice']);
$res = pg_query ($conn, "select * from phones where price >= '".$min."'");
}

/* Only maximum is correctly specified , sort only by maximum price*/
else if(strlen($max)>0 && $max!="") {
$max = preg_replace("/[^\d.]/", "",$_GET['maximumPrice']);
$res = pg_query ($conn, "select * from phones where price <= '".$max."'");
}

/* Both fields are incorrect , select all phones  */
else {
$res = pg_query ($conn, "select * from phones " );
}
}

/* No GET values specified , select all phones  */
else {
$res = pg_query ($conn, "select * from phones " );
}
/* Check to see if the query has brought any result . If there is any , display it.  */
if(pg_num_rows($res) > 0) {
echo "<form name='addtobasket' action='add.php' method='post'>
<table border='1'>
<tr>
<th>MANUFACTURER </th>
<th>MODEL </th> 
<th>OS </th>
<th>PRICE </th>
<th>SELECT</th> </tr> <tr>";
while($q= pg_fetch_array($res))  {
echo "<td>" . $q['manufacturer'] . "</td>";
echo "<td>" . $q['model'] . "</td>";
echo "<td>" . $q['os'] . "</td>";
echo "<td>" . $q['price'] . "</td>
<td>
<input type='checkbox' name='phones[]' value=".$q['ref'].">
</td>
</tr>"; 
} 
echo "</table>\n
<input type='submit' name='add' value='Add selected items' />
</form> ";
}




/*No phones matching the criteria , or the DB has gone bad , display error message */
else {
echo "<p> No phones matching your criteria. Try different price range. </p> ";
}
echo "</div>
<div class='sort'> 
<form name='tosort' action='shop.php' onsubmit='return validate()' method='get'>
<p> Sort phones by price</p>
<p>From: <input type='text' name='minimumPrice' size='3' maxlength='4'></p>
<p>To: <input type='text' name='maximumPrice' size='3' maxlength='4'> </p>
<input type='submit' name='send' value='Sort' />
</form>
</div>";   

}
	?>
		
        
		
		<div class="line" >
		 <p> Information provided on this and other pages by me, Evdzhan Mustafa (enm3@aber.ac.uk), is
                       under my own personal responsibility and not that of Aberystwyth University. Similarly,
                        any opinions expressed are my own and are in no way to be taken as those of A.U. </p>
		<p>
    <a href="http://validator.w3.org/check?uri=referer"><img
      src="http://www.w3.org/Icons/valid-xhtml10" alt="Valid XHTML 1.0 Strict" height="31" width="88" /></a>

    <a href="http://jigsaw.w3.org/css-validator/check/referer">
        <img style="border:0;width:88px;height:31px"
            src="http://jigsaw.w3.org/css-validator/images/vcss"
            alt="Valid CSS!" />
    </a>

  </p>
		 </div>
     		 
	</div>	
	   
</body>

</html>
