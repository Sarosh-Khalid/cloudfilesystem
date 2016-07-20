<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>


<!DOCTYPE html >
<html>
<head>
<meta charset="UTF-8">
<link rel="shortcut icon" href="assets/stylesheets/images/pc-title-logo3.png" type="image/png">

<title>Pocket Cloud</title>
<link href="assets/stylesheets/style.css" rel="stylesheet">
<link href="assets/stylesheets/new-login.css" rel="stylesheet">
<link href="assets/stylesheets/pc-style.css" rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Open+Sans:300' rel='stylesheet' type='text/css'></head>

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
                	<%
                		if(session.getAttribute("email") != null && session.getAttribute("email") != "")
                		{
                	
                			String name=(String)session.getAttribute("fullname");
                		
                	%>
                	<a href="logout.jsp"><li class="pc_nav_ul_li">Logout</li></a>
                	<a href="javascript:void(0)"><li class="pc_nav_ul_li"><%=name+" " %><img alt=">" src="assets/stylesheets/new-images/arrow-down.png" height=11px width=10px;></li></a>

                	<%
                		}
                		else{
                  	%>
                	
                	
                	<a href="login.jsp"><li class="pc_nav_ul_li">Sign in</li></a>
                	<a href="signup.jsp"><li class="pc_nav_ul_li pc_nav_ul_li_button">Sign up Today</li></a>
                	<%
                	}
                	%>
                </ul>
            
            </nav>
    </header>
  
  
  <nav class="pc_aside_nav_hidden">
      <ul>
      	<li><a href="index.jsp">Home</a></li>
        	<%
				  if(session.getAttribute("email") != null && session.getAttribute("email") != "")
				  {
			  
					  String name=(String)session.getAttribute("fullname");
				  
			  %>
			  <li><a href="logout.jsp">Logout</a></li>
			  <li><a href="javascript:void(0)"><%=name+" " %><img alt=">" src="assets/stylesheets/new-images/arrow-down.png" height=15px width=14px;></a>
			  	
			  </li>
			  
			  <%
				}
				else{
			%>
      	<li><a href="login.jsp">Sign in</a></li>
      	<li class="pc_nav_ul_li_button" ><a href="signup.jsp" class="pc_nav_ul_li_button_hidden" style="display:block" >Sign up Today</a></li>
              <%
                }
               %>
      </ul>
  </nav>


 <section class="section-hero parallax section-hero-back" style="background-image:url(assets/stylesheets/new-images/cloudcomputing4.png);background-size:cover;background-position:330px 85px;
 ">
  <div class="section-hero-child">
    <div itemprop="video" itemscope itemtype="http://schema.org/VideoObject">
	<meta itemprop="name" content=" A Place for Projects">
	<meta itemprop="keywords" content="innovation center, projects, design, engineering, hardware, portfolio, making, makers, ">
	<meta itemprop="thumbnail" content="">

		<div class="hero-video" >
	     <!--<video preload="auto" width='100%' poster="assets\stylesheets\images\hero-image.png" autoplay loop>
	      <source src="assets/stylesheets/videos/hero-video.mp4" type="video/mp4" itemprop="contentUrl">
          <source src="assets/stylesheets/videos/hero-video2.webm" type="video/webm" itemprop="contentUrl">
 
	    </video>
  -->
	    
	  </div> 
    

</div>

<div class="hero-text">
	<div class="row">
		<div class="columns small-12 text-center " >
		  <h4 style="color:#000;" class="slide-left" data-plugin-options='{"speed":1200, "distance":400}'><span style="color:#0079b4;font-weight:900;">Pocket</span> Cloud</h4>
		  <h1 style="color:#000;" class="slide-right" data-plugin-options='{"speed":1400, "distance":400}'>A Cloud in Your <span style="color:#0079b4">Pocket</span> </h1>
		</div>
		<div class="columns small-12 medium-10 large-6 medium-centered end text-center  " >
		  <p style="color:#000;" class="slide-left" data-plugin-options='{"speed":1600, "distance":400}'>Fast, <span style="color:#0079b4;font-weight:600;">Easy</span> and Secure <span style="color:#0079b4;font-weight:600;">Access</span> To Your Files.</p>
          <br><br>
		  <a class="button btn btn-1c border-white fade-border slide-bottom" data-plugin-options='{"distance":200}' href="signup.jsp" >START FREE</a>
		</div>
	</div>
</div>


</div>
  </section>
 <section class="section-trending" style="background-color:#f9f9f9;min-height:400px;margin-bottom:30px;" >
    <div class="row">

  <div class="small-12 columns" style="margin-bottom:3em;">
    <div class="trending-title slide-left" style="text-align: center;">
      <h2 style="color:#000">Pocket Cloud Benefits</h2>
      <span class="trending-underline-black" ></span>
    </div>
  </div>

    <div class="columns small-12 medium-6 large-3 slide-left" style="float:left;margin-bottom:30px;" itemscope itemtype="http://schema.org/CreativeWork">
  <div class="card card-mini" style="background-color:#f9f9f9;" >


<!-- Card Image -->
   
        <meta itemprop="image" content="">
        <div class="card-image " style="background-color:#f9f9f9;background-image:url(assets/stylesheets/new-images/speedometer.png);background-size:contain;background-repeat:no-repeat"></div>


    <!-- Card Text -->
    <div class="card-text" style="background-color:#f9f9f9;" >

      <!-- Project Authors -->
    <!--  <h6>BY
          <a href="" itemprop="author">JI SHI</a>
      </h6>
