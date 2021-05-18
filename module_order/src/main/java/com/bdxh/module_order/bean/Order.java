package com.bdxh.module_order.bean;

import com.bdxh.librarybase.base.BaseEntity;
import java.util.List;

public class Order extends BaseEntity {

    public String id;

    public int startIndex = 0;

    public int page = 1;

    public int total;

    public  List<OrderDetail> details;

    private static class OrderDetail extends BaseEntity{
        public String id;
        public String title;
        public String year;
        public String images_url;
    }
}
