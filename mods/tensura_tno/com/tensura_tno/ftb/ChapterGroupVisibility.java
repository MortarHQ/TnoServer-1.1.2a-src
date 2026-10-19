package com.tensura_tno.ftb;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.ChapterGroup;
import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.Nullable;

public final class ChapterGroupVisibility {
   public static final String HIDDEN_TAG = "tensura_tno_hidden_chapter_group";

   private ChapterGroupVisibility() {
   }

   public static boolean isHidden(@Nullable ChapterGroup group) {
      return group != null && group.hasTag("tensura_tno_hidden_chapter_group");
   }

   public static boolean isHiddenByGroup(@Nullable Chapter chapter) {
      return chapter != null && isHidden(chapter.getGroup());
   }

   public static void addVisibilityOption(ChapterGroup group, ConfigGroup config) {
      config.addBool("tensura_tno_hidden", isHidden(group), hidden -> setHidden(group, hidden), false).setNameKey("tensura_tno.ftbquests.hide_chapter_group");
   }

   public static void setHidden(ChapterGroup group, boolean hidden) {
      ChapterGroupVisibility.TagAccess access = (ChapterGroupVisibility.TagAccess)group;
      List<String> tags = new ArrayList<>(access.tensuraTno$getQuestTags());
      tags.removeIf("tensura_tno_hidden_chapter_group"::equals);
      if (hidden) {
         tags.add("tensura_tno_hidden_chapter_group");
      }

      access.tensuraTno$setQuestTags(tags);
      group.clearCachedData();
   }

   public interface TagAccess {
      List<String> tensuraTno$getQuestTags();

      void tensuraTno$setQuestTags(List<String> var1);
   }
}
