package com.carrie.lib.moneybook.model;

/**
 * Created by Carrie on 2018/3/27.
 * 模板
 */

public interface Template {

    int getId();
    /**
     * 模板名称
     * */
    String getName();

    double getExpense();
    String getClassify();
    String getAccount();


}
