<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
  
<! DOCTYPE HTML>
    <html>
    <head>
    <title>Home</title>
    <link href="assets/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/bootstrap/dist/js/bootstrap.min.js"></script>

    </head>
    <body>

    <nav  class = "navbar navbar-default navbar-fixed-top navbar-custom " role = "navigation">

    <div class = "navbar-header">
    <button type = "button" class = "navbar-toggle"
    data-toggle = "collapse" data-target = "#example-navbar-collapse">
    <span class = "sr-only">Toggle navigation</span>
    <span class = "icon-bar"></span>
    <span class = "icon-bar"></span>
    <span class = "icon-bar"></span>
    </button>

    <a class = "navbar-brand" href = "#">File System</a>
    </div>

    <div class = "collapse navbar-collapse" id = "example-navbar-collapse">

    <ul class = "nav navbar-nav">
    <li class = "active"><a href = "home.jsp">Home</a></li>
    <li><a href="#">Logout</a> </li>
    <li> <form id="uploadfile"  method="POST" action="javascript:;" enctype="multipart/form-data" class="form form-inline">
    <input type="file" name="file" id="file"  class = "form form-control"/>
    <input type="hidden" value="<%   out.write((String) session.getValue("email")) ; %>" id="id" name="id">
    <input type="hidden" value="<%  out.write(request.getParameter("path")); %>" id="path" name="path">
    <input type="submit" value="Submit" class = "form form-control"/>
    </form>
    </li>
    <li class = "dropdown">
    <a href = "#" class = "dropdown-toggle" data-toggle = "dropdown">
    New Folder
    <b class = "caret"></b>
    </a>

    <ul class = "dropdown-menu">
    <li>
    <input type="text" id="newfolder" placeholder="Folder Name"  class = "form form-control"/>
    <input type="button" value="Create" id="Create"  class = "form form-control"/>
    </li>
    </ul>

    </li>

    </ul>
    </div>

    </nav>
 <h2>kk</h2>
    <div id = "data"></div>
    <script>
    $( document ).ready(function() {
    	$('#data').html("<div class='loading'></div>");
	$.ajax({
		type :'POST',
               // contentType: 'application/json',
		url : '/files/fileops/pathdetails',
                 //dataType: "JsonObject",
                 data : getPath(),
		success : function(data){
			$('#data').html(data);	
                    
		}
	});
	
	
});
function getPath()
{
	var id = document.getElementById("id").value; 
	var path=document.getElementById("path").value;
	   return  {"id":id,"path":path};

}
$('#Create').click(function(e){

	
	$('#data').html("<div class='loading'></div>");
	$.ajax({
		type :'POST',
               // contentType: 'application/json',
		url : '/files/fileops/makefolder/',
                 //dataType: "JsonObject",
                 data : getnewfolinfo(),
		success : function(data){
			$('#data').html(data);	
                        
		}
	});
	e.preventDefault();
	
});
function getnewfolinfo()
{
	 var name = document.getElementById("newfolder").value;
	    var id=document.getElementById("id").value;
	    var path=document.getElementById("path").value;
      return  {"id":id,"name":name,"path":path};
   //return JSON.stringify({"email":email,"password":password}); 
}
$('#uploadfile').submit(function(e){

	e.preventDefault();
	$('#data').html("<div class='loading'></div>");
	var file = $('input[name="file"').get(0).files[0];
	 var id=document.getElementById("id").value;
	    var path=document.getElementById("path").value;
	  var formData = new FormData();
	  formData.append('file', file);
	 formData.append('path',path);
	 formData.append('id',id);
	 
	  $.ajax({
	      url: "/files/fileops/upload",
	      type: 'POST',
	      xhr: function() {  // Custom XMLHttpRequest
	        var myXhr = $.ajaxSettings.xhr();
	        return myXhr;
	      },
	      // Form data
	      data: formData,
	      //Options to tell jQuery not to process data or worry about content-type.
	      cache: false,
	      contentType: false,
	      processData: false,
	      // beforeSend: beforeSendHandler,
	      success: function(data) {
	    	  $('#data').html(data);	
	    	  $('#file').val("");    
	      }
	   
	    });
	
	
});
</script>


    </body>
   

    </html>


