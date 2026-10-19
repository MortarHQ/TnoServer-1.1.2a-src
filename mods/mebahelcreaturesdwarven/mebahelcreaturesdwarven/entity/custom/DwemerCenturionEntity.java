package mebahelcreaturesdwarven.entity.custom;

import mebahelcreaturesdwarven.entity.ai.CustomRevengeGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_centurion.DwarvenCenturionMeleeAttackGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_centurion.DwarvenCenturionShootingGoal;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.mebahelsapi.util.LargeEntityPathNodeMaker;
import net.minecraft.class_1266;
import net.minecraft.class_1267;
import net.minecraft.class_1282;
import net.minecraft.class_1299;
import net.minecraft.class_13;
import net.minecraft.class_1303;
import net.minecraft.class_1315;
import net.minecraft.class_1324;
import net.minecraft.class_1330;
import net.minecraft.class_1347;
import net.minecraft.class_1376;
import net.minecraft.class_1394;
import net.minecraft.class_1400;
import net.minecraft.class_1408;
import net.minecraft.class_1409;
import net.minecraft.class_1439;
import net.minecraft.class_1588;
import net.minecraft.class_1642;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
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

public class DwemerCenturionEntity extends DwemerEntity implements GeoEntity {
   private double lastStepTick = 0.0;
   private double rand;
   public static final class_2940<Boolean> SHOOTING = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13323);
   public static final class_2940<Boolean> SWINGING = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13323);
   public static final class_2940<Float> COOLDOWN = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13320);
   public static final class_2940<String> ATTACK_NAME = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13326);
   public static final class_2940<String> CURRENT_ANIMATION = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13326);
   private static final class_2940<Integer> DATA_ID_TYPE_VARIANT = class_2945.method_12791(DwemerCenturionEntity.class, class_2943.field_13327);
   private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.factory;
   }

   public DwemerCenturionEntity(class_1299<? extends DwemerEntity> entityType, class_1937 world) {
      super(entityType, world);
      this.field_6191 = -this.method_5970();
      this.method_5971();
   }

   @Override
   public float getCooldown() {
      return (Float)this.field_6011.method_12789(COOLDOWN);
   }

   @Override
   public void setCooldown(float cooldown) {
      this.field_6011.method_12778(COOLDOWN, cooldown);
   }

   @Override
   public boolean isShooting() {
      return (Boolean)this.field_6011.method_12789(SHOOTING);
   }

   @Override
   public void setShooting(boolean shooting) {
      this.field_6011.method_12778(SHOOTING, shooting);
   }

   @Override
   public void setSwinging(boolean swinging) {
      this.field_6011.method_12778(SWINGING, swinging);
   }

   @Override
   public boolean isSwinging() {
      return (Boolean)this.field_6011.method_12789(SWINGING);
   }

   @Override
   public void setAttackName(String attackName) {
      this.field_6011.method_12778(ATTACK_NAME, attackName);
   }

   @Override
   public String getAttackName() {
      return (String)this.field_6011.method_12789(ATTACK_NAME);
   }

   @Override
   public String getCurrentAnimation() {
      return (String)this.field_6011.method_12789(CURRENT_ANIMATION);
   }

   @Override
   public void setCurrentAnimation(String animation) {
      this.field_6011.method_12778(CURRENT_ANIMATION, animation);
   }

   @Override
   public boolean shouldDespawnInPeaceful() {
      return this.method_37908().method_8407() == class_1267.field_5801;
   }

   @Override
   protected void method_5693(class_9222 builder) {
      super.method_5693(builder);
      builder.method_56912(SHOOTING, false);
      builder.method_56912(SWINGING, false);
      builder.method_56912(COOLDOWN, 0.0F);
      builder.method_56912(DATA_ID_TYPE_VARIANT, 0);
      builder.method_56912(ATTACK_NAME, "attack2");
      builder.method_56912(CURRENT_ANIMATION, "idle");
   }

   public static class_5133 setAttributes() {
      return class_1588.method_26828()
         .method_26868(class_5134.field_23717, 20.0)
         .method_26868(class_5134.field_23716, 84.0)
         .method_26868(class_5134.field_23719, 0.3)
         .method_26868(class_5134.field_23724, 8.0)
         .method_26868(class_5134.field_23721, 9.0)
         .method_26868(class_5134.field_23718, 0.8F)
         .method_26868(class_5134.field_23722, 0.25);
   }

   protected void method_5959() {
      this.field_6201.method_6277(1, new class_1347(this));
      this.field_6201.method_6277(2, new DwarvenCenturionShootingGoal(this, 3.0F));
      this.field_6201.method_6277(3, new DwarvenCenturionMeleeAttackGoal(this, 1.0, 2));
      this.field_6201.method_6277(6, new class_1394(this, 0.85F, 1.0F));
      this.field_6201.method_6277(7, new class_1376(this));
      this.field_6185.method_6277(1, new CustomRevengeGoal(this, DwemerEntity.class));
      this.field_6185.method_6277(2, new class_1400(this, class_1657.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1646.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1439.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_3763.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1642.class, true));
   }

   public void method_5773() {
      super.method_5773();
      if (this.shouldDespawnInPeaceful()) {
         this.method_5650(class_5529.field_26999);
      } else {
         class_1324 speedAttr = this.method_5996(class_5134.field_23719);
         if (speedAttr != null) {
            if (this.isShooting()) {
               speedAttr.method_6192(0.0);
            } else if (speedAttr.method_6201() == 0.0) {
               speedAttr.method_6192(0.3);
            }
         }
      }
   }

   @Override
   public void method_6078(class_1282 source) {
      super.method_6078(source);
      int xpToDrop = 60 + this.field_5974.method_43048(30);
      if (!this.method_37908().field_9236) {
         while (xpToDrop > 0) {
            int xpAmount = class_1303.method_5918(xpToDrop);
            xpToDrop -= xpAmount;
            this.method_37908().method_8649(new class_1303(this.method_37908(), this.method_23317(), this.method_23318(), this.method_23321(), xpAmount));
         }
      }
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
      if (event.isMoving() && !this.isSwinging() && !this.isShooting()) {
         event.getController().setAnimation(RawAnimation.begin().then("walk", LoopType.LOOP));
         return PlayState.CONTINUE;
      } else if (!event.isMoving() && !this.isSwinging() && !this.isShooting()) {
         event.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
         return PlayState.CONTINUE;
      } else {
         return PlayState.CONTINUE;
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 4, this::predicate));
      controllers.add(
         new AnimationController(this, "attacking", 4, state -> PlayState.CONTINUE)
            .triggerableAnim("shooting", RawAnimation.begin().then("shooting", LoopType.PLAY_ONCE))
            .triggerableAnim("attack1", RawAnimation.begin().then("attack1", LoopType.PLAY_ONCE))
            .triggerableAnim("attack2", RawAnimation.begin().then("attack2", LoopType.PLAY_ONCE))
            .triggerableAnim("attack_dual", RawAnimation.begin().then("attack_dual", LoopType.PLAY_ONCE))
            .triggerableAnim("attack_double", RawAnimation.begin().then("attack_double", LoopType.PLAY_ONCE))
      );
   }

   @Override
   protected class_1408 method_5965(class_1937 world) {
      return new class_1409(this, world) {
         protected class_13 method_6336(int range) {
            this.field_6678 = new LargeEntityPathNodeMaker();
            this.field_6678.method_15(true);
            return new class_13(this.field_6678, range);
         }
      };
   }

   public float method_49476() {
      return 1.1F;
   }

   @Override
   public void method_5652(class_2487 nbt) {
      super.method_5652(nbt);
      nbt.method_10569("Variant", this.getTypeVariant());
   }

   @Override
   public void method_5749(class_2487 nbt) {
      super.method_5749(nbt);
      this.field_6011.method_12778(DATA_ID_TYPE_VARIANT, nbt.method_10550("Variant"));
   }

   @Override
   public int getTypeVariant() {
      return (Integer)this.field_6011.method_12789(DATA_ID_TYPE_VARIANT);
   }

   @Nullable
   public class_1315 method_5943(class_5425 world, class_1266 difficulty, class_3730 spawnReason, @Nullable class_1315 entityData) {
      return super.method_5943(world, difficulty, spawnReason, entityData);
   }

   @Override
   protected void method_5712(class_2338 pos, class_2680 state) {
      if (this.lastStepTick == 0.0) {
         this.method_5783(ModSounds.DWARVEN_CENTURION_WALK, 0.15F, 1.0F);
         this.lastStepTick = this.field_6012;
      }

      if (this.lastStepTick + 12.0 <= this.field_6012) {
         this.method_5783(ModSounds.DWARVEN_CENTURION_WALK, 0.15F, 1.0F);
         this.lastStepTick = this.field_6012;
      }
   }

   @Override
   public int method_5970() {
      return 360;
   }

   @Override
   protected class_3414 method_5994() {
      return ModSounds.DWARVEN_CENTURION_AMBIENT_1;
   }

   @Override
   public void method_5966() {
      class_3414 soundEvent = this.method_5994();
      if (soundEvent != null) {
         this.method_5783(soundEvent, 0.35F, 1.0F);
      }
   }

   @Override
   protected class_3414 method_6002() {
      return ModSounds.DWARVEN_CENTURION_DEATH_1;
   }

   @Override
   protected void method_6013(class_1282 source) {
      class_3414 hurtSound = this.method_6011(source);
      if (hurtSound != null) {
         this.method_5783(hurtSound, 0.45F, 1.0F);
      }
   }

   @Override
   protected class_3414 method_6011(class_1282 source) {
      this.rand = Math.random();
      return this.rand < 0.5 ? ModSounds.DWARVEN_CENTURION_INJURED_1 : ModSounds.DWARVEN_CENTURION_INJURED_2;
   }

   protected class_1330 method_5963() {
      return new class_1330(this) {
         private static final float BODY_TURN_SPEED = 8.0F;
         private static final float HEAD_TURN_SPEED = 12.0F;

         public void method_6224() {
            if (DwemerCenturionEntity.this.isSwinging()) {
               float desired = DwemerCenturionEntity.this.method_36454();
               float newBody = class_3532.method_15388(DwemerCenturionEntity.this.field_6283, desired, 8.0F);
               float newHead = class_3532.method_15388(DwemerCenturionEntity.this.field_6241, desired, 12.0F);
               DwemerCenturionEntity.this.method_5636(newBody);
               DwemerCenturionEntity.this.method_5847(newHead);
            } else {
               super.method_6224();
            }
         }
      };
   }
}
