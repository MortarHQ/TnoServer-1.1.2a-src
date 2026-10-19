package com.tensura_tno.mixin.client;

import com.tensura_tno.QuestNameCache;
import java.lang.Character.UnicodeScript;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Coerce;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "org.crypticdev.stextras.client.screen.prestige.STPrestigeQuestFormatter", remap = false)
public class QuestFormatterLocalizationMixin {
   private static final String FORMATTER_KEY_PREFIX = "tensura_tno.quest_formatter.";
   private static final String[][] MODERN_TEXT = new String[][]{
      {"Kill %s %s.", "description.kill"},
      {"Cook %s %s.", "description.cook"},
      {"Craft %s %s.", "description.craft"},
      {"Mine %s %s.", "description.mine"},
      {"Breed %s %s.", "description.breed"},
      {"Consume %s %s.", "description.consume"},
      {"Harvest %s %s.", "modern.harvest"},
      {"Smith %s %s using vanilla smithing.", "description.minecraft_smith"},
      {"Smith %s %s using Tensura smithing.", "description.tensura_smith"},
      {"Obtain %s %s from villager trading.", "description.obtain_trade"},
      {"Deal %s spiritual damage to %s.", "description.spiritual_damage"},
      {"Deal %s %s damage to %s.", "modern.damage_type"},
      {"Deal %s damage to %s.", "modern.damage"},
      {"Possess %s %s.", "modern.possess"},
      {"Plunder %s %s.", "modern.plunder"},
      {"Form %s %s with %s.", "modern.spirit_contract"},
      {"Enchant %s %s with %s.", "modern.enchant_with"},
      {"Enchant %s %s at %s.", "modern.enchant_level"},
      {"Enchant %s %s.", "modern.enchant"},
      {"Collect %s %s from the Tensura mining station.", "modern.mining_station"},
      {"Collect %s %s from the Tensura woodcutter.", "modern.woodcut"},
      {"Bind %s %s through the Tensura spellbinding table.", "modern.spell_binding"},
      {"Reach at least %s max EP.", "modern.ep_reach"},
      {"Evolve %s %s into valid gear.", "modern.evolve_valid"},
      {"Evolve %s %s into %s.", "modern.evolve_into"},
      {"Evolve %s %s.", "modern.evolve"},
      {"Master %s valid %s.", "modern.master_valid"},
      {"Master %s %s magic %s.", "modern.master_magic"},
      {"Master %s %s skills.", "modern.master_skills"},
      {"Master %s %s skill.", "modern.master_skills"},
      {"Master %s.", "description.master"},
      {"Name %s.", "description.name"},
      {"\nTarget: ", "modern.target"},
      {"\n\nSubordinate", "modern.subordinate"},
      {"\n\nDetails", "modern.details"},
      {"\n\nRewards", "modern.rewards"},
      {"\n\nHold Shift for more details.", "modern.shift_hint"},
      {"Requires fully grown crops.", "modern.fully_grown"},
      {"Requires stages %s to %s.", "modern.stages"},
      {"Requires stage %s or higher.", "modern.stage_min"},
      {"Requires stage %s or lower.", "modern.stage_max"},
      {"Requires stage %s.", "modern.stage"},
      {"Valid damage types: %s.", "modern.damage_types"},
      {"Can match listed entities or listed skills.", "modern.plunder_targets"},
      {"Spirit level required: %s.", "modern.spirit_level"},
      {"Valid items: %s.", "modern.valid_items"},
      {"Valid enchantments: %s.", "modern.valid_enchantments"},
      {"Entity tags: %s", "modern.entity_tags"},
      {"Entity tag filter active.", "modern.entity_tag_filter"},
      {"Cooldown: %s.", "modern.cooldown"},
      {"Mode: Help", "modern.subordinate_help"},
      {"Mode: Only", "modern.subordinate_only"},
      {"Your subordinate can help progress this quest, but you can also progress it yourself.", "modern.subordinate_help_detail"},
      {"Only your subordinate can progress this quest.", "modern.subordinate_only_detail"},
      {"Warning: this quest type does not currently support subordinate progress.", "modern.subordinate_warning"},
      {"Subordinate rewards:", "modern.subordinate_rewards"},
      {"Subordinate rewards available. Hold Shift to view them.", "modern.subordinate_rewards_hint"},
      {"Commands:", "modern.commands"},
      {"Commands x%s", "modern.commands_count"},
      {"[Shift to view reward commands]", "modern.commands_hint"},
      {"Skill: %s (%s)", "modern.skill_details"},
      {"Skill: %s", "modern.skill"},
      {"Mastery %s", "modern.mastery"},
      {"Temporary %st", "modern.temporary"},
      {"No magicule cost", "modern.no_magicule_cost"},
      {"valid options", "description.valid_options"},
      {"Valid options:", "modern.valid_options_header"},
      {"valid spirit options", "modern.valid_spirits"},
      {"Spirit options:", "modern.spirit_options"},
      {"valid enchantments", "modern.valid_enchantments_short"},
      {"Various Items", "modern.various_items"},
      {"Various Enchantments", "modern.various_enchantments"},
      {"Various Spirits", "modern.various_spirits"},
      {"Any Item", "modern.any_item"},
      {"any item", "modern.any_item"},
      {"any items", "modern.any_item"},
      {"any skill", "modern.any_skill"},
      {"any skills", "modern.any_skill"},
      {"any entity", "modern.any_entity"},
      {"any entities", "modern.any_entity"},
      {"any block", "modern.any_block"},
      {"any blocks", "modern.any_block"},
      {"any entity or skill", "modern.any_entity_or_skill"},
      {"Any Spirit", "modern.any_spirit"},
      {"any spirit", "modern.any_spirit"},
      {"any spirits", "modern.any_spirit"},
      {"any target", "modern.any_target"},
      {"any targets", "modern.any_target"},
      {"Max EP", "modern.max_ep"},
      {"max EP", "modern.max_ep"},
      {"Any", "modern.any"},
      {"None", "reward.none"},
      {"none", "reward.none"},
      {"Various", "type.various"},
      {"item", "modern.item"},
      {"items", "modern.item"},
      {"skill", "modern.skill_noun"},
      {"skills", "modern.skill_noun"},
      {"valid item", "modern.valid_item"},
      {"valid items", "modern.valid_item"},
      {"spirit contract", "modern.contract"},
      {"spirit contracts", "modern.contract"},
      {"%s spirits of any element", "modern.spirits_any_element"},
      {"%s spirits", "modern.spirits"},
      {"%s Spirit", "modern.spirits"},
      {"%s or lower", "modern.or_lower"}
   };
   private static final Pattern[] MODERN_PATTERNS = Arrays.stream(MODERN_TEXT)
      .map(row -> Pattern.compile(Pattern.quote(row[0]).replace("%s", "\\E(.+?)\\Q"), 32))
      .toArray(Pattern[]::new);

