package org.crypticdev.stextras.quest.codec;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.definition.QuestRarity;
import org.crypticdev.stextras.quest.definition.QuestRewardItem;
import org.crypticdev.stextras.quest.definition.QuestRewardSet;
import org.crypticdev.stextras.quest.definition.QuestRewardSkill;
import org.crypticdev.stextras.quest.definition.QuestSubordinateMode;
import org.crypticdev.stextras.quest.definition.TensuraQuestTargetIds;
import org.crypticdev.stextras.quest.race.RacePrestigeText;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.slf4j.Logger;

public final class QuestDefinitionJsonParser {
   private static final Logger LOGGER = LogUtils.getLogger();

   public static QuestDefinition parseQuest(ResourceLocation pathKey, JsonObject obj) {
      String[] parts = pathKey.getPath().split("/");
      if (parts.length < 3) {
         throw new IllegalStateException("Bad QuestDefinition path (need <category>/<...>): " + pathKey);
      }

      QuestDefinition QuestDefinition = new QuestDefinition();
      QuestDefinition.category = QuestCategory.fromPath(parts[0]);
      if (QuestDefinition.category == QuestCategory.REQUIRED) {
         QuestDefinition.prestige = Integer.parseInt(parts[1]);
         QuestDefinition.rarity = QuestRarity.fromPath(parts[2]);
         String questId = parts[parts.length - 1];
         String autoId = "required_" + QuestDefinition.prestige + "_" + parts[2] + "_" + questId;
         QuestDefinition.id = ResourceLocation.fromNamespaceAndPath(pathKey.getNamespace(), sanitize(autoId));
      } else if (QuestDefinition.category == QuestCategory.RACE_PRESTIGE) {
         if (obj.has("race_id")) {
            QuestDefinition.race = ResourceLocation.parse(obj.get("race_id").getAsString());
         } else {
            String raceNamespace = parts[1];
            String racePath = parts[2];
            QuestDefinition.race = ResourceLocation.fromNamespaceAndPath(raceNamespace, racePath);
         }

         QuestDefinition.rarity = obj.has("rarity") ? QuestRarity.fromPath(obj.get("rarity").getAsString()) : QuestRarity.NOVICE;
         String creator = getRequiredString(obj, "creator").toLowerCase(Locale.ROOT);
         String typeFlat = "generic";
         if (obj.has("type")) {
            String raw = obj.get("type").toString().replaceAll("[^a-z0-9_]", "_");
            if (raw.length() > 20) {
               raw = raw.substring(0, 20);
            }

            typeFlat = raw;
         }

         String safeRaceNamespace = QuestDefinition.race.getNamespace();
         String safeRacePath = QuestDefinition.race.getPath().replace('/', '_').replace(':', '_');
         String autoId = "raceprestige_" + safeRaceNamespace + "_" + safeRacePath + "_" + creator + "_" + typeFlat;
         QuestDefinition.id = ResourceLocation.fromNamespaceAndPath(pathKey.getNamespace(), sanitize(autoId));
         QuestDefinition.prestige = 0;
      } else {
         QuestDefinition.prestige = 0;
         QuestDefinition.rarity = QuestRarity.fromPath(parts[1]);
         String questId = parts[parts.length - 1];
         String autoId = parts[0] + "_" + parts[1] + "_" + questId;
         QuestDefinition.id = ResourceLocation.fromNamespaceAndPath(pathKey.getNamespace(), sanitize(autoId));
      }

      QuestDefinition.name = getRequiredString(obj, "name");
      QuestDefinition.creator = QuestObjectiveType.fromString(getRequiredString(obj, "creator"));
      parseTypeOrCategory(QuestDefinition, obj);
      parseDamageTypes(QuestDefinition, obj);
      parseSpiritLevel(QuestDefinition, obj);
      parseEnchantFilters(QuestDefinition, obj);
      parseStageConstraints(QuestDefinition, obj);
      if (obj.has("tags") && obj.get("tags").isJsonArray()) {
         QuestDefinition.tags = new ArrayList<>();

         for (JsonElement element : obj.getAsJsonArray("tags")) {
            QuestDefinition.tags.add(element.getAsString());
         }
      }

      QuestDefinition.amount = obj.has("amount") ? Math.max(0, obj.get("amount").getAsInt()) : 0;
      QuestDefinition.rewards = obj.has("rewards") ? parseRewards(obj.getAsJsonObject("rewards")) : new QuestRewardSet();
      QuestDefinition.subordinate = obj.has("subordinate") ? QuestSubordinateMode.fromString(obj.get("subordinate").getAsString()) : QuestSubordinateMode.NONE;
      QuestDefinition.subordinateRewards = obj.has("subordinate_rewards")
         ? parseSubordinateRewards(obj.getAsJsonObject("subordinate_rewards"))
         : new QuestRewardSet();
      if (obj.has("cooldown")) {
         QuestDefinition.cooldownSeconds = obj.get("cooldown").getAsInt();
         if (QuestDefinition.cooldownSeconds < -1) {
            QuestDefinition.cooldownSeconds = -1;
         }
      }

      if (QuestDefinition.creator == QuestObjectiveType.MASTER) {
         QuestDefinition.amount = 0;
      }

      return QuestDefinition;
   }

