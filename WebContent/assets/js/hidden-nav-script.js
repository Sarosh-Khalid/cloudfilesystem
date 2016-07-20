var clickME=0;
var degree1=45;
var degree2=-45;
var negdegree1=0;
var negdegree2=0;

$(window).resize(function(){
		var width=$(window).width();
    if(width >= 623 && clickME%2==1){
			$('.pc_aside_nav_hidden').css({'display':'none'});

				$('.logo-inner-1').css({'-webkit-transform' : 'rotate('+ negdegree1 +'deg)',
                 '-moz-transform' : 'rotate('+ negdegree1 +'deg)',
                 '-ms-transform' : 'rotate('+ negdegree1 +'deg)',
                 'transform' : 'rotate('+ negdegree1 +'deg)','width':'25px'});
		$('.logo-inner-2').css({'-webkit-transform' : 'rotate('+ negdegree2 +'deg)',
                 '-moz-transform' : 'rotate('+ negdegree2 +'deg)',
                 '-ms-transform' : 'rotate('+ negdegree2 +'deg)',
                 'transform' : 'rotate('+ negdegree2 +'deg)','width':'25px','margin-top':'5px'});
				 $('.logo-inner-3').show();	
		clickME++;
}
});
$('.push').on('click',function(){
	if(clickME%2==0){
		$('.pc_aside_nav_hidden').slideDown(500);
		$('.pc_aside_nav_hidden ul').show();
		$('.pc_aside_nav_hidden ul li').show();
		$('.pc_aside_nav_hidden ul li a').show();
		$('.logo-inner-1').css({'-webkit-transform' : 'rotate('+ degree1 +'deg)',
                 '-moz-transform' : 'rotate('+ degree1 +'deg)',
                 '-ms-transform' : 'rotate('+ degree1 +'deg)',
                 'transform' : 'rotate('+ degree1 +'deg)','width':'35px','transition':'transform 1s'});
		$('.logo-inner-2').css({'-webkit-transform' : 'rotate('+ degree2 +'deg)',
                 '-moz-transform' : 'rotate('+ degree2 +'deg)',
                 '-ms-transform' : 'rotate('+ degree2 +'deg)',
                 'transform' : 'rotate('+ degree2 +'deg)','width':'35px','margin-top':'-2.5px','transition':'transform 1s'});
		$('.logo-inner-3').hide();	 
		clickME++;
	}
	else{
		$('.pc_aside_nav_hidden').slideUp(500);

				$('.logo-inner-1').css({'-webkit-transform' : 'rotate('+ negdegree1 +'deg)',
                 '-moz-transform' : 'rotate('+ negdegree1 +'deg)',
                 '-ms-transform' : 'rotate('+ negdegree1 +'deg)',
                 'transform' : 'rotate('+ negdegree1 +'deg)','width':'25px','transition':'transform 1s'});
		$('.logo-inner-2').css({'-webkit-transform' : 'rotate('+ negdegree2 +'deg)',
                 '-moz-transform' : 'rotate('+ negdegree2 +'deg)',
                 '-ms-transform' : 'rotate('+ negdegree2 +'deg)',
                 'transform' : 'rotate('+ negdegree2 +'deg)','width':'25px','margin-top':'5px','transition':'transform 1s'});
				 $('.logo-inner-3').show(1000);	
		clickME++;
	}
	
});
