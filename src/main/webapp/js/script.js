var mapHandler = function(){
	var sundsvallLatLong =  new google.maps.LatLng(62.390836, 17.306916);
	var busStops = [];
	var resultMap;
	
	var mapOptions = { 
		    zoom: 14, 
		    center: sundsvallLatLong, 
		    mapTypeId: google.maps.MapTypeId.ROADMAP 
		};
	
   	resultMap = new google.maps.Map(document.getElementById('mapDiv'), mapOptions);
	
   	function addBusStop(busStop) {
   		var busStopIcon = 'img/busstopicon.png';
   		var position = new google.maps.LatLng(busStop.position.latitude, busStop.position.longitude);
   		var busStopMarker = new google.maps.Marker({position: position, 
   								map: resultMap, 
   								title: busStop.name, 
   								icon: busStopIcon});
   		busStops.push(busStopMarker);
   	}
   	
   	function clearBusStops() {
   		$.each(busStops, function(){
   			this.setMap(null);
   		});
   	}
   	
   	return {
   		addBusStop:addBusStop,
   		clearBusStops:clearBusStops
   	}
	
}();



function toggleMenu() {
	console.log('toogling menu...');
	$('ul.menu_body').slideToggle('medium');
}

function createButton() {
	$('button#linesButton').button({icons: {primary: "ui-icon-triangle-1-s"} });
	$('button#linesButton').click(toggleMenu);
}

function pimpMenu() {
	$('ul.menu_body li:even').addClass('alt');
	
	$('ul.menu_body li a').mouseover(function () { 
		$(this).animate({ paddingLeft: "20px" }, 50 ); 
	});

	$('ul.menu_body li a').mouseout(function () { 
		$(this).animate({ paddingLeft: "10px" }, 50 ); 
	});
}

function menuItemClicked(event) {
	console.log(event.data.busLine.name + ' clicked ' + event.data.busLine.lineNumber);
	mapHandler.clearBusStops();
	getRoute(event.data.busLine.lineNumber);
	toggleMenu();
	$('#currentLine').html("Current line: " + event.data.busLine.name).show();
}

function getLines() {
	console.log("getting bus lines");
	
	$.getJSON('service/busservice', function(data) {
		$.each(data, function(index, item) {
			var busLine = item;
			var menuItem = '<li><a href="#">' + busLine.name + '</a></li>';

			$(menuItem).bind('click', {"busLine":busLine}, menuItemClicked).appendTo('ul.menu_body');
			
			if (index == data.length - 1) {
				pimpMenu();
			}
		})
	});
}

function getRoute(lineNumber) {
	$.getJSON('service/busservice/route/' + lineNumber, function(data) {
		$.each(data.routeInfo.busStops, function(){
			mapHandler.addBusStop(this);
		});
	});
}

function fillMenu() {
	getLines();
}

$(function() {
	createButton();
	fillMenu();
});

