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
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import top.theillusivec4.curios.api.SlotContext;

public class PanicNecklaceItem extends WearableRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("panic")
                     .stat(
                        StatData.builder("movement")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value * 10.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("radius")
                           .initialValue(6.0, 8.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .research(ResearchData.builder().star(0, 7, 13).star(1, 15, 13).star(2, 11, 18).link(0, 1).link(1, 2).link(2, 0).build())
                     .build()
               )
               .build()
         )
         .style(
            StyleData.builder()
               .tooltip(TooltipData.builder().borderTop(-2348773).borderBottom(-11075571).build())
               .beams(BeamsData.builder().startColor(-232870).endColor(5701645).build())
               .build()
         )
         .leveling(
            LevelingData.builder()
               .initialCost(100)
               .maxLevel(10)
               .step(100)
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("panic").initialValue(1).gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.CAVE, LootEntries.MINESHAFT}).build())
         .build();
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (stack.getItem() != newStack.getItem() && slotContext.entity() instanceof Player player) {
         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE);
      }
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player && !player.getCommandSenderWorld().isClientSide() && this.canPlayerUseAbility(player, stack, "panic")) {
         double radius = this.getStatValue(stack, "panic", "radius");
         double target = player.getCommandSenderWorld()
                  .getEntitiesOfClass(Monster.class, player.getBoundingBox().inflate(radius))
                  .stream()
                  .filter(mob -> mob.getTarget() == player)
                  .count()
               * this.getStatValue(stack, "panic", "movement")
            + player.getCommandSenderWorld()
               .getEntitiesOfClass(Player.class, player.getBoundingBox().inflate(radius))
               .stream()
               .filter(player1 -> !player1.getUUID().equals(player.getUUID()))
               .count();
         double speed = this.getSpeed(stack);
         double step = 0.01;
         double modifier = speed < target ? step : (speed > target ? -step : 0.0);
         if (modifier != 0.0) {
            this.addSpeed(stack, modifier);
         }

         EntityUtils.resetAttribute(player, stack, Attributes.MOVEMENT_SPEED, (float)this.getSpeed(stack), Operation.ADD_MULTIPLIED_BASE);
      }
   }

   public void addSpeed(ItemStack stack, double val) {
      this.setSpeed(stack, this.getSpeed(stack) + val);
   }

   public double getSpeed(ItemStack stack) {
      return (Double)stack.getOrDefault(DataComponentRegistry.SPEED, 0.0);
   }

   public void setSpeed(ItemStack stack, double val) {
      stack.set(DataComponentRegistry.SPEED, Math.max(val, 0.0));
   }

   @EventBusSubscriber
   public static class PanicNecklaceEvent {
      @SubscribeEvent
      public static void onPlayerDamage(LivingIncomingDamageEvent event) {
         if (event.getEntity() instanceof Player player && event.getSource().getEntity() instanceof Mob) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ModItems.PANIC_NECKLACE.value());
            if (stack.getItem() instanceof PanicNecklaceItem relic && relic.isAbilityUnlocked(stack, "panic")) {
               relic.spreadRelicExperience(player, stack, 1);
            }
         }
      }
   }
}
