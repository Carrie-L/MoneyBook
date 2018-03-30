package com.carrie.lib.moneybook.db.entity;

import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableDouble;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import com.carrie.lib.moneybook.model.Classify;
import com.carrie.lib.moneybook.model.ClassifyParent;
import com.carrie.lib.moneybook.model.Common;

/**
 * Created by Carrie on 2018/3/27.
 * 消费类型：水果、早餐、购物...
 */
@Entity(tableName = "classify")
//        foreignKeys = @ForeignKey(entity = ClassifyParentEntity.class, parentColumns = "id", childColumns = "parentId", onDelete = ForeignKey.CASCADE),
//        indices = @Index(value = "parentId")
public class ClassifyEntity implements Common, Parcelable {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public int parentId;

    @NonNull
    public String classify;

    public boolean isParent;

    public double budget;

    @Ignore
    public final ObservableBoolean isSelected = new ObservableBoolean();


    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getName() {
        return classify;
    }

    @NonNull
    public String getClassify() {
        return classify;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setClassify(@NonNull String classify) {
        this.classify = classify;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.parentId);
        dest.writeString(this.classify);
        dest.writeByte(isParent ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.budget);
    }

    public ClassifyEntity() {
    }

    protected ClassifyEntity(Parcel in) {
        this.id = in.readInt();
        this.parentId = in.readInt();
        this.classify = in.readString();
        this.isParent = in.readByte() != 0;
        this.budget = in.readDouble();
    }

    public static final Parcelable.Creator<ClassifyEntity> CREATOR = new Parcelable.Creator<ClassifyEntity>() {
        public ClassifyEntity createFromParcel(Parcel source) {
            return new ClassifyEntity(source);
        }

        public ClassifyEntity[] newArray(int size) {
            return new ClassifyEntity[size];
        }
    };

    @Override
    public String toString() {
        return "ClassifyEntity{" +
                "id=" + id +
                ", parentId=" + parentId +
                ", classify='" + classify + '\'' +
                ", isParent=" + isParent +
                ", budget=" + budget +
                '}';
    }
}
