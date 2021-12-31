package daoge.machine_learning;

import org.w3c.dom.css.RGBColor;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MnistReader {

    public static final String TRAIN_IMAGES_FILE = "C:/Users/m1334/IdeaProjects/Machine_Learning/database/train-images.idx3-ubyte";
    public static final String TRAIN_LABELS_FILE = "C:/Users/m1334/IdeaProjects/Machine_Learning/database/train-labels.idx1-ubyte";
    public static final String TEST_IMAGES_FILE = "C:/Users/m1334/IdeaProjects/Machine_Learning/database/t10k-images.idx3-ubyte";
    public static final String TEST_LABELS_FILE = "C:/Users/m1334/IdeaProjects/Machine_Learning/database/t10k-labels.idx1-ubyte";

    /**
     * change bytes into a hex string.
     *
     * @param bytes bytes
     * @return the returned hex string
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(bytes[i] & 0xFF);
            if (hex.length() < 2) {
                sb.append(0);
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    /**
     * get images of 'train' or 'test'
     *
     * @param fileName the file of 'train' or 'test' about image
     * @return one row show a `picture`
     */
    public static int[][] getImages(String fileName) {
        int[][] x = null;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[4];
            bin.read(bytes, 0, 4);
            if (!"00000803".equals(bytesToHex(bytes))) {                        // 读取魔数
                throw new RuntimeException("Please select the correct file!");
            } else {
                bin.read(bytes, 0, 4);
                int number = Integer.parseInt(bytesToHex(bytes), 16);           // 读取样本总数
                bin.read(bytes, 0, 4);
                int xPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每行所含像素点数
                bin.read(bytes, 0, 4);
                int yPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每列所含像素点数
                x = new int[number][xPixel * yPixel];
                for (int i = 0; i < number; i++) {
                    int[] element = new int[xPixel * yPixel];
                    for (int j = 0; j < xPixel * yPixel; j++) {
                    element[j] = bin.read();                                // 逐一读取像素值
                    // normalization
//                        element[j] = bin.read() / 255.0;
                }
                    x[i] = element;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return x;
        }

        public static int[][] getImages2v(String fileName) {
        int[][] x = null;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[4];
            bin.read(bytes, 0, 4);
            if (!"00000803".equals(bytesToHex(bytes))) {                        // 读取魔数
                throw new RuntimeException("Please select the correct file!");
            } else {
                bin.read(bytes, 0, 4);
                int number = Integer.parseInt(bytesToHex(bytes), 16);           // 读取样本总数
                bin.read(bytes, 0, 4);
                int xPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每行所含像素点数
                bin.read(bytes, 0, 4);
                int yPixel = Integer.parseInt(bytesToHex(bytes), 16);           // 读取每列所含像素点数
                x = new int[number][xPixel * yPixel];
                for (int i = 0; i < number; i++) {
                    int[] element = new int[xPixel * yPixel];
                    for (int j = 0; j < xPixel * yPixel; j++) {
                        int value = bin.read();
                        if (value > 128)
                            value = 0;
                        else
                            value = 255;
                    element[j] = value;                                // 逐一读取像素值
                    // normalization
//                        element[j] = bin.read() / 255.0;
                }
                    x[i] = element;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return x;
        }

    /**
     * get labels of `train` or `test`
     *
     * @param fileName the file of 'train' or 'test' about label
     * @return
     */
    public static int[] getLabels(String fileName) {
        int[] y = null;
        try (BufferedInputStream bin = new BufferedInputStream(new FileInputStream(fileName))) {
            byte[] bytes = new byte[4];
            bin.read(bytes, 0, 4);
            if (!"00000801".equals(bytesToHex(bytes))) {
                throw new RuntimeException("Please select the correct file!");
            } else {
                bin.read(bytes, 0, 4);
                int number = Integer.parseInt(bytesToHex(bytes), 16);
                y = new int[number];
                for (int i = 0; i < number; i++) {
                    y[i] = bin.read();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
            return y;
        }

    public static void main(String[] args) {
        int[][] images = getImages(TRAIN_IMAGES_FILE);
        int[] labels = getLabels(TRAIN_LABELS_FILE);

        int[][] t_images = getImages(TEST_IMAGES_FILE);
        int[] t_labels = getLabels(TEST_LABELS_FILE);

        try {
            for (int k = 0;k <= 9;k++){
            Path path = Paths.get("C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures/training/" + k);
            Path path2v = Paths.get("C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures2v/training/" + k);
            if (!Files.exists(path))
                Files.createDirectories(path);
            if (!Files.exists(path2v))
                Files.createDirectories(path2v);
            path = Paths.get("C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures/testing/" + k);
            path2v = Paths.get("C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures2v/testing/" + k);
            if (!Files.exists(path))
                Files.createDirectories(path);
            if (!Files.exists(path2v))
                Files.createDirectories(path2v);
            }
            for (int i = 0;i < images.length;i++) {
                drawGrayPicture(images[i], 28, 28, "C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures/training/" + labels[i] + "/" + labels[i] + "-" + (i + 1) + ".jpg");
                drawGrayPicture2v(images[i], 28, 28, "C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures2v/training/" + labels[i] + "/" + labels[i] + "-" + (i + 1) + ".jpg");
                System.out.println("Finished output training " + (i + 1));
            }
            for (int j = 0;j < t_images.length;j++){
                drawGrayPicture(t_images[j], 28, 28, "C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures/testing/" + t_labels[j] + "/" + t_labels[j] + "-" + (j + 1) + ".jpg");
                drawGrayPicture2v(t_images[j], 28, 28, "C:/Users/m1334/IdeaProjects/Machine_Learning/database/pictures2v/testing/" + t_labels[j] + "/" + t_labels[j] + "-" + (j + 1) + ".jpg");
                System.out.println("Finished output testing " + (j + 1));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * draw a gray picture and the image format is JPEG.
     *
     * @param pixelValues pixelValues and ordered by column.
     * @param width       width
     * @param high        high
     * @param fileName    image saved file.
     */
    public static void drawGrayPicture(int[] pixelValues, int width, int high, String fileName) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, high, BufferedImage.TYPE_CUSTOM);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < high; j++) {
                int pixel = (255 - pixelValues[i * high + j]);
                int value = pixel + (pixel << 8) + (pixel << 16);   // r = g = b 时，正好为灰度
                bufferedImage.setRGB(j, i, value);
            }
        }

        ImageIO.write(bufferedImage, "JPEG", new File(fileName));
    }

    public static void drawGrayPicture2v(int[] pixelValues, int width, int high, String fileName) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(width, high, BufferedImage.TYPE_INT_RGB);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < high; j++) {
                int value = Color.white.getRGB();
                if (pixelValues[i * width + j] > 128)
                    value = Color.black.getRGB();
                bufferedImage.setRGB(j, i,value);
            }
        }

        ImageIO.write(bufferedImage, "JPEG", new File(fileName));
    }
}
