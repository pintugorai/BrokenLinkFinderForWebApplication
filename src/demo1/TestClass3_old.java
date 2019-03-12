package demo1;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TestClass3_old {
	private static final int MAX_PAGES_VISITED = 200;
	private static final String BASE_URL = "http://www.mitramain.com/";
	private static final String CURRENT_URL = "http://www.mitramain.com/";

	static HashSet<String> pageVisited = new HashSet<String>();
	static HashSet<String> pageToVisit = new HashSet<String>();
	static HashSet<String> brokenLink = new HashSet<String>();
	
	public static void main(String[] args) throws InterruptedException, IOException {

		traversAwebSites();

	}

	public static void traversAwebSites() throws InterruptedException {
		try {
			if (pageToVisit.isEmpty()) {
				printAllLinks1(CURRENT_URL);
			} else {

			}
			// Printing broken links
			Iterator<String> itrBroken = brokenLink.iterator();
			int c = 0;
			while (itrBroken.hasNext()) {

				System.err.println("Count = " + ++c + "     " + itrBroken.next());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void printAllLinks1(String url) throws IOException, InterruptedException {
		
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