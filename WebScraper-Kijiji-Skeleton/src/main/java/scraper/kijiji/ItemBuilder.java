/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper.kijiji;
import java.util.Objects;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author leizhe
 */
public class ItemBuilder {
    
    private static final String URL_BASE = "https://www.kijiji.ca";
    private static final String ATTRIBUTE_ID = "data-listing-id";
    private static final String ATTRIBUTE_IMAGE = "image";
    private static final String ATTRIBUTE_IMAGE_SRC = "data-src";
    private static final String ATTRIBUTE_IMAGE_NAME = "alt";
    private static final String ATTRIBUTE_PRICE = "price";
    private static final String ATTRIBUTE_TITLE = "title";
    private static final String ATTRIBUTE_LOCATION = "location";
    private static final String ATTRIBUTE_DATE = "date-posted";
    private static final String ATTRIBUTE_DESCRIPTION = "description";
    
    private Element element;
    private KijijiItem item;
    
    ItemBuilder(){
        item = new KijijiItem();
    }

    public ItemBuilder setElement(Element element){
        this.element = element;
        return this;
    }
    
    public KijijiItem build(){       
        //this.item = new KijijiItem();
        
        //if (element.hasAttr(ATTRIBUTE_ID)){
            item.setId(element.attr(ATTRIBUTE_ID).trim());
        //}
        
        Elements elementImg = element.getElementsByClass(ATTRIBUTE_IMAGE);
        if (!elementImg.isEmpty()){
            item.setImageUrl(elementImg.get(0).child(0).attr(ATTRIBUTE_IMAGE_SRC).trim());
        } else {
            item.setImageUrl("");          
        }
        
        if (!elementImg.isEmpty()) {
            item.setImageName(elementImg.get(0).child(0).attr(ATTRIBUTE_IMAGE_NAME).trim());
        } else {
            item.setImageName("");
        }

        Elements elementPrice = element.getElementsByClass(ATTRIBUTE_PRICE);
        if(!elementPrice.isEmpty()){   
            item.setPrice(elementPrice.get(0).text().trim());
        } else {
            item.setPrice("");
        }
        
        Elements elementTitle = element.getElementsByClass(ATTRIBUTE_TITLE);
        if(!elementTitle.isEmpty()){   
            item.setTitle(elementTitle.get(0).text().trim());
        } else {
            item.setTitle("");
        }

        Elements elementLocation = element.getElementsByClass(ATTRIBUTE_LOCATION);
        if(!elementLocation.isEmpty()){   
            item.setLocation(elementLocation.get(0).childNode(0).outerHtml().trim());
        } else {
            item.setLocation("");
        }

        Elements elementDate = element.getElementsByClass(ATTRIBUTE_DATE);
        if(!elementDate.isEmpty()){            
            item.setDate(elementDate.get(0).text().trim());
        } else {
            item.setDate("");
        }
        
        Elements elementDesc = element.getElementsByClass(ATTRIBUTE_DESCRIPTION);
        if(!elementDesc.isEmpty()){            
            item.setDescription(elementDesc.get(0).text().trim());
        } else {
            item.setDescription("");
        }
        
        item.setUrl(URL_BASE+element.getElementsByClass(ATTRIBUTE_TITLE).get(0).child(0).attr("href").trim());

        return item;
    }
    
}
