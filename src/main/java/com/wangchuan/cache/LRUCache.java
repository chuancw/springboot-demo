package com.wangchuan.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class LRUCache<K, V> {

    private final int MAX_CACHE_SIZE;

    private final float DEFAULT_LOAD_FACTORY = 0.75f;

    private LinkedHashMap<K, V> map;


    public LRUCache(int cacheSize) {
        MAX_CACHE_SIZE = cacheSize;
        int capacity = (int)Math.ceil(MAX_CACHE_SIZE / DEFAULT_LOAD_FACTORY) + 1;
        /*
         * 第三个参数设置为true，代表linkedlist按访问顺序排序，可作为LRU缓存
         * 第三个参数设置为false，代表按插入顺序排序，可作为FIFO缓存
         */
        map = new LinkedHashMap<K, V>(capacity, DEFAULT_LOAD_FACTORY, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > MAX_CACHE_SIZE;
            }
        };
    }

    public void put(K key, V value) {
        map.put(key, value);
    }

    public V get(K key) {
        return map.get(key);
    }

    public void remove(K key) {
        map.remove(key);
    }

    public Set<Map.Entry<K, V>> getAll() {
        return map.entrySet();
    }
}
