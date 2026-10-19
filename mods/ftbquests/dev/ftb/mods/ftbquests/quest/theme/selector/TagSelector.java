package dev.ftb.mods.ftbquests.quest.theme.selector;

import dev.ftb.mods.ftbquests.quest.QuestObjectBase;

public class TagSelector extends ThemeSelector {
   public final String tag;

   public TagSelector(String t) {
      this.tag = t;
   }

   @Override
   public boolean matches(QuestObjectBase quest) {
      return quest.hasTag(this.tag);
   }

   @Override
   public ThemeSelectorType getType() {
      return ThemeSelectorType.TAG;
   }

   @Override
   public String toString() {
      return "#" + this.tag;
   }

   @Override
   public int hashCode() {
      return this.tag.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return o instanceof TagSelector ? this.tag.equals(((TagSelector)o).tag) : false;
      }
   }
}
