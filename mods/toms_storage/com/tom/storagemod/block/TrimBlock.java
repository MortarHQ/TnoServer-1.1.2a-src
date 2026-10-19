package com.tom.storagemod.block;

import com.tom.storagemod.Content;
import com.tom.storagemod.client.ClientUtil;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;

public class TrimBlock extends Block implements IPaintable {
   public TrimBlock() {
      super(Properties.of().mapColor(MapColor.WOOD).sound(SoundType.WOOD).strength(3.0F));
   }

   public void appendHoverText(ItemStack itemStack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
      tooltip.add(Component.translatable("tooltip.toms_storage.paintable"));
      ClientUtil.tooltip("trim", tooltip);
   }

   @Override
   public boolean paint(Level world, BlockPos pos, BlockState to) {
      world.setBlockAndUpdate(pos, Content.paintedTrim.get().defaultBlockState());
      return Content.paintedTrim.get().paint(world, pos, to);
   }
}
