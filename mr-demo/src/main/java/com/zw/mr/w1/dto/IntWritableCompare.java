package com.zw.mr.w1.dto;

import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by zhangws on 16/8/6.
 */
public class IntWritableCompare implements WritableComparable {

    private int value;

    public IntWritableCompare() {

    }

    public IntWritableCompare(int value) {
        set(value);
    }

    public void set(int value) {
        this.value = value;
    }

    public int get() {
        return this.value;
    }

    @Override
    public int compareTo(Object o) {
        int thisValue = this.value;
        int thatValue = ((IntWritableCompare) o).value;
        return (thisValue > thatValue ? -1 : (thisValue == thatValue ? 0 : 1));
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(value);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        value = in.readInt();
    }
}
