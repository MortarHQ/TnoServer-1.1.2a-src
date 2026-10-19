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
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Pig;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.monster.hoglin.HoglinBase;
import net.minecraft.world.entity.monster.piglin.AbstractPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;

@EventBusSubscriber
public class CrownOfTheLegend extends MoreRelicBase {
   public CrownOfTheLegend(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("pig_king")
                     .stat(
                        StatData.builder("perch")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.ADD, 0.1)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(3)
                     .requiredLevel(0)
                     .build()
               )
               .ability(
                  AbilityData.builder("never_die")
                     .stat(
                        StatData.builder("potency")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.ADD, 0.02)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(3)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(8)
               .initialCost(100)
               .step(40)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("pig_king").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.BASTION, LootEntries.THE_NETHER}).build())
         .build();
   }

   @SubscribeEvent
   public static void onPlayerAttackPre(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         float damageMultiplier = (float)EntityUtils.findEquippedCurios(player, (Item)MoreRelics.CROWN_OF_THE_LEGEND.get()).stream().mapToDouble(stack -> {
            CrownOfTheLegend item = (CrownOfTheLegend)stack.getItem();
            if (!item.isAbilityUnlocked(stack, "pig_king")) {
               return 0.0;
            } else {
               return !isPig(event.getEntity()) ? 0.0 : item.getStatValue(stack, "pig_king", "perch");
            }
         }).sum();
         event.setAmount(event.getAmount() * (1.0F + damageMultiplier));
      }
   }

   @SubscribeEvent
   public static void onPlayerAttackPost(Pre event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         float healthToHeal = (float)EntityUtils.findEquippedCurios(player, (Item)MoreRelics.CROWN_OF_THE_LEGEND.get()).stream().mapToDouble(stack -> {
            CrownOfTheLegend item = (CrownOfTheLegend)stack.getItem();
            if (item.isAbilityUnlocked(stack, "pig_king") && isPig(event.getEntity())) {
               item.spreadRelicExperience(player, stack, 5);
            }

            if (!item.isAbilityUnlocked(stack, "never_die")) {
               return 0.0;
            }

            double potency = item.getStatValue(stack, "never_die", "potency");
            return (1.0F - player.getHealth() / player.getMaxHealth()) * event.getNewDamage() * potency;
         }).sum();
         player.heal(healthToHeal);
      }
   }

   private static boolean isPig(LivingEntity entity) {
      return entity instanceof AbstractPiglin || entity instanceof ZombifiedPiglin || entity instanceof HoglinBase || entity instanceof Pig;
   }
}
