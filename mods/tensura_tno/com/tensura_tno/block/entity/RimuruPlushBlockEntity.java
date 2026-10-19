package com.tensura_tno.block.entity;

import com.tensura_tno.block.RimuruPlushBlock;
import com.tensura_tno.registry.TensuraTNOBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import software.bernie.geckolib.animatable.GeoBlockEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class RimuruPlushBlockEntity extends BlockEntity implements GeoBlockEntity {
   private final RawAnimation standing;
   private final RawAnimation sitting;
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public RimuruPlushBlockEntity(BlockPos pos, BlockState state) {
      this((BlockEntityType<?>)TensuraTNOBlockEntities.RIMURU_PLUSH.get(), pos, state, "animation.rimuru_tempest.");
   }

   public RimuruPlushBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state, String animationPrefix) {
      super(type, pos, state);
      this.standing = RawAnimation.begin().thenLoop(animationPrefix + "idle");
      this.sitting = RawAnimation.begin().thenLoop(animationPrefix + "sit_idle");
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(
            this, "pose", 0, animation -> animation.setAndContinue(this.getBlockState().getValue(RimuruPlushBlock.SITTING) ? this.sitting : this.standing)
         )
      );
   }

   public double getBoneResetTime() {
      return 0.0;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
