package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BoneMealItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.event.EventHooks;
import net.neoforged.neoforge.event.entity.player.BonemealEvent;

public class RoyalBoneMealItem extends BoneMealItem {
   public RoyalBoneMealItem() {
      super(new Properties().stacksTo(64).rarity(Rarity.COMMON));
   }

   public InteractionResult useOn(UseOnContext p_40637_) {
      Level level = p_40637_.getLevel();
      BlockPos blockpos = p_40637_.getClickedPos();
      BlockPos blockpos1 = blockpos.relative(p_40637_.getClickedFace());
      if (p_40637_.getPlayer() != null && canBonemeal(p_40637_.getItemInHand(), level, blockpos, p_40637_.getPlayer())) {
         for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
               BlockPos blockPos = blockpos.offset(i, 0, j);
               if (applyBonemeal(p_40637_.getItemInHand(), level, blockPos, p_40637_.getPlayer()) && level instanceof ServerLevel serverLevel) {
                  level.levelEvent(1505, blockPos, 0);
                  addGrowthParticles(serverLevel, blockPos, 0);
               }
            }
         }

         return InteractionResult.sidedSuccess(level.isClientSide);
      } else {
         BlockState blockstate = level.getBlockState(blockpos);
         boolean flag = blockstate.isFaceSturdy(level, blockpos, p_40637_.getClickedFace());
         if (flag && growWaterPlant(p_40637_.getItemInHand(), level, blockpos1, p_40637_.getClickedFace())) {
            if (!level.isClientSide) {
               level.levelEvent(1505, blockpos1, 0);
            }

            for (int i = -1; i <= 1; i++) {
               for (int j = -1; j <= 1; j++) {
                  BlockPos blockPos = blockpos1.offset(i, 0, j);
                  if (growWaterPlant(p_40637_.getItemInHand(), level, blockPos, p_40637_.getClickedFace()) && level instanceof ServerLevel serverLevel) {
                     level.levelEvent(1505, blockPos, 0);
                     addGrowthParticles(serverLevel, blockPos, 0);
                  }
               }
            }

            return InteractionResult.sidedSuccess(level.isClientSide);
         } else {
            return InteractionResult.PASS;
         }
      }
   }

   public static boolean canBonemeal(ItemStack p_40628_, Level p_40629_, BlockPos p_40630_, Player player) {
      BlockState blockstate = p_40629_.getBlockState(p_40630_);
      BonemealEvent event = EventHooks.fireBonemealEvent(player, p_40629_, p_40630_, blockstate, p_40628_);
      if (event.isCanceled()) {
         return event.isSuccessful();
      } else {
         return blockstate.getBlock() instanceof BonemealableBlock bonemealableblock
            ? bonemealableblock.isValidBonemealTarget(p_40629_, p_40630_, blockstate)
            : false;
      }
   }

   public static void addGrowthParticles(ServerLevel p_40639_, BlockPos p_40640_, int p_40641_) {
      if (p_40641_ == 0) {
         p_40641_ = 15;
      }

      BlockState blockstate = p_40639_.getBlockState(p_40640_);
      if (!blockstate.isAir()) {
         double d0 = 0.5;
         double d1;
         if (blockstate.is(Blocks.WATER)) {
            p_40641_ *= 3;
            d1 = 1.0;
            d0 = 3.0;
         } else if (blockstate.isSolidRender(p_40639_, p_40640_)) {
            p_40640_ = p_40640_.above();
            p_40641_ *= 3;
            d0 = 3.0;
            d1 = 1.0;
         } else {
            d1 = blockstate.getShape(p_40639_, p_40640_).max(Axis.Y);
         }

         p_40639_.sendParticles(
            (SimpleParticleType)RVParticleTypes.SHINE.get(), p_40640_.getX() + 0.5, p_40640_.getY() + 0.5, p_40640_.getZ() + 0.5, 1, 0.0, 0.0, 0.0, 0.0
         );
         RandomSource randomsource = p_40639_.getRandom();

         for (int i = 0; i < p_40641_; i++) {
            double d2 = randomsource.nextGaussian() * 0.02;
            double d3 = randomsource.nextGaussian() * 0.02;
            double d4 = randomsource.nextGaussian() * 0.02;
            double d5 = 0.5 - d0;
            double d6 = p_40640_.getX() + d5 + randomsource.nextDouble() * d0 * 2.0;
            double d7 = p_40640_.getY() + randomsource.nextDouble() * d1;
            double d8 = p_40640_.getZ() + d5 + randomsource.nextDouble() * d0 * 2.0;
            if (!p_40639_.getBlockState(BlockPos.containing(d6, d7, d8).below()).isAir()) {
               p_40639_.sendParticles((SimpleParticleType)RVParticleTypes.SHINE.get(), d6, d7, d8, 0, d2, d3, d4, 0.5);
            }
         }
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable("info.royalvariations.royal_bone_meal"));
   }
}
