package com.cerbon.bosses_of_mass_destruction.projectile;

import java.util.function.Predicate;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;

public abstract class BaseThrownItemProjectile extends ThrowableItemProjectile {
   protected final Predicate<EntityHitResult> entityCollisionPredicate;
   protected Predicate<HitResult> collisionPredicate = hitResult -> !this.level().isClientSide();

   public BaseThrownItemProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
      this.entityCollisionPredicate = entityHitResult -> true;
   }

   public BaseThrownItemProjectile(
      EntityType<? extends ThrowableItemProjectile> entityType, LivingEntity livingEntity, Level level, Predicate<EntityHitResult> collisionPredicate
   ) {
      super(entityType, livingEntity, level);
      this.entityCollisionPredicate = collisionPredicate;
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         this.clientTick();
      }
   }

   protected void onHit(@NotNull HitResult hitResult) {
      if (this.collisionPredicate.test(hitResult)) {
         super.onHit(hitResult);
      }
   }

   protected void onHitEntity(@NotNull EntityHitResult entityHitResult) {
      if (this.entityCollisionPredicate.test(entityHitResult)) {
         this.entityHit(entityHitResult);
      }
   }

   protected abstract void entityHit(EntityHitResult var1);

   public void clientTick() {
   }

   @NotNull
   protected Item getDefaultItem() {
      return Items.SNOWBALL;
   }
}
