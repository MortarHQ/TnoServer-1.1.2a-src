package com.tensura_tno.ftb;

import com.tensura_tno.TensuraTNOMod;
import dev.architectury.registry.registries.Registrar;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.battlewill.Battlewill;
import io.github.manasmods.tensura.ability.magic.Magic;
import io.github.manasmods.tensura.ability.magic.Magic.MagicType;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public final class MasterySeedHelper {
   private static boolean loaded = false;
   private static final ThreadLocal<Set<UUID>> SEEDING_PLAYERS = ThreadLocal.withInitial(HashSet::new);
   private static Method GET_QUESTS;
   private static Method STORE_ACTIVE;
   private static Method STORE_COMPLETED;
   private static Method STORE_MARK_COMPLETED;
   private static Method STORE_HAS_COUNTED;
   private static Method STORE_ADD_COUNTED;
   private static Method STORE_ADD_PROGRESS;
   private static Method STORE_IS_DIRTY;
   private static Method FINISH_COMPLETED_QUEST;
   private static Method GRANT_REWARDS;
   private static Method MATCHES_QUEST;
   private static Object QUEST_REGISTRY_INSTANCE;
   private static Method REGISTRY_BY_CATEGORY;
   private static Object CAT_RACE_PRESTIGE;
   private static Field QD_ID;
   private static Field QD_CREATOR;
   private static Field QD_AMOUNT;
   private static Field QD_SUBORDINATE;
   private static Object QOT_MASTER;
   private static Object QOT_MASTER_TYPE;
   private static Object QOT_MASTER_TYPE_MAGIC;
   private static Object QOT_MASTER_SUBTYPE_MAGIC;
   private static Method SKILL_TYPE_ID;
   private static Method BATTLEWILL_ID;
   private static Method MAGIC_TYPE_ID;
   private static Method MAGIC_SUBTYPE_IDS;

   public static void seedMasteryQuests(ServerPlayer player) {
      if (loaded && player != null) {
         Set<UUID> seedingPlayers = SEEDING_PLAYERS.get();
         if (seedingPlayers.add(player.getUUID())) {
            Object store = null;

            try {
               store = GET_QUESTS.invoke(player);
               if (store != null) {
                  Set<ResourceLocation> active = (Set<ResourceLocation>)STORE_ACTIVE.invoke(store, CAT_RACE_PRESTIGE);
                  Set<ResourceLocation> completed = (Set<ResourceLocation>)STORE_COMPLETED.invoke(store, CAT_RACE_PRESTIGE);
                  List<Object> raceQuests = (List<Object>)REGISTRY_BY_CATEGORY.invoke(QUEST_REGISTRY_INSTANCE, CAT_RACE_PRESTIGE);
                  Registrar<ManasSkill> reg = SkillAPI.getSkillRegistry();
                  Skills skills = SkillAPI.getSkillsFrom(player);

                  for (Object quest : raceQuests) {
                     ResourceLocation qId = (ResourceLocation)QD_ID.get(quest);
                     if (active.contains(qId)
                        && !completed.contains(qId)
                        && !((QD_SUBORDINATE == null ? null : QD_SUBORDINATE.get(quest)) instanceof Enum<?> mode && "ONLY".equals(mode.name()))) {
                        Object creator = QD_CREATOR.get(quest);
                        if (creator.equals(QOT_MASTER)) {
                           seedMaster(player, store, quest, qId, reg, skills);
                        } else if (creator.equals(QOT_MASTER_TYPE)) {
                           seedMasterType(player, store, quest, qId, reg, skills);
                        } else if (creator.equals(QOT_MASTER_TYPE_MAGIC)) {
                           seedMasterTypeMagic(player, store, quest, qId, reg, skills);
                        } else if (creator.equals(QOT_MASTER_SUBTYPE_MAGIC)) {
                           seedMasterSubtypeMagic(player, store, quest, qId, reg, skills);
                        }
                     }
                  }

                  return;
               }
            } catch (Exception e) {
               TensuraTNOMod.LOGGER.warn("[TNO] MasterySeedHelper.seedMasteryQuests 出错: {}", e.getMessage());
               return;
            } finally {
               try {
                  if (store != null && (Boolean)STORE_IS_DIRTY.invoke(store)) {
                     STExtrasHelper.syncQuestState(player);
                  }
               } catch (ReflectiveOperationException var68) {
               } finally {
                  seedingPlayers.remove(player.getUUID());
                  if (seedingPlayers.isEmpty()) {
                     SEEDING_PLAYERS.remove();
                  }
               }
            }
         }
      }
   }

   private static void seedMaster(ServerPlayer player, Object store, Object quest, ResourceLocation qId, Registrar<ManasSkill> reg, Skills skills) throws Exception {
      for (ManasSkillInstance instance : skills.getLearnedSkills()) {
         if (instance.isMastered(player)) {
            ResourceLocation skillId = reg.getId(instance.getSkill());
            if (skillId != null && matchesMasteryQuest(player, quest, skillId)) {
               STORE_MARK_COMPLETED.invoke(store, qId);
               finishCompletedQuest(player, store, quest, qId);
               return;
            }
         }
      }
   }

   private static void seedMasterType(ServerPlayer player, Object store, Object quest, ResourceLocation qId, Registrar<ManasSkill> reg, Skills skills) throws Exception {
      int amount = QD_AMOUNT.getInt(quest);
      if (amount > 0) {
         int count = 0;

         for (ManasSkillInstance instance : skills.getLearnedSkills()) {
            if (instance.isMastered(player)) {
               ManasSkill ms = instance.getSkill();
               ResourceLocation msId = reg.getId(ms);
               if (msId != null && !(Boolean)STORE_HAS_COUNTED.invoke(store, qId, msId.toString())) {
                  ResourceLocation typeId = null;
                  if (ms instanceof Skill s) {
                     typeId = (ResourceLocation)SKILL_TYPE_ID.invoke(null, s.getType());
                  } else if (ms instanceof Battlewill) {
                     typeId = (ResourceLocation)BATTLEWILL_ID.invoke(null);
                  }

                  if (typeId != null && matchesMasteryQuest(player, quest, typeId)) {
                     STORE_ADD_COUNTED.invoke(store, qId, msId.toString());
                     count++;
                  }
               }
            }
         }

         if (count > 0) {
            addProgress(player, store, quest, qId, count, amount);
         }
      }
   }

   private static void seedMasterTypeMagic(ServerPlayer player, Object store, Object quest, ResourceLocation qId, Registrar<ManasSkill> reg, Skills skills) throws Exception {
      int amount = QD_AMOUNT.getInt(quest);
      if (amount > 0) {
         int count = 0;

         for (ManasSkillInstance instance : skills.getLearnedSkills()) {
            if (instance.isMastered(player)) {
               ManasSkill ms = instance.getSkill();
               if (ms instanceof Magic magic) {
                  ResourceLocation msId = reg.getId(ms);
                  if (msId != null && !(Boolean)STORE_HAS_COUNTED.invoke(store, qId, msId.toString())) {
                     ResourceLocation magicTypeId = (ResourceLocation)MAGIC_TYPE_ID.invoke(null, magic.getType());
                     if (matchesMasteryQuest(player, quest, magicTypeId)) {
                        STORE_ADD_COUNTED.invoke(store, qId, msId.toString());
                        count++;
                     }
                  }
               }
            }
         }

         if (count > 0) {
            addProgress(player, store, quest, qId, count, amount);
         }
      }
   }

   private static void seedMasterSubtypeMagic(ServerPlayer player, Object store, Object quest, ResourceLocation qId, Registrar<ManasSkill> reg, Skills skills) throws Exception {
      int amount = QD_AMOUNT.getInt(quest);
      if (amount > 0) {
         int count = 0;

         for (ManasSkillInstance instance : skills.getLearnedSkills()) {
            if (instance.isMastered(player)) {
               ManasSkill ms = instance.getSkill();
               if (ms instanceof Magic magic) {
                  ResourceLocation msId = reg.getId(ms);
                  if (msId != null && !(Boolean)STORE_HAS_COUNTED.invoke(store, qId, msId.toString())) {
                     Set<ResourceLocation> subtypeIds = (Set<ResourceLocation>)MAGIC_SUBTYPE_IDS.invoke(null, magic);
                     boolean match = false;

                     for (ResourceLocation subtypeId : subtypeIds) {
                        if (matchesMasteryQuest(player, quest, subtypeId)) {
                           match = true;
                           break;
                        }
                     }

                     if (match) {
                        STORE_ADD_COUNTED.invoke(store, qId, msId.toString());
                        count++;
                     }
                  }
               }
            }
         }

         if (count > 0) {
            addProgress(player, store, quest, qId, count, amount);
         }
      }
   }

   private static boolean matchesMasteryQuest(ServerPlayer player, Object quest, ResourceLocation typeId) throws Exception {
      return (Boolean)MATCHES_QUEST.invoke(null, quest, QD_CREATOR.get(quest), typeId, player, null);
   }

   private static void addProgress(ServerPlayer player, Object store, Object quest, ResourceLocation questId, int count, int amount) throws Exception {
      if ((Boolean)STORE_ADD_PROGRESS.invoke(store, questId, count, Math.max(1, amount))) {
         finishCompletedQuest(player, store, quest, questId);
      }
   }

   private static void finishCompletedQuest(ServerPlayer player, Object store, Object quest, ResourceLocation questId) throws Exception {
      if (FINISH_COMPLETED_QUEST != null) {
         FINISH_COMPLETED_QUEST.invoke(null, player, store, questId, quest, null);
      } else {
         GRANT_REWARDS.invoke(null, player, quest);
      }
   }

   private MasterySeedHelper() {
   }

   static {
      try {
         Class<?> iQuests = Class.forName("org.crypticdev.stextras.storage.quest.ISTExtrasQuests");
         GET_QUESTS = iQuests.getMethod("stextras$getQuests");
         Class<?> storeCls = Class.forName("org.crypticdev.stextras.storage.quest.STExtrasQuestStorage");
         Class<?> catCls = Class.forName("org.crypticdev.stextras.quest.definition.QuestCategory");
         STORE_ACTIVE = storeCls.getMethod("active", catCls);
         STORE_COMPLETED = storeCls.getMethod("completed", catCls);
         STORE_MARK_COMPLETED = storeCls.getMethod("markCompleted", ResourceLocation.class);
         STORE_HAS_COUNTED = storeCls.getMethod("hasCounted", ResourceLocation.class, String.class);
         STORE_ADD_COUNTED = storeCls.getMethod("addCounted", ResourceLocation.class, String.class);
         STORE_ADD_PROGRESS = storeCls.getMethod("addProgressAndMaybeComplete", ResourceLocation.class, int.class, int.class);
         STORE_IS_DIRTY = storeCls.getMethod("isDirty");
         Class<Enum> catEnum = (Class<Enum>)catCls;
         CAT_RACE_PRESTIGE = Enum.valueOf(catEnum, "RACE_PRESTIGE");
         Class<?> regCls = Class.forName("org.crypticdev.stextras.quest.registry.QuestRegistry");
         Field instanceField = regCls.getField("INSTANCE");
         QUEST_REGISTRY_INSTANCE = instanceField.get(null);
         REGISTRY_BY_CATEGORY = regCls.getMethod("byCategory", catCls);
         Class<?> qdCls = Class.forName("org.crypticdev.stextras.quest.definition.QuestDefinition");
         QD_ID = qdCls.getField("id");
         QD_CREATOR = qdCls.getField("creator");
         QD_AMOUNT = qdCls.getField("amount");

         try {
            QD_SUBORDINATE = qdCls.getField("subordinate");
         } catch (NoSuchFieldException var12) {
         }

         GRANT_REWARDS = Class.forName("org.crypticdev.stextras.quest.reward.QuestRewardService").getMethod("grant", ServerPlayer.class, qdCls);
         Class<?> listeners = Class.forName("org.crypticdev.stextras.handler.HandlePrestigeListeners");
         Class<?> qotCls = Class.forName("org.crypticdev.stextras.quest.definition.QuestObjectiveType");
         MATCHES_QUEST = listeners.getDeclaredMethod("matchesQuest", qdCls, qotCls, ResourceLocation.class, LivingEntity.class, BlockState.class);
         MATCHES_QUEST.setAccessible(true);

         try {
            Method finish = listeners.getDeclaredMethod("finishCompletedQuest", ServerPlayer.class, storeCls, ResourceLocation.class, qdCls, LivingEntity.class);
            finish.setAccessible(true);
            FINISH_COMPLETED_QUEST = finish;
         } catch (NoSuchMethodException var11) {
         }

         Class<Enum> qotEnum = (Class<Enum>)qotCls;
         QOT_MASTER = Enum.valueOf(qotEnum, "MASTER");
         QOT_MASTER_TYPE = Enum.valueOf(qotEnum, "MASTER_TYPE");
         QOT_MASTER_TYPE_MAGIC = Enum.valueOf(qotEnum, "MASTER_TYPE_MAGIC");
         QOT_MASTER_SUBTYPE_MAGIC = Enum.valueOf(qotEnum, "MASTER_SUBTYPE_MAGIC");
         Class<?> tmCls = Class.forName("org.crypticdev.stextras.quest.definition.TensuraMasteryTypeIds");
         SKILL_TYPE_ID = tmCls.getMethod("skillTypeId", SkillType.class);
         BATTLEWILL_ID = tmCls.getMethod("battlewillId");
         MAGIC_TYPE_ID = tmCls.getMethod("magicTypeId", MagicType.class);
         MAGIC_SUBTYPE_IDS = tmCls.getMethod("magicSubtypeIds", Magic.class);
         loaded = true;
      } catch (Exception | LinkageError e) {
         TensuraTNOMod.LOGGER.warn("[TNO] MasterySeedHelper 初始化失败（STExtras 未加载？）: {}", e.getMessage());
      }
   }
}
