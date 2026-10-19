package io.github.Memoires.mysticism.handler.skill;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.event.events.common.EntityEvent.LivingDeath;
import dev.architectury.event.events.common.PlayerEvent.PlayerJoin;
import io.github.Memoires.mysticism.ability.skill.unique.SubjugatorSkill;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.Memoires.mysticism.util.TimerUtils;
import io.github.Memoires.mysticism.util.subjugator.SubjugatorCopyQueue;
import io.github.Memoires.mysticism.util.subjugator.SubjugatorDialogue;
import io.github.Memoires.mysticism.world.ledgers.SubjugatorLedger;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.SkillEvents;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillActivationEvent;
import io.github.manasmods.manascore.skill.api.SkillEvents.SkillMasteryEvent;
import io.github.manasmods.tensura.ability.skill.unique.ChosenOneSkill;
import io.github.manasmods.tensura.ability.skill.unique.VillainSkill;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.SubordinateHelper;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class SubjugatorHandler {
   public static void init() {
      SkillEvents.SKILL_MASTERY
         .register(
            (SkillMasteryEvent)(manasSkillInstance, livingEntity, changeable) -> {
               if (manasSkillInstance.getSkill() instanceof SubjugatorSkill) {
                  double currentMastery = (Double)changeable.get();
                  if (currentMastery >= manasSkillInstance.getMaxMastery() / 2.0 && !manasSkillInstance.getOrCreateTag().getBoolean("Awakened")) {
                     if (livingEntity instanceof Player player && livingEntity.level() instanceof ServerLevel sLevel) {
                        SubjugatorDialogue.ManasSkillLike SK = new SubjugatorDialogue.ManasSkillLike() {
                           @Override
                           public CompoundTag getOrCreateTag(Object inst) {
                              return ((ManasSkillInstance)inst).getOrCreateTag();
                           }

                           @Override
                           public void markDirty(Object inst) {
                              ((ManasSkillInstance)inst).markDirty();
                           }
                        };
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.subjugator.awakening_1")
                              .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.BOLD}),
                           false
                        );
                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.subjugator.awakening_2")
                              .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.BOLD}),
                           false
                        );
                        boolean started = SubjugatorDialogue.prepareReply(
                           SK, manasSkillInstance, player, "awakening", "mysticism.skill.subjugator.awakening_response_", 3, 1
                        );
                        if (started) {
                           MinecraftServer server = sLevel.getServer();
                           Component msg = Component.translatable("mysticism.skill.subjugator.awakening_other")
                              .withStyle(new ChatFormatting[]{ChatFormatting.GRAY, ChatFormatting.BOLD});

                           for (ServerPlayer sp : server.getPlayerList().getPlayers()) {
                              if (!sp.equals(livingEntity)) {
                                 sp.displayClientMessage(msg, false);
                                 sp.playNotifySound(SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 1.0F, 0.8F);
                              }
                           }

                           manasSkillInstance.getOrCreateTag().putBoolean("Awakened", true);
                        }
                     }
                  } else if (currentMastery >= 600.0 && manasSkillInstance.getOrCreateTag().getBoolean("Awakened")) {
                  }
               }

               return EventResult.pass();
            }
         );
      SkillEvents.ACTIVATE_SKILL
         .register(
            (SkillActivationEvent)(changeable, livingEntity, i, mode) -> {
               if (((ManasSkillInstance)changeable.get()).getSkill() instanceof SubjugatorSkill && mode == 3) {
                  return EventResult.pass();
               }

               Optional<ManasSkillInstance> skillOpt = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get());
               ManasSkillInstance instance = null;
               if (skillOpt.isPresent()) {
                  instance = skillOpt.get();
               }

               if (instance == null) {
                  return EventResult.pass();
               }

               if (!instance.getOrCreateTag().getBoolean("AbsoluteDefense")) {
                  return EventResult.pass();
               }

               Set<ManasSkill> fullyUsableSkills = Set.of(
                  (ManasSkill)ExtraSkills.DEMON_LORD_HAKI.get(),
                  (ManasSkill)ExtraSkills.SACRED_HAKI.get(),
                  (ManasSkill)ExtraSkills.HAKI.get(),
                  (ManasSkill)ExtraSkills.MORTAL_FEAR.get()
               );
               if (fullyUsableSkills.contains(((ManasSkillInstance)changeable.get()).getSkill())) {
                  return EventResult.pass();
               }

               if (((ManasSkillInstance)changeable.get()).getSkill() instanceof ChosenOneSkill && mode == 0) {
                  return EventResult.pass();
               }

               if (((ManasSkillInstance)changeable.get()).getSkill() instanceof VillainSkill && mode == 0) {
                  return EventResult.pass();
               }

               if (instance.getOrCreateTag().getBoolean("Awakened")) {
                  CompoundTag tag = instance.getOrCreateTag();
                  String name = instance.getOrCreateTag().getBoolean("Named") ? tag.getString("ManasName") : "???";
                  if (name == null || name.isEmpty()) {
                     name = "???";
                  }

                  if (livingEntity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.subjugator.the_voice.unbreakable_defense_toggled", new Object[]{name})
                           .withStyle(ChatFormatting.GRAY),
                        false
                     );
                  }

                  livingEntity.level()
                     .playSound(
                        null, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), SoundEvents.VILLAGER_AMBIENT, SoundSource.PLAYERS, 1.0F, 1.0F
                     );
                  return EventResult.interruptFalse();
               } else {
                  if (livingEntity instanceof Player player) {
                     player.displayClientMessage(
                        Component.translatable("mysticism.skill.subjugator.unbreakable_defense_toggled").withStyle(ChatFormatting.RED), false
                     );
                  }

                  livingEntity.level()
                     .playSound(
                        null,
                        livingEntity.getX(),
                        livingEntity.getY(),
                        livingEntity.getZ(),
                        (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get(),
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                     );
                  return EventResult.interruptFalse();
               }
            }
         );
      EntityEvent.LIVING_DEATH.register((LivingDeath)(living, source) -> {
         if (living.level().isClientSide) {
            return EventResult.pass();
         }

         UUID ownerId = SubordinateHelper.getSubordinateOwnerUUID(living);
         if (ownerId == null) {
            return EventResult.pass();
         }

         ServerLevel sl = (ServerLevel)living.level();
         SubjugatorLedger.get(sl).markDead(ownerId, living.getUUID());
         return EventResult.pass();
      });
      EntityEvent.LIVING_DEATH.register((LivingDeath)(livingEntity, damageSource) -> {
         if (!(livingEntity instanceof Player)) {
            return EventResult.pass();
         }

         Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get());
         if (opt.isEmpty()) {
            return EventResult.pass();
         }

         ManasSkillInstance inst = opt.get();

         for (int id : SubjugatorCopyQueue.clearAll(inst)) {
            String key = SubjugatorCopyQueue.timerKey(livingEntity, id);
            TimerUtils.cancel(key);
         }

         return EventResult.pass();
      });
      EntityEvent.LIVING_DEATH
         .register(
            (LivingDeath)(deadEntity, damageSource) -> {
               if (!(deadEntity instanceof LivingEntity)) {
                  return EventResult.pass();
               }

               UUID deadId = deadEntity.getUUID();
               MinecraftServer srv = deadEntity.level().getServer();
               if (srv == null) {
                  return EventResult.pass();
               }

               for (ServerPlayer player : srv.getPlayerList().getPlayers()) {
                  Optional<ManasSkillInstance> skillOpt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get());
                  if (!skillOpt.isEmpty()) {
                     ManasSkillInstance inst = skillOpt.get();
                     if (SubjugatorCopyQueue.containsTarget(inst, deadId)) {
                        for (int id : SubjugatorCopyQueue.removeAllForTarget(inst, deadId)) {
                           String key = SubjugatorCopyQueue.timerKey(player, id);
                           TimerUtils.cancel(key);
                        }

                        player.displayClientMessage(
                           Component.translatable("mysticism.skill.mode.subjugator.takeover.canceled_source_death", new Object[]{deadEntity.getDisplayName()})
                              .withStyle(ChatFormatting.RED),
                           false
                        );
                        player.level()
                           .playSound(null, player.getX(), player.getY(), player.getZ(), TensuraSoundEvents.GENERIC_CAST_FAIL, SoundSource.PLAYERS, 1.0F, 1.0F);
                     }
                  }
               }

               return EventResult.pass();
            }
         );
      PlayerEvent.PLAYER_JOIN.register((PlayerJoin)player -> {
         if (player.level() instanceof ServerLevel sl) {
            Set<UUID> deadSubs = SubjugatorLedger.get(sl).consumeFor(player.getUUID());
            if (!deadSubs.isEmpty()) {
               for (UUID subId : deadSubs) {
                  processSubDeathForOwner(player, subId);
               }
            }
         }
      });
   }

   private static void processSubDeathForOwner(ServerPlayer owner, UUID subordinateId) {
      Skills skills = SkillAPI.getSkillsFrom(owner);
      Optional<ManasSkillInstance> opt = skills.getSkill((ManasSkill)UniqueMysticismSkills.SUBJUGATOR.get());
      if (!opt.isEmpty()) {
         ManasSkillInstance instance = opt.get();
         String MAP_KEY = "LoyaltyMap";
         String TOTAL_KEY = "TotalLoyalty";
         String BONUS_KEY = "UnbreakableDefenseBonus";
         CompoundTag tag = instance.getOrCreateTag();
         CompoundTag map = tag.getCompound("LoyaltyMap");
         String subKey = subordinateId.toString();
         if (map.contains(subKey)) {
            map.remove(subKey);
            int total = 0;

            for (String k : map.getAllKeys()) {
               total += map.getInt(k);
            }

            total = Math.max(0, Math.min(total, SubjugatorSkill.CONFIG.maximumTotalLoyaltyPoints));
            int steps = total / SubjugatorSkill.CONFIG.unbreakableDefenseStep;
            float bonus = (float)Math.min(steps * SubjugatorSkill.CONFIG.unbreakableDefenseGain, 1.0);
            if (map.isEmpty()) {
               tag.remove("LoyaltyMap");
            } else {
               tag.put("LoyaltyMap", map);
            }

            tag.putInt("TotalLoyalty", total);
            tag.putFloat("UnbreakableDefenseBonus", bonus);
            instance.markDirty();
         }
      }
   }
}
