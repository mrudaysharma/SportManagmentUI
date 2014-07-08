<%@ page language="java" contentType="text/xml; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.sql.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.URI"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.ws.rs.core.MediaType"%>
<%@ page import="javax.ws.rs.core.UriBuilder"%>

<%@ page import="com.sun.jersey.api.client.Client"%>
<%@ page import="com.sun.jersey.api.client.WebResource"%>
<%@ page import="com.sun.jersey.api.client.config.ClientConfig"%>
<%@ page import="com.sun.jersey.api.client.config.DefaultClientConfig"%>
<%@include file="BaseURI.jsp"%>
<%
	String sportText = BaseURI.getBaseURI();
	//System.out.println(sportText);
	out.print(sportText);
%>