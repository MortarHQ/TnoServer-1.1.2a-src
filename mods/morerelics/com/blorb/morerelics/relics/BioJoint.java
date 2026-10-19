package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
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
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class BioJoint extends CyberwareBase {
   public BioJoint(Properties properties) {
      super(properties);
   }

   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("suspension")
                     .stat(
                        StatData.builder("min_reduction")
                           .initialValue(4.0, 6.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("max_reduction")
                           .initialValue(10.0, 15.0)
                           .upgradeModifier(UpgradeOperation.ADD, 4.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("cyberpsychosis")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(3.0, 5.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(4.0, 7.0)
                           .upgradeModifier(UpgradeOperation.ADD, 2.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(8)
               .initialCost(100)
               .step(25)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("suspension").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.THE_NETHER, LootEntries.MINESHAFT}).build())
         .build();
   }

   @SubscribeEvent
   public static void onDamageTaken(LivingIncomingDamageEvent event) {
      if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
         double reduction = EntityUtils.findEquippedCurios(player, (Item)MoreRelics.BIOJOINT.get()).stream().mapToDouble(stack -> {
            BioJoint item = (BioJoint)stack.getItem();
            if (!item.isAbilityUnlocked(stack, "suspension")) {
               return 0.0;
            }

            item.spreadRelicExperience(player, stack, 3);
            double max_reduction = item.getStatValue(stack, "suspension", "max_reduction");
            if (player.hasEffect(MoreRelics.CYBERPSYCHOSIS.getDelegate())) {
               return max_reduction;
            }

            double min_reduction = item.getStatValue(stack, "suspension", "min_reduction");
            double amount = event.getAmount();
            return Math.min(amount / player.getMaxHealth(), 1.0) * (max_reduction - min_reduction) + min_reduction;
         }).sum();
         if (reduction > 60.0) {
            reduction = 60.0;
         } else if (reduction < 0.0) {
            reduction = 0.0;
         }

         event.setAmount((float)(event.getAmount() * (100.0 - reduction) / 100.0));
      }
   }
}
