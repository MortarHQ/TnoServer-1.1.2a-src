package twilightforest.item;

import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import twilightforest.block.JarBlock;
import twilightforest.block.entity.MasonJarBlockEntity;
import twilightforest.components.item.JarLid;
import twilightforest.init.TFDataComponents;

public class JarItem extends BlockItem {
   public JarItem(JarBlock block, Properties properties) {
      super(block, properties);
   }

   public ItemStack getDefaultInstance() {
      return (ItemStack)Util.make(
         super.getDefaultInstance(), stack -> stack.set((DataComponentType)TFDataComponents.JAR_LID.get(), new JarLid(this.getBlock().getDefaultLid()))
      );
   }

   public JarBlock getBlock() {
      return (JarBlock)super.getBlock();
   }

   public static class MasonJarItem extends JarItem {
      public MasonJarItem(JarBlock block, Properties properties) {
         super(block, properties);
      }

      public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> components, TooltipFlag flag) {
         super.appendHoverText(stack, context, components, flag);
         ItemContainerContents contents = (ItemContainerContents)stack.getComponents().get(DataComponents.CONTAINER);
         if (contents != null) {
            ItemStack storedStack = contents.copyOne();
            if (!storedStack.isEmpty()) {
               components.add(storedStack.getDisplayName().copy().append(" x" + storedStack.getCount()).withStyle(ChatFormatting.GRAY));
            }
         }
      }

      protected boolean placeBlock(BlockPlaceContext context, BlockState state) {
         boolean flag = super.placeBlock(context, state);
         Player player = context.getPlayer();
         if (player != null && context.getLevel().getBlockEntity(context.getClickedPos()) instanceof MasonJarBlockEntity jarBlockEntity) {
            jarBlockEntity.setItemRotation(RotationSegment.convertToSegment(player.getYRot() + 180.0F));
         }

         return flag;
      }
   }
}
