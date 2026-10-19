package cn.leolezury.eternalstarlight.common.entity.living;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import java.util.Arrays;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.AbstractGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class GrimstoneGolem extends AbstractGolem {
   public final AnimationState raiseArmsAnimationState = new AnimationState();
   public final AnimationState displayAnimationState = new AnimationState();
   public final AnimationState lowerArmsAnimationState = new AnimationState();

   public GrimstoneGolem(EntityType<? extends GrimstoneGolem> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.grimstoneGolem.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.grimstoneGolem.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.0) {
         public boolean canUse() {
            return super.canUse() && GrimstoneGolem.this.getMainHandItem().isEmpty();
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse() && GrimstoneGolem.this.getMainHandItem().isEmpty();
         }
      });
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0) {
         public boolean canUse() {
            return super.canUse() && GrimstoneGolem.this.getMainHandItem().isEmpty();
         }

         public boolean canContinueToUse() {
            return super.canContinueToUse() && GrimstoneGolem.this.getMainHandItem().isEmpty();
         }
      });
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
   }

   public void stopAllAnimStates() {
      this.raiseArmsAnimationState.stop();
      this.displayAnimationState.stop();
      this.lowerArmsAnimationState.stop();
   }

   public void handleEntityEvent(byte b) {
      switch (b) {
         case 100:
            this.stopAllAnimStates();
            this.raiseArmsAnimationState.start(this.tickCount);
            break;
         case 101:
            this.stopAllAnimStates();
            this.lowerArmsAnimationState.start(this.tickCount);
            break;
         default:
            super.handleEntityEvent(b);
      }
   }

   protected InteractionResult mobInteract(Player player, InteractionHand interactionHand) {
      if (this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && !player.getItemInHand(interactionHand).isEmpty()) {
         this.setItemInHand(InteractionHand.MAIN_HAND, player.getItemInHand(interactionHand).copy());
         Arrays.fill(this.handDropChances, 0.0F);
         if (!player.hasInfiniteMaterials()) {
            player.setItemInHand(interactionHand, ItemStack.EMPTY);
         }

         if (!player.level().isClientSide) {
            player.level().broadcastEntityEvent(this, (byte)100);
         }

         return InteractionResult.sidedSuccess(player.level().isClientSide);
      } else if (!this.getItemInHand(InteractionHand.MAIN_HAND).isEmpty() && player.getItemInHand(interactionHand).isEmpty()) {
         player.setItemInHand(interactionHand, this.getItemInHand(InteractionHand.MAIN_HAND).copy());
         this.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
         if (!player.level().isClientSide) {
            player.level().broadcastEntityEvent(this, (byte)101);
         }

         return InteractionResult.sidedSuccess(player.level().isClientSide);
      } else {
         return super.mobInteract(player, interactionHand);
      }
   }

   protected void dropCustomDeathLoot(ServerLevel serverLevel, DamageSource damageSource, boolean bl) {
      super.dropCustomDeathLoot(serverLevel, damageSource, bl);
      if (!this.getMainHandItem().isEmpty() && this.handDropChances[0] == 0.0F && this.handDropChances[1] == 0.0F) {
         this.spawnAtLocation(this.getMainHandItem().copy());
      }
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide) {
         if (this.raiseArmsAnimationState.isStarted() && (float)this.raiseArmsAnimationState.getAccumulatedTime() / 1000.0F * 20.0F > 20.0F) {
            this.raiseArmsAnimationState.stop();
            this.displayAnimationState.start(this.tickCount);
         }

         if (!this.getMainHandItem().isEmpty() && !this.raiseArmsAnimationState.isStarted()) {
            this.displayAnimationState.startIfStopped(this.tickCount);
         }
      }
   }
}
