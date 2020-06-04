/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author leizhe
 */
@WebServlet(name = "ImageDelivery", urlPatterns = {"/image/*"})
/**
 * Reference: https://stackoverflow.com/questions/8623709/output-an-image-file-from-a-servlet/8623747#8623747
 */
public class ImageDelivery extends HttpServlet{
     public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

      ServletContext cntx= req.getServletContext();
      // Get the absolute path of the image
      String filename = System.getProperty("user.home")+"/KijijiImages/"+req.getPathInfo().substring(1);
      // retrieve mimeType dynamically
      String mime = cntx.getMimeType(filename);
      if (mime == null) {
        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return;
      }

      resp.setContentType(mime);
      File file = new File(filename);
      resp.setContentLength((int)file.length());

      FileInputStream in = new FileInputStream(file);
      OutputStream out = resp.getOutputStream();

      // Copy the contents of the file to the output stream
       byte[] buf = new byte[1024];
       int count = 0;
       while ((count = in.read(buf)) >= 0) {
         out.write(buf, 0, count);
      }
    out.close();
    in.close();

}
    
}
