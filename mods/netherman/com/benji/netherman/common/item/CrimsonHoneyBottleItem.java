package com.benji.netherman.common.item;

import com.benji.netherman.init.ModBlocks;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class CrimsonHoneyBottleItem extends Item {
   public CrimsonHoneyBottleItem(Properties properties) {
      super(properties);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      Component honey = Component.translatable("tooltip.netherman.honey").withStyle(ChatFormatting.YELLOW);
      tooltipComponents.add(honey);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entityLiving) {
      super.finishUsingItem(stack, level, entityLiving);
      if (entityLiving instanceof ServerPlayer serverPlayer) {
         CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, stack);
      }

      if (stack.isEmpty()) {
         return new ItemStack(Items.GLASS_BOTTLE);
      }

      if (entityLiving instanceof Player player && !player.getAbilities().instabuild) {
         ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
         if (!player.getInventory().add(emptyBottle)) {
            player.drop(emptyBottle, false);
         }
      }

      return stack;
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return 40;
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.DRINK;
   }

   public SoundEvent getDrinkingSound() {
      return SoundEvents.HONEY_DRINK;
   }

   public SoundEvent getEatingSound() {
      return SoundEvents.HONEY_DRINK;
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      BlockPos pos = context.getClickedPos();
      Player player = context.getPlayer();
      ItemStack stack = context.getItemInHand();
      if (level.getBlockState(pos).is(Blocks.HONEY_BLOCK)) {
         if (!level.isClientSide()) {
            level.setBlockAndUpdate(pos, ((Block)ModBlocks.CRIMSON_HONEY_BLOCK.get()).defaultBlockState());
            level.playSound(null, pos, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.playSound(null, pos, SoundEvents.SLIME_BLOCK_BREAK, SoundSource.BLOCKS, 1.0F, 0.5F);
            ((ServerLevel)level)
               .sendParticles(
                  new BlockParticleOption(ParticleTypes.BLOCK, ((Block)ModBlocks.CRIMSON_HONEY_BLOCK.get()).defaultBlockState()),
                  pos.getX() + 0.5,
                  pos.getY() + 0.5,
                  pos.getZ() + 0.5,
                  30,
                  0.3,
                  0.3,
                  0.3,
                  0.05
               );
            if (player != null && !player.getAbilities().instabuild) {
               stack.shrink(1);
               ItemStack emptyBottle = new ItemStack(Items.GLASS_BOTTLE);
               if (!player.getInventory().add(emptyBottle)) {
                  player.drop(emptyBottle, false);
               }
            }
         }

         return InteractionResult.sidedSuccess(level.isClientSide());
      } else {
         return super.useOn(context);
      }
   }
}
