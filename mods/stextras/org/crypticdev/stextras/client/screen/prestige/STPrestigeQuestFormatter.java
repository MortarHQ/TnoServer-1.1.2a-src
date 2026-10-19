package org.crypticdev.stextras.client.screen.prestige;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.definition.QuestRarity;
import org.crypticdev.stextras.quest.definition.QuestRewardItem;
import org.crypticdev.stextras.quest.definition.QuestRewardSet;
import org.crypticdev.stextras.quest.definition.QuestRewardSkill;
import org.crypticdev.stextras.quest.definition.QuestSubordinateMode;
import org.crypticdev.stextras.storage.STExtarsStorage;

public final class STPrestigeQuestFormatter {
   private static final Set<String> SPIRIT_LEVELS = Set.of("lesser", "medium", "greater", "lord");
   private static final Set<String> SPIRIT_ELEMENTS = Set.of("darkness", "earth", "fire", "light", "space", "water", "wind", "holy", "unidentified");
   private static final int COLOR_TITLE = 16755200;
   private static final int COLOR_LABEL = 11184810;
   private static final int COLOR_TEXT = 16777215;
   private static final int COLOR_MUTED = 8947848;
   private static final int COLOR_SUCCESS = 5635925;
   private static final int COLOR_REWARD = 16777045;
   private static final int COLOR_HINT = 5636095;
   private static final int COLOR_WARNING = 16733525;

   public static String prettyRaceName(ResourceLocation id) {
      return formatTypeName(id.getPath());
   }

   public static int colorForRarity(QuestRarity rarity) {
      if (rarity == null) {
         return 16777215;
      }

      return switch (rarity) {
         case NOVICE -> 16777215;
         case ADEPT -> 5635925;
         case INTERMEDIATE -> 5592575;
         case EXPERT -> 11141290;
         case MASTER -> 16755200;
      };
   }

   public static String trimToWidthScaled(Font font, String text, int pxMax, float scale) {
      int maxWidth = (int)Math.floor(pxMax / Math.max(0.001F, scale));
      if (font.width(text) <= maxWidth) {
         return text;
      }

      String ellipsis = "...";
      int ellipsisWidth = font.width(ellipsis);
      int currentWidth = 0;
      StringBuilder builder = new StringBuilder();

      for (int i = 0; i < text.length(); i++) {
         int charWidth = font.width(String.valueOf(text.charAt(i)));
         if (currentWidth + charWidth + ellipsisWidth > maxWidth) {
            break;
         }

         builder.append(text.charAt(i));
         currentWidth += charWidth;
      }

      builder.append(ellipsis);
      return builder.toString();
   }

   public static String createQuestDescription(QuestDefinition quest, boolean shiftDown) {
      String name = quest.name != null ? quest.name : "Unknown quest";
      String type = targetSummary(quest, shiftDown);
      int amount = Math.max(quest.amount, 1);

      return switch (quest.creator) {
         case MASTER -> String.format("Master %s.", type);
         case KILL -> String.format("Kill %d %s.", amount, pluralize(type, amount));
         case COOK -> String.format("Cook %d %s.", amount, pluralize(type, amount));
         case BREED -> String.format("Breed %d %s.", amount, pluralize(type, amount));
         case CRAFT -> String.format("Craft %d %s.", amount, pluralize(type, amount));
         case MINE -> String.format("Mine %d %s.", amount, pluralize(type, amount));
         case FARM -> String.format("Harvest %d %s.", amount, pluralize(type, amount));
         case NAME -> String.format("Name %s.", type);
         case SMITH_MINECRAFT -> String.format("Smith %d %s using vanilla smithing.", amount, pluralize(type, amount));
         case SMITH_TENSURA -> String.format("Smith %d %s using Tensura smithing.", amount, pluralize(type, amount));
         case SPIRITUAL_DAMAGE -> String.format("Deal %d spiritual damage to %s.", amount, pluralize(type, amount));
         case DAMAGE -> damageDescription(quest, amount, type, shiftDown);
         case POSSESS -> String.format("Possess %d %s.", amount, pluralize(type, amount));
         case PLUNDER -> String.format("Plunder %d %s.", amount, pluralize(type, amount));
         case SPIRIT_CONTRACT -> String.format("Form %d %s with %s.", amount, pluralize("spirit contract", amount), spiritRequirement(quest, shiftDown));
         case ENCHANT -> enchantDescription(quest, amount, shiftDown);
         case MINING_STATION -> String.format("Collect %d %s from the Tensura mining station.", amount, pluralize(type, amount));
         case WOODCUT -> String.format("Collect %d %s from the Tensura woodcutter.", amount, pluralize(type, amount));
         case SPELL_BINDING -> String.format("Bind %d %s through the Tensura spellbinding table.", amount, pluralize(type, amount));
         case EP_REACH -> String.format("Reach at least %d max EP.", amount);
         case EQUIP_EVOLVE -> equipEvolveDescription(quest, amount, shiftDown);
         case TRADE -> String.format("Obtain %d %s from villager trading.", amount, pluralize(type, amount));
         case CONSUME -> String.format("Consume %d %s.", amount, pluralize(type, amount));
         case MASTER_TYPE, MASTER_TYPE_MAGIC, MASTER_SUBTYPE_MAGIC -> masterTypeDescription(quest, amount, type, shiftDown);
         default -> String.format("Complete the quest: %s.", name);
      };
   }

