package cannyedgedetection;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

/*------------------------------------------------------------------------------------------------*/
public class Main {

    public static void main(String[] args) {

        Canny canny = null;
        try {
            String s = "src/image/assignment04.png"; // -- read image from project directory

            // -- read input image (1)
            File infile = new File(s);
            BufferedImage bi = ImageIO.read(infile);

            // -- separate out image components (2)
            int red[][] = new int[bi.getHeight()][bi.getWidth()];
            int grn[][] = new int[bi.getHeight()][bi.getWidth()];
            int blu[][] = new int[bi.getHeight()][bi.getWidth()];
            for (int i = 0; i < red.length; ++i) {
                for (int j = 0; j < red[i].length; ++j) {
                    red[i][j] = bi.getRGB(j, i) >> 16 & 0xFF;
                    grn[i][j] = bi.getRGB(j, i) >> 8 & 0xFF;
                    blu[i][j] = bi.getRGB(j, i) & 0xFF;
                }
            }

            canny = new Canny(grn);

            // -- set very low thresholds just to see what happens
            int t0 = 0;
            int t1 = 50;
            canny.SetThresholds(t0, t1);

            int[][] edges = canny.Apply();

            // -- shift 0/1 values to 0/255
            for (int i = 0; i < edges.length; ++i) {
                for (int j = 0; j < edges[i].length; ++j) {
                    edges[i][j] = edges[i][j] << 7;
                }
            }

            for (int i = 0; i < bi.getHeight(); ++i) {
                for (int j = 0; j < bi.getWidth(); ++j) {
                    int pixel = (edges[i][j] << 16) | (edges[i][j] << 8) | (edges[i][j]);
                    bi.setRGB(j, i, pixel);
                }
            }

            s = "src/image/canny.png";
            File outputfile = new File(s);
            ImageIO.write(bi, "png", outputfile);

        } catch (IOException e) {
            System.out.println("image I/O error");
        }
    }

}
