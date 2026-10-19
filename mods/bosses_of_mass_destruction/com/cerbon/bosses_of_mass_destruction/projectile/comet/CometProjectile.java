package com.cerbon.bosses_of_mass_destruction.projectile.comet;

import com.cerbon.bosses_of_mass_destruction.entity.BMDEntities;
import com.cerbon.bosses_of_mass_destruction.projectile.BaseThrownItemProjectile;
import com.cerbon.bosses_of_mass_destruction.projectile.util.ExemptEntities;
import java.util.List;
import java.util.function.Consumer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class CometProjectile extends BaseThrownItemProjectile implements GeoEntity {
   private final AnimatableInstanceCache animationFactory = GeckoLibUtil.createInstanceCache(this);
   private Consumer<Vec3> impactAction;
   private boolean impacted = false;

   public CometProjectile(EntityType<? extends ThrowableItemProjectile> entityType, Level level) {
      super(entityType, level);
   }

   public CometProjectile(LivingEntity livingEntity, Level level, Consumer<Vec3> impactAction, List<EntityType<?>> exemptEntities) {
      super((EntityType<? extends ThrowableItemProjectile>)BMDEntities.COMET.get(), livingEntity, level, new ExemptEntities(exemptEntities));
      this.impactAction = impactAction;
   }

   @Override
   public void entityHit(EntityHitResult entityHitResult) {
      this.onImpact();
   }

   protected void onHitBlock(@NotNull BlockHitResult result) {
      this.onImpact();
      super.onHitBlock(result);
   }

   public boolean hurt(@NotNull DamageSource source, float amount) {
      this.onImpact();
      return super.hurt(source, amount);
   }

   private void onImpact() {
      if (!this.impacted) {
         this.impacted = true;
         Entity owner = this.getOwner();
         if (owner instanceof LivingEntity) {
            if (this.impactAction != null) {
               this.impactAction.accept(this.position());
            }

            this.discard();
         }
      }
   }

   public boolean canCollideWith(@NotNull Entity entity) {
      return true;
   }

   public float getXRot() {
      return this.tickCount * 5.0F;
   }

   public void registerControllers(ControllerRegistrar controllerRegistrar) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.animationFactory;
   }
}
