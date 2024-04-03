package ProjectGUI ;
import java.awt.*;
import java.io.IOException;
import java.util.*;

import javax.swing.event.PopupMenuListener;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class test {
	static ArrayList<pdfProduct> acdata = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("Hello World!");
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfh"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        acdata.add(new pdfProduct(0, 0, 0, 0, 0, 0, 0, "sdkfjhs"));
        
        try {
            test igen = new test();
            igen.Generate(null);
        } catch (InvoiceException e) {
            e.printStackTrace();
        }
    }

    final PDDocument document;
    final PDPage page;
    final PDPageContentStream contents;
    final Color ACCENT_COLOR = new Color(136, 144, 192);

    public test() throws InvoiceException {
        document = new PDDocument();
        page = new PDPage(PDRectangle.A4);
        document.addPage(page);
        try {
            contents = new PDPageContentStream(document, page);
        } catch (IOException e) {
            throw new InvoiceException("Failed to initialize PDF Content Stream", e);
        }
    }

    public void Generate(HashMap<String, String> data) throws InvoiceException {
        try {

            drawHeader();
            LinkedHashMap<String, String> invoiceInfoData = new LinkedHashMap<>();
            invoiceInfoData.put("INVOICE NO", "102332");
            invoiceInfoData.put("INVOICE DATE", "03/04/2024");
            drawSingleRowTable(invoiceInfoData, 40, 680, 110, 20, 5, 10);

            LinkedHashMap<String, String> siteData = new LinkedHashMap<>(); 
            siteData.put("BILL TO", "AYUSH");
            siteData.put("PH. NUMBER", "9559594477");
            drawSingleRowTable(siteData, 40, 620, 130, 20, 5, 10);

            

            HashMap<String,String> descData = new HashMap<>();
            
            descData.put("TOTAL","0000"); //TOTAL AMOUNT
            drawDescriptionTable(descData);

        } catch (IOException e) {
            throw new InvoiceException(e);
        }

        saveInvoice("C:\\Users\\AYUSH\\Desktop\\Invoice2.pdf");
    }

    private void saveInvoice(String fileName) throws InvoiceException {
        try {
            //Close the Content Stream before closing
            contents.close();
            document.save(fileName);
        } catch (IOException e) {
            throw new InvoiceException("Unable to save Invoice", e);
        }
    }

    private void drawDescriptionTable(Map<String,String> data) throws IOException {
        //Description column
        contents.setNonStrokingColor(ACCENT_COLOR);
        contents.addRect(40, 550, 360, 27);
        contents.fill();
        drawTableCellText("SN NO.", 45, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 45, 27);
        contents.stroke();
        contents.addRect(40, 180, 45, 370);
        contents.stroke();
//        drawTableCellText("1", 55, 530, Color.BLACK, PDType1Font.HELVETICA, 10);
//        drawTableCellText("2", 55, 515, Color.BLACK, PDType1Font.HELVETICA, 10);
       populateData(acdata);
        
        drawTableCellText("ITEM NAME", 90, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 125, 27);
        contents.stroke();
        contents.addRect(40, 180, 125, 370);
        contents.stroke();
        
        
        
        
        drawTableCellText("MRP", 170, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 175, 27);
        contents.stroke();
        contents.addRect(40, 180, 175, 370);
        contents.stroke();
        

        
        drawTableCellText("DISCOUNT", 220, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 250, 27);
        contents.stroke();
        contents.addRect(40, 180, 250, 370);
        contents.stroke();
        
        
        
        
        drawTableCellText("SP", 295, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 285, 27);
        contents.stroke();
        contents.addRect(40, 180, 285, 370);
        contents.stroke();
        
        
        
        
        drawTableCellText("TAX", 330, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 325, 27);
        contents.stroke();
        contents.addRect(40, 180, 325, 370);
        contents.stroke();
        
        
        
        
        drawTableCellText("QTY", 370, 560, Color.BLACK, 10);
        contents.addRect(40, 550, 360, 27);
        contents.stroke();
        contents.addRect(40, 180, 360, 370);
        contents.stroke();
        
        

        

        //Amount column
        contents.setNonStrokingColor(ACCENT_COLOR);
        contents.addRect(400, 550, 160, 27);
        contents.fill();
        drawTableCellText("AMOUNT", 510, 560, Color.BLACK, 10);
        contents.addRect(400, 550, 160, 27);
        contents.stroke();
        contents.addRect(400, 180, 160, 370);
        contents.stroke();
        
        
        
        
        //Invoice Currency AUD column cell
        contents.addRect(40, 130, 300, 50);
        contents.stroke();
        drawTableCellText("NET AMOUNT", 120, 150, Color.BLACK, PDType1Font.HELVETICA, 12);
        //TOTAL column cell
        contents.addRect(340, 130, 220, 50);
        contents.stroke();
        drawTableCellText("TOTAL", 350, 150, Color.BLACK, PDType1Font.HELVETICA_BOLD, 14);
        drawTableCellText(data.get("TOTAL"), 480, 150, Color.BLACK, PDType1Font.HELVETICA_BOLD, 14);
    }

    private void drawSingleRowTable(Map<String, String> data, float left, float top, float col_width, float row_height, float padding, float font_size) throws IOException {


        Iterator<Map.Entry<String, String>> columns = data.entrySet().iterator();

        int i = 0;
        while (columns.hasNext()) {
            Map.Entry<String, String> entry = columns.next();

            //Header background
            contents.setNonStrokingColor(ACCENT_COLOR);
            contents.addRect(left + (col_width * i), top, col_width, row_height);
            contents.fill();

            drawTableCellText(entry.getKey(), left + (col_width * i) + padding, top + padding, Color.BLACK, font_size);
            //Header border
            contents.addRect(left + (col_width * i), top, col_width, row_height);
            contents.stroke();
            //First row
            contents.addRect(left + (col_width * i), top - row_height, col_width, row_height);
            contents.stroke();

            drawTableCellText(entry.getValue(), left + (col_width * i) + padding, top - row_height + padding, Color.BLACK, font_size);

            i++;
        }

    }

    private void drawTableCellText(String text, float tx, float ty, Color color, float font_size) throws IOException {
        drawTableCellText(text,tx,ty,color,PDType1Font.HELVETICA,font_size);
    }

    private void drawTableCellText(String text, float tx, float ty, Color color, PDFont font,float font_size) throws IOException {
        contents.beginText();
        contents.setFont(font, font_size);
        contents.setNonStrokingColor(color);
        contents.newLineAtOffset(tx, ty);
        contents.showText(text);
        contents.endText();
    }
    private void populateData(ArrayList<pdfProduct> Data) {
    	int height= 530;
    	for(pdfProduct s: Data) {
    		try {
    			drawTableCellText(String.valueOf(s.sn), 55, height, Color.BLACK, PDType1Font.HELVETICA, 10); //sn
    			drawTableCellText(String.valueOf(s.amount), 510, height, Color.BLACK, PDType1Font.HELVETICA, 10); //amount
    			drawTableCellText(String.valueOf(s.qnt), 380, height, Color.BLACK, PDType1Font.HELVETICA, 10);  //quantity
    			drawTableCellText(String.valueOf(s.tax), 340, height, Color.BLACK, PDType1Font.HELVETICA, 10); //tax
    			drawTableCellText(String.valueOf(s.sp), 305, height, Color.BLACK, PDType1Font.HELVETICA, 10); //selling price
    			drawTableCellText(String.valueOf(s.discount), 230, height, Color.BLACK, PDType1Font.HELVETICA, 10);  //discount
    	        drawTableCellText(String.valueOf(s.mrp), 180, height, Color.BLACK, PDType1Font.HELVETICA, 10); //MRp
    	        drawTableCellText(s.itemName, 100, height, Color.BLACK, PDType1Font.HELVETICA, 10); //product name
    	        
    			height -= 15;
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
    	}
    	
    }

    private void drawHeader() throws IOException {

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA_BOLD, 30);
        contents.setNonStrokingColor(ACCENT_COLOR);
        contents.newLineAtOffset(370, 770);
        contents.showText("INVOICE");
        contents.endText();

        contents.beginText();
        contents.setNonStrokingColor(Color.BLACK);
        contents.setFont(PDType1Font.HELVETICA, 18);
        contents.newLineAtOffset(40, 770);
        contents.showText("HARDWARE STORE");
        contents.endText();

        contents.beginText();
        contents.setFont(PDType1Font.HELVETICA, 16);
        contents.newLineAtOffset(40, 750);
        contents.showText("");
        contents.endText();

    }

    public void finalize() throws InvoiceException {
        try {
            document.close();
        } catch (IOException e) {
            throw new InvoiceException(e);
        }
    }
}
class pdfProduct{
//	drawTableCellText(s, 55, height, Color.BLACK, PDType1Font.HELVETICA, 10); //sn
//	drawTableCellText("400", 510, 530, Color.BLACK, PDType1Font.HELVETICA, 10); //amount
//	drawTableCellText("4", 380, 530, Color.BLACK, PDType1Font.HELVETICA, 10);  //quantity
//	drawTableCellText("2.5", 340, 530, Color.BLACK, PDType1Font.HELVETICA, 10); //tax
//	drawTableCellText("60", 305, 530, Color.BLACK, PDType1Font.HELVETICA, 10); //selling price
//	drawTableCellText("10%", 230, 530, Color.BLACK, PDType1Font.HELVETICA, 10);  //discount
//    drawTableCellText("70", 180, 530, Color.BLACK, PDType1Font.HELVETICA, 10); //MRp
//    drawTableCellText("OIL", 100, 530, Color.BLACK, PDType1Font.HELVETICA, 10); //product name
	
	int sn;
	double tax,mrp,sp,discount,amount,qnt;
	String itemName;
	pdfProduct(int sn,double tax,double mrp,double sp,double discount,double amount,double qnt,String itemName){
		this.sn =sn;
		this.tax =tax;
		this.mrp=mrp;
		this.sp=sp;
		this.qnt=qnt;
		this.discount=discount;
		this.amount = amount;
		this.itemName = itemName;
		
	}
	
}
