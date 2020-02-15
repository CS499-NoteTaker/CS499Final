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
import com.google.gson.Gson;

import javax.inject.Singleton;
import javax.json.JsonObject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

//import org.apache.poi.xwpf.usermodel.XWPFDocument;

@Path("document/")
@Singleton
public class DocumentResource{
    private static int count = 0;
    private String string;
    public String globalDir;  //testing purposes
    //TESTING
    private File tempFile;

    @GET
    @Path("/test/")
    @Produces(MediaType.APPLICATION_JSON)
    public Htmls get(){
        Htmls h1 = new Htmls();
        String [] s = new String [3];
        s[0] = "<p>This is a first paragraph</p>";
        s[1] = "<p>This is a second paragraph</p>";
        s[2] = "<p>This is a third paragraph</p>";

        h1.setHtmls(s);
        h1.getHtmls();
        return h1;
    }


//        @GET
//        @Path("download")
//        @Produces("application/pdf")
//        public Response download() throws IOException {
//
//            //find out how to save the file in server
//            //where to write, pdf writer
//
//            javax.ws.rs.core.Response.ResponseBuilder response = Response.ok(tempFile );
//            response.header("Content-Disposition",
//                    "attachment; filename=\"ApplicationX.pdf\"");
//            return response.build();
//        }

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

//        JSONArray jarr = obj.getJSONArray("ht");
//        int n = jarr.length();
//
//        String [] arr = new String [n];
//        for(int i =0;i<arr.length;i++){
//            System.out.println(jarr.get(i).toString());
//            arr[i] = jarr.get(i).toString();
//        }
//        return arr;
    }

    @POST
    @Path("/convert/")
    @Produces("application/pdf")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createpdf2(String src) throws IOException{
        //hard coding for the test
        final JSONObject jsonObject2 = new JSONObject(src);
        final JSONArray data = jsonObject2.getJSONArray("ht");
        String [] arr = new String [data.length()];
        for(int i =0;i<arr.length;i++){
            arr[i] = data.get(i).toString();
        }
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