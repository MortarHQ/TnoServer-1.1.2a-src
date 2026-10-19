package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.ChapterGroupVisibility;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets = "dev.ftb.mods.ftbquests.quest.Chapter", remap = false)
public abstract class FTBChapterGroupVisibilityMixin {
   @Shadow
   public abstract ChapterGroup getGroup();

   @Inject(method = "isAlwaysInvisible", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$inheritGroupHiddenFlag(CallbackInfoReturnable<Boolean> cir) {
      if (ChapterGroupVisibility.isHidden(this.getGroup())) {
         cir.setReturnValue(true);
      }
   }

   @Inject(method = "isVisible", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$hideGroupedChapter(CallbackInfoReturnable<Boolean> cir) {
      if (ChapterGroupVisibility.isHidden(this.getGroup())) {
         cir.setReturnValue(false);
      }
   }

   @Inject(method = "getRelativeProgressFromChildren", at = @At("HEAD"), cancellable = true, remap = false)
   private void tensuraTno$matchInvisibleChapterProgress(CallbackInfoReturnable<Integer> cir) {
      if (ChapterGroupVisibility.isHidden(this.getGroup())) {
         cir.setReturnValue(100);
      }
   }
}
