package demo1;

import java.io.IOException;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class Rough {
	private static final String USER_AGENT = "Mozilla/55.0 (Windows 10; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/13.0.782.112 Safari/535.1";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String url = "http://10.10.3.83:8080/escm/login/auth";
		
		Connection connection = Jsoup.connect(url).userAgent(USER_AGENT);
		try {
			//System.out.println("adsfasdf = " + connection.execute().statusCode());
			Document htmlDocument = connection.get();
			//htmlDocument.getElementById("id").
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(url);
			//e.printStackTrace();
			
		}
		System.out.println("Status code = " + connection.response().statusCode());
		
	}

}
