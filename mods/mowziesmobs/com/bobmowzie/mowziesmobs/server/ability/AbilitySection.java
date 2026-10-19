package com.bobmowzie.mowziesmobs.server.ability;

public abstract class AbilitySection {
   public final AbilitySection.AbilitySectionType sectionType;

   protected AbilitySection(AbilitySection.AbilitySectionType sectionType) {
      this.sectionType = sectionType;
   }

   public static class AbilitySectionDuration extends AbilitySection {
      public final int duration;

      public AbilitySectionDuration(AbilitySection.AbilitySectionType sectionType, int duration) {
         super(sectionType);
         this.duration = duration;
      }
   }

   public static class AbilitySectionInfinite extends AbilitySection {
      public AbilitySectionInfinite(AbilitySection.AbilitySectionType sectionType) {
         super(sectionType);
      }
   }

   public static class AbilitySectionInstant extends AbilitySection {
      public AbilitySectionInstant(AbilitySection.AbilitySectionType sectionType) {
         super(sectionType);
      }
   }

   public enum AbilitySectionType {
      STARTUP,
      ACTIVE,
      RECOVERY,
      MISC;
   }
}