   private static boolean isTranslationEnabled() {
      return Language.getInstance().has("stextras.quest.name.daily_novice_kill_slime");
   }

   private static String tr(String suffix, String fallback) {
      Language lang = Language.getInstance();
      String key = "tensura_tno.quest_formatter." + suffix;
      return lang.has(key) ? lang.getOrDefault(key) : fallback;
   }

   private static String trf(String suffix, String fallback, Object... args) {
      return String.format(tr(suffix, fallback), args);
   }

   @Inject(method = "prettyRaceName", at = @At("RETURN"), remap = false, cancellable = true)
   private static void translateRaceName(ResourceLocation id, CallbackInfoReturnable<String> cir) {
      String localized = findRaceTranslation(id);
      if (localized != null) {
         cir.setReturnValue(localized);
      }
   }

   @Inject(method = "createQuestDescription", at = @At("RETURN"), remap = false, cancellable = true)
   private static void translateDescription(@Coerce Object quest, boolean shiftDown, CallbackInfoReturnable<String> cir) {
      if (isTranslationEnabled()) {
         String translated = translateDescriptionText((String)cir.getReturnValue());
         if (translated != null) {
            try {
               List<?> types = (List<?>)quest.getClass().getField("types").get(quest);
               if (!types.isEmpty()) {
                  for (Object rl : types) {
                     String ns = (String)rl.getClass().getMethod("getNamespace").invoke(rl);
                     String path = (String)rl.getClass().getMethod("getPath").invoke(rl);
                     String localizedName = tryTranslateRL(ns, path);
                     if (localizedName != null) {
                        String englishName = autoFormatName(path);
                        String pluralEn = englishName.endsWith("s") ? englishName : englishName + "s";
                        translated = translated.replace(pluralEn, localizedName);
                        translated = translated.replace(englishName, localizedName);
                     }
                  }

                  if (types.size() > 1) {
                     String localizedValidOptions = tr("description.valid_options", "valid options");
                     translated = translated.replace(" valid options", " " + localizedValidOptions);
                     translated = translated.replace(" valid option", " " + localizedValidOptions);
                     translated = translated.replace("valid options", localizedValidOptions);
                     translated = translated.replace("valid option", localizedValidOptions);
                  }
               }
            } catch (Exception var12) {
            }

            cir.setReturnValue(translated);
         }
      }
   }

