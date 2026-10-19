package mebahelcreaturesdwarven.block.entity.custom;

import mebahelcreaturesdwarven.block.entity.ModBlockEntities;
import mebahelcreaturesdwarven.sound.ModSounds;
import mebahelcreaturesdwarven.util.ClientSoundBridge;
import net.minecraft.class_1937;
import net.minecraft.class_2338;
import net.minecraft.class_2586;
import net.minecraft.class_2680;
import net.minecraft.class_2960;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.Animation.LoopType;

public class DwemerMetalPipeGearBlockEntity extends class_2586 implements GeoBlockEntity {
   private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);
   private boolean soundStarted = false;

   public DwemerMetalPipeGearBlockEntity(class_2338 pos, class_2680 state) {
      super(ModBlockEntities.DWARVEN_METAL_PIPE_GEAR, pos, state);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 0, this::predicate));
   }

   private <E extends GeoAnimatable> PlayState predicate(AnimationState<E> state) {
      state.getController().setAnimation(RawAnimation.begin().then("idle", LoopType.LOOP));
      return PlayState.CONTINUE;
   }

   public static void clientTick(class_1937 world, class_2338 pos, class_2680 state, DwemerMetalPipeGearBlockEntity be) {
      if (world.field_9236) {
         if (!be.soundStarted) {
            be.soundStarted = true;
            class_2960 soundId = ModSounds.DWARVEN_GEAR_MULTIPLE.method_14833();
            float volume = 0.3F;
            float pitch = 1.15F;
            ClientSoundBridge.startLoopingBlockSound(world.method_27983(), pos, soundId, volume, pitch);
         }
      }
   }

   public void method_11012() {
      super.method_11012();
      if (this.field_11863 != null && this.field_11863.field_9236) {
         ClientSoundBridge.stopLoopingBlockSound(this.field_11863.method_27983(), this.field_11867);
      }
   }
}
