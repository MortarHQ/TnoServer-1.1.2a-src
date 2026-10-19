package com.tensura_guild.item;

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
import org.jetbrains.annotations.NotNull;

public class GuildStructureMapItem extends Item {
   private final ResourceLocation structureTagId;
   private final String mapTranslationKey;

   public GuildStructureMapItem(Properties properties, ResourceLocation structureTagId, String mapTranslationKey) {
      super(properties);
      this.structureTagId = structureTagId;
      this.mapTranslationKey = mapTranslationKey;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(@NotNull Level level, @NotNull Player player, @NotNull InteractionHand hand) {
      ItemStack itemInHand = player.getItemInHand(hand);
      if (level.isClientSide()) {
         return InteractionResultHolder.pass(itemInHand);
      }

      ServerLevel serverLevel = (ServerLevel)level;
      TagKey<Structure> structureTag = TagKey.create(Registries.STRUCTURE, this.structureTagId);
      BlockPos structurePos = serverLevel.findNearestMapStructure(structureTag, player.blockPosition(), 500, false);
      if (structurePos == null) {
         player.displayClientMessage(Component.translatable("item.tensura_guild.map.not_found"), true);
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
