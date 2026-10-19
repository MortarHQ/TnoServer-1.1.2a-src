package com.natamus.collective_common_forge.schematic;

import com.mojang.datafixers.util.Pair;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class ParseSchematicFile {
   public static ParsedSchematicObject getParsedSchematicObject(
      InputStream schematicInputStream, Level level, BlockPos centerPos, int extraYOffset, boolean skipAir
   ) {
      return getParsedSchematicObject(schematicInputStream, level, centerPos, extraYOffset, skipAir, true);
   }

   @Nullable
   public static ParsedSchematicObject getParsedSchematicObject(
      InputStream schematicInputStream, Level level, BlockPos centerPos, int extraYOffset, boolean skipAir, boolean automaticCenter
   ) {
      Schematic schematic = new Schematic(schematicInputStream, level);
      if (!schematic.wasParsedCorrectly()) {
         return null;
      }

      int maxBuildHeight = level.getMaxBuildHeight();
      int length = schematic.getLength();
      int width = schematic.getWidth();
      int height = schematic.getHeight();
      int xoffset = schematic.getOffsetX();
      int yoffset = centerPos.getY() + extraYOffset;
      int zoffset = schematic.getOffsetZ();
      if (automaticCenter) {
         xoffset = -(width / 2);
         if (yoffset + height > maxBuildHeight) {
            yoffset = maxBuildHeight - height;
         }

         zoffset = -(length / 2);
      } else {
         yoffset += schematic.getOffsetY();
      }

      List<Pair<BlockPos, BlockState>> blocks = new ArrayList<>();

      for (SchematicBlockObject blockObject : schematic.getBlocks()) {
         if (blockObject != null) {
            BlockState blockState = blockObject.getState();
            if (!skipAir || !blockState.getBlock().equals(Blocks.AIR)) {
               blocks.add(new Pair(blockObject.getPosition().offset(centerPos.getX() + xoffset, yoffset, centerPos.getZ() + zoffset).immutable(), blockState));
            }
         }
      }

      List<Pair<BlockPos, Entity>> entities = new ArrayList<>();

      for (Pair<BlockPos, CompoundTag> rawEntityPair : schematic.getEntityRelativePosPairs()) {
         Optional<Entity> optionalNewEntity = EntityType.create((CompoundTag)rawEntityPair.getSecond(), level);
         if (optionalNewEntity.isPresent()) {
            BlockPos actualEntityPosition = ((BlockPos)rawEntityPair.getFirst())
               .offset(centerPos.getX() + xoffset, yoffset, centerPos.getZ() + zoffset)
               .immutable();
            Entity newEntity = optionalNewEntity.get();
            newEntity.setPos(actualEntityPosition.getX() + 0.5, actualEntityPosition.getY(), actualEntityPosition.getZ() + 0.5);
            entities.add(new Pair(actualEntityPosition, newEntity));
         }
      }

      List<BlockPos> blockEntityPositions = new ArrayList<>();

      for (CompoundTag blockEntityCompoundTag : schematic.getBlockEntities()) {
         blockEntityPositions.add(
            schematic.getBlockPosFromCompoundTag(blockEntityCompoundTag).offset(centerPos.getX() + xoffset, yoffset, centerPos.getZ() + zoffset)
         );
      }

      return new ParsedSchematicObject(schematic, blocks, entities, blockEntityPositions, "Parsed successfully.", true);
   }
}