   public static String formatRewards(QuestDefinition quest, int resetCounter) {
      return formatRewards(quest, resetCounter, false);
   }

   public static String formatRewards(QuestDefinition quest, int resetCounter, boolean shiftDown) {
      return quest.rewards != null && !quest.rewards.isEmpty() ? formatRewardSet(quest.rewards, quest, resetCounter, shiftDown, true) : "None";
   }

   public static String typeSummary(QuestDefinition quest) {
      if (quest.creator != null) {
         return switch (quest.creator) {
            case SPIRIT_CONTRACT -> compactSpiritSummary(quest);
            case ENCHANT -> compactEnchantSummary(quest);
            case EP_REACH -> "Max EP";
            default -> typeTargetSummary(quest);
         };
      } else {
         return typeTargetSummary(quest);
      }
   }

   private static String typeTargetSummary(QuestDefinition quest) {
      if (!quest.types.isEmpty()) {
         return quest.types.size() > 2 ? "Various" : quest.types.stream().map(STPrestigeQuestFormatter::formatResourceName).collect(Collectors.joining(", "));
      } else {
         return quest.mobCategory != null ? formatTypeName(quest.mobCategory.name()) : "-";
      }
   }

   public static MutableComponent buildQuestTooltip(
      QuestDefinition quest,
      ResourceLocation id,
      boolean completed,
      int progress,
      int amount,
      boolean showProgress,
      String progressTextOverride,
      int resetCounter,
      boolean shiftDown
   ) {
      String questName = quest.name != null ? quest.name : id.toString();
      String description = createQuestDescription(quest, shiftDown);
      String rarity = quest.rarity != null ? quest.rarity.name() : "-";
      int rarityColor = colorForRarity(quest.rarity);
      String progressText = progressTextOverride != null ? progressTextOverride : (completed ? "Completed" : (showProgress ? progress + "/" + amount : "0/1"));
      String rewardText = formatRewards(quest, resetCounter, shiftDown);
      List<String> details = questDetails(quest, shiftDown);
      List<String> subordinateDetails = subordinateDetails(quest, resetCounter, shiftDown);
      MutableComponent tooltip = Component.literal("");
      tooltip.append(Component.literal(questName).withColor(16755200));
      tooltip.append(Component.literal("\n"));
      tooltip.append(Component.literal(description).withColor(11184810));
      tooltip.append(Component.literal("\n\n"));
      appendLine(tooltip, "Rarity", rarity, rarityColor);
      appendLine(tooltip, "Target", typeSummary(quest), 16777215);
      appendLine(tooltip, "Progress", progressText, completed ? 5635925 : 16777045);
      if (!subordinateDetails.isEmpty()) {
         tooltip.append(Component.literal("\n\nSubordinate").withColor(16755200));

         for (String detail : subordinateDetails) {
            int color = detail.startsWith("Warning:") ? 16733525 : 16777215;
            tooltip.append(Component.literal("\n - ").withColor(8947848)).append(Component.literal(detail).withColor(color));
         }
      }

      if (!details.isEmpty()) {
         tooltip.append(Component.literal("\n\nDetails").withColor(16755200));

         for (String detail : details) {
            tooltip.append(Component.literal("\n - ").withColor(8947848)).append(Component.literal(detail).withColor(16777215));
         }
      }

      tooltip.append(Component.literal("\n\nRewards").withColor(16755200));
      tooltip.append(Component.literal("\n"));
      tooltip.append(Component.literal(rewardText).withColor(16777045));
      if (hasExpandableInfo(quest) && !shiftDown) {
         tooltip.append(Component.literal("\n\nHold Shift for more details.").withColor(5636095));
      }

      return tooltip;
   }

