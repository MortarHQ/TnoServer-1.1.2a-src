package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.config.CMCommonConfig;
import com.github.L_Ender.cataclysm.entity.projectile.Void_Rune_Entity;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.VoxelShape;

public class Void_core extends Item {
   public Void_core(Properties group) {
      super(group);
   }

   public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
      int standingOnY = Mth.floor(player.getY()) - 1;
      double headY = player.getY() + 1.0;
      float yawRadians = (float)Math.toRadians(90.0F + player.getYRot());
      boolean hasSucceeded = false;
      if (player.getXRot() > 70.0F) {
         for (int i = 0; i < 5; i++) {
            float mulPosedYaw = yawRadians + i * (float) Math.PI * 0.4F;
            if (this.spawnFangs(
               player.getX() + Mth.cos(mulPosedYaw) * 1.5, headY, player.getZ() + Mth.sin(mulPosedYaw) * 1.5, standingOnY, mulPosedYaw, 0, world, player
            )) {
               hasSucceeded = true;
            }
         }

         for (int k = 0; k < 8; k++) {
            float mulPosedYaw = yawRadians + k * (float) Math.PI * 2.0F / 8.0F + (float) (Math.PI * 2.0 / 5.0);
            if (this.spawnFangs(
               player.getX() + Mth.cos(mulPosedYaw) * 2.5, headY, player.getZ() + Mth.sin(mulPosedYaw) * 2.5, standingOnY, mulPosedYaw, 3, world, player
            )) {
               hasSucceeded = true;
            }
         }
      } else {
         for (int l = 0; l < 10; l++) {
            double d2 = 1.25 * (l + 1);
            if (this.spawnFangs(
               player.getX() + Mth.cos(yawRadians) * d2, headY, player.getZ() + Mth.sin(yawRadians) * d2, standingOnY, yawRadians, l, world, player
            )) {
               hasSucceeded = true;
            }
         }
      }

      ItemStack stack = player.getItemInHand(hand);
      if (hasSucceeded) {
         player.getCooldowns().addCooldown(this, CMCommonConfig.VoidCore.cooldown);
         return InteractionResultHolder.success(stack);
      } else {
         return InteractionResultHolder.pass(stack);
      }
   }

   private boolean spawnFangs(double x, double y, double z, int lowestYCheck, float yRot, int warmupDelayTicks, Level world, Player player) {
      BlockPos blockpos = BlockPos.containing(x, y, z);
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
      } while (blockpos.getY() >= lowestYCheck);

      if (flag) {
         world.addFreshEntity(
            new Void_Rune_Entity(world, x, blockpos.getY() + d0, z, yRot, warmupDelayTicks, (float)CMCommonConfig.VoidCore.runeDamage, player)
         );
         return true;
      } else {
         return false;
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flags) {
      tooltip.add(Component.translatable("item.cataclysm.void_core.desc").withStyle(ChatFormatting.DARK_GREEN));
   }
}
