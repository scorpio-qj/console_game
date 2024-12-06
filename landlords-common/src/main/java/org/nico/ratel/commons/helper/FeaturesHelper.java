package org.nico.ratel.commons.helper;

import java.util.*;

public class FeaturesHelper {

    public static final  String VERSION = "v0.0.1";

    private static final  Map<String, List<Features>> FEATURES = new HashMap<>();

    static{
        FEATURES.put(VERSION, Collections.singletonList(Features.READY));
    }


    /**
     * 版本是否包含某个功能
     * @param clientVersion
     * @param feature
     * @return
     */
    public static boolean supported(String clientVersion, Features feature){
        List<Features> features = FEATURES.get(clientVersion);
        if (Objects.isNull(features) || Objects.isNull(feature)){
            return false;
        }
        return features.contains(feature);
    }

    /**
     * 功能类型
     */
    public enum Features{

        //准备
        READY,


    }

}
