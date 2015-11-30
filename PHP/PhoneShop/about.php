<?php 
session_save_path("customcookies");  // set the cookies directory 
session_start();                       // start session
$message="Not logged in. <a href='index.php'> Login </a>"; // default message for non logged people
if(isset($_SESSION['username'])) {
$message=$_SESSION['message'];  // if person is logged we override the above message 
}
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<link rel="stylesheet" type="text/css" href="css2.css"/>

       <meta http-equiv="Content-Type"
content="text/html; charset=utf-8" />	<title>About page</title>

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
		 
<div class='menu'>


<a href='shop.php'>Shop
<img src='shop.png' alt='Go to shop' width='32' height='32' title='Go to shop'/></a> 
&nbsp;  &nbsp;
<a href='basket.php'>Basket
<img src='cart.png' alt='basket' width='32' height='32' title='Go to basket'/></a>
</div>
		<div class="about">
	<h1>Declaration of originality </h1>

                        <p>I confirm that:
This submission is my own work, except where clearly indicated.
I understand that there are severe penalties for plagiarism and other unfair practice, which can lead to loss of marks or even the withholding of a degree.
I have read the sections on unfair practice in the Students' Examinations Handbook and the relevant sections of the current Student Handbook of the Department of Computer Science.
I understand and agree to abide by the University's regulations governing these issues. </p>

<h1>Design </h1>
<h2> Source Files Link should appear here if current month is 12 or less than 7. If it's not please contact me asap.<?php if(date(m)==12 || date(m)<7) {echo  "<a href='src' > Click </a>" ; } ?> </h2>


<h2> Sessions </h2>
 <p> I stared the assignment by trying to launch a proper session. I met few problems on the way.
One of the problems was that the filestore I was developing my site on, did not grant me the permission to save cookies for my sessions in the default directory. 
So I had to use the <code>session_save_path(); </code> function to fix this problem. Another thing I thought was annoying was the fact the when the user tries to reresh a page, that has been load with POST information sent to it, he was always prompted if he wants to resend the data. I thougth that is very tedious. That's why I did put all data manipulation in different php pages, namely : verifylogin.php , add.php , remove.php and logout.php.
 </p><h2> Database </h2> <p>
The next challenge was establishing a connection to the database we were provided access to. It was pretty straight forward. I was looking at Adrian's example php pages and did some of the error checking. Not all of them worked , so I just decided to remove all error output from the code with the function <code>error_reporting(~E_ALL); </code> . I was going to do further error checking and error reporting to the user, but I decided for simplicity to leave it like this. In real site database reading/writing is much more complicated, and I think what I have produced is enough for this assignment. 
</p> <h2> Layout/Basket  </h2> <p>
Now that  I had connection with the database I started doing the layout for my phone elements.
I used a HTML table to put all the information for the phones.I realize that I  could have done the layout of my phones much better. I should have used divs for each phone, and improve the looks alot. But again for the sake of the assignment I thought HTML table  was enough.  It took me some time to  understand how I am going to do the checkboxes and the way I want the user to be able to add phones to his virtual basket. I used checkboxes with common name for them all - phones[]. Then for the value of the check box I used the corresponding REF number. This way I could read which phones the user has selected on clicking the "add selected items" button. Pressing the button sends data to add.php, which reads each ref number from the $_POST['phones'] array. Then  I store the phones added to an array. The array holds only the REF numbers of each phone added so far. When the user goes to the basket.php page, I read this array of REF numbers , and for each of them I query the database for the phone with this corresponding REF num. The following piece of code does this : <code> $refquery = pg_query ($conn, "select * from phones where ref='".$ref ."'" ); . </code> .  Here I had to decide whether the user could have repeating phones added to the basket. I decided that if the users adds the same phone twice, I ignore that request, and keep the phone only once in the array.
</p><h2> Sorting by price </h2> <p>
After I had an active basket with items allowed to be added in it, I went back to the shop.php site and created the the sorting mechanism. I decided to use two text fields , one for minimum price (" From " ) and the second one for maximum price ("To" ). The user can enter values in them and press a button, and query the phones database based on the criteria. For the purpose of me understanding the GET and POST methods , I decided to use a GET method for this form. So the user can bookmark the link, if he decides to. The JavaScript validation was easy to do. But the GET method has one huge flaw,  the user can change the URI at his address bar and type some gibberish text. That's why I had to double proof my code , and put additional php checks in the shop.php file. I ended up creating a large piece of code with lots of checks. I would remove every character from the input the user types with <code> preg_replace() </code> method and I would check if any sensible data is left ( digits only). Based on the criteria from the user I change the query statement and the result matches the user's input. If the criteria does not match any phones in our database , I show a polite message to the user. 
</p> <h2> Removing from basket </h2> <p>
Having done the sorting I went back to the basket page. I had to implement a way of removing  items from the basket. I decided checkboxes were too ugly and instead I used buttons against each phone in the user's basket. This proved to be kinda hard. Since the value of my buttons had to be "Remove" , I could not put the REF number here , as I did in checkboxes in shop.php page. Instead I put the REf number in the name of the buttons. <code>  type=submit name='".$ref."' value='Remove' </code> . Pressing one of the buttons would lead the user sending POST data to remove.php . This file takes the name of the button pressed( which is the REF number of the phone to be removed) and removes it from the array of phones added by the user. Then unknowingly to the user the flow is taken back to basket.php , but this time the array holds one less phone. The one the user just clicked "Remove" at . I had to use the php API to learn some functions like <code> array_diff() </code>
which gives the difference between two arrays. 
</p><h2>Checking out - Validation </h2>  <p>
At the end I had to finalize the user experience and give him the opportunity to checkout. This was relatively easy. I used client side validation (JavaScript), to check the input from the user. I used regular expression to do so. Most of them  I created myself. Except the one for the UK post code. I did copy it from the following site <a href='http://regexlib.com/'> click </a> . Since the valid UK post code regexp is really hard to implement, I decided that it is ok to just copy it.
Once the user enters valid data in all of the fields he can checkout by clicking BUY. Which then logs the user out.
	</p>
