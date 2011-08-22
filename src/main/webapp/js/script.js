function toggleMenu() {
	console.log('toogling menu...');
	$('ul.menu_body').slideToggle('medium');
}

function createButton() {
	$('button#linesButton').button({icons: {primary: "ui-icon-triangle-1-s"} });
	console.log('applied button');
	$('button#linesButton').click(toggleMenu);
//	$('button#linesButton').bind({
//		'click':toggleMenu,
//		'mouseenter':function(){
//			console.log('mouseenter...');
//		},
//	});
	console.log('attached click...');
}

function pimpMenu() {
	$('ul.menu_body li:even').addClass('alt');
	
//	$('ul.menu_body li a').mouseover(function () { 
//		$(this).animate({ paddingLeft: "20px" }, 50 ); 
//	});
//
//	$('ul.menu_body li a').mouseout(function () { 
//		$(this).animate({ paddingLeft: "10px" }, 50 ); 
//	});
}

function menuItemClicked(event) {
	console.log(event.data.busLine.name + ' clicked ' + event.data.busLine.lineNumber);
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

function fillMenu() {
	getLines();
}

$(function() {
	createButton();
	fillMenu();
});

