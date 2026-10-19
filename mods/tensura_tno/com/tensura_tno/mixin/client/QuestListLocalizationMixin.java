package com.tensura_tno.mixin.client;

import com.tensura_tno.QuestNameCache;
import net.minecraft.locale.Language;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "org.crypticdev.stextras.client.screen.STPrestigeScreens", remap = false)
public class QuestListLocalizationMixin {
   @Unique
   private static Language tensura_tno$lastLanguage;

   @Inject(method = "renderBackground", at = @At("HEAD"), remap = false)
   private void checkLanguageOnRender(CallbackInfo ci) {
      Language language = Language.getInstance();
      if (language != tensura_tno$lastLanguage) {
         tensura_tno$lastLanguage = language;
         QuestNameCache.reapply();
      }
   }
}
