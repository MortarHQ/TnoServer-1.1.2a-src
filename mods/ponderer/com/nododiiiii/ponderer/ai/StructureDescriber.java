package com.nododiiiii.ponderer.ai;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;

public class StructureDescriber {
   public static StructureDescriber.StructureInfo describe(Path nbtFile) throws IOException {
      try (InputStream is = Files.newInputStream(nbtFile)) {
         return describe(is);
      }
   }

   public static StructureDescriber.StructureInfo describe(InputStream inputStream) throws IOException {
      CompoundTag nbt = NbtIo.read(new DataInputStream(new BufferedInputStream(new GZIPInputStream(inputStream))), new NbtAccounter(536870912L, 512));
      return describeFromNbt(nbt);
   }

   private static StructureDescriber.StructureInfo describeFromNbt(CompoundTag nbt) {
      ListTag sizeTag = nbt.getList("size", 3);
      int sizeX = sizeTag.getInt(0);
      int sizeY = sizeTag.getInt(1);
      int sizeZ = sizeTag.getInt(2);
      ListTag palette = nbt.getList("palette", 10);
      List<String> paletteNames = new ArrayList<>();

      for (int i = 0; i < palette.size(); i++) {
         CompoundTag entry = palette.getCompound(i);
         String name = entry.getString("Name");
         if (entry.contains("Properties", 10)) {
            CompoundTag props = entry.getCompound("Properties");
            if (!props.isEmpty()) {
               StringBuilder sb = new StringBuilder(name).append("[");
               boolean first = true;

               for (String key : props.getAllKeys()) {
                  if (!first) {
                     sb.append(",");
                  }

                  sb.append(key).append("=").append(props.getString(key));
                  first = false;
               }

               sb.append("]");
               name = sb.toString();
            }
         }

         paletteNames.add(name);
      }

      ListTag blocks = nbt.getList("blocks", 10);
      Map<Integer, List<String>> layerBlocks = new LinkedHashMap<>();
      List<String> blockTypes = new ArrayList<>();

      for (int i = 0; i < blocks.size(); i++) {
         CompoundTag block = blocks.getCompound(i);
         ListTag pos = block.getList("pos", 3);
         int x = pos.getInt(0);
         int y = pos.getInt(1);
         int z = pos.getInt(2);
         int stateIdx = block.getInt("state");
         String blockName = stateIdx < paletteNames.size() ? paletteNames.get(stateIdx) : "unknown";
         if (!blockName.equals("minecraft:air")) {
            layerBlocks.computeIfAbsent(y, k -> new ArrayList<>()).add("[" + x + "," + y + "," + z + "] " + blockName);
            String baseName = blockName.contains("[") ? blockName.substring(0, blockName.indexOf(91)) : blockName;
            if (!blockTypes.contains(baseName)) {
               blockTypes.add(baseName);
            }
         }
      }

      StringBuilder sb = new StringBuilder();
      sb.append("Structure size: ").append(sizeX).append("x").append(sizeY).append("x").append(sizeZ).append("\n");
      sb.append("Block types: ").append(String.join(", ", blockTypes)).append("\n");

      for (int y = 0; y < sizeY; y++) {
         List<String> layer = layerBlocks.get(y);
         if (layer != null && !layer.isEmpty()) {
            sb.append("--- Y=").append(y).append(" ---\n");

            for (String entry : layer) {
               sb.append("  ").append(entry).append("\n");
            }
         } else {
            sb.append("--- Y=").append(y).append(" (empty) ---\n");
         }
      }

      return new StructureDescriber.StructureInfo(sizeX, sizeY, sizeZ, sb.toString(), blockTypes);
   }

   public record StructureInfo(int sizeX, int sizeY, int sizeZ, String textDescription, List<String> blockTypes) {
   }
}
