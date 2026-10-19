package com.tom.storagemod.inventory;

import com.tom.storagemod.Config;
import com.tom.storagemod.block.entity.IInventoryConnector;
import com.tom.storagemod.inventory.filter.ItemPredicate;
import com.tom.storagemod.util.Priority;
import com.tom.storagemod.util.WorldStates;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class MultiInventoryAccess implements IInventoryAccess {
   protected List<IInventoryAccess> connected = new ArrayList<>();
   protected MultiInventoryAccess.MultiChangeTracker tracker = new MultiInventoryAccess.MultiChangeTracker();

   public MultiInventoryAccess() {
      WorldStates.trackers.put(this.getPlatformHandler(), this.tracker());
   }

   public void build(IInventoryConnector self, Collection<IInventoryConnector> connectors) {
      this.connected.clear();
      Queue<IInventoryConnector> q = new ArrayDeque<>();
      q.add(self);
      q.addAll(connectors);
      Set<IInventoryConnector> all = new HashSet<>();

      while (!q.isEmpty()) {
         IInventoryConnector ic = q.poll();
         if (all.add(ic)) {
            q.addAll(ic.getConnectedConnectors());
         }
      }

      Set<Object> seenHandlers = new HashSet<>();
      EnumMap<Priority, List<IInventoryAccess>> map = all.stream()
         .flatMap(c -> c.getConnectedInventories().stream())
         .filter(e -> seenHandlers.add(e.get()))
         .collect(Collectors.groupingBy(Priority.IPriority.GETTER, () -> new EnumMap<>(Priority.class), Collectors.toList()));
      Set<IInventoryAccess> allRoots = new HashSet<>();
      allRoots.add(this);
      Set<IProxy> dejaVu = Collections.newSetFromMap(new IdentityHashMap<>());

      for (int i = Priority.VALUES.length - 1; i >= 0; i--) {
         for (IInventoryAccess a : map.getOrDefault(Priority.VALUES[i], Collections.emptyList())) {
            IInventoryAccess root = a.getRootHandler(dejaVu);
            if (!(root instanceof MultiInventoryAccess) && allRoots.add(root)) {
               this.connected.add(a);
            }
         }
      }

      this.refresh();
   }

   @Override
   public ItemStack pullMatchingStack(ItemStack st, long max) {
      ItemStack res = ItemStack.EMPTY;
      int c = 0;

      for (int i = 0; i < this.connected.size(); i++) {
         ItemStack p = this.connected.get(i).pullMatchingStack(st, max - c);
         if (!p.isEmpty()) {
            if (res.isEmpty()) {
               res = p;
            }

            c += p.getCount();
            if (c >= max) {
               break;
            }
         }
      }

      res.setCount(c);
      return res;
   }

   @Override
   public ItemStack pushStack(ItemStack stack) {
      for (int i = 0; i < this.connected.size(); i++) {
         stack = this.connected.get(i).pushStack(stack);
         if (stack.isEmpty()) {
            return ItemStack.EMPTY;
         }
      }

      return stack;
   }

   @Override
   public IInventoryAccess.IInventoryChangeTracker tracker() {
      return this.tracker;
   }

   @Override
   public int getFreeSlotCount() {
      int c = 0;

      for (int i = 0; i < this.connected.size(); i++) {
         c += this.connected.get(i).getFreeSlotCount();
      }

      return c;
   }

   @Override
   public int getSlotCount() {
      int c = 0;

      for (int i = 0; i < this.connected.size(); i++) {
         c += this.connected.get(i).getSlotCount();
      }

      return c;
   }

   public void clear() {
      this.connected.clear();
   }

   public int getInventoryCount() {
      return this.connected.size();
   }

   protected void refresh() {
   }

   @Override
   public IInventoryAccess getRootHandler(Set<IProxy> dejaVu) {
      return this;
   }

   public Collection<IInventoryAccess> getConnected() {
      return this.connected;
   }

   private static class ItemList extends ArrayList<StoredItemStack> {
      private static final long serialVersionUID = 6690277901361998268L;
   }

   protected class MultiChangeTracker implements IInventoryAccess.IInventoryChangeTracker {
      private long lastUpdate;
      private long lastChange;
      private long[] trackers = new long[0];
      private MultiInventoryAccess.ItemList[] items = new MultiInventoryAccess.ItemList[0];

      private boolean multithreadProcessing(Level level) {
         int size = MultiInventoryAccess.this.connected.size();
         List<MultiInventoryAccess.TrackerInfo> infos = new ArrayList<>(size);
         boolean ch = false;

         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess.IInventoryChangeTracker tr = MultiInventoryAccess.this.connected.get(i).tracker();
            if (tr instanceof IInventoryAccess.IMultiThreadedTracker mt) {
               Object prep = mt.prepForOffThread(level);
               if (prep != null) {
                  infos.add(new MultiInventoryAccess.TrackerInfo(i, mt, prep));
                  continue;
               }
            }

            long v = tr.getChangeTracker(level);
            if (v != this.trackers[i]) {
               ch |= true;
               this.items[i] = null;
               this.trackers[i] = v;
            }
         }

         infos.parallelStream().unordered().peek(MultiInventoryAccess.TrackerInfo::run).toArray();

         for (MultiInventoryAccess.TrackerInfo tr : infos) {
            tr.finish(level);
            int i = tr.id;
            long v = tr.tracker;
            if (v != this.trackers[i]) {
               ch |= true;
               this.items[i] = null;
               this.trackers[i] = v;
            }
         }

         return ch;
      }

      @Override
      public long getChangeTracker(Level level) {
         if (this.lastUpdate != level.getGameTime()) {
            if (this.trackers.length != MultiInventoryAccess.this.connected.size()) {
               this.trackers = new long[MultiInventoryAccess.this.connected.size()];
               this.items = new MultiInventoryAccess.ItemList[MultiInventoryAccess.this.connected.size()];

               for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
                  IInventoryAccess.IInventoryChangeTracker tr = MultiInventoryAccess.this.connected.get(i).tracker();
                  this.trackers[i] = tr.getChangeTracker(level);
               }

               this.lastChange = System.nanoTime();
               return this.lastChange;
            }

            boolean ch = false;
            if (Config.get().runMultithreaded) {
               ch = this.multithreadProcessing(level);
            } else {
               for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
                  IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
                  long v = ia.tracker().getChangeTracker(level);
                  if (v != this.trackers[i]) {
                     ch |= true;
                     this.items[i] = null;
                     this.trackers[i] = v;
                  }
               }
            }

            if (ch) {
               this.lastChange = System.nanoTime();
            }

            this.lastUpdate = level.getGameTime();
         }

         return this.lastChange;
      }

      @Override
      public Stream<StoredItemStack> streamWrappedStacks(boolean parallel) {
         Stream<MultiInventoryAccess.ItemList> str = IntStream.range(0, this.items.length)
            .mapToObj(
               i -> {
                  if (this.items[i] == null) {
                     this.items[i] = MultiInventoryAccess.this.connected
                        .get(i)
                        .tracker()
                        .streamWrappedStacks(false)
                        .collect(Collectors.toCollection(MultiInventoryAccess.ItemList::new));
                  }

                  return this.items[i];
               }
            );
         return parallel ? (Stream)str.toList().parallelStream().flatMap(Collection::parallelStream).unordered() : str.flatMap(Collection::stream);
      }

      @Override
      public long countItems(StoredItemStack filter) {
         long c = 0L;

         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
            c += ia.tracker().countItems(filter);
         }

         return c;
      }

      @Override
      public InventorySlot findSlot(ItemPredicate filter, boolean findEmpty) {
         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
            InventorySlot is = ia.tracker().findSlot(filter, findEmpty);
            if (is != null) {
               return is;
            }
         }

         return null;
      }

      @Override
      public InventorySlot findSlotDest(StoredItemStack forStack) {
         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
            InventorySlot is = ia.tracker().findSlotDest(forStack);
            if (is != null) {
               return is;
            }
         }

         return null;
      }

      @Override
      public InventorySlot findSlotAfter(InventorySlot slot, ItemPredicate filter, boolean findEmpty, boolean loop) {
         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
            InventorySlot is = ia.tracker().findSlotAfter(slot, filter, findEmpty, loop);
            if (is != null) {
               return is;
            }
         }

         return null;
      }

      @Override
      public InventorySlot findSlotDestAfter(InventorySlot slot, StoredItemStack forStack, boolean loop) {
         for (int i = 0; i < MultiInventoryAccess.this.connected.size(); i++) {
            IInventoryAccess ia = MultiInventoryAccess.this.connected.get(i);
            InventorySlot is = ia.tracker().findSlotDestAfter(slot, forStack, loop);
            if (is != null) {
               return is;
            }
         }

         return null;
      }
   }

   public static class TrackerInfo {
      private int id;
      private IInventoryAccess.IMultiThreadedTracker<Object, Object> mt;
      private Object prep;
      private Object result;
      private long tracker;

      public TrackerInfo(int id, IInventoryAccess.IMultiThreadedTracker<?, ?> mt, Object prep) {
         this.id = id;
         this.mt = (IInventoryAccess.IMultiThreadedTracker<Object, Object>)mt;
         this.prep = prep;
      }

      public void run() {
         this.result = this.mt.processOffThread(this.prep);
      }

      public void finish(Level level) {
         this.tracker = this.mt.finishOffThreadProcess(level, this.result);
      }
   }
}
