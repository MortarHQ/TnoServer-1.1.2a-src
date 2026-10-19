package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.block.OnUseOxidizable;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesSoundEvents;
import com.faboslav.friendsandfoes.common.util.CopperGolemBuildPatternPredicates;
import java.util.ArrayList;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CarvedPumpkinBlock;
import net.minecraft.world.level.block.ChangeOverTimeBlock;
import net.minecraft.world.level.block.LightningRodBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.WeatheringCopperFullBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.pattern.BlockInWorld;
import net.minecraft.world.level.block.state.pattern.BlockPattern;
import net.minecraft.world.level.block.state.pattern.BlockPatternBuilder;
import net.minecraft.world.level.block.state.pattern.BlockPattern.BlockPatternMatch;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LightningRodBlock.class, priority = 1001)
public abstract class LightningRodBlockMixin extends LightningRodBlockBlockMixin {
   @Nullable
   private BlockPattern friendsandfoes_copperGolemPattern;

   @Inject(method = "onPlace", at = @At("HEAD"))
   private void friendsandfoes_onBlockAdded(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean notify, CallbackInfo ci) {
      if (!oldState.is(state.getBlock())) {
         this.friendsandfoes_tryToSpawnCopperGolem(world, pos);
      }
   }

   private void friendsandfoes_tryToSpawnCopperGolem(Level world, BlockPos pos) {
      if (FriendsAndFoes.getConfig().enableCopperGolem) {
         BlockPatternMatch patternSearchResult = this.friendsandfoes_getCopperGolemPattern().find(world, pos);
         if (patternSearchResult != null) {
            BlockState lightningRodBlockState = patternSearchResult.getBlock(0, 0, 0).getState();
            BlockState headBlockState = patternSearchResult.getBlock(0, 1, 0).getState();
            BlockState bodyBlockState = patternSearchResult.getBlock(0, 2, 0).getState();
            WeatherState lightningRodOxidationLevel;
            if (lightningRodBlockState.is((Block)FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())) {
               lightningRodOxidationLevel = WeatherState.UNAFFECTED;
            } else if (lightningRodBlockState.is((Block)FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())) {
               lightningRodOxidationLevel = WeatherState.WEATHERED;
            } else if (lightningRodBlockState.is((Block)FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())) {
               lightningRodOxidationLevel = WeatherState.EXPOSED;
            } else if (lightningRodBlockState.is((Block)FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get())) {
               lightningRodOxidationLevel = WeatherState.OXIDIZED;
            } else {
               lightningRodOxidationLevel = (WeatherState)((WeatheringCopper)lightningRodBlockState.getBlock()).getAge();
            }

            WeatherState bodyOxidationLevel;
            if (bodyBlockState.is(Blocks.WAXED_COPPER_BLOCK)) {
               bodyOxidationLevel = WeatherState.UNAFFECTED;
            } else if (bodyBlockState.is(Blocks.WAXED_WEATHERED_COPPER)) {
               bodyOxidationLevel = WeatherState.WEATHERED;
            } else if (bodyBlockState.is(Blocks.WAXED_EXPOSED_COPPER)) {
               bodyOxidationLevel = WeatherState.EXPOSED;
            } else if (bodyBlockState.is(Blocks.WAXED_OXIDIZED_COPPER)) {
               bodyOxidationLevel = WeatherState.OXIDIZED;
            } else {
               bodyOxidationLevel = ((WeatheringCopperFullBlock)bodyBlockState.getBlock()).getAge();
            }

            if (lightningRodOxidationLevel == bodyOxidationLevel) {
               CarvedPumpkinBlock.clearPatternBlocks(world, patternSearchResult);
               BlockPos cachedBlockPosition = patternSearchResult.getBlock(0, 2, 0).getPos();
               float copperGolemYaw = ((Direction)headBlockState.getValue(CarvedPumpkinBlock.FACING)).toYRot();
               CopperGolemEntity copperGolem = (CopperGolemEntity)((EntityType)FriendsAndFoesEntityTypes.COPPER_GOLEM.get()).create(world);
               copperGolem.setPos(cachedBlockPosition.getX() + 0.5, cachedBlockPosition.getY() + 0.05, cachedBlockPosition.getZ() + 0.5);
               copperGolem.setSpawnYaw(copperGolemYaw);
               copperGolem.setOxidationLevel(bodyOxidationLevel);
               if (lightningRodOxidationLevel == WeatherState.OXIDIZED) {
                  ArrayList<FriendsAndFoesEntityPose> possiblePoses = new ArrayList<FriendsAndFoesEntityPose>() {
                     {
                        this.add(FriendsAndFoesEntityPose.SPIN_HEAD);
                        this.add(FriendsAndFoesEntityPose.PRESS_BUTTON_UP);
                        this.add(FriendsAndFoesEntityPose.PRESS_BUTTON_DOWN);
                     }
                  };
                  int randomPoseIndex = copperGolem.getRandom().nextInt(possiblePoses.size());
                  FriendsAndFoesEntityPose randomPose = possiblePoses.get(randomPoseIndex);
                  copperGolem.setEntityPose(randomPose);
                  AnimationHolder animation = copperGolem.getAnimationByPose();
                  if (animation != null) {
                     int keyFrameAnimationLengthInTicks = animation.get().lengthInTicks();
                     int randomKeyframeAnimationTick = copperGolem.getRandom()
                        .nextIntBetweenInclusive(keyFrameAnimationLengthInTicks / 6, keyFrameAnimationLengthInTicks - keyFrameAnimationLengthInTicks / 6);
                     copperGolem.setCurrentAnimationTick(randomKeyframeAnimationTick);
                  }
               } else {
                  boolean isHeadBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(headBlockState);
                  boolean isBodyBlockWaxed = this.friendsandfoes_isCopperBlockWaxed(bodyBlockState);
                  boolean isWaxed = isHeadBlockWaxed && isBodyBlockWaxed;
                  copperGolem.setIsWaxed(isWaxed);
               }

               CopperGolemBrain.setSpinHeadCooldown(copperGolem);
               CopperGolemBrain.setPressButtonCooldown(copperGolem);
               world.addFreshEntity(copperGolem);
               copperGolem.playSound((SoundEvent)FriendsAndFoesSoundEvents.ENTITY_COPPER_GOLEM_SPAWN.get(), 1.0F, copperGolem.getVoicePitch());

               for (ServerPlayer serverPlayerEntity : world.getEntitiesOfClass(ServerPlayer.class, copperGolem.getBoundingBox().inflate(5.0))) {
                  CriteriaTriggers.SUMMONED_ENTITY.trigger(serverPlayerEntity, copperGolem);
               }

               CarvedPumpkinBlock.updatePatternBlocks(world, patternSearchResult);
            }
         }
      }
   }