   public static MutableComponent buildRaceTooltip(
      ResourceLocation raceId, boolean completed, List<QuestDefinition> raceQuests, Set<ResourceLocation> completedRaceQuestIds
   ) {
      MutableComponent tooltip = Component.literal("")
         .append(Component.literal(completed ? "Completed" : "In progress").withColor(completed ? 5635925 : 16777045))
         .append(Component.literal("\n"))
         .append(Component.literal("Race: ").withColor(11184810))
         .append(Component.literal(prettyRaceName(raceId)).withColor(16777215))
         .append(Component.literal("\n\n"));
      if (!raceQuests.isEmpty()) {
         tooltip.append(Component.literal("Race Quests").withColor(16755200));

         for (QuestDefinition quest : raceQuests) {
            boolean questCompleted = completedRaceQuestIds.contains(quest.id);
            String questName = quest.name != null ? quest.name : formatResourceName(quest.id);
            tooltip.append(Component.literal("\n"))
               .append(Component.literal(questCompleted ? "✓ " : "○ ").withColor(questCompleted ? 5635925 : 11184810))
               .append(Component.literal(questName).withColor(16777215));
         }
      } else {
         tooltip.append(Component.literal("No quests found for this race.").withColor(11184810));
      }

      return tooltip;
   }

   private static void appendLine(MutableComponent tooltip, String label, String value, int valueColor) {
      tooltip.append(Component.literal("\n" + label + ": ").withColor(11184810)).append(Component.literal(value).withColor(valueColor));
   }

   private static List<String> questDetails(QuestDefinition quest, boolean shiftDown) {
      List<String> details = new ArrayList<>();
      if (quest != null && quest.creator != null) {
         switch (quest.creator) {
            case FARM:
               String stage = stageRequirementDescription(quest);
               if (!stage.isEmpty()) {
                  details.add(stage);
               } else {
                  details.add("Requires fully grown crops.");
               }
            case NAME:
            case SMITH_MINECRAFT:
            case SMITH_TENSURA:
            case SPIRITUAL_DAMAGE:
            case POSSESS:
            default:
               break;
            case DAMAGE:
               if (shiftDown) {
                  List<String> filters = damageFilterNames(quest);
                  if (!filters.isEmpty()) {
                     details.add("Valid damage types: " + String.join(", ", filters) + ".");
                  }
               }
               break;
            case PLUNDER:
               if (quest.types != null && quest.types.size() > 1 && shiftDown) {
                  details.add("Can match listed entities or listed skills.");
               }
               break;
            case SPIRIT_CONTRACT:
               if (quest.spiritLevel != null) {
                  details.add("Spirit level required: " + formatResourceName(quest.spiritLevel) + ".");
               }
               break;
            case ENCHANT:
               if (shiftDown) {
                  List<String> itemOptions = enchantItemOptions(quest);
                  List<String> enchantOptions = enchantmentOptions(quest);
                  if (itemOptions.size() > 1) {
                     details.add("Valid items: " + String.join(", ", itemOptions) + ".");
                  }

                  if (enchantOptions.size() > 1) {
                     details.add("Valid enchantments: " + String.join(", ", enchantOptions) + ".");
                  }
               }
         }

         if (quest.creator != QuestObjectiveType.FARM) {
            String stage = stageRequirementDescription(quest);
            if (!stage.isEmpty()) {
               details.add(stage);
            }
         }

         if (quest.tags != null && !quest.tags.isEmpty()) {
            if (shiftDown) {
               details.add("Entity tags: " + String.join(", ", quest.tags));
            } else {
               details.add("Entity tag filter active.");
            }
         }

         if (quest.cooldownSeconds >= 0) {
            details.add("Cooldown: " + formatCooldown(quest.cooldownSeconds) + ".");
         }

         return details;
      } else {
         return details;
      }
   }

