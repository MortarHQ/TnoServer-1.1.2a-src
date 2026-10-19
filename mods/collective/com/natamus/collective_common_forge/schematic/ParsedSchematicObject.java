package com.natamus.collective_common_forge.schematic;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class ParsedSchematicObject {
   public Schematic schematic;
   public List<Pair<BlockPos, BlockState>> blocks;
   public List<Pair<BlockPos, Entity>> entities;
   public List<BlockPos> blockEntityPositions;
   public String parseMessageString;
   public boolean parsedCorrectly;
   public int offsetX;
   public int offsetY;
   public int offsetZ;

   public ParsedSchematicObject(
      Schematic _schematic,
      List<Pair<BlockPos, BlockState>> _blocks,
      List<Pair<BlockPos, Entity>> _entities,
      List<BlockPos> _blockEntityPositions,
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

   public void placeBlockEntitiesInWorld(Level level) {
      List<CompoundTag> compoundTags = this.schematic.getBlockEntities();
      int i = 0;

      for (Pair<BlockPos, BlockEntity> pair : this.getBlockEntities(level)) {
         BlockPos pos = (BlockPos)pair.getFirst();
         CompoundTag compoundTag = compoundTags.get(i);
         compoundTag.remove("Pos");
         compoundTag.putString("id", compoundTag.getString("Id"));
         BlockEntity blockEntity = BlockEntity.loadStatic(pos, level.getBlockState(pos), compoundTag, level.registryAccess());
         level.setBlockEntity(blockEntity);
         i++;
      }
   }

   public List<Pair<BlockPos, BlockEntity>> getBlockEntities(Level level) {
      List<Pair<BlockPos, BlockEntity>> blockEntities = new ArrayList<>();

      for (BlockPos pos : this.blockEntityPositions) {
         blockEntities.add(new Pair(pos, level.getBlockEntity(pos)));
      }

      return blockEntities;
   }
}
