package Server.Resources;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
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

