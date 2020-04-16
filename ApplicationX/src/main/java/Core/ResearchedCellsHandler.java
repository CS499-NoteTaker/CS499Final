package Core;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.layout.element.IElement;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import java.util.ArrayList;

public class ResearchedCellsHandler {
    private ArrayList<String> researchedCells;


    public ResearchedCellsHandler(){
        researchedCells = new ArrayList<String>();
    }

    public ResearchedCellsHandler (JSONObject jsonObject) {
        final JSONArray jsonArray = jsonObject.getJSONArray("ht");
        researchedCells = new ArrayList<String>();

        // Loads jsonArray elements into an array of string of html
        for(int i = 0; i < jsonArray.length(); i++){
            String researchedCell = jsonArray.get(i).toString();
            researchedCells.add(i, researchedCell);
        }
    }

    public boolean add(String researchedCell) {
        return researchedCells.add( researchedCell );
    }


    /**
     * This Method will convert string representation of researched cell
     * into an HTML element.
     * @param index - index at which research cell to convert
     * @return - html IElement
     * @throws IOException
     */
    public IElement getHtmlResearchedCell(int index) throws IOException {
        ConverterProperties properties = new ConverterProperties();
        String researchedCell = researchedCells.get(index);
        IElement htmlElemnt = (HtmlConverter.convertToElements(researchedCell, properties)).get(0);

        return htmlElemnt;
    }

    public String get(int index) {
        return researchedCells.get(index);
    }

    public int size() {
        return researchedCells.size();
    }

    public String toString() {
        return researchedCells.toString();
    }

}