   public static List<QuestDefinition> parseRacePack(ResourceLocation pathKey, JsonObject root) {
      String[] parts = pathKey.getPath().split("/");
      if (parts.length < 2) {
         throw new IllegalStateException("Bad path");
      }

      String file = parts[parts.length - 1];
      if (file.endsWith(".json")) {
         file = file.substring(0, file.length() - 5);
      }

      ResourceLocation raceId;
      if (root.has("race_id")) {
         raceId = ResourceLocation.parse(root.get("race_id").getAsString());
      } else {
         int index = file.indexOf(95);
         if (index > 0) {
            String namespace = file.substring(0, index);
            String path = file.substring(index + 1);
            raceId = ResourceLocation.fromNamespaceAndPath(namespace, path);
         } else {
            raceId = ResourceLocation.fromNamespaceAndPath(pathKey.getNamespace(), file);
         }
      }

      if (!STExtarsStorage.Quests.isRacePrestigeEnabled(raceId)) {
         return List.of();
      }

      RacePrestigeText lore = readText(root.has("lore_message") ? root.getAsJsonObject("lore_message") : null);
      RacePrestigeText start = readText(root.has("start_message") ? root.getAsJsonObject("start_message") : null);
      RacePrestigeText complete = readText(root.has("completed_message") ? root.getAsJsonObject("completed_message") : null);
      int soulGradeReward = root.has("soul_grade") ? Math.max(0, root.get("soul_grade").getAsInt()) : 0;

      try {
         RacePrestigeMetadataRegistry.INSTANCE.registerRaceMeta(raceId, lore, start, complete, soulGradeReward);
      } catch (Throwable var19) {
      }

      if (root.has("quests") && root.get("quests").isJsonArray()) {
         List<QuestDefinition> out = new ArrayList<>();
         int questIndex = 0;

         for (JsonElement element : root.getAsJsonArray("quests")) {
            JsonObject obj = element.getAsJsonObject();
            QuestDefinition QuestDefinition = new QuestDefinition();
            QuestDefinition.category = QuestCategory.RACE_PRESTIGE;
            QuestDefinition.rarity = QuestRarity.NOVICE;
            QuestDefinition.prestige = 0;
            QuestDefinition.race = raceId;
            QuestDefinition.creator = QuestObjectiveType.fromString(getRequiredString(obj, "creator"));
            String typeSuffix = "";
            if (obj.has("type")) {
               typeSuffix = obj.get("type").isJsonArray() ? "Group" : obj.get("type").getAsString();
            }

            QuestDefinition.name = obj.has("name") ? obj.get("name").getAsString() : QuestDefinition.creator.name().toLowerCase(Locale.ROOT) + " " + typeSuffix;
            parseTypeOrCategory(QuestDefinition, obj);
            parseDamageTypes(QuestDefinition, obj);
            parseSpiritLevel(QuestDefinition, obj);
            parseEnchantFilters(QuestDefinition, obj);
            parseStageConstraints(QuestDefinition, obj);
            if (obj.has("tags") && obj.get("tags").isJsonArray()) {
               QuestDefinition.tags = new ArrayList<>();

               for (JsonElement tagElement : obj.getAsJsonArray("tags")) {
                  QuestDefinition.tags.add(tagElement.getAsString());
               }
            }

            if (obj.has("stat_first")) {
               QuestDefinition.statFirst = obj.get("stat_first").getAsBoolean();
            }

            QuestDefinition.amount = obj.has("amount") ? Math.max(0, obj.get("amount").getAsInt()) : 0;
            if (QuestDefinition.creator == QuestObjectiveType.MASTER) {
               QuestDefinition.amount = 0;
            }

            QuestDefinition.rewards = obj.has("rewards") ? parseRewards(obj.getAsJsonObject("rewards")) : new QuestRewardSet();
            QuestDefinition.subordinate = obj.has("subordinate")
               ? QuestSubordinateMode.fromString(obj.get("subordinate").getAsString())
               : QuestSubordinateMode.NONE;
            QuestDefinition.subordinateRewards = obj.has("subordinate_rewards")
               ? parseSubordinateRewards(obj.getAsJsonObject("subordinate_rewards"))
               : new QuestRewardSet();
            if (obj.has("cooldown")) {
               QuestDefinition.cooldownSeconds = obj.get("cooldown").getAsInt();
               if (QuestDefinition.cooldownSeconds < -1) {
                  QuestDefinition.cooldownSeconds = -1;
               }
            }

            String raceNamespace = raceId.getNamespace();
            String racePath = raceId.getPath().replace('/', '_');
            String autoId = "race_" + raceNamespace + "_" + racePath + "_" + questIndex++;
            QuestDefinition.id = ResourceLocation.fromNamespaceAndPath(pathKey.getNamespace(), autoId);
            out.add(QuestDefinition);
         }

         return out;
      } else {
         throw new IllegalStateException("race prestige pack missing 'quests'");
      }
   }

