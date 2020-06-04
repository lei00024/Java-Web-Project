/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;
import entity.Item;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import static junit.framework.Assert.assertEquals;
import java.util.Map;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 *
 * @author leizhe
 */
public class ItemLogicTest {

    private ItemLogic logic;
    private Map<String, String[]> sampleMap;
    private Map<String, String[]> sampleMap1;
    private static Tomcat tomcat;

    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat();
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }

    @BeforeEach
    protected final void setUp() throws Exception {
        logic = new ItemLogic();
        sampleMap = new HashMap<>();
        sampleMap1=new HashMap<>();
        sampleMap.put(ItemLogic.DESCRIPTION, new String[]{"desc"});
        //sampleMap.put(ItemLogic.CATEGORY_ID, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getCategory().getId())});
        //sampleMap.put(ItemLogic.IMAGE_ID, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getImage().getId())});
        sampleMap.put(ItemLogic.LOCATION, new String[]{"loc"});
        sampleMap.put(ItemLogic.PRICE, new String[]{"1.1"});
        sampleMap.put(ItemLogic.TITLE, new String[]{"tit"});
        sampleMap.put(ItemLogic.DATE, new String[]{"2020-01-01"});
        sampleMap.put(ItemLogic.URL, new String[]{"url"});
        sampleMap.put(ItemLogic.ID, new String[]{"1"});
        
        //used in testCreateEntity
        sampleMap1 = new HashMap<>();
        sampleMap1.put(ItemLogic.DESCRIPTION, new String[]{new ItemLogic().getAll().get(0).getDescription()});
        //sampleMap.put(ItemLogic.CATEGORY_ID, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getCategory().getId())});
        //sampleMap.put(ItemLogic.IMAGE_ID, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getImage().getId())});
        sampleMap1.put(ItemLogic.LOCATION, new String[]{new ItemLogic().getAll().get(0).getLocation()});
        sampleMap1.put(ItemLogic.PRICE, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getPrice())});
        sampleMap1.put(ItemLogic.TITLE, new String[]{new ItemLogic().getAll().get(0).getTitle()});
        sampleMap1.put(ItemLogic.DATE, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getDate())});
        sampleMap1.put(ItemLogic.URL, new String[]{new ItemLogic().getAll().get(0).getUrl()});
        sampleMap1.put(ItemLogic.ID, new String[]{String.valueOf(new ItemLogic().getAll().get(0).getId())});
    }

    @AfterEach
    protected final void tearDown() throws Exception {
    }

    @Test
//    @Disabled
    final void testGetAll() {

        List<Item> list = logic.getAll();
        int originalSize = list.size();
        Item testItem = logic.createEntity(sampleMap);
        testItem.setImage(new ImageLogic().getAll().get(0));
        testItem.setCategory(new CategoryLogic().getAll().get(0));
        logic.add(testItem);
        list = logic.getAll();
        assertEquals(originalSize + 1, list.size());
        logic.delete(testItem);
        list = logic.getAll();
        assertEquals(originalSize, list.size());
    }

    @Test
//    @Disabled
    final void testGetWithId() {
        Item item=new ItemLogic().getAll().get(0);
        assertEquals(new ItemLogic().getWithId(item.getId()),item);
    }

    @Test
//    @Disabled
    final void testGetWithPrice() {
        List<Item> list = logic.getAll();
        Item testEntity1 = list.get(0);
        List<Item> testList = logic.getWithPrice(testEntity1.getPrice());
        for (Item e : testList) {
            assertEquals(testEntity1.getPrice(), e.getPrice());
        }
    }

    @Test
//    @Disabled
    final void testGetWithTitle() {
        List<Item> list = logic.getAll();
        Item testEntity1 = list.get(0);
        List<Item> testList = logic.getWithTitle(testEntity1.getTitle());
        for (Item e : testList) {
            assertEquals(testEntity1.getTitle(), e.getTitle());
        }
    }

    @Test
//    @Disabled
    final void testGetWithDate() {
        List<Item> list = logic.getAll();
        Item testEntity1 = list.get(0);
        List<Item> testList = logic.getWithDate(testEntity1.getDate().toString());
        for (Item e : testList) {
            assertEquals(testEntity1.getDate(), e.getDate());
        }
    }

    @Test
