package demo1;

import java.net.URL;

import javax.lang.model.element.Element;
import javax.lang.model.util.Elements;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;

public class Task1 {
	private static final String USER_AGENT = "Mozilla/55.0 (Windows 10; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public static void main(String S[]) {
		//demoMethod2();
		
		String url = "http://global.ingrammicrocloud.com/in/wp-content/plugins/revslider/admin/assets/images/dummy.png";
		System.out.println("Status != 200, code = " + Jsoup.connect(url).response().statusCode());
	}

	public static void demoMethod1() {
		try {
			String url = "http://www.ingrammicrocloud.in/";
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();
			System.out.println("Status code = " + connection.response().statusCode());

			int idCount = htmlDocument.getElementsByAttribute("id").size();
			for (int i = 0; i < idCount; i++) {
				// System.out.println(i + " - " +
				// htmlDocument.getElementsByAttribute("id").get(i));
			}
			// System.out.println("num of images : " + countOfAllImages);
			System.out.println("Page Title " + htmlDocument.title());

			System.out.println("Encoding version of the page = " + htmlDocument.charset());
			System.out.println("Encoding version of the page = " + htmlDocument.title());
			System.out
					.println("icon = " + htmlDocument.getElementsByAttributeValue("rel", "shortcut icon").attr("href"));

			// get image size
			System.out.println("IMG = " + new URL(
					"http://www.ingrammicrocloud.in/wp-content/uploads/sites/50/2014/09/IM-Logo-topnavFINAL.png")
							.openConnection().getContentLength()
					+ "KB");
			System.out.println("favicon = "
					+ new URL("http://www.ingrammicrocloud.in/wp-content/uploads/sites/50/2014/09/favicon-2.png")
							.openConnection().getContentLength()
					+ "KB");

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// pb- new 38 - take from pb
	public static void demoMethod2() {
		/*
		 * 1. Open a page 2. Check the count of image 3. Check for any broken image 4.
		 * check if all images has alt_text 5. Check all images dimention
		 */
		try {
			String url = "http://www.ingrammicrocloud.in/";
			Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
			Document htmlDocument = connection.get();

			// image count
			int imgCount = htmlDocument.getElementsByTag("img").size();
			System.out.println("imgCount = " + imgCount);

			// Broken image
			int count = 0;
			for (int i = 0; i < imgCount; i++) {
				System.out.println("hi");
				String urlImg = htmlDocument.getElementsByTag("img").get(i).attr("src").toString();
				System.out.println("urlImg = " + urlImg);
				/*System.out.println("urlImg = " + urlImg);
				Connection conn = Jsoup.connect(urlImg);
				Document doc = conn.get();
				System.out.println("doc = " + conn.response().statusCode());*/
				if (Jsoup.connect(urlImg).response().statusCode() == 200) {
					System.out.println("Status = 200");
					count++;
				}
				else
				{
					System.out.println("Status != 200, code = " + Jsoup.connect(urlImg).response().statusCode());
				}
			}
			System.out.println("count = " + count);

		} catch (Exception e) {
			System.out.println("exception message = " + e.getMessage());
			System.out.println("exception = " + e);
		}
	}

	public static void demoMethod3() {
		/*
		 * I want to know total number of unique linkes in a given website in same
		 * domain. Input : url
		 * 
		 */
	}
}