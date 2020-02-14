package Server.Resources;



import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.IElement;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.io.*;
import java.util.List;

//import org.apache.poi.xwpf.usermodel.XWPFDocument;

@Path("document/")

public class DocumentResource{
    private static int count = 0;
    private String string;
    public String globalDir;  //testing purposes
    //TESTING
    private File tempFile;

    @GET
    @Path("/test/")
    @Produces(MediaType.APPLICATION_XML)
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


    @Path("/download")
    public class RestDownloadService {

        @GET
        @Path("/service-record")
        @Produces("application/pdf")
        public Response download() {

            //find out how to save the file in server
            //where to write, pdf writer

            javax.ws.rs.core.Response.ResponseBuilder response = Response.ok((Object) tempFile);
            response.header("Content-Disposition",
                    "attachment; filename=\"employee_1415.pdf\"");
            return response.build();
        }
    }
    @POST
    @Path("/convert/")
    @Produces("text/plain")
    @Consumes("application/json")
    public String createpdf2(Htmls src) throws IOException{
        //hard coding for the test

        ConverterProperties properties = new ConverterProperties();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfDocument pdfDoc = new PdfDocument(new PdfWriter(baos));
        Document doc = new Document(pdfDoc);
        for (String html : src.getArray()) {
            List<IElement> elements = HtmlConverter.convertToElements(new FileInputStream(html), properties);
            for (IElement element : elements) {
                doc.add((IBlockElement)element);
            }
        }
        doc.close();
        byte[] bytes = baos.toByteArray();

        String prefix = "tempFile"+count;
        String suffix = "pdf";

        tempFile = File.createTempFile(prefix,suffix);
        //tempFile.deleteOnExit();

        //user
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(bytes);
        fos.flush();
        fos.close();

        return "ok";
    }





    //return the htmls from frontend that will convert to pdf
    private String getHtml(){
        return null;
    }
    //merge all html to become 1 html that will convert to pdf using jsoup
    //eventually will be public return response to be able to give users to download the pdf file return response
    private void convertToPdf() {

    }


//
//
//	@POST
//	@Path("post")
//	public void postContenttoDoc(String text) {
//		createDocument(text);
//	}
//
//
//
//
////will be used in the future.
////	@GET
////	//@Path("{dir}")
////	@Produces(MediaType.TEXT_PLAIN)
////	public Response getDocument(@PathParam("dir")String dir) {
////		File file = new File(dir);
////		ResponseBuilder response = Response.ok((Object)dir);
////		response.header("Content=Disposition","attachment; filename="+dir);
////		return response.build();
////	}
//	//need produces
//	@Path("dir")
//	public Response getDocument() {
//		String dir = "/Users/sannge/Projects/createdocument.docx"; //just hardcoded the directory where the file is gonna store
//		File file = new File(dir);
//		ResponseBuilder response = Response.ok((Object)dir);
//		response.header("Content=Disposition","attachment; filename="+dir);
//		return response.build();
//	}
//
//
//	//might need to parse the text parsed from text area and see the style, format, title, size everything.
//	//but for now, we will just hard code and test if that works.
//
//	private void createDocument(String text) {
//		XWPFDocument document = new XWPFDocument();
//		FileOutputStream out;
//		try {
//			out = new FileOutputStream( new File("createdocument.docx"));
//			document.write(out);
//		      out.close();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
}