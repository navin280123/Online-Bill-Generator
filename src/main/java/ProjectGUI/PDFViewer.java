package ProjectGUI;

import javax.swing.*;
import java.awt.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

public class PDFViewer extends JFrame {
    private JPanel panel;
    private JLabel label;

    public PDFViewer(String path) {
        setTitle("PDF Viewer");
        setSize(700, 1050);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panel = new JPanel();
        label = new JLabel();

        panel.add(label);
        add(panel);
        setVisible(true);
        displayPDF(path); 
    }

    private void displayPDF(String filePath) {
        try {
            PDDocument document = PDDocument.load(new java.io.File(filePath));
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            PDPage page = document.getPage(0); // Get the first page of the PDF
            Dimension pageSize = new Dimension((int) page.getMediaBox().getWidth(), (int) page.getMediaBox().getHeight()); // Get the page size

            // Calculate the scaling factor to fit the entire page within the frame
            float scaleX = 1050f / pageSize.width;
            float scaleY = 700f / pageSize.height;
            float scale = Math.min(scaleX, scaleY);

            Image img = pdfRenderer.renderImage(0, scale, ImageType.RGB); // Render the image

            label.setIcon(new ImageIcon(img));

            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            PDFViewer viewer = new PDFViewer("C:\\Users\\user\\Desktop\\invoice.pdf");
            viewer.setVisible(true);
        });
    }
}
