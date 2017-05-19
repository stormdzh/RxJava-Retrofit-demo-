package com.stormdzh.androidtest.model;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 *
 * @author Administrator
 */
public class DateModel {
    public String version;
    public ResBean res;

    public static class ResBean {
        public List<ZBean> zlist ;
        public static class ZBean {
            public String name;
        }
    }
}
