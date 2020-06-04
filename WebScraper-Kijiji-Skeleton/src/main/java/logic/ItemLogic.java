/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.ValidationException;
import dal.CategoryDAL;
import dal.ItemDAL;
import entity.Category;
import entity.Image;
import entity.Item;
import java.math.BigDecimal;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author leizhe
 */
public class ItemLogic extends GenericLogic<Item, ItemDAL> {

    public static String DESCRIPTION = "description";
    public static String CATEGORY_ID = "categoryId";
    public static String IMAGE_ID = "imageId";
    public static String LOCATION = "location";
    public static String PRICE = "price";
    public static String TITLE = "title";
    public static String DATE = "date";
    public static String URL = "url";
    public static String ID = "id";

    public ItemLogic() {
        super(new ItemDAL());
    }

    @Override
    public List<Item> getAll() {
        return get(() -> dao().findAll());
    }

    @Override
    public Item getWithId(int id) {
        return get(() -> dao().findById(id));
    }

    public List<Item> getWithPrice(BigDecimal price) {
        return get(() -> dao().findByPrice(price));
    }

    public List<Item> getWithTitle(String title) {
        return get(() -> dao().findByTitle(title));
    }

    public List<Item> getWithDate(String date) {
        return get(() -> dao().findByDate(date));
    }

    public List<Item> getWithLocation(String location) {
        return get(() -> dao().findByLocation(location));
    }

    public List<Item> getWithDescription(String description) {
        return get(() -> dao().findByDescription(description));
    }

    public Item getWithUrl(String url) {
        return get(() -> dao().findByUrl(url));
    }

    public List<Item> getWithCategory(int categoryId) {
        return get(() -> dao().findByCategory(categoryId));
    }

    @Override
    public List<Item> search(String search) {
        return get(() -> dao().findContaining(search));
    }

    @Override
    public Item createEntity(Map<String, String[]> parameterMap) {
        Item item = new Item();
        //setID
        if (parameterMap.containsKey(ItemLogic.ID)) {
            item.setId(Integer.parseInt(parameterMap.get(ItemLogic.ID)[0]));
        }

        //setPrice
        try{
            String newPriceFormat="";
            String price=parameterMap.get(PRICE)[0];
            if(price.contains("$")||price.contains(",")){
                if(price.contains("$")&&!price.contains(",")){
                    newPriceFormat=price.replace("$", "");
                }
                if(!price.contains("$")&&price.contains(",")){
                    newPriceFormat=price.replace(",", "");
                }
                if(price.contains("$")&&price.contains(",")){
                    newPriceFormat=price.replace(",", "");
                    newPriceFormat=newPriceFormat.replace("$", "");
                }

                item.setPrice(new BigDecimal(newPriceFormat));
                }else{
                    item.setPrice(new BigDecimal(price));
                }
        }catch(Exception e){
            
        }

        //setTitle
        String title = parameterMap.get(TITLE)[0];
        item.setTitle(title);

        //setDate
        try {
            item.setDate(new SimpleDateFormat("yyyy-MM-dd").parse(parameterMap.get(DATE)[0]));
        } catch (ParseException ex) {
            item.setDate(new Date());
        }

        //set location
        item.setLocation(parameterMap.get(ItemLogic.LOCATION)[0]);
        
        //set Description
        item.setDescription(parameterMap.get(ItemLogic.DESCRIPTION)[0]);
        item.setUrl(parameterMap.get(ItemLogic.URL)[0]);

        return item;
    }

    @Override
    public List<String> getColumnNames() {
        return Arrays.asList("id", "imageId", "categoryId", "price", "title", "date", "location", "description", "url");
    }

    @Override
    public List<String> getColumnCodes() {
        return Arrays.asList(ID, IMAGE_ID, CATEGORY_ID, PRICE, TITLE, DATE, LOCATION, DESCRIPTION, URL);
    }

    @Override
    public List<?> extractDataAsList(Item e) {
        return Arrays.asList(e.getId(), e.getImage().getId(), e.getCategory().getId(), e.getPrice(), e.getTitle(), e.getDate(), e.getLocation(), e.getDescription(),
                e.getUrl());
    }

}
