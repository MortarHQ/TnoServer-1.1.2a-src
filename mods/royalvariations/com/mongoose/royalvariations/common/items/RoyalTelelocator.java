package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.client.particles.EndSplashParticleOption;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.items.component.TelelocatorTracker;
import com.mongoose.royalvariations.init.RVDataComponents;
import com.mongoose.royalvariations.utils.ItemHelper;
import com.mongoose.royalvariations.utils.MathHelper;
import com.mongoose.royalvariations.utils.ServerParticleUtil;
import java.util.List;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class RoyalTelelocator extends Item {
   public RoyalTelelocator() {
      super(new Properties().craftRemainder((Item)RVItems.EMPTY_ROYAL_TELELOCATOR.get()).stacksTo(1));
   }

   public boolean isFoil(ItemStack pStack) {
      return hasBlock(pStack);
   }

   public static boolean hasBlock(ItemStack stack) {
      if (!stack.has(RVDataComponents.TELELOCATOR_TRACKER)) {
         return false;
      }

      TelelocatorTracker tracker = (TelelocatorTracker)stack.get(RVDataComponents.TELELOCATOR_TRACKER);
      return tracker != null && tracker.getTarget().isPresent();
   }

   @Nullable
   public static GlobalPos getPosition(ItemStack stack) {
      if (stack.has(RVDataComponents.TELELOCATOR_TRACKER)) {
         TelelocatorTracker tracker = (TelelocatorTracker)stack.get(RVDataComponents.TELELOCATOR_TRACKER);
         if (tracker != null && tracker.getTarget().isPresent()) {
            return tracker.getTarget().get();
         }
      }

      return null;
   }

   @Nullable
   public static BlockPos getBlockPos(ItemStack itemStack) {
      GlobalPos globalPos = getPosition(itemStack);
      return globalPos != null ? globalPos.pos() : null;
   }

   public static boolean isSameDimension(LivingEntity livingEntity, ItemStack stack) {
      GlobalPos globalPos = getPosition(stack);
      return globalPos != null && globalPos.dimension() == livingEntity.level().dimension();
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      Level level = context.getLevel();
      InteractionHand hand = context.getHand();
      BlockPos blockpos = context.getClickedPos();
      BlockState blockState = level.getBlockState(blockpos);
      if (player != null) {
         ItemStack held = player.getItemInHand(hand);
         if (!held.isEmpty() && held.getItem() instanceof RoyalTelelocator && !hasBlock(held)) {
            player.swing(hand);
            TelelocatorTracker tracker = new TelelocatorTracker(Optional.of(GlobalPos.of(level.dimension(), blockpos)));
            player.playSound(SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, 1.0F, 1.0F);
            player.level()
               .playLocalSound(blockpos.getX(), blockpos.getY(), blockpos.getZ(), SoundEvents.RESPAWN_ANCHOR_SET_SPAWN, SoundSource.PLAYERS, 1.0F, 0.45F, false);
            held.set((DataComponentType)RVDataComponents.TELELOCATOR_TRACKER.get(), tracker);
            if (player.level() instanceof ServerLevel serverLevel) {
               ServerParticleUtil.blockBreakParticles((ParticleOptions)RVParticleTypes.END_POOF.get(), blockpos, blockState, serverLevel);
            }

            return InteractionResult.sidedSuccess(player.level().isClientSide);
         }
      }

      return InteractionResult.PASS;
   }

   @NotNull
   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
      ItemStack itemstack = playerIn.getItemInHand(handIn);
      if (hasBlock(itemstack)) {
         if (isSameDimension(playerIn, itemstack)) {
            playerIn.startUsingItem(handIn);
            return InteractionResultHolder.success(itemstack);
         }

         playerIn.displayClientMessage(Component.translatable("info.royalvariations.telelocator.outDim"), true);
      }

      return InteractionResultHolder.pass(itemstack);
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntity, ItemStack stack, int tick) {
      for (int i = 0; i < 2; i++) {
         worldIn.addParticle(
            ParticleTypes.PORTAL,
            livingEntity.getRandomX(0.5),
            livingEntity.getRandomY() - 0.25,
            livingEntity.getRandomZ(0.5),
            (worldIn.random.nextDouble() - 0.5) * 2.0,
            -worldIn.random.nextDouble(),
            (worldIn.random.nextDouble() - 0.5) * 2.0
         );
      }

      super.onUseTick(worldIn, livingEntity, stack, tick);
   }

   public ItemStack finishUsingItem(ItemStack p_41348_, Level p_41349_, LivingEntity p_41350_) {
      super.finishUsingItem(p_41348_, p_41349_, p_41350_);
      if (getPosition(p_41348_) != null) {
         GlobalPos globalPos = getPosition(p_41348_);
         if (globalPos != null && globalPos.dimension() == p_41350_.level().dimension()) {
            Vec3 vec3 = globalPos.pos().getCenter();
            p_41350_.teleportTo(vec3.x, vec3.y + 1.0, vec3.z);
            p_41349_.playSound(
               null, p_41350_.getX(), p_41350_.getY(), p_41350_.getZ(), SoundEvents.RESPAWN_ANCHOR_DEPLETE, p_41350_.getSoundSource(), 1.0F, 1.0F
            );
            p_41349_.playLocalSound(
               p_41350_.getX(),
               p_41350_.getY(),
               p_41350_.getZ(),
               (SoundEvent)SoundEvents.RESPAWN_ANCHOR_DEPLETE.value(),
               p_41350_.getSoundSource(),
               1.0F,
               1.0F,
               false
            );
            if (p_41349_ instanceof ServerLevel serverLevel) {
               for (int i = 0; i < 20; i++) {
                  double d0 = p_41349_.getRandom().nextGaussian() * 0.02;
                  double d1 = p_41349_.getRandom().nextGaussian() * 0.02;
                  double d2 = p_41349_.getRandom().nextGaussian() * 0.02;
                  double d3 = 10.0;
                  serverLevel.sendParticles(
                     (SimpleParticleType)RVParticleTypes.END_POOF.get(),
                     p_41350_.getX(1.0) - d0 * d3,
                     p_41350_.getRandomY() - d1 * d3,
                     p_41350_.getRandomZ(1.0) - d2 * d3,
                     0,
                     d0,
                     d1,
                     d2,
                     1.0
                  );
               }

               serverLevel.sendParticles(new EndSplashParticleOption(4.0F, 2), p_41350_.getX(), p_41350_.getY() + 0.5, p_41350_.getZ(), 1, 0.0, 0.0, 0.0, 0.0);
            }

            p_41348_.shrink(1);
            if (p_41348_.isEmpty()) {
               return new ItemStack((ItemLike)RVItems.EMPTY_ROYAL_TELELOCATOR.get());
            }

            if (p_41350_ instanceof Player player && !player.getAbilities().instabuild) {
               ItemStack itemstack = new ItemStack((ItemLike)RVItems.EMPTY_ROYAL_TELELOCATOR.get());
               if (!player.getInventory().add(itemstack)) {
                  player.drop(itemstack, false);
               }
            }
         }
      }

      return p_41348_;
   }

   public int getUseDuration(ItemStack p_41454_, LivingEntity entity) {
      return MathHelper.secondsToTicks(4);
   }

   public UseAnim getUseAnimation(ItemStack p_41452_) {
      return UseAnim.BOW;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(stack, tooltip));
   }

   public void addInformationAfterShift(ItemStack stack, List<Component> tooltip) {
      boolean flag = false;
      if (stack.has(RVDataComponents.TELELOCATOR_TRACKER)) {
         TelelocatorTracker tracker = (TelelocatorTracker)stack.get(RVDataComponents.TELELOCATOR_TRACKER);
         if (tracker != null && tracker.getTarget().isPresent()) {
            BlockPos blockPos = getBlockPos(stack);
            if (blockPos != null) {
               tooltip.add(Component.translatable("info.royalvariations.telelocator.active"));
               tooltip.add(
                  Component.translatable("info.royalvariations.telelocator.Pos")
                     .append(" ")
                     .append(Component.translatable("info.royalvariations.telelocator.PosNum", new Object[]{blockPos.getX(), blockPos.getY(), blockPos.getZ()}))
               );
               tooltip.add(
                  Component.translatable("info.royalvariations.telelocator.PosDim", new Object[]{tracker.getTarget().get().dimension().location().toString()})
               );
            }
         } else {
            flag = true;
         }
      } else {
         flag = true;
      }

      if (flag) {
         tooltip.add(Component.translatable("info.royalvariations.telelocator"));
         tooltip.add(Component.translatable("info.royalvariations.telelocator.0"));
         tooltip.add(Component.translatable("info.royalvariations.telelocator.1"));
      }
   }
}
