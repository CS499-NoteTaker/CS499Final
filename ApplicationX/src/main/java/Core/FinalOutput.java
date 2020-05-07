package Core;

public class FinalOutput {
    private String [] ht;
    private CitationObjects [] citationObjects;

    public FinalOutput(){

    }

    public String [] getHt(){
        return this.ht;
    }

    public void setHt(String [] ht){
        this.ht = ht;
    }

    public void setCitObjs(CitationObjects [] citationObjects){
        this.citationObjects = citationObjects;
    }

    public CitationObjects [] getCitObjs(){
        return this.citationObjects;
    }
}
