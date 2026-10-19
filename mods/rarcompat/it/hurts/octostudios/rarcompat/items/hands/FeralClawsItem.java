package it.hurts.octostudios.rarcompat.items.hands;

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
import net.neoforged.neoforge.event.entity.player.AttackEntityEvent;
import top.theillusivec4.curios.api.SlotContext;

public class FeralClawsItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("claws")
                     .stat(
                        StatData.builder("modifier")
                           .initialValue(0.05, 0.15)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.12)
                           .formatValue(value -> (int)MathUtils.round(value * 100.0, 1))
                           .build()
                     )
                     .research(
                        ResearchData.builder()
                           .star(0, 4, 15)
                           .star(1, 12, 3)
                           .star(2, 6, 19)
                           .star(3, 16, 6)
                           .star(4, 9, 23)
                           .star(5, 17, 12)
                           .star(6, 13, 24)
                           .star(7, 17, 18)
                           .link(0, 1)
                           .link(2, 3)
                           .link(4, 5)
                           .link(6, 7)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-9314292).borderBottom(-15103723).build())
               .beams(BeamsData.builder().startColor(-9314292).endColor(1673493).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("claws").initialValue(1).gem(GemShape.SQUARE, GemColor.GREEN).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.OVERWORLD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && player.tickCount % 20 == 0 && this.canPlayerUseAbility(player, stack, "claws")) {
         addTime(stack, 1);
         int time = getTime(stack);
         int attackCount = getAttackCount(stack);
         if (player.getAttackStrengthScale(0.0F) != 1.0F) {
            EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE);
            addAttackCount(stack, -attackCount);
         }

         if (time >= 3) {
            if (attackCount <= 0) {
               EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE);
            }

            if (time % 3 == 0 && attackCount > 0) {
               addAttackCount(stack, -1);
               resetAttribute(player, stack, this);
            }

            addTime(stack, -time);
         }
      }
   }

   public static void resetAttribute(Player player, ItemStack stack, FeralClawsItem relic) {
      EntityUtils.resetAttribute(
         player, stack, Attributes.ATTACK_SPEED, (float)(getAttackCount(stack) * relic.getStatValue(stack, "claws", "modifier")), Operation.ADD_MULTIPLIED_BASE
      );
   }

   public static void addTime(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.TIME, (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0) + val);
   }

   public static int getTime(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
   }

   public static void addAttackCount(ItemStack stack, int val) {
      stack.set(DataComponentRegistry.COUNT, (Integer)stack.getOrDefault(DataComponentRegistry.COUNT, 0) + val);
   }

   public static int getAttackCount(ItemStack stack) {
      return (Integer)stack.getOrDefault(DataComponentRegistry.COUNT, 0);
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && newStack.getItem() != stack.getItem()) {
         EntityUtils.removeAttribute(player, stack, Attributes.ATTACK_SPEED, Operation.ADD_MULTIPLIED_BASE);
      }
   }

   @EventBusSubscriber
   public static class FeralClawsEvent {
      @SubscribeEvent
      public static void onPlayerAttack(AttackEntityEvent event) {
         Player player = event.getEntity();
         ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.FERAL_CLAWS.value());
         if (stack.getItem() instanceof FeralClawsItem relic && relic.canPlayerUseAbility(player, stack, "claws")) {
            if (player.getAttackStrengthScale(0.0F) != 1.0F) {
               relic.spreadRelicExperience(player, stack, 1);
            }

            FeralClawsItem.addAttackCount(stack, 1);
            FeralClawsItem.addTime(stack, -FeralClawsItem.getTime(stack));
            FeralClawsItem.resetAttribute(player, stack, relic);
         }
      }
   }
}
