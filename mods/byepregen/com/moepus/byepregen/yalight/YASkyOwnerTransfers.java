package com.moepus.byepregen.yalight;

import it.unimi.dsi.fastutil.longs.Long2ByteOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ByteMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectBidirectionalIterator;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Arrays;
import net.minecraft.world.level.ChunkPos;

final class YASkyOwnerTransfers {
   private static final byte TRANSIENT = 1;
   private static final byte DECLARED = 2;
   private static final byte INITIALIZED = 4;
   private static final int INITIAL_MAP_CAPACITY = 16;
   private static final int MAX_RETAINED_MAP_ENTRIES = 256;
   private static final int MAX_POOLED_INBOXES = 64;
   private static final int LEVEL_MASK = 15;
   private static final int DIRECTION_SHIFT = 4;
   private static final int DIRECTION_MASK = 7;
   private static final int UNSIGNED_BYTE_MASK = 255;
   private final Long2ByteOpenHashMap states = new Long2ByteOpenHashMap(16);
   private final Long2ObjectLinkedOpenHashMap<YASkyOwnerTransfers.OwnerInbox> inboxes = new Long2ObjectLinkedOpenHashMap(16);
   private final YASkyOwnerTransfers.EdgePropagator propagator;
   private YASkyOwnerTransfers.OwnerInbox pooledInbox;
   private int declaredCount;
   private int readyCount;
   private int pooledInboxCount;

   YASkyOwnerTransfers(YASkyOwnerTransfers.EdgePropagator propagator) {
      this.propagator = propagator;
   }

   void setDeclaredDomain(Iterable<ChunkPos> owners) {
      this.clearPersistentState();

      for (ChunkPos pos : owners) {
         long ownerKey = pos.toLong();
         byte state = this.states.get(ownerKey);
         if ((state & 2) == 0) {
            this.states.put(ownerKey, (byte)(state | 2));
            this.declaredCount++;
         }
      }
   }

   void register(long ownerKey) {
      byte state = this.states.get(ownerKey);
      if (state == 0) {
         this.states.put(ownerKey, (byte)1);
      }
   }

   void markInitialized(long ownerKey) {
      byte state = this.states.get(ownerKey);
      if ((state & 4) == 0) {
         this.states.put(ownerKey, (byte)(state | 4));
         if (this.inboxes.getAndMoveToFirst(ownerKey) != null) {
            this.readyCount++;
         }
      }
   }

