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
public class MassGauntlet extends MoreRelicBase {
   public MassGauntlet(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("heavy_hitter")
                     .stat(
                        StatData.builder("perch")
                           .initialValue(6.0, 10.0)
                           .upgradeModifier(UpgradeOperation.ADD, 1.6)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(5)
               .initialCost(80)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("heavy_hitter").initialValue(1).build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.WILDCARD}).build())
         .build();
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof Player player && !player.level().isClientSide) {
         float extra_damage = (float)EntityUtils.findEquippedCurios(player, (Item)MoreRelics.MASS_GAUNTLET.get()).stream().mapToDouble(stack -> {
            MassGauntlet item = (MassGauntlet)stack.getItem();
            if (!item.isAbilityUnlocked(stack, "heavy_hitter")) {
               return 0.0;
            }

            double perch = item.getStatValue(stack, "heavy_hitter", "perch");
            item.spreadRelicExperience(player, stack, 2);
            return (float)Math.round(perch * player.getMaxHealth()) / 100.0F;
         }).sum();
         event.setAmount(event.getAmount() + extra_damage);
      }
   }
}
