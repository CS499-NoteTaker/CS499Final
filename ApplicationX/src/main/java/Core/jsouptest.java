package Core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class jsouptest {
    public static void main(String [] args) throws IOException {

        String url = "https://www.foxnews.com/media/biden-camp-reportedly-pushing-ny-times-report-as-talking-point-to-combat-tara-reade";
        Citation c = new Citation(url);


       ArrayList<String> authors = (ArrayList<String>)c.getAuthorNames();
       String  softtitle = c.getPageTitle();
       String title = c.getWebsiteTitle();
       String publisher = c.getPublisher();
       String releaseDate = c.getReleasedDate();
        System.out.println("Length of author list: "+authors.size());
       String authorNamesToCite = c.makeAuthorsFormat(authors);
       Date accessDate = c.getAccessDate();
       System.out.println(c.formatCitation());
       Bibliography b = new Bibliography();
       b.add(c);
       System.out.println(b.toString());



    }


    }

