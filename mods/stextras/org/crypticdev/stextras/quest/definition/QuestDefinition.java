package org.crypticdev.stextras.quest.definition;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.MobCategory;
import org.crypticdev.stextras.quest.codec.QuestDefinitionNbtCodec;

public class QuestDefinition {
   public ResourceLocation id;
   public String name;
   public QuestObjectiveType creator;
   public List<ResourceLocation> types = new ArrayList<>();
   public List<ResourceLocation> damageTypes = new ArrayList<>();
   public List<ResourceLocation> damageTypeTags = new ArrayList<>();
   public List<ResourceLocation> enchantItems = new ArrayList<>();
   public List<ResourceLocation> enchantments = new ArrayList<>();
   public int minEnchantLevel = -1;
   public int maxEnchantLevel = -1;
   public ResourceLocation spiritLevel;
   public int amount;
   public QuestRewardSet rewards;
   public QuestSubordinateMode subordinate = QuestSubordinateMode.NONE;
   public QuestRewardSet subordinateRewards = new QuestRewardSet();
   public QuestCategory category;
   public QuestRarity rarity;
   public int prestige;
   public int cooldownSeconds = -1;
   public ResourceLocation race;
   public MobCategory mobCategory;
   public List<String> tags = List.of();
   public int minStage = -1;
   public int maxStage = -1;
   public boolean statFirst = false;

   public boolean noAmountRequired() {
      return this.creator == QuestObjectiveType.MASTER;
   }

   public boolean hasStageConstraint() {
      return this.minStage >= 0 || this.maxStage >= 0;
   }

   public CompoundTag toNbt() {
      return QuestDefinitionNbtCodec.toNbt(this);
   }

   public static QuestDefinition fromNbt(CompoundTag tag) {
      return QuestDefinitionNbtCodec.fromNbt(tag);
   }
}