   private static List<String> subordinateDetails(QuestDefinition quest, int resetCounter, boolean shiftDown) {
      List<String> details = new ArrayList<>();
      if (quest != null && quest.subordinate != null && quest.subordinate != QuestSubordinateMode.NONE) {
         switch (quest.subordinate) {
            case HELP:
               details.add("Mode: Help");
               details.add("Your subordinate can help progress this quest, but you can also progress it yourself.");
               break;
            case ONLY:
               details.add("Mode: Only");
               details.add("Only your subordinate can progress this quest.");
               break;
            default:
               return details;
         }

         if (!QuestSubordinateMode.supports(quest.creator)) {
            details.add("Warning: this quest type does not currently support subordinate progress.");
         }

         if (quest.subordinateRewards != null && !quest.subordinateRewards.isEmpty()) {
            if (shiftDown) {
               String rewards = formatRewardSet(quest.subordinateRewards, quest, resetCounter, true, false);
               if (!rewards.isBlank() && !"None".equals(rewards)) {
                  details.add("Subordinate rewards:");

                  for (String rewardLine : rewards.split("\n")) {
                     if (!rewardLine.isBlank()) {
                        details.add("  " + rewardLine);
                     }
                  }
               }
            } else {
               details.add("Subordinate rewards available. Hold Shift to view them.");
            }
         }

         return details;
      } else {
         return details;
      }
   }

   private static String formatRewardSet(QuestRewardSet rewards, QuestDefinition quest, int resetCounter, boolean shiftDown, boolean applyReBoost) {
      if (rewards != null && !rewards.isEmpty()) {
         List<String> parts = new ArrayList<>();
         if (rewards.re != null && rewards.re > 0) {
            int re = rewards.re;
            if (applyReBoost) {
               re = STExtarsStorage.Player.GetREBoosted(resetCounter, rewards.re, quest.category);
            }

            parts.add("RE " + re);
         }

         if (rewards.xp != null && rewards.xp > 0) {
            parts.add("XP " + rewards.xp);
         }

         if (rewards.items != null && !rewards.items.isEmpty()) {
            for (QuestRewardItem item : rewards.items) {
               if (item != null && item.id != null) {
                  int count = Math.max(1, item.count);
                  parts.add(formatResourceName(item.id) + " x" + count);
               }
            }
         }

         if (rewards.skills != null && !rewards.skills.isEmpty()) {
            for (QuestRewardSkill skill : rewards.skills) {
               if (skill != null && skill.id != null) {
                  parts.add(formatSkillReward(skill));
               }
            }
         }

         if (rewards.commands != null && !rewards.commands.isEmpty()) {
            if (shiftDown) {
               parts.add("Commands:");

               for (String command : rewards.commands) {
                  if (command != null && !command.isBlank()) {
                     parts.add(" - /" + command);
                  }
               }
            } else {
               parts.add("Commands x" + rewards.commands.size());
               parts.add("[Shift to view reward commands]");
            }
         }

         return parts.isEmpty() ? "None" : String.join("\n", parts);
      } else {
         return "None";
      }
   }

