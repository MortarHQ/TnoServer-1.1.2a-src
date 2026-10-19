package it.hurts.octostudios.rarcompat.items.necklace;

import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingBreatheEvent;
import top.theillusivec4.curios.api.SlotContext;

public class CharmOfSinkingItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("dive")
                     .maxLevel(0)
                     .research(
                        ResearchData.builder()
                           .star(0, 11, 3)
                           .star(1, 11, 9)
                           .star(2, 8, 6)
                           .star(3, 14, 6)
                           .star(4, 11, 13)
                           .star(5, 11, 27)
                           .star(6, 4, 21)
                           .star(7, 4, 16)
                           .star(8, 18, 21)
                           .star(9, 18, 16)
                           .star(10, 5, 12)
                           .star(11, 17, 12)
                           .link(0, 2)
                           .link(0, 3)
                           .link(3, 1)
                           .link(2, 1)
                           .link(1, 4)
                           .link(4, 5)
                           .link(5, 6)
                           .link(6, 7)
                           .link(5, 8)
                           .link(8, 9)
                           .link(1, 4)
                           .link(4, 5)
                           .link(4, 10)
                           .link(4, 11)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("dipping")
                     .stat(
                        StatData.builder("air")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 12, 9)
                           .star(1, 9, 14)
                           .star(2, 16, 14)
                           .star(3, 9, 19)
                           .star(4, 4, 24)
                           .star(5, 12, 26)
                           .link(0, 1)
                           .link(1, 2)
                           .link(1, 3)
                           .link(1, 3)
                           .link(3, 4)
                           .link(3, 5)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-12816240).borderBottom(-14933715).build())
               .beams(BeamsData.builder().startColor(-12816240).endColor(1843501).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("dipping").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.AQUATIC}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && this.isAbilityUnlocked(stack, "dive")) {
         if (player.isUnderWater()) {
            EntityUtils.applyAttribute(player, stack, Attributes.GRAVITY, 2.0F, Operation.ADD_MULTIPLIED_TOTAL);
            if (!this.isAbilityUnlocked(stack, "dipping")) {
               return;
            }

            if (!player.onGround()) {
               this.setToggled(stack, true);
            }

            if (player.tickCount % 20 == 0 && player.onGround() && player.getMaxAirSupply() > player.getAirSupply()) {
               player.setAirSupply((int)(player.getAirSupply() + this.getStatValue(stack, "dipping", "air") * 15.0));
               if (this.getToggled(stack)) {
                  this.setToggled(stack, false);
                  this.spreadRelicExperience(player, stack, 1);
               }
            }
         } else {
            EntityUtils.removeAttribute(player, stack, Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (newStack.getItem() != stack.getItem() && slotContext.entity() instanceof Player player) {
         EntityUtils.removeAttribute(player, stack, Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   public void setToggled(ItemStack stack, boolean val) {
      stack.set(DataComponentRegistry.TOGGLED, val);
   }

   public boolean getToggled(ItemStack stack) {
      return (Boolean)stack.getOrDefault(DataComponentRegistry.TOGGLED, true);
   }

   @EventBusSubscriber
   public static class CharmOfSinkingEvent {
      @SubscribeEvent
      public static void onPlayerBreathe(LivingBreatheEvent event) {
         if (event.getEntity() instanceof Player player) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.CHARM_OF_SINKING.value());
            if (stack.getItem() instanceof CharmOfSinkingItem relic
               && player.isUnderWater()
               && relic.isAbilityUnlocked(stack, "dipping")
               && player.onGround()
               && player.getAirSupply() != player.getMaxAirSupply()) {
               event.setConsumeAirAmount(0);
            }
         }
      }
   }
}
