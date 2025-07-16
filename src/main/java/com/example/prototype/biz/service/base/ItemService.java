package com.example.prototype.biz.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.dao.base.JdbcItemDao;
import com.example.prototype.entity.base.Item;
import com.example.prototype.web.dto.base.ItemDto;

/**
 * 商品サービス
 */
@Service
public class ItemService {

    @Autowired
    private JdbcItemDao jdbcItemDao;

    /** 商品一覧取得 */
    public List<ItemDto> findAll() {
        List<ItemDto> itemList = new ArrayList<>();

        jdbcItemDao.findAll().forEach(item -> {
            var dto = new ItemDto();
            BeanUtils.copyProperties(item, dto);
            itemList.add(dto);
        });

        return itemList;
    }

    /** 商品検索 */
    public ItemDto findById(int id) {
        Item res = jdbcItemDao.findById(id);
        var dto = new ItemDto();
        BeanUtils.copyProperties(res, dto);

        return dto;
    }
}
