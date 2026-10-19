package it.hurts.sskirillss.relics.items.relics.base.data;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class RelicSlotModifier {
   private Multimap<String, Integer> modifiers;

   RelicSlotModifier(Multimap<String, Integer> modifiers) {
      this.modifiers = modifiers;
   }

   public static RelicSlotModifier.RelicSlotModifierBuilder builder() {
      return new RelicSlotModifier.RelicSlotModifierBuilder();
   }

   public Multimap<String, Integer> getModifiers() {
      return this.modifiers;
   }

   public void setModifiers(Multimap<String, Integer> modifiers) {
      this.modifiers = modifiers;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof RelicSlotModifier other)) {
         return false;
      } else {
         if (!other.canEqual(this)) {
            return false;
         }

         Object this$modifiers = this.getModifiers();
         Object other$modifiers = other.getModifiers();
         return this$modifiers == null ? other$modifiers == null : this$modifiers.equals(other$modifiers);
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof RelicSlotModifier;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      Object $modifiers = this.getModifiers();
      return result * 59 + ($modifiers == null ? 43 : $modifiers.hashCode());
   }

   @Override
   public String toString() {
      return "RelicSlotModifier(modifiers=" + this.getModifiers() + ")";
   }

   public static class RelicSlotModifierBuilder {
      Multimap<String, Integer> modifiers = ArrayListMultimap.create();

      public RelicSlotModifier.RelicSlotModifierBuilder modifier(String id, int count) {
         this.modifiers.put(id, count);
         return this;
      }

      RelicSlotModifierBuilder() {
      }

      public RelicSlotModifier.RelicSlotModifierBuilder modifiers(Multimap<String, Integer> modifiers) {
         this.modifiers = modifiers;
         return this;
      }

      public RelicSlotModifier build() {
         return new RelicSlotModifier(this.modifiers);
      }

      @Override
      public String toString() {
         return "RelicSlotModifier.RelicSlotModifierBuilder(modifiers=" + this.modifiers + ")";
      }
   }
}
