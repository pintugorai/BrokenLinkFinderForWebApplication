package demo1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestClass3 {
	
	
	private static int MAX_PAGES_VISITED;// = 500;
	private static String BASE_URL;// = "http://www.mitramain.com/";
	private static String CURRENT_URL;// = "http://www.mitramain.com/";
	
	static HashSet<String> pageVisited = new HashSet<String>();
	static HashSet<String> pageToVisit = new HashSet<String>();
	static HashSet<String> brokenLink = new HashSet<String>();
	static HashSet<String> othersSitesLink = new HashSet<String>();
	
	

	public static void main(String[] args) throws InterruptedException, IOException {
		
		System.out.println("Please wait....");
		TestClass3 caller = new TestClass3();
		caller.innitiator();
		caller.traversAwebSites();
		
		System.out.println("Total broken link: " + brokenLink.size());
		System.out.println("Total visited Page link: " + pageVisited.size());
		System.out.println("Total others Sites link: " + othersSitesLink.size());
		
		caller.printLists(brokenLink, "Broken Links");
		caller.printLists(pageVisited, "Visited Pages");
		caller.printLists(othersSitesLink, "Others Sites Links");
		caller.printLists(pageToVisit, "pageToVisit");
		System.out.println("Successfully completed!!!!");
	}

	private void innitiator() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream(System.getProperty("user.dir") + "\\src\\demo1\\Config.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MAX_PAGES_VISITED=Integer.parseInt(prop.getProperty("MAX_PAGES_VISITED"));
		BASE_URL=prop.getProperty("BASE_URL");
		CURRENT_URL=prop.getProperty("CURRENT_URL");
		//System.out.println("BASE_URLBASE_URL= " + BASE_URL);
				
	}

	private void printLists(HashSet<String> list, String message) {
		System.out.println("###########################################");
		System.out.println("############### Starting..#################");
		System.out.println("List of " + message +"[" + list.size()+"]");
		if(list.size()>0) {
		Iterator<String> alllist = list.iterator();
		int c = 0;
		while (alllist.hasNext()) {
			String s=alllist.next();
			System.out.println("Count = [" + ++c + "]: " + s);
		}
		
		}
		else
		{
			System.out.println("No sites found");
		}
		
		System.out.println("##################  Done. #################");
		System.out.println("###########################################");
		System.out.println();
		System.out.println();
		
	}

	public void traversAwebSites() throws InterruptedException {
		try {
			if (pageToVisit.isEmpty()) {
				printAllLinks1(CURRENT_URL);
			} else {

			}
			// Printing broken links
			/*Iterator<String> itrBroken = brokenLink.iterator();
			int c = 0;
			while (itrBroken.hasNext()) {

				System.err.println("Count = " + ++c + "     " + itrBroken.next());
			}*/

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void printAllLinks1(String url) throws IOException, InterruptedException {
		
		if(!url.startsWith(BASE_URL))
		{
			if(!othersSitesLink.contains(url.trim().toLowerCase()))
			{
				System.out.println("jhkjjkjljkjl: " + url);
				othersSitesLink.add(url.trim().toLowerCase());
			}
		}

		if ((!pageVisited.contains(url)) && url.startsWith(BASE_URL) && (pageVisited.size() <= MAX_PAGES_VISITED)) {
			Connection conn = Jsoup.connect(url);
			try {
				Document doc2 = conn.get();
				int statusCode = conn.response().statusCode();
				if (statusCode == 200) {
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
							}else
							{
								if(!othersSitesLink.contains(getUrl.trim().toLowerCase()))
								{
									othersSitesLink.add(getUrl.trim().toLowerCase());
								}
							}
						}
					}

				}
			} catch (Exception e) {
				brokenLink.add(url);
			}

		}

	}

}