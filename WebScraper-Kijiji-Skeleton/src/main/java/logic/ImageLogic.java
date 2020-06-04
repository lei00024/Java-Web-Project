/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.ImageDAL;
import entity.Image;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author leizhe
 */
public class ImageLogic extends GenericLogic<Image, ImageDAL>{

    public static final String PATH = "path";
    public static final String NAME = "name";
    public static final String URL = "url";
    public static final String ID = "id";
    
    public ImageLogic(){
        super(new ImageDAL());
    }
    
    @Override
    public List<Image> getAll(){
        return get(()->dao().findAll());
    }
    
    @Override
    public Image getWithId(int id){
        return get(()->dao().findById(id));
    }
    
    public List<Image> getWithUrl(String url){
        return get(()->dao().findByUrl(url));
    }
    
    public Image getWithPath(String path){
        return get(()->dao().findByPath(path));
    }
    
    public List<Image> getWithName(String name){
        return get(()->dao().findByName(name));
    }
    
    @Override
    public List<Image> search(String search){
        return get(()->dao().findContaining(search));
    }
    
    @Override
    public Image createEntity(Map<String, String[]> parameterMap) {
        Image image = new Image();
        //set id
        if(parameterMap.containsKey(ImageLogic.ID)){
            image.setId(Integer.parseInt(parameterMap.get(ImageLogic.ID)[0]));
        }
        //set url
        if(parameterMap.containsKey(URL) && parameterMap.get(URL)!= null){
            if (parameterMap.get(URL)[0].isEmpty() || parameterMap.get(URL)[0].length() > 255){
                throw new ValidationException("Invalid url input, url must be between 1 and 255 characters.");
            }
            image.setUrl(parameterMap.get(ImageLogic.URL)[0]);
        } else {
            throw new ValidationException("Url not exist");
        }
        //set path
        if(parameterMap.containsKey(PATH) && parameterMap.get(PATH) != null){
            if (parameterMap.get(PATH)[0].isEmpty() || parameterMap.get(PATH)[0].length() > 255){
                throw new ValidationException("Invalid path input, path must be between 1 and 255 characters.");
            }
            image.setPath(parameterMap.get(ImageLogic.PATH)[0]);
        } else {
            throw new ValidationException("Path not exist");
        }
        //set path
        if(parameterMap.containsKey(NAME) && parameterMap.get(NAME) != null){
            if (parameterMap.get(NAME)[0].isEmpty() || parameterMap.get(NAME)[0].length() > 255){
                throw new ValidationException("Invalid name input, name must be between 1 and 255 characters.");
            }
            image.setName(parameterMap.get(ImageLogic.NAME)[0]);
        } else {
            throw new ValidationException("Name not exist");
        }
        
        return image;
    } 

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "url", "path", "name");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ImageLogic.ID, ImageLogic.URL, ImageLogic.PATH, ImageLogic.NAME);
    }

    @Override
    public List<?> extractDataAsList( Image e) {
        return Arrays.asList(e.getId(), e.getUrl(), e.getPath(), e.getName());
    } 

    
}
