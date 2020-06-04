package logic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.Account;
import common.TomcatStartUp;
import dal.EMFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Shariar
 */
class AccountLogicTest {

    private AccountLogic logic;
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
    final void setUp() throws Exception {
        logic = new AccountLogic();
        sampleMap = new HashMap<>();
        sampleMap.put(AccountLogic.DISPLAY_NAME, new String[]{"Junit 5 Test"});
        sampleMap.put(AccountLogic.USER, new String[]{"junit"});
        sampleMap.put(AccountLogic.PASSWORD, new String[]{"junit5"});

    }

    @AfterEach
    final void tearDown() throws Exception {

    }

    @Test
    final void testGetAll() {
        List<Account> list = logic.getAll();
        int originalSize = list.size();
        Account testAccount = logic.createEntity(sampleMap);
        logic.add(testAccount);

        list = logic.getAll();
        assertEquals(originalSize + 1, list.size());
        logic.delete(testAccount);
        list = logic.getAll();
       
        assertEquals(originalSize, list.size());
    }

    @Test
    final void testGetWithId() {
        List<Account> list = logic.getAll();

        Account testAccount = list.get(0);
        Account returnedAccount = logic.getWithId(testAccount.getId());

        assertEquals(testAccount.getId(), returnedAccount.getId());
        assertEquals(testAccount.getDisplayName(), returnedAccount.getDisplayName());
        assertEquals(testAccount.getUser(), returnedAccount.getUser());
        assertEquals(testAccount.getPassword(), returnedAccount.getPassword());
    }
    
    @Test
    final void testGetAccountWithDisplayName() {
        List<Account> list = logic.getAll();
        Account testEntity1 = list.get(0);
        Account testEntity2 = logic.getAccountWithDisplayName(testEntity1.getDisplayName());

        assertEquals(testEntity1.getId(), testEntity2.getId());
        assertEquals(testEntity1.getDisplayName(), testEntity2.getDisplayName());
        assertEquals(testEntity1.getUser(), testEntity2.getUser());
        assertEquals(testEntity1.getPassword(), testEntity2.getPassword());
    }

    @Test
    final void testGetAccountWIthUser() {
        List<Account> list = logic.getAll();
        Account testEntity1 = list.get(0);
        Account testEntity2 = logic.getAccountWithUser(testEntity1.getUser());

        assertEquals(testEntity1.getId(), testEntity2.getId());
        assertEquals(testEntity1.getDisplayName(), testEntity2.getDisplayName());
        assertEquals(testEntity1.getUser(), testEntity2.getUser());
        assertEquals(testEntity1.getPassword(), testEntity2.getPassword());
    }

    @Test
    final void testGetAccountsWithPassword() {
        List<Account> list = logic.getAll();
        Account testEntity1 = list.get(0);
        List<Account> testList = logic.getAccountsWithPassword(testEntity1.getPassword());
        for (Account e : testList) {
            assertEquals(testEntity1.getPassword(), e.getPassword());
        }
    }

    @Test
    final void testGetAccountWIth() {
        List<Account> list = logic.getAll();
        Account testEntity1 = list.get(0);
        Account testEntity2 = logic.getAccountWith(testEntity1.getDisplayName(), testEntity1.getPassword());

        assertEquals(testEntity1.getId(), testEntity2.getId());
        assertEquals(testEntity1.getDisplayName(), testEntity2.getDisplayName());
        assertEquals(testEntity1.getUser(), testEntity2.getUser());
        assertEquals(testEntity1.getPassword(), testEntity2.getPassword());
    }

    @Test
    final void testSearch() {
        List<Account> testList = logic.search("t");
        for (Account e : testList) {
            assertTrue(e.getDisplayName().contains("t") || e.getUser().contains("t"));
        }

    }

    @Test
    final void testCreateEntity() {
        Account testAccount1 = logic.createEntity(sampleMap);
        logic.add(testAccount1);

        assertEquals(testAccount1.getDisplayName(), "Junit 5 Test");
        assertEquals(testAccount1.getUser(), "junit");
        assertEquals(testAccount1.getPassword(), "junit5");

        logic.delete(testAccount1);

    }

    @Test
    final void testGetColumnNames() {

        List<String> testList = logic.getColumnNames();
        assertEquals(testList.get(0), "id");
        assertEquals(testList.get(1), "displayName");
        assertEquals(testList.get(2), "user");
        assertEquals(testList.get(3), "password");
    }

    @Test
    final void testGetColumnCodes() {

        List<String> testList = logic.getColumnCodes();
        assertEquals(testList.get(0), AccountLogic.ID);
        assertEquals(testList.get(1), AccountLogic.DISPLAY_NAME);
        assertEquals(testList.get(2), AccountLogic.USER);
        assertEquals(testList.get(3), AccountLogic.PASSWORD);
    }

    @Test
    final void testExtractDataAsList() {
        List<Account> list = logic.getAll();
        Account e = list.get(0);
        List<?> testList = logic.extractDataAsList(e);
        assertEquals(testList.get(0), e.getId());
        assertEquals(testList.get(1), e.getDisplayName());
        assertEquals(testList.get(2), e.getUser());
        assertEquals(testList.get(3), e.getPassword());
    }
    

}
