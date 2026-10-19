package net.p3pp3rf1y.sophisticatedcore.upgrades;

import javax.annotation.Nullable;
import net.minecraft.resources.ResourceLocation;

public interface IUpgradeCountLimitConfig {
   int getMaxUpgradesPerStorage(String var1, @Nullable ResourceLocation var2);

   int getMaxUpgradesInGroupPerStorage(String var1, UpgradeGroup var2);
}
