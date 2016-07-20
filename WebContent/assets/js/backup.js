var countElement=0;
var name;
var id=null;
var path="Home";
var mulName=JSON;
var total=0;

var is_selected_not_multiple=false;
function setId(id){   if(this.id===null||this.id===""){   this.id=id;show();}}
function first() {

    $(this).css({border: '1px solid #0079b4'});
    $(this).children('.card-text').css({'background': '#0079b4'});
    $(this).children('.card-text').children('.file_name').css({'color': '#fff'});
    $(this).one("click", second);
    name=$(this).children('.card-text').children('.file_name').html();
    var find=false;
    for(var i=0;i<total;i++)
    {
        if(mulName[i].name===""){mulName[i]={"name":name};find =true;break;}
    }
    if(!find){  mulName[total]={"name":name};total++;}
    countElement++;
   
    if(countElement>0){
        is_selected_not_multiple=false;
        $('.dashboard_nav_ul').css({display: 'block'});
    }
    if(countElement==1){
        is_selected_not_multiple=true;
        $('.not_multiple_active').css({display: 'block'});

    }else{
        is_selected_not_multiple=false;
        $('.not_multiple_active').css({display: 'none'});
    }
}
function second() {
    //Code for second time click goes here
    $(this).css({border: '1px solid #fff'});
    $(this).children('.card-text').css({'background': '#fff'});
    $(this).children('.card-text').children('.file_name').css({'color': '#47525d'});
    $(this).one("click", first);
     name=$(this).children('.card-text').children('.file_name').html();
      for(var i=0;i<total;i++)
    {
        if(mulName[i].name===name){mulName[i]={"name":""};break;}
    }
    countElement--;

    if(countElement<=0){
        is_selected_not_multiple=false;
        $('.dashboard_nav_ul').css({display: 'none'});
    }
    if(countElement==1){
        is_selected_not_multiple=true;
        $('.not_multiple_active').css({display: 'block'});

    }else{
        is_selected_not_multiple=false;
        $('.not_multiple_active').css({display: 'none'});
    }
}
$("#AllOption").click(function(e){show();});
$("#BackOption").click(function(e){
    $.ajax({
        type :'POST',
        url : '/files/fileops/parentpath',
        data :  {"id":id,"path":path},
        success : function(data){
            path =data;
            show();
        }
    });
});
$("#HomeOption").click(function(e){path="Home"; show();   });
$("#FilesOption").click(function(e){ showspecific("files");   });
$("#AudioOption").click(function(e){ showspecific("audio");   });
$("#VideoOption").click(function(e){showspecific("video");});
$("#PhotosOption").click(function(e){showspecific("photos");});
$("#DocumentsOption").click(function(e){showspecific("documents");});
$("#ViewOption").click(function(e){
    if(is_selected_not_multiple){
        for(var i=0;i<total;i++)
    {
        if(mulName[i].name!==""){
            $.ajax({
                type :'POST',
                url : '/files/fileops/view',
                data :  {"id":id,"path":path,"name":mulName[i].name},
                success : function(data){
                    if (data !="Not Supported yet..")
                    { path =data;
                        show();
                    }
                }
            });
       break;
            }
    }
    }
});
$("#RenameOption").click(function(e){
    if(is_selected_not_multiple){
        for(var i=0;i<total;i++)
    {
        if(mulName[i].name!==""){
            $.ajax({
                type :'POST',
                url : '/files/fileops/rename',
                data :  {"id":id,"path":path,"oldname":mulname[i].name,"newname":"rename"},
                success : function(data){
                    if(data === "true")
                    {
                        alert(data);
                        show();
                    }
                    else
                    {
                        alert(data);
                    }
                }
            });
       break;
            }
    }
    }
});
$("#DeleteOption").click(function(e){
    
    for(var i=0;i<total;i++)
    {
        if(mulName[i].name!==""){
            $.ajax({
                type :'POST',
                url : '/files/fileops/delete',
                data :  {"id":id,"path":path,"name":mulName[i].name},
                success : function(data){
                    if(data === "true")
                    {
                        show();
                    }
                    else
                    {
                        alert(data);
                    }
                }
            });
        }
    }
    
});
$("#DownloadOption").click(function(e){
    for(var i=0;i<total;i++)
    {if(mulName[i].name!=="")
    {location.href="/files/fileops/download/file?id="+id+"&path="+path+"&name="+mulName[i].name;}
    }
});
$('#FileUpload').change(function(e){

    //e.preventDefault();
   // $('#data').html("<div class='loading'></div>");
    var file = $('input[name="files[]"').get(0).files[0];
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
            $('#files').val("");
            if(data == "true")
            {

                show();
            }
            else
            {
                $('#data').html(data);
            }
        }

    });


});
$('#CreateFolder').click(function(e){


    //$('#data').html("<div class='loading'></div>");
    $.ajax({
        type :'POST',
        // contentType: 'application/json',
        url : '/files/fileops/makefolder/',
        //dataType: "JsonObject",
        data : {"id":id,"path":path},
        success : function(data){
            var details =JSON.parse(data);
            if(details.result =="true")
            {
                show();
            }
            else{
                $('#data').html(data);
            }

        }
    });
    e.preventDefault();

});
function show() {
    $.ajax({
        type :'POST',
        // contentType: 'application/json',
        url : '/files/fileops/pathdetails',

        //dataType: "json",
        data :  {"id":id,"path":path},
        success : function(data){
            var details =JSON.parse(data);
            var show = "";
            for(node=0;node<details.length;node++)
            {     if(details[node].isfolder =="1")
            {
                show += "<div class=\"columns new-xsmall-12 new-small-6 new-medium-4 new-large-2 \""+
                    "style=\"float: left; margin-bottom: 30px; opacity: 1; visibility: visible;"+
                    "position: relative; right: 0px;\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">"+
                    "<div class=\"card card-mini\" style=\"background-color:#fff;\">"+
                    "<!-- Card Image -->"+
                    "<div class=\"card-image card-image-width\" style=\"background-color:#fff;"+
                    "background-image:url(assets/stylesheets/new-images/folder-icon.png);"+
                    "background-size:90% 80%;margin:0 auto;background-repeat:no-repeat\"></div>"+
                    "<div class=\"card-text\" style=\"background-color:#fff\">"+
                    "<p class=\"file_name\">"+details[node].name+"</p>"+
                    "</div> </div></div>";
            }
            else
            {
                fileicon="more.png";
                var ext=details[node].type;

                switch (ext){
                    case (".jpg"):
                        fileicon="scene.jpg";
                        break;
                    case (".pdf"):
                        fileicon="pdf.png";
                        break;
                    case (".xlx"):
                        fileicon="xlsx-file-format.png";
                        break;
                    case (".docx"):
                        fileicon="doc.png";
                        break;
                    case (".mp3"):
                        fileicon="mp3.png";
                        break;
                    case (".mp4"):
                        fileicon="mp4.png";
                        break;
                    case (".ppt"):
                        fileicon="ppt-file-format.png";
                        break;
                    case (".txt"):
                        fileicon="txt.png";
                        break;

                }

                show+="<div class=\"columns new-xsmall-12 new-small-6 new-medium-4 new-large-2 \""+
                    " style=\"float: left; margin-bottom: 30px; opacity: 1; visibility: visible; position: relative;"+
                    " right: 0px;\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">"+
                    "<div class=\"card card-mini\" style=\"background-color:#fff;\">"+
                    "<!-- Card Image -->"+
                    "<meta itemprop=\"image\" content=\"\">"+
                    "<div class=\"card-image card-image-width\" style=\"background-color:#fff;"+
                    "background-image:url(assets/stylesheets/new-images/"+
                    fileicon+
                    ");background-size:90% 80%;"+
                    "margin:0 auto;background-repeat:no-repeat\"></div>"+
                    "<!-- Card Text -->"+
                    "<div class=\"card-text\" style=\"background-color:#fff\">"+
                    "<p class=\"file_name\">"+details[node].name+"</p>"+
                    "</div></div></div>";
            }


            }
            $('#data').html(show);
            $('#PathViewing').html(path);
           
            if(countElement>0){
                $('card-mini').css({border: '1px solid #fff'});
                $('card-mini').children('.card-text').css({'background': '#fff'});
                $('card-mini').children('.card-text').children('.file_name').css({'color': '#47525d'});
                countElement=0;
                total=0;mulName=JSON;
                is_selected_not_multiple=false;
                $('.dashboard_nav_ul').css({display: 'none'});
            }
            $(".card-mini").one("click", first);

        }



    });
}
function showspecific(type) {
    $.ajax({
        type :'POST',
        // contentType: 'application/json',
        url : '/files/fileops/pathdetails/specific',

        //dataType: "json",
        data :  {"id":id,"path":path,"type":type},
        success : function(data){
            var details =JSON.parse(data);
            var show = "";
            for(node=0;node<details.length;node++)
            {     if(details[node].isfolder =="1")
            {
                show += "<div class=\"columns new-xsmall-12 new-small-6 new-medium-4 new-large-2 \""+
                    "style=\"float: left; margin-bottom: 30px; opacity: 1; visibility: visible;"+
                    "position: relative; right: 0px;\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">"+
                    "<div class=\"card card-mini\" style=\"background-color:#fff;\">"+
                    "<!-- Card Image -->"+
                    "<div class=\"card-image card-image-width\" style=\"background-color:#fff;"+
                    "background-image:url(assets/stylesheets/new-images/folder-icon.png);"+
                    "background-size:90% 80%;margin:0 auto;background-repeat:no-repeat\"></div>"+
                    "<div class=\"card-text\" style=\"background-color:#fff\">"+
                    "<p class=\"file_name\">"+details[node].name+"</p>"+
                    "</div> </div></div>";
            }
            else
            {
                fileicon="more.png";
                var ext=details[node].type;

                switch (ext){
                    case (".jpg"):
                        fileicon="scene.jpg";
                        break;
                    case (".pdf"):
                        fileicon="pdf.png";
                        break;
                    case (".xlx"):
                        fileicon="xlsx-file-format.png";
                        break;
                    case (".docx"):
                        fileicon="doc.png";
                        break;
                    case (".mp3"):
                        fileicon="mp3.png";
                        break;
                    case (".mp4"):
                        fileicon="mp4.png";
                        break;
                    case (".ppt"):
                        fileicon="ppt-file-format.png";
                        break;
                    case (".txt"):
                        fileicon="txt.png";
                        break;

                }

                show+="<div class=\"columns new-xsmall-12 new-small-6 new-medium-4 new-large-2 \""+
                    " style=\"float: left; margin-bottom: 30px; opacity: 1; visibility: visible; position: relative;"+
                    " right: 0px;\" itemscope=\"\" itemtype=\"http://schema.org/CreativeWork\">"+
                    "<div class=\"card card-mini\" style=\"background-color:#fff;\">"+
                    "<!-- Card Image -->"+
                    "<meta itemprop=\"image\" content=\"\">"+
                    "<div class=\"card-image card-image-width\" style=\"background-color:#fff;"+
                    "background-image:url(assets/stylesheets/new-images/"+
                    fileicon+
                    ");background-size:90% 80%;"+
                    "margin:0 auto;background-repeat:no-repeat\"></div>"+
                    "<!-- Card Text -->"+
                    "<div class=\"card-text\" style=\"background-color:#fff\">"+
                    "<p class=\"file_name\">"+details[node].name+"</p>"+
                    "</div></div></div>";
            }


            }
            $('#data').html(show);
            $('#PathViewing').html(path);

            if(countElement>0){
                $('card-mini').css({border: '1px solid #fff'});
                $('card-mini').children('.card-text').css({'background': '#fff'});
                $('card-mini').children('.card-text').children('.file_name').css({'color': '#47525d'});
                countElement=0;
                  total=0;mulName=JSON;
                is_selected_not_multiple=false;
                $('.dashboard_nav_ul').css({display: 'none'});
            }
            $(".card-mini").one("click", first);

        }



    });
}


function windowpop(url) {
    var leftPosition, topPosition;
    var width=window.screen.width / 2;
    var height=window.screen.height / 2;
    //Allow for borders.
    leftPosition = (window.screen.width / 2) - ((width / 2) + 10);
    //Allow for title and status bars.
    topPosition = (window.screen.height / 2) - ((height / 2) + 50);
    //Open the window.
    window.open(url, "Window2", "status=no,height=" + height + ",width=" + width + ",resizable=yes,left=" + leftPosition + ",top=" + topPosition + ",screenX=" + leftPosition + ",screenY=" + topPosition + ",toolbar=no,menubar=no,scrollbars=no,location=no,directories=no");
    return false;
}
