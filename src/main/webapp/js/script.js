



//var mapHandler = function(){
//	var sundsvallLatLong =  new google.maps.LatLng(62.390836, 17.306916);
//	
//	var mapOptions = { 
//		    zoom: 11, 
//		    center: sundsvallLatLong, 
//		    mapTypeId: google.maps.MapTypeId.ROADMAP 
//		};
//	
//   	resultMap = new google.maps.Map(document.getElementById(mapDiv), mapOptions);
//	
//	
//}();

function getLines() {
	console.log("getting bus lines");
	
	$.getJSON('service/busservice', function(data) {
		$.each(data, function() {
			console.log(this.name + ':' + this.lineNumber);
		})
	});
	
}



$(function() {
	
	console.log("running gui setup");
	
		$("#linesButton").button().bind('click',getLines);
		
	});