/**
 * 
 */
var xmlhttp;
var url;
var sporttext;
function getContent() {

	url = "sportsServer/id/UserID.jsp";
	xmlhttp = GetXmlHttpObject();

	if (xmlhttp == null) {
		alert("Your browser does not support Ajax HTTP");
		return;
	}

	xmlhttp.onreadystatechange = getOutput;

	xmlhttp.open("GET", url, true);
	xmlhttp.send(null);
}

function getOutput() {

	if (xmlhttp.readyState == 4) {
		if (xmlhttp.status == 200) {
            
			document.getElementById("showXML").innerHTML = xmlhttp.responseText;

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
