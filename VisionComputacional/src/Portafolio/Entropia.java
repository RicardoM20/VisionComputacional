package Portafolio;

import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Entropia {
	public static void main(String[] args) {
		 // Crear un file dialog para seleccionar la imagen
        Frame frame = new Frame();
        FileDialog fileDialog = new FileDialog(frame, "Seleccionar imagen", FileDialog.LOAD);
        fileDialog.setVisible(true);

        // Obtener la ruta de la imagen seleccionada
        String imagePath = fileDialog.getDirectory() + fileDialog.getFile();

        // Leer la imagen
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Calcular la entropía de la imagen
        double entropy = calculateEntropy(image);

        // Mostrar la entropía de la imagen
        JOptionPane.showMessageDialog(null, "La entropía de la imagen seleccionada es: " + entropy);

    }

    public static double calculateEntropy(BufferedImage image) {
        int totalPixels = image.getWidth() * image.getHeight();
        Map<Integer, Integer> pixelCounts = new HashMap<>();

        // Contar el número de veces que aparece cada intensidad de pixel en la imagen
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                int pixel = image.getRGB(i, j);
                int intensity = (pixel & 0xff) + ((pixel >> 8) & 0xff) + ((pixel >> 16) & 0xff);
                if (pixelCounts.containsKey(intensity)) {
                    pixelCounts.put(intensity, pixelCounts.get(intensity) + 1);
                } else {
                    pixelCounts.put(intensity, 1);
                }
            }
        }

        // Calcular la entropía de la imagen utilizando la fórmula de entropía de la información
        double entropy = 0;
        for (int intensity : pixelCounts.keySet()) {
            double probability = (double) pixelCounts.get(intensity) / totalPixels;
            entropy -= probability * Math.log(probability) / Math.log(2);
        }

        return entropy;
	}
}
