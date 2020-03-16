package ru.javawebinar.basejava.util;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ConverterUtils {

    public static <K,V> Map<K,V> setToMap(Set<Map.Entry<K, V>> set){
        return set.stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
