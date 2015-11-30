<?php
session_save_path("customcookies");          // set the cookies directory 
session_start();                              // start session

if($_SERVER['REQUEST_METHOD'] == 'POST') { // check if this page is accessed after with post post

if(isset($_POST['userid'])) {      
$uid=strip_tags($_POST['userid']);  // not really needed since we dont have a db for users
$uid=pg_escape_string($uid);   //  just to remind my self in the future that this is needed.
               
$ok=preg_match("/^[a-zA-Z]+[0-9]*$/",$uid); // just in case , java script shouldnt allow bad input
if(strlen($uid) > 0 && $ok===1)  {
$_SESSION['username']=$uid;
$_SESSION['message']="<p>Logged as:<b>"." ". $_SESSION['username'] ." " . "|" . " ". "  </b><a href='logout.php'>Logout.</a></p>";
header("location:shop.php");
exit;
 } else {

header("location:index.php");
exit;

}

}

}else {

header("location:index.php");
exit;
}








?>