   private static boolean hasExpandableInfo(QuestDefinition quest) {
      if (quest == null) {
         return false;
      } else if (quest.types != null && quest.types.size() > 1) {
         return true;
      } else if (quest.damageTypes != null && quest.damageTypes.size() > 1) {
         return true;
      } else if (quest.damageTypeTags != null && quest.damageTypeTags.size() > 1) {
         return true;
      } else if (quest.creator == QuestObjectiveType.ENCHANT) {
         return enchantItemOptions(quest).size() > 1 || enchantmentOptions(quest).size() > 1;
      } else if (quest.tags != null && !quest.tags.isEmpty()) {
         return true;
      } else {
         return quest.rewards != null && quest.rewards.commands != null && !quest.rewards.commands.isEmpty()
            ? true
            : quest.subordinateRewards != null && !quest.subordinateRewards.isEmpty();
      }
   }

   private static String formatSkillReward(QuestRewardSkill skill) {
      List<String> extra = new ArrayList<>();
      if (skill.mastery >= 0.0) {
         extra.add("Mastery " + trimNumber(skill.mastery));
      }

      if (skill.removeTime >= 0) {
         extra.add("Temporary " + skill.removeTime + "t");
      }

      if (skill.noMagiculeCost) {
         extra.add("No magicule cost");
      }

      String name = formatResourceName(skill.id);
      return extra.isEmpty() ? "Skill: " + name : "Skill: " + name + " (" + String.join(", ", extra) + ")";
   }

   private static String targetSummary(QuestDefinition quest, boolean shiftDown) {
      List<String> formattedTypes = quest.types.stream().map(id -> formatTargetName(quest, id)).toList();
      if (formattedTypes.isEmpty()) {
         return quest.mobCategory != null ? formatTypeName(quest.mobCategory.name()) : genericTargetName(quest);
      } else {
         return optionSummary(formattedTypes, "valid options", "Valid options", shiftDown);
      }
   }

   private static String optionSummary(List<String> values, String collapsedLabel, String expandedHeader, boolean shiftDown) {
      if (values.isEmpty()) {
         return "";
      }

      if (values.size() == 1) {
         return values.get(0);
      }

      if (!shiftDown) {
         return collapsedLabel;
      }

      StringBuilder extra = new StringBuilder(collapsedLabel).append("\n").append(expandedHeader).append(":");

      for (String line : values) {
         extra.append("\n - ").append(line);
      }

      return extra.toString();
   }

   private static List<String> damageFilterNames(QuestDefinition quest) {
      List<String> filters = new ArrayList<>();

      for (ResourceLocation id : quest.damageTypes) {
         filters.add(formatResourceWithId(id));
      }

      for (ResourceLocation id : quest.damageTypeTags) {
         filters.add(formatTypeName(id.getPath()) + " damage tag (#" + id + ")");
      }

      return filters;
   }

   private static String resourceFilterSummary(List<ResourceLocation> ids, String anyLabel, String collapsedLabel, String expandedHeader, boolean shiftDown) {
      List<String> values = ids.stream().map(id -> isAny(id) ? anyLabel : formatResourceName(id)).toList();
      return optionSummary(values, collapsedLabel, expandedHeader, shiftDown);
   }

   private static String damageDescription(QuestDefinition quest, int amount, String type, boolean shiftDown) {
      List<String> filters = damageFilterNames(quest);
      return filters.size() == 1
         ? String.format("Deal %d %s damage to %s.", amount, filters.get(0), pluralize(type, amount))
         : String.format("Deal %d damage to %s.", amount, pluralize(type, amount));
   }

