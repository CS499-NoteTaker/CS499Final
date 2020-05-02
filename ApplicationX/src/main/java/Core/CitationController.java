package Core;

import java.io.IOException;
import java.util.ArrayList;

public class CitationController {
    private Citation c;
    private Bibliography b;

    public CitationController()  {

    }

    public Citation scrapeUrl(String url) throws IOException {
        c = new Citation(url);
        System.out.println(c.formatCitation());
        return c;

    }

    public Bibliography getBib(ArrayList<Citation> citation){
        b = new Bibliography();
        for(int i =0;i<citation.size();i++){
            b.add(citation.get(i));
        }
        return b;
    }

    public String bibToString(){
        return b.toString();
    }
}
