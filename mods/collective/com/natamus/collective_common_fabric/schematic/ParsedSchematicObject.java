package com.natamus.collective_common_fabric.schematic;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.class_1297;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2586;
import net.minecraft.class_2680;

public class ParsedSchematicObject {
   public Schematic schematic;
   public List<Pair<class_2338, class_2680>> blocks;
   public List<Pair<class_2338, class_1297>> entities;
   public List<class_2338> blockEntityPositions;
   public String parseMessageString;
   public boolean parsedCorrectly;
   public int offsetX;
   public int offsetY;
   public int offsetZ;

   public ParsedSchematicObject(
      Schematic _schematic,
      List<Pair<class_2338, class_2680>> _blocks,
      List<Pair<class_2338, class_1297>> _entities,
      List<class_2338> _blockEntityPositions,
      String _parseMessageString,
      boolean _parsedCorrectly
   ) {
      this.schematic = _schematic;
      this.blocks = _blocks;
      this.entities = _entities;
      this.blockEntityPositions = _blockEntityPositions;
      this.parseMessageString = _parseMessageString;
      this.parsedCorrectly = _parsedCorrectly;
      this.offsetX = _schematic.getOffsetX();
      this.offsetY = _schematic.getOffsetY();
      this.offsetZ = _schematic.getOffsetZ();
   }

   public void placeBlockEntitiesInWorld(class_1937 level) {
      List<class_2487> compoundTags = this.schematic.getBlockEntities();
      int i = 0;

      for (Pair<class_2338, class_2586> pair : this.getBlockEntities(level)) {
         class_2338 pos = (class_2338)pair.getFirst();
         class_2487 compoundTag = compoundTags.get(i);
         compoundTag.method_10551("Pos");
         compoundTag.method_10582("id", compoundTag.method_10558("Id"));
         class_2586 blockEntity = class_2586.method_11005(pos, level.method_8320(pos), compoundTag, level.method_30349());
         level.method_8438(blockEntity);
         i++;
      }
   }

   public List<Pair<class_2338, class_2586>> getBlockEntities(class_1937 level) {
      List<Pair<class_2338, class_2586>> blockEntities = new ArrayList<>();

      for (class_2338 pos : this.blockEntityPositions) {
         blockEntities.add(new Pair(pos, level.method_8321(pos)));
      }

      return blockEntities;
   }
}
