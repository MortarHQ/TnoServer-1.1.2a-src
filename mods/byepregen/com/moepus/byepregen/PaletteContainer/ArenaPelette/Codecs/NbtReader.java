package com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs;

import com.moepus.byepregen.PaletteContainer.ArenaPelette.ArenaBlockStatePalettedContainer;
import java.util.HashMap;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public final class NbtReader {
   private static final String PALETTE = "palette";
   private static final String DATA = "data";
   private static final String NAME = "Name";
   private static final String PROPERTIES = "Properties";

   private NbtReader() {
   }

   public static ArenaBlockStatePalettedContainer read(CompoundTag blockStatesTag) {
      int[] paletteRawIds = readPaletteRawIds(blockStatesTag.getList("palette", 10));
      if (paletteRawIds == null) {
         return null;
      }

      long[] packedStorage = blockStatesTag.contains("data", 12) ? blockStatesTag.getLongArray("data") : null;
      ArenaBlockStatePalettedContainer container = new ArenaBlockStatePalettedContainer();
      return container.importVanillaPackedRawIds(paletteRawIds, packedStorage) ? container : null;
   }

   public static int[] readPaletteRawIds(ListTag paletteTag) {
      return paletteTag.isEmpty() ? null : readPaletteRawIds(paletteTag, new NbtReader.PaletteReadCache());
   }

   private static int[] readPaletteRawIds(ListTag paletteTag, NbtReader.PaletteReadCache cache) {
      int[] rawIds = new int[paletteTag.size()];

      for (int i = 0; i < rawIds.length; i++) {
         BlockState state = readState(paletteTag.getCompound(i), cache);
         if (state == null) {
            return null;
         }

         int rawId = Block.BLOCK_STATE_REGISTRY.getId(state);
         if (rawId < 0) {
            return null;
         }

         rawIds[i] = rawId;
      }

      return rawIds;
   }

   private static BlockState readState(CompoundTag stateTag, NbtReader.PaletteReadCache cache) {
      Block block = cache.readBlock(stateTag.getString("Name"));
      if (block == null) {
         return null;
      }

      BlockState state = block.defaultBlockState();
      if (!stateTag.contains("Properties", 10)) {
         return state;
      }

      CompoundTag properties = stateTag.getCompound("Properties");

      for (String key : properties.getAllKeys()) {
         state = setProperty(state, key, properties.getString(key), cache);
         if (state == null) {
            return null;
         }
      }

      return state;
   }

   private static BlockState setProperty(BlockState state, String key, String valueName, NbtReader.PaletteReadCache cache) {
      Property<?> property = state.getBlock().getStateDefinition().getProperty(key);
      return property == null ? null : setProperty(state, property, valueName, cache);
   }

   private static BlockState setProperty(BlockState state, Property<?> property, String valueName, NbtReader.PaletteReadCache cache) {
      Comparable<?> value = cache.readPropertyValue(property, valueName);
      return value == null ? null : setPropertyValue(state, property, value);
   }

   private static BlockState setPropertyValue(BlockState state, Property property, Comparable value) {
      return (BlockState)state.setValue(property, value);
   }

   private static final class PaletteReadCache {
      private HashMap<String, Block> blocksByName;
      private HashMap<Property<?>, HashMap<String, Comparable<?>>> propertyValues;

      Block readBlock(String name) {
         HashMap<String, Block> blocks = this.blocksByName;
         if (blocks != null) {
            Block cached = blocks.get(name);
            if (cached != null) {
               return cached;
            }
         }

         ResourceLocation id = ResourceLocation.tryParse(name);
         if (id != null && BuiltInRegistries.BLOCK.containsKey(id)) {
            Block value = (Block)BuiltInRegistries.BLOCK.get(id);
            if (blocks == null) {
               blocks = new HashMap<>();
               this.blocksByName = blocks;
            }

            blocks.put(name, value);
            return value;
         } else {
            return null;
         }
      }

      Comparable<?> readPropertyValue(Property<?> property, String valueName) {
         HashMap<Property<?>, HashMap<String, Comparable<?>>> propertyValues = this.propertyValues;
         if (propertyValues == null) {
            propertyValues = new HashMap<>();
            this.propertyValues = propertyValues;
         }

         HashMap<String, Comparable<?>> values = propertyValues.get(property);
         if (values == null) {
            values = new HashMap<>();
            propertyValues.put(property, values);
         }

         Comparable<?> cached = values.get(valueName);
         if (cached != null) {
            return cached;
         }

         Optional<? extends Comparable<?>> value = property.getValue(valueName);
         if (value.isEmpty()) {
            return null;
         }

         Comparable<?> resolved = (Comparable<?>)value.get();
         values.put(valueName, resolved);
         return resolved;
      }
   }
}
