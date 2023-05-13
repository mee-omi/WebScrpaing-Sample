package webscraper;

import com.gargoylesoftware.htmlunit.*;
import com.gargoylesoftware.htmlunit.html.*;
import java.io.IOException;
import java.util.List;
import java.io.FileWriter;

public class WebScraper {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// WebClient webClient = new WebClient(BrowserVersion.CHROME);

		try (WebClient webClient = new WebClient(BrowserVersion.CHROME);
			FileWriter blogsFile = new FileWriter("blogposts.csv", false);) {
			
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setPrintContentOnFailingStatusCode(false);
			HtmlPage page = webClient.getPage("https://illusiveperceptions.wordpress.com/");
			blogsFile.write("id,Title,link\n");
			webClient.getCurrentWindow().getJobManager().removeAllJobs();
			System.out.println("Printing out page details");
			System.out.println("-----------------------------------");
			String title = page.getTitleText();
			System.out.println("Page Title: " + title);

			List<HtmlAnchor> links = page.getAnchors();
			
			for (HtmlAnchor link : links) {
				String href = link.getHrefAttribute();
				//System.out.println("Link: " + href);
			}

			// @used for attributes	
			List<?> anchors = page.getByXPath("//a[@target='_self']");
			for (int i = 0; i < anchors.size(); i++) {
				HtmlAnchor link = (HtmlAnchor) anchors.get(i);
				//String blogTitle = link.getAttribute("title").replace(',', ';');
				String blogTitle = link.getVisibleText();
				String blogLink = link.getHrefAttribute();
				//System.out.println(link.getTextContent());
				/*
				 * System.out.println("post title :" + blogTitle);
				 * System.out.println("post link :" + blogLink);
				 */
				blogsFile.write(i + "," + blogTitle + "," + blogLink + "\n");
			}

			System.out.println("-----------------------------------");
			System.out.println("Before closing web client");
			
			// webClient.close();
			// recipesFile.close();
			System.out.println("reached here");

		} catch (IOException e) {
			System.out.println("An error occurred: " + e);
		}
	}

}
