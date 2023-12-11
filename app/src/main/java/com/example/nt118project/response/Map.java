package com.example.nt118project.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Map {
    @SerializedName("options")
    @Expose
    public Options options;
    @SerializedName("version")
    @Expose
    public Integer version;
    @SerializedName("sources")
    @Expose
    public Object sources;
    @SerializedName("sprite")
    @Expose
    public String sprite;
    @SerializedName("glyphs")
    @Expose
    public String glyphs;
    @SerializedName("layers")
    @Expose
    public Object layers[];
    public Options getoptions() {
        return options;
    }
    public void setoptions(Options options) {
        this.options = options;
    }

    public Integer getversion() {
        return version;
    }
    public void setversion(Integer version) {
        this.version = version;
    }

    public Object getsources() {
        return sources;
    }
    public void setsources(Object sources) {
        this.sources = sources;
    }

    public String getsprite() {
        return sprite;
    }
    public void setsprite(String sprite) {
        this.sprite = sprite;
    }

    public String getglyphs() {
        return glyphs;
    }
    public void setglyphs(String glyphs) {
        this.glyphs = glyphs;
    }

    @Override
    public String toString() {
        return "Map: " +
                "option ='" + options + '\'' +
                "\n version =" + version +
                "\n sources =" + sources +
                "\n sprite ='" + sprite + '\'' +
                "\n glyphs =" + glyphs;
    }
}
