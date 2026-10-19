package it.hurts.octostudios.rarcompat.items.necklace;

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
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;

public class LuckyScarfItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("luck")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.25)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 14, 6)
                           .star(1, 6, 12)
                           .star(2, 16, 15)
                           .star(3, 9, 20)
                           .star(4, 3, 21)
                           .star(5, 14, 23)
                           .star(6, 6, 24)
                           .link(0, 1)
                           .link(0, 2)
                           .link(1, 3)
                           .link(2, 3)
                           .link(3, 4)
                           .link(3, 5)
                           .link(6, 3)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-8074714).borderBottom(-14656498).build())
               .beams(BeamsData.builder().startColor(-8074714).endColor(1525512).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("luck").initialValue(1).gem(GemShape.SQUARE, GemColor.GREEN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.MINESHAFT, LootEntries.CAVE}).build())
         .build();
   }

   public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.canPlayerUseAbility(player, stack, "luck")) {
         RandomSource random = player.getRandom();
         int amount = MathUtils.multicast(random, this.getStatValue(stack, "luck", "chance"));
         if (amount > 0) {
            this.spreadRelicExperience(player, stack, random.nextInt(amount) + 1);
         }

         return amount;
      } else {
         return super.getFortuneLevel(slotContext, lootContext, stack);
      }
   }
}
