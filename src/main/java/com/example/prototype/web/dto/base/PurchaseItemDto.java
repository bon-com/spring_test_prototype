package com.example.prototype.web.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseItemDto {
    /** 商品ID（外部キー） */
    private int itemId;
    /** 購入数量 */
    private int quantity;
    /** 購入時の値段 */
    private int price;
    /** 商品名 */
    private String itemName;
}
