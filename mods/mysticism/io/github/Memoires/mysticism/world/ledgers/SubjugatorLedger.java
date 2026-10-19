package io.github.Memoires.mysticism.world.ledgers;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public class SubjugatorLedger extends SavedData {
   public static final String STORAGE_KEY = "mysticism_subjugator_ledger";
   private final Map<UUID, Set<UUID>> deadByOwner = new HashMap<>();
   public static final Factory<SubjugatorLedger> FACTORY = new Factory(SubjugatorLedger::new, SubjugatorLedger::load, null);

   public static SubjugatorLedger get(ServerLevel level) {
      return (SubjugatorLedger)level.getServer().overworld().getDataStorage().computeIfAbsent(FACTORY, "mysticism_subjugator_ledger");
   }

   public void markDead(UUID owner, UUID subordinate) {
      if (this.deadByOwner.computeIfAbsent(owner, k -> new HashSet<>()).add(subordinate)) {
         this.setDirty();
      }
   }

   public Set<UUID> consumeFor(UUID owner) {
      Set<UUID> set = this.deadByOwner.remove(owner);
      if (set == null) {
         return Collections.emptySet();
      }

      this.setDirty();
      return set;
   }

   public CompoundTag save(CompoundTag tag, Provider provider) {
      ListTag owners = new ListTag();

      for (Map.Entry<UUID, Set<UUID>> row : this.deadByOwner.entrySet()) {
         CompoundTag o = new CompoundTag();
         o.putUUID("owner", row.getKey());
         ListTag list = new ListTag();

         for (UUID sub : row.getValue()) {
            CompoundTag t = new CompoundTag();
            t.putUUID("subordinate", sub);
            list.add(t);
         }

         o.put("list", list);
         owners.add(o);
      }

      tag.put("owners", owners);
      return tag;
   }

   public static SubjugatorLedger load(CompoundTag tag, Provider provider) {
      SubjugatorLedger d = new SubjugatorLedger();
      ListTag owners = tag.getList("owners", 10);

      for (int i = 0; i < owners.size(); i++) {
         CompoundTag o = owners.getCompound(i);
         UUID owner = o.getUUID("owner");
         ListTag list = o.getList("list", 10);
         Set<UUID> set = new HashSet<>(list.size());

         for (int j = 0; j < list.size(); j++) {
            set.add(list.getCompound(j).getUUID("subordinate"));
         }

         if (!set.isEmpty()) {
            d.deadByOwner.put(owner, set);
         }
      }

      return d;
   }

   public static final class Entry {
      public UUID subordinate;

      public Entry() {
      }

      public Entry(UUID subordinate) {
         this.subordinate = subordinate;
      }

      public CompoundTag save() {
         CompoundTag t = new CompoundTag();
         t.putUUID("subordinate", this.subordinate);
         return t;
      }

      public static SubjugatorLedger.Entry load(CompoundTag t) {
         return new SubjugatorLedger.Entry(t.getUUID("subordinate"));
      }
   }

   public enum Status {
      ALIVE,
      DEAD;
   }
}
