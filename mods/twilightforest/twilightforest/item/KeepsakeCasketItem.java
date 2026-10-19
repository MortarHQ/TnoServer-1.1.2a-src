package twilightforest.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import twilightforest.init.TFDataComponents;

public class KeepsakeCasketItem extends BlockItem {
   public KeepsakeCasketItem(Block block, Properties properties) {
      super(block, properties);
   }

   public String getDescriptionId(ItemStack stack) {
      if ((Integer)stack.getOrDefault(TFDataComponents.CASKET_DAMAGE, 0) > 0) {
         String damage = stack.get(TFDataComponents.CASKET_DAMAGE) == 1 ? "chipped_" : "damaged_";
         return "block.twilightforest." + damage + "keepsake_casket";
      } else {
         return super.getDescriptionId(stack);
      }
   }
}
