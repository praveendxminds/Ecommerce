package com.nisarga.nisargaveggiez.adapter;

import java.util.List;

/**
 * Created by praveen on 28/11/18.
 */

public class ProductDataWrapper {

    private String strSearch;
    private String status;
    private String customer_id;
    private String message;
    private List<ProductData> result;

    public ProductDataWrapper(String customer_id)
    {
        this.customer_id = customer_id;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String searchStatus)
    {
        this.status = searchStatus;
    }




    public String getMessage()
    {
        return message;
    }

    public void setMessage(String searchMsg)
    {
        this.message = searchMsg;
    }

    public List<ProductData> getResult()
    {
        return result;
    }

    public void setResult(List<ProductData> result) {
        this.result = result;
    }
}
