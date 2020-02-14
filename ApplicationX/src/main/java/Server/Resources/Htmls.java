package Server.Resources;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Htmls {
    private String [] html;
    public Htmls(){

    }

    public String[] getHtmls() {
        return html;
    }

    public void setHtmls(String[] html) {
        this.html = html;
    }
}
