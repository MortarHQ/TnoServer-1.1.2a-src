package mebahelcreaturesdwarven.entity.projectile;

import mebahelcreaturesdwarven.entity.ModEntities;
import mebahelcreaturesdwarven.entity.custom.DwemerEntity;
import mebahelcreaturesdwarven.particle.ModParticles;
import net.minecraft.class_1293;
import net.minecraft.class_1294;
import net.minecraft.class_1297;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1792;
import net.minecraft.class_1799;
import net.minecraft.class_1802;
import net.minecraft.class_1937;
import net.minecraft.class_239;
import net.minecraft.class_2394;
import net.minecraft.class_243;
import net.minecraft.class_2680;
import net.minecraft.class_3857;
import net.minecraft.class_3965;
import net.minecraft.class_3966;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;

public class SteamProjectileEntity extends class_3857 implements GeoEntity {
   private static final class_1792 DEFAULT_RENDER_ITEM = class_1802.field_8543;
   private static final int MAX_LIFETIME_TICKS = 15;
   private float damage;
   private int ticksExisted = 0;
   private class_243 previousPosition;
   private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

   public SteamProjectileEntity(class_1299<? extends SteamProjectileEntity> entityType, class_1937 world) {
      super(entityType, world);
      this.method_16940(new class_1799(DEFAULT_RENDER_ITEM));
   }

   public SteamProjectileEntity(class_1937 world, class_1309 owner, float damage) {
      super(ModEntities.STEAM_PROJECTILE, owner, world);
      this.damage = damage;
      this.method_16940(new class_1799(DEFAULT_RENDER_ITEM));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.factory;
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
      state.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   protected class_1792 method_16942() {
      return DEFAULT_RENDER_ITEM;
   }

   protected void method_7488(class_239 hitResult) {
      super.method_7488(hitResult);
      if (!this.method_37908().field_9236) {
         this.method_31472();
      }
   }

   protected void method_7454(class_3966 entityHitResult) {
      class_1297 hit = entityHitResult.method_17782();
      if (!(hit instanceof DwemerEntity)) {
         if (hit instanceof class_1309 target) {
            class_1297 owner = this.method_24921();
            target.method_5643(this.method_48923().method_48811(this, owner), this.damage > 0.0F ? this.damage : 5.0F);
            if (!target.method_6039()) {
               class_1293 slow = new class_1293(class_1294.field_5909, 80, 0, false, false, false);
               target.method_37222(slow, owner);
            }
         }

         if (!this.method_37908().field_9236) {
            this.method_31472();
         }
      }
   }

   protected void method_24920(class_3965 blockHitResult) {
      class_2680 blockState = this.method_37908().method_8320(blockHitResult.method_17777());
      blockState.method_26175(this.method_37908(), blockState, blockHitResult, this);
      if (!this.method_37908().field_9236) {
         this.method_31472();
      }
   }

   public void method_5773() {
      super.method_5773();
      this.ticksExisted++;
      if (this.ticksExisted >= 15) {
         this.method_31472();
      } else {
         if (this.method_37908().field_9236) {
            this.generateTrailParticles(this.method_19538());
         }
      }
   }

   private void generateTrailParticles(class_243 currentPosition) {
      class_2394 particleEffect = ModParticles.STEAM_PARTICLE;
      if (this.previousPosition != null) {
         class_243 delta = currentPosition.method_1020(this.previousPosition);
         double distance = delta.method_1033();
         if (distance >= 0.05) {
            int steps = Math.max(1, (int)(distance / 0.1));

            for (int i = 0; i <= steps; i++) {
               double t = (double)i / steps;
               class_243 p = this.previousPosition.method_35590(currentPosition, t);

               for (int j = 0; j < 3; j++) {
                  double ox = (this.field_5974.method_43058() - 0.5) * 0.3;
                  double oy = (this.field_5974.method_43058() - 0.5) * 0.3;
                  double oz = (this.field_5974.method_43058() - 0.5) * 0.3;
                  this.method_37908().method_8406(particleEffect, p.field_1352 + ox, p.field_1351 + oy, p.field_1350 + oz, 0.0, 0.0, 0.0);
               }
            }
         }
      }

      this.previousPosition = currentPosition;
   }

   protected double method_7490() {
      return 0.0;
   }
}
