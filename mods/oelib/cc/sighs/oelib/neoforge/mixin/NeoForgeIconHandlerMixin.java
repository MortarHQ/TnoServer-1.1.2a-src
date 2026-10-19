package cc.sighs.oelib.neoforge.mixin;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.icon.DynamicIconRegistry;
import java.util.Optional;
import net.neoforged.neoforge.client.gui.ModListScreen;
import net.neoforged.neoforgespi.language.IModInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModListScreen.class)
public abstract class NeoForgeIconHandlerMixin {
   @Redirect(method = "updateCache()V", at = @At(value = "INVOKE", target = "Lnet/neoforged/neoforgespi/language/IModInfo;getLogoFile()Ljava/util/Optional;"))
   private Optional<String> oelib$redirectLogo(IModInfo info) {
      String modId = info.getModId();
      Optional<String> dynamic = DynamicIconRegistry.getSelectedNeoForgePath(modId);
      if (dynamic.isPresent()) {
         OELib.LOGGER.info("[OELib: DynamicIcon] Set icon for {}: {}", modId, dynamic.get());
         return dynamic;
      } else {
         return info.getLogoFile();
      }
   }
}
