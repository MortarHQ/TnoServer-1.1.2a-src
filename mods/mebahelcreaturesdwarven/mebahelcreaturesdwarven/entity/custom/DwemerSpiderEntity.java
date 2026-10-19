package mebahelcreaturesdwarven.entity.custom;

import java.util.ArrayList;
import java.util.List;
import mebahelcreaturesdwarven.entity.ai.CustomRevengeGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_spider.DwarvenSpiderMeleeAttackGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_spider.DwarvenSpiderWanderFarGoal;
import mebahelcreaturesdwarven.entity.ai.dwarven_spider.MineOreGoal;
import mebahelcreaturesdwarven.sound.ModSounds;
import net.minecraft.class_1266;
import net.minecraft.class_1267;
import net.minecraft.class_1282;
import net.minecraft.class_1299;
import net.minecraft.class_13;
import net.minecraft.class_1315;
import net.minecraft.class_1330;
import net.minecraft.class_1347;
import net.minecraft.class_1376;
import net.minecraft.class_14;
import net.minecraft.class_1400;
import net.minecraft.class_1408;
import net.minecraft.class_1409;
import net.minecraft.class_1439;
import net.minecraft.class_1588;
import net.minecraft.class_1642;
import net.minecraft.class_1646;
import net.minecraft.class_1657;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_2248;
import net.minecraft.class_2338;
import net.minecraft.class_2487;
import net.minecraft.class_2680;
import net.minecraft.class_2940;
import net.minecraft.class_2943;
import net.minecraft.class_2945;
import net.minecraft.class_3218;
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

public class DwemerSpiderEntity extends DwemerEntity implements GeoEntity {
   private final List<class_1799> minedResources = new ArrayList<>();
   private double rand;
   private static final class_2940<Integer> DATA_ID_TYPE_VARIANT = class_2945.method_12791(DwemerSpiderEntity.class, class_2943.field_13327);
   private final AnimatableInstanceCache factory = new SingletonAnimatableInstanceCache(this);

   public DwemerSpiderEntity(class_1299<? extends DwemerEntity> entityType, class_1937 world) {
      super(entityType, world);
      this.field_6191 = -this.method_5970();
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.factory;
   }

   @Override
   public boolean shouldDespawnInPeaceful() {
      return this.method_37908().method_8407() == class_1267.field_5801;
   }

   public void method_5773() {
      super.method_5773();
      if (this.shouldDespawnInPeaceful()) {
         this.method_5650(class_5529.field_26999);
      }

      if ((Integer)this.field_6011.method_12789(MINING_COOLDOWN) > 0) {
         this.field_6011.method_12778(MINING_COOLDOWN, (Integer)this.field_6011.method_12789(MINING_COOLDOWN) - 1);
      }
   }

   @Override
   protected void method_5693(class_9222 builder) {
      super.method_5693(builder);
      builder.method_56912(DATA_ID_TYPE_VARIANT, 0);
   }

   public static class_5133 setAttributes() {
      return class_1588.method_26828()
         .method_26868(class_5134.field_23717, 20.0)
         .method_26868(class_5134.field_23716, 35.0)
         .method_26868(class_5134.field_23719, 0.3)
         .method_26868(class_5134.field_23724, 4.0)
         .method_26868(class_5134.field_23721, 6.0)
         .method_26868(class_5134.field_23718, 0.6F)
         .method_26868(class_5134.field_23722, 0.15F);
   }

   protected void method_5959() {
      this.field_6201.method_6277(1, new class_1347(this));
      this.field_6201.method_6277(2, new DwarvenSpiderMeleeAttackGoal(this, 1.0, 0));
      this.field_6201.method_6277(3, new MineOreGoal(this));
      this.field_6201.method_6277(5, new DwarvenSpiderWanderFarGoal(this, 1.0, 1.0F));
      this.field_6201.method_6277(7, new class_1376(this));
      this.field_6185.method_6277(1, new CustomRevengeGoal(this, DwemerEntity.class));
      this.field_6185.method_6277(2, new class_1400(this, class_1657.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1646.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1439.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_3763.class, true));
      this.field_6185.method_6277(3, new class_1400(this, class_1642.class, true));
   }

