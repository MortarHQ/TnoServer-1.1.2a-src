package com.tensura_tno.mixin.client;

import com.tensura_tno.ftb.ChapterGroupVisibility;
import java.util.List;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.Shadow;

@Pseudo
@Mixin(targets = "dev.ftb.mods.ftbquests.quest.QuestObjectBase", remap = false)
public abstract class FTBQuestObjectTagsMixin implements ChapterGroupVisibility.TagAccess {
   @Shadow
   private List<String> tags;

   @Override
   public List<String> tensuraTno$getQuestTags() {
      return this.tags;
   }

   @Override
   public void tensuraTno$setQuestTags(List<String> tags) {
      this.tags = tags;
   }
}
