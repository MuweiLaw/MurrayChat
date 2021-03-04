package com.murray.utils;

import io.lettuce.core.dynamic.support.ReflectionUtils;
import org.springframework.beans.BeanUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Murray Law
 * @describe 类的工具类
 * @createTime 2020/11/20
 */
public class ClassUtil {

    public static Map<String, Object> parseObj2Map(Object args) {
        return Arrays.stream(BeanUtils.getPropertyDescriptors(args.getClass()))
                .filter(pd -> !"class".equals(pd.getName()))
                .collect(HashMap::new,
                        (map, pd) -> map.put(pd.getName(), ReflectionUtils.invokeMethod(pd.getReadMethod(), args)),
                        HashMap::putAll);
    }
}
