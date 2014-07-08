package push;

import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.vocabulary.VCARD;

public class VCARDCUSTOMIZE extends VCARD {
	
	    protected static final String uri ="http://thm.de/#";
	    private static Model m = ModelFactory.createDefaultModel();
	    
	    public static final Property EVENT = m.createProperty(uri, "event" );
	    public static final Property TV =  m.createProperty(uri, "tv_sport" );
	

}
