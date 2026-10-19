package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.ChapterGroupVisibility;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.ftb.mods.ftbquests.quest.ChapterGroup", remap = false)
public abstract class FTBChapterGroupQuickPropertiesMixin {
   @Inject(method = "fillConfigGroup", at = @At("TAIL"), remap = false)
   private void tensuraTno$addGroupVisibility(ConfigGroup config, CallbackInfo ci) {
      ChapterGroup group = (ChapterGroup)this;
      if (!group.isDefaultGroup()) {
         ChapterGroupVisibility.addVisibilityOption(group, config);
      }
   }
}
