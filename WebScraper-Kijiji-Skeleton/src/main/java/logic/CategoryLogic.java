/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.CategoryDAL;
import entity.Category;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

/**
 *
 * @author leizhe
 */
public class CategoryLogic extends GenericLogic<Category, CategoryDAL>{
    
    public static final String TITLE = "title";
    public static final String URL = "url";
    public static final String ID = "id";

    public CategoryLogic() {
        super(new CategoryDAL());
    }
    
    @Override
    public List<Category> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Category getWithId(int id){
        return get(()->dao().findById(id));
    }
    
    public Category getWithUrl(String url){
        return get(()->dao().findByUrl(url));
    }
    
    public Category getWithTitle(String title){
        return get(()->dao().findByTitle(title));
    }
     
    @Override
    public List<Category> search(String search){
        return get(()->dao().findContaining(search));
    }
     
    @Override
    public Category createEntity(Map<String, String[]> parameterMap) {
        Category category = new Category();
        //setid
        if(parameterMap.containsKey(ID)){
            category.setId(Integer.parseInt(parameterMap.get(ID)[0]));
        }
        //setUrl
        if (parameterMap.containsKey(URL) && parameterMap.get(URL) != null){
            String url = parameterMap.get(URL)[0];
            if (url.length() > 255){
                throw new ValidationException("Url must be between 1 and 255 characters.");
            }
            category.setUrl(parameterMap.get(URL)[0]);
        } else {
            throw new ValidationException("Url must exist.");
        }
        //setTitle
        if (parameterMap.containsKey(TITLE) && parameterMap.get(TITLE) != null){
            String title = parameterMap.get(TITLE)[0];
            if (title.length() > 255){
                throw new ValidationException("Title must be between 1 and 255 characters.");
            }
            category.setTitle(parameterMap.get(TITLE)[0]);
        } else {
            throw new ValidationException("Url must exist.");
        }
       
        return category;
    }
    
    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("Id", "Url", "Title");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, URL, TITLE);
    }

    @Override
    public List<?> extractDataAsList(Category e) {
        return Arrays.asList(e.getId(), e.getUrl(), e.getTitle());
    }

    

    
}
