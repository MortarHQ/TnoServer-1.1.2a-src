package com.chronodawn.entities.projectiles;

import com.chronodawn.ChronoDawn;
import com.chronodawn.entities.bosses.TimeTyrantEntity;
import com.chronodawn.registry.ModItems;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public class TimeArrowEntity extends AbstractArrow {
   public TimeArrowEntity(EntityType<? extends TimeArrowEntity> entityType, Level level) {
      super(entityType, level);
      ChronoDawn.LOGGER.debug("TimeArrowEntity constructed (no shooter)");
   }

   public TimeArrowEntity(EntityType<? extends TimeArrowEntity> entityType, LivingEntity shooter, Level level, ItemStack pickupItemStack) {
      super(entityType, shooter, level, pickupItemStack, null);
      ChronoDawn.LOGGER.debug("TimeArrowEntity constructed with shooter: {}", shooter.getName().getString());
   }

   protected ItemStack getDefaultPickupItem() {
      return new ItemStack((ItemLike)ModItems.TIME_ARROW.get());
   }

   protected void onHitEntity(EntityHitResult result) {
      super.onHitEntity(result);
      if (!this.level().isClientSide()) {
         ChronoDawn.LOGGER.debug("Time Arrow hit entity: {}", result.getEntity().getType());
         if (result.getEntity() instanceof LivingEntity target) {
            ChronoDawn.LOGGER.debug("Time Arrow applying effects to LivingEntity: {}", target.getName().getString());
            if (target instanceof TimeTyrantEntity) {
               ChronoDawn.LOGGER.debug("Time Arrow hit Time Tyrant - applying strong effects");
               target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
               target.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 100, 1));
               target.addEffect(new MobEffectInstance(MobEffects.GLOWING, 200, 0));
            } else {
               ChronoDawn.LOGGER.debug("Time Arrow hit normal mob - applying Slowness II");
               target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 1));
            }
         } else {
            ChronoDawn.LOGGER.warn("Time Arrow hit non-living entity: {}", result.getEntity().getType());
         }
      }
   }
}
