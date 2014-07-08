<%@ page language="java"
	contentType="text/xml; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.net.URI"%>
<%@ page import="java.util.*"%>
<%@ page import="java.io.*"%>
<%@ page import="java.net.URL"%>
<%@ page import="java.net.HttpURLConnection"%>

<%!public static class BaseURI {
private static String xml;

		public static String getBaseURI() {

			try {
				URL url = new URL(
						"http://localhost:8080/SportManagementUI/sportdata.xml");
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				InputStream stream = connection.getInputStream();
				xml = getStringFromInputStream(stream);
			} catch (Exception e) {

			}
			return xml;
		}

		private static String getStringFromInputStream(InputStream is) {

			BufferedReader br = null;
			StringBuilder sb = new StringBuilder();

			String line;
			try {

				br = new BufferedReader(new InputStreamReader(is));
				while ((line = br.readLine()) != null) {
					sb.append(line);
				}

			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			return sb.toString();

		}

	}%>
