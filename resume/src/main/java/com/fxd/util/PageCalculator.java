package com.fxd.util;

public class PageCalculator {
    public static int calculateRowIndex(int pageIndex, int pageSize) {
        //从 0*5  0之后开始5条数据
        //从 1*5  5之后开始5条数据
        //从第 1 页开始
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
