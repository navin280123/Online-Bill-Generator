package ProjectGUI;
import java.io.IOException;
import java.util.Date;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class test {
    public static void main(String[] args) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            
            // 1. Add "Grocery Invoice" and date
            contentStream.beginText();
            contentStream.newLineAtOffset(70, 750);
            contentStream.showText("Grocery Invoice");
            contentStream.endText();
            
            contentStream.beginText();
            contentStream.newLineAtOffset(250, 750);
            contentStream.showText("Date: " + new Date());
            contentStream.endText();
            
            // 2. Add seller and buyer information in a table
            drawTable(contentStream, 50, 700, new String[]{"Seller Information", "Buyer Information"},
                    new String[][]{{"Name of the Store", "Name"}, {"Phone Number", "Phone Number"}, {"Email", "Address"}});
            
            // 3. Add table with item details
            drawTable(contentStream, 50, 500, new String[]{"SN No.", "Item Name", "MRP", "SP", "Tax", "Quantity", "Amount"},
                    new String[][]{{"1", "Item 1", "10", "8", "2", "2", "16"}, {"2", "Item 2", "20", "15", "3", "1", "15"},{"2", "Item 2", "20", "15", "3", "1", "15"}});
            
            // 4. Add total amount
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 14);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 200);
            contentStream.showText("Total Amount: $100");
            contentStream.endText();
            
            // 5. Add additional text
            contentStream.setFont(PDType1Font.HELVETICA, 10);
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 150);
            contentStream.showText("Once sold, no return.");
            contentStream.endText();
            
            contentStream.close();
            document.save("c:/Users/user/Desktop/invoice.pdf");
            document.close();
            
            System.out.println("PDF created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private static void drawTable(PDPageContentStream contentStream, float startX, float startY, String[] headers, String[][] rows) throws IOException {
        final int rowsPerPage = 20;
        final int numRows = rows.length;
        final int numCols = headers.length;
        final float rowHeight = 20f;
        final float tableWidth = 500f;
        final float tableHeight = rowHeight * rowsPerPage;
        final float colWidth = tableWidth / (float) numCols;
        final float cellMargin = 5f;
        final float borderThickness = 1f;

        // Draw headers with borders
        contentStream.setStrokingColor(0f, 0f, 0f); // Set border color to black
        contentStream.setLineWidth(borderThickness); // Set border thickness
        contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
        for (int i = 0; i < numCols; i++) {
            // Draw border lines for headers
            contentStream.addRect(startX + (i * colWidth), startY, colWidth, rowHeight);
            contentStream.stroke();
            
            // Draw header text
            contentStream.beginText();
            contentStream.moveTextPositionByAmount(startX + (i * colWidth) + cellMargin, startY + cellMargin);
            contentStream.showText(headers[i]);
            contentStream.endText();
        }

        // Draw rows with borders
        contentStream.setFont(PDType1Font.HELVETICA, 12);
        float nextY = startY - rowHeight; // Adjust startY for the first row to accommodate the headers
        for (int i = 0; i < numRows; i++) {
            nextY -= rowHeight;
            for (int j = 0; j < numCols; j++) {
                // Draw border lines for each cell
                contentStream.addRect(startX + (j * colWidth), nextY, colWidth, rowHeight);
                contentStream.stroke();
                
                // Draw cell text
                contentStream.beginText();
                contentStream.moveTextPositionByAmount(startX + (j * colWidth) + cellMargin, nextY + cellMargin);
                contentStream.showText(rows[i][j]);
                contentStream.endText();
            }
        }

        // Draw border lines for the whole table
        contentStream.addRect(startX, nextY, tableWidth, tableHeight);
        contentStream.stroke();
    }

}