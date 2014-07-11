package push;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;

import com.sun.syndication.feed.synd.SyndEntry;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/shoutServlet" }, asyncSupported = true)
public class ShoutServlet extends HttpServlet {
	private List<AsyncContext> contexts = new LinkedList<>();
	private TeamNews news;
	private String rdf;
	private String goal;
	private String comment;
	private int charCount = 0;
	private String splitstring;

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		final AsyncContext asyncContext = request.startAsync(request, response);
		asyncContext.setTimeout(10 * 60 * 1000);
		contexts.add(asyncContext);
	}

	@Override
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// URL url = new
		// URL("http://www1.skysports.com/football/match/live/commentary/319160");
		String url = "http://www1.skysports.com/football/match/live/commentary/319160";
		List<AsyncContext> asyncContexts = new ArrayList<>(this.contexts);
		this.contexts.clear();
		Constant.cname = request.getParameter("country");
		Constant.sname = request.getParameter("state");
		Constant.uname = request.getParameter("uid");
		Constant.spname = request.getParameter("sportname");
		Constant.lgname = request.getParameter("leaguename");
		Constant.event = request.getParameter("event");
		Constant.tname = request.getParameter("team");
		Iterator item = null;

		request.getServletContext();

		String htmlMessage = "";
		if (Constant.event.equals("news")) {
			GenerateRDF generate = new GenerateRDF();

			rdf = generate.generateRDF();

			news = new TeamNews(Constant.tname);
			try {
				item = news.readRSSDocument();
			} catch (Exception e) {

				e.printStackTrace();
			}
			while (item.hasNext()) {

				// Iterate over our main elements. Should have one for each
				// article
				SyndEntry items = (SyndEntry) item.next();

				htmlMessage += "<div id='newstext'><hr><b>Title:</b> "
						+ items.getTitle() + "<br><b>Link:</b> "
						+ items.getLink() + "<br><b>Description:</b> "
						+ items.getDescription().getValue()
						+ "<br><b>Publish Date:</b>" + items.getPublishedDate()
						+ "<br><hr></div>";

			}
		}

		if (Constant.event.equals("LiveText")) {
			try {
				String genreJson = IOUtils.toString(new URL(url).openStream());
				new JSONParser();
				JSONObject jsonObject = (JSONObject) JSONValue
						.parseWithException(genreJson);

				// get an array from the JSON object
				JSONArray lang = (JSONArray) jsonObject.get("line");

				// take the elements of the json array
				for (int i = 0; i < lang.size(); i++) {
					System.out.println("The " + i + " element of the array: "
							+ lang.get(i));
				}
				Iterator i = lang.iterator();

				// take each value from the json array separately
				
				while (i.hasNext()) {
					JSONObject innerObj = (JSONObject) i.next();
					System.out.println("Action:" + innerObj.get("line_type")
							+ "Text:" + innerObj.get("text"));
					goal = innerObj.get("line_type").toString();
					comment = innerObj.get("text").toString();
					if (goal.contains("Goal")) {
						for (int j = 0; j <= innerObj.get("text").toString()
								.length(); j++) {
							if (comment.charAt(j) == '!') {

								charCount++;
							}
							
						
						splitstring = innerObj.get("text").toString().substring(charCount-1, charCount);
						 
						}

					}
					htmlMessage += "<div id='livetext'><hr><b>Action:</b> "
							+ innerObj.get("line_type")
							+ "<br><b>Commentery:</b> " + innerObj.get("text")
							+ "<br>" + "<b>Minute:</b> "
							+ innerObj.get("minutes")
							+"<br><b>Goal :</b>"+splitstring+"<br><hr></div>";
				}

			} catch (Exception e) {

			}

		}

		for (AsyncContext asyncContext : asyncContexts) {
			try (PrintWriter writer = asyncContext.getResponse().getWriter()) {

				writer.println(htmlMessage);
				writer.flush();
				asyncContext.complete();
			} catch (Exception ex) {
			}
		}

	}
}