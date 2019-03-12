package demo1;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestClass1 {
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64)";
	private static final int MAX_PAGES_VISITED = 200;
	
	static HashSet<String> pageVisited = new HashSet<String>();
	static HashSet<String> pageToVisit = new HashSet<String>();

	public static void main(String[] args) throws InterruptedException {
		/*
		 * Deep Copy Shallow Copy check endcoding version of the page.
		 */

		try {
			/*
			 * Document doc =
			 * Jsoup.connect("https://www.javatpoint.com/jsoup-examples").get();
			 * System.out.println(doc.title());
			 * 
			 * // all links in the page Elements links = doc.select("a[href]");
			 * 
			 * System.out.println("links = " + links.size());
			 * 
			 * for (Element link : links) { System.out.println("Link = " +
			 * link.attr("abs:href").toString()); Document doc2 =
			 * Jsoup.connect(link.attr("abs:href").toString()).get(); }
			 */

			// printAllLinks1("https://www.javatpoint.com/jsoup-examples");
			String current_URL = "https://www.javatpoint.com/jsoup-examples";
			if (pageToVisit.isEmpty()) {
				printAllLinks1(current_URL);
			} else {

			}
			Iterator<String> itr = pageVisited.iterator();
			System.out.println("Visited list : :::::::::::::::::::::::::::::::::");
			int count = 0;
			while (itr.hasNext()) {
				
				System.out.println("Count = " + count++ + "     " + itr.next());
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void printAllLinks1(String url) throws IOException, InterruptedException {

		if ((!pageVisited.contains(url)) && url.startsWith("https://www.javatpoint.com/") && (pageVisited.size() <=MAX_PAGES_VISITED)) {
			Connection conn = Jsoup.connect(url);
			System.out.println("Target URL = " + url);
			Document doc2 = conn.get();
			////System.out.println("Conn status = " + conn.response().statusCode());
			if (conn.response().statusCode() == 200) {

				Elements links = doc2.select("a[href]");

				pageVisited.add(url.trim().toLowerCase());
				////System.out.println("Size = ==================== " + links.size() + "\n\n\n\n");
				Thread.sleep(1000);

				for (Element link : links) {

					String getUrl = link.attr("abs:href").toString().trim().toLowerCase();
					////System.out.println("Link = " + getUrl);
					if (pageVisited.contains(getUrl)) {
						////System.out.println("continue");
						continue;
					} else {
						////System.out.println("Add1");

						if (getUrl.startsWith("https://www.javatpoint.com/")) {
							////System.out.println("Add2");
							////System.out.println("Link -============ " + getUrl);
							pageToVisit.add(getUrl);
							printAllLinks1(getUrl);
							////System.out.println("=========Add21");
						}
					}
				}

			}

		}

	}

	public static void printAllLinks(String url) throws IOException {

		if (pageToVisit.isEmpty()) {
			pageToVisit.add(url);

		} else {
			// System.out.println("url = " + url);
			Document doc = Jsoup.connect(url).get();
			Elements links = doc.select("a[href]");
			pageToVisit.add(url);

			for (Element link : links) {
				// printAllLinks(link.attr("abs:href").toString());
				// allLinksInWebsite.add(link.attr("abs:href").toString());
				pageToVisit.add(link.attr("abs:href").toString());
			}
		}
	}
}
