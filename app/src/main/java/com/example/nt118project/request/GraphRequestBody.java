package com.example.nt118project.request;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class GraphRequestBody {
    @SerializedName("fromTimestamp")
    private long fromTimestamp;

    @SerializedName("toTimestamp")
    private long toTimestamp;

    @SerializedName("type")
    private String type;

    public GraphRequestBody(long fromTimestamp, long toTimestamp, String type)
    {
        this.fromTimestamp = fromTimestamp;
        this.toTimestamp = toTimestamp;
        this.type = type;
    }

    // Add getters and setters for the fields
    public long getFromTimestamp() {
        return fromTimestamp;
    }

    public void setFromTimestamp(long fromTimestamp) {
        this.fromTimestamp = fromTimestamp;
    }

    public long getToTimestamp() {
        return toTimestamp;
    }

    public void setToTimestamp(long toTimestamp) {
        this.toTimestamp = toTimestamp;
    }

    public String getType() {
        return type;
    }

    public void setTypeAttribute(String type) {
        this.type = type;
    }

    @NonNull
    @Override
    public String toString() {
        return "{" +
                "fromTimestamp=" + fromTimestamp +
                ", toTimestamp=" + toTimestamp +
                ", typeAttribute='" + type + '\'' +
                '}';
    }
}
