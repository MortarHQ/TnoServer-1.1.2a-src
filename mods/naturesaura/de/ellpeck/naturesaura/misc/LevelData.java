package de.ellpeck.naturesaura.misc;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ListMultimap;
import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntityPickupStopper;
import de.ellpeck.naturesaura.blocks.tiles.BlockEntitySpawnLamp;
import de.ellpeck.naturesaura.blocks.tiles.ItemStackHandlerNA;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.items.ModItems;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMaps;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.LongTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.SavedData;
import net.minecraft.world.level.saveddata.SavedData.Factory;
import net.minecraft.world.phys.Vec3;

public class LevelData extends SavedData implements ILevelData {
   public static final Factory<LevelData> FACTORY = new Factory(LevelData::new, LevelData::new);
   public static LevelData client;
   public final ListMultimap<ResourceLocation, Tuple<Vec3, Integer>> effectPowders = ArrayListMultimap.create();
   public final Long2ObjectMap<AuraChunk> auraChunksWithSpots = Long2ObjectMaps.synchronize(new Long2ObjectOpenHashMap());
   public final List<BlockPos> recentlyConvertedMossStones = new ArrayList<>();
   public final Set<BlockEntitySpawnLamp> spawnLamps = new HashSet<>();
   public final Set<BlockEntityPickupStopper> pickupStoppers = new HashSet<>();
   private final Map<String, ItemStackHandlerNA> enderStorages = new HashMap<>();
   public Level level;

   public LevelData() {
   }

   public LevelData(CompoundTag compound, Provider registries) {
      for (Tag base : compound.getList("storages", 10)) {
         CompoundTag storageComp = (CompoundTag)base;
         ItemStackHandlerNA storage = this.getEnderStorage(storageComp.getString("name"));
         storage.deserializeNBT(registries, storageComp);
      }

      for (Tag base : compound.getList("converted_moss", 4)) {
         this.recentlyConvertedMossStones.add(BlockPos.of(((LongTag)base).getAsLong()));
      }
   }

   public CompoundTag save(CompoundTag compound, Provider registries) {
      ListTag storages = new ListTag();

      for (Entry<String, ItemStackHandlerNA> entry : this.enderStorages.entrySet()) {
         ItemStackHandlerNA handler = entry.getValue();
         if (!Helper.isEmpty(handler)) {
            CompoundTag storageComp = handler.serializeNBT(registries);
            storageComp.putString("name", entry.getKey());
            storages.add(storageComp);
         }
      }

      compound.put("storages", storages);
      ListTag moss = new ListTag();

      for (BlockPos pos : this.recentlyConvertedMossStones) {
         moss.add(LongTag.valueOf(pos.asLong()));
      }

      compound.put("converted_moss", moss);
      return compound;
   }

   public boolean isDirty() {
      return true;
   }

   public ItemStackHandlerNA getEnderStorage(String name) {
      return this.enderStorages.computeIfAbsent(name, n -> new ItemStackHandlerNA(27));
   }

   @Override
   public boolean isEnderStorageLocked(String name) {
      ItemStackHandlerNA handler = this.getEnderStorage(name);

      for (int i = 0; i < handler.getSlots(); i++) {
         ItemStack stack = handler.getStackInSlot(i);
         if (!stack.isEmpty() && stack.getItem() == ModItems.TOKEN_TERROR) {
            return true;
         }
      }

      return false;
   }

   public void addMossStone(BlockPos pos) {
      this.recentlyConvertedMossStones.add(pos);
      if (this.recentlyConvertedMossStones.size() > 512) {
         this.recentlyConvertedMossStones.removeFirst();
      }
   }
}
