package io.github.Memoires.mysticism.event;

import io.github.manasmods.manascore.skill.api.EntityEvents;
import io.github.manasmods.manascore.skill.api.EntityEvents.LivingTickEvent;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class InventoryAddWatcher {
   private static final Map<ServerPlayer, List<ItemStack>> SNAP = new IdentityHashMap<>();
   static final Map<ServerPlayer, Integer> LAST_CHANGE = new IdentityHashMap<>();

   public static void register(InventoryAddWatcher.AddedCallback cb) {
      EntityEvents.LIVING_POST_TICK.register((LivingTickEvent)entity -> {
         if (entity instanceof ServerPlayer p) {
            int changed = p.getInventory().getTimesChanged();
            Integer last = LAST_CHANGE.get(p);
            if (last == null || last != changed) {
               Inventory inv = p.getInventory();
               List<ItemStack> prev = SNAP.computeIfAbsent(p, __ -> snapshot(inv));
               List<ItemStack> now = snapshot(inv);

               for (int i = 0; i < now.size(); i++) {
                  ItemStack a = prev.get(i);
                  ItemStack b = now.get(i);
                  if (a.isEmpty() && !b.isEmpty()) {
                     cb.onAdded(p, b.copy(), b.getCount());
                  } else if (!a.isEmpty() && !b.isEmpty() && ItemStack.isSameItemSameComponents(a, b) && b.getCount() > a.getCount()) {
                     cb.onAdded(p, b.copy(), b.getCount() - a.getCount());
                  } else if (!a.isEmpty() && !b.isEmpty() && !ItemStack.isSameItemSameComponents(a, b)) {
                     cb.onAdded(p, b.copy(), b.getCount());
                  }
               }

               SNAP.put(p, now);
               LAST_CHANGE.put(p, changed);
            }
         }
      });
   }

   private static List<ItemStack> snapshot(Inventory inv) {
      int size = inv.items.size() + inv.armor.size() + inv.offhand.size();
      List<ItemStack> out = new ArrayList<>(size);
      inv.items.forEach(s -> out.add(s.copy()));
      inv.armor.forEach(s -> out.add(s.copy()));
      inv.offhand.forEach(s -> out.add(s.copy()));
      return out;
   }

   public interface AddedCallback {
      void onAdded(ServerPlayer var1, ItemStack var2, int var3);
   }
}
