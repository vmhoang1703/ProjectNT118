package com.example.nt118project.response;

import com.google.gson.annotations.SerializedName;

public class Options {
    @SerializedName("default")
    public Default _default;

    public Default get_default() {
        return _default;
    }

    public void set_default(Default _default) {
        this._default = _default;
    }
}
