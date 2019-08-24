package com.deltaa.superrduperr.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import com.deltaa.superrduperr.bean.request.ItemBean;
import com.deltaa.superrduperr.bean.request.SuperDuperBean;
import com.deltaa.superrduperr.business.SuperDuperService;
import com.deltaa.superrduperr.entity.SuperDuper;
import com.deltaa.superrduperr.repository.SuperDuperRepository;
import com.deltaa.superrduperr.rest.util.TestUtil;

/**
 * Test class for SuperDuperWS
 * @author rajesh
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = SuperrduperrApplication.class)
public class SuperDuperWSIntTest {
	
	private MockMvc restMockMvc;
	
    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;
    
    @Autowired
    private SuperDuperService superDuperService;
    
    @Autowired
    private SuperDuperRepository superDuperRepository;
    
    private SuperDuper superDuper;
    
    private List<ItemBean> itemBeans;
    
    private SuperDuperBean superDuperBean;
    
    private static final String NAME = "SuperDuper";
	private static final String ITEM_NAME = "Test Item";
	private static final String ITEM_STATUS = "COMPLETED";
    
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final SuperDuperWS superDuperWS = new SuperDuperWS();
        superDuperWS.setSuperDuperService(superDuperService);
        this.restMockMvc = MockMvcBuilders.standaloneSetup(superDuperWS)
        								.setCustomArgumentResolvers(pageableArgumentResolver).build();
    }
    
	@Before
    public void initTest() {
		superDuper = createSuperDuper();
		itemBeans = createItemBeans();
    }

	private SuperDuper createSuperDuper() {
		SuperDuper superDuper = new SuperDuper().setName(NAME);
		return superDuper;
	}
	
	private List<ItemBean> createItemBeans() {
		ItemBean itemBean = new ItemBean()
				.setName(ITEM_NAME).setStatus(ITEM_STATUS);
		List<ItemBean> itemBeans = new ArrayList<ItemBean>();
		itemBeans.add(itemBean);
		return itemBeans;
	}
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void saveSuperDuper() throws Exception {
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/save")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuper)))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.name").value(NAME));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void saveSuperDuperFail() throws Exception {
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/save")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuper.setName(""))))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addItemsToSuperDuper() throws Exception {
		// intialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuperBean = new SuperDuperBean();
		superDuperBean.setSuperDuperId(Long.valueOf(superDuper.getId()));
		superDuperBean.setItems(itemBeans);
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/add-items")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuperBean)))
	        .andExpect(status().isOk())
	        .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
	        .andExpect(jsonPath("$.name").value(NAME))
	        .andExpect(jsonPath("$.items.[0].name").value(ITEM_NAME))
	        .andExpect(jsonPath("$.items.[0].status").value(ITEM_STATUS));
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addItemsToSuperDuperFail1() throws Exception {
		// intialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuperBean = new SuperDuperBean();
		superDuperBean.setItems(itemBeans);
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/add-items")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuperBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addItemsToSuperDuperFail2() throws Exception {
		// intialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuperBean = new SuperDuperBean();
		superDuperBean.setSuperDuperId(Long.valueOf(superDuper.getId()));
		itemBeans.get(0).setName("");
		superDuperBean.setItems(itemBeans);
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/add-items")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuperBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addItemsToSuperDuperFail3() throws Exception {
		// intialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuperBean = new SuperDuperBean();
		superDuperBean.setSuperDuperId(Long.valueOf(superDuper.getId()));
		itemBeans.get(0).setStatus("");
		superDuperBean.setItems(itemBeans);
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/add-items")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuperBean)))
	        .andExpect(status().isBadRequest());
    }
	
	@Test
    @Transactional
    @WithMockUser(username = "tester", password = "testpassword")
    public void addItemsToSuperDuperFail4() throws Exception {
		// intialize the data
		superDuper = superDuperRepository.saveAndFlush(superDuper);
		superDuperBean = new SuperDuperBean();
		superDuperBean.setSuperDuperId(Long.valueOf(superDuper.getId()));
		
        // save superDuper
        restMockMvc.perform(post("/api/v1/super-duper/add-items")
        	.contentType(TestUtil.APPLICATION_JSON_UTF8)
        	.content(TestUtil.convertObjectToJsonBytes(superDuperBean)))
	        .andExpect(status().isBadRequest());
    }
}