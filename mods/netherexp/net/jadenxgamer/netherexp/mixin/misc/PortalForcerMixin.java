package net.jadenxgamer.netherexp.mixin.misc;

import java.util.Optional;
import net.jadenxgamer.netherexp.core.entity.PortalGlow;
import net.minecraft.BlockUtil.FoundRectangle;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction.Axis;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.portal.PortalForcer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalForcer.class)
public class PortalForcerMixin {
   @Shadow
   @Final
   protected ServerLevel level;

   @Inject(method = "createPortal", at = @At("RETURN"))
   private void netherexp$spawnPortalGlow(BlockPos pos, Axis axis, CallbackInfoReturnable<Optional<FoundRectangle>> cir) {
      ((Optional)cir.getReturnValue()).ifPresent(rectangle -> PortalGlow.spawnForPortal(this.level, rectangle.minCorner));
   }
}
