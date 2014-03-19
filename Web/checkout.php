<?php
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
$message="Not logged in. <a href='index.php'> Login </a>"; // default message for non logged people
if(isset($_SESSION['username'])) {
$message=$_SESSION['message'];    // if person is logged we override the above message 
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
<meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />
<title>Checkout</title>

<?php 
if(isset($_SESSION['username'])) {
echo "<script type='text/javascript'>
<!--
function check() {

/* A regular expression for a email */
var mail= /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})$/; 

/* A regular expression for a credit card - 16 digits. Numbers only, exactly 16. */
var creditcard=/^([0-9]{16})$/;

/* A regular expression for a street name  .Only letters, digits and spaces */
var streetname=/^([a-zA-Z0-9(\s)]){2,}$/;  

/* A regular expression for a city/town . Letters only. 2 letters minimum. */

var city=/^[a-zA-Z]{2,}$/;

/* A regular expression for Valid UK post codes */
var postCode=/^([A-PR-UWYZ0-9][A-HK-Y0-9][AEHMNPRTVXY0-9]?[ABEHMNPRVWXY0-9]? {1,2}[0-9][ABD-HJLN-UW-Z]{2}|GIR 0AA)$/;

/* Test if the email is valid */
if(!(mail.test(pay.elements['email'].value))) {
alert('Bad email');
return false;

}
/* Test if the credit card is valid */
else if(!(creditcard.test(pay.elements['creditcard'].value))) {
alert('Bad CreditCard number. Exactly 16 digits!');
return false;
}

/* Test if the street name is valid */
else if(!(streetname.test(pay.elements['street'].value))) {
alert('Bad street name. Numbers and/or letters only!');
return false;
}
/* Test if the city/town  is valid */
else if(!(city.test(pay.elements['city'].value))) {
alert('Bad city/town name. Letters only !');
return false;
}
/* Test if the postcode is valid */
else if(!(postCode.test(pay.elements['postcode'].value))) {
alert('Not a valid UK post code !');
return false;
}

else {
alert('Success ! Thanks for using my site...');
return true;
}

}

--> </script>
"; } ?>

<link rel="stylesheet" type="text/css" href="css2.css"/>
</head>
<body>
<div class ="container">
<div class='userInfo'>

<?php 
  echo $message;
?>
</div>
<div class='banner'>
 <img  src="banner.png" alt="banner" title="Not impressed? Check our phones ..."  width="960" height="150"/>
</div>
 <?php
           if(isset($_SESSION['username'])) {


echo "<div class='menu'>

<a href=about.php>About page
<img src='about.png' alt='About' width='32' height='32' title='About'></a> 
&nbsp  &nbsp
<a href='shop.php'>Shop
<img src='shop.png' alt='Go to shop' width='32' height='32' title='Go to shop'></a> 
&nbsp  &nbsp
<a href='basket.php'>Basket
<img src='cart.png' alt='basket' width='32' height='32' title='Go to basket'></a>
</div>";

if(isset($_SESSION['phonesAdded']) && count($_SESSION['phonesAdded'])>0 ) {
echo "<div class='contenta'>
<p> This is not a real web shop;
it is created as part of my university coursework. Please do not attempt to buy anything
from this site, nor enter your real card details.
</p><div id='forms'>
<form name='pay' action='logout.php' onsubmit='return check()' method='post'>
<label>Email:</label> 
<input type='text' size='20' maxlength='20' name='email'> 
<label>Credit card number:</label> 
<input type='text' size='20' maxlength='16' name='creditcard'>
<label>Street name: </label>
<input type='text' size='20' maxlength='20'name='street'>   
<label> City/Town : </label>
<input type='text' size='20' maxlength='20' name='city'>  
<label>Post code : </label>
<input type='text' size='20' maxlength='10' name='postcode'> 

</div>
</br> <p>Your phones will cost you : ". $_SESSION['total']."
<input type='submit' name='send' value='BUY' </input> </p> </form>
</div>

";
	
}
else {
echo "<div class='contenta'><p> Nothing selected , your basket is empty ! </p></div>";
}
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