   private static void parseTypeOrCategory(QuestDefinition QuestDefinition, JsonObject obj) {
      if (obj.has("type")) {
         JsonElement typeElement = obj.get("type");
         if (!typeElement.isJsonArray()) {
            String raw = typeElement.getAsString();
            if (QuestDefinition.creator == QuestObjectiveType.SPIRIT_CONTRACT && !isMinecraftAny(raw)) {
               QuestDefinition.types.add(TensuraQuestTargetIds.parseTensuraDefault(raw));
            } else if (raw.contains(":")) {
               QuestDefinition.types.add(ResourceLocation.parse(raw));
            } else {
               try {
                  QuestDefinition.mobCategory = MobCategory.valueOf(raw.toUpperCase(Locale.ROOT));
               } catch (IllegalArgumentException ex) {
                  try {
                     QuestDefinition.types.add(ResourceLocation.parse(raw));
                  } catch (Exception ignored) {
                     LOGGER.warn("[STExtras] Unknown mob category or invalid type '{}'", raw);
                  }
               }
            }
         } else {
            for (JsonElement element : typeElement.getAsJsonArray()) {
               String raw = element.getAsString();
               QuestDefinition.types
                  .add(
                     QuestDefinition.creator == QuestObjectiveType.SPIRIT_CONTRACT && !isMinecraftAny(raw)
                        ? TensuraQuestTargetIds.parseTensuraDefault(raw)
                        : ResourceLocation.parse(raw)
                  );
            }
         }
      }
   }

   private static void parseDamageTypes(QuestDefinition QuestDefinition, JsonObject obj) {
      if (obj.has("damage_type")) {
         JsonElement damageTypeElement = obj.get("damage_type");
         if (!damageTypeElement.isJsonArray()) {
            addDamageType(QuestDefinition, damageTypeElement.getAsString());
         } else {
            for (JsonElement element : damageTypeElement.getAsJsonArray()) {
               addDamageType(QuestDefinition, element.getAsString());
            }
         }
      }
   }

