package net.unusual.block_factorys_bosses.block.entity;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.unusual.block_factorys_bosses.block.HugeDoorBlock;
import net.unusual.block_factorys_bosses.client.camera.client.CinematicAnimationController;
import net.unusual.block_factorys_bosses.geckolib.util.SoundKeyframePlayer;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.SingletonGeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.AnimationState;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.animation.AnimationController.CustomKeyframeHandler;
import software.bernie.geckolib.animation.AnimationController.State;
import software.bernie.geckolib.animation.keyframe.event.CustomInstructionKeyframeEvent;
import software.bernie.geckolib.util.GeckoLibUtil;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class HugeDoorBlockEntity extends BlockEntity implements GeoBlockEntity, CustomKeyframeHandler<HugeDoorBlockEntity> {
   private static final RawAnimation OPENING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.opening");
   private static final RawAnimation OPEN_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.open");
   private static final RawAnimation CLOSING_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.closing");
   private static final RawAnimation CLOSED_ANIM = RawAnimation.begin().thenPlayAndHold("animation.bf_bosses.underworld_arena_door.closed");
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private long startedMovingAtGameTime = -1L;
   private long startedUnlockingAtGameTime = -1L;

   public HugeDoorBlockEntity(BlockEntityType<? extends HugeDoorBlockEntity> type, BlockPos pos, BlockState blockState) {
      super(type, pos, blockState);
      SingletonGeoAnimatable.registerSyncedAnimatable(this);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      AnimationController<HugeDoorBlockEntity> controller = new CinematicAnimationController(this, "main_controller", this::getAnimationState)
         .setSoundKeyframeHandler(new SoundKeyframePlayer())
         .setCustomInstructionKeyframeHandler(this);
      controllers.add(controller);
   }

   public void handle(CustomInstructionKeyframeEvent<HugeDoorBlockEntity> event) {
      String instruction = event.getKeyframeData().getInstructions();
      switch (instruction) {
         case "use_unlocking_texture;":
            assert this.getLevel() != null;
            this.startedUnlockingAtGameTime = this.getLevel().getGameTime();
            break;
         case "use_normal_texture;":
            this.startedUnlockingAtGameTime = -1L;
      }
   }

   private PlayState getAnimationState(AnimationState<HugeDoorBlockEntity> state) {
      BlockState blockState = this.getBlockState();
      Boolean isOpen = (Boolean)blockState.getValue(HugeDoorBlock.OPEN);
      if (!(Boolean)blockState.getValue(HugeDoorBlock.MOVING)) {
         return state.getAnimationTick() <= 0.0 ? state.setAndContinue(isOpen ? OPEN_ANIM : CLOSED_ANIM) : PlayState.CONTINUE;
      } else {
         return state.setAndContinue(isOpen ? CLOSING_ANIM : OPENING_ANIM);
      }
   }

   public boolean isPlayingOpeningAnimation() {
      AnimationController<GeoAnimatable> controller = (AnimationController<GeoAnimatable>)this.getAnimatableInstanceCache()
         .getManagerForId(this.getBlockPos().asLong())
         .getAnimationControllers()
         .get("main_controller");
      return controller == null ? false : controller.getAnimationState() == State.RUNNING && controller.getCurrentRawAnimation() == OPENING_ANIM;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean isFinishedMoving(int duration) {
      assert this.getLevel() != null;
      return this.getLevel().getGameTime() - this.startedMovingAtGameTime >= duration;
   }

   public boolean isStartedMoving() {
      return this.startedMovingAtGameTime != -1L;
   }

   public void setStartedMoving() {
      assert this.getLevel() != null;
      this.startedMovingAtGameTime = this.getLevel().getGameTime();
   }

   public int getUnlockingTextureFrame() {
      if (this.startedUnlockingAtGameTime < 0L) {
         return -1;
      }

      assert this.getLevel() != null;
      return (int)(this.getLevel().getGameTime() - this.startedUnlockingAtGameTime);
   }
}
