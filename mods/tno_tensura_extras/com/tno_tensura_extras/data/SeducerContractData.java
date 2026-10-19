package com.tno_tensura_extras.data;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

public final class SeducerContractData extends SavedData {
   private static final String DATA_NAME = "tno_tensura_extras_seducer_contracts";
   private static final String CONTRACTS = "contracts";
   private static final String SUBJECT = "subject";
   private static final String OWNER = "owner";
   private static final String FOLLOWING = "following";
   private static final String WAS_PERSISTENT = "was_persistent";
   private static final Factory<SeducerContractData> FACTORY = new Factory(SeducerContractData::new, SeducerContractData::load);
   private final Map<UUID, SeducerContractData.Contract> contracts = new HashMap<>();

   public static SeducerContractData get(MinecraftServer server) {
      return (SeducerContractData)server.overworld().getDataStorage().computeIfAbsent(FACTORY, "tno_tensura_extras_seducer_contracts");
   }

   @Nullable
   public SeducerContractData.Contract getContract(UUID subject) {
      return this.contracts.get(subject);
   }

   public boolean isBoundTo(UUID subject, UUID owner) {
      SeducerContractData.Contract contract = this.contracts.get(subject);
      return contract != null && contract.owner().equals(owner);
   }

   public int countOwnedBy(UUID owner) {
      int count = 0;

      for (SeducerContractData.Contract contract : this.contracts.values()) {
         if (contract.owner().equals(owner)) {
            count++;
         }
      }

      return count;
   }

   public boolean canBind(UUID owner, UUID subject) {
      if (owner != null && subject != null && !owner.equals(subject) && !this.contracts.containsKey(subject)) {
         Set<UUID> visited = new HashSet<>();
         UUID current = owner;

         while (current != null) {
            if (current.equals(subject) || !visited.add(current)) {
               return false;
            }

            SeducerContractData.Contract contract = this.contracts.get(current);
            current = contract == null ? null : contract.owner();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean bind(UUID owner, UUID subject, boolean wasPersistent) {
      if (!this.canBind(owner, subject)) {
         return false;
      }

      this.contracts.put(subject, new SeducerContractData.Contract(owner, true, wasPersistent));
      this.setDirty();
      return true;
   }

   public boolean release(UUID owner, UUID subject) {
      if (!this.isBoundTo(subject, owner)) {
         return false;
      }

      this.remove(subject);
      return true;
   }

   public void remove(UUID subject) {
      if (this.contracts.remove(subject) != null) {
         this.setDirty();
      }
   }

   public boolean setFollowing(UUID owner, UUID subject, boolean following) {
      SeducerContractData.Contract contract = this.contracts.get(subject);
      if (contract != null && contract.owner().equals(owner)) {
         if (contract.following() != following) {
            this.contracts.put(subject, new SeducerContractData.Contract(owner, following, contract.wasPersistent()));
            this.setDirty();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean areRelated(UUID a, UUID b) {
      if (a != null && b != null) {
         SeducerContractData.Contract first = this.contracts.get(a);
         SeducerContractData.Contract second = this.contracts.get(b);
         return first != null && first.owner().equals(b)
            || second != null && second.owner().equals(a)
            || first != null && second != null && first.owner().equals(second.owner());
      } else {
         return false;
      }
   }

   public Map<UUID, SeducerContractData.Contract> entries() {
      return Map.copyOf(this.contracts);
   }

   public static SeducerContractData load(CompoundTag tag, Provider registries) {
      SeducerContractData data = new SeducerContractData();
      Tag rawContracts = tag.get("contracts");
      if (!(rawContracts instanceof ListTag entries)) {
         if (rawContracts != null) {
            data.setDirty();
         }

         return data;
      } else {
         boolean repaired = false;

         for (Tag rawEntry : entries) {
            if (rawEntry instanceof CompoundTag entry
               && entry.hasUUID("subject")
               && entry.hasUUID("owner")
               && entry.contains("following", 1)
               && entry.contains("was_persistent", 1)) {
               UUID subject = entry.getUUID("subject");
               UUID owner = entry.getUUID("owner");
               if (!subject.equals(owner) && !data.contracts.containsKey(subject)) {
                  data.contracts.put(subject, new SeducerContractData.Contract(owner, entry.getBoolean("following"), entry.getBoolean("was_persistent")));
               } else {
                  repaired = true;
               }
            } else {
               repaired = true;
            }
         }

         if (data.removeCycles() || repaired) {
            data.setDirty();
         }

         return data;
      }
   }

   public CompoundTag save(CompoundTag tag, Provider registries) {
      ListTag entries = new ListTag();
      this.contracts.forEach((subject, contract) -> {
         CompoundTag entry = new CompoundTag();
         entry.putUUID("subject", subject);
         entry.putUUID("owner", contract.owner());
         entry.putBoolean("following", contract.following());
         entry.putBoolean("was_persistent", contract.wasPersistent());
         entries.add(entry);
      });
      tag.put("contracts", entries);
      return tag;
   }

   private boolean removeCycles() {
      Set<UUID> checked = new HashSet<>();
      Set<UUID> cyclic = new HashSet<>();

      for (UUID subject : this.contracts.keySet()) {
         Set<UUID> path = new HashSet<>();

         label33:
         for (UUID current = subject; this.contracts.containsKey(current) && !checked.contains(current); current = this.contracts.get(current).owner()) {
            if (!path.add(current)) {
               UUID cycleStart = current;

               while (true) {
                  cyclic.add(current);
                  current = this.contracts.get(current).owner();
                  if (current.equals(cycleStart)) {
                     break label33;
                  }
               }
            }
         }

         checked.addAll(path);
      }

      cyclic.forEach(this.contracts::remove);
      return !cyclic.isEmpty();
   }

   public record Contract(UUID owner, boolean following, boolean wasPersistent) {
      public Contract {
         Objects.requireNonNull(owner, "owner");
      }
   }
}
