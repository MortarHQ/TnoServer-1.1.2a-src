package cn.star.toms_storage_star_optimized.mixin;

import com.tom.storagemod.inventory.IInventoryAccess;
import com.tom.storagemod.inventory.MultiInventoryAccess;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MultiInventoryAccess.class)
public interface MultiInventoryAccessAccessor {
   @Accessor("connected")
   List<IInventoryAccess> tsso$getConnected();
}
