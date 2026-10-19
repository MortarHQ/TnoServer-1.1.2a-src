package com.sjyueluo.tensura_skill_books;

import com.sjyueluo.tensura_skill_books.catalog.SkillCategory;
import java.util.EnumMap;
import java.util.Map;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;

public final class TensuraSkillBooksConfig {
   public static final ModConfigSpec SPEC;
   private static final Map<SkillCategory, BooleanValue> RANDOM_BOOKS = new EnumMap<>(SkillCategory.class);

   private TensuraSkillBooksConfig() {
   }

   public static boolean randomBookEnabled(SkillCategory category) {
      BooleanValue value = RANDOM_BOOKS.get(category);
      return value != null && (Boolean)value.get();
   }

   private static void defineRandomBook(Builder builder, SkillCategory category, String key, boolean defaultValue) {
      RANDOM_BOOKS.put(category, builder.comment("Adds the random " + key + " skill study book to Misc, JEI, and EMI.").define("enable_" + key, defaultValue));
   }

   static {
      Builder builder = new Builder();
      builder.push("random_books");
      defineRandomBook(builder, SkillCategory.ALL, "all", true);
      defineRandomBook(builder, SkillCategory.COMBAT, "combat", true);
      defineRandomBook(builder, SkillCategory.COMMON, "common", true);
      defineRandomBook(builder, SkillCategory.RESISTANCE, "resistance", true);
      defineRandomBook(builder, SkillCategory.INTRINSIC, "intrinsic", true);
      defineRandomBook(builder, SkillCategory.EXTRA, "extra", true);
      defineRandomBook(builder, SkillCategory.UNIQUE, "unique", true);
      defineRandomBook(builder, SkillCategory.ULTIMATE, "ultimate", true);
      defineRandomBook(builder, SkillCategory.MAGIC_ASPECTUAL, "magic_aspectual", true);
      defineRandomBook(builder, SkillCategory.MAGIC_SPIRITUAL, "magic_spiritual", true);
      defineRandomBook(builder, SkillCategory.MAGIC_SUMMONING, "magic_summoning", true);
      defineRandomBook(builder, SkillCategory.MAGIC_MISC, "magic_misc", true);
      builder.pop();
      SPEC = builder.build();
   }
}
