package Server.Resources;
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
    /*The request will get array of htmls as JSON, and then it is converted to String array of htmls .
    After that it will be written into pdf.
     */
    @POST
    @Path("/convert/")
    @Produces("application/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createpdf2(String src) throws IOException{
        //need to provide our own CSS.
        //Getting JSON objects and put them in array.
        final JSONObject jsonObject2 = new JSONObject(src);
        final JSONArray data = jsonObject2.getJSONArray("ht");
        String [] arr = new String [data.length()];
        for(int i =0;i<arr.length;i++){
            arr[i] = data.get(i).toString();
        }
        //convert htmls and create pdf then write to it and store as bytes
        ConverterProperties properties = new ConverterProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);

        for (String html : arr) {
            System.out.println(html);
            List<IElement> elements = HtmlConverter.convertToElements(html, properties);
            System.out.println(elements);
            for (IElement element : elements) {
                doc.add((IBlockElement)element);
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
}