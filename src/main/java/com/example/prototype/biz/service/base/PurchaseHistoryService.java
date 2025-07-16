package com.example.prototype.biz.service.base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.dao.base.JdbcPurchaseHistoryDao;
import com.example.prototype.entity.base.PurchaseHistory;
import com.example.prototype.web.dto.base.PurchaseHistoryDto;
import com.example.prototype.web.dto.base.PurchaseItemDto;

/**
 * 注文履歴サービス
 */
@Service
public class PurchaseHistoryService {

    @Autowired
    private JdbcPurchaseHistoryDao jdbcPurchaseHistoryDao;

    /** 購入日付一覧を取得 */
    public List<LocalDate> getPurchaseDateList() {
        return jdbcPurchaseHistoryDao.findAllPurchaseDate();
    }

    /** 購入日付から履歴取得 */
    public PurchaseHistoryDto findPurchaseHistory(LocalDate purchaseDate) {
        // 購入履歴情報取得
        PurchaseHistory purchaseHistory = jdbcPurchaseHistoryDao.findByPurchaseDate(purchaseDate);
        var purchaseHistoryDto = new PurchaseHistoryDto();
        BeanUtils.copyProperties(purchaseHistory, purchaseHistoryDto);
        
        purchaseHistoryDto.setItemList(new ArrayList<PurchaseItemDto>());
        purchaseHistory.getItemList().forEach(item -> {
            var dto = new PurchaseItemDto();
            BeanUtils.copyProperties(item, dto);
            purchaseHistoryDto.getItemList().add(dto);
        });
        return purchaseHistoryDto;
    }
    
    /** 購入履歴合計金額を取得 */
    public int getTotalPrice(List<PurchaseItemDto> purchaseList) {
        return purchaseList.stream()
                .mapToInt(PurchaseItemDto::getPrice)
                .sum();
    }
}
