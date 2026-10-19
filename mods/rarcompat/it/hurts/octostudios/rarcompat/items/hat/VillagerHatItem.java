package it.hurts.octostudios.rarcompat.items.hat;

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
import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import top.theillusivec4.curios.api.SlotContext;

public class VillagerHatItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("passive")
                     .maxLevel(0)
                     .research(
                        ResearchData.builder()
                           .star(0, 2, 24)
                           .star(1, 12, 27)
                           .star(2, 12, 22)
                           .star(3, 11, 13)
                           .star(4, 6, 11)
                           .star(5, 11, 8)
                           .star(6, 15, 11)
                           .star(7, 7, 5)
                           .star(8, 15, 6)
                           .link(0, 2)
                           .link(1, 2)
                           .link(0, 2)
                           .link(2, 3)
                           .link(3, 5)
                           .link(3, 4)
                           .link(3, 6)
                           .link(4, 5)
                           .link(6, 5)
                           .link(5, 7)
                           .link(5, 8)
                           .link(8, 6)
                           .link(8, 7)
                           .link(7, 8)
                           .link(4, 7)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("discount")
                     .stat(
                        StatData.builder("multiplier")
                           .initialValue(10.0, 20.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> (int)MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 3, 25)
                           .star(1, 12, 23)
                           .star(2, 12, 19)
                           .star(3, 9, 15)
                           .star(4, 9, 7)
                           .star(5, 15, 7)
                           .star(6, 15, 15)
                           .link(0, 1)
                           .link(0, 2)
                           .link(0, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 3)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-2571448).borderBottom(-7709899).build())
               .beams(BeamsData.builder().startColor(-1714024).endColor(6441223).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("discount").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.VILLAGE}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.isAbilityUnlocked(stack, "passive")) {
         player.getCommandSenderWorld()
            .getEntitiesOfClass(IronGolem.class, player.getBoundingBox().inflate(8.0))
            .stream()
            .filter(mob -> mob.getTarget() == player)
            .forEach(mob -> mob.setTarget(null));
      }
   }

   @EventBusSubscriber
   public static class VillagerHatEvent {
      @SubscribeEvent
      public static void onLivingChangeTargetEvent(LivingChangeTargetEvent event) {
         if (event.getEntity() instanceof IronGolem && event.getNewAboutToBeSetTarget() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.VILLAGER_HAT.value());
            if (stack.getItem() instanceof VillagerHatItem relic && relic.isAbilityUnlocked(stack, "passive")) {
               event.setCanceled(true);
            }
         }
      }
   }
}