   @Inject(method = "typeSummary", at = @At("RETURN"), remap = false, cancellable = true)
   private static void translateTypeSummary(@Coerce Object quest, CallbackInfoReturnable<String> cir) {
      if (isTranslationEnabled()) {
         String translated = translateModernText((String)cir.getReturnValue());

         try {
            String creator = String.valueOf(quest.getClass().getField("creator").get(quest));
            if (!Set.of("ENCHANT", "SPIRIT_CONTRACT", "EP_REACH").contains(creator)) {
               String generic = buildTranslatedTypeSummary(quest);
               if (generic != null) {
                  translated = generic;
               }
            }
         } catch (ReflectiveOperationException var5) {
         }

         cir.setReturnValue(translated);
      }
   }

   @Inject(method = "buildQuestTooltip", at = @At("RETURN"), remap = false, cancellable = true)
   private static void translateTooltipLabels(
      @Coerce Object quest,
      ResourceLocation id,
      boolean completed,
      int progress,
      int amount,
      boolean showProgress,
      String progressTextOverride,
      int resetCounter,
      boolean shiftDown,
      CallbackInfoReturnable<MutableComponent> cir
   ) {
      MutableComponent tooltip = (MutableComponent)cir.getReturnValue();
      List<Component> siblings = tooltip.getSiblings();
      if (!siblings.isEmpty()) {
         String resolved = resolveQuestDisplayName(quest, id);
         if (resolved != null) {
            siblings.set(0, Component.literal(resolved).setStyle(siblings.get(0).getStyle()));
         }
      }

      if (isTranslationEnabled()) {
         translateComponentTree(tooltip);
      }
   }

   @Inject(method = "buildRaceTooltip", at = @At("RETURN"), remap = false, cancellable = true)
   private static void translateRaceTooltip(
      ResourceLocation raceId, boolean completed, List<?> raceQuests, Set<?> completedIds, CallbackInfoReturnable<MutableComponent> cir
   ) {
      cir.setReturnValue(rebuildRaceTooltip(raceId, completed, raceQuests, completedIds));
   }

   private static MutableComponent rebuildRaceTooltip(ResourceLocation raceId, boolean completed, List<?> raceQuests, Set<?> completedIds) {
      MutableComponent tooltip = Component.literal("");
      tooltip.append(
         Component.literal(completed ? tr("status.completed", "Completed") : tr("status.in_progress", "In progress")).withColor(completed ? 5635925 : 16777045)
      );
      tooltip.append(Component.literal("\n"));
      tooltip.append(Component.literal(tr("label.race", "Race: ")).withColor(11184810));
      tooltip.append(Component.literal(localizeRaceName(raceId)).withColor(16777215));
      tooltip.append(Component.literal("\n\n"));
      if (!raceQuests.isEmpty()) {
         tooltip.append(Component.literal(tr("label.race_quests", "Race Quests:\n")).withColor(16755200));

         for (Object quest : raceQuests) {
            ResourceLocation questId = getQuestId(quest);
            boolean questCompleted = questId != null && completedIds.contains(questId);
            String questName = resolveQuestDisplayName(quest, questId);
            tooltip.append(Component.literal(questCompleted ? "✓ " : "○ ").withColor(questCompleted ? 5635925 : 11184810))
               .append(Component.literal(questName).withColor(16777215))
               .append(Component.literal("\n"));
         }
      } else {
         tooltip.append(Component.literal(tr("label.race_none", "No quests found for this race")).withColor(11184810));
      }

      return tooltip;
   }

