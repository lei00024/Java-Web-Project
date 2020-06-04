/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import common.ValidationException;
import entity.Category;
import entity.Image;
import entity.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.CategoryLogic;
import logic.ImageLogic;
import logic.ItemLogic;
import static logic.ItemLogic.CATEGORY_ID;
import static logic.ItemLogic.IMAGE_ID;
import org.apache.maven.surefire.shade.org.apache.commons.lang3.math.NumberUtils;

/**
 *
 * @author leizhe
 */
@WebServlet(name = "CreateItem", urlPatterns = {"/CreateItem"})
public class CreateItem extends HttpServlet {

    private String errorMessage = null;
    static String url="";
    static String category="";
    static String image="";
    static String id="";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Create Item</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<div style=\"text-align: center;\">");
            out.println("<div style=\"display: inline-block; text-align: left;\">");
            out.println("<form method=\"post\">");
            out.println("ID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.ID);
            out.println("<br>");
            out.println("ImageID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.IMAGE_ID);
            out.println("<br>");
            out.println("CategoryID:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.CATEGORY_ID);
            out.println("<br>");
            out.println("Price:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.PRICE);
            out.println("<br>");
            out.println("Title:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.TITLE);
            out.println("<br>");
            out.println("Date:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.DATE);
            out.println("<br>");
            out.println("Location:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.LOCATION);
            out.println("<br>");
            out.println("Description:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.DESCRIPTION);
            out.println("<br>");
            out.println("Url:<br>");
            out.printf("<input type=\"text\" name=\"%s\" value=\"\"><br>", ItemLogic.URL);
            out.println("<br>");
            out.println("<input type=\"submit\" name=\"view\" value=\"Add and View\">");
            out.println("<input type=\"submit\" name=\"add\" value=\"Add\">");
            out.println("</form>");
            if (errorMessage != null && !errorMessage.isEmpty()) {
                out.println("<p color=red>");
                out.println("<font color=red size=4px>");
                out.println(errorMessage);
                out.println("</font>");
                out.println("</p>");
            }
            out.println("<pre>");
            out.println("Submitted keys and values:");
            out.println(toStringMap(request.getParameterMap()));
            out.println("</pre>");
            out.println("</div>");
            out.println("</div>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    private String toStringMap(Map<String, String[]> values) {
        StringBuilder builder = new StringBuilder();
        values.forEach((k, v) -> builder.append("Key=").append(k)
                .append(", ")
                .append("Value/s=").append(Arrays.toString(v))
                .append(System.lineSeparator()));
        return builder.toString();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * get method is called first when requesting a URL. since this servlet will
     * create a host this method simple delivers the html code. creation will be
     * done in doPost method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("GET");
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * this method will handle the creation of entity. as it is called by user
     * submitting data through browser.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("POST");

        ItemLogic iLogic = new ItemLogic();
        ImageLogic imgLogic=new ImageLogic();
        Image img=new Image();
        //test if fields are empty
        if(!request.getParameter(ItemLogic.ID).isEmpty()&&!request.getParameter(ItemLogic.TITLE).isEmpty()&&
                !request.getParameter(ItemLogic.DESCRIPTION).isEmpty()&&
                !request.getParameter(ItemLogic.URL).isEmpty()&&
                !request.getParameter(ItemLogic.CATEGORY_ID).isEmpty()&&
                !request.getParameter(ItemLogic.IMAGE_ID).isEmpty()){
            if(request.getParameter(ItemLogic.TITLE).length()<=255&&
                    request.getParameter(ItemLogic.PRICE).length()<=15&&
                    request.getParameter(ItemLogic.LOCATION).length()<=45&&
                    request.getParameter(ItemLogic.DESCRIPTION).length()<=255&&
                    request.getParameter(ItemLogic.URL).length()<=255){
                if(iLogic.getWithId(Integer.valueOf(request.getParameter(ItemLogic.ID)))==null){
                    if(!iLogic.getWithCategory(Integer.parseInt(request.getParameter(ItemLogic.CATEGORY_ID))).isEmpty()){
                        if(imgLogic.getWithId(Integer.valueOf(request.getParameter(ItemLogic.IMAGE_ID)))!=null){
                            if(iLogic.getWithUrl(request.getParameter(ItemLogic.URL))==null){
                                    Item item = iLogic.createEntity(request.getParameterMap());
                                    int categoryId = Integer.parseInt(request.getParameter(ItemLogic.CATEGORY_ID));
                                    CategoryLogic catLogic = new CategoryLogic();
                                    Category cat = catLogic.getWithId(categoryId);
                                    int imageId = Integer.parseInt(request.getParameter(ItemLogic.IMAGE_ID));
                                    ImageLogic imgLogic2 = new ImageLogic();
                                    img = imgLogic2.getWithId(imageId);
                                    item.setImage(img);
                                    item.setCategory(cat);
                                    iLogic.add(item);
                                
                            }else{
                                errorMessage="Url: \""+url+"\"already exists";
                            }
                        }else{
                            errorMessage="Image_ID: \"" +image+"\"not exist";
                        }
                    }else{
                        errorMessage= "Category_ID: \"" + category + "\" not exist";
                    }
                }else{
                    errorMessage= "ID: \"" + id + "\" already exists";
                }
            }else{
                errorMessage="Invalid inputs. Title, description, url should be within 1 to 255 characters. The length of location is within 1 to 45, the length of price is within 1 to 15(2 decimal places)"; 
            }
        }else{
            errorMessage="ID, Image_ID, Category_ID, Title, description, url should not be empty";
        }


        if (request.getParameter("add") != null) {
            //if add button is pressed return the same page
            processRequest(request, response);
        } else if (request.getParameter("view") != null) {
            //if view button is pressed redirect to the appropriate table
            response.sendRedirect("ItemTable");
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Create an item Entity";
    }

    private static final boolean DEBUG = true;

    public void log(String msg) {
        if (DEBUG) {
            String message = String.format("[%s] %s", getClass().getSimpleName(), msg);
            getServletContext().log(message);
        }
    }

    public void log(String msg, Throwable t) {
        String message = String.format("[%s] %s", getClass().getSimpleName(), msg);
        getServletContext().log(message, t);
    }

}
