var xmlhttp;
var element;
var geocoder;
var conorstate;
var Country;
var State;
var param;
var sportsName;
var uid;




function loadTVContent()
{
var id = "sportsServer/id/UserID.jsp";
loadContent(id);
}

function getCountry() {
	var url = "sportsServer/UserLocation/Country.jsp";
	loadContent(url);
}
function loadContent(url) {
	
	xmlhttp = GetXmlHttpObject();

	if (xmlhttp == null) {
		alert("Your browser does not support Ajax HTTP");
		return;
	}
    
	xmlhttp.onreadystatechange = getOutput;

	xmlhttp.open("GET", url, false);
	xmlhttp.setRequestHeader('Content-Type', 'text/xml');
	
	xmlhttp.send(null);
}

function getOutput() {

	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
			var doc="";
			
			//var tvsport = xmlhttp.responseXML.documentElement.getElementsByTagName("TV");
			if(xmlhttp.responseXML==null)
				{
				// doc =  Titanium.XML.parseString(xmlhttp.responseData.toString());
				}
			else
				  doc = xmlhttp.responseXML.documentElement;
	        var tvsport = doc.getElementsByTagName("TV")[0].getAttribute("uid");
	       /* for (var i=0;i<tvsport.length;i++)
	        {
	        uid = tvsport[0].getAttribute('uid');
	      
	        }*/
	        document.getElementsByName("userID")[0].value = "";
			document.getElementsByName("userID")[0].value = tvsport;
			
			/*x=xmlhttp.responseXML.documentElement.getElementsByTagName("Event");
			 for (var i=0;i<x.length;i++)
		      {
				sportsName = x[i].getAttribute('sportsName');*/ 
			
			
			
			
		}
	}

	}


function GetXmlHttpObject() {
	if (window.XMLHttpRequest) {
		return new XMLHttpRequest();
	}
	if (window.ActiveXObject) {
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
	return null;
}





/*if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(successFunction, errorFunction);
} 
// Get the latitude and the longitude;
function successFunction(position) {
    var lat = position.coords.latitude;
    var lng = position.coords.longitude;
    codeLatLng(lat, lng);
}

function errorFunction(){
    alert("Geocoder failed");
}

  function initialize() {
	 
    geocoder = new google.maps.Geocoder();
  


  }

 // country and state
  function codeLatLng(lat, lng) {
	  geocoder = new google.maps.Geocoder();
	
    var latlng = new google.maps.LatLng(lat, lng);
   
    geocoder.geocode({'latLng': latlng}, function(results, status) {
      if (status == google.maps.GeocoderStatus.OK) {
      console.log(results);
        if (results[1]) {
         // formatted address
       // alert(results[0].formatted_address)
        // find country name
        	
             for (var i=0; i<results[0].address_components.length; i++) {
            for (var b=0;b<results[0].address_components[i].types.length;b++) {
            
            // there are different types that might hold a city admin_area_lvl_1
			// usually does in come cases looking for sublocality type will be
			// more appropriate
                if (results[0].address_components[i].types[b] == "country") {
                    // this is the object you are looking for
                    Country= results[0].address_components[i];
                    break;
                }
                if (results[0].address_components[i].types[b] == "administrative_area_level_1") {
                    // this is the object you are looking for
                    State= results[0].address_components[i];
                    break;
                }
            }
        }
        // city data
        // alert(city.short_name + " " + city.long_name)
             
       // document.getElementsByName("CountryText")[0].value =
		// Country.long_name;
        // document.getElementsByName("StateText")[0].value = State.long_name;

        } else {
          alert("No results found");
        }
      } else {
        alert("Geocoder failed due to: " + status);
      }
    });
		
  }*/