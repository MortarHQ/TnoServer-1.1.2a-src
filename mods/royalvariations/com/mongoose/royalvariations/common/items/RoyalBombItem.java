package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.common.entities.projectile.RoyalBombProjectile;
import com.mongoose.royalvariations.utils.ItemHelper;
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

public class RoyalBombItem extends Item {
   public RoyalBombItem() {
      super(new Properties().stacksTo(64));
   }

   public InteractionResultHolder<ItemStack> use(Level p_43142_, Player p_43143_, InteractionHand p_43144_) {
      ItemStack itemstack = p_43143_.getItemInHand(p_43144_);
      p_43142_.playSound(
         (Player)null,
         p_43143_.getX(),
         p_43143_.getY(),
         p_43143_.getZ(),
         SoundEvents.SNOWBALL_THROW,
         SoundSource.NEUTRAL,
         0.5F,
         0.4F / (p_43142_.getRandom().nextFloat() * 0.4F + 0.8F)
      );
      if (!p_43142_.isClientSide) {
         RoyalBombProjectile snowball = new RoyalBombProjectile(p_43143_, p_43142_);
         snowball.shootFromRotation(p_43143_, p_43143_.getXRot(), p_43143_.getYRot(), 0.0F, 0.8F, 1.0F);
         p_43142_.addFreshEntity(snowball);
      }

      p_43143_.awardStat(Stats.ITEM_USED.get(this));
      if (!p_43143_.getAbilities().instabuild) {
         itemstack.shrink(1);
      }

      p_43143_.getCooldowns().addCooldown(this, 40);
      return InteractionResultHolder.sidedSuccess(itemstack, p_43142_.isClientSide());
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.royal_bomb"));
   }
}
