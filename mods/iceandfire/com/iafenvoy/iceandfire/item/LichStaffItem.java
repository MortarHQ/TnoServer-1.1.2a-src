package com.iafenvoy.iceandfire.item;

import com.iafenvoy.iceandfire.entity.DreadLichSkullEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import com.iafenvoy.iceandfire.registry.IafItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class LichStaffItem extends Item {
   public LichStaffItem() {
      super(new Properties().durability(100));
   }

   public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
      return repair.getItem() == IafItems.DREAD_SHARD.get() || super.isValidRepairItem(toRepair, repair);
   }

   public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand hand) {
      ItemStack itemStackIn = playerIn.getItemInHand(hand);
      if (!worldIn.isClientSide) {
         playerIn.startUsingItem(hand);
         playerIn.swing(hand);
         double d2 = playerIn.getLookAngle().x;
         double d3 = playerIn.getLookAngle().y;
         double d4 = playerIn.getLookAngle().z;
         float inaccuracy = 1.0F;
         d2 += playerIn.getRandom().nextGaussian() * 0.0075F * inaccuracy;
         d3 += playerIn.getRandom().nextGaussian() * 0.0075F * inaccuracy;
         d4 += playerIn.getRandom().nextGaussian() * 0.0075F * inaccuracy;
         DreadLichSkullEntity charge = new DreadLichSkullEntity((EntityType<? extends AbstractArrow>)IafEntities.DREAD_LICH_SKULL.get(), worldIn, playerIn, 6.0);
         charge.shoot(playerIn.getXRot(), playerIn.getYRot(), 0.0, 7.0F, 1.0F);
         charge.setPos(playerIn.getX(), playerIn.getY() + 1.0, playerIn.getZ());
         worldIn.addFreshEntity(charge);
         charge.shoot(d2, d3, d4, 1.0F, 1.0F);
         playerIn.playSound(SoundEvents.ZOMBIE_INFECT, 1.0F, 0.75F + 0.5F * playerIn.getRandom().nextFloat());
         itemStackIn.hurtAndBreak(1, playerIn, LivingEntity.getSlotForHand(hand));
         playerIn.getCooldowns().addCooldown(this, 4);
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, itemStackIn);
   }
}
