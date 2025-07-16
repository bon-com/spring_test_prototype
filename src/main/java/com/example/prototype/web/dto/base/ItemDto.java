package com.example.prototype.web.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品クラス
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {
    /** 商品ID */
    private int id;
    /** 商品名 */
    private String name;
    /** 値段 */
    private int price;
}
