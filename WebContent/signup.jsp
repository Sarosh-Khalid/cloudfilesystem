<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
 <%
if(session.getAttribute("email") !=null && session.getAttribute("email") != "" ){
	
%>
<script>
location.assign('index.jsp');
</script>
<%
}
else{

%>   
    
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta charset="UTF-8">
<link rel="shortcut icon" href="assets/stylesheets/images/pc-title-logo3.png" type="image/png">
<title>Signup</title>
<link href="assets/stylesheets/style.css" rel="stylesheet">
<link href="assets/stylesheets/new-login.css" rel="stylesheet">
<link href="assets/stylesheets/pc-style.css" rel="stylesheet">

<link href='https://fonts.googleapis.com/css?family=Open+Sans:300' rel='stylesheet' type='text/css'>


</head>

<body>
    
    <div id="site-logo" class="menu-button push">
  
  <div class="logo-black fade-border">
    <div class="logo-inner fade-border logo-inner-1"></div>
        <div class="logo-inner fade-border logo-inner-2"></div>
        <div class="logo-inner fade-border logo-inner-3 "></div>
       </div>
  </div>
  

  
  
  
   <header class="pc_header">
       <a href="index.jsp" class="pc_header_logo_a"><div class="pc_header_logo"></div></a>
            <nav class="pc_nav" >
            	<ul class="pc_nav_ul">
                	<a href="index.jsp"><li class="pc_nav_ul_li">Home</li></a>
                	<a href="login.jsp"><li class="pc_nav_ul_li">Sign in</li></a>
                	<a href="signup.jsp"><li class="pc_nav_ul_li pc_nav_ul_li_button">Sign up Today</li></a>
                </ul>
            
            </nav>
    </header>
  
  
  <nav class="pc_aside_nav_hidden">
      <ul>
      	<li><a href="index.jsp">Home</a></li>
      	<li><a href="login.jsp">Sign in</a></li>
      	<li class="pc_nav_ul_li_button" ><a href="signup.jsp" class="pc_nav_ul_li_button_hidden"  style="display:block" >Sign up Today</a></li>
      </ul>
  </nav>

<section class="new-login-sec">

 
<div class="row">
<div class="signup-container">

  <div class="row" style="">
 <div class="columns">
    <div class="feature-title">
      <p style="font-weight:100" class="login_para">Create An Account</p>
      <span class="feature-underline" style="border-bottom:1px solid #000; "></span>
    </div>
  </div>
</div>

<p style="font-size:14px;color:#333;">Please provide your valid information.</p>
<br><br>
<form method="post" id="signup">
	<div class="login-form-outer">
    	 <input class="login-form" name="fullname" id="fullname" placeholder="Fullname" type="text"  autocomplete="off" oninput="validateFullname()" >
     </div>
     
     <p id="name_para" style="font-size:0.9rem;color:#F00;position:absolute;"></p>
     
	 <br> <br>
     
     <div class="login-form-outer "> 
     	<input class="login-form" name="email" id="email" placeholder="Email"   type="text"  autocomplete="off" onblur="validateEmail()">
     </div>
     
     <p id="email_para" style="font-size:0.9rem;color:#F00;position:absolute;"></p>     
	 <br> <br>
     
     <div class="login-form-outer"> 
     	<input class="login-form" name="password" id="password" placeholder="Password"  type="password" oninput="validatePassword()" >
     </div>
      
     <p style="font-size:0.9rem;color:#333;">Password should have at least 8-charaters.</p>
     
     <p id="pass_para" style="font-size:0.9rem;color:#F00;"></p>

     <p id="signup-message" style="font-size:0.9rem;color:#F00;font-weight:bold;"></p>
      
      
      <input class="login-button" id="submit-button" style="float:left;margin-top:30px; " name="submit" value="Sign up"  type="submit">

</form>


</div>


</div>




</section>






<footer class="pc_footer">
    <ul class="pc_footer_ul">
        <li class="pc_footer_ul_li"><a href="">About Us</a></li>
        <li class="pc_footer_ul_li"><a href="">Contact Us</a></li>
        <li class="pc_footer_ul_li"><a href="">Terms & Condition</a></li>
    </ul>

</footer>

</body>

</html>
	<script src="assets/js/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.3/jquery.min.js"></script>


<script src="assets/js/hidden-nav-script.js" type="text/javascript" ></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/aes.js"></script>
<script type="text/javascript">
function validateFullname(){
	var fullname=$('#fullname').val();
	$.ajax({
		type:'POST',
		url:'/Cloudfile/rest/signing/ValidateFullName',
		data:{"fullname":fullname},
		success: function(data){
			$('#name_para').html(data);
		}	
			
	});
}

function validateEmail(){
	
	var email=$('#email').val();
	$.ajax({
		type:'POST',
		url:'/Cloudfile/rest/signing/ValidateEmail',
		data:{"email":email},
		success: function(data){
			$('#email_para').html(data);
		}
	});
}

function validatePassword(){
	
	var password=$('#password').val();
	$.ajax({
		type:'POST',
		url:'/Cloudfile/rest/signing/ValidatePassword',
		data:{"password":password},
		success:function(data){
			$('#pass_para').html(data);
		}
	});
}

$('#signup').submit(function(e){

	$('#signup-message').html("<div class='loading'></div>");
	$.ajax({
		type :'POST',
		url :'/Cloudfile/rest/signing/signup',
		data :toJson(),
		success : function(data){
			if(parseInt(data)){
				var encrypted = CryptoJS.AES.encrypt("Acount Registerd", "Pocket Cloud Encryption");
				location.assign('login.jsp?ac='+encrypted);
			}
			else{
				$('#name_para').html(null); 
				$('#email_para').html(null);
				$('#pass_para').html(null);
				$('#signup-message').html(data);
			}
		}
	});
	e.preventDefault();
	
});
function toJson()
{
    var fullname=$('#fullname').val();
	var email=$('#email').val();
	var password=$('#password').val();
      return  {"fullname":fullname,"email":email,"password":password} ;
   //return JSON.stringify({"email":email,"password":password}); 
}
</script>

<%
}
%>

