package com.groot.utils.json;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SubDong on 2015/2/26.
 */
public class JSONJudge {
    public static String getJsonDge(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            for (int i = 1; i <= 20; i++) {
                boolean judge = jsonObject.isNull("SEO1_" + i + "_problems");
//                List<Object> objects = new ArrayList<>();
                if (!judge) {
                    JSONArray jsonArray = jsonObject.getJSONArray("SEO1_" + i + "_problems");
                    jsonObject.put("SEO1_" + i + "_num", jsonArray.length());
                }
            }
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}

