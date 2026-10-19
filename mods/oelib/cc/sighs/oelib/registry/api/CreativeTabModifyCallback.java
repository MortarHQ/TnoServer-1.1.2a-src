package cc.sighs.oelib.registry.api;

import net.minecraft.world.flag.FeatureFlagSet;

public interface CreativeTabModifyCallback {
   void accept(FeatureFlagSet var1, CreativeTabOutput var2, boolean var3);
}