//    @Disabled
    final void testGetWithLocation() {
        List<Item> list = logic.getAll();
        Item testEntity1 = list.get(0);
        List<Item> testList = logic.getWithLocation(testEntity1.getLocation());
        for (Item e : testList) {
            assertEquals(testEntity1.getLocation(), e.getLocation());
        }
    }

    @Test
//    @Disabled
    final void testGetWithDescription() {
        List<Item> list = logic.getAll();
        Item testEntity1 = list.get(0);
        List<Item> testList = logic.getWithDescription(testEntity1.getDescription());
        for (Item e : testList) {
            assertEquals(testEntity1.getDescription(), e.getDescription());
        }
    }

    @Test
//    @Disabled
    final void testGetWithUrl() {
        List<Item> list = logic.getAll();
        Item item1 = list.get(0);
        Item item2 = logic.getWithUrl(item1.getUrl());

        //assertEquals(item1.getId(), item2.getId());
        assertEquals(item1.getDescription(), item2.getDescription());
        assertEquals(item1.getCategory(), item2.getCategory());
        assertEquals(item1.getImage(), item2.getImage());
        assertEquals(item1.getLocation(), item2.getLocation());
        assertEquals(item1.getPrice(), item2.getPrice());
        assertEquals(item1.getTitle(), item2.getTitle());
        assertEquals(item1.getDate(), item2.getDate());
        assertEquals(item1.getUrl(), item2.getUrl());

    }

    @Test
//    @Disabled
    final void testGetWithCategory() {
        Item testEntity1 = logic.getAll().get(0);
        assertEquals(new ItemLogic().getWithCategory(testEntity1.getCategory().getId()).get(0),testEntity1);
    }

    @Test
//    @Disabled
    final void testSearch() {
        List<Item> testList = logic.search(new ItemLogic().getAll().get(0).getUrl());
        assertEquals(testList.get(0), logic.getAll().get(0));
        
    }

    @Test
//    @Disabled
    final void testCreateEntity() {
        Item testItem = logic.createEntity(sampleMap1);
        assertEquals(new ItemLogic().getAll().get(0), testItem);
    }

    @Test
//    @Disabled
    final void testGetColumnNames() {
        List<String> testList = logic.getColumnNames();

        assertEquals(testList.get(0), "id");
        assertEquals(testList.get(1), "imageId");
        assertEquals(testList.get(2), "categoryId");
        assertEquals(testList.get(3), "price");
        assertEquals(testList.get(4), "title");
        assertEquals(testList.get(5), "date");
        assertEquals(testList.get(6), "location");
        assertEquals(testList.get(7), "description");
        assertEquals(testList.get(8), "url");

    }

    @Test
//    @Disabled
    final void testGetColumnCodes() {
        List<String> testList = logic.getColumnCodes();
    
        assertEquals(testList.get(0), ItemLogic.ID);
        assertEquals(testList.get(1), ItemLogic.IMAGE_ID);
        assertEquals(testList.get(2), ItemLogic.CATEGORY_ID);
        assertEquals(testList.get(3), ItemLogic.PRICE);
        assertEquals(testList.get(4), ItemLogic.TITLE);
        assertEquals(testList.get(5), ItemLogic.DATE);
        assertEquals(testList.get(6), ItemLogic.LOCATION);
        assertEquals(testList.get(7), ItemLogic.DESCRIPTION);
        assertEquals(testList.get(8), ItemLogic.URL);

    }

    @Test
//    @Disabled
    final void testExtractDataAsList() {
        List<Item> list = logic.getAll();
        Item e = list.get(0);
        List<?> testList = logic.extractDataAsList(e);

        assertEquals(testList.get(0), e.getId());
        assertEquals(testList.get(1), e.getImage().getId());
        assertEquals(testList.get(2), e.getCategory().getId());
        assertEquals(testList.get(3), e.getPrice());
        assertEquals(testList.get(4), e.getTitle());
        assertEquals(testList.get(5), e.getDate());
        assertEquals(testList.get(6), e.getLocation());
        assertEquals(testList.get(7), e.getDescription());
        assertEquals(testList.get(8), e.getUrl());

    }

}
