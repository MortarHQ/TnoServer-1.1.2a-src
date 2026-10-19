package com.iafenvoy.iceandfire.world.processor;

import com.iafenvoy.iceandfire.registry.IafProcessors;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public class VillageHouseProcessor extends StructureProcessor {
   public static final ResourceLocation LOOT = ResourceLocation.fromNamespaceAndPath("iceandfire", "chest/village_scribe");
   public static final VillageHouseProcessor INSTANCE = new VillageHouseProcessor();
   public static final MapCodec<VillageHouseProcessor> CODEC = MapCodec.unit(() -> INSTANCE);

   public StructureBlockInfo processBlock(
      LevelReader world, BlockPos pos, BlockPos pivot, StructureBlockInfo originalBlockInfo, StructureBlockInfo currentBlockInfo, StructurePlaceSettings data
   ) {
      RandomSource random = data.getRandom(currentBlockInfo.pos());
      if (currentBlockInfo.state().getBlock() == Blocks.CHEST) {
         CompoundTag tag = new CompoundTag();
         tag.putString("LootTable", LOOT.toString());
         tag.putLong("LootTableSeed", random.nextLong());
         return new StructureBlockInfo(currentBlockInfo.pos(), currentBlockInfo.state(), tag);
      } else {
         return currentBlockInfo;
      }
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)IafProcessors.VILLAGE_HOUSE_PROCESSOR.get();
   }
}
