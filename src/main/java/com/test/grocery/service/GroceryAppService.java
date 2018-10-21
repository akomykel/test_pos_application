package com.test.grocery.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroceryAppService {

    @Value("${appTitle:unknown}")
    private String appTitle;

    @Value("${receiptHeader:unknown}")
    private String receiptHeader;

    public String appTitle(){
        return appTitle;
    }

    public String receiptHeader(){
        return receiptHeader;
    }

    public List<String> getAllItems(){
        /*
         *** Promo Code
         *** 0: Non promo
         *** 1: Buy 1 Take 2
         *** 2: Buy 2 Take 1
         */

        List<String> items = new ArrayList<>();
        items.add("Chippy,       12345, pc,     35,  0");
        items.add("Rice,         67890, kg,     45,  0");
        items.add("Toothpick,    98765, box,    20,  0");
        items.add("Coffee,       23456, pc,     12,  0");
        items.add("Beer,         34567, bottle, 65,  1");
        items.add("555 Sardines, 45678, can,    18,  0");
        items.add("Tissue,       56789, pack,   95,  2");
        items.add("Ice Cream,    87654, pc,     250, 1");
        items.add("Juice,        76543, pc,     14,  0");
        items.add("Porkchop,     65432, kg,     227, 0");
        return items;
    }
}
