/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package scraper.kijiji;

import java.util.Objects;

/**
 *
 * @author leizhe
 */
public class KijijiItem {
    
    private String id;
    private String url;
    private String imageUrl;
    private String imageName;
    private String price;
    private String title;
    private String date;
    private String location;
    private String description;
    
    
    KijijiItem(){
    }
    
    public String getDate(){
        return date;
    }
    void setUrl(String url){
        this.url = url;
    }
    
    public String getUrl(){
        return url;
    }
    
    void setId(String id){
        this.id = id;
    }
    
    public String getId(){
        return id;
    }
    
    void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }
    
    public String getImageUrl(){
        return imageUrl;
    }
    
    void setImageName(String imageName){
        this.imageName = imageName;
    }
    
    public String getImageName(){
        return imageName;
    }
    
    void setPrice(String price){
        this.price = price;
    }
    
    public String getPrice(){
        return price;
    }
    
    void setTitle(String title){
        this.title = title;
    }
    
    public String getTitle(){
        return title;
    }
    
    void setDate(String date){
        this.date = date;
    }
    
    void setLocation(String location){
        this.location = location;
    }
    
    public String getLocation(){
        return location;
    }
    
    void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.url);
        hash = 83 * hash + Objects.hashCode(this.imageUrl);
        hash = 83 * hash + Objects.hashCode(this.imageName);
        hash = 83 * hash + Objects.hashCode(this.price);
        hash = 83 * hash + Objects.hashCode(this.title);
        hash = 83 * hash + Objects.hashCode(this.date);
        hash = 83 * hash + Objects.hashCode(this.location);
        hash = 83 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final KijijiItem other = (KijijiItem) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.url, other.url)) {
            return false;
        }
        if (!Objects.equals(this.imageUrl, other.imageUrl)) {
            return false;
        }
        if (!Objects.equals(this.imageName, other.imageName)) {
            return false;
        }
        if (!Objects.equals(this.price, other.price)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }
    


    @Override
    public String toString() {
        return String.format("[id:%s, image_url:%s, image_name:%s, price:%s, title:%s, date:%s, location:%s, description:%s]",
                id, imageUrl, imageName, price, title, date, location, description);
    } 
    
    
}
