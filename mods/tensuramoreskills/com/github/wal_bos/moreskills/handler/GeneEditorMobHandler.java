package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.item.GeneticSyringeItem;
import dev.architectury.event.events.common.TickEvent;
import dev.architectury.event.events.common.TickEvent.Player;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.commands.arguments.EntityAnchorArgument.Anchor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.phys.AABB;

public final class GeneEditorMobHandler {
   private static final String ROOT = "tensuramoreskills:gene_editor_mob";
   private static final String SKILLS = "InjectedSkills";
   private static final String EP = "InjectedEP";
   private static final String MP = "InjectedMP";
   private static final String HP = "InjectedHP";
   private static final String SHP = "InjectedSHP";
   private static final String NEXT_ACTION_INDEX = "NextActionIndex";
   private static final String LAST_CAST = "LastCast";
   private static final String ACTION_GATES = "tensuramoreskills:gene_action_gates";
   private static final String NIGHTMARE_LAST_CAST = "nightmareutils_last_entity_skill_cast";
   private static final String GENE_CONTROL_TAG = "tensuramoreskills_gene_controlled";
   private static final String PRESSED_TOGGLE_TAG = "tensuramoreskills_gene_pressed_toggle";
   private static final ResourceLocation GATE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "gene_editor_autocast");
   private static final ResourceLocation CREATOR_ID = ResourceLocation.fromNamespaceAndPath("tensura", "creator");
   private static final int HARD_SCAN_INTERVAL = 2;
   private static final int HARD_GLOBAL_COOLDOWN = 2;
   private static final int HARD_GATE_COOLDOWN = 3;
   private static final int MAX_FAILED_ACTION_CHECKS = 64;
   private static final int HOLD_TICKS = 70;
   private static final int EXTRA_CAST_CHANCE_PERCENT = 100;
   private static final String[] PASSIVE_SKILL_TOKENS = new String[]{
      "breathing",
      "aura",
      "passive",
      "ambient",
      "regeneration",
      "body_armor",
      "barrier",
      "presence",
      "coercion",
      "domain",
      "field",
      "enchant",
      "enchantment",
      "resistance",
      "nullification"
   };
   private static final String[] BAD_SKILL_TOKENS = new String[]{
      "farsight", "storage", "portal", "gate", "craft", "analyze", "analysis", "appraisal", "reincarnation", "body_double", "escape"
   };
   private static final String[] BAD_MODE_TOKENS = new String[]{
      "menu", "gui", "screen", "storage", "open", "selection", "select", "copy", "craft", "refine", "synthesis", "separation", "appraisal"
   };
   private static final Map<UUID, Map<ResourceLocation, Object>> INSTANCE_CACHE = new HashMap<>();
   private static final Map<UUID, List<GeneEditorMobHandler.CastAction>> ACTION_CACHE = new HashMap<>();
   private static final Map<UUID, Set<ResourceLocation>> DERIVED_SKILL_CACHE = new HashMap<>();

   private GeneEditorMobHandler() {
   }

   private static MoreSkillsConfigs.GeneEditorConfig cfg() {
      return MoreSkillsConfigs.GRAND.geneEditor;
   }

   public static void init() {
      TickEvent.PLAYER_POST.register((Player)player -> {
         if (player instanceof ServerPlayer serverPlayer) {
            if (serverPlayer.tickCount % 2 == 0) {
               AABB box = serverPlayer.getBoundingBox().inflate(Math.max((Double)cfg().mobScanRadius.get(), (Double)cfg().mobCastRange.get()));

               for (Mob mob : serverPlayer.level().getEntitiesOfClass(Mob.class, box, mobx -> mobx.isAlive() && hasGeneData(mobx))) {
                  tickMob(mob);
               }
            }
         }
      });
   }

   public static void addInjectedData(Mob mob, List<ResourceLocation> skills, double ep, double mp, double hp, double shp) {
      if (mob != null && !mob.level().isClientSide()) {
         CompoundTag root = mob.getPersistentData().getCompound("tensuramoreskills:gene_editor_mob");
         Set<ResourceLocation> known = new LinkedHashSet<>(readSkills(root));

         for (ResourceLocation id : skills) {
            if (id != null) {
               known.add(id);
               learnSkillByIdSilent(mob, id);
            }
         }

         ListTag list = new ListTag();

         for (ResourceLocation id : known.stream().sorted(Comparator.comparing(ResourceLocation::toString)).toList()) {
            list.add(StringTag.valueOf(id.toString()));
         }

         root.put("InjectedSkills", list);
         root.putDouble("InjectedEP", root.getDouble("InjectedEP") + Math.max(0.0, ep));
         root.putDouble("InjectedMP", root.getDouble("InjectedMP") + Math.max(0.0, mp));
         root.putDouble("InjectedHP", root.getDouble("InjectedHP") + Math.max(0.0, hp));
         root.putDouble("InjectedSHP", root.getDouble("InjectedSHP") + Math.max(0.0, shp));
         root.putInt("NextActionIndex", 0);
         root.putLong("LastCast", 0L);
         mob.getPersistentData().put("tensuramoreskills:gene_editor_mob", root);
         mob.getPersistentData().putBoolean("tensuramoreskills_gene_controlled", true);
         applyStoredAttributes(mob, root);

         for (ResourceLocation id : known) {
            learnSkillByIdSilent(mob, id);
         }
      }
   }

   public static void addInjectedSkills(Mob mob, List<ResourceLocation> skills) {
      addInjectedData(mob, skills, 0.0, 0.0, 0.0, 0.0);
   }

   public static boolean hasInjectedSkills(Mob mob) {
      return hasGeneData(mob);
   }

   public static boolean hasGeneData(Mob mob) {
      if (mob == null) {
         return false;
      }

      CompoundTag root = mob.getPersistentData().getCompound("tensuramoreskills:gene_editor_mob");
      return root.contains("InjectedSkills")
         || root.getDouble("InjectedEP") > 0.0
         || root.getDouble("InjectedMP") > 0.0
         || root.getDouble("InjectedHP") > 0.0
         || root.getDouble("InjectedSHP") > 0.0;
   }

   private static void applyStoredAttributes(Mob mob, CompoundTag root) {
      GeneticSyringeItem.applyBaseInjectedAttributes(
         mob, root.getDouble("InjectedEP"), root.getDouble("InjectedMP"), root.getDouble("InjectedHP"), root.getDouble("InjectedSHP")
      );
   }

   public static void tickMob(Mob mob) {
      if (mob != null && !mob.level().isClientSide()) {
         if (mob.isAlive()) {
            CompoundTag root = mob.getPersistentData().getCompound("tensuramoreskills:gene_editor_mob");
            applyStoredAttributes(mob, root);
            List<ResourceLocation> injected = readSkills(root);
            if (!injected.isEmpty()) {
               mob.getPersistentData().putBoolean("tensuramoreskills_gene_controlled", true);
               LivingEntity target = getTarget(mob);
               if (validTarget(mob, target)) {
                  forceInjectedRuntime(mob, injected);
                  ensureToggleSkills(mob, injected);
                  suppressOldMoveset(mob, target);
                  List<GeneEditorMobHandler.CastAction> actions = buildActionList(mob, target, injected);
                  if (!actions.isEmpty()) {
                     if (globalCooldownReady(mob)) {
                        if (gateReady(mob)) {
                           int start = Math.floorMod(root.getInt("NextActionIndex"), actions.size());
                           int checks = Math.min(64, actions.size());

                           for (int i = 0; i < checks; i++) {
                              int index = (start + i) % actions.size();
                              GeneEditorMobHandler.CastAction action = actions.get(index);
                              Object instance = getCachedInstance(mob, action.skillId());
                              if (instance != null && !isOnCooldown(instance, action.mode())) {
                                 orient(mob, target);
                                 suppressOldMoveset(mob, target);
                                 boolean casted = castDirect(mob, target, action.skillId(), instance, action.mode());
                                 if (casted) {
                                    root.putInt("NextActionIndex", (index + 1) % actions.size());
                                    root.putLong("LastCast", mob.tickCount);
                                    mob.getPersistentData().put("tensuramoreskills:gene_editor_mob", root);
                                    markGlobalCooldown(mob);
                                    blockOtherAutocasters(mob);
                                    setGateCooldown(mob, Math.min(Math.max(1, (Integer)cfg().mobCastCooldownTicks.get()), 3));
                                    chainAdditionalCasts(mob, target, action);
                                    return;
                                 }
                              }
                           }

                           root.putInt("NextActionIndex", (start + 1) % actions.size());
                           mob.getPersistentData().put("tensuramoreskills:gene_editor_mob", root);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private static void suppressOldMoveset(LivingEntity entity, LivingEntity target) {
      if (entity != null && target != null) {
         entity.getPersistentData().putBoolean("tensuramoreskills_gene_controlled", true);
         entity.getPersistentData().putLong("nightmareutils_last_entity_skill_cast", entity.tickCount + 200L);
         if (entity instanceof Mob mob) {
            if (mob.getTarget() == null || !mob.getTarget().isAlive() || mob.getTarget().isAlliedTo(mob)) {
               mob.setTarget(target);
            }

            mob.setAggressive(true);
         }
      }
   }

   private static void forceInjectedRuntime(Mob mob, List<ResourceLocation> injected) {
      for (ResourceLocation id : injected) {
         learnSkillByIdSilent(mob, id);
      }
   }

   private static LivingEntity getTarget(Mob mob) {
      LivingEntity target = mob.getTarget();
      if (validTarget(mob, target)) {
         return target;
      }

      double range = Math.max((Double)cfg().mobCastRange.get(), 32.0);
      AABB box = mob.getBoundingBox().inflate(range);
      LivingEntity best = null;
      double bestScore = Double.MAX_VALUE;

      for (LivingEntity candidate : mob.level()
         .getEntitiesOfClass(LivingEntity.class, box, entity -> entity.isAlive() && entity != mob && !mob.isAlliedTo(entity))) {
         if (!(candidate instanceof net.minecraft.world.entity.player.Player player && (player.isCreative() || player.isSpectator()))) {
            double dist = mob.distanceToSqr(candidate);
            if (!(dist > range * range)) {
               double score = dist;
               if (candidate instanceof Mob candidateMob && hasGeneData(candidateMob)) {
                  score += 128.0;
               }

               if (!mob.hasLineOfSight(candidate)) {
                  score += 512.0;
               }

               if (!(score >= bestScore)) {
                  best = candidate;
                  bestScore = score;
               }
            }
         }
      }

      if (best != null) {
         mob.setTarget(best);
      }

      return best;
   }

   private static boolean validTarget(LivingEntity mob, LivingEntity target) {
      if (mob == null || target == null) {
         return false;
      } else if (!mob.isAlive() || !target.isAlive()) {
         return false;
      } else if (mob.isAlliedTo(target)) {
         return false;
      } else if (!(target instanceof net.minecraft.world.entity.player.Player player && (player.isCreative() || player.isSpectator()))) {
         double range = Math.max((Double)cfg().mobCastRange.get(), 32.0);
         return mob.distanceToSqr(target) <= range * range;
      } else {
         return false;
      }
   }

   private static List<GeneEditorMobHandler.CastAction> buildActionList(Mob mob, LivingEntity target, List<ResourceLocation> injected) {
      Map<ResourceLocation, Object> instances = new HashMap<>();
      Set<ResourceLocation> derived = new LinkedHashSet<>();
      Set<ResourceLocation> allowed = new LinkedHashSet<>(injected);
      List<GeneEditorMobHandler.CastAction> actions = new ArrayList<>();

      try {
         Object skills = getSkillsFrom(mob);

         for (Object instance : (Collection)call(skills, "getLearnedSkills")) {
            Object skill = call(instance, "getSkill");
            ResourceLocation id = (ResourceLocation)call(skill, "getRegistryName");
            if (id != null && allowed.contains(id) && isLearnedForUse(instance) && !shouldSkipSkill(id, skill)) {
               List<Integer> modes = collectValidModes(mob, id, skill, instance);
               if (!modes.isEmpty()) {
                  instances.put(id, instance);

                  for (Integer mode : modes) {
                     if (mode != null) {
                        actions.add(new GeneEditorMobHandler.CastAction(id, mode));
                     }
                  }

                  addSubAbilityActions(mob, id, skill, instance, allowed, instances, actions, derived);
               }
            }
         }
      } catch (Exception var16) {
      }

      actions = dedupeActions(actions);
      actions.sort(
         Comparator.<GeneEditorMobHandler.CastAction, String>comparing(action -> action.skillId().toString())
            .thenComparingInt(GeneEditorMobHandler.CastAction::mode)
      );
      INSTANCE_CACHE.put(mob.getUUID(), instances);
      ACTION_CACHE.put(mob.getUUID(), actions);
      DERIVED_SKILL_CACHE.put(mob.getUUID(), derived);
      return actions;
   }

   private static void addSubAbilityActions(
      Mob mob,
      ResourceLocation holderId,
      Object holderSkill,
      Object holderInstance,
      Set<ResourceLocation> allowed,
      Map<ResourceLocation, Object> instances,
      List<GeneEditorMobHandler.CastAction> actions,
      Set<ResourceLocation> derived
   ) {
      try {
         List<?> slots = getSubSlots(holderInstance);
         if (slots.isEmpty()) {
            return;
         }

         int offset = 1;

         try {
            if (call(holderSkill, "getSubModeOffset", holderInstance.getClass(), holderInstance) instanceof Number number) {
               offset = number.intValue();
            }
         } catch (Exception var15) {
         }

         for (int i = 0; i < slots.size(); i++) {
            Object slot = slots.get(i);
            Object subSkill = call(slot, "getSkill");
            if (subSkill != null) {
               ResourceLocation subId = (ResourceLocation)call(subSkill, "getRegistryName");
               if (subId != null && (allowed.contains(subId) || CREATOR_ID.equals(holderId)) && !shouldSkipSkill(subId, subSkill)) {
                  int mode = offset + i;
                  if ((!isBadMode(holderSkill, holderInstance, mode) || isForcedCombatMode(subId, holderSkill, holderInstance, mode))
                     && canBeSlotted(mob, holderSkill, holderInstance, mode)) {
                     instances.put(subId, holderInstance);
                     actions.add(new GeneEditorMobHandler.CastAction(subId, mode));
                     derived.add(subId);
                  }
               }
            }
         }
      } catch (Exception var16) {
      }
   }

   private static List<?> getSubSlots(Object instance) {
      try {
         Object skill = call(instance, "getSkill");

         for (Class<?> iface : skill.getClass().getInterfaces()) {
            if (iface.getName().endsWith("ISubAbilityModeHolder")) {
               for (Method method : iface.getMethods()) {
                  if (method.getName().equals("getSubSlots") && method.getParameterCount() == 1 && method.invoke(null, instance) instanceof List<?> list) {
                     return list;
                  }
               }
            }
         }

         for (Method method : skill.getClass().getMethods()) {
            if (method.getName().equals("getSubSlots") && method.getParameterCount() == 1 && method.invoke(skill, instance) instanceof List<?> list) {
               return list;
            }
         }
      } catch (Exception var12) {
      }

      return List.of();
   }

   private static List<Integer> collectValidModes(LivingEntity mob, ResourceLocation id, Object skill, Object instance) {
      int modes = 1;

      try {
         if (call(instance, "getModes") instanceof Number number) {
            modes = Math.max(1, number.intValue());
         }
      } catch (Exception var7) {
      }

      List<Integer> out = new ArrayList<>();

      for (int mode = 0; mode < modes; mode++) {
         if ((!isBadMode(skill, instance, mode) || isForcedCombatMode(id, skill, instance, mode)) && canBeSlotted(mob, skill, instance, mode)) {
            out.add(mode);
         }
      }

      return out;
   }

   private static boolean isForcedCombatMode(ResourceLocation id, Object skill, Object instance, int mode) {
      String joined = (id + " " + safeModeId(skill, instance, mode) + " " + safeModeName(skill, instance, mode)).toLowerCase();
      return isForcedCombatText(joined);
   }

   private static boolean castDirect(LivingEntity mob, LivingEntity target, ResourceLocation id, Object instance, int mode) {
      try {
         Object skill = call(instance, "getSkill");
         boolean directPressed = invokeSkillOnPressedDirect(skill, instance, mob, mode);
         boolean held = fastForwardHold(skill, instance, mob, mode);
         return directPressed || held;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean invokeSkillOnPressedDirect(Object skill, Object instance, LivingEntity mob, int mode) {
      try {
         for (Method method : skill.getClass().getMethods()) {
            if (method.getName().equals("onPressed")) {
               Class<?>[] p = method.getParameterTypes();
               if (p.length == 4
                  && p[0].isAssignableFrom(instance.getClass())
                  && p[1].isAssignableFrom(mob.getClass())
                  && p[2] == int.class
                  && p[3] == int.class) {
                  method.invoke(skill, instance, mob, 0, mode);
                  return true;
               }

               if (p.length == 4
                  && p[0].isAssignableFrom(instance.getClass())
                  && p[1].isAssignableFrom(LivingEntity.class)
                  && p[2] == int.class
                  && p[3] == int.class) {
                  method.invoke(skill, instance, mob, 0, mode);
                  return true;
               }
            }
         }
      } catch (Exception var10) {
      }

      try {
         Method method = instance.getClass().getMethod("onPressed", LivingEntity.class, int.class, int.class);
         method.invoke(instance, mob, 0, mode);
         return true;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean fastForwardHold(Object skill, Object instance, LivingEntity mob, int mode) {
      boolean used = false;

      try {
         Method onHeld = null;

         for (Method method : skill.getClass().getMethods()) {
            if (method.getName().equals("onHeld")) {
               Class<?>[] p = method.getParameterTypes();
               if (p.length == 4
                  && p[0].isAssignableFrom(instance.getClass())
                  && p[1].isAssignableFrom(LivingEntity.class)
                  && p[2] == int.class
                  && p[3] == int.class) {
                  onHeld = method;
                  break;
               }
            }
         }

         if (onHeld == null) {
            return used;
         }

         for (int held = 1; held <= 70; held++) {
            Object result = onHeld.invoke(skill, instance, mob, held, mode);
            used = true;
            if (result instanceof Boolean keep && !keep) {
               break;
            }
         }

         for (Method method : skill.getClass().getMethods()) {
            if (method.getName().equals("onRelease")) {
               Class<?>[] p = method.getParameterTypes();
               if (p.length == 5
                  && p[0].isAssignableFrom(instance.getClass())
                  && p[1].isAssignableFrom(LivingEntity.class)
                  && p[2] == int.class
                  && p[3] == int.class
                  && p[4] == int.class) {
                  method.invoke(skill, instance, mob, 70, 0, mode);
                  used = true;
                  break;
               }
            }
         }
      } catch (Exception var11) {
      }

      return used;
   }

   private static void chainAdditionalCasts(LivingEntity mob, LivingEntity target, GeneEditorMobHandler.CastAction firstAction) {
      List<GeneEditorMobHandler.CastAction> actions = ACTION_CACHE.get(mob.getUUID());
      if (actions != null && actions.size() >= 2) {
         if (ThreadLocalRandom.current().nextInt(100) < 100) {
            CompoundTag root = mob.getPersistentData().getCompound("tensuramoreskills:gene_editor_mob");
            int start = Math.floorMod(root.getInt("NextActionIndex"), actions.size());

            for (int i = 0; i < actions.size(); i++) {
               GeneEditorMobHandler.CastAction action = actions.get((start + i) % actions.size());
               if (!action.equals(firstAction)) {
                  Object instance = getCachedInstance(mob, action.skillId());
                  if (instance != null && !isOnCooldown(instance, action.mode())) {
                     orient(mob, target);
                     suppressOldMoveset(mob, target);
                     if (castDirect(mob, target, action.skillId(), instance, action.mode())) {
                        root.putInt("NextActionIndex", (start + i + 1) % actions.size());
                        mob.getPersistentData().put("tensuramoreskills:gene_editor_mob", root);
                        return;
                     }
                  }
               }
            }
         }
      }
   }

   private static void ensureToggleSkills(LivingEntity mob, List<ResourceLocation> injected) {
      try {
         Object skills = getSkillsFrom(mob);
         Collection<?> learned = (Collection<?>)call(skills, "getLearnedSkills");
         Set<ResourceLocation> allowed = new LinkedHashSet<>(injected);

         for (Object instance : learned) {
            Object skill = call(instance, "getSkill");
            ResourceLocation id = (ResourceLocation)call(skill, "getRegistryName");
            if (id != null && allowed.contains(id) && isLearnedForUse(instance)) {
               if (canToggle(skill, instance, mob) && !isToggled(instance)) {
                  call(instance, "setToggled", boolean.class, true);
                  invokeToggleOn(skill, instance, mob);
               }

               ensurePressedToggle(skill, instance, mob, id);
            }
         }
      } catch (Exception var9) {
      }
   }

   private static void ensurePressedToggle(Object skill, Object instance, LivingEntity mob, ResourceLocation id) {
      try {
         String joined = id.toString().toLowerCase();
         if (!joined.contains("sense") && !joined.contains("aura") && !joined.contains("domain") && !joined.contains("barrier")) {
            return;
         }

         if (!canInteract(instance, mob)) {
            return;
         }

         CompoundTag tag = (CompoundTag)call(instance, "getOrCreateTag");
         if (tag.getBoolean("tensuramoreskills_gene_pressed_toggle")) {
            return;
         }

         invokeSkillOnPressedDirect(skill, instance, mob, 0);
         tag.putBoolean("tensuramoreskills_gene_pressed_toggle", true);
      } catch (Exception var6) {
      }
   }

   private static void invokeToggleOn(Object skill, Object instance, LivingEntity mob) {
      try {
         for (Method method : skill.getClass().getMethods()) {
            if (method.getName().equals("onToggleOn")) {
               Class<?>[] p = method.getParameterTypes();
               if (p.length == 2 && p[0].isAssignableFrom(instance.getClass()) && p[1].isAssignableFrom(LivingEntity.class)) {
                  method.invoke(skill, instance, mob);
                  return;
               }
            }
         }
      } catch (Exception var9) {
      }

      try {
         Method method = instance.getClass().getMethod("onToggleOn", LivingEntity.class);
         method.invoke(instance, mob);
      } catch (Exception var8) {
      }
   }

   private static boolean shouldSkipSkill(ResourceLocation id, Object skill) {
      if (id == null) {
         return true;
      }

      String joined = (id + " " + (skill == null ? "" : skill.getClass().getName())).toLowerCase();
      if (isForcedCombatText(joined)) {
         return false;
      }

      for (String token : BAD_SKILL_TOKENS) {
         if (joined.contains(token)) {
            return true;
         }
      }

      for (String token : PASSIVE_SKILL_TOKENS) {
         if (joined.contains(token)) {
            return true;
         }
      }

      return false;
   }

   private static boolean isForcedCombatText(String joined) {
      return joined.contains("summon")
         || joined.contains("summoning")
         || joined.contains("magic")
         || joined.contains("spell")
         || joined.contains("fire")
         || joined.contains("ice")
         || joined.contains("acid")
         || joined.contains("water")
         || joined.contains("wind")
         || joined.contains("lightning")
         || joined.contains("thunder")
         || joined.contains("attack")
         || joined.contains("damage")
         || joined.contains("shot")
         || joined.contains("ball")
         || joined.contains("shell")
         || joined.contains("blade")
         || joined.contains("ray")
         || joined.contains("beam");
   }

   private static boolean isBadMode(Object skill, Object instance, int mode) {
      String joined = (safeModeId(skill, instance, mode) + " " + safeModeName(skill, instance, mode)).toLowerCase();
      if (isForcedCombatText(joined)) {
         return false;
      }

      for (String token : BAD_MODE_TOKENS) {
         if (joined.contains(token)) {
            return true;
         }
      }

      return false;
   }

   private static String safeModeId(Object skill, Object instance, int mode) {
      try {
         Object value = call(skill, "getModeId", instance.getClass(), instance, int.class, mode);
         return String.valueOf(value);
      } catch (Exception ignored) {
         return "";
      }
   }

   private static String safeModeName(Object skill, Object instance, int mode) {
      try {
         Object value = call(skill, "getModeName", instance.getClass(), instance, int.class, mode);
         return String.valueOf(value);
      } catch (Exception ignored) {
         return "";
      }
   }

   private static Object getCachedInstance(LivingEntity mob, ResourceLocation id) {
      Map<ResourceLocation, Object> map = INSTANCE_CACHE.get(mob.getUUID());
      return map == null ? null : map.get(id);
   }

   private static void orient(LivingEntity caster, LivingEntity target) {
      if (caster instanceof Mob mob) {
         mob.getLookControl().setLookAt(target, 75.0F, 75.0F);
      }

      caster.lookAt(Anchor.EYES, target.getEyePosition());
   }

   private static boolean globalCooldownReady(LivingEntity entity) {
      long last = entity.getPersistentData().getLong("tensuramoreskills_gene_last_entity_skill_cast");
      return entity.tickCount - last >= 2L;
   }

   private static void markGlobalCooldown(LivingEntity entity) {
      entity.getPersistentData().putLong("tensuramoreskills_gene_last_entity_skill_cast", entity.tickCount);
   }

   private static void blockOtherAutocasters(LivingEntity entity) {
      entity.getPersistentData().putLong("nightmareutils_last_entity_skill_cast", entity.tickCount + 200L);
   }

   private static boolean gateReady(LivingEntity entity) {
      CompoundTag root = entity.getPersistentData().getCompound("tensuramoreskills:gene_action_gates");
      return entity.tickCount >= root.getInt(GATE_ID.toString());
   }

   private static void setGateCooldown(LivingEntity entity, int ticks) {
      CompoundTag data = entity.getPersistentData();
      CompoundTag root = data.getCompound("tensuramoreskills:gene_action_gates");
      root.putInt(GATE_ID.toString(), entity.tickCount + Math.max(0, ticks));
      data.put("tensuramoreskills:gene_action_gates", root);
   }

   private static boolean isLearnedForUse(Object instance) {
      try {
         return call(instance, "getMastery") instanceof Number number && number.doubleValue() >= 0.0;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean isOnCooldown(Object instance, int mode) {
      try {
         return call(instance, "onCoolDown", int.class, mode) instanceof Boolean bool && bool;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean canInteract(Object instance, LivingEntity mob) {
      try {
         return !(call(instance, "canInteractSkill", LivingEntity.class, mob) instanceof Boolean bool && !bool);
      } catch (Exception ignored) {
         return true;
      }
   }

   private static boolean canBeSlotted(LivingEntity mob, Object skill, Object instance, int mode) {
      try {
         return !(call(skill, "canBeSlotted", instance.getClass(), instance, LivingEntity.class, mob, int.class, mode) instanceof Boolean bool && !bool);
      } catch (Exception ignored) {
         return true;
      }
   }

   private static boolean canToggle(Object skill, Object instance, LivingEntity mob) {
      try {
         return call(skill, "canBeToggled", instance.getClass(), instance, LivingEntity.class, mob) instanceof Boolean bool && bool;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean isToggled(Object instance) {
      try {
         return call(instance, "isToggled") instanceof Boolean bool && bool;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static boolean learnSkillByIdSilent(LivingEntity target, ResourceLocation skillId) {
      try {
         if (target instanceof net.minecraft.world.entity.player.Player) {
            return false;
         }

         if (hasSkill(target, skillId)) {
            Object existing = getSkillInstance(target, skillId);
            ensureUsable(existing);
            return true;
         }

         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
         if (skill == null) {
            return false;
         }

         boolean helper = SkillHelper.learnSkill(target, skill);
         Object existing = getSkillInstance(target, skillId);
         if (existing != null) {
            ensureUsable(existing);
            return true;
         }

         Object instance = createSkillInstance(skill);
         if (instance == null) {
            return helper;
         }

         ensureUsable(instance);
         Object skills = getSkillsFrom(target);
         boolean learned = invokeUpdateSkill(skills, instance) || invokeLearnSkill(skills, instance);
         Object after = getSkillInstance(target, skillId);
         ensureUsable(after);
         return helper || learned || after != null;
      } catch (Exception ignored) {
         return false;
      }
   }

   private static Object getSkillInstance(LivingEntity entity, ResourceLocation skillId) {
      try {
         Object skills = getSkillsFrom(entity);

         for (Object instance : (Iterable)call(skills, "getLearnedSkills")) {
            Object skill = call(instance, "getSkill");
            Object id = call(skill, "getRegistryName");
            if (skillId.equals(id)) {
               return instance;
            }
         }
      } catch (Exception var8) {
      }

      return null;
   }

   private static boolean hasSkill(LivingEntity entity, ResourceLocation skillId) {
      return getSkillInstance(entity, skillId) != null;
   }

   private static void ensureUsable(Object instance) {
      if (instance != null) {
         try {
            if (call(instance, "getMastery") instanceof Number number && number.doubleValue() < 0.0) {
               try {
                  Method method = instance.getClass().getMethod("setMastery", double.class);
                  method.invoke(instance, 0.0);
               } catch (Exception ignored) {
                  try {
                     Method method = instance.getClass().getMethod("setMastery", int.class);
                     method.invoke(instance, 0);
                  } catch (Exception var8) {
                  }
               }
            }
         } catch (Exception var10) {
         }

         try {
            Method method = instance.getClass().getMethod("setRemoveTime", int.class);
            method.invoke(instance, -1);
         } catch (Exception var7) {
         }

         try {
            CompoundTag tag = (CompoundTag)call(instance, "getOrCreateTag");
            tag.putBoolean("tensuramoreskills_gene_projected", true);
            tag.putBoolean("nightmareutils_weapon_ego", true);
            tag.putBoolean("nightmareutils_item_projected", false);
         } catch (Exception var6) {
         }

         try {
            Method method = instance.getClass().getMethod("markDirty");
            method.invoke(instance);
         } catch (Exception var5) {
         }
      }
   }

   private static Object createSkillInstance(Object skill) throws Exception {
      try {
         Method method = skill.getClass().getMethod("createDefaultInstance");
         return method.invoke(skill);
      } catch (NoSuchMethodException var11) {
         List<String> candidates = new ArrayList<>();
         candidates.add("io.github.manasmods.tensura.ability.TensuraSkillInstance");
         candidates.add("io.github.manasmods.manascore.skill.api.ManasSkillInstance");

         for (String name : candidates) {
            try {
               Class<?> cls = Class.forName(name);

               for (Constructor<?> constructor : cls.getConstructors()) {
                  Class<?>[] p = constructor.getParameterTypes();
                  if (p.length == 1 && p[0].isAssignableFrom(skill.getClass())) {
                     return constructor.newInstance(skill);
                  }
               }
            } catch (Exception var10) {
            }
         }

         return null;
      }
   }

   private static boolean invokeLearnSkill(Object skills, Object instance) throws Exception {
      for (Method method : skills.getClass().getMethods()) {
         if (method.getName().equals("learnSkill")) {
            Class<?>[] p = method.getParameterTypes();
            if (p.length == 1 && p[0].isAssignableFrom(instance.getClass())) {
               return !(method.invoke(skills, instance) instanceof Boolean bool && !bool);
            }

            if (p.length == 2 && p[0].isAssignableFrom(instance.getClass())) {
               return !(method.invoke(skills, instance, null) instanceof Boolean bool && !bool);
            }
         }
      }

      return false;
   }

   private static boolean invokeUpdateSkill(Object skills, Object instance) throws Exception {
      for (Method method : skills.getClass().getMethods()) {
         if (method.getName().equals("updateSkill")) {
            Class<?>[] p = method.getParameterTypes();
            if (p.length == 2 && p[0].isAssignableFrom(instance.getClass()) && p[1] == boolean.class) {
               return !(method.invoke(skills, instance, true) instanceof Boolean bool && !bool);
            }
         }
      }

      return false;
   }

   private static Object getSkillsFrom(LivingEntity entity) throws Exception {
      Class<?> api = Class.forName("io.github.manasmods.manascore.skill.api.SkillAPI");
      Method method = api.getMethod("getSkillsFrom", LivingEntity.class);
      return method.invoke(null, entity);
   }

   private static Object call(Object target, String method) throws Exception {
      Method m = target.getClass().getMethod(method);
      return m.invoke(target);
   }

   private static Object call(Object target, String method, Object... args) throws Exception {
      Class<?>[] types = new Class[args.length / 2];
      Object[] values = new Object[args.length / 2];
      int i = 0;

      for (int j = 0; i < args.length; j++) {
         types[j] = (Class<?>)args[i];
         values[j] = args[i + 1];
         i += 2;
      }

      Method m = target.getClass().getMethod(method, types);
      return m.invoke(target, values);
   }

   private static List<ResourceLocation> readSkills(CompoundTag root) {
      List<ResourceLocation> out = new ArrayList<>();
      ListTag list = root.getList("InjectedSkills", 8);

      for (int i = 0; i < list.size(); i++) {
         try {
            out.add(ResourceLocation.parse(list.getString(i)));
         } catch (Exception var5) {
         }
      }

      return out;
   }

   private static List<GeneEditorMobHandler.CastAction> dedupeActions(List<GeneEditorMobHandler.CastAction> actions) {
      return new ArrayList<>(new LinkedHashSet<>(actions));
   }

   private record CastAction(ResourceLocation skillId, int mode) {
   }
}
