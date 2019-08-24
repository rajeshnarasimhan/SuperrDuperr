package com.deltaa.superrduperr.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.deltaa.superrduperr.SuperrduperrApplication;
import com.deltaa.superrduperr.bean.request.IdBean;
import com.deltaa.superrduperr.bean.request.IdListBean;
import com.deltaa.superrduperr.bean.request.ReminderBean;
import com.deltaa.superrduperr.bean.request.TagBean;
import com.deltaa.superrduperr.business.ItemService;
import com.deltaa.superrduperr.entity.Item;
import com.deltaa.superrduperr.entity.SuperDuper;
import com.deltaa.superrduperr.enums.ItemStatus;
import com.deltaa.superrduperr.repository.SuperDuperRepository;
import com.deltaa.superrduperr.rest.util.TestUtil;

/**
 * Test class for ItemWS
 * @author rajesh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperrduperrApplication.class)
public class ItemWSIntTest {
	
	private MockMvc restMockMvc;
	
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private SuperDuperRepository superDuperRepository;
    
    private SuperDuper superDuper;
    
    private Item item;
    
    private static final String NAME = "SuperDuper";
	private static final String ITEM_NAME = "Test Item";
	private static final ItemStatus ITEM_STATUS = ItemStatus.STARTED;
	private static final String TAG_DESC = "Test Tag";
	private static final String REMINDER_DESC = "Test Reminder";
	private static final LocalDateTime REMINDER_TIME = LocalDateTime.now();
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ItemWS itemWS = new ItemWS();
        itemWS.setItemService(itemService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(itemWS)
        								.setCustomArgumentResolvers(pageableArgumentResolver).build();
    }
    
	@Before
    public void initTest() {
		item = createItem();
		superDuper = createSuperDuper();
    }
	
	private SuperDuper createSuperDuper() {
		SuperDuper superDuper = new SuperDuper()
				.setName(NAME);
		return superDuper;
	}

	private Item createItem() {
		Item item = new Item();
		item.setName(ITEM_NAME)
			.setStatus(ITEM_STATUS);
		return item;
	}
	
	private IdListBean getIdListBean(Long id) {
		IdListBean idListBean = new IdListBean();
		IdBean idbean = new IdBean();
		idbean.setId(id.toString());
		List<IdBean> idBeans = new ArrayList<IdBean>();
		idBeans.add(idbean);
		idListBean.setIds(idBeans);
		return idListBean;
	}
	
	private IdListBean getIdListBeanFail1() {
		IdListBean idListBean = new IdListBean();
		IdBean idbean = new IdBean();
		idbean.setId("xyz");
		List<IdBean> idBeans = new ArrayList<IdBean>();
		idBeans.add(idbean);
		idListBean.setIds(idBeans);
		return idListBean;
	}
	
	private IdListBean getIdListBeanFail2() {
		IdListBean idListBean = new IdListBean();
		IdBean idbean = new IdBean();
		List<IdBean> idBeans = new ArrayList<IdBean>();
		idBeans.add(idbean);
		idListBean.setIds(idBeans);
		return idListBean;
	}
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void markItemAsCompleted() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		
        // mark item by item id
		restMockMvc.perform(get("/api/v1/item/mark/{itemId}", itemId))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.status").value(ItemStatus.COMPLETED.toString()));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void markItemAsCompletedFail() throws Exception {
		
        // mark item by item id
		restMockMvc.perform(get("/api/v1/item/mark/{itemId}", 1000L))
	        .andExpect(status().isNotFound());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItem() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		
        // delete item by item id
		restMockMvc.perform(get("/api/v1/item/delete/{itemId}", itemId))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.deleted").value(true));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItemFail() throws Exception {
		
        // delete item by item id
		restMockMvc.perform(get("/api/v1/item/delete/{itemId}", 1000L))
	        .andExpect(status().isNotFound());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItems() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		
        // delete item by idListBean
		restMockMvc.perform(post("/api/v1/items/delete/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBean(itemId))))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.[0].id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.[0].name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.[0].status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.[0].deleted").value(true));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItemsFail1() throws Exception {
        // delete item by idListBean
		restMockMvc.perform(post("/api/v1/items/delete/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(new IdListBean())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItemsFail2() throws Exception {
        // delete item by idListBean
		restMockMvc.perform(post("/api/v1/items/delete/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBeanFail1())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void deleteItemsFail3() throws Exception {
        // delete item by idListBean
		restMockMvc.perform(post("/api/v1/items/delete/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBeanFail2())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItem() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		
        // delete item by item id
		restMockMvc.perform(get("/api/v1/item/restore/{itemId}", itemId))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.deleted").value(false));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItemFail() throws Exception {
		
        // delete item by item id
		restMockMvc.perform(get("/api/v1/item/restore/{itemId}", 1000L))
	        .andExpect(status().isNotFound());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItems() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		
        // restore item by idListBean
		restMockMvc.perform(post("/api/v1/items/restore/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBean(itemId))))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.[0].id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.[0].name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.[0].status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.[0].deleted").value(false));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItemsFail1() throws Exception {
        // restore item by idListBean
		restMockMvc.perform(post("/api/v1/items/restore/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(new IdListBean())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItemsFail2() throws Exception {
        // restore item by idListBean
		restMockMvc.perform(post("/api/v1/items/restore/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBeanFail1())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void restoreItemsFail3() throws Exception {
        // restore item by idListBean
		restMockMvc.perform(post("/api/v1/items/restore/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(getIdListBeanFail2())))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void tagItem() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		TagBean tagBean = new TagBean().setItemId(itemId).setTagDesc(TAG_DESC);
		
        // tag item by TagBean
		restMockMvc.perform(post("/api/v1/item/add-tag/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(tagBean)))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.deleted").value(false))
	        .andExpect(jsonPath("$.tags.[0].tagDesc").value(TAG_DESC));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void tagItemFail1() throws Exception {
		// Initialize the data
		TagBean tagBean = new TagBean().setItemId(1000L).setTagDesc(TAG_DESC);
		
        // tag item by TagBean
		restMockMvc.perform(post("/api/v1/item/add-tag/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(tagBean)))
	        .andExpect(status().isNotFound());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void tagItemFail2() throws Exception {
		// Initialize the data
		TagBean tagBean = new TagBean().setTagDesc(TAG_DESC);
		
        // tag item by TagBean
		restMockMvc.perform(post("/api/v1/item/add-tag/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(tagBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void tagItemFail3() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		TagBean tagBean = new TagBean().setItemId(itemId);
		
        // tag item by TagBean
		restMockMvc.perform(post("/api/v1/item/add-tag/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(tagBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addReminderToItem() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		ReminderBean reminderBean = new ReminderBean().setItemId(itemId)
				.setDesc(REMINDER_DESC).setDatetime(REMINDER_TIME);
		
        // add reminder to item by ReminderBean
		restMockMvc.perform(post("/api/v1/item/add-reminder/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(reminderBean)))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.id").value(itemId.intValue()))
	        .andExpect(jsonPath("$.name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.status").value(ITEM_STATUS.toString()))
	        .andExpect(jsonPath("$.deleted").value(false))
	        .andExpect(jsonPath("$.reminders.[0].desc").value(REMINDER_DESC))
	        .andExpect(jsonPath("$.reminders.[0].datetime[0]").value(REMINDER_TIME.getYear()))
	        .andExpect(jsonPath("$.reminders.[0].datetime[1]").value(REMINDER_TIME.getMonthValue()))
	        .andExpect(jsonPath("$.reminders.[0].datetime[2]").value(REMINDER_TIME.getDayOfMonth()))
	        .andExpect(jsonPath("$.reminders.[0].datetime[3]").value(REMINDER_TIME.getHour()))
	        .andExpect(jsonPath("$.reminders.[0].datetime[4]").value(REMINDER_TIME.getMinute()))
	        .andExpect(jsonPath("$.reminders.[0].datetime[5]").value(REMINDER_TIME.getSecond()));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addReminderToItemFail1() throws Exception {
		// Initialize the data
		ReminderBean reminderBean = new ReminderBean().setItemId(1000L)
				.setDesc(REMINDER_DESC).setDatetime(REMINDER_TIME);
		
		// add reminder to item by ReminderBean
		restMockMvc.perform(post("/api/v1/item/add-reminder/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(reminderBean)))
	        .andExpect(status().isNotFound());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addReminderToItemFail2() throws Exception {
		// Initialize the data
		ReminderBean reminderBean = new ReminderBean()
				.setDesc(REMINDER_DESC).setDatetime(REMINDER_TIME);
		
		// add reminder to item by ReminderBean
		restMockMvc.perform(post("/api/v1/item/add-reminder/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(reminderBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addReminderToItemFail3() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		ReminderBean reminderBean = new ReminderBean().setItemId(itemId)
				.setDatetime(REMINDER_TIME);
		
		// add reminder to item by ReminderBean
		restMockMvc.perform(post("/api/v1/item/add-reminder/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(reminderBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addReminderToItemFail4() throws Exception {
		// Initialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuper.addItem(item);
		item.setSuperDuper(superDuper);
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		Long itemId = superDuper.getItems().get(0).getId();
		ReminderBean reminderBean = new ReminderBean().setItemId(itemId)
				.setDesc(REMINDER_DESC);
		
		// add reminder to item by ReminderBean
		restMockMvc.perform(post("/api/v1/item/add-reminder/")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(reminderBean)))
	        .andExpect(status().isBadRequest());
    }
}