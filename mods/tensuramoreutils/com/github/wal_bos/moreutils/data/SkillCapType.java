package com.github.wal_bos.moreutils.data;

import io.github.manasmods.tensura.ability.skill.Skill.SkillType;

public enum SkillCapType {
   UNIQUE(SkillType.UNIQUE),
   ULTIMATE(SkillType.ULTIMATE);

   private final SkillType skillType;

   SkillCapType(SkillType skillType) {
      this.skillType = skillType;
   }

   public SkillType skillType() {
      return this.skillType;
   }

   public static SkillCapType parse(String value) {
      return switch (value.toLowerCase()) {
         case "unique", "uniques" -> UNIQUE;
         case "ultimate", "ult", "ultimates" -> ULTIMATE;
         default -> throw new IllegalArgumentException("Invalid cap type: " + value);
      };
   }

   public String displayName() {
      return this == UNIQUE ? "Unique" : "Ultimate";
   }
}
