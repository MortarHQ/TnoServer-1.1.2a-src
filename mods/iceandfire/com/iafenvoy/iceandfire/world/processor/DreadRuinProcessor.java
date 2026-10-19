package com.iafenvoy.iceandfire.world.processor;

import com.iafenvoy.iceandfire.item.block.DreadBaseBlock;
import com.iafenvoy.iceandfire.item.block.util.DreadBlock;
import com.iafenvoy.iceandfire.registry.IafBlocks;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class DreadRuinProcessor extends StructureProcessor {
   public static final DreadRuinProcessor INSTANCE = new DreadRuinProcessor();
   public static final MapCodec<DreadRuinProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   public static BlockState getRandomCrackedBlock(RandomSource random) {
      float rand = random.nextFloat();
      if (rand < 0.5) {
         return (BlockState)((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS.get()).defaultBlockState().setValue(DreadBlock.UNBREAKABLE, true);
      } else {
         return rand < 0.9
            ? (BlockState)((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS_CRACKED.get()).defaultBlockState().setValue(DreadBlock.UNBREAKABLE, true)
            : (BlockState)((DreadBaseBlock)IafBlocks.DREAD_STONE_BRICKS_MOSSY.get()).defaultBlockState().setValue(DreadBlock.UNBREAKABLE, true);
      }
   }

   public StructureBlockInfo processBlock(
      LevelReader world, BlockPos pos, BlockPos pivot, StructureBlockInfo originalBlockInfo, StructureBlockInfo currentBlockInfo, StructurePlaceSettings data
   ) {
      RandomSource random = data.getRandom(currentBlockInfo.pos());
      if (currentBlockInfo.state().getBlock() == IafBlocks.DREAD_STONE_BRICKS.get()) {
         BlockState state = getRandomCrackedBlock(random);
         return new StructureBlockInfo(currentBlockInfo.pos(), state, null);
      } else if (currentBlockInfo.state().getBlock() == IafBlocks.DREAD_SPAWNER.get()) {
         CompoundTag tag = new CompoundTag();
         CompoundTag spawnData = new CompoundTag();
         ResourceLocation spawnerMobId = BuiltInRegistries.ENTITY_TYPE.getKey(this.getRandomMobForMobSpawner(random));
         CompoundTag entity = new CompoundTag();
         entity.putString("id", spawnerMobId.toString());
         spawnData.put("entity", entity);
         tag.remove("SpawnPotentials");
         tag.put("SpawnData", spawnData.copy());
         return new StructureBlockInfo(currentBlockInfo.pos(), ((Block)IafBlocks.DREAD_SPAWNER.get()).defaultBlockState(), tag);
      } else {
         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)IafProcessors.DREAD_MAUSOLEUM_PROCESSOR.get();
   }

   private EntityType<?> getRandomMobForMobSpawner(RandomSource random) {
      float rand = random.nextFloat();
      if (rand < 0.3) {
         return (EntityType<?>)IafEntities.DREAD_THRALL.get();
      } else if (rand < 0.5) {
         return (EntityType<?>)IafEntities.DREAD_GHOUL.get();
      } else if (rand < 0.7) {
         return (EntityType<?>)IafEntities.DREAD_BEAST.get();
      } else {
         return rand < 0.85 ? (EntityType)IafEntities.DREAD_SCUTTLER.get() : (EntityType)IafEntities.DREAD_KNIGHT.get();
      }
   }
}
