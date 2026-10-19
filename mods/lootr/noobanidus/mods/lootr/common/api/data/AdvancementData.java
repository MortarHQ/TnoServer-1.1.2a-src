package noobanidus.mods.lootr.common.api.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import org.jetbrains.annotations.NotNull;

@Deprecated
public class AdvancementData extends SavedData {
   @Deprecated
   public static final Factory<AdvancementData> FACTORY = new Factory(AdvancementData::new, AdvancementData::load, null);
   private final Set<AdvancementData.UUIDPair> data = new HashSet<>();

   public static AdvancementData load(CompoundTag compound, Provider provider) {
      AdvancementData data = new AdvancementData();
      data.data.clear();
      ListTag incoming = compound.getList("data", 10);

      for (int i = 0; i < incoming.size(); i++) {
         data.data.add(AdvancementData.UUIDPair.fromNBT(provider, incoming.getCompound(i)));
      }

      return data;
   }

   public boolean contains(UUID first, UUID second) {
      return this.contains(new AdvancementData.UUIDPair(first, second));
   }

   public boolean contains(AdvancementData.UUIDPair pair) {
      return !this.data.isEmpty() && this.data.contains(pair);
   }

   public void add(UUID first, UUID second) {
      this.add(new AdvancementData.UUIDPair(first, second));
   }

   public void add(AdvancementData.UUIDPair pair) {
      this.data.add(pair);
      this.setDirty();
   }

   public CompoundTag save(CompoundTag pCompound, Provider provider) {
      ListTag result = new ListTag();

      for (AdvancementData.UUIDPair pair : this.data) {
         result.add(pair.serializeNBT(provider));
      }

      pCompound.put("data", result);
      return pCompound;
   }

   public static class UUIDPair {
      @NotNull
      private UUID first;
      private UUID second;

      protected UUIDPair() {
      }

      public UUIDPair(@NotNull UUID first, @NotNull UUID second) {
         this.first = first;
         this.second = second;
      }

      public static AdvancementData.UUIDPair fromNBT(Provider provider, CompoundTag tag) {
         AdvancementData.UUIDPair pair = new AdvancementData.UUIDPair();
         pair.deserializeNBT(provider, tag);
         return pair;
      }

      @NotNull
      public UUID getFirst() {
         return this.first;
      }

      @NotNull
      public UUID getSecond() {
         return this.second;
      }

      @Override
      public boolean equals(Object o) {
         if (this == o) {
            return true;
         } else if (o != null && this.getClass() == o.getClass()) {
            AdvancementData.UUIDPair uuidPair = (AdvancementData.UUIDPair)o;
            return !this.first.equals(uuidPair.first) ? false : this.second.equals(uuidPair.second);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int result = this.first.hashCode();
         return 31 * result + this.second.hashCode();
      }

      public CompoundTag serializeNBT(Provider provider) {
         CompoundTag result = new CompoundTag();
         result.putUUID("first", this.getFirst());
         result.putUUID("second", this.getSecond());
         return result;
      }

      public void deserializeNBT(Provider provider, CompoundTag nbt) {
         this.first = nbt.getUUID("first");
         this.second = nbt.getUUID("second");
      }
   }
}
