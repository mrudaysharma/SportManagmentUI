package push;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.syndication.feed.synd.SyndEntry;



@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/shoutServlet" }, asyncSupported = true)
public class ShoutServlet extends HttpServlet {
	private List<AsyncContext> contexts = new LinkedList<>();
	private TeamNews news;
	private String rdf;

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

		GenerateRDF generate = new GenerateRDF();

		rdf = generate.generateRDF();
		news = new TeamNews(Constant.tname);
		try {
			item = news.readRSSDocument();
		} catch (Exception e) {
			
			e.printStackTrace();
		}

		String htmlMessage = "";
		while (item.hasNext()) {

			// Iterate over our main elements. Should have one for each article
			SyndEntry items = (SyndEntry) item.next();

			htmlMessage += "<hr><b>Title:</b> " + items.getTitle()
					+ "<br><b>Link:</b> " + items.getLink()
					+ "<br><b>Description:</b> "
					+ items.getDescription().getValue() + "<br><hr>";

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