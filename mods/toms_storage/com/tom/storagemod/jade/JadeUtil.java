package com.tom.storagemod.jade;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import net.minecraft.world.phys.Vec2;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElement;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.BoxStyle.GradientBorder;

public class JadeUtil {
   public static void addFilterInfo(ITooltip tooltip, BlockAccessor accessor, ItemStack f) {
      IElementHelper elements = IElementHelper.get();
      if (accessor.showDetails()) {
         ITooltip t = elements.tooltip();
         IElement icon = elements.item(f, 1.0F).size(new Vec2(18.0F, 18.0F)).translate(new Vec2(0.0F, -1.0F));
         icon.message(null);
         t.add(icon);
         f.getTooltipLines(TooltipContext.of(accessor.getLevel()), accessor.getPlayer(), Default.NORMAL).forEach(t::add);
         GradientBorder b = BoxStyle.getTransparent().clone();
         b.borderColor = new int[]{-65536, -65536, -65536, -65536};
         b.borderWidth = 1.0F;
         tooltip.add(elements.box(t, b));
      } else {
         tooltip.add(Component.translatable("tooltip.toms_storage.block_filter.item", new Object[]{f.getHoverName()}));
      }
   }
}
