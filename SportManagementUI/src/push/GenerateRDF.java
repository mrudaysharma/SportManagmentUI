package push;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.Statement;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.sparql.pfunction.library.concat;
import com.hp.hpl.jena.vocabulary.VCARD;

public class GenerateRDF {
	// some definitions
	static String sportURI;
	static String fullName;
	public String country = Constant.cname;
	public String user = Constant.uname;
	public String watching = "WATCH_TV";
	public String sport_event = Constant.spname;
	public String watching_tv = "Event";

	public String league_name = Constant.lgname;
	public String team_name = Constant.tname;
	public String user_currently_seeing =Constant.event; 
	public String returnString;
	String nsA = "http://country/#";
	String nsB = "http://country/name/#";
	String nsc = "http://country/user/tv#";
	String nsd = "http://country/user/tv/sport#";

	// create an empty Model
	Model m;

	// create the resource
	Resource sportrdf;

	public String generateRDF() {
		sportURI = "http://thm.de/#";
		m = ModelFactory.createDefaultModel();

		Resource COUNTRY = m.createResource(nsA + country);
		Property USER = m.createProperty(nsB + "APP_USER");
		Resource USER_ID = m.createResource(nsB + user);
		Property WATCHING_TV = m.createProperty(nsB + "WATCHING_TV");
		Resource TV = m.createResource(nsB + "EVENT");
		Property EVENT = m.createProperty(nsc + "EVENT_NAME");
		Resource EVENT_NAME = m.createResource(nsc + sport_event);
		Property LEAGUE = m.createProperty(nsc + "LEAGUE_NAME");
		Resource LEAGUE_NAME = m.createResource(nsd + league_name);
		Property FOLLOW_BY = m.createProperty(nsc + "FOLLOW_BY_APP_USER");
		Property TEAM_NAME = m.createProperty(nsc + "TEAM_NAME");
		Property FOLLOW_TEAM = m.createProperty(nsc + "FOLLOW_TEAM");
		Resource TEAM = m.createResource(nsd + team_name);
		Property TEAM_INFORMATION = m.createProperty(nsd+"TEAM_INFORMATION");
		Resource INFORMATION = m.createResource(nsd + user_currently_seeing);
		
		
		// Resource USER_FAV = m.createResource(nsB+team_name);

		StringWriter outwrite = new StringWriter();

		m.add(COUNTRY, USER, USER_ID).add(USER_ID, WATCHING_TV, TV)
				.add(TV, EVENT, EVENT_NAME).add(EVENT_NAME,LEAGUE,LEAGUE_NAME)
				.add(LEAGUE_NAME,TEAM_NAME,TEAM).add(TEAM,FOLLOW_BY,USER_ID)
				.add(TEAM,TEAM_INFORMATION,INFORMATION);
			
		// m.add(COUNTRY,USER,USER_ID) ;
		System.out.println("# -- no special prefixes defined");

		m.write(outwrite, "RDF/XML-ABBREV");

		System.out.println(outwrite.toString());
		return outwrite.toString();
	}
}
