<?php
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
if(isset($_SESSION['username']))
                               {


 if($_SERVER['REQUEST_METHOD'] == 'POST'){
if(isset($_POST['phones'])) {

if(!(isset($_SESSION['phonesAdded']))) {
$_SESSION['phonesAdded']=array();
$_SESSION['phonesAdded']=$_POST['phones'];
}
else {
foreach($_POST['phones'] as $value) {
if(!(in_array($value,$_SESSION['phonesAdded']))) {
$_SESSION['phonesAdded'][]=$value;
}
}
}
}

} 


header("location:shop.php");
exit;
} else {
header("location:index.php");
exit;
}



?>