   private static String enchantDescription(QuestDefinition quest, int amount, boolean shiftDown) {
      List<String> itemOptions = enchantItemOptions(quest);
      List<String> enchantOptions = enchantmentOptions(quest);
      String itemName = enchantPrimaryItemName(itemOptions, amount);
      String enchantName = enchantPrimaryEnchantmentName(enchantOptions);
      String levelText = enchantLevelSuffix(quest);
      boolean hasItem = !itemOptions.isEmpty();
      boolean hasEnchantment = !enchantOptions.isEmpty();
      if (hasItem && hasEnchantment) {
         return String.format("Enchant %d %s with %s%s.", amount, itemName, enchantName, levelText);
      } else if (hasItem) {
         return String.format("Enchant %d %s.", amount, itemName);
      } else if (hasEnchantment) {
         return String.format("Enchant %d %s with %s%s.", amount, pluralize("item", amount), enchantName, levelText);
      } else {
         return !levelText.isEmpty()
            ? String.format("Enchant %d %s at %s.", amount, pluralize("item", amount), levelText.trim())
            : String.format("Enchant %d %s.", amount, pluralize("item", amount));
      }
   }

   private static String equipEvolveDescription(QuestDefinition quest, int amount, boolean shiftDown) {
      List<String> targets = quest.types.stream().filter(id -> id != null && !isAny(id)).map(STPrestigeQuestFormatter::formatResourceName).toList();
      if (targets.isEmpty()) {
         return String.format("Evolve %d %s.", amount, pluralize("item", amount));
      } else {
         return targets.size() == 1
            ? String.format("Evolve %d %s into %s.", amount, pluralize("item", amount), targets.get(0))
            : String.format("Evolve %d %s into valid gear.", amount, pluralize("item", amount));
      }
   }

   private static String masterTypeDescription(QuestDefinition quest, int amount, String type, boolean shiftDown) {
      String target = targetSummary(quest, shiftDown);
      if (quest.types.size() > 1 && !shiftDown) {
         return String.format("Master %d valid %s.", amount, pluralize("skill", amount));
      }

      return switch (quest.creator) {
         case MASTER_TYPE -> String.format("Master %d %s %s.", amount, target, pluralize("skill", amount));
         case MASTER_TYPE_MAGIC -> String.format("Master %d %s magic %s.", amount, target, pluralize("skill", amount));
         case MASTER_SUBTYPE_MAGIC -> String.format("Master %d %s magic %s.", amount, target, pluralize("skill", amount));
         default -> String.format("Master %d %s.", amount, pluralize(type, amount));
      };
   }

   private static String compactEnchantSummary(QuestDefinition quest) {
      List<String> itemOptions = enchantItemOptions(quest);
      List<String> enchantOptions = enchantmentOptions(quest);
      if (!itemOptions.isEmpty()) {
         return itemOptions.size() > 2 ? "Various Items" : String.join(", ", itemOptions);
      } else if (!enchantOptions.isEmpty()) {
         return enchantOptions.size() > 2 ? "Various Enchantments" : String.join(", ", enchantOptions);
      } else {
         return "Any Item";
      }
   }

   private static List<String> enchantItemOptions(QuestDefinition quest) {
      List<ResourceLocation> ids = new ArrayList<>();
      if (quest.enchantItems != null && !quest.enchantItems.isEmpty()) {
         ids.addAll(quest.enchantItems);
      } else if (quest.types != null && !quest.types.isEmpty()) {
         ids.addAll(quest.types);
      }

      return ids.stream().filter(id -> id != null && !isAny(id)).map(STPrestigeQuestFormatter::formatResourceName).toList();
   }

   private static List<String> enchantmentOptions(QuestDefinition quest) {
      return quest.enchantments != null && !quest.enchantments.isEmpty()
         ? quest.enchantments.stream().filter(id -> id != null && !isAny(id)).map(STPrestigeQuestFormatter::formatResourceName).toList()
         : List.of();
   }

   private static String enchantPrimaryItemName(List<String> itemOptions, int amount) {
      if (itemOptions.isEmpty()) {
         return pluralize("item", amount);
      } else {
         return itemOptions.size() == 1 ? pluralize(itemOptions.get(0), amount) : pluralize("valid item", amount);
      }
   }

   private static String enchantPrimaryEnchantmentName(List<String> enchantOptions) {
      if (enchantOptions.isEmpty()) {
         return "";
      } else {
         return enchantOptions.size() == 1 ? enchantOptions.get(0) : "valid enchantments";
      }
   }

