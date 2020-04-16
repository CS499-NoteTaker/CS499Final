package Server.Resources;
import Core.ResearchedCellsHandler;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.util.Arrays;
import java.util.List;

@Path("document/")
//@Singleton
public class DocumentResource{

    /*The request will get array of htmls as JSON, and then it is converted to String array of htmls .
    After that it will be written into pdf.
     */

    /**
     * This method receives a string representation of a json object. Converts string into json object.
     * Parses json object to retrieve json array value with key "ht". Then Converts json array into array
     * of string denoted as "htmlArray" which contains html elements as string.
     *
     * Traverses "htmlArray" elements to append to a pdf document. Once finished, converts pdf doc object
     * into bytes and returns back to user, a pdf file.
     * @param src - string representation of json object.
     * @return - pdf file
     * @throws IOException
     */
    @POST
    @Path("/convert/")
    @Produces("application/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createPdf(String src) throws IOException{
        // src - string representation of a json array of html.
        // Converts json into a JSON Object
        final JSONObject jsonObject = new JSONObject(src);
        /*
        final JSONArray jsonArray = jsonObject.getJSONArray("ht");
        String [] htmlArray = new String [jsonArray.length()];

        // Loads jsonArray elements into an array of string of html
        for(int i = 0; i < htmlArray.length; i++){
            htmlArray[i] = jsonArray.get(i).toString();
        }

        */
        ResearchedCellsHandler rcHandler = new ResearchedCellsHandler(jsonObject);
        System.out.println(rcHandler.toString() );
        
        // Instantiates Byte array output stream and PDF doc.
        ConverterProperties properties = new ConverterProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);

        // Converts htmlArray elements into HTML objects (according to iText) and append to PDF doc
        for(int i = 0; i < rcHandler.size(); i++) {
            String researchedCell = rcHandler.get(i);
            List<IElement> htmlResearchedCells = HtmlConverter.convertToElements(researchedCell, properties);
            System.out.println(htmlResearchedCells);
            for (IElement element : htmlResearchedCells) {
                doc.add((IBlockElement) element);
            }
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