   private static void translateComponentTree(MutableComponent component) {
      List<Component> siblings = component.getSiblings();

      for (int i = 0; i < siblings.size(); i++) {
         Component sib = siblings.get(i);
         String litText = getLiteralText(sib);
         if (litText != null) {
            String cn = translateLabel(litText);
            if (cn != null) {
               MutableComponent replacement = Component.literal(cn).setStyle(sib.getStyle());
               sib.getSiblings().forEach(replacement::append);
               siblings.set(i, replacement);
               sib = replacement;
            }
         }

         if (sib instanceof MutableComponent mc && !mc.getSiblings().isEmpty()) {
            translateComponentTree(mc);
         }
      }
   }

   private static String getLiteralText(Component c) {
      try {
         Object contents = c.getContents();
         return (String)contents.getClass().getMethod("text").invoke(contents);
      } catch (Exception e) {
         return null;
      }
   }

   private static String translateLabel(String text) {
      return switch (text) {
         case "\nRarity: " -> tr("label.rarity", "\nRarity: ");
         case "\nType: " -> tr("label.type", "\nType: ");
         case "\nProgress: " -> tr("label.progress", "\nProgress: ");
         case "\n\nRewards:\n" -> tr("label.rewards", "\n\nRewards:\n");
         case "Completed" -> tr("status.completed", "Completed");
         case "In progress" -> tr("status.in_progress", "In progress");
         case "NOVICE" -> tr("rarity.novice", "NOVICE");
         case "INTERMEDIATE" -> tr("rarity.intermediate", "INTERMEDIATE");
         case "ADEPT" -> tr("rarity.adept", "ADEPT");
         case "EXPERT" -> tr("rarity.expert", "EXPERT");
         case "MASTER" -> tr("rarity.master", "MASTER");
         case "None" -> tr("reward.none", "None");
         case "Various" -> tr("type.various", "Various");
         case "Race: " -> tr("label.race", "Race: ");
         case "Race Quests:\n" -> tr("label.race_quests", "Race Quests:\n");
         case "No quests found for this race" -> tr("label.race_none", "No quests found for this race");
         case "\n\n[Shift to see valid options]" -> tr("description.shift_hint", "\n\n[Shift to see valid options]");
         default -> {
            String translated = translateModernText(text);
            yield translated.equals(text) ? null : translated;
         }
      };
   }

   private static String localizeRaceName(ResourceLocation id) {
      String localized = findRaceTranslation(id);
      return localized != null ? localized : autoFormatName(id.getPath());
   }

   private static String findRaceTranslation(ResourceLocation id) {
      Language lang = Language.getInstance();
      String directKey = id.getNamespace() + ".race." + id.getPath();
      if (lang.has(directKey)) {
         return lang.getOrDefault(directKey);
      }

      String legacyKey = "stextras.race." + id.getPath();
      return lang.has(legacyKey) ? lang.getOrDefault(legacyKey) : null;
   }

   private static ResourceLocation getQuestId(Object quest) {
      try {
         return (ResourceLocation)quest.getClass().getField("id").get(quest);
      } catch (Exception e) {
         return null;
      }
   }

   private static String resolveQuestDisplayName(Object quest, ResourceLocation questId) {
      try {
         String name = (String)quest.getClass().getField("name").get(quest);
         String resolved = QuestNameCache.resolveName(questId, name);
         if (resolved != null && !resolved.isBlank()) {
            return resolved;
         }
      } catch (ReflectiveOperationException var4) {
      }

      return questId != null ? autoFormatName(questId.getPath()) : tr("misc.unknown", "Unknown");
   }

