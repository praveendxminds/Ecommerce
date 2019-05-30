package com.app.ecommerce.adapter;

import java.util.List;

/**
 * Created by praveen on 28/11/18.
 */

public class ProductDataWrapper {
    private List<ProductData> items;

    public List<ProductData> getCoupons() {
        return items;
    }

    public void setCoupons(List<ProductData> items) {
        this.items = items;
    }
}
