package com.zw.website.model.car;

public class DiffBrandMonthSaleDist {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column diff_brand_month_sale_dist.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private String brand;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column diff_brand_month_sale_dist.month
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private String month;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column diff_brand_month_sale_dist.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    private Integer amount;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column diff_brand_month_sale_dist.brand
     *
     * @return the value of diff_brand_month_sale_dist.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public String getBrand() {
        return brand;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column diff_brand_month_sale_dist.brand
     *
     * @param brand the value for diff_brand_month_sale_dist.brand
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column diff_brand_month_sale_dist.month
     *
     * @return the value of diff_brand_month_sale_dist.month
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public String getMonth() {
        return month;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column diff_brand_month_sale_dist.month
     *
     * @param month the value for diff_brand_month_sale_dist.month
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column diff_brand_month_sale_dist.amount
     *
     * @return the value of diff_brand_month_sale_dist.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public Integer getAmount() {
        return amount;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column diff_brand_month_sale_dist.amount
     *
     * @param amount the value for diff_brand_month_sale_dist.amount
     *
     * @mbggenerated Sat Sep 24 20:17:02 CST 2016
     */
    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}