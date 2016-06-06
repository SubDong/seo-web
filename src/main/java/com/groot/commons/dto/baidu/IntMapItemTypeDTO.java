package com.groot.commons.dto.baidu;

/**
 * Created by XiaoWei on 2015/2/13.
 */
public class IntMapItemTypeDTO {
    protected String key;
    protected int value;

    /**
     * Gets the value of the key property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getKey() {
        return key;
    }

    /**
     * Sets the value of the key property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setKey(String value) {
        this.key = value;
    }

    /**
     * Gets the value of the value property.
     *
     */
    public int getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     */
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "IntMapItemType [key=" + key + ", value=" + value + "]";
    }
}
