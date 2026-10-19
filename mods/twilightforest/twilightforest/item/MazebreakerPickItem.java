package twilightforest.item;

import javax.annotation.Nonnull;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.data.tags.BlockTagGenerator;

public class MazebreakerPickItem extends PickaxeItem {
   public MazebreakerPickItem(Tier material, Properties properties) {
      super(material, properties);
   }

   public float getDestroySpeed(@Nonnull ItemStack stack, BlockState state) {
      float destroySpeed = super.getDestroySpeed(stack, state);
      return state.is(BlockTagGenerator.MAZEBREAKER_ACCELERATED) ? destroySpeed * 16.0F : destroySpeed;
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack otherStack) {
      return false;
   }
}
