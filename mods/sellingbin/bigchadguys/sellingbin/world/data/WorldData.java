package bigchadguys.sellingbin.world.data;

import bigchadguys.sellingbin.data.serializable.INbtSerializable;
import java.io.File;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.saveddata.SavedData;

public abstract class WorldData extends SavedData implements INbtSerializable<CompoundTag> {
   public CompoundTag save(CompoundTag nbt, Provider registryLookup) {
      return this.writeNbt().orElse(nbt);
   }

   public void save(File file, Provider registryLookup) {
      file.getParentFile().mkdirs();
      super.save(file, registryLookup);
   }
}
