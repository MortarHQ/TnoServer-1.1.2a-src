package noobanidus.mods.lootr.common.api.data;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.io.File;
import java.util.UUID;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;

@Deprecated
public class TickingData extends SavedData {
   public static final Factory<TickingData> FACTORY = new Factory(TickingData::new, TickingData::load, null);
   private final Object2IntMap<UUID> tickMap = new Object2IntOpenHashMap();

   public TickingData() {
      this.tickMap.defaultReturnValue(-1);
   }

   public static TickingData load(CompoundTag pCompound, Provider provider) {
      TickingData data = new TickingData();
      data.tickMap.clear();
      data.tickMap.defaultReturnValue(-1);
      ListTag decayList = pCompound.getList("result", 10);

      for (int i = 0; i < decayList.size(); i++) {
         CompoundTag thisTag = decayList.getCompound(i);
         data.tickMap.put(thisTag.getUUID("id"), thisTag.getInt("value"));
      }

      return data;
   }

   public boolean isComplete(UUID id) {
      int value = this.getValue(id);
      return value == 0 || value == 1;
   }

   public int getValue(UUID id) {
      return this.tickMap.getInt(id);
   }

   public void setValue(UUID id, int decayAmount) {
      if (this.tickMap.put(id, decayAmount) == -1) {
         this.setDirty();
      }
   }

   public void remove(UUID id) {
      if (this.tickMap.removeInt(id) != -1) {
         this.setDirty();
      }
   }

   public void tick() {
      if (!this.tickMap.isEmpty()) {
         Object2IntMap<UUID> newMap = new Object2IntOpenHashMap();
         newMap.defaultReturnValue(-1);
         boolean changed = false;
         ObjectIterator var3 = this.tickMap.object2IntEntrySet().iterator();

         while (var3.hasNext()) {
            Entry<UUID> entry = (Entry<UUID>)var3.next();
            int value = entry.getIntValue();
            if (value > 0) {
               value--;
               changed = true;
            }

            newMap.put((UUID)entry.getKey(), value);
         }

         if (changed) {
            this.tickMap.clear();
            this.tickMap.putAll(newMap);
            this.setDirty();
         }
      }
   }

   public CompoundTag save(CompoundTag pCompound, Provider provider) {
      ListTag decayList = new ListTag();
      ObjectIterator var4 = this.tickMap.object2IntEntrySet().iterator();

      while (var4.hasNext()) {
         Entry<UUID> entry = (Entry<UUID>)var4.next();
         CompoundTag thisTag = new CompoundTag();
         thisTag.putUUID("id", (UUID)entry.getKey());
         thisTag.putInt("value", entry.getIntValue());
         decayList.add(thisTag);
      }

      pCompound.put("result", decayList);
      return pCompound;
   }

   public void save(File file, Provider registries) {
      if (this.isDirty()) {
         file.getParentFile().mkdirs();
      }

      super.save(file, registries);
   }

   public Object2IntMap<UUID> getTickMap() {
      return this.tickMap;
   }

   public void clear() {
      this.tickMap.clear();
      this.setDirty();
   }
}
