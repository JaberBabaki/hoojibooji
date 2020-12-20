package com.futuredeveloper.android.shopfinder.internet;

import java.util.ArrayList;
import org.json.JSONArray;


public class StrucFilter {

    public String                     header;
    public String                     propertyName;
    public int                        select;
    public JSONArray                  JsonArrayFilter;
    public ArrayList<StrucItemFilter> listItemFilter = new ArrayList<StrucItemFilter>();

}