   private static String tryTranslateRL(String ns, String path) {
      String specialType = tryTranslateTypeIdentifier(path);
      if (specialType != null) {
         return specialType;
      }

      Language lang = Language.getInstance();

      for (String prefix : new String[]{"entity", "item", "block", "enchantment"}) {
         String key = prefix + "." + ns + "." + path;
         if (lang.has(key)) {
            return lang.getOrDefault(key);
         }
      }

      for (String kind : new String[]{"skill", "race"}) {
         String key = ns + "." + kind + "." + path;
         if (lang.has(key)) {
            return lang.getOrDefault(key);
         }
      }

      return null;
   }

   private static String tryTranslateTypeIdentifier(String path) {
      Language lang = Language.getInstance();
      String key = "tensura_tno.quest_formatter.type." + path;
      return lang.has(key) ? lang.getOrDefault(key) : null;
   }

   private static String buildTranslatedTypeSummary(Object quest) {
      try {
         Field typesField = quest.getClass().getField("types");
         List<?> types = (List<?>)typesField.get(quest);
         if (types.isEmpty()) {
            return null;
         }

         if (types.size() > 2) {
            return tr("type.various", "Various");
         }

         List<String> names = new ArrayList<>();

         for (Object rl : types) {
            Method getNs = rl.getClass().getMethod("getNamespace");
            Method getPath = rl.getClass().getMethod("getPath");
            String ns = (String)getNs.invoke(rl);
            String path = (String)getPath.invoke(rl);
            String localized = tryTranslateRL(ns, path);
            if (localized == null) {
               return null;
            }

            names.add(localized);
         }

         return String.join(", ", names);
      } catch (Exception e) {
         return null;
      }
   }

   private static String autoFormatName(String path) {
      if (path != null && !path.isEmpty()) {
         StringBuilder sb = new StringBuilder();

         for (String word : path.split("_")) {
            if (!word.isEmpty()) {
               sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
         }

         return sb.toString().trim();
      } else {
         return "Unknown";
      }
   }

   private static String translateDescriptionText(String text) {
      if (text != null && !text.isEmpty()) {
         if (!text.startsWith("You have to ")) {
            String modern = translateModernText(text);
            if (!modern.equals(text)) {
               return modern;
            }
         }

         String suffix = "";
         String main = text;
         int voIdx = text.indexOf("\n\nValid options:\n");
         if (voIdx >= 0) {
            main = text.substring(0, voIdx);
            suffix = tr("description.valid_options_header", "\n\nValid options:\n") + text.substring(voIdx + "\n\nValid options:\n".length());
         } else if (text.contains("\n\n[Shift to see valid options]")) {
            main = text.replace("\n\n[Shift to see valid options]", "");
            suffix = tr("description.shift_hint", "\n\n[Shift to see valid options]");
         }

         Matcher m = p("You have to kill (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.kill", "You have to kill %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to cook (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.cook", "You have to cook %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to craft (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.craft", "You have to craft %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to mine (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.mine", "You have to mine %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to breed (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.breed", "You have to breed %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to consume (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.consume", "You have to consume %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to tensura smith (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.tensura_smith", "You have to tensura smith %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to minecraft smith (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.minecraft_smith", "You have to minecraft smith %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to obtain (\\d+) (.+) from trading with a villager\\.").matcher(main);
         if (m.matches()) {
            return trf("description.obtain_trade", "You have to obtain %s %s from trading with a villager.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to deal (\\d+) spiritual damage to (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.spiritual_damage", "You have to deal %1$s spiritual damage to %2$s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to farm (\\d+) (.+?)( at stage \\d+| between stages \\d+ and \\d+| from stage \\d+ onward| up to stage \\d+| when fully grown)?\\.")
            .matcher(main);
         if (m.matches()) {
            String stageCn = m.group(3) != null ? translateStageSuffix(m.group(3)) : "";
            return trf("description.farm", "You have to farm %s %s%s.", m.group(1), m.group(2), stageCn) + suffix;
         }

         m = p("You have to master (\\d+) (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.master_count", "You have to master %s %s.", m.group(1), m.group(2)) + suffix;
         }

         m = p("You have to master (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.master", "You have to master %s.", m.group(1)) + suffix;
         }

         m = p("You have to name (.+)\\.").matcher(main);
         if (m.matches()) {
            return trf("description.name", "You have to name %s.", m.group(1)) + suffix;
         }

         m = p("Complete the quest: (.+)\\.").matcher(main);
         return m.matches() ? trf("description.complete", "Complete the quest: %s.", m.group(1)) + suffix : text;
      } else {
         return text;
      }
   }

