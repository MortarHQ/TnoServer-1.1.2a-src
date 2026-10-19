package xueluoanping.swayinggarden.mixin;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import net.irisshaders.iris.Iris;
import net.irisshaders.iris.shaderpack.materialmap.WorldRenderingSettings;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import xueluoanping.swayinggarden.client.shader.IAttach;
import xueluoanping.swayinggarden.client.shader.IrisAttach;

@Mixin(WorldRenderingSettings.class)
public abstract class MixinWorldRenderingSettings implements IAttach {
   @Shadow(remap = false)
   private boolean reloadRequired;
   @Shadow(remap = false)
   private Object2IntMap<BlockState> blockStateIds;

   @Inject(at = @At("RETURN"), method = "setBlockStateIds", require = 0, remap = false)
   private void ecliptic$reload(CallbackInfo ci) {
      IrisAttach.reload(this.blockStateIds, Iris.getIrisConfig().getShaderPackName().orElse(""));
   }

   @Unique
   @Override
   public void swaying_garden$set(boolean yes) {
      this.reloadRequired = yes;
   }
}
