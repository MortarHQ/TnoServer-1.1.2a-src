package nadiendev.constructionwand.api;

import java.util.List;
import javax.annotation.Nonnull;
import nadiendev.constructionwand.basics.option.WandOptions;
import nadiendev.constructionwand.wand.undo.ISnapshot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;

public interface IWandAction {
   int getLimit(ItemStack var1);

   @Nonnull
   List<ISnapshot> getSnapshots(Level var1, Player var2, BlockHitResult var3, ItemStack var4, WandOptions var5, IWandSupplier var6, int var7);

   @Nonnull
   List<ISnapshot> getSnapshotsFromAir(Level var1, Player var2, BlockHitResult var3, ItemStack var4, WandOptions var5, IWandSupplier var6, int var7);
}
