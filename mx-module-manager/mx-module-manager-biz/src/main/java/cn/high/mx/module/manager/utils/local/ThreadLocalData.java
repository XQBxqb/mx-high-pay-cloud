package cn.high.mx.module.manager.utils.local;


import javafx.util.Pair;

import java.util.HashMap;

public class ThreadLocalData {
    private static ThreadLocal<HashMap<String,String>> threadLocal = new ThreadLocal<>();
    private static HashMap<String,String> hashMap = new HashMap<>();
    public static final String PERMISSIONS = "permissons";

    public static final String TOKEN = "token";

    static {
        threadLocal.set(hashMap);
    }

    public static void setThreadLocal(Pair<String,String> pair){
        hashMap.put(pair.getKey(),pair.getValue());
    }

    public static String getThreadLocal(String key){
        return hashMap.get(key);
    }

    public static void destroy(){
        threadLocal.remove();
    }
}
