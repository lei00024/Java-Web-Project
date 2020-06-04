/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;
import entity.Image;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static junit.framework.Assert.assertEquals;
import junit.framework.TestCase;
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
public class ImageLogicTest{
    
    private ImageLogic logic;
    private Map<String, String[]> sampleMap;
    
    @BeforeAll
    final static void setUpBeforeClass() throws Exception {
        TomcatStartUp.createTomcat();
    }

    @AfterAll
    final static void tearDownAfterClass() throws Exception {
        TomcatStartUp.stopAndDestroyTomcat();
    }
    
    @BeforeEach
    protected void setUp() throws Exception {
        //super.setUp();
        logic = new ImageLogic();  
        sampleMap = new HashMap<>();
        sampleMap.put(ImageLogic.URL, new String[]{"junit"});
        sampleMap.put(ImageLogic.PATH, new String[]{"junit5Test"});
        sampleMap.put(ImageLogic.NAME, new String[]{"junit5"});
    }
    
    @AfterEach
    protected void tearDown() throws Exception {
        //super.tearDown();
    }
    
    @Test
//    @Disabled
    final void testGetAll(){
        List<Image> list = logic.getAll();
        int originalSize = list.size();
        Image testImage = logic.createEntity(sampleMap);
        logic.add(testImage);
        list = logic.getAll();
        assertEquals(originalSize + 1, list.size());
        logic.delete(testImage);
        list = logic.getAll();
        assertEquals(originalSize, list.size());
    }
    
    @Test
//    @Disabled
    final void testGetWithId(){
        List<Image> list = logic.getAll();

        Image image1 = list.get(0);
        Image image2 = logic.getWithId(image1.getId());

        assertEquals(image1.getId(), image2.getId());
        assertEquals(image1.getUrl(), image2.getUrl());
        assertEquals(image1.getPath(), image2.getPath());
        assertEquals(image1.getName(), image2.getName());
    }
    
    @Test
//    @Disabled
    final void testGetWithUrl(){
        List<Image> list = logic.getAll();
        Image testEntity1 = list.get(0);
        List<Image> testList = logic.getWithUrl(testEntity1.getUrl());
        for (Image e : testList) {
            assertEquals(testEntity1.getUrl(), e.getUrl());
        }
        
    }
    
    @Test
//    @Disabled
    final void testGetWithPath(){
        List<Image> list = logic.getAll();

        Image image1 = list.get(0);
        Image image2 = logic.getWithPath(image1.getPath());

        assertEquals(image1.getId(), image2.getId());
        assertEquals(image1.getUrl(), image2.getUrl());
        assertEquals(image1.getPath(), image2.getPath());
        assertEquals(image1.getName(), image2.getName());
    }
    
    @Test
//    @Disabled
    final void testGetWithName(){
        List<Image> list = logic.getAll();
        Image testEntity1 = list.get(0);
        List<Image> testList = logic.getWithName(testEntity1.getName());
        for (Image e : testList) {
            assertEquals(testEntity1.getName(), e.getName());
        }
    }
    
    @Test
//    @Disabled
    final void testSearch(){
        List<Image> testList = logic.search("t");
        for (Image e : testList) {
            assertTrue(e.getName().contains("t") || e.getUrl().contains("t") || e.getPath().contains("t"));
        }
    }
    
    @Test
//    @Disabled
    final void testCreateEntity(){
        Image testImage = logic.createEntity(sampleMap);
        logic.add(testImage);

        assertEquals(testImage.getUrl(), "junit");
        assertEquals(testImage.getPath(), "junit5Test");
        assertEquals(testImage.getName(), "junit5");

        logic.delete(testImage);
    }
    
    @Test
//    @Disabled
    final void testGetColumnNames(){
        List<Image> list = logic.getAll();
        Image e = list.get(0);
        List<String> testList = logic.getColumnCodes();
        assertEquals(testList.get(0), "id");
        assertEquals(testList.get(1), "url");
        assertEquals(testList.get(2), "path");
        assertEquals(testList.get(3), "name");
    }
    
    @Test
//    @Disabled
    final void testGetColunmCodes(){
        List<Image> list = logic.getAll();
        Image e = list.get(0);
        List<String> testList = logic.getColumnCodes();
        assertEquals(testList.get(0), ImageLogic.ID);
        assertEquals(testList.get(1), ImageLogic.URL);
        assertEquals(testList.get(2), ImageLogic.PATH);
        assertEquals(testList.get(3), ImageLogic.NAME);
    }
    
    @Test
//    @Disabled
    final void testExtractDataAsList(){
        List<Image> list = logic.getAll();
        Image e = list.get(0);
        List<?> testList = logic.extractDataAsList(e);
        assertEquals(testList.get(0), e.getId());
        assertEquals(testList.get(1), e.getUrl());
        assertEquals(testList.get(2), e.getPath());
        assertEquals(testList.get(3), e.getName());
    }
    
}
