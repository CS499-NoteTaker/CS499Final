package Core;
import io.github.cdimascio.essence.Essence;
import io.github.cdimascio.essence.EssenceResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Citation MLA format for websites
 * Author's Last name, First name. "Title of Individual Web Page." Title of Website, Publisher, Date, URL.
 */
public class Citation implements Serializable {
    private ArrayList<String> authorNames;
    private String pageTitle;
    private String websiteTitle;
    private String publisher;
    private String releasedate;
    private String url;
    private Date accessdate;
    
    private EssenceResult data;
    private Document doc;
    public Citation(String url) throws IOException {
        doc = Jsoup.connect(url).get();
        data = Essence.extract(doc.html());

        this.authorNames = scrapeAuthorNames();
        this.pageTitle = data.getSoftTitle();
        this.websiteTitle = data.getTitle();
        this.publisher = data.getPublisher();
        this.releasedate = data.getDate();
        this.url = url;
        this.accessdate = getAccessDate();
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

    public String getPageTitle(){

        return data.getSoftTitle();
    }

    public String getWebsiteTitle(){

        return doc.title();
    }

    public String getPublisher(){

        return data.getPublisher();
    }

    public String getReleasedDate(){

        return data.getDate();

    }

    public Date getAccessDate(){
        Date date = new Date();

        return date;
    }

    //for UI
    public String formatCitation(){
        String names = makeAuthorsFormat(getAuthorNames());
        String pt = getPageTitle();
        String wt = getWebsiteTitle();
        String pb = getPublisher();
        String rd = getReleasedDate();
        String ul = getUrl();
        Date ad = getAccessDate();


        if(!pt.equals("")) pt = "\""+pt+"\". ";
        if(!wt.equals("")) wt = "\""+wt+"\". ";
        if(!pb.isEmpty()) pb = pb+". ";
        if(!rd.equals(""))  rd = rd+". ";
        ul = ul+". ";
        String d = ad+". ";
        String s;

        s = names+pt+wt+pb+rd+ul+d;
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
