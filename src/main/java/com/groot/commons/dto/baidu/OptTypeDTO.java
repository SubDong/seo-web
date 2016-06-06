package com.groot.commons.dto.baidu;

import com.groot.mongodb.entity.baidu.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoWei on 2015/2/13.
 */
public class OptTypeDTO {
    protected List<StringMapItemTypeEntity> optString;
    protected List<IntMapItemTypeEntity> optInt;
    protected List<LongMapItemTypeEntity> optLong;
    protected List<FloatMapItemTypeEntity> optFloat;
    protected List<DoubleMapItemTypeEntity> optDouble;

    /**
     * Gets the value of the optString property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optString property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptString().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StringMapItemTypeEntity }
     *
     *
     */
    public List<StringMapItemTypeEntity> getOptString() {
        if (optString == null) {
            optString = new ArrayList<StringMapItemTypeEntity>();
        }
        return this.optString;
    }

    /**
     * Gets the value of the optInt property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optInt property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptInt().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link IntMapItemTypeEntity }
     *
     *
     */
    public List<IntMapItemTypeEntity> getOptInt() {
        if (optInt == null) {
            optInt = new ArrayList<IntMapItemTypeEntity>();
        }
        return this.optInt;
    }

    /**
     * Gets the value of the optLong property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optLong property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptLong().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link LongMapItemTypeEntity }
     *
     *
     */
    public List<LongMapItemTypeEntity> getOptLong() {
        if (optLong == null) {
            optLong = new ArrayList<LongMapItemTypeEntity>();
        }
        return this.optLong;
    }

    /**
     * Gets the value of the optFloat property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optFloat property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptFloat().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link FloatMapItemTypeEntity }
     *
     *
     */
    public List<FloatMapItemTypeEntity> getOptFloat() {
        if (optFloat == null) {
            optFloat = new ArrayList<FloatMapItemTypeEntity>();
        }
        return this.optFloat;
    }

    /**
     * Gets the value of the optDouble property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the optDouble property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOptDouble().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DoubleMapItemTypeEntity }
     *
     *
     */
    public List<DoubleMapItemTypeEntity> getOptDouble() {
        if (optDouble == null) {
            optDouble = new ArrayList<DoubleMapItemTypeEntity>();
        }
        return this.optDouble;
    }

    @Override
    public String toString() {
        return "OptType [optString=" + optString + ", optInt=" + optInt + ", optLong=" + optLong + ", optFloat="
                + optFloat + ", optDouble=" + optDouble + "]";
    }

}
