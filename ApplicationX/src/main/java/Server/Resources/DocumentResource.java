package Server.Resources;

import Core.Bibliography;
import Core.Citation;
import Core.CitationController;
import Core.ResearchedCellsHandler;
import com.google.gson.Gson;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

@Path("document/")
//@Singleton
public class DocumentResource{
    private CitationController c;

    public DocumentResource(){
        c = new CitationController();
    }

    @POST
    @Path("/scrapeurl")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String scrapeUrl(String payload) throws IOException {
        JSONObject urlJson = new JSONObject(payload);
        String url = urlJson.getString("url");
        Citation citation = c.scrapeUrl(url);
        JSONObject jsonObject = new JSONObject(citation);
        String jString = jsonObject.toString();
        System.out.println(jString);

        return jString;
    }
    /*The request will 678get array of htmls as JSON, and then it is converted to String array of htmls .
    After that it will be written into pdf.
     */

    /**
     * This method receives a string representation of a json object. Converts string into json object.
     * Parses json object to retrieve json array value with key "ht". Then Converts json array into array
     * of string denoted as "htmlArray" which contains html elements as string.
     *
     * Traverses "htmlArray" elements to append to a pdf document. Once finished, converts pdf doc object
     * into bytes and returns back to user, a pdf file.
     * @param src - string representation of json object that is html.
     * @return - pdf file
     * @throws IOException
     */
    @POST
    @Path("/convert/")
    @Produces("application/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPdf(String src) throws IOException{
        // Converts json into a JSON Object
        final JSONObject jsonObject = new JSONObject(src);
        Bibliography b = new Bibliography();
        JSONArray citationObjectsArray = jsonObject.getJSONArray("citationObjects");
        for(int i=0;i<citationObjectsArray.length();i++){
            String s = citationObjectsArray.get(i).toString();

             JSONObject citOb = new JSONObject(s);
             int index =Integer.parseInt( citOb.get("index").toString());
             Gson g = new Gson();
             Citation citation = g.fromJson(citOb.get("citation").toString(),Citation.class);
             b.add(citation);

             System.out.println("Formatted citation here"+citation.formatCitation());
        }


        ResearchedCellsHandler rcHandler = new ResearchedCellsHandler(jsonObject);

       // System.out.println(rcHandler.toString());

        // Instantiates Byte array output stream and PDF doc.
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);

        // Converts researched cells into HTML elements and appends to PDF doc
        for(int i = 0; i < rcHandler.size(); i++) {
            List<IElement> htmlElements = rcHandler.getHtmlResearchedCell(i);
            for( IElement element : htmlElements) {
                System.out.println("This is IElement");
                doc.add((IBlockElement) element);
            }
        }
        if(b.getCitationList().size()!=0){
            Paragraph p = new Paragraph("\n\nReferences");
            p.setTextAlignment(TextAlignment.CENTER);
            doc.add(p);
            Paragraph p1 = new Paragraph(b.toString());
            doc.add(p1);

        }



        doc.close();


        byte[] bytes = baos.toByteArray();
        //download purposes
        StreamingOutput fileStream =  new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException, WebApplicationException {
                output.write(bytes);
                output.flush();
            }
        };
        Response.ResponseBuilder response = Response.ok(fileStream);
        return response.build();
    }

    @POST
    @Path("/formatCitation")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String formatCitation(Citation citation){
        // Paramater should be String citation
        // Convert string into a Citation object "citeObj"
        // then return "citeObj.formatCitation();
        System.out.println("passed");
        return citation.formatCitation();
    }

    @POST
    @Path("/createBib")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String makeBib(Bibliography b){
        // makeBib resource method probably not necessary?
        // the bibliography.toString() should be called within in createPdf Resource.
        // the list of htmls. (One big json file)
        // Because the list of citation objects will be passed with html list in one big object.
        return b.toString();
    }

    //TESTING
    @POST
    @Path("/tesst2/")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    //LEARNT, RESTAPI cannot return String [] or any array type since its not text plain.
    public String testing(String src){
        final JSONObject jsonObject = new JSONObject(src);
        final JSONArray data = jsonObject.getJSONArray("ht");
        String [] arr = new String [data.length()];
        for(int i =0;i<arr.length;i++){
            System.out.println(data.get(i).toString());
            arr[i] = data.get(i).toString();
        }
        return Arrays.toString(arr);

    }


}