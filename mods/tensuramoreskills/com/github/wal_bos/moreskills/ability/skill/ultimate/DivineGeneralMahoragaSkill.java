package com.github.wal_bos.moreskills.ability.skill.ultimate;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.menu.MahoragaAdaptationMenu;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.util.SkillObtainmentHelper;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.api.Skills;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.enchantment.TensuraEnchantmentHelper;
import io.github.manasmods.tensura.enchantment.TensuraEnchantments;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.skill.ResistanceSkills;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent.Pre;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DivineGeneralMahoragaSkill extends Skill {
   public static final ResourceLocation SKILL_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "divine_general_mahoraga");
   private static final MoreSkillsConfigs.DivineGeneralMahoraga CONFIG = MoreSkillsConfigs.GRAND.divineGeneralMahoraga;
   private static final int MODE_SUMMON_SWORD = 0;
   private static final int MODE_ADAPTED = 1;
   private static final String PD_ROOT = "tms_mahoraga";
   private static final String PD_TAKEN = "takenHits";
   private static final String PD_HIT_COUNTERS = "hitCounters";
   private static final String PD_ADAPTED_EFFECTS = "adaptedEffects";
   private static final String PD_ENABLED_EFFECTS = "enabledEffects";
   private static final String PD_ADAPTED_DAMAGE = "adaptedDamage";
   private static final String PD_ENABLED_DAMAGE = "enabledDamage";
   private static final String PD_LEARNED_STAGE = "learnedStage";
   private static final String PD_SELECTED_INDEX = "selectedIndex";
   private static final String PD_GLOBAL_TICK = "globalTick";
   public static final String TAG_BLADE = "tms_extermination_blade";
   private static final ResourceLocation MAHORAGA_DEGRADE_ID = ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "mahoraga_resistance_degradation");
   private static volatile Method CACHED_ADD_MASTERY_ENTITY_INT;
   private static volatile Method CACHED_ADD_MASTERY_INT;
   private static final Map<String, MobEffect> EFFECT_CACHE = new ConcurrentHashMap<>();

   public DivineGeneralMahoragaSkill() {
      super(SkillType.ULTIMATE);
   }

   public double getDefaultAcquiringMagiculeCost() {
      return (Double)MoreSkillsConfigs.GRAND.divineGeneralMahoraga.acquirementMagiculeCost.get();
   }

   public int getAcquirementMastery(LivingEntity entity) {
      return 0;
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return SkillObtainmentHelper.canAcquire(
         entity,
         (Double)MoreSkillsConfigs.GRAND.divineGeneralMahoraga.acquirementMagiculeCost.get(),
         (String)MoreSkillsConfigs.GRAND.divineGeneralMahoraga.requiredSkillId.get(),
         (Boolean)MoreSkillsConfigs.GRAND.divineGeneralMahoraga.requiredSkillMustBeMastered.get()
      );
   }

   @NotNull
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("tensuramoreskills", "textures/skill/ultimate/mahoraga.png");
   }

   public boolean canBeToggled(ManasSkillInstance instance, @NotNull LivingEntity living) {
      return true;
   }

   public int getModes(ManasSkillInstance instance) {
      return 2;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      return mode == 0 ? 1 : 0;
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (mode) {
         case 0 -> "divine_general_mahoraga.summon_sword";
         case 1 -> "divine_general_mahoraga.adapted";
         default -> super.getModeId(instance, mode);
      };
   }

   public Component getModeName(ManasSkillInstance instance, int mode) {
      return (Component)(switch (mode) {
         case 0 -> Component.translatable("tensuramoreskills.skill.mode.divine_general_mahoraga.summon_sword");
         case 1 -> Component.translatable("tensuramoreskills.skill.mode.divine_general_mahoraga.adapted");
         default -> super.getModeName(instance, mode);
      });
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      applyInfiniteRegen(entity);
      syncMasteredDegradationModifier(instance, entity);
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      entity.removeEffect(TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION));
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      syncMasteredDegradationModifier(instance, entity);
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
      if (degrade != null) {
         degrade.addOrReplacePermanentModifier(
            new AttributeModifier(MAHORAGA_DEGRADE_ID, (Double)CONFIG.masteredResistanceDegradationBonus.get(), Operation.ADD_VALUE)
         );
      }
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance degrade = entity.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
      if (degrade != null) {
         degrade.removeModifier(MAHORAGA_DEGRADE_ID);
      }
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer owner, boolean conqueredEnd) {
      syncMasteredDegradationModifier(instance, owner);
   }

   public boolean onEffectAdded(ManasSkillInstance instance, LivingEntity entity, @Nullable Entity source, Changeable<MobEffectInstance> effect) {
      if (entity instanceof ServerPlayer player) {
         if (!instance.isToggled()) {
            return true;
         }

         MobEffectInstance incoming = (MobEffectInstance)effect.get();
         if (incoming == null) {
            return true;
         }

         Holder<MobEffect> holder = incoming.getEffect();
         ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)holder.value());
         if (id == null) {
            return true;
         }

         String key = id.toString();
         CompoundTag root = root(player);
         ListTag adapted = root.getList("adaptedEffects", 8);
         if (!containsString(adapted, key)) {
            adapted.add(StringTag.valueOf(key));
            root.put("adaptedEffects", sanitizeStringList(adapted));
            player.displayClientMessage(Component.translatable("tensuramoreskills.skill.divine_general_mahoraga.adapted_effect", new Object[]{key}), true);
         }

         ListTag enabled = root.getList("enabledEffects", 8);
         return !containsString(enabled, key);
      } else {
         return true;
      }
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         if (instance.isToggled()) {
            applyInfiniteRegen(player);
            syncMasteredDegradationModifier(instance, player);
            CompoundTag r = root(player);
            int t = r.getInt("globalTick") + 1;
            if (t >= (Integer)CONFIG.learningBonusIntervalTicks.get()) {
               t = 0;
               applyLearningBonus(player, instance, (Integer)CONFIG.learningBonusAmount.get());
            }

            r.putInt("globalTick", t);
            adaptCurrentlyActiveEffects(player);
            cleanseEnabledEffects(player);
         }
      }
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity instanceof ServerPlayer player) {
         if (mode == 0) {
            if (!instance.onCoolDown(mode)) {
               if (hasBladeAnywhere(player)) {
                  player.displayClientMessage(
                     Component.translatable("tensuramoreskills.skill.divine_general_mahoraga.sword_summon").withStyle(ChatFormatting.GRAY), true
                  );
                  instance.setCoolDown((Integer)CONFIG.bladeAlreadyOwnedCooldownTicks.get(), mode);
               } else {
                  ItemStack blade = DivineGeneralMahoragaSkill.BladeUtil.createBladeStack(player, instance.isMastered(player));
                  if (player.getMainHandItem().isEmpty()) {
                     player.setItemInHand(InteractionHand.MAIN_HAND, blade);
                  } else if (player.getOffhandItem().isEmpty()) {
                     player.setItemInHand(InteractionHand.OFF_HAND, blade);
                  } else {
                     player.addItem(blade);
                  }

                  instance.setCoolDown((Integer)CONFIG.bladeCooldownTicks.get(), mode);
                  player.displayClientMessage(
                     Component.translatable("tensuramoreskills.skill.divine_general_mahoraga.sword_summon").withStyle(ChatFormatting.LIGHT_PURPLE), true
                  );
               }
            }
         } else {
            openAdaptationMenu(player, instance.isToggled());
         }
      }
   }

   public static void handleIncomingDamage(ServerPlayer player, DamageSource source, float amount) {
      Optional<ManasSkillInstance> optional = getMahoragaInstance(player);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         if (instance.isToggled()) {
            if (!(amount >= player.getHealth())) {
               String rawKey = rawDamageKey(source);
               String bucketKey = bucketFromRaw(rawKey);
               rememberDamageKey(player, rawKey);
               adaptCurrentlyActiveEffects(player);
               CompoundTag root = root(player);
               CompoundTag taken = root.getCompound("takenHits");
               int hitsTaken = taken.getInt(rawKey) + 1;
               taken.putInt(rawKey, hitsTaken);
               root.put("takenHits", taken);
               int stage = stageFromTaken(hitsTaken);
               int curStage = getLearnedStage(player, rawKey);
               if (stage > curStage) {
                  setLearnedStage(player, rawKey, stage);
               }

               if (hitsTaken >= (Integer)CONFIG.gainNullAfterHits.get()) {
                  DivineGeneralMahoragaSkill.ResistPair pair = resolveResistPair(bucketKey);
                  if (pair != null && pair.nullificationId != null) {
                     learnIfMissing(player, pair.nullificationId);
                  }
               }

               syncMasteredDegradationModifier(instance, player);
            }
         }
      }
   }

   public static void handleOutgoingDamage(ServerPlayer attacker, LivingEntity target, DamageSource source) {
      Optional<ManasSkillInstance> optional = getMahoragaInstance(attacker);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         if (instance.isToggled()) {
            String rawKey = rawDamageKey(source);
            rememberDamageKey(attacker, rawKey);
            int built = incHitCount(attacker, target, rawKey);
            int learned = getLearnedStage(attacker, rawKey);
            int stage = Math.max(stageFromBuilt(built), learned);
            if (stage > learned) {
               setLearnedStage(attacker, rawKey, stage);
            }

            syncMasteredDegradationModifier(instance, attacker);
         }
      }
   }

   public static void handleOutgoingStageEffects(ServerPlayer attacker, LivingEntity target, DamageSource source, Pre event) {
      Optional<ManasSkillInstance> optional = getMahoragaInstance(attacker);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         if (instance.isToggled()) {
            String rawKey = rawDamageKey(source);
            int stage = getLearnedStage(attacker, rawKey);
            if (stage > 0) {
               float mult = getOutgoingStageDamageMultiplier(rawKey, stage);
               if (instance.isMastered(attacker)) {
                  mult += ((Double)CONFIG.masteredOutgoingDamageBonus.get()).floatValue();
               }

               event.setNewDamage(event.getNewDamage() * mult);
               applyOutgoingStageDisruption(target, stage);
            }
         }
      }
   }

   public static void handlePostDamageReduction(ServerPlayer player, DamageSource source, Pre event) {
      Optional<ManasSkillInstance> optional = getMahoragaInstance(player);
      if (!optional.isEmpty()) {
         ManasSkillInstance instance = optional.get();
         if (instance.isToggled()) {
            String rawKey = rawDamageKey(source);
            if (!rememberDamageKey(player, rawKey)) {
               adaptCurrentlyActiveEffects(player);
            }

            ListTag enabled = root(player).getList("enabledDamage", 8);
            if (containsString(enabled, rawKey)) {
               int dr = getDamageReduction(player, rawKey, instance.isMastered(player));
               float mult = 1.0F - dr / 100.0F;
               if (mult < 0.01F) {
                  mult = 0.01F;
               }

               event.setNewDamage(event.getNewDamage() * mult);
               syncMasteredDegradationModifier(instance, player);
            }
         }
      }
   }

   public static void openAdaptationMenu(ServerPlayer player, boolean toggled) {
      player.openMenu(
         new SimpleMenuProvider(
            (containerId, inventory, p) -> MahoragaAdaptationMenu.server(containerId, inventory, player, toggled),
            Component.translatable("tensuramoreskills.skill.divine_general_mahoraga")
         ),
         buf -> MahoragaAdaptationMenu.writeExtraData(player, buf, toggled)
      );
   }

   public static void selectAdaptationEntry(ServerPlayer player, int index) {
      List<MahoragaAdaptationMenu.Entry> entries = buildMenuEntries(player);
      if (index >= 0 && index < entries.size()) {
         setSelectedIndex(player, index);
      }
   }

   public static void toggleAdaptationEntry(ServerPlayer player, int index) {
      List<MahoragaAdaptationMenu.Entry> entries = buildMenuEntries(player);
      if (index >= 0 && index < entries.size()) {
         MahoragaAdaptationMenu.Entry entry = entries.get(index);
         setSelectedIndex(player, index);
         boolean on;
         if (entry.damage()) {
            on = toggleDamage(player, entry.id());
         } else {
            on = toggleEffect(player, entry.id());
            if (on) {
               removeEnabledEffectNow(player, entry.id());
            }
         }

         player.displayClientMessage(
            Component.translatable(
               on ? "tensuramoreskills.skill.divine_general_mahoraga.toggled_on" : "tensuramoreskills.skill.divine_general_mahoraga.toggled_off",
               new Object[]{entry.id()}
            ),
            true
         );
      }
   }

   public static boolean hasMahoraga(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills == null) {
         return false;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(SKILL_ID);
      return skill == null ? false : skills.getSkill(skill).isPresent();
   }

   public static boolean isMahoragaToggled(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills == null) {
         return false;
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(SKILL_ID);
      return skill == null ? false : skills.getSkill(skill).<Boolean>map(ManasSkillInstance::isToggled).orElse(false);
   }

   public static Optional<ManasSkillInstance> getMahoragaInstance(ServerPlayer player) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills == null) {
         return Optional.empty();
      }

      ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(SKILL_ID);
      return skill == null ? Optional.empty() : skills.getSkill(skill);
   }

   public static void sanitizeOnLogin(ServerPlayer player) {
      CompoundTag r = root(player);
      r.put("adaptedEffects", sanitizeStringList(r.getList("adaptedEffects", 8)));
      r.put("enabledEffects", sanitizeStringList(r.getList("enabledEffects", 8)));
      r.put("adaptedDamage", sanitizeStringList(r.getList("adaptedDamage", 8)));
      r.put("enabledDamage", sanitizeStringList(r.getList("enabledDamage", 8)));
   }

   public static void onClone(ServerPlayer oldPlayer, ServerPlayer newPlayer, boolean wasDeath) {
      CompoundTag oldRoot = getRootOrEmpty(oldPlayer);
      CompoundTag newRoot;
      if (!wasDeath) {
         newRoot = oldRoot.copy();
      } else {
         newRoot = new CompoundTag();
         newRoot.put("adaptedEffects", oldRoot.getList("adaptedEffects", 8).copy());
         newRoot.put("enabledEffects", oldRoot.getList("enabledEffects", 8).copy());
         newRoot.put("adaptedDamage", new ListTag());
         newRoot.put("enabledDamage", new ListTag());
         newRoot.put("takenHits", new CompoundTag());
         newRoot.put("hitCounters", new CompoundTag());
         newRoot.put("learnedStage", new CompoundTag());
      }

      newRoot.put("adaptedEffects", sanitizeStringList(newRoot.getList("adaptedEffects", 8)));
      newRoot.put("enabledEffects", sanitizeStringList(newRoot.getList("enabledEffects", 8)));
      newRoot.put("adaptedDamage", sanitizeStringList(newRoot.getList("adaptedDamage", 8)));
      newRoot.put("enabledDamage", sanitizeStringList(newRoot.getList("enabledDamage", 8)));
      newPlayer.getPersistentData().put("tms_mahoraga", newRoot);
   }

   public static List<MahoragaAdaptationMenu.Entry> buildMenuEntries(ServerPlayer player) {
      CompoundTag root = root(player);
      List<MahoragaAdaptationMenu.Entry> entries = new ArrayList<>();
      ListTag adaptedEffects = root.getList("adaptedEffects", 8);
      ListTag enabledEffects = root.getList("enabledEffects", 8);

      for (int i = 0; i < adaptedEffects.size(); i++) {
         String id = adaptedEffects.getString(i);
         entries.add(new MahoragaAdaptationMenu.Entry(false, id, containsString(enabledEffects, id), 0, 0));
      }

      ListTag adaptedDamage = root.getList("adaptedDamage", 8);
      ListTag enabledDamage = root.getList("enabledDamage", 8);
      CompoundTag learnedStages = root.getCompound("learnedStage");
      boolean mastered = getMahoragaInstance(player).map(inst -> inst.isMastered(player)).orElse(false);

      for (int i = 0; i < adaptedDamage.size(); i++) {
         String id = adaptedDamage.getString(i);
         entries.add(
            new MahoragaAdaptationMenu.Entry(true, id, containsString(enabledDamage, id), learnedStages.getInt(id), getDamageReduction(player, id, mastered))
         );
      }

      return entries;
   }

   public static int getSelectedIndex(ServerPlayer player, int size) {
      return size <= 0 ? 0 : Math.max(0, Math.min(size - 1, root(player).getInt("selectedIndex")));
   }

   public static void setSelectedIndex(ServerPlayer player, int index) {
      root(player).putInt("selectedIndex", Math.max(0, index));
   }

   @NotNull
   public List<Holder<MobEffect>> getImmuneEffects(ManasSkillInstance instance, @NotNull LivingEntity entity) {
      List<Holder<MobEffect>> out = new ArrayList<>();
      if (!instance.isToggled()) {
         return out;
      }

      if (entity instanceof ServerPlayer player) {
         ListTag enabled = root(player).getList("enabledEffects", 8);

         for (int i = 0; i < enabled.size(); i++) {
            String id = enabled.getString(i);
            MobEffect effect = resolveEffectCached(id);
            if (effect != null) {
               ResourceLocation rl = BuiltInRegistries.MOB_EFFECT.getKey(effect);
               if (rl != null) {
                  Optional<Reference<MobEffect>> holder = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceKey.create(Registries.MOB_EFFECT, rl));
                  holder.ifPresent(out::add);
               }
            }
         }
      }

      return out;
   }

   private static boolean toggleEffect(ServerPlayer player, String id) {
      CompoundTag root = root(player);
      ListTag enabled = root.getList("enabledEffects", 8);
      boolean on;
      if (containsString(enabled, id)) {
         removeString(enabled, id);
         on = false;
      } else {
         enabled.add(StringTag.valueOf(id));
         on = true;
      }

      root.put("enabledEffects", sanitizeStringList(enabled));
      return on;
   }

   private static boolean toggleDamage(ServerPlayer player, String id) {
      CompoundTag root = root(player);
      ListTag enabled = root.getList("enabledDamage", 8);
      String normalized = id.toLowerCase(Locale.ROOT);
      boolean on;
      if (containsString(enabled, normalized)) {
         removeString(enabled, normalized);
         on = false;
      } else {
         enabled.add(StringTag.valueOf(normalized));
         on = true;
      }

      root.put("enabledDamage", sanitizeStringList(enabled));
      return on;
   }

   private static boolean adaptCurrentlyActiveEffects(ServerPlayer player) {
      CompoundTag root = root(player);
      ListTag adapted = root.getList("adaptedEffects", 8);
      boolean changed = false;

      for (MobEffectInstance instance : player.getActiveEffects()) {
         if (instance != null) {
            Holder<MobEffect> holder = instance.getEffect();
            ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)holder.value());
            if (id != null) {
               String key = id.toString();
               if (!containsString(adapted, key)) {
                  adapted.add(StringTag.valueOf(key));
                  changed = true;
                  player.displayClientMessage(Component.translatable("tensuramoreskills.skill.divine_general_mahoraga.adapted_effect", new Object[]{key}), true);
               }
            }
         }
      }

      if (changed) {
         root.put("adaptedEffects", sanitizeStringList(adapted));
      }

      return changed;
   }

   private static void cleanseEnabledEffects(ServerPlayer player) {
      ListTag enabled = root(player).getList("enabledEffects", 8);
      if (!enabled.isEmpty()) {
         for (MobEffectInstance instance : new ArrayList(player.getActiveEffects())) {
            if (instance != null) {
               Holder<MobEffect> holder = instance.getEffect();
               ResourceLocation id = BuiltInRegistries.MOB_EFFECT.getKey((MobEffect)holder.value());
               if (id != null && containsString(enabled, id.toString())) {
                  player.removeEffect(holder);
               }
            }
         }
      }
   }

   private static void removeEnabledEffectNow(ServerPlayer player, String id) {
      MobEffect effect = resolveEffectCached(id);
      if (effect != null) {
         ResourceLocation rl = BuiltInRegistries.MOB_EFFECT.getKey(effect);
         if (rl != null) {
            Optional<Reference<MobEffect>> holder = BuiltInRegistries.MOB_EFFECT.getHolder(ResourceKey.create(Registries.MOB_EFFECT, rl));
            holder.ifPresent(player::removeEffect);
         }
      }
   }

   private static void applyInfiniteRegen(LivingEntity entity) {
      if (entity instanceof Player) {
         Holder<MobEffect> effect = TensuraMobEffects.getReference(TensuraMobEffects.INSTANT_REGENERATION);
         MobEffectInstance current = entity.getEffect(effect);
         if (current == null
            || current.getDuration() < (Integer)CONFIG.playerRegenRefreshBelowTicks.get()
            || current.getAmplifier() != (Integer)CONFIG.playerRegenAmplifier.get()) {
            entity.addEffect(
               new MobEffectInstance(effect, (Integer)CONFIG.playerRegenDurationTicks.get(), (Integer)CONFIG.playerRegenAmplifier.get(), false, false, false)
            );
         }
      } else {
         Holder<MobEffect> effect = TensuraMobEffects.getReference(TensuraMobEffects.SELF_REGENERATION);
         MobEffectInstance current = entity.getEffect(effect);
         if (current == null
            || current.getDuration() < (Integer)CONFIG.mobRegenRefreshBelowTicks.get()
            || current.getAmplifier() != (Integer)CONFIG.mobRegenAmplifier.get()) {
            entity.addEffect(
               new MobEffectInstance(effect, (Integer)CONFIG.mobRegenDurationTicks.get(), (Integer)CONFIG.mobRegenAmplifier.get(), false, false, false)
            );
         }
      }
   }

   private static void applyLearningBonus(ServerPlayer player, ManasSkillInstance mahoraga, int give) {
      Skills skills = SkillAPI.getSkillsFrom(player);
      if (skills != null) {
         List<ManasSkillInstance> snapshot = new ArrayList<>();

         try {
            snapshot.addAll(skills.getLearnedSkills());
         } catch (Throwable var7) {
         }

         for (ManasSkillInstance inst : snapshot) {
            if (inst != null) {
               safeAddMastery(inst, player, give);
            }
         }

         safeAddMastery(mahoraga, player, give);
      }
   }

   private static void safeAddMastery(ManasSkillInstance inst, LivingEntity entity, int give) {
      if (give > 0) {
         if (!tryInvokeBatchedMastery(inst, entity, give)) {
            for (int i = 0; i < give; i++) {
               try {
                  inst.addMasteryPoint(entity);
               } catch (Throwable ignored) {
                  break;
               }
            }
         }
      }
   }

   private static boolean tryInvokeBatchedMastery(ManasSkillInstance inst, LivingEntity entity, int give) {
      try {
         Method m = CACHED_ADD_MASTERY_ENTITY_INT;
         if (m == null) {
            m = inst.getClass().getMethod("addMasteryPoint", LivingEntity.class, int.class);
            m.setAccessible(true);
            CACHED_ADD_MASTERY_ENTITY_INT = m;
         }

         m.invoke(inst, entity, give);
         return true;
      } catch (Throwable var5) {
         try {
            Method m2 = CACHED_ADD_MASTERY_INT;
            if (m2 == null) {
               m2 = inst.getClass().getMethod("addMasteryPoint", int.class);
               m2.setAccessible(true);
               CACHED_ADD_MASTERY_INT = m2;
            }

            m2.invoke(inst, give);
            return true;
         } catch (Throwable var4) {
            return false;
         }
      }
   }

   private static void syncMasteredDegradationModifier(ManasSkillInstance instance, LivingEntity owner) {
      AttributeInstance degrade = owner.getAttribute(TensuraAttributes.RESISTANCE_DEGRADATION);
      if (degrade != null) {
         if (instance.isMastered(owner)) {
            degrade.addOrReplacePermanentModifier(
               new AttributeModifier(MAHORAGA_DEGRADE_ID, (Double)CONFIG.masteredResistanceDegradationBonus.get(), Operation.ADD_VALUE)
            );
         } else {
            degrade.removeModifier(MAHORAGA_DEGRADE_ID);
         }
      }
   }

   private static boolean rememberDamageKey(ServerPlayer player, String rawKey) {
      CompoundTag root = root(player);
      ListTag adapted = root.getList("adaptedDamage", 8);
      if (!containsString(adapted, rawKey)) {
         adapted.add(StringTag.valueOf(rawKey));
         root.put("adaptedDamage", sanitizeStringList(adapted));
         return true;
      } else {
         return false;
      }
   }

   private static int incHitCount(ServerPlayer attacker, LivingEntity target, String key) {
      CompoundTag root = root(attacker);
      CompoundTag counters = root.getCompound("hitCounters");
      String uuid = target.getUUID().toString();
      CompoundTag perTarget = counters.getCompound(uuid);
      int hits = perTarget.getInt(key) + 1;
      perTarget.putInt(key, hits);
      counters.put(uuid, perTarget);
      root.put("hitCounters", counters);
      return hits;
   }

   private static int stageFromBuilt(int builtHits) {
      if (builtHits >= (Integer)CONFIG.stage5BuiltHits.get()) {
         return 5;
      } else if (builtHits >= (Integer)CONFIG.stage4BuiltHits.get()) {
         return 4;
      } else if (builtHits >= (Integer)CONFIG.stage3BuiltHits.get()) {
         return 3;
      } else if (builtHits >= (Integer)CONFIG.stage2BuiltHits.get()) {
         return 2;
      } else {
         return builtHits >= 1 ? 1 : 0;
      }
   }

   private static int stageFromTaken(int takenHits) {
      if (takenHits >= (Integer)CONFIG.stage5BuiltHits.get()) {
         return 5;
      } else if (takenHits >= (Integer)CONFIG.stage4BuiltHits.get()) {
         return 4;
      } else if (takenHits >= (Integer)CONFIG.stage3BuiltHits.get()) {
         return 3;
      } else if (takenHits >= (Integer)CONFIG.stage2BuiltHits.get()) {
         return 2;
      } else {
         return takenHits >= 1 ? 1 : 0;
      }
   }

   private static int getLearnedStage(ServerPlayer player, String rawKey) {
      CompoundTag root = root(player);
      CompoundTag ls = root.getCompound("learnedStage");
      return ls.getInt(rawKey);
   }

   private static void setLearnedStage(ServerPlayer player, String rawKey, int stage) {
      CompoundTag root = root(player);
      CompoundTag ls = root.getCompound("learnedStage");
      ls.putInt(rawKey, stage);
      root.put("learnedStage", ls);
   }

   private static String rawDamageKey(DamageSource source) {
      String id;
      try {
         id = source.getMsgId();
      } catch (Throwable t) {
         id = "unknown";
      }

      if (id == null || id.isBlank()) {
         id = "unknown";
      }

      return id.toLowerCase(Locale.ROOT);
   }

   private static String bucketFromRaw(String raw) {
      String id = raw;
      if (id.contains("susanoo")) {
         return "susanoo";
      } else if (id.contains("cook") || id.contains("cooked")) {
         return "cook";
      } else if (id.contains("sever")) {
         return "severance";
      } else if (id.contains("spatial")) {
         return "spatial";
      } else if (id.contains("spirit")) {
         return "spiritual";
      } else if (id.contains("gravity")) {
         return "gravity";
      } else if (id.contains("holy")) {
         return "holy";
      } else if (id.contains("dark")) {
         return "darkness";
      } else if (id.contains("light")) {
         return "light";
      } else if (id.contains("magic")) {
         return "magic";
      } else if (id.contains("fire") || id.contains("flame")) {
         return "fire";
      } else if (id.contains("cold") || id.contains("frost")) {
         return "cold";
      } else if (id.contains("water")) {
         return "water";
      } else if (id.contains("wind")) {
         return "wind";
      } else if (id.contains("earth")) {
         return "earth";
      } else if (id.contains("electric") || id.contains("lightning")) {
         return "electricity";
      } else if (id.contains("poison")) {
         return "poison";
      } else if (id.contains("corrosion") || id.contains("acid")) {
         return "corrosion";
      } else {
         return id.contains("pierce") ? "pierce" : "physical";
      }
   }

   private static DivineGeneralMahoragaSkill.ResistPair resolveResistPair(String key) {
      return switch (key) {
         case "cold" -> new DivineGeneralMahoragaSkill.ResistPair(ResistanceSkills.COLD_RESISTANCE.getId(), ResistanceSkills.COLD_NULLIFICATION.getId());
         case "corrosion" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.CORROSION_RESISTANCE.getId(), ResistanceSkills.CORROSION_NULLIFICATION.getId()
         );
         case "darkness" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.DARKNESS_ATTACK_RESISTANCE.getId(), ResistanceSkills.DARKNESS_ATTACK_NULLIFICATION.getId()
         );
         case "earth" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.EARTH_ATTACK_RESISTANCE.getId(), ResistanceSkills.EARTH_ATTACK_NULLIFICATION.getId()
         );
         case "electricity" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.ELECTRICITY_RESISTANCE.getId(), ResistanceSkills.ELECTRICITY_NULLIFICATION.getId()
         );
         case "fire" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.FLAME_ATTACK_RESISTANCE.getId(), ResistanceSkills.FLAME_ATTACK_NULLIFICATION.getId()
         );
         case "gravity" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.GRAVITY_ATTACK_RESISTANCE.getId(), ResistanceSkills.GRAVITY_ATTACK_NULLIFICATION.getId()
         );
         case "holy" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.HOLY_ATTACK_RESISTANCE.getId(), ResistanceSkills.HOLY_ATTACK_NULLIFICATION.getId()
         );
         case "light" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.LIGHT_ATTACK_RESISTANCE.getId(), ResistanceSkills.LIGHT_ATTACK_NULLIFICATION.getId()
         );
         case "magic" -> new DivineGeneralMahoragaSkill.ResistPair(ResistanceSkills.MAGIC_RESISTANCE.getId(), ResistanceSkills.MAGIC_NULLIFICATION.getId());
         case "pierce" -> new DivineGeneralMahoragaSkill.ResistPair(ResistanceSkills.PIERCE_RESISTANCE.getId(), ResistanceSkills.PIERCE_NULLIFICATION.getId());
         case "poison" -> new DivineGeneralMahoragaSkill.ResistPair(ResistanceSkills.POISON_RESISTANCE.getId(), ResistanceSkills.POISON_NULLIFICATION.getId());
         case "spatial" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.SPATIAL_ATTACK_RESISTANCE.getId(), ResistanceSkills.SPATIAL_ATTACK_NULLIFICATION.getId()
         );
         case "spiritual" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.SPIRITUAL_ATTACK_RESISTANCE.getId(), ResistanceSkills.SPIRITUAL_ATTACK_NULLIFICATION.getId()
         );
         case "water" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.WATER_ATTACK_RESISTANCE.getId(), ResistanceSkills.WATER_ATTACK_NULLIFICATION.getId()
         );
         case "wind" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.WIND_ATTACK_RESISTANCE.getId(), ResistanceSkills.WIND_ATTACK_NULLIFICATION.getId()
         );
         case "physical" -> new DivineGeneralMahoragaSkill.ResistPair(
            ResistanceSkills.PHYSICAL_ATTACK_RESISTANCE.getId(), ResistanceSkills.PHYSICAL_ATTACK_NULLIFICATION.getId()
         );
         default -> null;
      };
   }

   private static void learnIfMissing(ServerPlayer player, ResourceLocation skillId) {
      try {
         ManasSkill skill = (ManasSkill)SkillAPI.getSkillRegistry().get(skillId);
         if (skill == null) {
            return;
         }

         if (!SkillUtils.hasSkill(player, skill)) {
            SkillHelper.learnSkill(player, new TensuraSkillInstance(skill));
         }
      } catch (Throwable var3) {
      }
   }

   private static MobEffect resolveEffectCached(String id) {
      MobEffect cached = EFFECT_CACHE.get(id);
      if (cached != null) {
         return cached;
      }

      MobEffect resolved = resolveEffect(id);
      if (resolved != null) {
         EFFECT_CACHE.put(id, resolved);
      }

      return resolved;
   }

   private static MobEffect resolveEffect(String id) {
      try {
         ResourceLocation rl = ResourceLocation.parse(id);
         return (MobEffect)BuiltInRegistries.MOB_EFFECT.get(rl);
      } catch (Throwable t) {
         return null;
      }
   }

   private static CompoundTag root(ServerPlayer player) {
      CompoundTag data = player.getPersistentData();
      if (!data.contains("tms_mahoraga")) {
         data.put("tms_mahoraga", new CompoundTag());
      }

      return data.getCompound("tms_mahoraga");
   }

   private static CompoundTag getRootOrEmpty(ServerPlayer player) {
      CompoundTag pd = player.getPersistentData();
      if (!pd.contains("tms_mahoraga")) {
         return new CompoundTag();
      }

      try {
         return pd.getCompound("tms_mahoraga");
      } catch (Throwable t) {
         return new CompoundTag();
      }
   }

   private static ListTag sanitizeStringList(ListTag in) {
      LinkedHashSet<String> set = new LinkedHashSet<>();

      for (int i = 0; i < in.size(); i++) {
         String s;
         try {
            s = in.getString(i);
         } catch (Throwable t) {
            continue;
         }

         if (s != null) {
            s = s.trim();
            if (!s.isEmpty()) {
               set.add(s);
            }
         }
      }

      ListTag out = new ListTag();

      for (String s : set) {
         out.add(StringTag.valueOf(s));
      }

      return out;
   }

   private static boolean containsString(ListTag list, String value) {
      for (int i = 0; i < list.size(); i++) {
         if (value.equals(list.getString(i))) {
            return true;
         }
      }

      return false;
   }

   private static void removeString(ListTag list, String value) {
      for (int i = list.size() - 1; i >= 0; i--) {
         if (value.equals(list.getString(i))) {
            list.remove(i);
         }
      }
   }

   private static boolean hasBladeAnywhere(ServerPlayer player) {
      for (ItemStack stack : player.getInventory().items) {
         if (DivineGeneralMahoragaSkill.BladeUtil.isBlade(stack)) {
            return true;
         }
      }

      for (ItemStack stack : player.getInventory().armor) {
         if (DivineGeneralMahoragaSkill.BladeUtil.isBlade(stack)) {
            return true;
         }
      }

      for (ItemStack stack : player.getInventory().offhand) {
         if (DivineGeneralMahoragaSkill.BladeUtil.isBlade(stack)) {
            return true;
         }
      }

      return false;
   }

   private static int getDamageReduction(ServerPlayer player, String rawKey, boolean mastered) {
      int cap = mastered ? (Integer)CONFIG.maxDamageReductionMasteredPercent.get() : (Integer)CONFIG.maxDamageReductionUnmasteredPercent.get();
      int hits = getTakenHits(player, rawKey);
      int total = (Integer)CONFIG.baseDamageReductionPercent.get() + hits / (Integer)CONFIG.hitsPerExtraReductionPercent.get();
      if (total > cap) {
         total = cap;
      }

      return total;
   }

   private static int getTakenHits(ServerPlayer player, String rawKey) {
      CompoundTag r = root(player);
      CompoundTag taken = r.getCompound("takenHits");
      return Math.max(0, taken.getInt(rawKey));
   }

   private static float getOutgoingStageDamageMultiplier(String rawKey, int stage) {
      boolean tensuraTyped = resolveResistPair(bucketFromRaw(rawKey)) != null;

      return switch (stage) {
         case 2 -> ((Double)CONFIG.stage2DamageMultiplier.get()).floatValue();
         case 3 -> ((Double)CONFIG.stage3DamageMultiplier.get()).floatValue();
         case 4 -> ((Double)CONFIG.stage4DamageMultiplier.get()).floatValue();
         case 5 -> tensuraTyped
            ? ((Double)CONFIG.stage5TensuraDamageMultiplier.get()).floatValue()
            : ((Double)CONFIG.stage5DamageMultiplier.get()).floatValue();
         default -> 1.0F;
      };
   }

   private static void applyOutgoingStageDisruption(LivingEntity target, int stage) {
      if (stage >= 4) {
         ;
      }
   }

   public static final class BladeUtil {
      private BladeUtil() {
      }

      public static boolean isBlade(ItemStack stack) {
         CompoundTag tag = getCustomData(stack);
         return tag.getBoolean("tms_extermination_blade");
      }

      public static ItemStack createBladeStack(ServerPlayer player, boolean mastered) {
         ItemStack stack = new ItemStack((ItemLike)TenaddexItems.EXTERMINATION_BLADE.get());
         CompoundTag tag = getCustomData(stack);
         tag.putBoolean("tms_extermination_blade", true);
         setCustomData(stack, tag);
         applyBladeEnchantments(player, stack, mastered);
         stack.set(DataComponents.CUSTOM_NAME, Component.literal("Extermination Blade").withStyle(ChatFormatting.LIGHT_PURPLE));
         return stack;
      }

      private static void applyBladeEnchantments(ServerPlayer player, ItemStack stack, boolean mastered) {
         Holder<Enchantment> severance = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.SEVERANCE);
         Holder<Enchantment> tsukumogami = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.TSUKUMOGAMI);
         Holder<Enchantment> unbreaking = TensuraEnchantmentHelper.getEnchantment(player.level(), Enchantments.UNBREAKING);
         Holder<Enchantment> sharpness = TensuraEnchantmentHelper.getEnchantment(player.level(), Enchantments.SHARPNESS);
         Holder<Enchantment> deadEndRainbow = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.DEAD_END_RAINBOW);
         Holder<Enchantment> soulEater = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.SOUL_EATER);
         Holder<Enchantment> energySteal = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.ENERGY_STEAL);
         Holder<Enchantment> holyWeapon = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.HOLY_WEAPON);
         Holder<Enchantment> holyCoat = TensuraEnchantmentHelper.getEnchantment(player.level(), TensuraEnchantments.HOLY_COAT);
         if (severance != null
            && level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.severanceBladeLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.severanceBladeLevelMastered.get()
               )
               > 0) {
            stack.enchant(
               severance,
               level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.severanceBladeLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.severanceBladeLevelMastered.get()
               )
            );
         }

         if (tsukumogami != null
            && level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.tsukumogamiLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.tsukumogamiLevelMastered.get()
               )
               > 0) {
            stack.enchant(
               tsukumogami,
               level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.tsukumogamiLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.tsukumogamiLevelMastered.get()
               )
            );
         }

         if (unbreaking != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.unbreakingLevel.get() > 0) {
            stack.enchant(unbreaking, (Integer)DivineGeneralMahoragaSkill.CONFIG.unbreakingLevel.get());
         }

         if (sharpness != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.sharpnessLevel.get() > 0) {
            stack.enchant(sharpness, (Integer)DivineGeneralMahoragaSkill.CONFIG.sharpnessLevel.get());
         }

         if (deadEndRainbow != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.deadEndRainbowLevel.get() > 0) {
            stack.enchant(deadEndRainbow, (Integer)DivineGeneralMahoragaSkill.CONFIG.deadEndRainbowLevel.get());
         }

         if (soulEater != null
            && level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.soulEaterLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.soulEaterLevelMastered.get()
               )
               > 0) {
            stack.enchant(
               soulEater,
               level(
                  mastered,
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.soulEaterLevel.get(),
                  (Integer)DivineGeneralMahoragaSkill.CONFIG.soulEaterLevelMastered.get()
               )
            );
         }

         if (energySteal != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.energyStealLevel.get() > 0) {
            stack.enchant(energySteal, (Integer)DivineGeneralMahoragaSkill.CONFIG.energyStealLevel.get());
         }

         if (holyWeapon != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.holyWeaponLevel.get() > 0) {
            stack.enchant(holyWeapon, (Integer)DivineGeneralMahoragaSkill.CONFIG.holyWeaponLevel.get());
         }

         if (holyCoat != null && (Integer)DivineGeneralMahoragaSkill.CONFIG.holyCoatLevel.get() > 0) {
            stack.enchant(holyCoat, (Integer)DivineGeneralMahoragaSkill.CONFIG.holyCoatLevel.get());
         }
      }

      private static int level(boolean mastered, int base, int masteredLevel) {
         return mastered ? masteredLevel : base;
      }

      private static CompoundTag getCustomData(ItemStack stack) {
         CustomData data = (CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY);
         return data.copyTag();
      }

      private static void setCustomData(ItemStack stack, CompoundTag tag) {
         if (tag.isEmpty()) {
            stack.remove(DataComponents.CUSTOM_DATA);
         } else {
            stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
         }
      }
   }

   private record ResistPair(ResourceLocation resistanceId, ResourceLocation nullificationId) {
   }
}
