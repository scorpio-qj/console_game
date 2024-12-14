package org.nico.ratel.commons.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.nico.noson.Noson;
import org.nico.noson.entity.NoType;
import org.nico.ratel.commons.helper.MapHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ProtoDataUtils {

    private static Gson gson;

    static {
        gson=new Gson();
    }


    public static <T> String toString(T data){

        if(data instanceof String){
            return (String) data;
        }
        return gson.toJson(data);
    }

    public static <T> T toObject(String data,Class<T> clazz){

        return gson.fromJson(data, clazz);
    }

    public static  MapBuilder mapBuilder( ){

        return new MapBuilder();
    }


    public static class MapBuilder{

        private final Map<String,String> map;

        private MapBuilder(){
            map=new HashMap<>();
        }

        public MapBuilder put(String key,Object value){
            map.put(key,String.valueOf(value));
            return this;
        }

        public Map<String,String> build(){
            return map;
        }

        public String toString(){
           return ProtoDataUtils.toString(map);
        }

    }
}
