package com.bobmowzie.mowziesmobs.server.item;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class ItemSpear extends DiggerItem {
   public static final ResourceLocation SPEAR_REACH_ID = ResourceLocation.fromNamespaceAndPath("mowziesmobs", "base_attack_damage");

   public ItemSpear(Properties properties) {
      super(Tiers.STONE, BlockTags.MINEABLE_WITH_HOE, properties);
   }

   public boolean canAttackBlock(BlockState p_43291_, Level p_43292_, BlockPos p_43293_, Player p_43294_) {
      return !p_43294_.isCreative();
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flagIn) {
      super.appendHoverText(stack, context, tooltip, flagIn);
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.0").setStyle(ItemHandler.TOOLTIP_STYLE));
      tooltip.add(Component.translatable(this.getDescriptionId() + ".text.1").setStyle(ItemHandler.TOOLTIP_STYLE));
   }
}
