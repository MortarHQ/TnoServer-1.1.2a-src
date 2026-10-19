package com.mortal_cultivation.item;

import com.mortal_cultivation.entity.TenThousandSoulBannerEntity;
import com.mortal_cultivation.registry.CultivationEntities;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class TenThousandSoulBannerItem extends Item {
   public TenThousandSoulBannerItem(Properties properties) {
      super(properties);
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (level.isClientSide) {
         return InteractionResult.SUCCESS;
      }

      Player player = context.getPlayer();
      if (player == null) {
         return InteractionResult.FAIL;
      }

      BlockPos pos = context.getClickedPos();
      Direction direction = context.getClickedFace();
      BlockState blockState = level.getBlockState(pos);
      BlockPos placePos = pos.relative(direction);
      if (direction == Direction.UP) {
         placePos = pos.above();
      }

      if (level.getBlockState(placePos).isAir()
         && level.getBlockState(placePos.above()).isAir()
         && level.getBlockState(placePos.above(2)).isAir()
         && level.getBlockState(placePos.above(3)).isAir()) {
         TenThousandSoulBannerEntity banner = new TenThousandSoulBannerEntity((EntityType<?>)CultivationEntities.TEN_THOUSAND_SOUL_BANNER.get(), level);
         float playerYRot = player.getYRot();
         float yRot = playerYRot + 90.0F;

         while (playerYRot < 0.0F) {
            playerYRot += 360.0F;
         }

         while (playerYRot >= 360.0F) {
            playerYRot -= 360.0F;
         }

         boolean isNorth = playerYRot >= 315.0F || playerYRot <= 45.0F;
         boolean isSouth = playerYRot >= 135.0F && playerYRot <= 225.0F;
         if (isNorth || isSouth) {
            yRot += 180.0F;
         }

         while (yRot < 0.0F) {
            yRot += 360.0F;
         }

         while (yRot >= 360.0F) {
            yRot -= 360.0F;
         }

         banner.setOwnerUuid(player.getUUID());
         banner.absMoveTo(placePos.getX() + 0.5, placePos.getY(), placePos.getZ() + 0.5, yRot, 0.0F);
         banner.setYRot(yRot);
         banner.setYBodyRot(yRot);
         banner.setYHeadRot(yRot);
         banner.yRotO = yRot;
         ItemStack itemStack = context.getItemInHand();
         int soulCount = getSoulCount(itemStack);
         int soulCapacity = getSoulCapacity(itemStack);
         banner.setSoulCapacity(soulCapacity);
         banner.setSoulCount(soulCount);
         level.addFreshEntity(banner);
         level.playSound(null, placePos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
         if (!player.getAbilities().instabuild) {
            context.getItemInHand().shrink(1);
         }

         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.FAIL;
      }
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return InteractionResultHolder.pass(player.getItemInHand(hand));
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      int soulCount = getSoulCount(stack);
      int soulCapacity = getSoulCapacity(stack);
      tooltip.add(Component.translatable("mortal_cultivation.item.ten_thousand_soul_banner.souls", new Object[]{soulCount, soulCapacity}));
   }

   public static int getSoulCount(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      return tag != null && tag.contains("SoulCount") ? tag.getInt("SoulCount") : 0;
   }

   public static void setSoulCount(ItemStack stack, int count) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putInt("SoulCount", count);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   public static int getSoulCapacity(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      return tag != null && tag.contains("SoulCapacity") ? tag.getInt("SoulCapacity") : 500;
   }

   public static void setSoulCapacity(ItemStack stack, int capacity) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putInt("SoulCapacity", capacity);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }
}
