package dev.aika.smsn.neoforge.mixin.hexerei;

import dev.aika.smsn.SMSN;
import java.net.URL;
import net.joefoxe.hexerei.util.HexereiSupporterBenefits;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = HexereiSupporterBenefits.class, remap = false)
public abstract class HexereiSupporterBenefitsMixin {
   @Inject(method = "init()V", at = @At("HEAD"), cancellable = true)
   private static void init(CallbackInfo ci) {
      if (!SMSN.CONFIG.isHexereiSupporter()) {
         ci.cancel();
      }
   }

   @Inject(method = "readUrl(Ljava/net/URL;)Ljava/lang/String;", at = @At("HEAD"), cancellable = true)
   private static void readUrl(URL url, CallbackInfoReturnable<String> cir) {
      if (!SMSN.CONFIG.isHexereiSupporter()) {
         cir.setReturnValue("");
      }
   }
}
