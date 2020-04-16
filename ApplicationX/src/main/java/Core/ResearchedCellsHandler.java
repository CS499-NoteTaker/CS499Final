package Core;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ResearchedCellsHandler {
    private ArrayList<String> researchedCells;


    public ResearchedCellsHandler(){
        researchedCells = new ArrayList<String>();
    }

    public ResearchedCellsHandler (JSONObject jsonObject) {
        final JSONArray jsonArray = jsonObject.getJSONArray("ht");
        researchedCells = new ArrayList<String>(jsonArray.length());

        // Loads jsonArray elements into an array of string of html
        for(int i = 0; i < researchedCells.size(); i++){
            String researchedCell = jsonArray.get(i).toString();
            researchedCells.add(i, researchedCell);
        }
    }

    public boolean add(String researchedCell) {
        return researchedCells.add( researchedCell );
    }

    public String get(int index) {
        return researchedCells.get(index);
    }

}
