package com.zw.website.model.car;

public class OwnerTypeBrand {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column owner_type_brand.owner
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private String owner;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column owner_type_brand.type
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private String type;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column owner_type_brand.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private String brand;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column owner_type_brand.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private Integer amount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column owner_type_brand.owner
     *
     * @return the value of owner_type_brand.owner
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public String getOwner() {
        return owner;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column owner_type_brand.owner
     *
     * @param owner the value for owner_type_brand.owner
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setOwner(String owner) {
        this.owner = owner == null ? null : owner.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column owner_type_brand.type
     *
     * @return the value of owner_type_brand.type
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public String getType() {
        return type;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column owner_type_brand.type
     *
     * @param type the value for owner_type_brand.type
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column owner_type_brand.brand
     *
     * @return the value of owner_type_brand.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public String getBrand() {
        return brand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column owner_type_brand.brand
     *
     * @param brand the value for owner_type_brand.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column owner_type_brand.amount
     *
     * @return the value of owner_type_brand.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column owner_type_brand.amount
     *
     * @param amount the value for owner_type_brand.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}