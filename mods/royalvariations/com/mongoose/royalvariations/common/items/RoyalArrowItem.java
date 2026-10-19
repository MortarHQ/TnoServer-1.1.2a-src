package com.mongoose.royalvariations.common.items;

import com.mongoose.royalvariations.common.entities.projectile.RoyalArrow;
import com.mongoose.royalvariations.utils.ItemHelper;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;

public class RoyalArrowItem extends ArrowItem {
   public RoyalArrowItem() {
      super(new Properties());
   }

   public AbstractArrow createArrow(Level level, ItemStack ammo, LivingEntity shooter, @Nullable ItemStack weapon) {
      RoyalArrow arrow = new RoyalArrow(level, shooter, ammo.copyWithCount(1), weapon);
      arrow.setMarking(false);
      return arrow;
   }

   public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
      RoyalArrow arrow = new RoyalArrow(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1), null);
      arrow.pickup = Pickup.ALLOWED;
      return arrow;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      ItemHelper.addOnShift(tooltip, () -> this.addInformationAfterShift(tooltip));
   }

   public void addInformationAfterShift(List<Component> tooltip) {
      tooltip.add(Component.translatable("info.royalvariations.royal_arrow.0"));
      tooltip.add(Component.translatable("info.royalvariations.royal_arrow.1"));
   }
}
