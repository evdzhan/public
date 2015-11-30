<?php
session_save_path("customcookies");  // set the cookies directory 
session_start();   
if(isset($_SESSION['username'])) {                 
session_destroy();  // end session. log the user out.
header( "Refresh:5; url=index.php");
}  
else {
header("location:index.php");
exit;
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css2.css"/>

       <meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />	<title>Index of my Assignment page</title>

</head>

<body>

<div class ="container">
	     <div class="banner" > 
		 <img  src="banner.png" alt="wrong" title="Not impressed? Check our phones ..."  width="960" height="150"/>
		 </div>
		 

		<div class="contenta">
	You have been logged out. Redirecting to index page in a moment...
<a href='index.php' > Login again. </a>
	</div>
		
		<div class="line" >
		 <p> Information provided on this and other pages by me, Evdzhan Mustafa (enm3@aber.ac.uk), is
                       under my own personal responsibility and not that of Aberystwyth University. Similarly,
                        any opinions expressed are my own and are in no way to be taken as those of A.U. </p>
		
	 </div>
     		 
	</div>		   
		   
</body>
</html>
