package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper.WeatherState;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;

public final class CopperGolemPressButtonTask extends Behavior<CopperGolemEntity> {
   private static final float WITHING_DISTANCE = 2.0F;
   private int pressButtonTicks = 0;
   private int minPressButtonTick = 0;
   private int maxPressButtonTicks = 0;
   private boolean wasButtonPressed = false;
   private int heightDifference = 0;

   public CopperGolemPressButtonTask() {
      super(
         Map.of(
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(),
            MemoryStatus.VALUE_PRESENT,
            (MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(),
            MemoryStatus.VALUE_ABSENT
         ),
         100
      );
   }

   protected boolean checkExtraStartConditions(ServerLevel world, CopperGolemEntity copperGolem) {
      return copperGolem.getButtonPos() != null
         && copperGolem.isButtonValidToBePressed(copperGolem.getButtonPos().pos())
         && copperGolem.getButtonPos().pos().closerToCenterThan(copperGolem.position(), 2.0)
         && !copperGolem.getNavigation().isInProgress();
   }

   protected void start(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      this.pressButtonTicks = 0;
      this.wasButtonPressed = false;
      GlobalPos buttonPos = copperGolem.getButtonPos();
      if (buttonPos != null) {
         this.heightDifference = buttonPos.pos().getY() - copperGolem.blockPosition().getY();
         if (this.heightDifference >= 1) {
            this.maxPressButtonTicks = CopperGolemAnimations.PRESS_BUTTON_UP.get().lengthInTicks(copperGolem.getAnimationSpeedModifier());
         } else {
            this.maxPressButtonTicks = CopperGolemAnimations.PRESS_BUTTON_DOWN.get().lengthInTicks(copperGolem.getAnimationSpeedModifier());
         }

         this.minPressButtonTick = copperGolem.getRandom()
            .nextIntBetweenInclusive((int)(this.maxPressButtonTicks * 0.4), (int)(this.maxPressButtonTicks * 0.6));
      }
   }

   protected boolean canStillUse(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      return this.pressButtonTicks <= this.maxPressButtonTicks && !copperGolem.isOxidized();
   }

   protected void tick(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      this.pressButtonTicks++;
      if (this.heightDifference >= 1) {
         copperGolem.startPressButtonUpAnimation();
      } else {
         copperGolem.startPressButtonDownAnimation();
      }

      if (!copperGolem.isWaxed() && copperGolem.getOxidationLevel().ordinal() >= WeatherState.WEATHERED.ordinal() && copperGolem.getRandom().nextFloat() > 0.99
         )
       {
         copperGolem.setOxidationLevel(WeatherState.OXIDIZED);
      }

      if (this.pressButtonTicks >= this.minPressButtonTick) {
         if (!this.wasButtonPressed) {
            GlobalPos buttonPos = copperGolem.getButtonPos();
            if (buttonPos != null) {
               this.wasButtonPressed = this.tryToPressButton(copperGolem, buttonPos.pos());
            }
         }
      }
   }

   protected void stop(ServerLevel world, CopperGolemEntity copperGolem, long time) {
      copperGolem.setEntityPose(FriendsAndFoesEntityPose.IDLE);
      copperGolem.getBrain().eraseMemory((MemoryModuleType)FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
      CopperGolemBrain.setPressButtonCooldown(copperGolem);
   }

   private boolean tryToPressButton(CopperGolemEntity copperGolem, BlockPos buttonPos) {
      BlockState buttonBlockState = this.getButtonBlockState(copperGolem, buttonPos);
      if (buttonBlockState != null && !(Boolean)buttonBlockState.getValue(ButtonBlock.POWERED)) {
         ButtonBlock buttonBlock = (ButtonBlock)buttonBlockState.getBlock();
         buttonBlock.press(buttonBlockState, copperGolem.level(), buttonPos, null);
         copperGolem.level().playSound(null, buttonPos, buttonBlock.getSound(true), SoundSource.BLOCKS, 0.3F, 0.6F);
         copperGolem.level().gameEvent(copperGolem, GameEvent.BLOCK_ACTIVATE, buttonPos);
         return true;
      } else {
         return false;
      }
   }

   @Nullable
   private BlockState getButtonBlockState(CopperGolemEntity copperGolem, BlockPos buttonBlockPos) {
      ServerLevel serverWorld = (ServerLevel)copperGolem.level();
      BlockState blockState = serverWorld.getBlockState(buttonBlockPos);
      return !(blockState.getBlock() instanceof ButtonBlock) ? null : blockState;
   }
}
