package nadiendev.constructionwand.items.core;

import java.util.List;
import javax.annotation.Nonnull;
import nadiendev.constructionwand.api.IWandCore;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public abstract class ItemCore extends Item implements IWandCore {
   public ItemCore(Properties properties) {
      super(properties);
   }

   public boolean hasCraftingRemainingItem(ItemStack stack) {
      return false;
   }

   public ItemStack getCraftingRemainingItem(ItemStack itemStack) {
      return ItemStack.EMPTY;
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(@Nonnull ItemStack itemstack, Level worldIn, @Nonnull List<Component> lines, @Nonnull TooltipFlag extraInfo) {
      lines.add(Component.translatable("constructionwand.option.cores." + this.getRegistryName().toString() + ".desc").withStyle(ChatFormatting.GRAY));
      lines.add(Component.translatable("constructionwand.tooltip.core_tip").withStyle(ChatFormatting.AQUA));
   }
}
