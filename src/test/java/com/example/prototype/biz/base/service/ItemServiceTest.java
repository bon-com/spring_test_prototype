package com.example.prototype.biz.base.service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import com.example.prototype.base.entity.Item;
import com.example.prototype.biz.base.dao.JdbcItemDao;
import com.example.prototype.web.base.dto.ItemDto;

@RunWith(MockitoJUnitRunner.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService target;

    @Mock
    private JdbcItemDao jdbcItemDao;

    @Before
    public void setUp() {
        // 事前準備したいとき
    }

    @Test
    public void testFindAll001() {
        // モック定義
        Mockito.when(jdbcItemDao.findAll()).thenReturn(Collections.emptyList());

        // 実行
        List<ItemDto> resultList = target.findAll();

        assertTrue(resultList.isEmpty());
    }

    @Test
    public void testFindAll002() {
        // モック定義
        var item1 = new Item(1, "大葉", 100);
        var item2 = new Item(2, "大根", 200);
        List<Item> mockItemList = Arrays.asList(item1, item2);
        Mockito.when(jdbcItemDao.findAll()).thenReturn(mockItemList);

        // 実行
        List<ItemDto> resultList = target.findAll();

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

    @Test(expected = DataAccessException.class)
    public void testFindById001() {
        // モック定義
        when(jdbcItemDao.findById(anyInt()))
                .thenThrow(new EmptyResultDataAccessException(1));

        // 実行（ヒットしないケース）
        target.findById(5);
    }

    @Test
    public void testFindById002() {
        // モック定義
        var item = new Item(1, "大葉", 100);
        when(jdbcItemDao.findById(anyInt())).thenReturn(item);

        // 実行
        ItemDto result = target.findById(1);

        // 検証
        assertEquals(1, result.getId());
        assertEquals("大葉", result.getName());
        assertEquals(100, result.getPrice());
    }

}