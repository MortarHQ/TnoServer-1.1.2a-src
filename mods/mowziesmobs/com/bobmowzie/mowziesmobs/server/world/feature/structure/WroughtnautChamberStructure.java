package com.bobmowzie.mowziesmobs.server.world.feature.structure;

import com.bobmowzie.mowziesmobs.server.config.ConfigHandler;
import com.mojang.serialization.MapCodec;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.Direction.Plane;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.GenerationStep.Decoration;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.apache.commons.lang3.tuple.Pair;

public class WroughtnautChamberStructure extends MowzieStructure {
   public static final MapCodec<WroughtnautChamberStructure> CODEC = simpleCodec(WroughtnautChamberStructure::new);

   public WroughtnautChamberStructure(StructureSettings settings) {
      super(settings, ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig, StructureTypeHandler.FERROUS_WROUGHTNAUT_BIOMES, false, false, true);
   }

   @Override
   public void generatePieces(StructurePiecesBuilder builder, GenerationContext context) {
      int x = context.chunkPos().getMiddleBlockX();
      int z = context.chunkPos().getMiddleBlockZ();
      int y = context.chunkGenerator().getFirstOccupiedHeight(x, z, Types.OCEAN_FLOOR_WG, context.heightAccessor(), context.randomState());
      Pair<BlockPos, Rotation> tryResult = tryWroughtChamber(context.chunkGenerator(), context.heightAccessor(), x, y, z, context.randomState());
      if (tryResult != null) {
         BlockPos pos = (BlockPos)tryResult.getLeft();
         Rotation rotation = (Rotation)tryResult.getRight();
         BlockPos rotationOffset = new BlockPos(0, 0, -9).rotate(rotation);
         pos = pos.offset(rotationOffset);
         WroughtnautChamberPieces.start(context.structureTemplateManager(), pos, rotation, builder);
      }
   }

   @Nullable
   public static Pair<BlockPos, Rotation> tryWroughtChamber(
      ChunkGenerator generator, LevelHeightAccessor heightAccessor, int x, int surfaceY, int z, RandomState state
   ) {
      int xzCheckDistance = 8;
      int heightMax = ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig.heightMax.get()).intValue();
      int heightMin = ((Double)ConfigHandler.COMMON.MOBS.FERROUS_WROUGHTNAUT.generationConfig.heightMin.get()).intValue();
      if (heightMax == -65 || heightMax > surfaceY) {
         heightMax = surfaceY;
      }

      if (heightMin == -65) {
         heightMin = -64;
      }

      for (int dx = -xzCheckDistance; dx < xzCheckDistance; dx += 2) {
         for (int dz = -xzCheckDistance; dz < xzCheckDistance; dz += 2) {
            BlockPos airPos = null;
            NoiseColumn column = generator.getBaseColumn(x + dx, z + dz, heightAccessor, state);

            for (int y = heightMax; y > heightMin; y--) {
               if (!column.getBlock(y).isSolid()) {
                  airPos = new BlockPos(x + dx, y, z + dz);
                  break;
               }
            }

            if (airPos != null) {
               BlockPos groundPos = null;

               for (int y = airPos.getY(); y > heightMin; y--) {
                  if (column.getBlock(y).isSolid()) {
                     groundPos = airPos.atY(y);
                     break;
                  }
               }

               if (groundPos != null) {
                  for (Direction dir : Plane.HORIZONTAL) {
                     MutableBlockPos checkWallPos = groundPos.above().mutable();

                     for (int d = 1; d <= xzCheckDistance; d++) {
                        checkWallPos.move(dir);
                        NoiseColumn wallCheckColumn = generator.getBaseColumn(checkWallPos.getX(), checkWallPos.getZ(), heightAccessor, state);
                        int wallBaseY = checkWallPos.getY() - 1;
                        int wallHeightCount = 1;

                        while (true) {
                           BlockState wallBlock = wallCheckColumn.getBlock(checkWallPos.getY());
                           if (!wallBlock.isSolid()) {
                              break;
                           }

                           if (wallHeightCount == 4) {
                              Rotation rotation = switch (dir) {
                                 case NORTH -> Rotation.COUNTERCLOCKWISE_90;
                                 case EAST -> Rotation.NONE;
                                 case WEST -> Rotation.CLOCKWISE_180;
                                 default -> Rotation.CLOCKWISE_90;
                              };
                              return Pair.of(new BlockPos(checkWallPos.getX(), wallBaseY, checkWallPos.getZ()), rotation);
                           }

                           checkWallPos.move(Direction.UP);
                           wallHeightCount++;
                        }
                     }
                  }
               }
            }
         }
      }

      return null;
   }

   @Override
   public Decoration step() {
      return Decoration.UNDERGROUND_STRUCTURES;
   }

   public StructureType<?> type() {
      return (StructureType<?>)StructureTypeHandler.WROUGHTNAUT_CHAMBER.get();
   }
}
