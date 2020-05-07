package Core;

import java.util.ArrayList;

/** need to put citation numbers to the paragraphs that was cited. like this =>e.g. this is that[1].
 **/

public class Bibliography {
    private ArrayList<Citation> citationList ;
    private String bibString;
    public Bibliography(){
        citationList = new ArrayList<>();
    }
    public void add(Citation citation){
        citationList.add(citation);
    }

    public ArrayList<Citation> getCitationList(){
        return this.citationList;
    }

    public void setCitationList(ArrayList<Citation> citationList){
        this.citationList = citationList;
    }

    public void delete(int index){
        citationList.remove(index);
    }

    public Citation getCitationByIndex(int index){
        return citationList.get(index);
    }

    public String toString(){
        String list = "";
        ArrayList<Citation> templist= new ArrayList<>();
        if(citationList.size()!=0){
            for(int i =0;i < citationList.size();i++){
                templist.add(citationList.get(i));
            }
            for(int i =0;i< templist.size();i++){
                list += templist.get(i).addNumbertoCitation(i,templist.get(i))+"\n\n"; // need to check later
            }
        }
        bibString = list;
        return list;
    }

}
