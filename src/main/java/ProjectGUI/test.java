package ProjectGUI;

import javax.swing.*;
import java.awt.*;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class test {
    public static void main(String[] args) throws WriterException {
        String barcodeData = "123456789"; // Data to be encoded in the barcode
        generateBarcode(barcodeData);
    }

    public static void generateBarcode(String barcodeData) throws WriterException {
        int width = 300; // Width of the barcode
        int height = 100; // Height of the barcode

        // Create the barcode writer
        Code128Writer barcodeWriter = new Code128Writer();
        BitMatrix bitMatrix;
        // Encode the data into a BitMatrix
		bitMatrix = barcodeWriter.encode(barcodeData, BarcodeFormat.CODE_128, width, height);

		// Create a buffered image from the BitMatrix
		Image barcodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix);

		// Display the image in a frame
		JFrame frame = new JFrame("Barcode");
		JLabel label = new JLabel(new ImageIcon(barcodeImage));
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
    }
}
