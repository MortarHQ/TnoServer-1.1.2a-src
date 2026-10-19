package net.conczin.immersive_optimization.mixin.client;

import java.util.List;
import net.conczin.immersive_optimization.TickScheduler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import net.minecraft.client.multiplayer.ClientLevel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DebugScreenOverlay.class)
public class DebugHudMixin {
   @Inject(at = @At("RETURN"), method = "getGameInformation")
   protected void getLeftText(CallbackInfoReturnable<List<String>> info) {
      ClientLevel level = Minecraft.getInstance().level;
      if (level != null) {
         TickScheduler.LevelData data = TickScheduler.INSTANCE.getLevelData(level);
         if (data != null) {
            ((List)info.getReturnValue()).add("[IO] " + data.toLog());
         }
      }
   }
}
