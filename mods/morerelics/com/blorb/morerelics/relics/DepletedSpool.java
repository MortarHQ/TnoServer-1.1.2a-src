package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.LocationHolder;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import top.theillusivec4.curios.api.SlotContext;

public class DepletedSpool extends MoreRelicBase {
   private static final int stringRequired = 300;
   private static final int potionsRequired = 1;

   public DepletedSpool(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(AbilitiesData.builder().ability(AbilityData.builder("restoration").build()).build())
         .leveling(LevelingData.builder().maxLevel(1).initialCost(100).step(20).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT, LootEntries.MOUNTAIN}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         if (!player.level().isClientSide) {
            ItemStack mainHandItem = player.getMainHandItem();
            LocationHolder itemsEaten = DataComponentHandler.getTripleStorage(stack);
            int stringsEaten = (int)itemsEaten.getX();
            int potionsEaten = (int)itemsEaten.getY();
            if (mainHandItem.is(Items.STRING)) {
               if (stringsEaten < 300) {
                  mainHandItem.consume(1, player);
                  player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.MASTER, 0.5F, 1.0F);
                  itemsEaten.setX(stringsEaten + 1);
               }
            } else if (this.isRegenerationPotion(mainHandItem) && potionsEaten < 1) {
               mainHandItem.consume(1, player);
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BREWING_STAND_BREW, SoundSource.MASTER, 0.5F, 0.9F);
               itemsEaten.setY(potionsEaten + 1);
            }

            this.checkAndTransform(stack, player);
         }
      }
   }

   private void checkAndTransform(ItemStack stack, Player player) {
      LocationHolder itemsEaten = DataComponentHandler.getTripleStorage(stack);
      if (itemsEaten.getX() >= 300.0 && itemsEaten.getY() >= 1.0) {
         Component message = Component.translatable("tooltip.morerelics.depleted_spool_evolve").setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16766720)));
         player.sendSystemMessage(message);
         EntityUtils.addItem(player, MoreRelics.WEAVERS_SPOOL.toStack());
         stack.setCount(0);
      }
   }

   private boolean isRegenerationPotion(ItemStack potionStack) {
      PotionContents contents = (PotionContents)potionStack.get(DataComponents.POTION_CONTENTS);
      return contents != null && (contents.is(Potions.REGENERATION) || contents.is(Potions.STRONG_REGENERATION) || contents.is(Potions.LONG_REGENERATION));
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
      list.add(
         Component.translatable("tooltip.morerelics.depleted_spool_string")
            .append((int)holder.x + "/300")
            .withStyle(ChatFormatting.LIGHT_PURPLE)
            .withStyle(ChatFormatting.ITALIC)
      );
      list.add(
         Component.translatable("tooltip.morerelics.depleted_spool_regen_potion")
            .append((int)holder.y + "/1")
            .withStyle(ChatFormatting.LIGHT_PURPLE)
            .withStyle(ChatFormatting.ITALIC)
      );
      return list;
   }
}
