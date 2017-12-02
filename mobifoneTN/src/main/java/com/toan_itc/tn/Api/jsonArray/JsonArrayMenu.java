package com.toan_itc.tn.Api.jsonArray;

import com.google.gson.annotations.Expose;

import java.util.List;

public class JsonArrayMenu<T> {
    @Expose
    public List<T> menu;
}
