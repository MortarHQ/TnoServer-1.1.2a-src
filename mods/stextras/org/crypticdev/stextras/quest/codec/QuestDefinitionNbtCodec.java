package org.crypticdev.stextras.quest.codec;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.definition.QuestObjectiveType;
import org.crypticdev.stextras.quest.definition.QuestRarity;
import org.crypticdev.stextras.quest.definition.QuestRewardSet;
import org.crypticdev.stextras.quest.definition.QuestSubordinateMode;

public final class QuestDefinitionNbtCodec {
   public static CompoundTag toNbt(QuestDefinition QuestDefinition) {
      CompoundTag tag = new CompoundTag();
      tag.putString("id", QuestDefinition.id.toString());
      if (QuestDefinition.name != null) {
         tag.putString("name", QuestDefinition.name);
      }

      if (QuestDefinition.creator != null) {
         tag.putString("creator", QuestDefinition.creator.name());
      }

      if (QuestDefinition.category != null) {
         tag.putString("category", QuestDefinition.category.name());
      }

      if (QuestDefinition.rarity != null) {
         tag.putString("rarity", QuestDefinition.rarity.name());
      }

      tag.putInt("prestige", QuestDefinition.prestige);
      tag.putInt("amount", QuestDefinition.amount);
      if (QuestDefinition.subordinate != null && QuestDefinition.subordinate != QuestSubordinateMode.NONE) {
         tag.putString("subordinate", QuestDefinition.subordinate.name());
      }

      if (!QuestDefinition.types.isEmpty()) {
         ListTag typeList = new ListTag();

         for (ResourceLocation type : QuestDefinition.types) {
            typeList.add(StringTag.valueOf(type.toString()));
         }

         tag.put("types", typeList);
      }

      if (!QuestDefinition.damageTypes.isEmpty()) {
         tag.put("damageTypes", resourceListToNbt(QuestDefinition.damageTypes));
      }

      if (!QuestDefinition.damageTypeTags.isEmpty()) {
         tag.put("damageTypeTags", resourceListToNbt(QuestDefinition.damageTypeTags));
      }

      if (!QuestDefinition.enchantItems.isEmpty()) {
         tag.put("enchantItems", resourceListToNbt(QuestDefinition.enchantItems));
      }

      if (!QuestDefinition.enchantments.isEmpty()) {
         tag.put("enchantments", resourceListToNbt(QuestDefinition.enchantments));
      }

      if (QuestDefinition.minEnchantLevel >= 0) {
         tag.putInt("minEnchantLevel", QuestDefinition.minEnchantLevel);
      }

      if (QuestDefinition.maxEnchantLevel >= 0) {
         tag.putInt("maxEnchantLevel", QuestDefinition.maxEnchantLevel);
      }

      if (QuestDefinition.spiritLevel != null) {
         tag.putString("spiritLevel", QuestDefinition.spiritLevel.toString());
      }

      if (QuestDefinition.mobCategory != null) {
         tag.putString("mobCategory", QuestDefinition.mobCategory.name());
      }

      if (QuestDefinition.race != null) {
         tag.putString("race", QuestDefinition.race.toString());
      }

      if (QuestDefinition.minStage >= 0) {
         tag.putInt("minStage", QuestDefinition.minStage);
      }

      if (QuestDefinition.maxStage >= 0) {
         tag.putInt("maxStage", QuestDefinition.maxStage);
      }

      if (QuestDefinition.cooldownSeconds >= 0) {
         tag.putInt("cooldown", QuestDefinition.cooldownSeconds);
      }

      if (QuestDefinition.statFirst) {
         tag.putBoolean("statFirst", true);
      }

      if (QuestDefinition.tags != null && !QuestDefinition.tags.isEmpty()) {
         ListTag tagList = new ListTag();

         for (String questTag : QuestDefinition.tags) {
            tagList.add(StringTag.valueOf(questTag));
         }

         tag.put("tags", tagList);
      }

      if (QuestDefinition.rewards != null) {
         tag.put("rewards", QuestDefinition.rewards.toNbt());
      }

      if (QuestDefinition.subordinateRewards != null && !QuestDefinition.subordinateRewards.isEmpty()) {
         tag.put("subordinateRewards", QuestDefinition.subordinateRewards.toNbt());
      }

      return tag;
   }

