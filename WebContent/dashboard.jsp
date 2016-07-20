
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
 <%
if(session.getAttribute("email") ==null || session.getAttribute("email") == ""){
 
%>
<script>
location.assign('index.jsp');
</script>
<%
}
else{
%>
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
<script src="assets/js/jquery.min.js" type="text/javascript" ></script>
<body>
   <div id="site-logo" class="menu-button push">
  
  <div class="logo-black fade-border">
    <div class="logo-inner fade-border logo-inner-1"></div>
        <div class="logo-inner fade-border logo-inner-2"></div>
        <div class="logo-inner fade-border logo-inner-3 "></div>
       </div>
  </div>
   <header class="pc_header">
       <a href="dashboard.jsp" class="pc_header_logo_a"><div class="pc_header_logo"></div></a>
            <nav class="pc_nav" >
            	<ul class="pc_nav_ul">
                	<a href="dashboard.jsp"><li class="pc_nav_ul_li">Home</li></a>
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
            
            <div  class="dashboard_nav">
            	<div class="toggle_dashboard_nav"></div>
                <ul class="dashboard_nav_ul">
                	<li id="ShareOption" class="dashboard_nav_ul_li">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/share-symbol.png);"></span>
                    <span class="dashboard_nav_ul_li_span_text">Share</span>
                    </li>                	
                    <li id="ViewOption" class="dashboard_nav_ul_li not_multiple_active">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/eye-close-up.png);"></span>
                    <span  class="dashboard_nav_ul_li_span_text">View</span>
                    </li>
                	<li id="DownloadOption" class="dashboard_nav_ul_li">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/download.png);"></span>
                   	<span  class="dashboard_nav_ul_li_span_text"> Download</span>
                    </li>
                 	<li id="DeleteOption" class="dashboard_nav_ul_li">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/garbage.png);"></span>
                    <span class="dashboard_nav_ul_li_span_text">Delete</span>
                    </li>
                	<li id="MoveToOption" class="dashboard_nav_ul_li">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/moveto-new.png);"></span>
                   	<span class="dashboard_nav_ul_li_span_text"> Move to</span>
                    </li>
                	<li id="CopyToOption" class="dashboard_nav_ul_li">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/copy.png);"></span>
                    <span class="dashboard_nav_ul_li_span_text">Copy to</span>
                    </li>
                	<li id="RenameOption" class="dashboard_nav_ul_li not_multiple_active">
                    <span class="dashboard_nav_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/edit.png);"></span>
                    <span class="dashboard_nav_ul_li_span_text">Rename</span>
                    </li>
                	
                </ul>
            </div>
            
    </header>
   <nav class="pc_aside_nav_hidden">
      <ul>
      	<li><a href="dashboard.jsp">Home</a></li>
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



	<div class="dashboard_aside">
    	<ul class="dashboard_aside_ul">
        <form  enctype="multipart/form-data">
            <input id="FileUpload" type="file"  name="files[]"  multiple style="visibility:hidden">
        </form>
        	<li class="dashboard_aside_ul_li upload_button fileupload" id="" onclick='$("#FileUpload").click()'>
            
            	<span class="dashboard_aside_ul_li_span_img " style="background-image:url(assets/stylesheets/new-images/upload.png);"></span>
           		Upload
            </li>
            <li  id="CreateFolder" class="dashboard_aside_ul_li newFolder_button">
            <span class="dashboard_aside_ul_li_span_img" style="background-image:url(assets/stylesheets/new-images/plus.png);"></span>
            New Folder
            </li>
        	<li  class=" seperate_li"></li>
        	<li id="BackOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover ">
            <span class="dashboard_aside_ul_li_span_img background_image_file"></span>
            Back
            </li>
                <li id="HomeOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover ">
            <span class="dashboard_aside_ul_li_span_img background_image_file"></span>
            Home
            </li>
            <li id="AllOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover ">
            <span class="dashboard_aside_ul_li_span_img background_image_file"></span>
            Refresh
            </li> 
            <li id="FilesOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover ">
            <span class="dashboard_aside_ul_li_span_img background_image_file"></span>
            Files
            </li>
        	<li id="AudioOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover ">
            <span class="dashboard_aside_ul_li_span_img background_image_audio" ></span>
            Audio
            </li>
        	<li id="VideoOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover">
             <span class="dashboard_aside_ul_li_span_img background_image_video" ></span>
            Video
            </li>
        	<li id="PhotosOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover">
             <span class="dashboard_aside_ul_li_span_img background_image_photos" ></span>
            Photos
            </li>
        	<li id="DocumentsOption" class="dashboard_aside_ul_li dashboard_aside_ul_li_hover">
            <span class="dashboard_aside_ul_li_span_img background_image_documents"></span>
			Documents
            </li>
        </ul>
    </div> 
   
   <style>
   .card-mini{
	  height:auto; 
	  border:1px solid #fff;
	  cursor:pointer;
   }
 
   </style>
   
   <div class="dashboard_section_layer"></div>
    <div class="dashboard_section" >
         <div class="row">
             <h6 id="PathViewing" style="color:#666;margin:0 0 0px 15px;">Home</h6>
	<h2 style="color:#666;margin:0 0 20px 15px;line-height:60px;border-bottom:1px solid #ccc;">Files</h2>
       
        <span id ="data"> <div class="loading"></div>
           
      
       
       
        </span>
</div>
    
    
    </div>


</body>

</html> 
<!--
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.3/jquery.min.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script>
-->

<script src="assets/js/hidden-nav-script.js" type="text/javascript" ></script>
<script src="assets/js/nav_toggle.js" type="text/javascript" ></script>
<script src="assets/js/dashboardScript.js" type="text/javascript" ></script>

<%
out.write("<script>setId('"+session.getAttribute("email")+"');</script>");
}
%>