   private static void addDamageType(QuestDefinition QuestDefinition, String raw) {
      String value = raw.trim();
      if (!value.isEmpty()) {
         if (value.charAt(0) == '#') {
            QuestDefinition.damageTypeTags.add(ResourceLocation.parse(value.substring(1)));
         } else {
            QuestDefinition.damageTypes.add(ResourceLocation.parse(value));
         }
      }
   }

   private static void parseEnchantFilters(QuestDefinition QuestDefinition, JsonObject obj) {
      if (QuestDefinition.creator == QuestObjectiveType.ENCHANT) {
         parseResourceList(obj, "item", QuestDefinition.enchantItems);
         parseResourceList(obj, "enchantment", QuestDefinition.enchantments);
         if (obj.has("level")) {
            int level = Math.max(1, obj.get("level").getAsInt());
            QuestDefinition.minEnchantLevel = level;
            QuestDefinition.maxEnchantLevel = level;
         } else if (obj.has("lvl")) {
            int level = Math.max(1, obj.get("lvl").getAsInt());
            QuestDefinition.minEnchantLevel = level;
            QuestDefinition.maxEnchantLevel = level;
         } else if (obj.has("enchantment_level")) {
            int level = Math.max(1, obj.get("enchantment_level").getAsInt());
            QuestDefinition.minEnchantLevel = level;
            QuestDefinition.maxEnchantLevel = level;
         }

         if (obj.has("min_level")) {
            QuestDefinition.minEnchantLevel = Math.max(1, obj.get("min_level").getAsInt());
         } else if (obj.has("min_lvl")) {
            QuestDefinition.minEnchantLevel = Math.max(1, obj.get("min_lvl").getAsInt());
         } else if (obj.has("min_enchantment_level")) {
            QuestDefinition.minEnchantLevel = Math.max(1, obj.get("min_enchantment_level").getAsInt());
         }

         if (obj.has("max_level")) {
            QuestDefinition.maxEnchantLevel = Math.max(1, obj.get("max_level").getAsInt());
         } else if (obj.has("max_lvl")) {
            QuestDefinition.maxEnchantLevel = Math.max(1, obj.get("max_lvl").getAsInt());
         } else if (obj.has("max_enchantment_level")) {
            QuestDefinition.maxEnchantLevel = Math.max(1, obj.get("max_enchantment_level").getAsInt());
         }

         if (QuestDefinition.minEnchantLevel >= 0 && QuestDefinition.maxEnchantLevel >= 0 && QuestDefinition.maxEnchantLevel < QuestDefinition.minEnchantLevel) {
            QuestDefinition.maxEnchantLevel = QuestDefinition.minEnchantLevel;
         }
      }
   }

   private static void parseResourceList(JsonObject obj, String key, List<ResourceLocation> out) {
      if (obj.has(key)) {
         JsonElement element = obj.get(key);
         if (!element.isJsonArray()) {
            out.add(ResourceLocation.parse(element.getAsString()));
         } else {
            for (JsonElement value : element.getAsJsonArray()) {
               out.add(ResourceLocation.parse(value.getAsString()));
            }
         }
      }
   }

   private static void parseSpiritLevel(QuestDefinition QuestDefinition, JsonObject obj) {
      if (QuestDefinition.creator == QuestObjectiveType.SPIRIT_CONTRACT && obj.has("level")) {
         QuestDefinition.spiritLevel = TensuraQuestTargetIds.parseTensuraDefault(obj.get("level").getAsString());
      }
   }

   private static void parseStageConstraints(QuestDefinition QuestDefinition, JsonObject obj) {
      QuestDefinition.minStage = -1;
      QuestDefinition.maxStage = -1;
      if (obj.has("stage")) {
         int stage = Math.max(0, obj.get("stage").getAsInt());
         QuestDefinition.minStage = stage;
         QuestDefinition.maxStage = stage;
      } else {
         if (obj.has("min_stage")) {
            QuestDefinition.minStage = Math.max(0, obj.get("min_stage").getAsInt());
         }

         if (obj.has("max_stage")) {
            QuestDefinition.maxStage = Math.max(0, obj.get("max_stage").getAsInt());
         }

         if (QuestDefinition.minStage >= 0 && QuestDefinition.maxStage >= 0 && QuestDefinition.maxStage < QuestDefinition.minStage) {
            QuestDefinition.maxStage = QuestDefinition.minStage;
         }
      }
   }

