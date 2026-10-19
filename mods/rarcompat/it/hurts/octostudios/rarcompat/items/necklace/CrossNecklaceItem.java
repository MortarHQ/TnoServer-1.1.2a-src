package it.hurts.octostudios.rarcompat.items.necklace;

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
import top.theillusivec4.curios.api.SlotContext;

public class CrossNecklaceItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("invulnerability")
                     .stat(
                        StatData.builder("modifier")
                           .initialValue(0.3, 0.5)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 4, 6)
                           .star(1, 10, 6)
                           .star(2, 4, 20)
                           .star(3, 10, 19)
                           .star(4, 19, 15)
                           .star(5, 19, 20)
                           .star(6, 11, 26)
                           .star(7, 19, 6)
                           .link(0, 2)
                           .link(2, 6)
                           .link(6, 5)
                           .link(5, 4)
                           .link(7, 4)
                           .link(4, 1)
                           .link(1, 3)
                           .link(1, 7)
                           .link(1, 0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-202418).borderBottom(-5217519).build())
               .beams(BeamsData.builder().startColor(-262981).endColor(8201730).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("invulnerability").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.DESERT}).build())
         .build();
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (newStack.getItem() != stack.getItem() && slotContext.entity() instanceof Player player) {
         player.invulnerableTime = player.invulnerableTime - (int)this.getStatValue(stack, "invulnerability", "modifier");
      }
   }

   @EventBusSubscriber
   public static class CrossNecklaceEvent {
      @SubscribeEvent
      public static void onLivingDamaged(Post event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.CROSS_NECKLACE.value());
            if (stack.getItem() instanceof CrossNecklaceItem relic && relic.canPlayerUseAbility(player, stack, "invulnerability")) {
               relic.spreadRelicExperience(player, stack, 1);
               player.invulnerableTime = player.invulnerableTime + (int)(relic.getStatValue(stack, "invulnerability", "modifier") * 20.0);
            }
         }
      }
   }
}
