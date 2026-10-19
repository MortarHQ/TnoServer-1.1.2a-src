package dev.ftb.mods.ftbfiltersystem.util;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> {
   private final int capacity;
   private final Map<K, V> map;
   private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

   public LRUCache(int capacity) {
      this.capacity = capacity;
      this.map = new LinkedHashMap<K, V>(capacity, 0.75F, true) {
         @Override
         protected boolean removeEldestEntry(Entry<K, V> eldest) {
            return this.size() > LRUCache.this.capacity;
         }
      };
   }

   public V get(K key) {
      this.lock.readLock().lock();

      try {
         return this.map.get(key);
      } finally {
         this.lock.readLock().unlock();
      }
   }

   public void put(K key, V value) {
      this.lock.writeLock().lock();

      try {
         this.map.put(key, value);
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   public void clear() {
      this.lock.writeLock().lock();

      try {
         this.map.clear();
      } finally {
         this.lock.writeLock().unlock();
      }
   }

   @Override
   public String toString() {
      this.lock.readLock().lock();

      try {
         return this.map.toString();
      } finally {
         this.lock.readLock().unlock();
      }
   }
}