   private static QuestRewardSet parseRewards(JsonObject json) {
      QuestRewardSet rewardSet = new QuestRewardSet();
      if (json.has("xp")) {
         rewardSet.xp = Math.max(0, json.get("xp").getAsInt());
      }

      if (json.has("re")) {
         rewardSet.re = Math.max(0, json.get("re").getAsInt());
      }

      parseRewardCommands(json, rewardSet);
      if (json.has("items")) {
         rewardSet.items = new ArrayList<>();

         for (JsonElement element : json.getAsJsonArray("items")) {
            JsonObject itemObject = element.getAsJsonObject();
            QuestRewardItem rewardItem = new QuestRewardItem();
            rewardItem.id = getRequiredResourceLocation(itemObject, "id");
            rewardItem.count = Math.max(1, itemObject.has("count") ? itemObject.get("count").getAsInt() : 1);
            rewardSet.items.add(rewardItem);
         }
      }

      parseRewardSkills(json, rewardSet);
      return rewardSet;
   }

   private static QuestRewardSet parseSubordinateRewards(JsonObject json) {
      QuestRewardSet rewardSet = new QuestRewardSet();
      parseRewardCommands(json, rewardSet);
      parseRewardSkills(json, rewardSet);
      return rewardSet;
   }

   private static void parseRewardCommands(JsonObject json, QuestRewardSet rewardSet) {
      if (json.has("commands")) {
         rewardSet.commands = new ArrayList<>();

         for (JsonElement element : json.getAsJsonArray("commands")) {
            rewardSet.commands.add(element.getAsString());
         }
      }
   }

   private static void parseRewardSkills(JsonObject json, QuestRewardSet rewardSet) {
      if (json.has("skills")) {
         rewardSet.skills = new ArrayList<>();

         for (JsonElement element : json.getAsJsonArray("skills")) {
            JsonObject skillObject = element.getAsJsonObject();
            QuestRewardSkill rewardSkill = new QuestRewardSkill();
            rewardSkill.id = getRequiredResourceLocation(skillObject, "id");
            if (skillObject.has("mastery")) {
               rewardSkill.mastery = Math.max(0.0, skillObject.get("mastery").getAsDouble());
            }

            if (skillObject.has("remove_time")) {
               rewardSkill.removeTime = Math.max(-1, skillObject.get("remove_time").getAsInt());
            } else if (skillObject.has("duration_ticks")) {
               rewardSkill.removeTime = Math.max(-1, skillObject.get("duration_ticks").getAsInt());
            }

            if (skillObject.has("no_magicule_cost")) {
               rewardSkill.noMagiculeCost = skillObject.get("no_magicule_cost").getAsBoolean();
            }

            rewardSet.skills.add(rewardSkill);
         }
      }
   }

   private static RacePrestigeText readText(JsonObject object) {
      if (object == null) {
         return null;
      }

      String title = object.has("title") ? object.get("title").getAsString() : "";
      String subtitle = object.has("subtitle") ? object.get("subtitle").getAsString() : "";
      String chat = object.has("chat") ? object.get("chat").getAsString() : "";
      return new RacePrestigeText(title, subtitle, chat);
   }

   private static String getRequiredString(JsonObject object, String key) {
      if (!object.has(key)) {
         throw new IllegalStateException("Missing '" + key + "'");
      } else {
         return object.get(key).getAsString();
      }
   }

   private static ResourceLocation getRequiredResourceLocation(JsonObject object, String key) {
      if (!object.has(key)) {
         throw new IllegalStateException("Missing '" + key + "'");
      } else {
         return ResourceLocation.parse(object.get(key).getAsString());
      }
   }

   private static String sanitize(String value) {
      return value.replace(':', '_').replace('/', '_').replace(' ', '_');
   }

   private static boolean isMinecraftAny(String raw) {
      return "minecraft:any".equals(raw) || "any".equals(raw);
   }

   private QuestDefinitionJsonParser() {
   }
}
