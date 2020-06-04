/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import common.TomcatStartUp;
import entity.Category;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import junit.framework.TestCase;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author leizhe
 */
public class CategoryLogicTest{
    
    private CategoryLogic logic;
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
        logic = new CategoryLogic();  
        sampleMap = new HashMap<>();
        sampleMap.put(CategoryLogic.URL, new String[]{"junit"});
        sampleMap.put(CategoryLogic.TITLE, new String[]{"junit5"});
    }
    
    @AfterEach
  
    protected void tearDown() throws Exception {
        //super.tearDown();
    }

    @Test
    final void testGetAll(){
        List<Category> list = logic.getAll();
        int originalSize = list.size();
        Category testCategory = logic.createEntity(sampleMap);
        logic.add(testCategory);
        list = logic.getAll();
        assertEquals(originalSize + 1, list.size());
        logic.delete(testCategory);
        list = logic.getAll();
        assertEquals(originalSize, list.size());
    }
    
    @Test
    final void testGetWithId(){
        List<Category> list = logic.getAll();

        Category category1 = list.get(0);
        Category category2 = logic.getWithId(category1.getId());

        assertEquals(category1.getId(), category2.getId());
        assertEquals(category1.getUrl(), category2.getUrl());
        assertEquals(category1.getTitle(), category2.getTitle());
    }
    
    @Test
    final void testGetWithUrl(){
        List<Category> list = logic.getAll();

        Category category1 = list.get(0);
        Category category2 = logic.getWithUrl(category1.getUrl());

        assertEquals(category1.getId(), category2.getId());
        assertEquals(category1.getUrl(), category2.getUrl());
        assertEquals(category1.getTitle(), category2.getTitle());
    }
    
    @Test
    final void testGetWithTitle(){
        List<Category> list = logic.getAll();

        Category category1 = list.get(0);
        Category category2 = logic.getWithTitle(category1.getTitle());

        assertEquals(category1.getId(), category2.getId());
        assertEquals(category1.getUrl(), category2.getUrl());
        assertEquals(category1.getTitle(), category2.getTitle());
    }
    
    @Test
    final void testSearch(){
        List<Category> testList = logic.search("t");
        for (Category e : testList) {
            assertTrue(e.getUrl().contains("t") || e.getTitle().contains("t"));
        }
    }
    
    @Test
    final void testCreateEntity(){
        Category category1 = logic.createEntity(sampleMap);
        logic.add(category1);

        assertEquals(category1.getUrl(), "junit");
        assertEquals(category1.getTitle(), "junit5");

        logic.delete(category1);
    }
    
    @Test
    final void testGetColumnNames(){
        List<String> testList = logic.getColumnNames();
        assertEquals(testList.get(0), "Id");
        assertEquals(testList.get(1), "Url");
        assertEquals(testList.get(2), "Title");
    }
    
    @Test
    final void testGetColumnCodes(){
        List<String> testList = logic.getColumnCodes();
        assertEquals(testList.get(0), CategoryLogic.ID);
        assertEquals(testList.get(1), CategoryLogic.URL);
        assertEquals(testList.get(2), CategoryLogic.TITLE);
    }
    
    @Test
    final void testExtractDataAsList(){
        List<Category> list = logic.getAll();
        Category e = list.get(0);
        List<?> testList = logic.extractDataAsList(e);
        assertEquals(testList.get(0), e.getId());
        assertEquals(testList.get(1), e.getUrl());
        assertEquals(testList.get(2), e.getTitle());
    }
}
