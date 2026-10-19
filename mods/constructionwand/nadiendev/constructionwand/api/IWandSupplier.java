package nadiendev.constructionwand.api;

import javax.annotation.Nullable;
import nadiendev.constructionwand.wand.undo.PlaceSnapshot;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public interface IWandSupplier {
   void getSupply(@Nullable BlockItem var1);

   @Nullable
   PlaceSnapshot getPlaceSnapshot(Level var1, BlockPos var2, BlockHitResult var3, @Nullable BlockState var4);

   int takeItemStack(ItemStack var1);
}
