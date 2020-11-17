package com.sjsu.sister.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JsonParser {
    private HashMap<String,String> parseJsonObject(JSONObject object){
        HashMap<String,String> dataList = new HashMap<>();
        try{
            String name = object.getString("name");
            String latitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lat");
            String longitude = object.getJSONObject("geometry")
                    .getJSONObject("location").getString("lng");
            String rating = object.getString("rating");
            String vicinity= object.getString("vicinity");
            dataList.put("name",name);
            dataList.put("lat",latitude);
            dataList.put("lng",longitude);
            dataList.put("rating",rating);
            dataList.put("vicinity",vicinity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return dataList;
    }

    private List<HashMap<String, String>> parseJsonArray(JSONArray jsonArray){
        List<HashMap<String, String>> dataList = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i++){
            try{
                HashMap<String, String> data = parseJsonObject((JSONObject) jsonArray.get(i));
                dataList.add(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return dataList;
    }

    public List<HashMap<String, String>> parseResult(JSONObject object){
        JSONArray jsonArray = null;
        try{
            jsonArray = object.getJSONArray("results");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return parseJsonArray(jsonArray);
    }
}
