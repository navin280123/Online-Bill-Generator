package ProjectGUI;
import java.io.IOException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class test {

    public static void main(String[] args) {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Set font
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);

            // Company logo (Assuming logo.png is your logo file)
            contentStream.drawImage(PDImageXObject.createFromFile("C:\\Users\\user\\Desktop\\jar_files\\logo.png", document), 50, 750, 100, 50);

            // Company information
            contentStream.beginText();
            contentStream.newLineAtOffset(200, 750);
            contentStream.showText("Company Name");
            contentStream.newLine();
            contentStream.showText("123 Company Address");
            contentStream.newLine();
            contentStream.showText("City, State, Zip Code");
            contentStream.newLine();
            contentStream.showText("Phone: 123-456-7890");
            contentStream.newLine();
            contentStream.showText("Email: info@example.com");
            contentStream.endText();

            // Invoice information
            contentStream.beginText();
            contentStream.newLineAtOffset(400, 750);
            contentStream.showText("Invoice #: INV001");
            contentStream.newLine();
            contentStream.showText("Date: 01-04-2024");
            contentStream.newLine();
            contentStream.showText("Due Date: 15-04-2024");
            contentStream.endText();

            // Customer information
            contentStream.beginText();
            contentStream.newLineAtOffset(50, 700);
            contentStream.showText("Customer Name");
            contentStream.newLine();
            contentStream.showText("456 Customer Address");
            contentStream.newLine();
            contentStream.showText("City, State, Zip Code");
            contentStream.newLine();
            contentStream.showText("Phone: 987-654-3210");
            contentStream.newLine();
            contentStream.showText("Email: customer@example.com");
            contentStream.endText();

            contentStream.close();

            document.save("C:\\Users\\user\\Desktop\\jar_files\\Invoice.pdf");
            document.close();

            System.out.println("Invoice generated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