   private static String enchantLevelSuffix(QuestDefinition quest) {
      int min = quest.minEnchantLevel;
      int max = quest.maxEnchantLevel;
      if (min < 0 && max < 0) {
         return "";
      } else if (min >= 0 && max >= 0 && min == max) {
         return " " + romanNumeral(min);
      } else if (min >= 0 && max >= 0) {
         return " " + romanNumeral(min) + "-" + romanNumeral(max);
      } else {
         return min >= 0 ? " " + romanNumeral(min) + "+" : " " + romanNumeral(max) + " or lower";
      }
   }

   private static String romanNumeral(int number) {
      return switch (number) {
         case 1 -> "I";
         case 2 -> "II";
         case 3 -> "III";
         case 4 -> "IV";
         case 5 -> "V";
         case 6 -> "VI";
         case 7 -> "VII";
         case 8 -> "VIII";
         case 9 -> "IX";
         case 10 -> "X";
         default -> String.valueOf(number);
      };
   }

   private static String enchantLevelRequirement(QuestDefinition quest) {
      int min = quest.minEnchantLevel;
      int max = quest.maxEnchantLevel;
      if (min < 0 && max < 0) {
         return "";
      } else if (min >= 0 && max >= 0 && min == max) {
         return "level " + min;
      } else if (min >= 0 && max >= 0) {
         return "levels " + min + "-" + max;
      } else {
         return min >= 0 ? "level " + min + "+" : "level " + max + " or lower";
      }
   }

   private static String spiritRequirement(QuestDefinition quest, boolean shiftDown) {
      List<String> elements = new ArrayList<>();
      List<String> levels = new ArrayList<>();

      for (ResourceLocation id : quest.types) {
         if (isAny(id)) {
            return quest.spiritLevel != null ? formatResourceName(quest.spiritLevel) + " spirits of any element" : "any spirit";
         }

         if (isSpiritLevel(id)) {
            levels.add(formatResourceName(id));
         } else if (isSpiritElement(id)) {
            elements.add(formatResourceName(id));
         } else {
            elements.add(formatResourceName(id));
         }
      }

      if (quest.spiritLevel != null) {
         levels.clear();
         levels.add(formatResourceName(quest.spiritLevel));
      }

      List<String> options = spiritOptionNames(elements, levels);
      return options.isEmpty() ? "any spirit" : optionSummary(options, "valid spirit options", "Spirit options", shiftDown);
   }

   private static String compactSpiritSummary(QuestDefinition quest) {
      if (quest.types.isEmpty() && quest.spiritLevel == null) {
         return "Any Spirit";
      }

      if (quest.types.stream().anyMatch(STPrestigeQuestFormatter::isAny)) {
         return quest.spiritLevel != null ? formatResourceName(quest.spiritLevel) + " Spirit" : "Any Spirit";
      }

      List<String> parts = new ArrayList<>();

      for (ResourceLocation id : quest.types) {
         parts.add(formatResourceName(id));
      }

      if (quest.spiritLevel != null && parts.stream().noneMatch(part -> part.equals(formatResourceName(quest.spiritLevel)))) {
         parts.add(formatResourceName(quest.spiritLevel));
      }

      return parts.size() > 2 ? "Various Spirits" : String.join(", ", parts);
   }

   private static String formatResourceName(ResourceLocation id) {
      return isAny(id) ? "Any" : formatTypeName(id.getPath());
   }

   private static String formatTargetName(QuestDefinition quest, ResourceLocation id) {
      return isAny(id) ? genericTargetName(quest) : formatResourceName(id);
   }

   private static String genericTargetName(QuestDefinition quest) {
      if (quest != null && quest.creator != null) {
         return switch (quest.creator) {
            case MASTER, SPELL_BINDING, MASTER_TYPE, MASTER_TYPE_MAGIC, MASTER_SUBTYPE_MAGIC -> "any skill";
            case KILL, BREED, NAME, SPIRITUAL_DAMAGE, DAMAGE, POSSESS -> "any entity";
            case COOK, CRAFT, SMITH_MINECRAFT, SMITH_TENSURA, MINING_STATION, WOODCUT, EQUIP_EVOLVE, TRADE, CONSUME -> "any item";
            case MINE, FARM -> "any block";
            case PLUNDER -> "any entity or skill";
            case SPIRIT_CONTRACT -> "any spirit";
            case ENCHANT -> "any item";
            case EP_REACH -> "max EP";
         };
      } else {
         return "any target";
      }
   }

