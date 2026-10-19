package it.hurts.octostudios.rarcompat.items.hands;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.items.relics.base.data.research.ResearchData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.TooltipData;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Post;

public class VampiricGloveItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("vampire")
                     .stat(
                        StatData.builder("amount")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 9)
                           .star(1, 11, 13)
                           .star(2, 11, 20)
                           .star(3, 11, 24)
                           .star(4, 5, 13)
                           .star(5, 17, 13)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(1, 4)
                           .link(1, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-6521753).borderBottom(-11260640).build())
               .beams(BeamsData.builder().startColor(-6521753).endColor(5516576).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("vampire").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.NETHER_LIKE, LootEntries.THE_NETHER}).build())
         .build();
   }

   @EventBusSubscriber
   public static class VampiricGloveEvent {
      @SubscribeEvent
      public static void onAttack(Post event) {
         if (event.getSource().getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.VAMPIRIC_GLOVE.value());
            if (stack.getItem() instanceof VampiricGloveItem relic && relic.canPlayerUseAbility(player, stack, "vampire")) {
               double damageToHeal = event.getNewDamage() * relic.getStatValue(stack, "vampire", "amount");
               if (damageToHeal * 5.0 / player.getMaxHealth() >= player.getRandom().nextDouble()) {
                  relic.spreadRelicExperience(player, stack, 1);
               }

               player.heal((float)Math.min(event.getEntity().getMaxHealth(), damageToHeal));
            }
         }
      }
   }
}
