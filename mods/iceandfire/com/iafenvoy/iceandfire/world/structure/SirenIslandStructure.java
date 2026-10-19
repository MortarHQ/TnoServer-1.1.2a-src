package com.iafenvoy.iceandfire.world.structure;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.SirenEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafStructurePieces;
import com.iafenvoy.iceandfire.registry.IafStructureTypes;
import com.iafenvoy.iceandfire.world.DangerousGeneration;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationContext;
import net.minecraft.world.level.levelgen.structure.Structure.GenerationStub;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;

public class SirenIslandStructure extends Structure implements DangerousGeneration {
   public static final MapCodec<SirenIslandStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(settingsCodec(instance)).apply(instance, SirenIslandStructure::new)
   );

   protected SirenIslandStructure(StructureSettings config) {
      super(config);
   }

   protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
      if (context.random().nextDouble() >= (Double)IafCommonConfig.INSTANCE.worldGen.generateSirenIslandChance.getValue()) {
         return Optional.empty();
      }

      Rotation blockRotation = Rotation.getRandom(context.random());
      BlockPos blockPos = this.getLowestYIn5by5BoxOffset7Blocks(context, blockRotation);
      return !this.isFarEnoughFromSpawn(blockPos)
         ? Optional.empty()
         : Optional.of(
            new GenerationStub(
               blockPos,
               collector -> collector.addPiece(
                  new SirenIslandStructure.SirenIslandPiece(
                     0, new BoundingBox(blockPos.getX(), blockPos.getY(), blockPos.getZ(), blockPos.getX(), blockPos.getY(), blockPos.getZ())
                  )
               )
            )
         );
   }

   public StructureType<?> type() {
      return (StructureType<?>)IafStructureTypes.SIREN_ISLAND.get();
   }

   public static class SirenIslandPiece extends StructurePiece {
      protected SirenIslandPiece(int length, BoundingBox boundingBox) {
         super((StructurePieceType)IafStructurePieces.SIREN_ISLAND.get(), length, boundingBox);
      }

      public SirenIslandPiece(StructurePieceSerializationContext context, CompoundTag nbt) {
         super((StructurePieceType)IafStructurePieces.SIREN_ISLAND.get(), nbt);
      }

      protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag nbt) {
      }

      public void postProcess(
         WorldGenLevel world,
         StructureManager structureAccessor,
         ChunkGenerator chunkGenerator,
         RandomSource random,
         BoundingBox chunkBox,
         ChunkPos chunkPos,
         BlockPos pivot
      ) {
         if (chunkBox.isInside(pivot)) {
            int up = random.nextInt(4) + 1;
            BlockPos center = pivot.above(up);
            int layer = 0;
            int sirens = 1 + random.nextInt(3);
            int radius = this.getRadius(up, up);

            for (super.boundingBox = new BoundingBox(
                  center.getX() - radius, center.getY() - up, center.getZ() - radius, center.getX() + radius, center.getY() + 1, center.getZ() + radius
               );
               !world.getBlockState(center).canOcclude() && center.getY() >= world.getMinBuildHeight();
               center = center.below()
            ) {
               layer++;

               for (float i = 0.0F; i < this.getRadius(layer, up); i += 0.5F) {
                  for (float j = 0.0F; j < (Math.PI * 2) * i + random.nextInt(2); j += 0.5F) {
                     BlockPos stonePos = BlockPos.containing(
                        Math.floor(center.getX() + Mth.sin(j) * i + random.nextInt(2)),
                        center.getY(),
                        Math.floor(center.getZ() + Mth.cos(j) * i + random.nextInt(2))
                     );
                     world.setBlock(stonePos, this.getStone(random), 3);
                     BlockPos upPos = stonePos.above();
                     if (world.isEmptyBlock(upPos)
                        && world.isEmptyBlock(upPos.east())
                        && world.isEmptyBlock(upPos.north())
                        && world.isEmptyBlock(upPos.north().east())
                        && random.nextInt(3) == 0
                        && sirens > 0) {
                        this.spawnSiren(world, random, upPos.north().east());
                        sirens--;
                     }
                  }
               }
            }

            layer++;

            for (float i = 0.0F; i < this.getRadius(layer, up); i += 0.5F) {
               for (float j = 0.0F; j < (Math.PI * 2) * i + random.nextInt(2); j += 0.5F) {
                  for (BlockPos stonePos = BlockPos.containing(
                        Math.floor(center.getX() + Mth.sin(j) * i + random.nextInt(2)),
                        center.getY(),
                        Math.floor(center.getZ() + Mth.cos(j) * i + random.nextInt(2))
                     );
                     !world.getBlockState(stonePos).canOcclude() && stonePos.getY() >= 0;
                     stonePos = stonePos.below()
                  ) {
                     world.setBlock(stonePos, this.getStone(random), 3);
                  }
               }
            }
         }
      }

      private int getRadius(int layer, int up) {
         int MAX_ISLAND_RADIUS = 10;
         return layer > up ? (int)(layer * 0.25) + up : Math.min(layer, MAX_ISLAND_RADIUS);
      }

      private BlockState getStone(RandomSource random) {
         int chance = random.nextInt(100);
         if (chance > 90) {
            return Blocks.MOSSY_COBBLESTONE.defaultBlockState();
         } else if (chance > 70) {
            return Blocks.GRAVEL.defaultBlockState();
         } else {
            return chance > 45 ? Blocks.COBBLESTONE.defaultBlockState() : Blocks.STONE.defaultBlockState();
         }
      }

      private void spawnSiren(ServerLevelAccessor worldIn, RandomSource rand, BlockPos position) {
         SirenEntity siren = new SirenEntity((EntityType<SirenEntity>)IafEntities.SIREN.get(), worldIn.getLevel());
         siren.setSinging(true);
         siren.setHairColor(rand.nextInt(2));
         siren.setSingingPose(rand.nextInt(2));
         siren.absMoveTo(position.getX() + 0.5, position.getY() + 1, position.getZ() + 0.5, rand.nextFloat() * 360.0F, 0.0F);
         worldIn.addFreshEntity(siren);
      }
   }
}
