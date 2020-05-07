package Core;
import java.io.IOException;
import java.net.URISyntaxException;
public class jsouptest {
    public static void main(String [] args) throws IOException, URISyntaxException {

        String url = "https://www.nytimes.com/2020/05/02/us/politics/vaccines-coronavirus-research.html?action=click&module=Spotlight&pgtype=Homepage";
        Citation c = new Citation(url);

        System.out.println(c.formatCitation());



//        System.out.println(URLEncoder.encode(url,"UTF-8"));
//
//
//
//
//       String  softtitle = c.getPageTitle();
//       String title = c.getWebsiteTitle();
//       String publisher = c.getPublisher();
//       String releaseDate = c.getReleasedDate();
//      // String authorNamesToCite = c.makeAuthorsFormat(authors);
//       Date accessDate = c.getAccessDate();
//       System.out.println(c.formatCitation());
//        System.out.println(c.formatCitation());
//       Bibliography b = new Bibliography();
//       b.add(c);
//       System.out.println(b.toString());



    }


    }

