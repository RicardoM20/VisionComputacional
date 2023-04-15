package Portafolio;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Varianza {
	public static double calculate(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double mean = 0;
        double sum = 0;
        int count = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                int red = (rgb >> 16) & 0xff;
                int green = (rgb >> 8) & 0xff;
                int blue = rgb & 0xff;
                double gray = (red + green + blue) / 3.0;
                sum += (gray - mean) * (gray - mean);
                mean += (gray - mean) / (++count);
            }
        }
        return sum / (count - 1);
    }

	public static void main(String[] args) {
	    // Configuración de la caja de diálogo de selección de archivo
	    FileDialog fileDialog = new FileDialog(new Frame(), "Seleccionar archivo", FileDialog.LOAD);
	    fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));

	    // Mostrar la caja de diálogo de selección de archivo
	    fileDialog.setVisible(true);

	    if (fileDialog.getFile() != null) {
	        // El usuario ha seleccionado un archivo
	        File file = new File(fileDialog.getDirectory(), fileDialog.getFile());

	        try {
	            // Cargar la imagen desde el archivo
	            BufferedImage image = ImageIO.read(file);

	            // Calcular la varianza de la intensidad de grises de la imagen
	            double variance = calculate(image);

	            // Imprimir el resultado
	            System.out.println("La varianza de la imagen es: " + variance);
	        } catch (Exception e) {
	            System.out.println("Error al cargar la imagen: " + e.getMessage());
	        }
	    }
	}

}	

