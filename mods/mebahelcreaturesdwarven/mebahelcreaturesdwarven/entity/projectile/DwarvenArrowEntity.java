package mebahelcreaturesdwarven.entity.projectile;

import mebahelcreaturesdwarven.item.ModItems;
import net.minecraft.class_1299;
import net.minecraft.class_1309;
import net.minecraft.class_1657;
import net.minecraft.class_1667;
import net.minecraft.class_1799;
import net.minecraft.class_1937;
import net.minecraft.class_1665.class_1666;
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

public class DwarvenArrowEntity extends class_1667 implements GeoEntity {
   private static final double EBONY_BONUS_DAMAGE = 2.0;
   private boolean ebonyDamageApplied = false;
   private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

   public DwarvenArrowEntity(class_1299<? extends DwarvenArrowEntity> type, class_1937 world) {
      super(type, world);
      this.applyEbonyBonusOnce();
   }

   public DwarvenArrowEntity(class_1299<? extends DwarvenArrowEntity> type, class_1937 world, class_1309 owner) {
      super(type, world);
      this.method_7432(owner);
      this.method_5814(owner.method_23317(), owner.method_23320() - 0.1, owner.method_23321());
      if (owner instanceof class_1657) {
         this.field_7572 = class_1666.field_7593;
      }

      this.applyEbonyBonusOnce();
   }

   private void applyEbonyBonusOnce() {
      if (!this.method_37908().field_9236 && !this.ebonyDamageApplied) {
         this.method_7438(this.method_7448() + 2.0);
         this.ebonyDamageApplied = true;
      }
   }

   public void method_5773() {
      super.method_5773();
      this.applyEbonyBonusOnce();
   }

   protected class_1799 method_57314() {
      return new class_1799(ModItems.DWARVEN_ARROW);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
      state.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
      return PlayState.CONTINUE;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }
}
