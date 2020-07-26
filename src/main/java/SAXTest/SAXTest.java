package SAXTest;

import java.io.InputStream;
import java.net.URL;


import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

public class SAXTest {

	public static void main(String[] args) throws Exception {
		String url;
		if (args.length == 0) {
			url = "http://www.w3c.org";
			System.out.println("Using" + url);
		}
		else {
			url = args[0];
		}
		DefaultHandler handler = new DefaultHandler() {
			 
			public void startElement(String namespaceURI,String lname, String qname, Attributes attrs) {
				
				if (lname.equals("a") && attrs!=null) {
					for (int i = 0; i < attrs.getLength(); i++) {
						String aname = attrs.getLocalName(i);
						if (aname.equals("href")) {
							System.out.println(attrs.getValue(i));
							
						}
					}
				}
			}
		};
		SAXParserFactory factory = SAXParserFactory.newInstance();
		factory.setNamespaceAware(true);
		factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
		SAXParser saxParser = factory.newSAXParser();
		InputStream in = new URL(url).openStream();
		saxParser.parse(in, handler);
		
		
		
		
		

	}

}
