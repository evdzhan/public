<?php
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
if(isset($_SESSION['username'])){
if($_SERVER['REQUEST_METHOD'] == 'POST') {
if(isset($_POST)) {
$key=array_search("Remove",$_POST);
$_SESSION['phonesAdded']=array_diff($_SESSION['phonesAdded'],array($key));
header("location:basket.php");
exit;
}}}

else {

header("location:index.php");
exit;
}
?>
