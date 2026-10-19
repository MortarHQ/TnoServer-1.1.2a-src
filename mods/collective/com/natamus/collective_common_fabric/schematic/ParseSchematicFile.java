package com.natamus.collective_common_fabric.schematic;

import com.mojang.datafixers.util.Pair;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1937;
import net.minecraft.class_2246;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2680;

public class ParseSchematicFile {
   public static ParsedSchematicObject getParsedSchematicObject(
      InputStream schematicInputStream, class_1937 level, class_2338 centerPos, int extraYOffset, boolean skipAir
   ) {
      return getParsedSchematicObject(schematicInputStream, level, centerPos, extraYOffset, skipAir, true);
   }

   @Nullable
   public static ParsedSchematicObject getParsedSchematicObject(
      InputStream schematicInputStream, class_1937 level, class_2338 centerPos, int extraYOffset, boolean skipAir, boolean automaticCenter
   ) {
      Schematic schematic = new Schematic(schematicInputStream, level);
      if (!schematic.wasParsedCorrectly()) {
         return null;
      }

      int maxBuildHeight = level.method_31600();
      int length = schematic.getLength();
      int width = schematic.getWidth();
      int height = schematic.getHeight();
      int xoffset = schematic.getOffsetX();
      int yoffset = centerPos.method_10264() + extraYOffset;
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

      List<Pair<class_2338, class_2680>> blocks = new ArrayList<>();

      for (SchematicBlockObject blockObject : schematic.getBlocks()) {
         if (blockObject != null) {
            class_2680 blockState = blockObject.getState();
            if (!skipAir || !blockState.method_26204().equals(class_2246.field_10124)) {
               blocks.add(
                  new Pair(
                     blockObject.getPosition().method_10069(centerPos.method_10263() + xoffset, yoffset, centerPos.method_10260() + zoffset).method_10062(),
                     blockState
                  )
               );
            }
         }
      }

      List<Pair<class_2338, class_1297>> entities = new ArrayList<>();

      for (Pair<class_2338, class_2487> rawEntityPair : schematic.getEntityRelativePosPairs()) {
         Optional<class_1297> optionalNewEntity = class_1299.method_5892((class_2487)rawEntityPair.getSecond(), level);
         if (optionalNewEntity.isPresent()) {
            class_2338 actualEntityPosition = ((class_2338)rawEntityPair.getFirst())
               .method_10069(centerPos.method_10263() + xoffset, yoffset, centerPos.method_10260() + zoffset)
               .method_10062();
            class_1297 newEntity = optionalNewEntity.get();
            newEntity.method_5814(actualEntityPosition.method_10263() + 0.5, actualEntityPosition.method_10264(), actualEntityPosition.method_10260() + 0.5);
            entities.add(new Pair(actualEntityPosition, newEntity));
         }
      }

      List<class_2338> blockEntityPositions = new ArrayList<>();

      for (class_2487 blockEntityCompoundTag : schematic.getBlockEntities()) {
         blockEntityPositions.add(
            schematic.getBlockPosFromCompoundTag(blockEntityCompoundTag)
               .method_10069(centerPos.method_10263() + xoffset, yoffset, centerPos.method_10260() + zoffset)
         );
      }

      return new ParsedSchematicObject(schematic, blocks, entities, blockEntityPositions, "Parsed successfully.", true);
   }
}
