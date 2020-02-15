package Server.Resources;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Htmls {
    private String [] html;

    public void getHtmls() {
        for(int i = 0;i <html.length;i++){
            System.out.print(html[i]);
        }
    }

    public String [] getArray(){
        return html;
    }

    public void setHtmls(String[] html) {
        this.html = html;
    }

}

