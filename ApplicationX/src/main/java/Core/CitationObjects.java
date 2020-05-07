package Core;

public class CitationObjects {
    private int index;
    private Citation citation;
    public CitationObjects(){

    }

    public int getIndex(){
        return this.index;
    }

    public void setIndex(int index){
        this.index = index;
    }

    public void setCitation(Citation citation){
        this.citation = citation;
    }

    public Citation getCitation(){
        return this.citation;
    }
}
