package com.iafenvoy.iceandfire.item.tool;

import com.iafenvoy.iceandfire.entity.AmphithereArrowEntity;
import com.iafenvoy.iceandfire.registry.IafEntities;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ArrowItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class AmphithereArrowItem extends ArrowItem {
   public AmphithereArrowItem() {
      super(new Properties());
   }

   public AbstractArrow createArrow(Level world, ItemStack stack, LivingEntity shooter, @Nullable ItemStack shotFrom) {
      return new AmphithereArrowEntity((EntityType<? extends AbstractArrow>)IafEntities.AMPHITHERE_ARROW.get(), shooter, world, shotFrom);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag type) {
      super.appendHoverText(stack, context, tooltip, type);
      tooltip.add(Component.translatable("item.iceandfire.amphithere_arrow.desc").withStyle(ChatFormatting.GRAY));
   }
}
