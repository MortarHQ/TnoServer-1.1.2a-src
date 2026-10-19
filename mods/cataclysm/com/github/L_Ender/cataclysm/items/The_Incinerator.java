package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.effect.Flame_Strike_Entity;
import com.github.L_Ender.cataclysm.entity.effect.ScreenShake_Entity;
import com.github.L_Ender.cataclysm.init.ModSounds;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.common.ItemAbilities;
import net.neoforged.neoforge.common.ItemAbility;

public class The_Incinerator extends Cataclysm_Weapon {
   public The_Incinerator(Properties group) {
      super(group);
   }

   public UseAnim getUseAnimation(ItemStack p_77661_1_) {
      return UseAnim.BOW;
   }

   public int getUseDuration(ItemStack pStack, LivingEntity pEntity) {
      return 72000;
   }

   public void releaseUsing(ItemStack p_43394_, Level p_43395_, LivingEntity p_43396_, int p_43397_) {
      if (p_43396_ instanceof Player player) {
         int i = this.getUseDuration(p_43394_, p_43396_) - p_43397_;
         double headY = player.getY() + 1.0;
         int standingOnY = Mth.floor(player.getY()) - 2;
         float yawRadians = (float)Math.toRadians(90.0F + player.getYRot());
         boolean hasSucceeded = false;
         if (i >= 60) {
            for (int l = 0; l < 10; l++) {
               double d2 = 2.25 * (l + 1);
               int j2 = (int)(1.5F * l);
               if (this.spawnFlameStrike(
                  player.getX() + Mth.cos(yawRadians) * d2,
                  player.getZ() + Mth.sin(yawRadians) * d2,
                  standingOnY,
                  headY,
                  yawRadians,
                  40,
                  j2,
                  j2,
                  p_43395_,
                  1.0F,
                  player
               )) {
                  hasSucceeded = true;
               }
            }

            if (hasSucceeded) {
               if (!p_43395_.isClientSide) {
                  player.getCooldowns().addCooldown(this, CMCommonConfig.Incinerator.cooldown);
               }

               ScreenShake_Entity.ScreenShake(p_43395_, player.position(), 30.0F, 0.15F, 0, 30);
               player.playSound((SoundEvent)ModSounds.SWORD_STOMP.get(), 1.0F, 1.0F);
            }
         }
      }
   }

   public void onUseTick(Level worldIn, LivingEntity livingEntityIn, ItemStack stack, int count) {
      int i = this.getUseDuration(stack, livingEntityIn) - count;
      if (i == 60) {
         livingEntityIn.playSound((SoundEvent)ModSounds.FLAME_BURST.get(), 1.0F, 1.0F);
      }
   }

   public InteractionResultHolder<ItemStack> use(Level p_77659_1_, Player p_77659_2_, InteractionHand p_77659_3_) {
      ItemStack item = p_77659_2_.getItemInHand(p_77659_3_);
      InteractionHand otherhand = p_77659_3_ == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND;
      ItemStack otheritem = p_77659_2_.getItemInHand(otherhand);
      if (otheritem.canPerformAction(ItemAbilities.SHIELD_BLOCK) && !p_77659_2_.getCooldowns().isOnCooldown(otheritem.getItem())) {
         return InteractionResultHolder.fail(item);
      }

      p_77659_2_.startUsingItem(p_77659_3_);
      return InteractionResultHolder.consume(item);
   }

   public boolean canDisableShield(ItemStack stack, ItemStack shield, LivingEntity entity, LivingEntity attacker) {
      return true;
   }

   public boolean isEnchantable(ItemStack stack) {
      return true;
   }

   public int getEnchantmentValue() {
      return 16;
   }

   public boolean canAttackBlock(BlockState state, Level worldIn, BlockPos pos, Player player) {
      return !player.isCreative();
   }

   public float getDestroySpeed(ItemStack p_43288_, BlockState p_43289_) {
      if (p_43289_.is(Blocks.COBWEB)) {
         return 15.0F;
      } else {
         return p_43289_.is(BlockTags.SWORD_EFFICIENT) ? 1.5F : 1.0F;
      }
   }

   public boolean isCorrectToolForDrops(BlockState p_43298_) {
      return p_43298_.is(Blocks.COBWEB);
   }

   private boolean spawnFlameStrike(
      double x, double z, double minY, double maxY, float rotation, int duration, int wait, int delay, Level world, float radius, LivingEntity player
   ) {
      BlockPos blockpos = BlockPos.containing(x, maxY, z);
      boolean flag = false;
      double d0 = 0.0;

      do {
         BlockPos blockpos1 = blockpos.below();
         BlockState blockstate = world.getBlockState(blockpos1);
         if (blockstate.isFaceSturdy(world, blockpos1, Direction.UP)) {
            if (!world.isEmptyBlock(blockpos)) {
               BlockState blockstate1 = world.getBlockState(blockpos);
               VoxelShape voxelshape = blockstate1.getCollisionShape(world, blockpos);
               if (!voxelshape.isEmpty()) {
                  d0 = voxelshape.max(Axis.Y);
               }
            }

            flag = true;
            break;
         }

         blockpos = blockpos.below();
      } while (blockpos.getY() >= minY);

      if (flag) {
         world.addFreshEntity(new Flame_Strike_Entity(world, x, blockpos.getY() + d0, z, rotation, duration, wait, delay, radius, 6.0F, 2.0F, false, player));
         return true;
      } else {
         return false;
      }
   }

   public boolean canPerformAction(ItemStack stack, ItemAbility itemAbility) {
      return ItemAbilities.DEFAULT_SWORD_ACTIONS.contains(itemAbility);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.incinerator.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.translatable("item.cataclysm.incinerator2.desc").withStyle(ChatFormatting.DARK_GREEN));
      tooltip.add(Component.translatable("item.cataclysm.incinerator3.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
