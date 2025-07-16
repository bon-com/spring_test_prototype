package com.example.prototype.biz.service.base;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.prototype.web.dto.base.CartDto;
import com.example.prototype.web.dto.base.CartItemDto;
import com.example.prototype.web.dto.base.ItemDto;

/**
 * カートサービス
 */
@Service
public class CartService {

    /** カートに商品と数量を追加 */
    public void addItem(CartDto cart, ItemDto item, int quantity) {
        cart.getItems().compute(item.getId(), (key, val) -> {
            if (val == null) {
                // 商品が存在しない場合、新規追加
                return new CartItemDto(item, quantity);
            } else {
                // 商品が存在する場合、数量追加
                val.setQuantity(val.getQuantity() + quantity);
                return val;
            }
        });
    }

    /** カート内商品を削除 */
    public void deleteItem(CartDto cart, Integer itemId) {
        cart.getItems().remove(itemId);
    }

    /** カート内の合計金額を取得 */
    public int getTotalPrice(CartDto cart) {
        return cart.getItems().values().stream()
                .mapToInt(CartItemDto::getTotal)
                .sum();
    }

    /** カート内の商品一覧を取得 */
    public List<CartItemDto> getAllItems(CartDto cart) {
        return new ArrayList<>(cart.getItems().values());
    }
}
