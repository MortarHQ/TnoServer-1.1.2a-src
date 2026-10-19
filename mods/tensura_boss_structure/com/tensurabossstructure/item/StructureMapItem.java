package com.tensurabossstructure.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;

public class StructureMapItem extends Item {
   private final String structureId;
   private final String mapTranslationKey;

   public StructureMapItem(Properties properties, String structureId, String mapTranslationKey) {
      super(properties);
      this.structureId = structureId;
      this.mapTranslationKey = mapTranslationKey;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemInHand = player.getItemInHand(hand);
      if (level.isClientSide()) {
         return InteractionResultHolder.pass(itemInHand);
      }

      ServerLevel serverLevel = (ServerLevel)level;
      BlockPos playerPos = player.blockPosition();
      TagKey<Structure> structureTag = TagKey.create(Registries.STRUCTURE, ResourceLocation.parse(this.structureId));
      BlockPos structurePos = serverLevel.findNearestMapStructure(structureTag, playerPos, 500, false);
      if (structurePos == null) {
         player.displayClientMessage(Component.translatable("item.tensura_boss_structure.map.not_found"), true);
         return InteractionResultHolder.fail(itemInHand);
      }

      ItemStack mapStack = MapItem.create(serverLevel, structurePos.getX(), structurePos.getZ(), (byte)2, true, true);
      MapItem.renderBiomePreviewMap(serverLevel, mapStack);
      MapItemSavedData.addTargetDecoration(mapStack, structurePos, "+", MapDecorationTypes.RED_X);
      mapStack.set(DataComponents.CUSTOM_NAME, Component.translatable(this.mapTranslationKey));
      if (!player.getAbilities().instabuild) {
         itemInHand.shrink(1);
      }

      if (itemInHand.isEmpty()) {
         return InteractionResultHolder.sidedSuccess(mapStack, false);
      }

      if (!player.getInventory().add(mapStack)) {
         player.drop(mapStack, false);
      }

      return InteractionResultHolder.sidedSuccess(itemInHand, false);
   }
}
