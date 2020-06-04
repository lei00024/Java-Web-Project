/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Item;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import logic.AccountLogic;
import logic.ItemLogic;

/**
 *
 * @author leizhe
 */
@WebServlet(name = "ItemTableFancy", urlPatterns = {"/ItemTableFancy"})
public class ItemTableViewFancy extends HttpServlet {

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
            out.println("<title>ItemViewFancy</title>");
            //https://www.w3schools.com/css/css_table.asp
            out.println("<style>");
            out.println("table {border-collapse: collapse; width: auto; margin-left: auto; margin-right: auto;}");
            out.println("th, td {text-align: left;padding: 8px;}");
            out.println("tr:nth-child(even) {background-color: #f2f2f2;}");
            out.println("td.edit{width:65px;}");
            out.println("td.delete{text-align: center;}");
            out.println("input.editor{width: 100%;}");
            out.println("input.update{width: 100%;}");
            out.println("</style>");
            out.println("<script>");
            out.println("var isEditActive = false;");
            out.println("var activeEditID = -1;");
            out.println("function createTextInput(text, name) {");
            out.println("var node = document.createElement(\"input\");");
            out.println("node.name = name;");
            out.println("node.className = \"editor\";");
            out.println("node.type = \"text\";");
            out.println("node.value = text;");
            out.println("return node;");
            out.println("}");
            out.println("function convertCellToInput( id, readOnly, name){");
            out.println("var idCell = document.getElementById(id);");
            out.println("var idInput = createTextInput(idCell.innerText, name);");
            out.println("idInput.readOnly = readOnly;");
            out.println("idCell.innerText = null;");
            out.println("idCell.appendChild(idInput);");
            out.println("}");
            out.println("window.onload = function () {");
            out.println("var elements = document.getElementsByClassName(\"edit\");");
            out.println("for (let i = 0; i < elements.length; i++) {");
            out.println("elements[i].childNodes[0].onclick = function () {");
            out.println("var id = elements[i].id;");
            out.println("if (isEditActive) {");
            out.println("if (activeEditID === id) {");
            out.println("this.type = \"submit\";");
            out.println("}");
            out.println("return;");
            out.println("}");
            out.println("isEditActive = true;");
            out.println("activeEditID = id;");
            out.println("this.value = \"Update\";");
            ItemLogic logic = new ItemLogic();//|*****************
            logic.getColumnCodes().forEach((columnCode) -> {
                out.printf("convertCellToInput( ++id, false, %s);", columnCode);
            });
            out.println("};");
            out.println("}");
            out.println("};");
            out.println("</script>");
            out.println("</head>");
            out.println("<body>");
            //https://www.w3schools.com/css/css_table.asp
            out.println("<form method=\"post\">");
            out.println("<table>");
            out.println("<tr>");
            out.println("<td><input type=\"text\" name=\"searchText\" /></td>");
            out.println("<td><input type=\"submit\" name=\"search\" value=\"Search\" /></td>");
            out.println("</tr>");
            out.println("</table>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th><input type=\"submit\" name=\"delete\" value=\"Delete\" /></th>");
            out.println("<th>Edit</th>");
            logic.getColumnNames().forEach((columnName) -> {
                out.printf("<th>%s</th>", columnName);
            });
            out.println("</tr>");
            List<Item> entities = logic.getAll();
            long counter = 0;
            for (Item entity : entities) {
                out.println("<tr>");
                out.println("<td class=\"delete\">");
                out.printf("<input type=\"checkbox\" name=\"deleteMark\" value=\"%d\" />", entity.getId());
                out.println("</td>");
                out.printf("<td class=\"edit\" id=\"%d\" ><input class=\"update\" type=\"button\" name=\"edit\" value=\"Edit\" /></td>", counter++);
                List<?> data = logic.extractDataAsList(entity);
                List<String> codes = logic.getColumnCodes();
                for(int i=0; i<data.size();i++){
                    out.printf("<td class=\"%s\" id=\"%d\" >%s</td>", codes.get(i), counter++, data.get(i));
                }
                out.println("</tr>");
            }
            out.println("<tr>");
            out.println("<th><input type=\"submit\" name=\"delete\" value=\"Delete\" /></th>");
            out.println("<th>Edit</th>");
            logic.getColumnNames().forEach((columnName) -> {
                out.printf("<th>%s</th>", columnName);
            });
            out.println("</tr>");
            out.println("</table>");
            out.println("</form>");
            out.printf("<div style=\"text-align: center;\"><pre>%s</pre></div>", toStringMap(request.getParameterMap()));
            out.println("</body>");
            out.println("</html>");
            
        //bonus add search function
        String searchText=request.getParameter("searchText");
        if(searchText!=null&&request.getParameter("search")!=null){

        List<Item> item_list=new ItemLogic().search(searchText);
        if(item_list.size()>0){
            out.println("Search Result(s):");
            out.println("<br>");
            out.println("<br>");
            for(Item item:item_list){
                out.println("Id: "+item.getId()+"<br>"+"Image_id: "+item.getImage().getId().toString()+"<br>"+"Category_id: "+item.getCategory().getId().toString() +
                        "<br>"+"Price: "+item.getPrice().toString() + "<br>"+"Title: "+item.getTitle() + 
                        "<br>"+"Date: "+item.getDate().toString() + "<br>"+"Location: "+item.getLocation() + 
                        "<br>"+"Description: "+item.getDescription() + "<br>"+"Url: "+item.getUrl());
                out.println("<br>");
                out.println("<br>");
            }
            out.println("Total Item(s) found: "+item_list.size());
        }else{
            out.println("No matching result(s) found.");
        }
        }
        
        /**bonus Delete**/
        String[] delArray=request.getParameterValues("deleteMark");
        if(delArray.length!=0){
            for(int i=0;i<delArray.length;i++){
                int itemId=Integer.parseInt(delArray[i]);
                Item item=new ItemLogic().getWithId(itemId);
                new ItemLogic().delete(item);
                
                Item item_test=new ItemLogic().getWithId(itemId);
                if(item_test==null){
                    out.println("Selected Item "+i+" deleted!");
                    out.println("<br>");
                }else{
                    out.println("Deletion failed.");
                }
            }
        }else{
            out.println("Deletion error.");
        }
        
       
        /**bonus Update**/

            
        }
    }

    private String toStringMap(Map<String, String[]> m) {
        StringBuilder builder = new StringBuilder();
        m.keySet().forEach((k) -> {
            builder.append("Key=").append(k)
                    .append(", ")
                    .append("Value/s=").append(Arrays.toString(m.get(k)))
                    .append(System.lineSeparator());
        });
        return builder.toString();
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        log("GET");
    }

    /**
     * Handles the HTTP <code>POST</code> method.
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
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Sample of Item View Fancy";
    }

    private static final boolean DEBUG = true;

    public void log( String msg) {
        if(DEBUG){
            String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
            getServletContext().log( message);
        }
    }

    public void log( String msg, Throwable t) {
        String message = String.format( "[%s] %s", getClass().getSimpleName(), msg);
        getServletContext().log( message, t);
    }

}
