package Core;
import io.github.cdimascio.essence.Essence;
import io.github.cdimascio.essence.EssenceResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Citation MLA format for websites
 * Author's Last name, First name. "Title of Individual Web Page." Title of Website, Publisher, Date, URL.
 */
public class Citation {
    String authorNames;
    String pageTitle;
    String websiteTitle;
    String publisher;
    String releasedate;
    String url;
    Date accessdate;

    EssenceResult data;
    Document doc;
    public Citation(String url) throws IOException{
        this.url = url;
        doc = Jsoup.connect(url).get();
        data = Essence.extract(doc.html());
    }



    public List<String> getAuthorNames(){
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
        pageTitle = data.getSoftTitle();
        return data.getSoftTitle();
    }

    public String getWebsiteTitle(){
        websiteTitle = doc.title();
        return doc.title();
    }

    public String getPublisher(){
        publisher = data.getPublisher();
        return data.getPublisher();
    }

    public String getReleasedDate(){
        releasedate = data.getDate();
        return data.getDate();

    }

    public Date getAccessDate(){
        Date date = new Date();
        accessdate = date;
        return date;
    }


    public String formatCitation(){
        String pt = pageTitle;
        String wt = websiteTitle;
        String pb = publisher;
        String rd = releasedate;
        String ul = url;
        Date ad = accessdate;

        if(!pt.equals("")) pt = "\""+pt+"\". ";
        if(!wt.equals("")) wt = "\""+wt+"\". ";

        pb = pb+". ";
        if(!rd.equals(""))  rd = rd+". ";
        ul = ul+". ";
        String d = ad+". ";
        String s;

        s = authorNames+pt+wt+pb+rd+ul+d;
        return s;
    }

    /**
     *
     * make switch or if statements to take care of multiple cases: N/A cases
     */

    public String makeAuthorsFormat(ArrayList<String> authorList){

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

        authorNames = s;
        return s;
    }

    public String addNumbertoCitation(int num, Citation citation) {
        return +(num+1) + ". " + "   " + citation.formatCitation();
    }



}