   void remove(long ownerKey) {
      byte state = this.states.remove(ownerKey);
      if ((state & 2) != 0) {
         this.declaredCount--;
      }

      YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)this.inboxes.remove(ownerKey);
      if (inbox != null) {
         if ((state & 4) != 0) {
            this.readyCount--;
         }

         this.recycleInbox(inbox);
      }
   }

   byte state(long ownerKey) {
      return this.states.get(ownerKey);
   }

   boolean isTransferTarget(byte state) {
      return state != 0;
   }

   boolean isInitialized(byte state) {
      return (state & 4) != 0;
   }

   void enqueue(long ownerKey, long fromPos, int level, int directionIndex) {
      YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)this.inboxes.get(ownerKey);
      if (inbox == null) {
         inbox = this.acquireInbox();
         if (this.isInitialized(this.states.get(ownerKey))) {
            this.inboxes.putAndMoveToFirst(ownerKey, inbox);
            this.readyCount++;
         } else {
            this.inboxes.put(ownerKey, inbox);
         }
      }

      inbox.add(fromPos, level, directionIndex);
   }

   int drain() {
      int work = this.drainReady(true);
      return work <= 0 && !this.inboxes.isEmpty() ? this.drainFallback() : work;
   }

   void finishRun() {
      if (this.declaredCount == 0) {
         this.clearPersistentState();
      } else {
         if (this.states.size() != this.declaredCount) {
            ObjectIterator<Entry> iterator = this.states.long2ByteEntrySet().fastIterator();

            while (iterator.hasNext()) {
               Entry entry = (Entry)iterator.next();
               byte state = entry.getByteValue();
               if ((state & 1) != 0) {
                  YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)this.inboxes.remove(entry.getLongKey());
                  if (inbox != null) {
                     if ((state & 4) != 0) {
                        this.readyCount--;
                     }

                     this.recycleInbox(inbox);
                  }

                  iterator.remove();
               }
            }
         }

         this.trimMaps();
      }
   }

   void clearPersistentState() {
      this.states.clear();
      this.recycleInboxes();
      this.inboxes.clear();
      this.trimMaps();
      this.declaredCount = 0;
      this.readyCount = 0;
   }

   private int drainFallback() {
      int work = 0;
      ObjectBidirectionalIterator<it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry<YASkyOwnerTransfers.OwnerInbox>> iterator = this.inboxes
         .long2ObjectEntrySet()
         .fastIterator();

      while (iterator.hasNext()) {
         it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry<YASkyOwnerTransfers.OwnerInbox> entry = (it.unimi.dsi.fastutil.longs.Long2ObjectMap.Entry<YASkyOwnerTransfers.OwnerInbox>)iterator.next();
         byte state = this.states.get(entry.getLongKey());
         if ((state & 2) == 0) {
            YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)entry.getValue();
            iterator.remove();
            work += this.drainInbox(inbox, false);
         }
      }

      return work;
   }

   private int drainReady(boolean transferToOwner) {
      int work = 0;

      while (this.readyCount > 0) {
         YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)this.inboxes.removeFirst();
         this.readyCount--;
         work += this.drainInbox(inbox, transferToOwner);
      }

      return work;
   }

   private int drainInbox(YASkyOwnerTransfers.OwnerInbox inbox, boolean transferToOwner) {
      int work = 0;
      long childFlags = transferToOwner ? 288230376151711744L : 0L;

      try {
         while (!inbox.isEmpty()) {
            long fromPos = inbox.position();
            int edgeMeta = inbox.edgeMeta();
            inbox.remove();
            this.propagator.propagate(fromPos, edgeMeta & 15, edgeMeta >>> 4 & 7, childFlags);
            work++;
         }

         return work;
      } finally {
         this.recycleInbox(inbox);
      }
   }

   private YASkyOwnerTransfers.OwnerInbox acquireInbox() {
      YASkyOwnerTransfers.OwnerInbox inbox = this.pooledInbox;
      if (inbox == null) {
         return new YASkyOwnerTransfers.OwnerInbox();
      }

      this.pooledInbox = inbox.nextPooled;
      inbox.nextPooled = null;
      this.pooledInboxCount--;
      return inbox;
   }

   private void recycleInbox(YASkyOwnerTransfers.OwnerInbox inbox) {
      if (this.pooledInboxCount < 64) {
         inbox.clearForReuse();
         inbox.nextPooled = this.pooledInbox;
         this.pooledInbox = inbox;
         this.pooledInboxCount++;
      }
   }

   private void recycleInboxes() {
      ObjectIterator var1 = this.inboxes.values().iterator();

      while (var1.hasNext()) {
         YASkyOwnerTransfers.OwnerInbox inbox = (YASkyOwnerTransfers.OwnerInbox)var1.next();
         this.recycleInbox(inbox);
      }
   }

   private void trimMaps() {
      this.states.trim(Math.max(this.states.size(), 256));
      this.inboxes.trim(Math.max(this.inboxes.size(), 256));
   }

   @FunctionalInterface
   interface EdgePropagator {
      void propagate(long var1, int var3, int var4, long var5);
   }

   private static final class OwnerInbox {
      private static final int INITIAL_CAPACITY = 32;
      private static final int MAX_RETAINED_CAPACITY = 256;
      private long[] positions = new long[32];
      private byte[] edgeMetas = new byte[32];
      private int readIndex;
      private int writeIndex;
      private YASkyOwnerTransfers.OwnerInbox nextPooled;

      private void add(long position, int level, int directionIndex) {
         if (this.writeIndex >= this.positions.length) {
            int newCapacity = this.positions.length << 1;
            this.positions = Arrays.copyOf(this.positions, newCapacity);
            this.edgeMetas = Arrays.copyOf(this.edgeMetas, newCapacity);
         }

         this.positions[this.writeIndex] = position;
         this.edgeMetas[this.writeIndex] = (byte)(level | directionIndex << 4);
         this.writeIndex++;
      }

      private boolean isEmpty() {
         return this.readIndex >= this.writeIndex;
      }

      private long position() {
         return this.positions[this.readIndex];
      }

      private int edgeMeta() {
         return this.edgeMetas[this.readIndex] & 0xFF;
      }

      private void remove() {
         this.readIndex++;
      }

      private void clearForReuse() {
         if (this.positions.length > 256) {
            this.positions = new long[256];
            this.edgeMetas = new byte[256];
         }

         this.readIndex = 0;
         this.writeIndex = 0;
      }
   }
}