-->
      <!-- Project Title -->
      <p style=" text-align:center;color:#47525d;font-size:2em;">
         Fast
      </p>
      <p class="" style=" text-align:center;color:#47525d;font-size:0.9em;" >
     Now fast access to all your file and folders on Pocket Cloud.
      </p>


       <!-- <h5 style="color:#7171ff"><i class="fa fa-flash"></i> NEW</h5>-->
</div> 
<!--<h4 style=" text-align:center;" >
                   <a class="trending-button  " href="">DETAILS</a>

      </h4>-->
    </div>


  </div>
 
     <div class="columns small-12 medium-6 large-3 slide-left" style="float:left;margin-bottom:30px;" itemscope itemtype="http://schema.org/CreativeWork">
  <div class="card card-mini" style="background-color:#f9f9f9;" >


<!-- Card Image -->
   
        <meta itemprop="image" content="">
        <div class="card-image " style="background-color:#f9f9f9;background-image:url(assets/stylesheets/new-images/wallet.png);background-size:contain;background-repeat:no-repeat"></div>


    <!-- Card Text -->
    <div class="card-text" style="background-color:#f9f9f9;" >

      <!-- Project Authors -->
    <!--  <h6>BY
          <a href="" itemprop="author">JI SHI</a>
      </h6>
-->
      <!-- Project Title -->
      <p style=" text-align:center;color:#47525d;font-size:2em;">
         Free
          
      </p>
            <p class="" style=" text-align:center;color:#47525d;font-size:0.9em;" >
Store unlimited data on Pocket Cloud without any worry because Pocket Cloud is absolutely Free.
 </p>

       <!-- <h5 style="color:#7171ff"><i class="fa fa-flash"></i> NEW</h5>-->
</div> 
<!--<h4 style=" text-align:center;" >
                   <a class="trending-button  " href="">DETAILS</a>

      </h4>-->
    </div>


  </div>
  
  
   <div class="columns small-12 medium-6 large-3 slide-left" style="float:left;margin-bottom:30px;" itemscope itemtype="http://schema.org/CreativeWork">
  <div class="card card-mini" style="background-color:#f9f9f9;" >


<!-- Card Image -->
   
        <meta itemprop="image" content="">
        <div class="card-image " style="background-color:#f9f9f9;background-image:url(assets/stylesheets/new-images/shield.png);background-size:contain;background-repeat:no-repeat"></div>


    <!-- Card Text -->
    <div class="card-text" style="background-color:#f9f9f9;" >

      <!-- Project Authors -->
    <!--  <h6>BY
          <a href="" itemprop="author">JI SHI</a>
      </h6>
-->
      <!-- Project Title -->
      <p style=" text-align:center;color:#47525d;font-size:2em;">
         Secure
          
      </p>
            <p class="" style=" text-align:center;color:#47525d;font-size:0.9em;" >
Pocket Cloud takes the security and privacy of your data very seriously. All your files are encrypted with high security protocols.     
 </p>
 

       <!-- <h5 style="color:#7171ff"><i class="fa fa-flash"></i> NEW</h5>-->
</div> 
<!--<h4 style=" text-align:center;" >
                   <a class="trending-button  " href="">DETAILS</a>

      </h4>-->
    </div>


  </div>
  
  
   <div class="columns small-12 medium-6 large-3 slide-left" style="float:left;margin-bottom:30px;" itemscope itemtype="http://schema.org/CreativeWork">
  <div class="card card-mini" style="background-color:#f9f9f9;" >


<!-- Card Image -->
   
        <meta itemprop="image" content="">
        <div class="card-image " style="background-color:#f9f9f9;background-image:url(assets/stylesheets/new-images/unlimited.png);background-size:contain;background-repeat:no-repeat"></div>


    <!-- Card Text -->
    <div class="card-text" style="background-color:#f9f9f9;" >

      <!-- Project Authors -->
    <!--  <h6>BY
          <a href="" itemprop="author">JI SHI</a>
      </h6>
-->
      <!-- Project Title -->
      <p style=" text-align:center;color:#47525d;font-size:2em;">
         Unlimited     
      </p>
       <p class="" style=" text-align:center;color:#47525d;font-size:0.9em;" >
      Unlike other cloud storage services, we don't limit the amount of files you can backup. Enjoy the freedom you get with Pocket Cloud.
      </p>


       <!-- <h5 style="color:#7171ff"><i class="fa fa-flash"></i> NEW</h5>-->
</div> 
<!--<h4 style=" text-align:center;" >
                   <a class="trending-button  " href="">DETAILS</a>

      </h4>-->
    </div>


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
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.3/jquery.min.js"></script>
<script src="assets/js/jquery.min.js"></script>
<script type="text/javascript" src="assets/js/jquery.fadethis.min.js"></script>

<script src="assets/js/hidden-nav-script.js" type="text/javascript" ></script>
<script>$(window).fadeThis({
    reverse:false
});</script>

<script>$('.dark').fadeThis({
    reverse:false,
	speed:2000
});</script>
<script>

/*(function(){

  var parallax = document.querySelectorAll(".parallax"),
      speed = 0.6;

  window.onscroll = function(){
    [].slice.call(parallax).forEach(function(el,i){

      var windowYOffset = window.pageYOffset,
          elBackgrounPos = "0 " + (windowYOffset * speed) + "px";
      
      el.style.backgroundPosition = elBackgrounPos;

    });
  };

})();*/
</script>