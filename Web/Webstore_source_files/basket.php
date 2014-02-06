<?php 
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
$message="Not logged in. <a href='index.php'> Login </a>";  // default message for non logged people
if(isset($_SESSION['username'])) {
$message=$_SESSION['message'];  // if person is logged we override the above message 
}
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">

<head>
<meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />
<title>Basket</title>
<link rel="stylesheet" type="text/css" href="css2.css"/>
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


echo "<div class='menu'>


<a href=about.php> About page
<img src='about.png' alt='About' width='32' height='32' title='About'></a> 
&nbsp  &nbsp
<a href='shop.php'>Shop
<img src='shop.png' alt='Go to shop' width='32' height='32' title='Go to shop'></a> 
";
echo "</div><div class='contenta'>";
 /* Check to see if any  phones are added to the basket */
     if(isset($_SESSION['phonesAdded']) && count($_SESSION['phonesAdded'])>0 ) {
       $conn = pg_connect("host=db.dcs.aber.ac.uk port=5432
      dbname=teaching user=csguest password=r3p41r3d");
echo "<h3> ITEMS CURRENTLY IN THE BASKET </h3>
<table border='1px'>
<tr><th>MANUFACTURER </th>
<th>MODEL </th> 
<th>PRICE </th>
<th>Remove</th>
 </tr>";
/*Innitialize the totla price of the phones */
$totalPrice=0;

/* Go through all the ref numbers added to the array , get all the database data corresponding to those ref numbers.*/
foreach($_SESSION['phonesAdded'] as $ref) {
$refquery = pg_query ($conn, "select * from phones where ref='".$ref ."'" );
$a=pg_fetch_array($refquery);

/* add up the sum of all prices  */
$totalPrice+=$a['price'];
echo 
"
<form name='remove' action='remove.php' method='post'>
<tr>
<td>". $a['manufacturer']." </td>
<td>". $a['model']."</td>
<td>". $a['price']."</td>
<td><input type='submit' name='".$ref."' value='Remove'></td></tr></form>  "
;

}
echo "</table> </br> <p><b> Total Price : ".$totalPrice ."</b> </br>
<a href='checkout.php'>Checkout<img src='checkout.png' alt='Click to checkout' width='32' height='32' title='Checkout'></a></p> ";
$_SESSION['total']=$totalPrice;
}
/*No phones added to the basket yet or the user has removed all of the phones , display basket empty message */
else {
echo "<p> Basket empty.</p>";
}
echo "</div>";
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
