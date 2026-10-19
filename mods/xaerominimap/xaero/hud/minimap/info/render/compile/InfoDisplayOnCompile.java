package xaero.hud.minimap.info.render.compile;

import net.minecraft.core.BlockPos;
import xaero.hud.minimap.info.InfoDisplay;
import xaero.hud.minimap.module.MinimapSession;

@FunctionalInterface
public interface InfoDisplayOnCompile<T> {
   void onCompile(InfoDisplay<T> var1, InfoDisplayCompiler var2, MinimapSession var3, int var4, BlockPos var5);
}
