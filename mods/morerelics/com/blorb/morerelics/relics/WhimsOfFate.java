package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.LocationHolder;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;

@EventBusSubscriber
public class WhimsOfFate extends MoreRelicBase {
   public WhimsOfFate(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("just_one_more")
                     .stat(
                        StatData.builder("damage_reduction_per")
                           .initialValue(0.5, 1.0)
                           .upgradeModifier(UpgradeOperation.ADD, 0.1F)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(8)
                     .requiredPoints(1)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(8)
               .initialCost(100)
               .step(25)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("just_one_more").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.DESERT, LootEntries.BASTION}).build())
         .build();
   }

   @SubscribeEvent
   public static void onDamageTaken(Pre event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         int highestRoll = 0;
         double highestReductionPer = 0.0;
         Random rand = new Random();

         for (ItemStack stack : EntityUtils.findEquippedCurios(player, (Item)MoreRelics.WHIMS_OF_FATE.get())) {
            WhimsOfFate item = (WhimsOfFate)stack.getItem();
            if (item.isAbilityUnlocked(stack, "just_one_more")) {
               item.spreadRelicExperience(player, stack, 2);
               int diceRoll = rand.nextInt(20) + 1;
               if (diceRoll > highestRoll) {
                  highestRoll = diceRoll;
               }

               LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
               holder.x = diceRoll;
               if (diceRoll == 20) {
                  holder.y++;
               } else if (diceRoll == 1) {
                  holder.z++;
               }

               double reductionPer = item.getStatValue(stack, "just_one_more", "damage_reduction_per");
               if (reductionPer > highestReductionPer) {
                  highestReductionPer = reductionPer;
               }
            }
         }

         if (highestRoll != 0) {
            if (highestRoll == 1) {
               Component message = Component.translatable("action.morerelics.roll1")
                  .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(16711680)))
                  .withStyle(ChatFormatting.BOLD);
               player.displayClientMessage(message, true);
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.WITHER_HURT, SoundSource.MASTER, 0.3F, 0.6F);
               event.setNewDamage(event.getNewDamage() * 2.0F);
            } else if (highestRoll == 20) {
               Component message = Component.translatable("action.morerelics.roll20")
                  .setStyle(Style.EMPTY.withColor(TextColor.fromRgb(-16711936)))
                  .withStyle(ChatFormatting.BOLD);
               player.displayClientMessage(message, true);
               player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_LEVELUP, SoundSource.MASTER, 0.3F, 1.2F);
               event.setNewDamage(0.0F);
            } else {
               event.setNewDamage((float)(event.getNewDamage() * (100.0 - highestRoll * highestReductionPer) / 100.0));
            }
         }
      }
   }

   public List<Component> getAttributesTooltip(List<Component> tooltips, TooltipContext context, ItemStack stack) {
      ArrayList<Component> list = new ArrayList<>();
      LocationHolder holder = DataComponentHandler.getTripleStorage(stack);
      if (holder.x != 0.0) {
         list.add(
            Component.translatable("tooltip.morerelics.last_roll", new Object[]{(int)holder.x}).withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.ITALIC)
         );
      }

      if (holder.y != 0.0) {
         list.add(
            Component.translatable("tooltip.morerelics.20count", new Object[]{(int)holder.y}).withStyle(ChatFormatting.GREEN).withStyle(ChatFormatting.ITALIC)
         );
      }

      if (holder.z != 0.0) {
         list.add(
            Component.translatable("tooltip.morerelics.1count", new Object[]{(int)holder.z}).withStyle(ChatFormatting.RED).withStyle(ChatFormatting.ITALIC)
         );
      }

      return list;
   }
}
