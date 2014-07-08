<%@ page contentType="text/html"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.*"%>
<%@ page import="java.util.*"%>
<%@ page import="javaQuery.j2ee.GeoLocation"%>

<%
	request.getHeader("VIA");
	String ipAddress = request.getHeader("X-FORWARDED-FOR");
	if (ipAddress == null) {
		ipAddress = request.getRemoteAddr();
	}
	GeoLocation gl = new GeoLocation();
    gl.GetGeoLocationByIP("195.37.234.237");
	
	response.getWriter().print(gl.Country);
%>