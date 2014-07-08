package push;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;


import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;



public class TeamNews {
	private String tname;
	public TeamNews(String teamname)
	{
		tname = teamname;
	}
	public Iterator readRSSDocument() throws Exception{
	    System.out.println("Team Name"+tname.toLowerCase());
	    
	    URL url = new URL("http://www.bbc.com/sport/football/teams/"+tname.toLowerCase()+"/rss.xml");
        HttpURLConnection httpcon = (HttpURLConnection)url.openConnection();
        // Reading the feed
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(httpcon));
        List entries = feed.getEntries();
        Iterator itEntries = entries.iterator();
	    
	  
	      return itEntries;
	    
	}
}
