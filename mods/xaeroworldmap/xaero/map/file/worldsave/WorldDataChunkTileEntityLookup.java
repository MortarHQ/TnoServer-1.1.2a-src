package xaero.map.file.worldsave;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;

public class WorldDataChunkTileEntityLookup {
   private ListTag tileEntitiesNbt;
   private Int2ObjectMap<Int2ObjectMap<Int2ObjectMap<CompoundTag>>> tileEntities;

   public WorldDataChunkTileEntityLookup(ListTag tileEntitiesNbt) {
      this.tileEntitiesNbt = tileEntitiesNbt;
   }

   private void loadIfNeeded() {
      if (this.tileEntities == null) {
         this.tileEntities = new Int2ObjectOpenHashMap();
         this.tileEntitiesNbt.forEach(tag -> {
            if (tag instanceof CompoundTag compoundNbt) {
               if (!compoundNbt.contains("x", 99)) {
                  return;
               }

               int x = compoundNbt.getInt("x") & 15;
               if (!compoundNbt.contains("y", 99)) {
                  return;
               }

               int y = compoundNbt.getInt("y");
               if (!compoundNbt.contains("z", 99)) {
                  return;
               }

               int z = compoundNbt.getInt("z") & 15;
               Int2ObjectMap<Int2ObjectMap<CompoundTag>> byX = (Int2ObjectMap<Int2ObjectMap<CompoundTag>>)this.tileEntities.get(x);
               if (byX == null) {
                  this.tileEntities.put(x, byX = new Int2ObjectOpenHashMap());
               }

               Int2ObjectMap<CompoundTag> byY = (Int2ObjectMap<CompoundTag>)byX.get(y);
               if (byY == null) {
                  byX.put(y, byY = new Int2ObjectOpenHashMap());
               }

               byY.put(z, compoundNbt);
            }
         });
         this.tileEntitiesNbt = null;
      }
   }

   public CompoundTag getTileEntityNbt(int x, int y, int z) {
      this.loadIfNeeded();
      Int2ObjectMap<Int2ObjectMap<CompoundTag>> byX = (Int2ObjectMap<Int2ObjectMap<CompoundTag>>)this.tileEntities.get(x);
      if (byX == null) {
         return null;
      }

      Int2ObjectMap<CompoundTag> byY = (Int2ObjectMap<CompoundTag>)byX.get(y);
      return byY == null ? null : (CompoundTag)byY.get(z);
   }
}