   public static QuestDefinition fromNbt(CompoundTag tag) {
      QuestDefinition QuestDefinition = new QuestDefinition();
      QuestDefinition.id = ResourceLocation.parse(tag.getString("id"));
      if (tag.contains("name")) {
         QuestDefinition.name = tag.getString("name");
      }

      try {
         QuestDefinition.creator = QuestObjectiveType.valueOf(tag.getString("creator"));
      } catch (Exception ignored) {
         QuestDefinition.creator = QuestObjectiveType.MASTER;
      }

      try {
         QuestDefinition.category = QuestCategory.valueOf(tag.getString("category"));
      } catch (Exception ignored) {
         QuestDefinition.category = QuestCategory.DAILY;
      }

      if (tag.contains("rarity")) {
         QuestDefinition.rarity = QuestRarity.valueOf(tag.getString("rarity"));
      }

      QuestDefinition.prestige = tag.getInt("prestige");
      QuestDefinition.amount = tag.getInt("amount");
      QuestDefinition.subordinate = tag.contains("subordinate") ? QuestSubordinateMode.fromString(tag.getString("subordinate")) : QuestSubordinateMode.NONE;
      if (tag.contains("types")) {
         ListTag typeList = tag.getList("types", 8);

         for (int i = 0; i < typeList.size(); i++) {
            QuestDefinition.types.add(ResourceLocation.parse(typeList.getString(i)));
         }
      } else if (tag.contains("type")) {
         QuestDefinition.types.add(ResourceLocation.parse(tag.getString("type")));
      }

      if (tag.contains("damageTypes")) {
         readResourceList(tag.getList("damageTypes", 8), QuestDefinition.damageTypes);
      }

      if (tag.contains("damageTypeTags")) {
         readResourceList(tag.getList("damageTypeTags", 8), QuestDefinition.damageTypeTags);
      }

      if (tag.contains("enchantItems")) {
         readResourceList(tag.getList("enchantItems", 8), QuestDefinition.enchantItems);
      }

      if (tag.contains("enchantments")) {
         readResourceList(tag.getList("enchantments", 8), QuestDefinition.enchantments);
      }

      if (tag.contains("minEnchantLevel")) {
         QuestDefinition.minEnchantLevel = tag.getInt("minEnchantLevel");
      }

      if (tag.contains("maxEnchantLevel")) {
         QuestDefinition.maxEnchantLevel = tag.getInt("maxEnchantLevel");
      }

      if (tag.contains("spiritLevel")) {
         QuestDefinition.spiritLevel = ResourceLocation.parse(tag.getString("spiritLevel"));
      }

      if (tag.contains("mobCategory")) {
         QuestDefinition.mobCategory = MobCategory.valueOf(tag.getString("mobCategory"));
      }

      if (tag.contains("race")) {
         QuestDefinition.race = ResourceLocation.parse(tag.getString("race"));
      }

      if (tag.contains("minStage")) {
         QuestDefinition.minStage = tag.getInt("minStage");
      }

      if (tag.contains("maxStage")) {
         QuestDefinition.maxStage = tag.getInt("maxStage");
      }

      if (tag.contains("cooldown")) {
         QuestDefinition.cooldownSeconds = Math.max(-1, tag.getInt("cooldown"));
      }

      if (tag.contains("statFirst")) {
         QuestDefinition.statFirst = tag.getBoolean("statFirst");
      }

      if (tag.contains("tags")) {
         ListTag tagList = tag.getList("tags", 8);
         QuestDefinition.tags = new ArrayList<>();

         for (int i = 0; i < tagList.size(); i++) {
            QuestDefinition.tags.add(tagList.getString(i));
         }
      }

      if (tag.contains("rewards")) {
         QuestDefinition.rewards = QuestRewardSet.fromNbt(tag.getCompound("rewards"));
      } else {
         QuestDefinition.rewards = new QuestRewardSet();
      }

      if (tag.contains("subordinateRewards")) {
         QuestDefinition.subordinateRewards = QuestRewardSet.fromNbt(tag.getCompound("subordinateRewards"));
      } else if (tag.contains("subordinate_rewards")) {
         QuestDefinition.subordinateRewards = QuestRewardSet.fromNbt(tag.getCompound("subordinate_rewards"));
      } else {
         QuestDefinition.subordinateRewards = new QuestRewardSet();
      }

      return QuestDefinition;
   }

   private static ListTag resourceListToNbt(Iterable<ResourceLocation> values) {
      ListTag list = new ListTag();

      for (ResourceLocation value : values) {
         list.add(StringTag.valueOf(value.toString()));
      }

      return list;
   }

   private static void readResourceList(ListTag tag, List<ResourceLocation> out) {
      for (int i = 0; i < tag.size(); i++) {
         out.add(ResourceLocation.parse(tag.getString(i)));
      }
   }

   private QuestDefinitionNbtCodec() {
   }
}
