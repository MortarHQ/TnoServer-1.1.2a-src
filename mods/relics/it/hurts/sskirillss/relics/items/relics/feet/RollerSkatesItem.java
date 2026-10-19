package it.hurts.sskirillss.relics.items.relics.feet;

import it.hurts.sskirillss.relics.api.events.common.EntityBlockSpeedFactorEvent;
import it.hurts.sskirillss.relics.api.events.common.LivingSlippingEvent;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import top.theillusivec4.curios.api.SlotContext;

public class RollerSkatesItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("skating")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.001, 0.005)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)(MathUtils.round(value, 3) * 100.0 * 100.0))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(15.0, 35.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> MathUtils.round(value / 5.0, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 200))
         .loot(LootData.builder().entry(LootEntries.OVERWORLD).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         int var5 = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
         if (player.isSprinting() && !player.isShiftKeyDown() && !player.isInWater() && !player.isInLava()) {
            if (player.tickCount % 20 == 0) {
               this.spreadRelicExperience(player, stack, 1);
            }

            if (var5 < this.getStatValue(stack, "skating", "duration") && player.tickCount % 4 == 0) {
               stack.set(DataComponentRegistry.CHARGE, var5 + 1);
            }
         } else if (var5 > 0) {
            stack.set(DataComponentRegistry.CHARGE, --var5);
         }

         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         if (var5 > 0) {
            EntityUtils.applyAttribute(
               player, stack, Attributes.MOVEMENT_SPEED, (float)(var5 * this.getStatValue(stack, "skating", "speed")), Operation.ADD_MULTIPLIED_TOTAL
            );
            EntityUtils.applyAttribute(player, stack, Attributes.STEP_HEIGHT, 0.6F, Operation.ADD_VALUE);
         } else {
            EntityUtils.removeAttribute(player, stack, Attributes.STEP_HEIGHT, Operation.ADD_VALUE);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      if (stack.getItem() != newStack.getItem()) {
         LivingEntity entity = slotContext.entity();
         EntityUtils.removeAttribute(entity, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         EntityUtils.removeAttribute(entity, stack, Attributes.STEP_HEIGHT, Operation.ADD_VALUE);
      }
   }

   @EventBusSubscriber(modid = "relics")
   public static class Events {
      @SubscribeEvent
      public static void onLivingSlipping(LivingSlippingEvent event) {
         if (event.getEntity() instanceof Player player && !player.isInLiquid() && !player.isFallFlying() && player.onGround() && !player.isSwimming()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.ROLLER_SKATES.get());
            if (!stack.isEmpty()) {
               event.setFriction(1.075F);
            }
         }
      }

      @SubscribeEvent
      public static void onSpeedFactor(EntityBlockSpeedFactorEvent event) {
         if (event.getEntity() instanceof Player player && !player.isInLiquid() && !player.isFallFlying() && player.onGround() && !player.isSwimming()) {
            ItemStack stack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.ROLLER_SKATES.get());
            if (!stack.isEmpty()) {
               event.setSpeedFactor(1.0F);
            }
         }
      }
   }
}
