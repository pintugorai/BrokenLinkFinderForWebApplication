package demo1;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestClass2 {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64)";
	private static final int MAX_PAGES_VISITED = 200;
	private static final String BASE_URL="http://www.mitramain.com/";    //base url you want to visit
	private static final String CURRENT_URL="http://www.mitramain.com/"; // url of the root page
	
	static HashSet<String> pageVisited = new HashSet<String>();
	static HashSet<String> pageToVisit = new HashSet<String>();
	static HashSet<String> brokenLink = new HashSet<String>();

	public static void main(String[] args) throws InterruptedException, IOException {
		
		/*Document doc = loginToApplication();
		Elements links = doc.select("a[href]");
		for (Element link : links) {

			String getUrl = link.attr("abs:href").toString().trim().toLowerCase();
		System.out.println("getUrl = " + getUrl);	
		}*/
		
		traversAwebSites();

	}
	
	public static void traversAwebSites() throws InterruptedException
	{
		try {
			//String current_URL = "http://www.mitramain.com/";
			if (pageToVisit.isEmpty()) {
				printAllLinks1(CURRENT_URL);
			} else {

			}
			Iterator<String> itr = pageVisited.iterator();
			System.out.println("Visited list : :::::::::::::::::::::::::::::::::");
			int count = 0;
			while (itr.hasNext()) {
				
				System.out.println("Count = " + ++count + "     " + itr.next());
			}
			
			// Printing broken links
			Iterator<String> itrBroken = brokenLink.iterator();
			System.out.println("No of Broken Link = " + brokenLink.size());
			System.out.println("Broken list : :::::::::::::::::::::::::::::::::");
			int c = 0;
			while (itrBroken.hasNext()) {
				
				System.err.println("Count-b = " + ++c + "     " + itrBroken.next());
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printAllLinks1(String url) throws IOException, InterruptedException {

		if ((!pageVisited.contains(url)) && url.startsWith(BASE_URL) && (pageVisited.size() <=MAX_PAGES_VISITED)) {
			Connection conn = Jsoup.connect(url);
			try {
			Document doc2 = conn.get();
			getPageDetails(doc2);
			
			int statusCode = conn.response().statusCode();
			if (statusCode == 200) {
				System.out.println("Target URL = " + url);
				Elements links = doc2.select("a[href]");

				pageVisited.add(url.trim().toLowerCase());
				Thread.sleep(1000);

				for (Element link : links) {

					String getUrl = link.attr("abs:href").toString().trim().toLowerCase();
					if (pageVisited.contains(getUrl)) {
						continue;
					} else {
						if (getUrl.startsWith(BASE_URL)) {
							pageToVisit.add(getUrl);
							printAllLinks1(getUrl);
						}
					}
				}

			}
			} catch(Exception e)
			{
				System.err.println("broken link = " + url);
				brokenLink.add(url);
			}

		}

	}
	
	
	public static void getPageDetails(Document doc)
	{
		String title = doc.title();
		Elements links = doc.select("a[href]");
		int totalLinks = links.size();
		
		Elements images = doc.select("img[src~=(?i)\\.(png|jpe?g|gif)]"); 
		int totalImages = images.size();
		
		int textBoxes = doc.getElementsByTag("input").size();
		
		System.out.println("\n\n title = " + title);
		System.out.print(" totalLinks = " + totalLinks);
		System.out.print(" totalImages = " + totalImages );
		System.out.print(" textBoxes = " + textBoxes);
		
		
	}
	
	public static Document loginToApplication() throws IOException
	{
		Properties prop=new Properties();
		FileInputStream fis = new FileInputStream(".//config//config.properties");
		prop.load(fis);
		String username = prop.getProperty("username");
		String password = prop.getProperty("password");
		String url = prop.getProperty("url");
		
		System.out.print(prop.getProperty("username"));
		System.out.print(prop.getProperty("password"));
		System.out.print(prop.getProperty("url"));
		
	/*	Document doc = Jsoup.connect(url).get();
		doc.getElementById("username").attr("value", username);
		doc.getElementById("password").attr("value", password);
		//doc.getElementById("loginBtn").
*/		
		//Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
	    Connection connection = (Connection) Jsoup.connect( url )
		            		.data("username", username).data("password", password).post();
		            Document doc = connection.get();
		            return doc;
		
	}

}