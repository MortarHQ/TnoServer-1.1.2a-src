package com.foxablazeaqzl.wiki.client;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public final class FoxWikiCommands {
   private static final String OUTPUT_FILE = "foxablazeaqzl_wiki_missing_skills.txt";

   private FoxWikiCommands() {
   }

   public static <S> void register(CommandDispatcher<S> dispatcher, BiConsumer<S, Component> success, BiConsumer<S, Component> failure) {
      dispatcher.register(
         (LiteralArgumentBuilder)LiteralArgumentBuilder.literal("foxwiki")
            .then(LiteralArgumentBuilder.literal("missing_skills").executes(context -> dumpMissingSkills(context.getSource(), success, failure)))
      );
   }

   private static <S> int dumpMissingSkills(S source, BiConsumer<S, Component> success, BiConsumer<S, Component> failure) {
      Minecraft minecraft = Minecraft.getInstance();
      List<FoxWikiCommands.MissingSkill> missingSkills = collectMissingSkills();
      Path outputPath = minecraft.gameDirectory.toPath().resolve("foxablazeaqzl_wiki_missing_skills.txt");

      try {
         Files.write(outputPath, formatLines(missingSkills), StandardCharsets.UTF_8);
         success.accept(
            source, Component.translatable("foxablazeaqzl_wiki.command.missing_skills.success", new Object[]{missingSkills.size(), outputPath.toString()})
         );
      } catch (IOException exception) {
         failure.accept(source, Component.translatable("foxablazeaqzl_wiki.command.missing_skills.failure", new Object[]{outputPath.toString()}));
         return 0;
      }

      return missingSkills.size();
   }

   private static List<FoxWikiCommands.MissingSkill> collectMissingSkills() {
      List<FoxWikiCommands.MissingSkill> missingSkills = new ArrayList<>();

      for (ManasSkill skill : SkillAPI.getSkillRegistry()) {
         if (skill != null) {
            ResourceLocation skillId = SkillAPI.getSkillRegistry().getId(skill);
            if (skillId != null && !FoxWikiData.hasDescription(skillId)) {
               missingSkills.add(new FoxWikiCommands.MissingSkill(skillId, skillName(skill)));
            }
         }
      }

      missingSkills.sort(Comparator.comparing(entry -> entry.id().toString()));
      return missingSkills;
   }

   private static List<String> formatLines(List<FoxWikiCommands.MissingSkill> missingSkills) {
      boolean chinese = isChineseLanguage();
      return missingSkills.stream().map(entry -> formatLine(entry, chinese)).toList();
   }

   private static String formatLine(FoxWikiCommands.MissingSkill skill, boolean chinese) {
      if (skill.name().isBlank()) {
         return skill.id().toString();
      } else {
         return chinese ? "【" + skill.name() + "】" + skill.id() : skill.name() + " " + skill.id();
      }
   }

   private static String skillName(ManasSkill skill) {
      Component name = skill.getName();
      if (name == null) {
         return "";
      }

      String value = name.getString().trim();
      return !value.isBlank() && !looksLikeTranslationKey(value) ? value : "";
   }

   private static boolean looksLikeTranslationKey(String value) {
      return value.indexOf(32) < 0
         && value.indexOf(46) >= 0
         && value.chars()
            .allMatch(
               character -> character == 46
                  || character == 95
                  || character == 45
                  || character == 58
                  || character >= 97 && character <= 122
                  || character >= 65 && character <= 90
                  || character >= 48 && character <= 57
            );
   }

   private static boolean isChineseLanguage() {
      String languageCode = Minecraft.getInstance().options.languageCode;
      return languageCode != null && languageCode.toLowerCase(Locale.ROOT).startsWith("zh");
   }

   private record MissingSkill(ResourceLocation id, String name) {
   }
}
