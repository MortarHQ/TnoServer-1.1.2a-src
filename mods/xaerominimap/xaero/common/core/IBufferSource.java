package xaero.common.core;

import net.minecraft.client.renderer.RenderType;

public interface IBufferSource {
   RenderType getXaero_lastRenderType();

   void setXaero_lastRenderType(RenderType var1);
}
