package dev.ftb.mods.ftbchunks.util;

import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.snbt.config.BaseValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.level.ChunkPos;
import org.jetbrains.annotations.Nullable;

public class ChunkPosCustomYSetValue extends BaseValue<Set<ChunkPosWithMinY>> {
   private final HashMap<Long, Integer> lookup = new HashMap<>();

   public ChunkPosCustomYSetValue(@Nullable SNBTConfig c, String n, Set<ChunkPosWithMinY> def) {
      super(c, n, def);
      super.set(new HashSet());
   }

   public void write(SNBTCompoundTag tag) {
      ListTag listTag = new ListTag();

      for (ChunkPosWithMinY pos : (Set)this.get()) {
         SNBTCompoundTag posTag = new SNBTCompoundTag();
         posTag.putInt("x", pos.chunkX());
         posTag.putInt("z", pos.chunkZ());
         posTag.putInt("min_y", pos.minY());
         listTag.add(posTag);
      }

      tag.put(this.key, listTag);
   }

   public void read(SNBTCompoundTag tag) {
      List<SNBTCompoundTag> list = tag.getList(this.key, SNBTCompoundTag.class);
      Set<ChunkPosWithMinY> set = new HashSet<>();

      for (SNBTCompoundTag posTag : list) {
         int x = posTag.getInt("x");
         int z = posTag.getInt("z");
         int minY = posTag.getInt("min_y");
         set.add(new ChunkPosWithMinY(x, z, minY));
      }

      this.set(set);
   }

   public void set(Set<ChunkPosWithMinY> value) {
      super.set(value);
      this.lookup.clear();

      for (ChunkPosWithMinY pos : value) {
         this.lookup.put(ChunkPos.asLong(pos.chunkX(), pos.chunkZ()), pos.minY());
      }
   }

   public Map<Long, Integer> lookup() {
      return Collections.unmodifiableMap(this.lookup);
   }
}
