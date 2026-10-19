package com.tensura_tno.mixin.client;

import com.tensura_tno.QuestNameCache;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "org.crypticdev.stextras.quest.registry.QuestRegistry", remap = false)
public class QuestNameLocalizationMixin {
   @Inject(method = "setQuestsOnClient", at = @At("TAIL"), remap = false)
   private void localizeReplacement(List<?> quests, CallbackInfo ci) {
      QuestNameCache.update(quests, true);
   }

   @Inject(method = "mergeQuestsOnClient", at = @At("TAIL"), remap = false, require = 0)
   private void localizeMerge(List<?> quests, CallbackInfo ci) {
      QuestNameCache.update(quests, false);
   }
}
