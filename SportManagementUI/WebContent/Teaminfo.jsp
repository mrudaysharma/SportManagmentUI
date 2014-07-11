<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
	String cname = request.getParameter("CountryText");
	String sname = request.getParameter("StateText");
	String uid = request.getParameter("userID");
	String spname = request.getParameter("SportText");
	String lgname = request.getParameter("Leaguetext");
	String tname = request.getParameter("Teams");
%>
<html>
<head>
<script type="text/javascript">
var cn = "<%=cname%>";
var sn = "<%=sname%>";
var uid = "<%=uid%>";
var spn = "<%=spname%>";
var lgn = "<%=lgname%>";
var tm =  "<%=tname%>";
	var eventinfo;
	function getData() {
		document.getElementById('content').innerHTML = "";
	}
	function postMessage(event) {
		eventinfo = event;
		var xmlhttp = new XMLHttpRequest();
		xmlhttp.open("POST", "shoutServlet?", false);
		xmlhttp.setRequestHeader("Content-Type",
				"application/x-www-form-urlencoded");

		xmlhttp.send("country=" + cn + "&state=" + sn + "&uid=" + uid
				+ "&sportname=" + spn + "&leaguename=" + lgn + "&team=" + tm
				+ "&event=" + eventinfo);
	}
	var messagesWaiting = false;
	function getMessages() {
		if (!messagesWaiting) {
			messagesWaiting = true;
			var xmlhttp = new XMLHttpRequest();
			xmlhttp.onreadystatechange = function() {
				if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
					messagesWaiting = false;
					var contentElement = document.getElementById("content");
					contentElement.innerHTML = xmlhttp.responseText
							+ contentElement.innerHTML;
				}
			}
			xmlhttp.open("GET", "shoutServlet?country=" + cn + "&state=" + sn
					+ "&uid=" + uid + "&sportname=" + spn + "&leaguename="
					+ lgn + "&team=" + tm, true);
			xmlhttp.send();
		}
	}
	setInterval(getMessages, 1000);
	
</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JSP Page</title>
</head>
<body onload="getMessages();">
	<h1><%=tname%>
		Team Information
	</h1>
	<form>
		<table>
			<tr>

				<td><input type="button" onclick="postMessage('news');"
					value="News" /></td>
			</tr>

			<tr>
				<td><input type="button" onclick="postMessage('LiveText');"
					value="Text Commentary" /></td>
			</tr>
		</table>
	</form>
	<h2>Current Shouts</h2>
	<div id="content">

		<%
			if (application.getAttribute("messages") != null) {
		%>
		<%=application.getAttribute("messages")%>
		<%
			}
		%>
	</div>
	<script type="text/javascript">
		getData();
	</script>


	
</body>
</html>