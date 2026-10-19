package it.hurts.sskirillss.relics.items.relics.feet;

import it.hurts.sskirillss.relics.init.DataComponentRegistry;
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
import it.hurts.sskirillss.relics.utils.WorldUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class IceSkatesItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("skating")
                     .stat(
                        StatData.builder("speed")
                           .initialValue(0.01, 0.035)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> (int)(MathUtils.round(value, 3) * 10.0 * 100.0))
                           .build()
                     )
                     .stat(
                        StatData.builder("duration")
                           .initialValue(25.0, 50.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value / 10.0, 1))
                           .build()
                     )
                     .build()
               )
               .ability(
                  AbilityData.builder("ram")
                     .requiredLevel(5)
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.05, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value * 10.0, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 200))
         .loot(LootData.builder().entry(LootEntries.FROST).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (slotContext.entity() instanceof Player player) {
         Level var12 = player.getCommandSenderWorld();
         BlockPos pos = player.blockPosition().atY((int)Math.floor(WorldUtils.getGroundHeight(player, player.position(), 16)));
         int duration = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
         int maxDuration = (int)Math.round(this.getStatValue(stack, "skating", "duration"));
         if (player.isSprinting() && !player.isShiftKeyDown() && !player.isInWater() && !player.isInLava() && var12.getBlockState(pos).is(BlockTags.ICE)) {
            if (player.tickCount % 20 == 0) {
               this.spreadRelicExperience(player, stack, 1);
            }

            if (duration < maxDuration && player.tickCount % 2 == 0) {
               stack.set(DataComponentRegistry.CHARGE, ++duration);
            }

            if (var12.getRandom().nextInt(maxDuration) < duration) {
               var12.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.15F, player.getZ(), 0.0, 0.25, 0.0);
            }
         } else if (duration > 0) {
            stack.set(DataComponentRegistry.CHARGE, Math.max(0, duration - 2));
         }

         if (this.isAbilityUnlocked(stack, "ram") && duration >= 10) {
            for (LivingEntity entity : var12.getEntitiesOfClass(LivingEntity.class, player.getBoundingBox())) {
               if (entity != player && entity.hurtTime <= 0) {
                  EntityUtils.hurt(entity, var12.damageSources().playerAttack(player), (float)(duration * this.getStatValue(stack, "ram", "damage")));
                  double factor = Mth.clamp(duration * 0.025, 1.0, 2.0);
                  entity.setDeltaMovement(
                     entity.position().add(0.0, 0.5, 0.0).subtract(player.position()).normalize().multiply(factor, Math.max(1.0, factor / 2.0), factor)
                  );
               }
            }
         }

         EntityUtils.removeAttribute(player, stack, Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_TOTAL);
         if (duration > 0) {
            EntityUtils.applyAttribute(
               player, stack, Attributes.MOVEMENT_SPEED, (float)(duration * this.getStatValue(stack, "skating", "speed")), Operation.ADD_MULTIPLIED_TOTAL
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
}
