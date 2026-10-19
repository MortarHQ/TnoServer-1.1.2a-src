package com.iafenvoy.iceandfire.world.structure;

import com.iafenvoy.iceandfire.config.IafCommonConfig;
import com.iafenvoy.iceandfire.entity.DragonBaseEntity;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafStructurePieces;
import com.iafenvoy.iceandfire.registry.IafStructureTypes;
import com.iafenvoy.iceandfire.registry.tag.CommonBlockTags;
import com.iafenvoy.iceandfire.registry.tag.IafBlockTags;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.Plane;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.Structure.StructureSettings;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.storage.loot.LootTable;

public class LightningDragonRoostStructure extends DragonRoostStructure {
   public static final MapCodec<LightningDragonRoostStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(settingsCodec(instance)).apply(instance, LightningDragonRoostStructure::new)
   );

   protected LightningDragonRoostStructure(StructureSettings config) {
      super(config);
   }

   @Override
   protected DragonRoostStructure.DragonRoostPiece createPiece(BoundingBox boundingBox, boolean isMale) {
      return new LightningDragonRoostStructure.LightningDragonRoostPiece(0, boundingBox, (Block)IafBlocks.COPPER_PILE.get(), isMale);
   }

   @Override
   protected double getGenerateChance() {
      return (Double)IafCommonConfig.INSTANCE.worldGen.generateLightningDragonRoostChance.getValue();
   }

   public StructureType<?> type() {
      return (StructureType<?>)IafStructureTypes.LIGHTNING_DRAGON_ROOST.get();
   }

   public static class LightningDragonRoostPiece extends DragonRoostStructure.DragonRoostPiece {
      private static final ResourceLocation DRAGON_CHEST = ResourceLocation.fromNamespaceAndPath("iceandfire", "chest/lightning_dragon_roost");

      protected LightningDragonRoostPiece(int length, BoundingBox boundingBox, Block treasureBlock, boolean isMale) {
         super((StructurePieceType)IafStructurePieces.LIGHTNING_DRAGON_ROOST.get(), length, boundingBox, treasureBlock, isMale);
      }

      public LightningDragonRoostPiece(StructurePieceSerializationContext context, CompoundTag nbt) {
         super((StructurePieceType)IafStructurePieces.LIGHTNING_DRAGON_ROOST.get(), nbt);
      }

      @Override
      protected EntityType<? extends DragonBaseEntity> getDragonType() {
         return (EntityType<? extends DragonBaseEntity>)IafEntities.LIGHTNING_DRAGON.get();
      }

      @Override
      protected ResourceKey<LootTable> getRoostLootTable() {
         return ResourceKey.create(Registries.LOOT_TABLE, DRAGON_CHEST);
      }

      @Override
      protected BlockState transform(BlockState state) {
         Block block = null;
         if (state.is(Blocks.GRASS_BLOCK)) {
            block = (Block)IafBlocks.CRACKLED_GRASS.get();
         } else if (state.is(Blocks.DIRT_PATH)) {
            block = (Block)IafBlocks.CRACKLED_DIRT_PATH.get();
         } else if (state.is(CommonBlockTags.GRAVELS)) {
            block = (Block)IafBlocks.CRACKLED_GRAVEL.get();
         } else if (state.is(BlockTags.DIRT)) {
            block = (Block)IafBlocks.CRACKLED_DIRT.get();
         } else if (state.is(CommonBlockTags.STONES)) {
            block = (Block)IafBlocks.CRACKLED_STONE.get();
         } else if (state.is(CommonBlockTags.COBBLESTONES)) {
            block = (Block)IafBlocks.CRACKLED_COBBLESTONE.get();
         } else if (state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS)) {
            block = (Block)IafBlocks.ASH.get();
         } else if (state.is(IafBlockTags.GRASSES) || state.is(BlockTags.LEAVES) || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS)) {
            block = Blocks.AIR;
         }

         return block != null ? block.defaultBlockState() : state;
      }

      @Override
      protected void handleCustomGeneration(WorldGenLevel world, BlockPos origin, RandomSource random, BlockPos position, double distance) {
         if (distance > 0.05 && random.nextInt(800) == 0) {
            this.generateSpire(world, random, this.getSurfacePosition(world, position));
         }

         if (distance > 0.05 && random.nextInt(1000) == 0) {
            this.generateSpike(world, random, this.getSurfacePosition(world, position), Plane.HORIZONTAL.getRandomDirection(random));
         }
      }

      private void generateSpike(LevelAccessor worldIn, RandomSource rand, BlockPos position, Direction direction) {
         int radius = 5;

         for (int i = 0; i < 5; i++) {
            int j = Math.max(0, radius - (int)(i * 1.75F));
            int l = radius - i;
            int k = Math.max(0, radius - (int)(i * 1.5F));
            float f = (j + l) * 0.333F + 0.5F;
            BlockPos up = position.above().relative(direction, i);
            int xOrZero = direction.getAxis() == Axis.Z ? j : 0;
            int zOrZero = direction.getAxis() == Axis.Z ? 0 : k;

            for (BlockPos blockpos : (Set)BlockPos.betweenClosedStream(up.offset(-xOrZero, -l, -zOrZero), up.offset(xOrZero, l, zOrZero))
               .map(BlockPos::immutable)
               .collect(Collectors.toSet())) {
               if (blockpos.distSqr(position) <= f * f) {
                  int height = Math.max(blockpos.getY() - up.getY(), 0);
                  if (i == 0) {
                     if (rand.nextFloat() < height * 0.3F) {
                        worldIn.setBlock(blockpos, ((Block)IafBlocks.CRACKLED_STONE.get()).defaultBlockState(), 2);
                     }
                  } else {
                     worldIn.setBlock(blockpos, ((Block)IafBlocks.CRACKLED_STONE.get()).defaultBlockState(), 2);
                  }
               }
            }
         }
      }

      private void generateSpire(LevelAccessor worldIn, RandomSource rand, BlockPos position) {
         int height = 5 + rand.nextInt(5);
         Direction bumpDirection = Direction.NORTH;

         for (int i = 0; i < height; i++) {
            worldIn.setBlock(position.above(i), ((Block)IafBlocks.CRACKLED_STONE.get()).defaultBlockState(), 2);
            if (rand.nextBoolean()) {
               bumpDirection = bumpDirection.getClockWise();
            }

            int offset = 1;
            if (i < 4) {
               worldIn.setBlock(position.above(i).north(), ((Block)IafBlocks.CRACKLED_GRAVEL.get()).defaultBlockState(), 2);
               worldIn.setBlock(position.above(i).south(), ((Block)IafBlocks.CRACKLED_GRAVEL.get()).defaultBlockState(), 2);
               worldIn.setBlock(position.above(i).east(), ((Block)IafBlocks.CRACKLED_GRAVEL.get()).defaultBlockState(), 2);
               worldIn.setBlock(position.above(i).west(), ((Block)IafBlocks.CRACKLED_GRAVEL.get()).defaultBlockState(), 2);
               offset = 2;
            }

            if (i < height - 2) {
               worldIn.setBlock(position.above(i).relative(bumpDirection, offset), ((Block)IafBlocks.CRACKLED_COBBLESTONE.get()).defaultBlockState(), 2);
            }
         }
      }
   }
}
