package Portafolio;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UmbralConVarianza {
    public static void main(String[] args) {
        try {
        	// Crear un FileDialog para que el usuario seleccione la imagen
            FileDialog fileDialog = new FileDialog(new Frame(), "Seleccionar imagen");
            fileDialog.setFile("*.jpg;*.jpeg;*.png;*.bmp");
            fileDialog.setVisible(true);
            String filename = fileDialog.getFile();
            if (filename == null) {
                return;
            }
            String directory = fileDialog.getDirectory();
            File file = new File(directory + filename);

            // Cargar la imagen desde el archivo
            BufferedImage image = ImageIO.read(file);

            // Obtener el umbral óptimo
            int threshold = UmbralConVarianza.calculateThreshold(image);

            // Imprimir el umbral óptimo
            System.out.println("Umbral óptimo: " + threshold);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int calculateThreshold(BufferedImage image) {
        // Calcular la media de la imagen
        int mean = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                mean += new Color(image.getRGB(x, y)).getRed();
            }
        }
        mean /= image.getWidth() * image.getHeight();

        // Calcular la varianza global de la imagen
        double variance = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int value = new Color(image.getRGB(x, y)).getRed();
                variance += Math.pow((value - mean), 2);
            }
        }
        variance /= image.getWidth() * image.getHeight();

     // Calcular el umbral óptimo
        int threshold = 0;
        double maxVariance = Double.MIN_VALUE;
        for (int t = 0; t < 256; t++) {
            // Dividir la imagen en dos regiones
            int count1 = 0;
            int sum1 = 0;
            int count2 = 0;
            int sum2 = 0;
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int value = new Color(image.getRGB(x, y)).getRed();
                    if (value <= t) {
                        count1++;
                        sum1 += value;
                    } else {
                        count2++;
                        sum2 += value;
                    }
                }
            }
            if (count1 == 0 || count2 == 0) {
                continue;
            }
            // Calcular la varianza entre las dos regiones
            double varianceBetween = ((count1 * Math.pow((sum1 / count1) - mean, 2)) 
                                    + (count2 * Math.pow((sum2 / count2) - mean, 2))) 
                                    / (count1 + count2);
            // Actualizar el umbral óptimo si la varianza es mayor
            if (varianceBetween > maxVariance) {
                maxVariance = varianceBetween;
                threshold = t;
            }
        }
        // Devolver el umbral óptimo
        return threshold;
        }
    }
