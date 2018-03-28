package com.carrie.lib.moneybook.utils;

import com.carrie.lib.moneybook.db.entity.AccountEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyEntity;
import com.carrie.lib.moneybook.db.entity.ClassifyParentEntity;
import com.carrie.lib.moneybook.model.ClassifyParent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Carrie on 2018/3/28.
 */

public class DataGenerator {

    public static List<ClassifyParentEntity> generateClassifyParents() {
        List<ClassifyParentEntity> list = new ArrayList<>();
        String[] parents = {"衣", "食", "住", "行"};
        int length = parents.length;
        for (int i = 0; i < length; i++) {
            list.add(new ClassifyParentEntity(i+1, parents[i], 0));
        }
        return list;
    }

    public static List<ClassifyEntity> generateClassifies() {
        List<ClassifyEntity> list = new ArrayList<>();
        String[] parents = {"睡衣", "鞋子", "早餐", "水果", "零食", "外卖", "房租水电", "公交", "地铁"};
        int length = parents.length;
        ClassifyEntity entity;
        for (int i = 0; i < length; i++) {
            entity = new ClassifyEntity();
            entity.id = i+1;
            if (i < 2) {
                entity.parentId = 1;
            } else if (i < 6) {
                entity.parentId = 2;
            } else if (i < 8) {
                entity.parentId = 3;
            } else {
                entity.parentId = 4;
            }
            entity.classify = parents[i];
            list.add(entity);
        }
        return list;
    }

    public static List<AccountEntity> generateAccounts() {
        List<AccountEntity> list = new ArrayList<>();
        String[] parents = {"支付宝", "微信钱包"};
        int length = parents.length;
        for (int i = 0; i < length; i++) {
            list.add(new AccountEntity( parents[i]));
        }
        return list;
    }


}
