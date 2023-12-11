package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class GraphResponse {
    @SerializedName("x")
    private long timestamp;
    @SerializedName("y")
    private double attributeValue;
    public void setTimestamp(long timestamp)
    {
        this.timestamp = timestamp;
    }
    public long getTimestamp()
    {
        return timestamp;
    }
    public void setAttributeValue(double attributeValue)
    {
        this.attributeValue = attributeValue;
    }
    public double getAttributeValue()
    {
        return attributeValue;
    }
}
