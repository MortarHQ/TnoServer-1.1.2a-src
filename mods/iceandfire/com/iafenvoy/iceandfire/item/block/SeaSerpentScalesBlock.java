package com.iafenvoy.iceandfire.item.block;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class SeaSerpentScalesBlock extends Block {
   final ChatFormatting color;
   final String name;

   public SeaSerpentScalesBlock(String name, ChatFormatting color) {
      super(Properties.of().mapColor(MapColor.STONE).strength(30.0F, 500.0F).sound(SoundType.STONE).requiresCorrectToolForDrops());
      this.color = color;
      this.name = name;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag options) {
      super.appendHoverText(stack, context, tooltip, options);
      tooltip.add(Component.translatable("sea_serpent." + this.name).withStyle(this.color));
   }
}
