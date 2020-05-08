package Core;

import io.github.cdimascio.essence.Essence;
import io.github.cdimascio.essence.EssenceResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

import static java.time.format.DateTimeFormatter.ISO_ZONED_DATE_TIME;

/**
 * Citation MLA format for websites
 * Author's Last name, First name. "Title of Individual Web Page." Title of Website, Publisher, Date, URL.
 */


public class Citation implements Serializable {


    private String pageTitle;
    private String websiteTitle;
    private String publisher;
    private String releasedDate;
    private String url;
    private String accessDate;
    private ArrayList<String> authorNames;

    final private String dateformat = "yyyy-MM-dd'T'HH:mm:ssZ";
    private EssenceResult data;

    private Document doc;

    public Citation() throws IOException{

    }

    public Citation(String url) throws IOException {
        doc = Jsoup.connect(url).get();
        data = Essence.extract(doc.html());
        this.authorNames = scrapeAuthorNames();
        this.pageTitle = data.getSoftTitle();
        this.websiteTitle = data.getTitle();
        this.publisher = data.getPublisher();
        this.releasedDate = data.getDate();
        this.url = url;


        try {
            // Gets the format from what the HTML brings and converts it to new time format: "yyyy-MM-dd'T'HH:mm:ssZ"
            ZonedDateTime d = ZonedDateTime.parse(releasedDate, ISO_ZONED_DATE_TIME);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateformat).withZone(ZoneId.of("UTC"));
            this.releasedDate = d.format(dtf);
        } catch (Exception e) {
            System.out.println("Error: ReleaseDate field was the following: \"" + releasedDate + "\"");
            releasedDate = "";
        }
        //Converts time zone format to "yyyy-MM-dd'T'HH:mm:ssZ"
        SimpleDateFormat sdf = new SimpleDateFormat(dateformat);
        this.accessDate = sdf.format(new Date());
    }

    public void setUrl(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public ArrayList<String> getAuthorNames() {
        return this.authorNames;
    }

    public void setAuthorNames(ArrayList<String> authorNames){
        this.authorNames = authorNames;
    }

    public String getPageTitle(){
        return this.pageTitle;
    }

    public void setPageTitle(String pageTitle){
        this.pageTitle = pageTitle;
    }

    public String getWebsiteTitle(){
        return this.websiteTitle;
    }

    public void setWebsiteTitle(String websiteTitle){
        this.websiteTitle = websiteTitle;
    }

    public String getPublisher(){
        return this.publisher;
    }

    public void setPublisher(String publisher){
        this.publisher = publisher;
    }

    public String getReleasedDate(){
        return this.releasedDate;
    }

    public void setReleasedDate(String releasedDate){
        this.releasedDate = releasedDate;
    }

    public String getAccessDate(){
        return this.accessDate;
    }

    public void setAccessDate(String accessDate){
        this.accessDate = accessDate;
    }
    private ArrayList<String> scrapeAuthorNames(){
        //data.getAuthor is only returning single element with all author names.
        ArrayList<String> finalAuthorList = new ArrayList<>();
        ArrayList<String> templist = (ArrayList<String>)data.getAuthors();

        if(templist.isEmpty()){
            ArrayList<String> dummy = new ArrayList<>();
            dummy.add("");
            return dummy;
        }
        String temp;
        temp = templist.get(0).replaceAll(" and ",", ");
        temp = temp.replaceAll(", and ",", ");
        temp = temp.replaceAll(" & ",", ");

        String [] permanentList;
        permanentList = temp.split(", ");
        for(int i=0;i<permanentList.length;i++) {
            finalAuthorList.add(permanentList[i]);
        }

        for(int i = 0;i < finalAuthorList.size();i++){
            System.out.println("author name: "+finalAuthorList.get(i));
        }
        ArrayList<String> dataList = new ArrayList<>();

        for(String author:finalAuthorList){
            if(author.startsWith("By ")){
                author = author.replace("By ","");
            }
            author = author.replaceAll("\\<.*?\\>", "");
            System.out.println("Before adding author: "+author);
            String [] testNameList = author.split(" ");
            if(testNameList.length>=2 && testNameList.length<=3){

                dataList.add(author);
            }
        }
        for(int i = 0;i < dataList.size();i++){
            System.out.println("author finallist:"+dataList.get(i) );
        }

        return dataList;
    }

    public String getDay(String date){
        if(!date.isEmpty() || !date.equals("")){
            return date.substring(8,10);
        }
        else return "";

    }

    public String getMonth(String date){
        if(!date.isEmpty() || !date.equals("")){
            String s = date.substring(5,7);
            if(s.equals("01")) return "Jan";
            else if(s.equals("02")) return "Feb";
            else if(s.equals("03")) return "Mar";
            else if(s.equals("04")) return "Apr";
            else if(s.equals("05")) return "May";
            else if(s.equals("06")) return "Jun";
            else if(s.equals("07")) return "Jul";
            else if(s.equals("08")) return "Aug";
            else if(s.equals("09")) return "Sept";
            else if(s.equals("10")) return "Oct";
            else if(s.equals("11")) return "Nov";
            else if(s.equals("12")) return "Dec";
            else return "";
        }
        return "";


    }

    public String getYear(String date){
        if(!date.isEmpty() || !date.equals("")){
            return date.substring(0,4);
        }
        else return "";

    }

    public String formatCitation(){
        System.out.println(this.authorNames);
        String names = makeAuthorsFormat(this.authorNames);

        String pt =this.pageTitle;
        String wt = this.websiteTitle;
        String pb = this.publisher;
        String rd="";
        if(!this.releasedDate.equals("")){
            rd = getDay(this.releasedDate)+" "+getMonth(this.releasedDate)+" "+getYear(this.releasedDate);
        }
        String ul = this.url;
        String ad = getDay(this.accessDate)+" "+getMonth(this.accessDate)+" "+getYear(this.accessDate);
        System.out.println("access date:"+ad);




        if(!pt.equals("")) pt = "\""+pt+"\". ";

        if(!wt.equals("")) wt = "\""+wt+"\". ";

        if(!pb.equals("")) pb = pb+", ";

        if(!rd.equals(""))  rd = rd+", ";

        ul = ul+". ";
        //System.out.println(ul);

        String d = ad+". ";
        System.out.println(d);
        String s="";

        s = names+pt+wt+pb+rd+ul+d;
       System.out.println(s);

        return s;
    }

    /**
     *
     * make switch or if statements to take care of multiple cases: N/A cases
     */

    private String makeAuthorsFormat(ArrayList<String> authorList){

        String s = "";
        if(authorList.isEmpty() || authorList.get(0)==""){
            return "";
        }
        System.out.println(authorList.size());
        for(int i=0;i<authorList.size();i++){
            String temp = authorList.get(i);
            System.out.println("Author: "+temp);
            String [] nameToken = temp.split(" ");
            String lastName = nameToken[nameToken.length-1];
            String firstName = nameToken[0].substring(0,1);
            if(i == authorList.size()-2){
                s += lastName+", "+firstName+"., & ";
            }
            else if(i == authorList.size()-1){
                s += lastName+", "+firstName+". ";
            }
            else{
                s += lastName+", "+firstName+"., ";
            }
        }

        return s;
    }


    public String addNumbertoCitation(int num, Citation citation) {
        return +(num+1) + ". " + "   " + citation.formatCitation();
    }

}
