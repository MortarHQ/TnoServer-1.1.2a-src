package earth.terrarium.handcrafted.common.items;

import earth.terrarium.handcrafted.common.blocks.base.Hammerable;
import earth.terrarium.handcrafted.common.constants.ConstantComponents;
import earth.terrarium.handcrafted.common.utils.TooltipUtils;
import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class HammerItem extends Item {
   public HammerItem(Properties properties) {
      super(properties);
   }

   @NotNull
   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (level.getBlockState(context.getClickedPos()).getBlock() instanceof Hammerable block) {
         block.onHammer(
            level,
            context.getClickedPos(),
            level.getBlockState(context.getClickedPos()),
            context.getClickedFace(),
            context.getPlayer(),
            context.getClickLocation()
         );
         return InteractionResult.SUCCESS;
      } else {
         return super.useOn(context);
      }
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
      TooltipUtils.addDescriptionComponent(tooltipComponents, ConstantComponents.HAMMER);
   }
}
