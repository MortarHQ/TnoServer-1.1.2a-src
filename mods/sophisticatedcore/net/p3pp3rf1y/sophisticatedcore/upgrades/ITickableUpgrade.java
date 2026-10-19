package net.p3pp3rf1y.sophisticatedcore.upgrades;

import javax.annotation.Nullable;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

public interface ITickableUpgrade {
   void tick(@Nullable Entity var1, Level var2, BlockPos var3);
}
