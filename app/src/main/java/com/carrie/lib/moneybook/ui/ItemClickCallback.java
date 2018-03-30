package com.carrie.lib.moneybook.ui;

/**
 * Created by Carrie on 2018/3/27.
 */

public interface ItemClickCallback<T> {


    /**
     *
     * @param entity
//     * @param flag [-1,-2]
//     *             -1: onClick();
//     *             -2: onLongClick();
//     *             [0,...] : position
     */
    void onItemClick(T entity,int flag);

}
