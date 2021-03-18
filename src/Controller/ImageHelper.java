/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author LE NGUYEN TOAN
 */
public class ImageHelper {
    //custom size Image
    public static Image resize(Image originalImage,int targetWidth, int targetHeight){
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
        //return image after customized size
        return resultingImage;
    }
    //conver object images into array bytes
    public static byte [] toByteArray(Image img , String type) throws IOException{
        BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_RGB);
        //covert image into graphics
        Graphics2D g = bimage.createGraphics();
        g.drawImage(img, 0, 0,null);
        g.dispose();
        //data output stream
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bimage, type, baos);
        byte[] imageInByte = baos.toByteArray();
        return imageInByte;
    }
    //conver bytes into image and allow display label
    public static Image CreateImageFromByteArray(byte[]data,String type) throws IOException{
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        BufferedImage bImage2 = ImageIO.read(bis);// reads data from the input byte stream to the bufferImage
        
        Image img = bImage2.getScaledInstance(bImage2.getWidth(), bImage2.getHeight(), Image.SCALE_SMOOTH);
        return img;
    }
}
