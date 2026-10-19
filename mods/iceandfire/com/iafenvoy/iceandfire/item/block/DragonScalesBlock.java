package com.iafenvoy.iceandfire.item.block;

import com.iafenvoy.iceandfire.data.DragonColor;
import com.iafenvoy.iceandfire.item.block.util.DragonProof;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

public class DragonScalesBlock extends Block implements DragonProof {
   final DragonColor type;

   public DragonScalesBlock(DragonColor type) {
      super(
         Properties.of()
            .mapColor(MapColor.STONE)
            .instrument(NoteBlockInstrument.BASEDRUM)
            .dynamicShape()
            .strength(30.0F, 500.0F)
            .sound(SoundType.STONE)
            .requiresCorrectToolForDrops()
      );
      this.type = type;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag options) {
      super.appendHoverText(stack, context, tooltip, options);
      tooltip.add(Component.translatable("dragon." + this.type.getName()).withStyle(this.type.getColorFormatting()));
   }
}
