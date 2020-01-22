package Server.Resources;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

//import org.apache.poi.xwpf.usermodel.XWPFDocument;

@Path("document")
public class DocumentResource{
    private String string;
    //TESTING
    @POST
    public String postingTest(String str) {
        string = str;
        return str;
    }

    private void parseHTML() {
        //parse the html and create the document.
    }

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