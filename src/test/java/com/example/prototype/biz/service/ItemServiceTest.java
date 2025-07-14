package com.example.prototype.biz.service;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.example.prototype.biz.dao.JdbcItemDao;
import com.example.prototype.entity.Item;
import com.example.prototype.web.dto.ItemDto;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private JdbcItemDao jdbcItemDao;
    
    @Before
    public void setUp() {
        // 事前準備したいとき
    }
    
    @Test
    public void testFindAll001() {
        // モックデータ作成
        var item1 = new Item(1, "大葉", 100);
        var item2 = new Item(2, "大根", 200);
        List<Item> mockItemList = Arrays.asList(item1, item2);
        
        // モック定義
        Mockito.when(jdbcItemDao.findAll()).thenReturn(mockItemList);
        
        // 実行
        List<ItemDto> resultList = itemService.findAll();
        
        // 期待値
        var expected1 = new ItemDto();
        expected1.setId(1);
        expected1.setName("大葉");
        expected1.setPrice(100);

        var expected2 = new ItemDto();
        expected2.setId(2);
        expected2.setName("大根");
        expected2.setPrice(200);
        
        // 検証
        assertEquals(2, resultList.size());
        assertEquals(expected1.getId(), resultList.get(0).getId());
        assertEquals(expected1.getName(), resultList.get(0).getName());
        assertEquals(expected1.getPrice(), resultList.get(0).getPrice());

        assertEquals(expected2.getId(), resultList.get(1).getId());
        assertEquals(expected2.getName(), resultList.get(1).getName());
        assertEquals(expected2.getPrice(), resultList.get(1).getPrice());
    }
}