   private BlockPattern friendsandfoes_getCopperGolemPattern() {
      if (this.friendsandfoes_copperGolemPattern == null) {
         this.friendsandfoes_copperGolemPattern = BlockPatternBuilder.start()
            .aisle(new String[]{"|", "^", "#"})
            .where('|', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_LIGHTNING_ROD_PREDICATE))
            .where('^', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_GOLEM_HEAD_PREDICATE))
            .where('#', BlockInWorld.hasState(CopperGolemBuildPatternPredicates.IS_COPPER_GOLEM_BODY_PREDICATE))
            .build();
      }

      return this.friendsandfoes_copperGolemPattern;
   }

   private boolean friendsandfoes_isCopperBlockWaxed(BlockState blockState) {
      return blockState.is(Blocks.WAXED_COPPER_BLOCK)
         || blockState.is(Blocks.WAXED_WEATHERED_COPPER)
         || blockState.is(Blocks.WAXED_EXPOSED_COPPER)
         || blockState.is(Blocks.WAXED_OXIDIZED_COPPER)
         || blockState.is((Block)FriendsAndFoesBlocks.WAXED_LIGHTNING_ROD.get())
         || blockState.is((Block)FriendsAndFoesBlocks.WAXED_WEATHERED_LIGHTNING_ROD.get())
         || blockState.is((Block)FriendsAndFoesBlocks.WAXED_EXPOSED_LIGHTNING_ROD.get())
         || blockState.is((Block)FriendsAndFoesBlocks.WAXED_OXIDIZED_LIGHTNING_ROD.get());
   }

   @Override
   public void friendsandfoes_hasRandomTicks(BlockState state, CallbackInfoReturnable<Boolean> cir) {
      if (!FriendsAndFoes.getConfig().enableLightningRodOxidation) {
         cir.setReturnValue(false);
      } else {
         cir.setReturnValue(true);
      }
   }

   @Override
   public void friendsandfoes_randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random, CallbackInfo ci) {
      if (FriendsAndFoes.getConfig().enableLightningRodOxidation) {
         ((ChangeOverTimeBlock)this).changeOverTime(state, world, pos, random);
      }

      ci.cancel();
   }

   @Override
   public void friendsandfoes_onUse(
      BlockState state, Level world, BlockPos pos, Player player, BlockHitResult hit, CallbackInfoReturnable<InteractionResult> cir
   ) {
      InteractionResult actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);
      if (actionResult.consumesAction()) {
         cir.setReturnValue(actionResult);
      }
   }
}
