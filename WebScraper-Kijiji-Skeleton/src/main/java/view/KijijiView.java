///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package view;

import common.FileUtility;
import dal.ImageDAL;
import entity.Category;
import entity.Image;
import entity.Item;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.AccountLogic;
import logic.CategoryLogic;
import static logic.CategoryLogic.URL;
import logic.ImageLogic;
import logic.ItemLogic;
import scraper.kijiji.BadKijijiItem;
import scraper.kijiji.Kijiji;
import scraper.kijiji.KijijiItem;

/**
 *
 * @author leizhe
 */
@WebServlet(name = "Kijiji", urlPatterns = {"/Kijiji"})
public class KijijiView extends HttpServlet{
    
    private Kijiji kijiji;

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
             List<Item> itemList = new ItemLogic().getAll();
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title >Kijiji</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<caption style=\"font-size:60px;\" style=\"border:2px solid DodgerBlue;\">Kijiji</caption>");
            for(Item item:itemList){
            out.println("<div class=\"left-column\">");
            out.println("<div class=\"item\">");
            out.println("<div class=\"image\">");
            out.println("<img src=\"image/"+item.getId()+".jpg\" style=max-width: 50px; max-height: 20px;/>");
            out.println(" </div>");
            out.println("<div class=\"details\">");
            out.println("<div class=\"title\">");
            out.println("<a style=\"color:red\" href=\""+item.getUrl()+"\" target=\"_blank\">Link to Kijiji</a>");
            out.println("</div>");
            out.println("<div class=\"price\">");
            out.println("$"+String.valueOf(item.getPrice()));
            out.println("</div>");
            //out.println("<div class=\"date\">");
            //out.printf("<div style=\"text-align: center;\"><pre>%s</pre></div>", toStringMap(request.getParameterMap()));
            
            out.println("<div class=\"date\">");
            out.println("Date: "+String.valueOf(item.getDate()));
            out.println("</div>");
            out.println("<div class=\"location\">");
            out.println("Location: "+item.getLocation());
            out.println("</div>");
            out.println("<div class=\"description\">");
            out.println("Item description: "+item.getDescription());
            out.println("<br>");
            out.println("<br>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            out.println("</div>");
            }
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    /***********Reference: By discussion with classmate: Kaiwen Gu***********/
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        log("GET");
        /***********Reference: By discussion with classmate: Kaiwen Gu***********/
        String filePath = System.getProperty("user.home")+"/KijijiImages/";
        if(!Files.exists(Paths.get(filePath))){
            File file;
            file=new File(filePath);
            file.mkdir();
        }
        
        log("GET");
        
        Category cat=new CategoryLogic().getWithTitle("Computer Accessories");
        kijiji=new Kijiji();
        kijiji.downloadPage(cat.getUrl()).findAllItems();
        
        Consumer<KijijiItem> saveItems=(KijijiItem kijijiItem)->{
            Map<String,String[]> itemMap=new HashMap<>();
            Map<String,String[]> imageMap=new HashMap<>();
            Image image=new Image();
            Item item=null;
            
            if(new ItemLogic().getWithId(Integer.parseInt(kijijiItem.getId()))==null){
                if(!kijijiItem.getImageUrl().isEmpty() && !kijijiItem.getImageName().isEmpty()){
                    if(new ImageDAL().findByPath(filePath + kijijiItem.getId() + ".jpg") == null){
                        FileUtility.downloadAndSaveFile(kijijiItem.getImageUrl(), filePath, kijijiItem.getId()+".jpg");
                        
                        imageMap.put(ImageLogic.URL, new String[]{kijijiItem.getImageUrl()});
                        imageMap.put(ImageLogic.NAME, new String[]{kijijiItem.getImageName()});
                        imageMap.put(ImageLogic.PATH, new String[]{filePath + kijijiItem.getId()+".jpg"});
                        
                        try{
                            new ImageLogic().add(image);
                        }catch(Exception e){
                            
                        }
                    }
                }
                itemMap.put(ItemLogic.ID, new String[]{kijijiItem.getId()});
                itemMap.put(ItemLogic.URL, new String[]{kijijiItem.getUrl()});
                itemMap.put(ItemLogic.DATE, new String[]{kijijiItem.getDate()});
                itemMap.put(ItemLogic.TITLE, new String[]{kijijiItem.getTitle()});
                itemMap.put(ItemLogic.PRICE, new String[]{kijijiItem.getPrice()});
                itemMap.put(ItemLogic.LOCATION, new String[]{kijijiItem.getLocation()});
                itemMap.put(ItemLogic.DESCRIPTION, new String[]{kijijiItem.getDescription()});
                
                item = new ItemLogic().createEntity(itemMap);
                item.setCategory(cat);
                item.setImage(image);
                
                try{
                    new ItemLogic().add(item);
                }catch(Exception e){
                    
                }

            }
        };
        kijiji.proccessItems(saveItems);
        processRequest(request,response);
    };
            
        
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "KjijiView";
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
