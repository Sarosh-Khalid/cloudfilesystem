var clickMEDashboard=0;


$('.toggle_dashboard_nav').on('click',function(){
	
	if(clickMEDashboard%2==0){

		$('.dashboard_section_layer').show('slide',{direction:'left'},500);
		$('.dashboard_section').animate({'left' : "200px"  },500);

		$('.dashboard_aside').show('slide',{direction:'left'},500);
		clickMEDashboard++;
	}
	else{

		$('.dashboard_section_layer').hide('slide',{direction:'right'},500);
		$('.dashboard_aside').hide('slide',{direction:'left'},500);
		$('.dashboard_section').animate({'left' : "0px"  },500);

		clickMEDashboard++;
	}
	
});