   private static Pattern p(String regex) {
      return Pattern.compile(regex, 32);
   }

   private static String translateStageSuffix(String s) {
      if (s == null) {
         return "";
      } else {
         s = s.trim();
         Matcher m = Pattern.compile("at stage (\\d+)").matcher(s);
         if (m.find()) {
            return trf("stage.at", " at stage %s", m.group(1));
         } else {
            m = Pattern.compile("between stages (\\d+) and (\\d+)").matcher(s);
            if (m.find()) {
               return trf("stage.between", " between stages %s and %s", m.group(1), m.group(2));
            } else {
               m = Pattern.compile("from stage (\\d+) onward").matcher(s);
               if (m.find()) {
                  return trf("stage.from", " from stage %s onward", m.group(1));
               } else {
                  m = Pattern.compile("up to stage (\\d+)").matcher(s);
                  if (m.find()) {
                     return trf("stage.up_to", " up to stage %s", m.group(1));
                  } else {
                     return s.contains("when fully grown") ? tr("stage.fully_grown", " when fully grown") : s;
                  }
               }
            }
         }
      }
   }

   @Inject(method = "formatResourceName", at = @At("RETURN"), remap = false, cancellable = true, require = 0)
   private static void localizeResourceName(ResourceLocation id, CallbackInfoReturnable<String> cir) {
      if (isTranslationEnabled() && id != null) {
         String localized = tryTranslateRL(id.getNamespace(), id.getPath());
         cir.setReturnValue(localized != null ? localized : translateModernText((String)cir.getReturnValue()));
      }
   }

   @Inject(method = "pluralize", at = @At("HEAD"), remap = false, cancellable = true)
   private static void preserveLocalizedPlural(String word, int amount, CallbackInfoReturnable<String> cir) {
      if (isTranslationEnabled() && word.codePoints().anyMatch(c -> UnicodeScript.of(c) == UnicodeScript.HAN)) {
         cir.setReturnValue(word);
      }
   }

   private static String translateModernText(String text) {
      return translateModernText(text, 0);
   }

   private static String translateModernText(String text, int depth) {
      if (text != null && !text.isEmpty() && depth <= 6 && !text.stripLeading().startsWith("- /")) {
         for (int i = 0; i < MODERN_TEXT.length; i++) {
            if (!text.contains("\n") || MODERN_TEXT[i][0].contains("\n") || MODERN_TEXT[i][0].endsWith(".")) {
               Matcher matcher = MODERN_PATTERNS[i].matcher(text);
               if (matcher.matches()) {
                  String key = "tensura_tno.quest_formatter." + MODERN_TEXT[i][1];
                  Language language = Language.getInstance();
                  if (language.has(key)) {
                     Object[] args = new Object[matcher.groupCount()];

                     for (int group = 1; group <= matcher.groupCount(); group++) {
                        args[group - 1] = translateModernText(matcher.group(group), depth + 1);
                     }

                     return String.format(language.getOrDefault(key), args);
                  }
               }
            }
         }

         if (text.contains("\n")) {
            return Arrays.stream(text.split("\n", -1)).map(line -> translateModernText(line, depth + 1)).collect(Collectors.joining("\n"));
         } else if (text.contains(", ")) {
            return Arrays.stream(text.split(", ", -1)).map(part -> translateModernText(part, depth + 1)).collect(Collectors.joining(", "));
         } else {
            String stripped = text.strip();
            if (!stripped.equals(text) && !stripped.isEmpty()) {
               int start = text.indexOf(stripped);
               return text.substring(0, start) + translateModernText(stripped, depth + 1) + text.substring(start + stripped.length());
            } else {
               return text;
            }
         }
      } else {
         return text;
      }
   }
}
