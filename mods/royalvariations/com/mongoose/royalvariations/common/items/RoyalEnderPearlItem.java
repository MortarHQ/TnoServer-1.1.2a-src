package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.common.entities.projectile.ThrownRoyalEnderPearl;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class RoyalEnderPearlItem extends Item {
   public RoyalEnderPearlItem() {
      super(new Properties().stacksTo(16));
   }

   public InteractionResultHolder<ItemStack> use(Level p_41190_, Player p_41191_, InteractionHand p_41192_) {
      ItemStack itemstack = p_41191_.getItemInHand(p_41192_);
      p_41190_.playSound(
         (Player)null,
         p_41191_.getX(),
         p_41191_.getY(),
         p_41191_.getZ(),
         SoundEvents.ENDER_PEARL_THROW,
         SoundSource.NEUTRAL,
         0.5F,
         0.4F / (p_41190_.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      p_41191_.getCooldowns().addCooldown(this, 20);
      if (!p_41190_.isClientSide) {
         ThrownRoyalEnderPearl thrownenderpearl = new ThrownRoyalEnderPearl(p_41190_, p_41191_);
         thrownenderpearl.setItem(itemstack);
         thrownenderpearl.shootFromRotation(p_41191_, p_41191_.getXRot(), p_41191_.getYRot(), 0.0F, 1.5F, 1.0F);
         p_41190_.addFreshEntity(thrownenderpearl);
      }

      p_41191_.awardStat(Stats.ITEM_USED.get(this));
      if (!p_41191_.getAbilities().instabuild) {
         itemstack.shrink(1);
      }

      return InteractionResultHolder.sidedSuccess(itemstack, p_41190_.isClientSide());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable("info.royalvariations.royal_ender_pearl"));
   }
}
