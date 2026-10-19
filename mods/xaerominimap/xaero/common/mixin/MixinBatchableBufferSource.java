package xaero.common.mixin;

import net.minecraft.client.renderer.RenderType;
import net.raphimc.immediatelyfast.feature.core.BatchableBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import xaero.common.core.IBufferSource;
import xaero.common.core.XaeroMinimapCore;

@Mixin(BatchableBufferSource.class)
public class MixinBatchableBufferSource implements IBufferSource {
   private RenderType xaero_lastRenderType;

   @Override
   public RenderType getXaero_lastRenderType() {
      return this.xaero_lastRenderType;
   }

   @Override
   public void setXaero_lastRenderType(RenderType lastRenderType) {
      this.xaero_lastRenderType = lastRenderType;
   }

   @ModifyVariable(method = "getBuffer", index = 1, at = @At("HEAD"))
   public RenderType onGetBuffer(RenderType argument) {
      XaeroMinimapCore.onBufferSourceGetBuffer(this, argument);
      return argument;
   }
}
