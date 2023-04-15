package Portafolio;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class Varianza {
	
	// Método público y estático para calcular la varianza de la intensidad de grises de una imagen
	public static double calculate(BufferedImage image) {
		
		// Obtener el ancho y la altura de la imagen
		int width = image.getWidth();
		int height = image.getHeight();
		
		// Variables para calcular la media y la varianza
		double mean = 0;
		double sum = 0;
		int count = 0;
		
		// Recorrer cada píxel de la imagen
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				
				// Obtener el valor RGB del píxel
				int rgb = image.getRGB(x, y);
				
				// Obtener los valores de rojo, verde y azul del valor RGB
				int red = (rgb >> 16) & 0xff;
				int green = (rgb >> 8) & 0xff;
				int blue = rgb & 0xff;
				
				// Calcular la intensidad de grises promedio del píxel
				double gray = (red + green + blue) / 3.0;
				
				// Actualizar las variables de media y varianza
				sum += (gray - mean) * (gray - mean);
				mean += (gray - mean) / (++count);
			}
		}
		
		// Calcular y devolver la varianza de la intensidad de grises de la imagen
		return sum / (count - 1);
	}
	
	// Método principal
	public static void main(String[] args) {
		
		// Configuración de la ventana de diálogo de selección de archivo
		FileDialog fileDialog = new FileDialog(new Frame(), "Seleccionar archivo", FileDialog.LOAD);
		fileDialog.setFilenameFilter((dir, name) -> name.endsWith(".jpg") || name.endsWith(".png"));
		
		// Mostrar la ventana de diálogo de selección de archivo
		fileDialog.setVisible(true);
		
		// Si el usuario ha seleccionado un archivo
		if (fileDialog.getFile() != null) {
			
			// Obtener el archivo seleccionado
			File file = new File(fileDialog.getDirectory(), fileDialog.getFile());
			
			try {
				// Cargar la imagen desde el archivo
				BufferedImage image = ImageIO.read(file);
				
				// Calcular la varianza de la intensidad de grises de la imagen
				double variance = calculate(image);
				
				// Imprimir el resultado en la consola
				System.out.println("La varianza de la imagen es: " + variance);
			} catch (Exception e) {
				// Si se produce un error al cargar la imagen, imprimir un mensaje de error en la consola
				System.out.println("Error al cargar la imagen: " + e.getMessage());
			}
		}
	}
	
}


