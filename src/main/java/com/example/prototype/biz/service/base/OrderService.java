package com.example.prototype.biz.service.base;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.prototype.biz.dao.base.JdbcPurchaseHistoryDao;
import com.example.prototype.biz.dao.base.JdbcPurchaseItemDao;
import com.example.prototype.entity.base.PurchaseHistory;
import com.example.prototype.entity.base.PurchaseItem;
import com.example.prototype.web.dto.base.CartDto;

/**
 * 注文サービス
 */
@Service
public class OrderService {

    @Autowired
    private JdbcPurchaseHistoryDao jdbcPurchaseHistoryDao;

    @Autowired
    private JdbcPurchaseItemDao jdbcPurchaseItemDao;

    /** 購入履歴に追加（AOPトランザクション制御あり） */
    public void insertPurchaseHistory(CartDto cart, int totalPrice) {

        // 購入履歴生成
        var history = new PurchaseHistory();
        history.setPurchaseDate(LocalDate.now());
        List<PurchaseItem> purchaceItemList = createPurchaseItemList(cart);
        history.setItemList(purchaceItemList);

        // 購入履歴 DB登録
        int id = jdbcPurchaseHistoryDao.insert(history);

        // 購入商品履歴 DB登録
        purchaceItemList.forEach(pItem -> {
            pItem.setPurchaseId(id);
            jdbcPurchaseItemDao.insert(pItem);
        });
    }

    /** カート情報から購入商品履歴情報を生成 */
    private List<PurchaseItem> createPurchaseItemList(CartDto cart) {
        // 購入商品リスト生成
        List<PurchaseItem> purchaceItemList = new ArrayList<>();
        cart.getItems().values().forEach(cartItem -> {
            var dto = new PurchaseItem();
            var item = cartItem.getItem();
            dto.setItemId(item.getId());
            dto.setPrice(item.getPrice());
            dto.setQuantity(cartItem.getQuantity());

            purchaceItemList.add(dto);
        });

        return purchaceItemList;
    }
}
