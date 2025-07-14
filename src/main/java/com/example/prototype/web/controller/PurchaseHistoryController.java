package com.example.prototype.web.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.prototype.biz.service.PurchaseHistoryService;
import com.example.prototype.web.dto.PurchaseHistoryDto;

@Controller
@RequestMapping("history")
public class PurchaseHistoryController {

    @Autowired
    private PurchaseHistoryService purchaseHistoryService;
    
    /**
     * 購入日付一覧をモデルに追加
     * @return
     */
    @ModelAttribute("availableDates")
    public List<LocalDate> getAvailableDates() {
        return purchaseHistoryService.getPurchaseDateList();
    }
    
    /**
     * 初期表示
     * @param model
     * @return
     */
    @GetMapping(value = "/")
    public String historyDate(Model model) {
        return "purchase_history";
    }
    
    /**
     * 購入履歴検索
     * @param purchaseDateStr
     * @param model
     * @return
     */
    @GetMapping(value = "purchase-history")
    public String history(@RequestParam("purchaseDate") String purchaseDateStr, Model model) {
        if (!StringUtils.isEmpty(purchaseDateStr)) {
            // 購入日付検索
            var purchaseDate = LocalDate.parse(purchaseDateStr);
            PurchaseHistoryDto purchaseHistory = purchaseHistoryService.findPurchaseHistory(purchaseDate);
            model.addAttribute("purchaseHistory", purchaseHistory);
            
            // 合計金額
            int totalPrice = purchaseHistoryService.getTotalPrice(purchaseHistory.getItemList());
            model.addAttribute("totalPrice", totalPrice);
        }
        return "purchase_history";
    }
}
