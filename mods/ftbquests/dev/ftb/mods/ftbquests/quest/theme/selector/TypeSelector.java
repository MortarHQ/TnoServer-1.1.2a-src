package dev.ftb.mods.ftbquests.quest.theme.selector;

import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;

public class TypeSelector extends ThemeSelector {
   public final QuestObjectType type;

   public TypeSelector(QuestObjectType t) {
      this.type = t;
   }

   @Override
   public boolean matches(QuestObjectBase object) {
      return object.getObjectType() == this.type;
   }

   @Override
   public ThemeSelectorType getType() {
      return ThemeSelectorType.TYPE;
   }

   @Override
   public int compareTo(ThemeSelector o) {
      return o instanceof TypeSelector ? ((TypeSelector)o).type.compareTo(this.type) : super.compareTo(o);
   }

   @Override
   public String toString() {
      return this.type.getId();
   }

   @Override
   public int hashCode() {
      return this.type.hashCode();
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else {
         return o instanceof TypeSelector ? this.type == ((TypeSelector)o).type : false;
      }
   }
}