   private static String formatResourceWithId(ResourceLocation id) {
      return formatResourceName(id) + " (" + id + ")";
   }

   private static boolean isAny(ResourceLocation id) {
      return id != null && "minecraft".equals(id.getNamespace()) && "any".equals(id.getPath());
   }

   private static boolean isSpiritLevel(ResourceLocation id) {
      return id != null && SPIRIT_LEVELS.contains(id.getPath());
   }

   private static boolean isSpiritElement(ResourceLocation id) {
      return id != null && SPIRIT_ELEMENTS.contains(id.getPath());
   }

   private static List<String> spiritOptionNames(List<String> elements, List<String> levels) {
      List<String> options = new ArrayList<>();
      if (elements.isEmpty() && levels.isEmpty()) {
         return options;
      }

      if (elements.isEmpty()) {
         for (String level : levels) {
            options.add(level + " spirits");
         }

         return options;
      } else if (levels.isEmpty()) {
         for (String element : elements) {
            options.add(element + " spirits");
         }

         return options;
      } else {
         for (String level : levels) {
            for (String element : elements) {
               options.add(level + " " + element + " spirits");
            }
         }

         return options;
      }
   }

   private static String formatTypeName(String rawType) {
      if (rawType != null && !rawType.isEmpty()) {
         String[] parts = rawType.split(":");
         String path = parts.length > 1 ? parts[1] : parts[0];
         String[] words = path.split("_");
         StringBuilder builder = new StringBuilder();

         for (String word : words) {
            if (!word.isEmpty()) {
               builder.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1)).append(" ");
            }
         }

         return builder.toString().trim();
      } else {
         return "Unknown";
      }
   }

   private static String pluralize(String word, int amount) {
      if (amount == 1) {
         return word;
      } else if (word.contains("\n") || word.contains(" or ")) {
         return word;
      } else if (word.endsWith("s")) {
         return word;
      } else {
         return word.endsWith("y") ? word.substring(0, word.length() - 1) + "ies" : word + "s";
      }
   }

   private static String stageRequirementDescription(QuestDefinition quest) {
      if (quest == null) {
         return "";
      } else if (quest.minStage >= 0 && quest.maxStage >= 0 && quest.minStage == quest.maxStage) {
         return "Requires stage " + quest.minStage + ".";
      } else if (quest.minStage >= 0 && quest.maxStage >= 0) {
         return "Requires stages " + quest.minStage + " to " + quest.maxStage + ".";
      } else if (quest.minStage >= 0) {
         return "Requires stage " + quest.minStage + " or higher.";
      } else {
         return quest.maxStage >= 0 ? "Requires stage " + quest.maxStage + " or lower." : "";
      }
   }

   private static String formatCooldown(int seconds) {
      if (seconds < 0) {
         return "";
      }

      if (seconds == 0) {
         return "none";
      }

      int days = seconds / 86400;
      seconds %= 86400;
      int hours = seconds / 3600;
      seconds %= 3600;
      int minutes = seconds / 60;
      seconds %= 60;
      List<String> parts = new ArrayList<>();
      if (days > 0) {
         parts.add(days + "d");
      }

      if (hours > 0) {
         parts.add(hours + "h");
      }

      if (minutes > 0) {
         parts.add(minutes + "m");
      }

      if (seconds > 0 || parts.isEmpty()) {
         parts.add(seconds + "s");
      }

      return String.join(" ", parts);
   }

   private static String trimNumber(double value) {
      return value == Math.rint(value) ? String.valueOf((long)value) : String.valueOf(value);
   }

   private STPrestigeQuestFormatter() {
   }
}
