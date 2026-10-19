package dev.ftbqlang.mixin.client;

import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.net.SyncTranslationMessageToClient;
import dev.ftbqlang.client.QuestLanguageClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SyncTranslationMessageToClient.class, remap = false)
public abstract class SyncTranslationMessageToClientMixin {
   @Inject(
      method = "lambda$handle$2",
      at = @At(value = "INVOKE", target = "Ldev/ftb/mods/ftbquests/quest/QuestObjectBase;clearCachedData()V", shift = Shift.AFTER)
   )
   private static void ftbqlang$refreshEditedText(CallbackInfo callback) {
      QuestLanguageClient.refreshTranslations(ClientQuestFile.INSTANCE);
   }
}
