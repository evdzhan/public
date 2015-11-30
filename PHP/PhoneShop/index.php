<?php
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css2.css"/>

       <meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />	<title>Index of my Assignment page</title>
<?php 
if(!(isset($_SESSION['username']))) {
echo "<script type='text/javascript'>
<!--
function check() {

/* Regural expression for correct user name . Letters and numbers only. Must begin with letter. */
var uname= /^[a-zA-Z]+[0-9]*$/;

/* Check if the username is valid */
if(!(uname.test(login.elements['userid'].value))) 
{
alert('Bad user name. Must begin with letter. Only letters and numbers allowed.');
return false;
}
else {
return true;
}

}

--> </script>
"; } ?>
</head>

<body>

<div class ="container">
	     <div class="banner" > 
		 <img  src="banner.png" alt="wrong" title="Not impressed? Check our phones ..."  width="960" height="150"/>
		 </div>
		 <div class='menu'>

<a href='about.php'>About page
<img src='about.png' alt='About' width='32' height='32' title='About'/>
</a> 



</div>

		<div class="contenta">
		
				 <?php 
                              if(isset($_SESSION['username'])) {
                             echo "<p> Already logged as :<b>" ." ".$_SESSION['username']." " ."</b> <br/> 
               <a href='logout.php'>Logout</a>". " " . " or " . "  <a href='shop.php'> Visit the shop </a> </p>";

}
else {
echo "<form name='login' action='verifylogin.php' onsubmit='return check()' method='post'>
		
        <p> Username:
		<input type='text' name='userid'  size='25' maxlength='25' />
             <br/>
		<input type='submit' name='log' value='Log in' />
          </p>  
        </form>   ";
}
?>
    
				
       
      
        
        
        </div>
		
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
