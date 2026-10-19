package it.hurts.octostudios.rarcompat.items.hat;

import artifacts.registry.ModAttributes;
import artifacts.registry.ModItems;
import it.hurts.octostudios.rarcompat.items.WearableRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier.Modifier;
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
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import top.theillusivec4.curios.api.SlotContext;

public class DrinkingHatItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("drinking")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.3, 0.4)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 14, 4)
                           .star(1, 19, 9)
                           .star(2, 6, 10)
                           .star(3, 13, 16)
                           .star(4, 8, 16)
                           .star(5, 4, 25)
                           .star(6, 12, 26)
                           .star(7, 11, 12)
                           .star(8, 18, 5)
                           .link(0, 2)
                           .link(1, 3)
                           .link(4, 2)
                           .link(4, 3)
                           .link(4, 5)
                           .link(4, 6)
                           .link(2, 7)
                           .link(3, 7)
                           .link(1, 8)
                           .link(0, 8)
                           .link(5, 6)
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("nutrition")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("hunger")
                           .initialValue(1.0, 3.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 14, 2)
                           .star(1, 13, 9)
                           .star(2, 19, 13)
                           .star(3, 10, 16)
                           .star(4, 19, 20)
                           .star(5, 13, 25)
                           .star(6, 4, 23)
                           .star(7, 4, 29)
                           .link(0, 1)
                           .link(1, 2)
                           .link(1, 3)
                           .link(2, 4)
                           .link(4, 5)
                           .link(6, 3)
                           .link(6, 5)
                           .link(6, 7)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(15)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("nutrition").initialValue(1).gem(GemShape.SQUARE, GemColor.CYAN).build())
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(
                  (player, stack) -> stack.getItem() == ModItems.PLASTIC_DRINKING_HAT.value()
                     ? TooltipData.builder().borderTop(-224452).borderBottom(-5424861).build()
                     : TooltipData.builder().borderTop(-12493392).borderBottom(-14868387).build()
               )
               .beams(
                  (player, stack) -> stack.getItem() == ModItems.PLASTIC_DRINKING_HAT.value()
                     ? BeamsData.builder().startColor(-224452).endColor(10635040).build()
                     : BeamsData.builder().startColor(-4380911).endColor(1908829).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.VILLAGE}).build())
         .build();
   }

   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      return !this.isAbilityUnlocked(stack, "drinking")
         ? super.getRelicAttributeModifiers(stack)
         : RelicAttributeModifier.builder()
            .attribute(new Modifier(ModAttributes.DRINKING_SPEED, (float)this.getStatValue(stack, "drinking", "speed"), Operation.ADD_MULTIPLIED_TOTAL))
            .build();
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && newStack.getItem() != stack.getItem()) {
         EntityUtils.removeAttribute(player, stack, ModAttributes.DRINKING_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }

   @EventBusSubscriber
   public static class DrinkingHatEvents {
      @SubscribeEvent
      public static void onUseItem(Finish event) {
         if (event.getEntity() instanceof Player player && !player.getCommandSenderWorld().isClientSide()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.PLASTIC_DRINKING_HAT.value());
            if (stack.isEmpty()) {
               stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.NOVELTY_DRINKING_HAT.value());
            }

            if (stack.getItem() instanceof DrinkingHatItem relic
               && event.getItem().getUseAnimation() == UseAnim.DRINK
               && relic.isAbilityUnlocked(stack, "drinking")) {
               relic.spreadRelicExperience(player, stack, (int)Math.ceil(event.getDuration() / 20.0F));
               if (relic.canPlayerUseAbility(player, stack, "nutrition")) {
                  int hunger = (int)relic.getStatValue(stack, "nutrition", "hunger");
                  player.getFoodData().eat(hunger, hunger / 2.0F);
               }
            }
         }
      }
   }
}
