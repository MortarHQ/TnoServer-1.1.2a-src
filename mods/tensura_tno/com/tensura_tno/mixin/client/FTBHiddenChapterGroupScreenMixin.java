package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.ChapterGroupVisibility;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.quest.Chapter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Pseudo
@Mixin(targets = "dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen", remap = false)
public abstract class FTBHiddenChapterGroupScreenMixin {
   @Shadow
   @Final
   ClientQuestFile file;
   @Shadow
   Chapter selectedChapter;

   @Shadow
   public abstract void selectChapter(Chapter var1);

   @ModifyVariable(method = "selectChapter", at = @At("HEAD"), argsOnly = true, remap = false)
   private Chapter tensuraTno$skipHiddenSelection(Chapter chapter) {
      return !this.file.canEdit() && ChapterGroupVisibility.isHiddenByGroup(chapter) ? this.file.getFirstVisibleChapter(this.file.selfTeamData) : chapter;
   }

   @Inject(method = "tick", at = @At("TAIL"), remap = false)
   private void tensuraTno$closeNewlyHiddenChapter(CallbackInfo ci) {
      if (!this.file.canEdit() && ChapterGroupVisibility.isHiddenByGroup(this.selectedChapter)) {
         this.selectChapter(this.file.getFirstVisibleChapter(this.file.selfTeamData));
      }
   }
}
