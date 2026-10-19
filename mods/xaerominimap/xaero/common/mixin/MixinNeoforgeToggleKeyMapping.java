package xaero.common.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.client.ToggleKeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import xaero.common.core.XaeroMinimapCore;

@Mixin(ToggleKeyMapping.class)
public class MixinNeoforgeToggleKeyMapping {
   @ModifyReturnValue(method = "isDown", at = @At("RETURN"))
   public boolean onIsDown(boolean original) throws Exception {
      return XaeroMinimapCore.onToggleKeyIsDown((ToggleKeyMapping)this) ? true : original;
   }
}
