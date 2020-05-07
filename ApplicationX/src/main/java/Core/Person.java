package Core;

import io.github.cdimascio.essence.EssenceResult;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class Person {


    private String firstName;

    private String lastName;
    final private String dateformat = "yyyy-MM-dd'T'HH:mm:ssZ";
    private String id;
    private String url;
    private EssenceResult es;
    private Document d;



    public Person(){

    }

    public Person(String firstName,String lastName,String url) throws IOException {
        d = Jsoup.connect(url).get();


        this.firstName = firstName;
        this.lastName = lastName;


    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }


    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getId(){
        return id;
    }
}
