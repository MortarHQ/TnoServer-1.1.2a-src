package it.hurts.octostudios.rarcompat.items.feet;

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
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class RunningShoesItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("runner")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.5, 0.8)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 13, 27)
                           .star(1, 4, 18)
                           .star(2, 4, 9)
                           .star(3, 8, 6)
                           .star(4, 12, 6)
                           .star(5, 16, 10)
                           .star(6, 16, 15)
                           .star(7, 20, 21)
                           .star(8, 20, 27)
                           .link(0, 1)
                           .link(1, 2)
                           .link(2, 3)
                           .link(3, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(7, 8)
                           .link(8, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-2803672).borderBottom(-5107187).build())
               .beams(BeamsData.builder().startColor(-65536).endColor(16203326).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("runner").initialValue(1).gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.VILLAGE, LootEntries.PLAINS}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.isAbilityUnlocked(stack, "runner")) {
         double speedIncrement = this.getStatValue(stack, "runner", "speed") / 1000.0;
         AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speedAttribute != null && !player.isSwimming() && player.onGround()) {
            double currentSpeed = speedAttribute.getBaseValue();
            if (player.isSprinting()) {
               double newSpeed = Math.min(currentSpeed + speedIncrement, 0.30000000000000004);
               if (player.tickCount % 20 == 0) {
                  this.spreadRelicExperience(player, stack, 1);
               }

               speedAttribute.setBaseValue(newSpeed);
            } else {
               double newSpeed = Math.max(currentSpeed - speedIncrement * 4.0, 0.1);
               speedAttribute.setBaseValue(newSpeed);
            }
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (newStack.getItem() != stack.getItem() && slotContext.entity() instanceof Player player) {
         AttributeInstance speedAttribute = player.getAttribute(Attributes.MOVEMENT_SPEED);
         if (speedAttribute != null) {
            speedAttribute.setBaseValue(0.1);
         }
      }
   }
}
