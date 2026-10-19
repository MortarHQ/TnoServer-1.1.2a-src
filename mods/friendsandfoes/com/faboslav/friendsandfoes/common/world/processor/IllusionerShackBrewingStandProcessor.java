package com.faboslav.friendsandfoes.common.world.processor;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureProcessorTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedNbt;
import com.mojang.serialization.MapCodec;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;

public final class IllusionerShackBrewingStandProcessor extends StructureProcessor {
   public static final MapCodec<IllusionerShackBrewingStandProcessor> CODEC = MapCodec.unit(IllusionerShackBrewingStandProcessor::new);

   private IllusionerShackBrewingStandProcessor() {
   }

   public StructureBlockInfo processBlock(
      LevelReader world,
      BlockPos pos,
      BlockPos pivot,
      StructureBlockInfo originalBlockInfo,
      StructureBlockInfo currentBlockInfo,
      StructurePlaceSettings structurePlacementData
   ) {
      if (currentBlockInfo.state().getBlock() != Blocks.BREWING_STAND) {
         return currentBlockInfo;
      }

      RandomSource random = structurePlacementData.getRandom(currentBlockInfo.pos());
      CompoundTag nbt = currentBlockInfo.nbt();
      ListTag itemsListNbt = VersionedNbt.getList(nbt, "Items");
      int randomNumber = random.nextInt(2);
      switch (randomNumber) {
         case 0:
            this.addBrewingRecipe(itemsListNbt, "minecraft:golden_carrot", "minecraft:night_vision", random);
            break;
         case 1:
            this.addBrewingRecipe(itemsListNbt, "minecraft:fermented_spider_eye", "minecraft:invisibility", random);
      }

      return new StructureBlockInfo(currentBlockInfo.pos(), currentBlockInfo.state(), nbt);
   }

   private void addBrewingRecipe(ListTag itemsListTag, String inputItemId, String outputPotionId, RandomSource randomSource) {
      itemsListTag.add((Tag)Util.make(new CompoundTag(), itemTag -> this.putInputItem(itemTag, inputItemId, (byte)(randomSource.nextInt(1) + 2))));
      itemsListTag.add((Tag)Util.make(new CompoundTag(), itemTag -> {
         this.putPotionInSlot(itemTag, (byte)1, outputPotionId);
         if (randomSource.nextFloat() < 0.5F) {
            this.putPotionInSlot(itemTag, (byte)0, outputPotionId);
         }

         if (randomSource.nextFloat() < 0.5F) {
            this.putPotionInSlot(itemTag, (byte)2, outputPotionId);
         }
      }));
   }

   private void putInputItem(CompoundTag itemTag, String itemId, byte count) {
      itemTag.putByte("Slot", (byte)3);
      itemTag.putString("id", itemId);
      itemTag.putByte("Count", count);
   }

   private void putPotionInSlot(CompoundTag itemTag, byte slot, String potionId) {
      itemTag.putByte("Slot", slot);
      itemTag.putString("id", "minecraft:potion");
      itemTag.putByte("Count", (byte)1);
      itemTag.put("tag", (Tag)Util.make(new CompoundTag(), potionTag -> potionTag.putString("Potion", potionId)));
   }

   protected StructureProcessorType<?> getType() {
      return (StructureProcessorType<?>)FriendsAndFoesStructureProcessorTypes.ILLUSIONER_SHACK_BREWING_STAND_PROCESSOR.get();
   }
}
