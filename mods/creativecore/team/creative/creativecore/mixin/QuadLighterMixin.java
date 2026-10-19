package team.creative.creativecore.mixin;

import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.lighting.QuadLighter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import team.creative.creativecore.client.render.model.CreativeQuadLighter;

@Mixin(value = QuadLighter.class, remap = false)
public abstract class QuadLighterMixin implements CreativeQuadLighter {
   @Unique
   public int customTint = -1;
   @Shadow
   public int cachedTintIndex;
   @Shadow
   @Final
   public float[] cachedTintColor;

   @Accessor
   @Override
   public abstract void setState(BlockState var1);

   @Inject(at = @At("HEAD"), method = "getColorFast(I)[F", cancellable = true, remap = false)
   public void getColorMultiplierHook(int tint, CallbackInfoReturnable info) {
      if (this.customTint != -1) {
         this.cachedTintIndex = tint;
         this.cachedTintColor[0] = (this.customTint >> 16 & 0xFF) / 255.0F;
         this.cachedTintColor[1] = (this.customTint >> 8 & 0xFF) / 255.0F;
         this.cachedTintColor[2] = (this.customTint & 0xFF) / 255.0F;
         info.setReturnValue(this.cachedTintColor);
      }
   }

   @Override
   public void setCustomTint(int tint) {
      this.customTint = tint;
   }
}
