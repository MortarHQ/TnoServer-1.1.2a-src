package mebahelcreaturesdwarven.entity.custom;

import mebahelcreaturesdwarven.entity.ai.CustomRevengeGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_sphere.DwemerSphereMeleeAttackGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_sphere.DwemerSphereShootingGoal;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1266;
import net.minecraft.class_1267;
import net.minecraft.class_1282;
import net.minecraft.class_1299;
import net.minecraft.class_13;
import net.minecraft.class_1309;
import net.minecraft.class_1315;
import net.minecraft.class_1330;
import net.minecraft.class_1347;
import net.minecraft.class_1376;
import net.minecraft.class_1394;
import net.minecraft.class_14;
import net.minecraft.class_1400;
import net.minecraft.class_1408;
import net.minecraft.class_1409;
import net.minecraft.class_1439;
import net.minecraft.class_1642;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_243;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3414;
import net.minecraft.class_3532;
import net.minecraft.class_3730;
import net.minecraft.class_3763;
import net.minecraft.class_5134;
import net.minecraft.class_5425;
import net.minecraft.class_9;
import net.minecraft.class_1297.class_5529;
import net.minecraft.class_2945.class_9222;
import net.minecraft.class_5132.class_5133;
import org.jetbrains.annotations.Nullable;
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

