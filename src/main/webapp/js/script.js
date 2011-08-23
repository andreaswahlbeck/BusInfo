function isStringEmpty(str) {
	return (!str || 0 === str.length); 
}

var mapHandler = function(){
	var sundsvallLatLong =  new google.maps.LatLng(62.390836, 17.306916);
	var busStops = [];
	var resultMap;
	var currentBusPosition;
	
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
   								icon: busStopIcon,
   								zIndex: 10});
   		busStops.push(busStopMarker);
   	}
   	
   	function clearBusStops() {
   		$.each(busStops, function(){
   			this.setMap(null);
   		});
   		removeBusIcon();
   	}
   	
   	function updateBusPosition(position) {
   		var busIcon = 'img/busicon.png';
   		var position = new google.maps.LatLng(position.latitude, position.longitude);
   		removeBusIcon();
   		currentBusPosition = new google.maps.Marker({position: position, 
														map: resultMap, 
														title: 'The Bus', 
														icon: busIcon,
						   								zIndex: 20});
   	}
   	
   	function removeBusIcon() {
   		if (currentBusPosition && currentBusPosition.getMap()) {
   			currentBusPosition.setMap(null);
   		}
   	}
   	
   	return {
   		addBusStop:addBusStop,
   		clearBusStops:clearBusStops,
   		updateBusPosition:updateBusPosition,
   		removeBusIcon:removeBusIcon
   	}
	
}();

var currentLineNumber = "";
var busUpdater;

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
	if (busUpdater) {
		console.log("shutting down current updater...")
		clearInterval(busUpdater);
	}
	startTrackCurrentBus(event.data.busLine.lineNumber);
}

function startTrackCurrentBus(lineNumber) {
	console.log("Starting new updater for: " + lineNumber)
	currentLineNumber = lineNumber;
	busUpdater = setInterval(getLatestBusPosition, "2000");
}

function getLatestBusPosition() {
	//console.log("updating bus position for line: " + currentLineNumber);
	$.getJSON('service/busservice/position/' + currentLineNumber, function(data) {
		//console.log("Got updated position: " + data);
		if (!isStringEmpty(data.longitude) && !isStringEmpty(data.latitude)) {
			mapHandler.updateBusPosition(data);
		} else {
			console.log("Clearing update interval");
			clearInterval(busUpdater);
		}
	});
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

