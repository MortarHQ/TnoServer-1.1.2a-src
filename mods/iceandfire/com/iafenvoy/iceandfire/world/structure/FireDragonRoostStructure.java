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
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
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

public class FireDragonRoostStructure extends DragonRoostStructure {
   public static final MapCodec<FireDragonRoostStructure> CODEC = RecordCodecBuilder.mapCodec(
      instance -> instance.group(settingsCodec(instance)).apply(instance, FireDragonRoostStructure::new)
   );

   protected FireDragonRoostStructure(StructureSettings config) {
      super(config);
   }

   @Override
   protected DragonRoostStructure.DragonRoostPiece createPiece(BoundingBox boundingBox, boolean isMale) {
      return new FireDragonRoostStructure.FireDragonRoostPiece(0, boundingBox, (Block)IafBlocks.GOLD_PILE.get(), isMale);
   }

   @Override
   protected double getGenerateChance() {
      return (Double)IafCommonConfig.INSTANCE.worldGen.generateFireDragonRoostChance.getValue();
   }

   public StructureType<?> type() {
      return (StructureType<?>)IafStructureTypes.FIRE_DRAGON_ROOST.get();
   }

   public static class FireDragonRoostPiece extends DragonRoostStructure.DragonRoostPiece {
      private static final ResourceLocation DRAGON_CHEST = ResourceLocation.fromNamespaceAndPath("iceandfire", "chest/fire_dragon_roost");

      protected FireDragonRoostPiece(int length, BoundingBox boundingBox, Block treasureBlock, boolean isMale) {
         super((StructurePieceType)IafStructurePieces.FIRE_DRAGON_ROOST.get(), length, boundingBox, treasureBlock, isMale);
      }

      public FireDragonRoostPiece(StructurePieceSerializationContext context, CompoundTag nbt) {
         super((StructurePieceType)IafStructurePieces.FIRE_DRAGON_ROOST.get(), nbt);
      }

      @Override
      protected EntityType<? extends DragonBaseEntity> getDragonType() {
         return (EntityType<? extends DragonBaseEntity>)IafEntities.FIRE_DRAGON.get();
      }

      @Override
      protected ResourceKey<LootTable> getRoostLootTable() {
         return ResourceKey.create(Registries.LOOT_TABLE, DRAGON_CHEST);
      }

      @Override
      protected BlockState transform(BlockState state) {
         Block block = null;
         if (state.is(Blocks.GRASS_BLOCK)) {
            block = (Block)IafBlocks.CHARRED_GRASS.get();
         } else if (state.is(Blocks.DIRT_PATH)) {
            block = (Block)IafBlocks.CHARRED_DIRT_PATH.get();
         } else if (state.is(CommonBlockTags.GRAVELS)) {
            block = (Block)IafBlocks.CHARRED_GRAVEL.get();
         } else if (state.is(BlockTags.DIRT)) {
            block = (Block)IafBlocks.CHARRED_DIRT.get();
         } else if (state.is(CommonBlockTags.STONES)) {
            block = (Block)IafBlocks.CHARRED_STONE.get();
         } else if (state.is(CommonBlockTags.COBBLESTONES)) {
            block = (Block)IafBlocks.CHARRED_COBBLESTONE.get();
         } else if (state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS)) {
            block = (Block)IafBlocks.ASH.get();
         } else if (state.is(IafBlockTags.GRASSES) || state.is(BlockTags.LEAVES) || state.is(BlockTags.FLOWERS) || state.is(BlockTags.CROPS)) {
            block = Blocks.AIR;
         }

         return block != null ? block.defaultBlockState() : state;
      }

      @Override
      protected void handleCustomGeneration(WorldGenLevel world, BlockPos origin, RandomSource random, BlockPos position, double distance) {
         if (random.nextInt(1000) == 0) {
            this.generateRoostPile(world, random, this.getSurfacePosition(world, position), (Block)IafBlocks.ASH.get());
         }
      }
   }
}