public class DwemerSphereEntity extends DwemerEntity implements GeoEntity {
   public static final class_2940<Boolean> RANGED_MODE = class_2945.method_12791(DwemerSphereEntity.class, class_2943.field_13323);
   public static final class_2940<Boolean> IN_COMBAT = class_2945.method_12791(DwemerSphereEntity.class, class_2943.field_13323);
   public static final class_2940<Boolean> IS_TRANSFORMING = class_2945.method_12791(DwemerSphereEntity.class, class_2943.field_13323);
   public static final class_2940<Integer> ATTACK_BOW_SEQ = class_2945.method_12791(DwemerSphereEntity.class, class_2943.field_13327);
   private static final class_2940<Integer> DATA_ID_TYPE_VARIANT = class_2945.method_12791(DwemerSphereEntity.class, class_2943.field_13327);
   private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);
   private boolean wasInCombat = false;
   private static final int TRANSFORM_LOCK_TICKS = 30;
   private int transformLockTicks = 0;
   private int lastAttackBowSeqSeen = 0;

   public DwemerSphereEntity(class_1299<? extends DwemerEntity> entityType, class_1937 world) {
      super(entityType, world);
      this.field_6191 = -this.method_5970();
   }

   public boolean isRangedMode() {
      return (Boolean)this.field_6011.method_12789(RANGED_MODE);
   }

   public void setRangedMode(boolean ranged) {
      this.field_6011.method_12778(RANGED_MODE, ranged);
   }

   public boolean isInCombat() {
      return (Boolean)this.field_6011.method_12789(IN_COMBAT);
   }

   public void setInCombat(boolean v) {
      this.field_6011.method_12778(IN_COMBAT, v);
   }

   public boolean isTransforming() {
      return (Boolean)this.field_6011.method_12789(IS_TRANSFORMING);
   }

   private void setTransforming(boolean v) {
      this.field_6011.method_12778(IS_TRANSFORMING, v);
   }

   @Override
   public boolean shouldDespawnInPeaceful() {
      return this.method_37908().method_8407() == class_1267.field_5801;
   }

   public void bumpAttackBowSeq() {
      if (!this.method_37908().field_9236) {
         this.field_6011.method_12778(ATTACK_BOW_SEQ, (Integer)this.field_6011.method_12789(ATTACK_BOW_SEQ) + 1);
      }
   }

   public void rollModeSwitchAfterAttack() {
      if (!this.method_37908().field_9236) {
         if (this.field_5974.method_43048(3) == 0) {
            this.setRangedMode(!this.isRangedMode());
         }
      }
   }

   private boolean isMovingHorizontally() {
      class_243 v = this.method_18798();
      return v.field_1352 * v.field_1352 + v.field_1350 * v.field_1350 > 4.0E-4;
   }

   private boolean computeCombatServer() {
      if (this.method_6510()) {
         return true;
      }

      class_1309 t = this.method_5968();
      return t != null && t.method_5805() ? !(t instanceof class_1657 p && (p.method_7337() || p.method_7325())) : false;
   }

   private void startTransformLock(boolean opening) {
      this.transformLockTicks = 30;
      this.setTransforming(true);
      this.setShooting(false);
      this.setSwinging(false);
      this.method_19540(false);
      this.method_5942().method_6340();
      this.method_5962().method_6243(0.0F, 0.0F);
      this.method_18800(0.0, this.method_18798().field_1351, 0.0);
      this.field_6007 = true;
      this.method_37908()
         .method_43128(
            null,
            this.method_23317(),
            this.method_23318(),
            this.method_23321(),
            opening ? ModSounds.DWARVEN_SPHERE_OPEN : ModSounds.DWARVEN_SPHERE_CLOSE,
            this.method_5634(),
            0.8F + this.method_59922().method_43057() * 0.2F,
            0.9F + this.method_59922().method_43057() * 0.2F
         );
   }

   private void tickTransformLockServer() {
      if (this.transformLockTicks > 0) {
         this.transformLockTicks--;
         if (this.transformLockTicks <= 0) {
            this.transformLockTicks = 0;
            this.setTransforming(false);
         } else {
            this.setShooting(false);
            this.setSwinging(false);
            this.method_19540(false);
            this.method_5942().method_6340();
            this.method_5962().method_6243(0.0F, 0.0F);
            this.method_18800(0.0, this.method_18798().field_1351, 0.0);
            this.field_6007 = true;
         }
      }
   }

   public void method_5773() {
      super.method_5773();
      if (this.shouldDespawnInPeaceful()) {
         this.method_5650(class_5529.field_26999);
      }

      if (!this.method_37908().field_9236) {
         this.tickTransformLockServer();
         boolean nowCombat = this.computeCombatServer();
         this.setInCombat(nowCombat);
         if (nowCombat && !this.wasInCombat) {
            this.triggerAnim("attacking", "transform");
            this.startTransformLock(true);
            this.wasInCombat = true;
         } else if (!nowCombat && this.wasInCombat) {
            this.triggerAnim("attacking", "transform_backward");
            this.startTransformLock(false);
            this.wasInCombat = false;
         }
      }
   }

   @Override
   protected void method_5693(class_9222 builder) {
      super.method_5693(builder);
      builder.method_56912(IN_COMBAT, false);
      builder.method_56912(RANGED_MODE, false);
      builder.method_56912(IS_TRANSFORMING, false);
      builder.method_56912(ATTACK_BOW_SEQ, 0);
      builder.method_56912(DATA_ID_TYPE_VARIANT, 0);
   }

   public static class_5133 setAttributes() {
      return DwemerEntity.setAttributes()
         .method_26868(class_5134.field_23716, 45.0)
         .method_26868(class_5134.field_23724, 5.0)
         .method_26868(class_5134.field_23721, 8.0)
         .method_26868(class_5134.field_23722, 0.15F);
   }

   protected void method_5959() {
      this.field_6201.method_6277(1, new class_1347(this));
      this.field_6201.method_6277(2, new DwemerSphereShootingGoal(this));
      this.field_6201.method_6277(3, new DwemerSphereMeleeAttackGoal(this, 1.0, 1, 2.0));
      this.field_6201.method_6277(6, new class_1394(this, 1.0, 1.0F));
      this.field_6201.method_6277(7, new class_1376(this));
      this.field_6185.method_6277(1, new CustomRevengeGoal(this, DwemerEntity.class));
      this.field_6185.method_6277(2, new class_1400(this, class_1657.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1646.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1439.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_3763.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1642.class, true));
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
      AnimationController<?> controller = event.getController();
      if (this.isTransforming()) {
         return PlayState.CONTINUE;
      }

      int seq = (Integer)this.field_6011.method_12789(ATTACK_BOW_SEQ);
      if (seq != this.lastAttackBowSeqSeen) {
         this.lastAttackBowSeqSeen = seq;
         controller.forceAnimationReset();
         controller.setAnimation(RawAnimation.begin().then("attack_bow", LoopType.PLAY_ONCE));
         this.setCurrentAnimation("attack_bow");
         return PlayState.CONTINUE;
      }

      if (this.isShooting()) {
         return PlayState.CONTINUE;
      }

      boolean inCombat = this.isInCombat();
      boolean moving = event.isMoving() && this.isMovingHorizontally();
      if (!this.isSwinging()) {
         if (inCombat) {
            if (moving) {
               controller.setAnimation(RawAnimation.begin().then("move_forward", LoopType.LOOP));
               this.setCurrentAnimation("move_forward");
            } else {
               controller.setAnimation(RawAnimation.begin().then("idle_combat", LoopType.LOOP));
               this.setCurrentAnimation("idle_combat");
            }
         } else if (moving) {
            controller.setAnimation(RawAnimation.begin().then("sm_move_forward", LoopType.LOOP));
            this.setCurrentAnimation("sm_move_forward");
         } else {
            controller.setAnimation(RawAnimation.begin().then("sphere_mode_idle", LoopType.LOOP));
            this.setCurrentAnimation("sphere_mode_idle");
         }
      }

      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "attacking", 4, this::predicate)
            .triggerableAnim("transform", RawAnimation.begin().then("transform", LoopType.PLAY_ONCE))
            .triggerableAnim("transform_backward", RawAnimation.begin().then("transform_backward", LoopType.PLAY_ONCE))
            .triggerableAnim("attack_bow", RawAnimation.begin().then("attack_bow", LoopType.PLAY_ONCE))
            .triggerableAnim("attack1", RawAnimation.begin().then("attack1", LoopType.PLAY_ONCE))
            .triggerableAnim("attack2", RawAnimation.begin().then("attack2", LoopType.PLAY_ONCE))
            .triggerableAnim("attack_double", RawAnimation.begin().then("attack_double", LoopType.PLAY_ONCE))
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.factory;
   }

   @Override
   protected class_3414 method_6011(class_1282 source) {
      return this.method_59922().method_43056() ? ModSounds.DWARVEN_SPHERE_INJURED_1 : ModSounds.DWARVEN_SPHERE_INJURED_2;
   }

   @Override
   protected class_3414 method_6002() {
      return this.method_59922().method_43056() ? ModSounds.DWARVEN_SPHERE_DEATH_1 : ModSounds.DWARVEN_SPHERE_DEATH_2;
   }

   @Override
   protected void method_6013(class_1282 source) {
      class_3414 hurtSound = this.method_6011(source);
      if (hurtSound != null) {
         this.method_5783(hurtSound, 0.45F, 0.8F + this.method_59922().method_43057() * 0.2F);
      }
   }

   @Override
   protected class_1408 method_5965(class_1937 world) {
      return new class_1409(this, world) {
         protected class_13 method_6336(int range) {
            this.field_6678 = new class_14();
            this.field_6678.method_15(true);
            return new class_13(this.field_6678, range) {
               protected float method_44000(class_9 a, class_9 b) {
                  return a.method_44022(b);
               }
            };
         }
      };
   }

   @Override
   public int method_5970() {
      return 240;
   }

   @Override
   protected class_3414 method_5994() {
      return ModSounds.DWARVEN_SPHERE_AMBIENT_1;
   }

   @Override
   public void method_5966() {
      class_3414 soundEvent = this.method_5994();
      if (soundEvent != null) {
         this.method_5783(soundEvent, 0.35F, 0.8F + this.method_59922().method_43057() * 0.2F);
      }
   }

   @Override
   public void method_5652(class_2487 nbt) {
      super.method_5652(nbt);
      nbt.method_10569("Variant", this.getTypeVariant());
      nbt.method_10556("RangedMode", this.isRangedMode());
   }

   @Override
   public void method_5749(class_2487 nbt) {
      super.method_5749(nbt);
      this.field_6011.method_12778(DATA_ID_TYPE_VARIANT, nbt.method_10550("Variant"));
      if (nbt.method_10545("RangedMode")) {
         this.setRangedMode(nbt.method_10577("RangedMode"));
      }
   }

   @Nullable
   public class_1315 method_5943(class_5425 world, class_1266 difficulty, class_3730 spawnReason, @Nullable class_1315 entityData) {
      class_1315 data = super.method_5943(world, difficulty, spawnReason, entityData);
      if (!world.method_8608()) {
         this.setRangedMode(this.field_5974.method_43056());
      }

      return data;
   }

   @Override
   protected void method_5712(class_2338 pos, class_2680 state) {
   }

   protected class_1330 method_5963() {
      return new class_1330(this) {
         private static final float BODY_TURN_SPEED = 8.0F;
         private static final float HEAD_TURN_SPEED = 12.0F;

         public void method_6224() {
            if (DwemerSphereEntity.this.isSwinging()) {
               float desired = DwemerSphereEntity.this.method_36454();
               float newBody = class_3532.method_15388(DwemerSphereEntity.this.field_6283, desired, 8.0F);
               float newHead = class_3532.method_15388(DwemerSphereEntity.this.field_6241, desired, 12.0F);
               DwemerSphereEntity.this.method_5636(newBody);
               DwemerSphereEntity.this.method_5847(newHead);
            } else {
               super.method_6224();
            }
         }
      };
   }
}
