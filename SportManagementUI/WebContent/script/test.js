var geocoder;
var conorstate;
var Country;
var State;
var param;
var league;
var team = [];
var team2;
var userteam;

function loadTVContent(cname) {
	var id = "http://localhost:8080/SportManagementUI/xml/sportdata.xml";
	loadXMLDoc(id, cname);
}
function loadXMLDoc(url, cname) {
	var xmlhttp;
	var txt, x, xx, i;
	var tvsport;
	var uid;
	if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		xmlhttp = new XMLHttpRequest();
	} else {// code for IE6, IE5
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {

			// Fetch time
			var starttime = xmlhttp.responseXML.documentElement
					.getElementsByTagName("starttime")[0].childNodes[0].nodeValue;
			var endtime = xmlhttp.responseXML.documentElement
					.getElementsByTagName("endtime")[0].childNodes[0].nodeValue;
			var check = checkTime(starttime, endtime);

			if (check == true) {
				// Fetch UID
				var uid = xmlhttp.responseXML.documentElement
						.getElementsByTagName("UID")[0].childNodes[0].nodeValue;

				// Fetch SportName
				tvsport = xmlhttp.responseXML.documentElement
						.getElementsByTagName("name")[0].childNodes[0].nodeValue;

				document.getElementsByName("userID")[0].value = uid;
				document.getElementsByName("SportText")[0].value = tvsport;

				if (tvsport == "Football") {
					// Fetch League name
					league = xmlhttp.responseXML.documentElement
							.getElementsByTagName("League")[0].childNodes[0].nodeValue;

					// Fetch Team1 Name
					team[0] = xmlhttp.responseXML.documentElement
							.getElementsByTagName("Team1")[0].childNodes[0].nodeValue;

					// Fetch Team2 Name
					team[1] = xmlhttp.responseXML.documentElement
							.getElementsByTagName("Team2")[0].childNodes[0].nodeValue;

					var select = document.getElementById('TeamText');
					//select.disabled = true;
					for (var i = 0; i < team.length; i++) {
						var opt = document.createElement('option');
						opt.value = team[i];
						opt.innerHTML = team[i];
						select.appendChild(opt);
					}

					document.getElementsByName("Leaguetext")[0].value = league;

					if (league == "FIFA WM 2014") {

						if (cname == team[0] || cname == team[1]) {

							//document.getElementsByName("TeamText")[0].value = cname;

							var ele = document.getElementById('TeamText');
							for (var ii = 0; ii < ele.length; ii++)
								if (ele.options[ii].text == cname) { // Found!
									ele.options[ii].selected = true;
									//alert("Hey Today is a "+cname+" Match!\n Good Luck "+cname+" Team :)");
									$.Zebra_Dialog('<strong>Huhu!!</strong>Prost for'+cname, {
										    'custom_class':  'myclass',
										    'title': 'Good Luck Team'+cname
										});
								}

						} else {
							$.Zebra_Dialog('<strong></strong>, No Match of Team'+cname );
							
						}
					}

				}

			} else {
				alert("Currently, No ongoing sport");

			}
		}

	};
	xmlhttp.open("GET", url, true);
	xmlhttp.send();

}

// Check time range
function checkTime(starttime, endtime) {

	sthour = starttime.substr(0, starttime.indexOf(":"));
	ehour = endtime.substr(0, endtime.indexOf(":"));

	stminute = starttime.substr(starttime.indexOf(":") + 1);
	eminute = endtime.substr(endtime.indexOf(":") + 1);

	var d = new Date(), // current time
	hours = d.getHours(), mins = d.getMinutes();

	if (hours >= sthour && (hours < ehour || hours == ehour && mins <= eminute))
		return true;
	else
		return false;
}

function fetchxmlData(xmlhttp, data) {
	var datafetch = xmlhttp.responseXML.documentElement
			.getElementsByTagName(data)[0].childNodes[0].nodeValue;

	return datafetch;
}

function loadScript(src, callback) {

	var script = document.createElement("script");
	script.type = "text/javascript";
	if (callback)
		script.onload = callback;
	document.getElementsByTagName("head")[0].appendChild(script);
	script.src = src;
}

function log(str) {
	document.getElementsByTagName('pre')[0].appendChild(document
			.createTextNode('[' + new Date().getTime() + ']\n' + str + '\n\n'));
}

if (navigator.geolocation) {
	navigator.geolocation.getCurrentPosition(successFunction, errorFunction);
}
// Get the latitude and the longitude;
function successFunction(position) {
	var lat = position.coords.latitude;
	var lng = position.coords.longitude;
	codeLatLng(lat, lng);
}

function errorFunction() {
	alert("Geocoder failed");
}

function initialize() {

	geocoder = new google.maps.Geocoder();

}

function codeLatLng(lat, lng) {
	geocoder = new google.maps.Geocoder();

	var latlng = new google.maps.LatLng(lat, lng);

	geocoder
			.geocode(
					{
						'latLng' : latlng
					},
					function(results, status) {
						if (status == google.maps.GeocoderStatus.OK) {
							console.log(results);
							if (results[1]) {
								// formatted address
								// alert(results[0].formatted_address)
								// find country name

								for (var i = 0; i < results[0].address_components.length; i++) {
									for (var b = 0; b < results[0].address_components[i].types.length; b++) {

										// there are different types that might
										// hold a city admin_area_lvl_1 usually
										// does in come cases looking for
										// sublocality type will be more
										// appropriate
										if (results[0].address_components[i].types[b] == "country") {
											// this is the object you are
											// looking for
											Country = results[0].address_components[i];
											break;
										}
										if (results[0].address_components[i].types[b] == "administrative_area_level_1") {
											// this is the object you are
											// looking for
											State = results[0].address_components[i];
											break;
										}
									}
								}
								// city data
								// alert(city.short_name + " " + city.long_name)
								document.getElementsByName("CountryText")[0].value = Country.long_name;
								document.getElementsByName("StateText")[0].value = State.long_name;
								loadTVContent(Country.long_name);

							} else {
								alert("No results found");
							}
						} else {
							alert("Geocoder failed due to: " + status);
						}
					});

}