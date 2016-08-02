<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
   
<%
if(session.getAttribute("email") !=null && session.getAttribute("email") != ""){
	
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

<title>Login</title>
<!--
<link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet">
-->

<link href="assets/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="assets/stylesheets/style.css" rel="stylesheet">
<link href="assets/stylesheets/new-login.css" rel="stylesheet">
<link href="assets/stylesheets/pc-style.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300' rel='stylesheet' type='text/css'>
<script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/hidden-nav-script.js" type="text/javascript" ></script>
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
      	<li><a href="signin.jsp">Sign in</a></li>
      	<li class="pc_nav_ul_li_button" ><a href="signup.jsp" class="pc_nav_ul_li_button_hidden" style="display:block" >Sign up Today</a></li>
      </ul>
  </nav>
<section class="new-login-sec">

 
<div class="row">
<div class="login-container">

  <div class="row" style="">
 <div class="columns">
    <div class="feature-title">
      <p style="font-weight:100" class="login_para">LOGIN</p>
      <span class="feature-underline" style="border-bottom:1px solid #000; "></span>
    </div>
  </div>
</div>
  <%  
   String AC=null;
   if(request.getParameter("ac") != null){
   AC=request.getParameter("ac");
  

   
   %>
<div class='alert alert-success fade in'>
				  <a href='#' class='close' data-dismiss='alert' aria-label='close'>&times;</a>
				  <strong>Success!</strong> Your Account Successfully Created.
				</div>
				
				
	<%
   }
	%>
<p style="font-size:14px;color:#333;margin-bottom:20px;">Please log in to get access.</p>

<form method="post" id="login">
       <div class="login-form-outer marginbottom-30px"> 
       <input class="login-form" id="email" name="email" placeholder="Email" type="email" autocomplete="off" >
       </div>
      <div class="login-form-outer"> 
      <input class="login-form" id="password" name="password" placeholder="Password" type="password">
      </div>
      		
     <p style="font-size:14px;color:#F00;margin-top:10px;" id="login-message"></p>

      <p class="forget-pass new-trigger-overlay" > Forgot your password?</p>
      
      <input class="login-button" style="display:inline-block; margin:0px 20px 20px 0; "  name="Submit"  value="Login" type="submit">
      <a href="signup.jsp"><div class="login-button" style="display:inline-block;margin-bottom:10px;">Create Account</div></a>

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
    
<!--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.3/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js" type="text/javascript" ></script>
!-->
<script src="assets/bootstrap/dist/js/bootstrap.min.js"></script>
<script>
$('#login').submit(function(e){

	$('#login-message').html("<div class='loading'></div>");
	$.ajax({
		type :'POST',
               // contentType: 'application/json',
		url : '/rest/signing/signin',
                 //dataType: "JsonObject",
                 data : toJson(),
		success : function(data){
	//var details =JSON.parse(data);
       if(data ==="true")
        { 
            $('#login-message').html("<p style='font-size:0.9rem;"
                             + "color:green;font-weight:bold;'>"
                             + "Login Successfully.</p>"
                            );

            var millisecondsToWait = 1000;          
            setTimeout(function() {location.assign('dashboard.jsp');}, millisecondsToWait);
        }
        else
        {     $('#login-message').html(data);    }             
		}
	});
    
	e.preventDefault();
	
});

function toJson()
{
    var email=$('#email').val();
	var password=$('#password').val();
      return  {"email":email,"password":password};
   //return JSON.stringify({"email":email,"password":password}); 
}
</script>

<%
}
%>
