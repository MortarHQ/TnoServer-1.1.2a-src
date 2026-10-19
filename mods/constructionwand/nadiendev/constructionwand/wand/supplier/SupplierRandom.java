package nadiendev.constructionwand.wand.supplier;

import java.util.LinkedHashMap;
import nadiendev.constructionwand.basics.WandUtil;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.basics.pool.RandomPool;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SupplierRandom extends SupplierInventory {
   public SupplierRandom(Player player, WandOptions options) {
      super(player, options);
   }

   @Override
   public void getSupply(@Nullable BlockItem target) {
      this.itemCounts = new LinkedHashMap<>();
      this.itemPool = new RandomPool<>(this.player.getRandom());

      for (ItemStack stack : WandUtil.getHotbarWithOffhand(this.player)) {
         if (stack.getItem() instanceof BlockItem blockItem) {
            this.addBlockItem(blockItem);
         }
      }
   }
}
