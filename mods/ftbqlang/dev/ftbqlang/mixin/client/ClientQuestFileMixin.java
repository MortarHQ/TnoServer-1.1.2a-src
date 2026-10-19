package dev.ftbqlang.mixin.client;

import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftbqlang.client.QuestLanguageClient;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientQuestFile.class, remap = false)
public abstract class ClientQuestFileMixin {
   @Inject(method = "refreshGui", at = @At("HEAD"), cancellable = true)
   private void ftbqlang$preserveLanguageModal(CallbackInfo callback) {
      if (Minecraft.getInstance().screen instanceof ScreenWrapper wrapper
         && wrapper.getGui() instanceof QuestScreen screen
         && QuestLanguageClient.blocksBookClose(screen)) {
         QuestLanguageClient.refreshTranslations((ClientQuestFile)this);
         callback.cancel();
      }
   }

   @Inject(method = "getLocale", at = @At("HEAD"), cancellable = true)
   private void ftbqlang$questLocale(CallbackInfoReturnable<String> callback) {
      String locale = QuestLanguageClient.localeOverride();
      if (!locale.isEmpty()) {
         callback.setReturnValue(locale);
      }
   }
}