<h2> Self evaluation </h2>
<p> 
I believe I satisfied the requirements as described in the Assignment document. However, I realize that there are many things that can be improved.  
I do believe that my site is still not very secure and there might be someways to inject/hack my code. For a real proffesional site there would be  lots of error and injection checks. This assignment helped me to improve in many areas. One of them was learning the basics of PHP. It is very good high-level language. I also had to use regular expressions, and only now I realize how powerful and useful tools they can be. A good web programmer should have a rich library of common reg expressions. <br/>
There are some things I am not happy about in my assignment.
For example, I tried to use onmousehover in places, to increase the overall effectness of my site, but I failed to do so. I also  had a quick look of some features we haven't studied yet - jQuery library and AJAX. But yet again  they seemed too complicated, and I could not waste that much time for extra additional stuff.
</p>
<h2>Testing</h2> 
<table border='1px' >
<tr> <th>Tested</th>
<th>Description </th>
<th>Expected behaviour</th>
<th>Pass/Fail</th></tr>
<tr><td>Index page, test username input. </td>
<td>Try to input various username strings. </td>
<td>Should not be able to login if the user name does not match the required criteria. </td>
<td> PASS </td>
</tr>

<tr><td>Shop page, test sorting input fields </td>
<td>Try to input various numbers strings or other random charachters. </td>
<td>Should not be able to sort by bad criteria.Only numbers must be allowed. </td>
<td> PASS </td>
</tr>
<tr><td>Shop page, try to modify URL link. </td>
<td>Try to input various numbers strings or other random charachters in the URL link where a GET variable is located. </td>
<td>Should not be able to sort by bad criteria,even if the URL link is modified.PHP check methods must not allow this. </td>
<td> PASS </td>
</tr>

<tr><td>Shop page, try adding phones. </td>
<td>Select several phones, press button to add them to the basket. </td>
<td>Should be able to add phones to the basket . </td>
<td> PASS </td>
</tr>

<tr><td>Basket page, try removing phones. </td>
<td>See if the user can remove phones from the basket, by pressing a button. </td>
<td>Should be able to remove phones from the basket . </td>
<td> PASS </td>
</tr>
<tr><td>Every page, try logging out. </td>
<td>See if the user can successfuly log out. </td>
<td>Should be able to log out at any time. </td>
<td> PASS </td>
</tr>
<tr><td>Checkout page, try checking out. </td>
<td>See if you can checkout with bad data in the fields. Test each field. </td>
<td>Should not be able to checkout if the user enters bad data.Check out only if the data is valid. </td>
<td> PASS </td>
</tr>
<tr><td>Every page,where there is input field. </td>
<td>See if you can inject HTML tags, try typing huge amound of charaters. </td>
<td>HTML tags should be trimmed.Every input field should have limit, based on how much information is expected to be entered. </td>
<td> PASS </td>
</tr>
<tr><td>Every page,validate HTML/CSS. </td>
<td>See if my mark up is XHTML 1.0 strict. </td>
<td>All pages must validate. </td>
<td> PASS </td>
</tr>



</table>




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
