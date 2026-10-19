package dev.aika.smsn.neoforge.mixin.tenshilib;

import dev.aika.smsn.SMSN;
import io.github.flemmli97.tenshilib.patreon.PatreonDataManager;
import io.github.flemmli97.tenshilib.patreon.PatreonDataManager.PatreonPlayerInfo;
import java.util.HashMap;
import java.util.Map;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = PatreonDataManager.class, remap = false)
public abstract class PatreonDataManagerMixin {
   @Shadow
   private static Map<String, PatreonPlayerInfo> PLAYERS;
   @Shadow
   private static boolean READING;

   @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
   private static void init(CallbackInfo ci) {
      if (!SMSN.CONFIG.isTenshilibPatreon()) {
         if (PLAYERS == null) {
            PLAYERS = new HashMap<>();
            READING = false;
         }

         ci.cancel();
      }
   }
}