   public void collectDroppedItems(class_2338 pos) {
      class_1937 world = this.method_37908();
      class_2680 blockState = world.method_8320(pos);
      if (!world.field_9236) {
         if (world instanceof class_3218 sw) {
            for (class_1799 drop : class_2248.method_9562(blockState, sw, pos, null)) {
               this.minedResources.add(drop.method_7972());
            }
         }
      }
   }

   @Override
   public void method_6078(class_1282 source) {
      super.method_6078(source);

      for (class_1799 stack : this.minedResources) {
         this.method_5775(stack);
      }

      this.minedResources.clear();
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> event) {
      if (event.isMoving() && !this.isSwinging()) {
         event.getController().setAnimation(RawAnimation.begin().then("walk", LoopType.LOOP));
         this.setCurrentAnimation("walk");
         return PlayState.CONTINUE;
      }

      if ((Boolean)this.method_5841().method_12789(IS_MINING) && !this.isSwinging()) {
         event.getController().setAnimation(RawAnimation.begin().then("mine", LoopType.LOOP));
         this.setCurrentAnimation("mine");
         return PlayState.CONTINUE;
      }

      if (!this.isSwinging()) {
         event.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
         this.setCurrentAnimation("idle");
      }

      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "controller", 4, this::predicate)
            .setSoundKeyframeHandler(
               state -> {
                  if (this.method_37908().field_9236) {
                     this.method_37908()
                        .method_43128(
                           null, this.method_23317(), this.method_23318(), this.method_23321(), ModSounds.DWARVEN_SPIDER_MINING, this.method_5634(), 0.5F, 0.6F
                        );
                  }
               }
            )
      );
      controllers.add(
         new AnimationController(this, "attacking", 4, state -> PlayState.CONTINUE)
            .triggerableAnim("attack1", RawAnimation.begin().then("attack1", LoopType.PLAY_ONCE))
            .triggerableAnim("attack2", RawAnimation.begin().then("attack2", LoopType.PLAY_ONCE))
            .triggerableAnim("attack3", RawAnimation.begin().then("attack3", LoopType.PLAY_ONCE))
      );
   }

   @Override
   protected class_3414 method_6011(class_1282 source) {
      this.rand = Math.random();
      return this.rand < 0.5 ? ModSounds.DWARVEN_SPIDER_INJURED_1 : ModSounds.DWARVEN_SPIDER_INJURED_1;
   }

   @Override
   protected class_3414 method_6002() {
      this.rand = Math.random();
      return this.rand < 0.5 ? ModSounds.DWARVEN_SPIDER_DEATH_1 : ModSounds.DWARVEN_SPIDER_DEATH_1;
   }

   @Override
   protected void method_6013(class_1282 source) {
      class_3414 hurtSound = this.method_6011(source);
      if (hurtSound != null) {
         this.method_5783(hurtSound, 0.45F, 1.0F);
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
      return ModSounds.DWARVEN_SPIDER_AMBIENT_1;
   }

   @Override
   public void method_5966() {
      class_3414 soundEvent = this.method_5994();
      if (soundEvent != null) {
         this.method_5783(soundEvent, 0.35F, 1.0F);
      }
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

   @Nullable
   public class_1315 method_5943(class_5425 world, class_1266 difficulty, class_3730 spawnReason, @Nullable class_1315 entityData) {
      return super.method_5943(world, difficulty, spawnReason, entityData);
   }

   @Override
   protected void method_5712(class_2338 pos, class_2680 state) {
      this.method_5783(ModSounds.DWARVEN_SPIDER_WALK, 0.15F, 1.0F);
   }

   protected class_1330 method_5963() {
      return new class_1330(this) {
         private static final float BODY_TURN_SPEED = 8.0F;
         private static final float HEAD_TURN_SPEED = 12.0F;

         public void method_6224() {
            if (DwemerSpiderEntity.this.isSwinging()) {
               float desired = DwemerSpiderEntity.this.method_36454();
               float newBody = class_3532.method_15388(DwemerSpiderEntity.this.field_6283, desired, 8.0F);
               float newHead = class_3532.method_15388(DwemerSpiderEntity.this.field_6241, desired, 12.0F);
               DwemerSpiderEntity.this.method_5636(newBody);
               DwemerSpiderEntity.this.method_5847(newHead);
            } else {
               super.method_6224();
            }
         }
      };
   }
}
