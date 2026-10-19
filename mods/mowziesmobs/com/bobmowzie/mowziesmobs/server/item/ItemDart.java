package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.entity.EntityDart;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
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
import org.jetbrains.annotations.NotNull;

public class ItemDart extends ArrowItem {
   public ItemDart(Properties properties) {
      super(properties);
   }

   @NotNull
   public AbstractArrow createArrow(@NotNull Level level, @NotNull ItemStack ammo, @NotNull LivingEntity shooter, @Nullable ItemStack weapon) {
      return new EntityDart((EntityType<? extends EntityDart>)EntityHandler.DART.get(), level, shooter);
   }

   @NotNull
   public Projectile asProjectile(@NotNull Level level, @NotNull Position pos, @NotNull ItemStack stack, @NotNull Direction direction) {
      EntityDart dart = new EntityDart((EntityType<? extends EntityDart>)EntityHandler.DART.get(), level, pos);
      dart.pickup = Pickup.ALLOWED;
      return dart;
   }

   public void appendHoverText(
      @NotNull ItemStack stack, @NotNull TooltipContext context, @NotNull List<Component> tooltipComponents, @NotNull TooltipFlag tooltipFlag
   ) {
      super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
      tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltipComponents.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
   }
}
