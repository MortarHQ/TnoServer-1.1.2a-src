package com.foxablazeultimate.ability.skill.raphael;

import com.foxablazeultimate.ability.skill.ciel.CielAwakening;
import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.ability.skill.shubniggurath.ShubNiggurathEvolution;
import com.foxablazeultimate.ability.skill.shubniggurath.ShubNiggurathSkill;
import com.foxablazeultimate.ability.skill.uriel.UrielSkill;
import com.foxablazeultimate.ability.skill.yogsothoth.YogSothothEvolution;
import com.foxablazeultimate.config.RaphaelConfig;
import com.foxablazeultimate.network.OpenRaphaelNamingPayload;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.ability.skill.unique.DegenerateSkill;
import io.github.manasmods.tensura.ability.skill.unique.GreatSageSkill;
import io.github.manasmods.tensura.ability.subclass.IRefining;
import io.github.manasmods.tensura.ability.subclass.IRepeatCrafting;
import io.github.manasmods.tensura.ability.subclass.ISynthesisSeparation;
import io.github.manasmods.tensura.block.entity.CharybdisCoreBlockEntity;
import io.github.manasmods.tensura.data.TensuraItemTags;
import io.github.manasmods.tensura.data.TensuraSkillTags;
import io.github.manasmods.tensura.entity.TensuraProjectile;
import io.github.manasmods.tensura.event.TensuraSkillEvents;
import io.github.manasmods.tensura.event.TensuraSkillEvents.SkillPlunderEvent;
import io.github.manasmods.tensura.menu.RefiningMenu;
import io.github.manasmods.tensura.menu.UncraftingMenu;
import io.github.manasmods.tensura.menu.container.SpatialStorageContainer;
import io.github.manasmods.tensura.network.s2c.OpenDegenerateMenuPayload;
import io.github.manasmods.tensura.network.s2c.OpenDegenerateMenuPayload.MenuType;
import io.github.manasmods.tensura.registry.advancement.TensuraCriteriaTriggers;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.ObjectSelectionHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.critereon.PlayerTrigger;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ResultContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.neoforged.fml.loading.FMLEnvironment;
import org.jetbrains.annotations.NotNull;

public class RaphaelSkill extends Skill implements IRefining<RaphaelSkill>, IRepeatCrafting<RaphaelSkill>, ISynthesisSeparation {
   private static final ResourceLocation RAPHAEL_MOVEMENT = id("movement_speed");
   private static final ResourceLocation RAPHAEL_ATTACK = id("attack_speed");
   private static final ResourceLocation RAPHAEL_CHANT = id("chant_speed");
   private static final ResourceLocation RAPHAEL_DODGE_M = id("melee_dodge");
   private static final ResourceLocation RAPHAEL_DODGE_P = id("projectile_dodge");
   private static final ResourceLocation RAPHAEL_LEARNING = id("learning");
   private static final ResourceLocation RAPHAEL_MASTERY = id("mastery");
   private static final ResourceLocation RAPHAEL_PRESENCE_SENSE = id("presence_sense");
   private static final ResourceLocation RAPHAEL_AURA_GAIN = id("aura_gain");
   private static final ResourceLocation RAPHAEL_MAGICULE_GAIN = id("magicule_gain");
   public static final String NBT_CUSTOM_NAME = "RaphaelCustomName";
   public static final String NBT_NAMED_FLAG = "RaphaelNamed";
   private static final int MODE_COUNT = 6;
   public static final String NBT_SYNTHESISE_SUBMODE = "RaphaelSynthSub";
   public static final int SYNTH_SUBMODE_DEFAULT = 0;
   public static final int SYNTH_SUBMODE_EVOLVE_URIEL = 1;
   public static final int SYNTH_SUBMODE_EVOLVE_SHUB_NIGGURATH = 2;
   public static final int SYNTH_SUBMODE_EVOLVE_YOG_SOTHOTH = 3;

   private static RaphaelConfig.RaphaelSettings cfg() {
      return RaphaelConfig.get().Raphael;
   }

   public RaphaelSkill() {
      super(SkillType.ULTIMATE);
   }

   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "textures/skill/raphael.png");
   }

   public MutableComponent getColoredName() {
      if (!FMLEnvironment.dist.isDedicatedServer()) {
         MutableComponent custom = RaphaelSkill.ClientCustomNameAccessor.tryGetLocalPlayerCustomName();
         if (custom != null) {
            return custom.withStyle(ChatFormatting.RED);
         }
      }

      MutableComponent name = super.getName();
      return name == null ? null : name.withStyle(ChatFormatting.RED);
   }

   public static String readCustomName(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.contains("RaphaelCustomName")) {
         return null;
      }

      String value = tag.getString("RaphaelCustomName");
      return value != null && !value.isBlank() ? value : null;
   }

   public static void applyCustomName(ManasSkillInstance instance, String rawName) {
      applyCustomName(instance, rawName, null);
   }

   public static void applyCustomName(ManasSkillInstance instance, String rawName, ServerPlayer player) {
      CompoundTag tag = instance.getOrCreateTag();
      boolean cleared;
      if (rawName != null && !rawName.isBlank()) {
         tag.putString("RaphaelCustomName", rawName);
         cleared = false;
      } else {
         tag.remove("RaphaelCustomName");
         cleared = true;
      }

      tag.putBoolean("RaphaelNamed", true);
      instance.markDirty();
      if (player != null) {
         MutableComponent feedback = cleared
            ? Component.translatable("foxablazeultimate.skill.raphael.naming.cleared").withStyle(ChatFormatting.GRAY)
            : Component.translatable("foxablazeultimate.skill.raphael.naming.applied", new Object[]{Component.literal(rawName).withStyle(ChatFormatting.RED)})
               .withStyle(ChatFormatting.GRAY);
         player.sendSystemMessage(feedback);
      }
   }

   public static void sendNamingPrompt(ServerPlayer player, ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      if (!tag.getBoolean("RaphaelNamed")) {
         ManasSkill skill = instance.getSkill();
         ResourceLocation skillId = skill.getRegistryName();
         if (skillId != null) {
            String defaultLangKey = String.format("%s.skill.%s", skillId.getNamespace(), skillId.getPath().replace('/', '.'));
            player.nextContainerCounter();
            NetworkManager.sendToPlayer(player, new OpenRaphaelNamingPayload(player.containerCounter, defaultLangKey, skillId));
         }
      }
   }

   public double getDefaultAcquiringMagiculeCost() {
      return Double.POSITIVE_INFINITY;
   }

   public double getMagiculeCost(LivingEntity entity, ManasSkillInstance instance, int mode) {
      boolean mastered = instance.isMastered(entity);
      RaphaelConfig.RaphaelSettings c = cfg();

      return switch (mode) {
         case 1 -> mastered ? c.magiculeCostAnalysisMastered : c.magiculeCostAnalysis;
         default -> 0.0;
         case 4 -> mastered ? c.magiculeCostSynthesiseMastered : c.magiculeCostSynthesise;
         case 5 -> mastered ? c.magiculeCostSeparateMastered : c.magiculeCostSeparate;
      };
   }

   public boolean checkAcquiringRequirement(Player entity, double newEP) {
      return false;
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity living) {
      return instance.getMastery() >= 0.0;
   }

   private static int sanitizeMode(int mode) {
      return Math.floorMod(mode, 6);
   }

   public int getModes(ManasSkillInstance instance) {
      return 6;
   }

   public int nextMode(LivingEntity entity, ManasSkillInstance instance, int mode, boolean reverse) {
      int safe = sanitizeMode(mode);
      if (reverse) {
         return safe == 0 ? 5 : safe - 1;
      } else {
         return safe == 5 ? 0 : safe + 1;
      }
   }

   public String getModeId(ManasSkillInstance instance, int mode) {
      return switch (sanitizeMode(mode)) {
         case 0 -> "raphael.analytical_appraisal";
         case 1 -> "raphael.analysis";
         case 2 -> "raphael.refine";
         case 3 -> "raphael.crafting";
         case 4 -> "raphael.synthesise";
         case 5 -> "raphael.separate";
         default -> super.getModeId(instance, mode);
      };
   }

   public boolean canTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         return true;
      }

      if (!(entity instanceof Player)) {
         return false;
      }

      CompoundTag tag = instance.getTag();
      return tag != null && (tag.getBoolean("Brewing") || tag.getBoolean("Repeating"));
   }

   public void onTick(ManasSkillInstance instance, LivingEntity entity) {
      if (instance.isToggled()) {
         gainMastery(instance, entity);
      }

      if (entity instanceof Player player) {
         if (this.onRepeatCraftingTick(instance, player)) {
            gainMastery(instance, entity);
         }

         if (this.onRefiningTick(instance, player)) {
            gainMastery(instance, entity);
         }
      }
   }

   private RaphaelSkill.AnalysisOutcome doAnalysis(ManasSkillInstance instance, LivingEntity entity) {
      RaphaelConfig.RaphaelSettings c = cfg();
      TensuraProjectile projectile = (TensuraProjectile)ObjectSelectionHelper.getTargetingEntity(
         TensuraProjectile.class, entity, c.analysisProjectileRange, 0.5, true, true, false
      );
      if (projectile != null && projectile.isAlive()) {
         ManasSkillInstance projSkill = projectile.getSkill();
         if (projSkill != null && projSkill.getMastery() >= 0.0 && projSkill.is(TensuraSkillTags.COPIABLE_MAGIC)) {
            entity.swing(InteractionHand.MAIN_HAND, true);
            return tryCopySkill(instance, entity, projSkill, projectile.getOwner());
         } else {
            sendFail(entity, "tensura.ability.activation_failed");
            playCastFail(entity);
            return RaphaelSkill.AnalysisOutcome.FAIL_COOLDOWN;
         }
      } else {
         LivingEntity target = ObjectSelectionHelper.getTargetingEntity(entity, c.analysisEntityRange, false);
         if (target != null && target.isAlive()) {
            if (target instanceof Player p && p.getAbilities().invulnerable) {
               sendFail(entity, "tensura.targeting.not_allowed");
               playCastFail(entity);
               return RaphaelSkill.AnalysisOutcome.FAIL_NO_COOLDOWN;
            } else {
               entity.swing(InteractionHand.MAIN_HAND, true);
               double chance = instance.isMastered(entity) ? c.analysisCopyChanceMastered : c.analysisCopyChance;
               if (entity.getRandom().nextInt(100) > chance) {
                  sendFail(entity, "tensura.ability.activation_failed");
                  playCastFail(entity);
                  return RaphaelSkill.AnalysisOutcome.FAIL_COOLDOWN;
               } else {
                  List<ManasSkillInstance> copiable = SkillAPI.getSkillsFrom(target).getLearnedSkills().stream().filter(s -> canCopySkill(s, c)).toList();
                  if (copiable.isEmpty()) {
                     sendFail(entity, "tensura.ability.activation_failed.plunder.empty");
                     playCastFail(entity);
                     return RaphaelSkill.AnalysisOutcome.FAIL_COOLDOWN;
                  } else {
                     ManasSkillInstance picked = copiable.get(target.getRandom().nextInt(copiable.size()));
                     return tryCopySkill(instance, entity, picked, target);
                  }
               }
            }
         } else {
            sendFail(entity, "tensura.targeting.not_targeted");
            return RaphaelSkill.AnalysisOutcome.FAIL_NO_COOLDOWN;
         }
      }
   }

   private static RaphaelSkill.AnalysisOutcome tryCopySkill(
      ManasSkillInstance instance, LivingEntity entity, ManasSkillInstance targetSkill, Entity sourceEntity
   ) {
      Changeable<ManasSkill> changeable = Changeable.of(targetSkill.getSkill());
      boolean blocked = ((SkillPlunderEvent)TensuraSkillEvents.SKILL_PLUNDER.invoker()).plunder(sourceEntity, entity, false, changeable).isFalse();
      if (blocked) {
         playCastFail(entity);
         return RaphaelSkill.AnalysisOutcome.FAIL_COOLDOWN;
      }

      if (!SkillHelper.learnSkill(entity, (ManasSkill)changeable.get(), instance.getRemoveTime())) {
         ManasSkill failedSkill = (ManasSkill)changeable.get();
         entity.sendSystemMessage(
            Component.translatable("tensura.ability.activation_failed.plunder", new Object[]{failedSkill.getChatDisplayName(true)})
               .withStyle(ChatFormatting.RED)
         );
         playCastFail(entity);
         return RaphaelSkill.AnalysisOutcome.FAIL_COOLDOWN;
      }

      SoundEvent castSound = (SoundEvent)TensuraSoundEvents.GENERIC_CAST.get();
      if (castSound != null) {
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), castSound, SoundSource.PLAYERS, 1.0F, 1.0F);
      }

      return RaphaelSkill.AnalysisOutcome.SUCCESS;
   }

   private static void sendFail(LivingEntity entity, String translationKey) {
      entity.sendSystemMessage(Component.translatable(translationKey).withStyle(ChatFormatting.RED));
   }

   private static void playCastFail(LivingEntity entity) {
      SoundEvent fail = (SoundEvent)TensuraSoundEvents.GENERIC_CAST_FAIL.get();
      if (fail != null) {
         entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), fail, SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   private static boolean canCopySkill(ManasSkillInstance instance, RaphaelConfig.RaphaelSettings c) {
      if (instance.isTemporarySkill()) {
         return false;
      } else if (instance.getMastery() < 0.0) {
         return false;
      } else if (instance.is(TensuraSkillTags.NO_PLUNDERING)) {
         return false;
      } else {
         ResourceLocation rl = instance.getSkill().getRegistryName();
         if (rl == null) {
            return false;
         } else if (!c.analysisAllowedNamespaces.contains(rl.getNamespace())) {
            return false;
         } else if (c.analysisBlacklist.contains(rl.toString())) {
            return false;
         } else if (!(instance.getSkill() instanceof Skill skill)) {
            return false;
         } else {
            String var8 = skill.getType().getNamespace();
            boolean typeAllowed = false;

            for (String allowed : c.analysisAllowedTypes) {
               if (allowed != null && allowed.equalsIgnoreCase(var8)) {
                  typeAllowed = true;
                  break;
               }
            }

            return !typeAllowed
               ? false
               : !"unique".equalsIgnoreCase(var8) || c.analysisUniqueWhitelist.isEmpty() || c.analysisUniqueWhitelist.contains(rl.toString());
         }
      }
   }

   private static void applyCooldownAndMastery(ManasSkillInstance instance, LivingEntity entity, int mode, boolean activated) {
      boolean mastered = instance.isMastered(entity);
      RaphaelConfig.RaphaelSettings c = cfg();
      int cd;
      if (activated && mode == 5) {
         cd = mastered ? c.activationCooldownSeparateSuccessMastered : c.activationCooldownSeparateSuccess;
      } else {
         cd = activated
            ? (mastered ? c.activationCooldownSuccessMastered : c.activationCooldownSuccess)
            : (mastered ? c.activationCooldownFailMastered : c.activationCooldownFail);
      }

      instance.setCoolDown(cd, mode);
      if (activated) {
         instance.addMasteryPoint(entity);
      }
   }

   private static void gainMastery(ManasSkillInstance instance, LivingEntity entity) {
      CompoundTag tag = instance.getOrCreateTag();
      int time = tag.getInt("activatedTimes");
      if (time % BASE_CONFIG.Mastery.masteryActivateTime == 0) {
         instance.addMasteryPoint(entity);
      }

      tag.putInt("activatedTimes", time + 1);
   }

   public void onPressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      GreatSageSkill sage = (GreatSageSkill)UniqueSkills.GREAT_SAGE.get();
      DegenerateSkill degen = (DegenerateSkill)UniqueSkills.DEGENERATE.get();
      switch (mode) {
         case 0:
            RaphaelDelegateHelper.delegateOnPressed(instance, "raphael_sage", sage, entity, keyNumber, mode);
            break;
         case 1:
            if (instance.onCoolDown(mode)) {
               return;
            }

            if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return;
            }

            RaphaelSkill.AnalysisOutcome outcome = this.doAnalysis(instance, entity);
            switch (outcome) {
               case SUCCESS:
                  applyCooldownAndMastery(instance, entity, mode, true);
                  return;
               case FAIL_COOLDOWN:
                  applyCooldownAndMastery(instance, entity, mode, false);
                  return;
               case FAIL_NO_COOLDOWN:
               default:
                  return;
            }
         case 2:
            this.openRefineOrRepeatCrafting(instance, entity);
            break;
         case 3:
            this.openUncraftingOrSynthesis(instance, entity);
            break;
         case 4:
            this.onSynthesisePressed(instance, entity, keyNumber, mode, degen);
            break;
         case 5:
            if (instance.onCoolDown(mode)) {
               return;
            }

            if (EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               return;
            }

            boolean activated = RaphaelDelegateHelper.delegateOnPressed(instance, "raphael_degenerate", degen, entity, keyNumber, mode - 3);
            applyCooldownAndMastery(instance, entity, mode, activated);
      }
   }

   private void onSynthesisePressed(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode, DegenerateSkill degen) {
      if (entity instanceof Player player && player.isShiftKeyDown()) {
         this.cycleSynthesiseSubMode(instance, player);
      } else {
         int subMode = readSynthesiseSubMode(instance);
         if (subMode == 1 && canEvolveToUriel(instance, entity)) {
            if (!instance.onCoolDown(mode)) {
               if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
                  boolean activated = this.doEvolveToUriel(entity, instance);
                  applyCooldownAndMastery(instance, entity, mode, activated);
               }
            }
         } else if (subMode == 2) {
            if (entity instanceof ServerPlayer player) {
               ShubNiggurathEvolution.tryEvolve(player);
            }
         } else if (subMode == 3) {
            if (entity instanceof ServerPlayer player) {
               YogSothothEvolution.tryEvolve(player);
            }
         } else if (!instance.onCoolDown(mode)) {
            if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
               BlockPos targetedCore = findTargetedCharybdisCore(entity);
               boolean activated = RaphaelDelegateHelper.delegateOnPressed(instance, "raphael_degenerate", degen, entity, keyNumber, mode - 3);
               if (targetedCore != null) {
                  activated = true;
                  if (entity instanceof ServerPlayer player && !CielState.isAwakened(player)) {
                     CielState.recordIntegratedCharybdisCores(player, 1);
                     CielAwakening.tryUnlockNaming(player);
                  }
               }

               applyCooldownAndMastery(instance, entity, mode, activated);
            }
         }
      }
   }

   private static int readSynthesiseSubMode(ManasSkillInstance instance) {
      CompoundTag tag = instance.getOrCreateTag();
      int v = tag.getInt("RaphaelSynthSub");

      return switch (v) {
         case 1 -> 1;
         case 2 -> 2;
         case 3 -> FoxAblazeUltimateSkills.yogSothothHolder().isPresent() ? 3 : 0;
         default -> 0;
      };
   }

   private void cycleSynthesiseSubMode(ManasSkillInstance instance, Player player) {
      int current = readSynthesiseSubMode(instance);
      List<Integer> available = new ArrayList<>();
      available.add(0);
      boolean canUriel = canEvolveToUriel(instance, player);
      if (canUriel) {
         available.add(1);
      }

      if (ShubNiggurathEvolution.canSelect(player)) {
         available.add(2);
      }

      if (YogSothothEvolution.canSelect(player)) {
         available.add(3);
      }

      int index = available.indexOf(current);
      int next = available.get(index < 0 ? 0 : (index + 1) % available.size());
      if (next != current) {
         instance.getOrCreateTag().putInt("RaphaelSynthSub", next);
         instance.markDirty();
      }

      this.announceSynthesiseSubMode(player, next);
   }

   private void announceSynthesiseSubMode(Player player, int subMode) {
      Component label = switch (subMode) {
         case 1 -> Component.translatable(
            "foxablazeultimate.skill.raphael.synthesise.submode.evolve", new Object[]{((UrielSkill)FoxAblazeUltimateSkills.URIEL.get()).getColoredName()}
         );
         case 2 -> Component.translatable(
            "foxablazeultimate.skill.raphael.synthesise.submode.evolve",
            new Object[]{((ShubNiggurathSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get()).getColoredName()}
         );
         case 3 -> Component.translatable(
            "foxablazeultimate.skill.raphael.synthesise.submode.evolve",
            new Object[]{FoxAblazeUltimateSkills.yogSothoth().map(skill -> skill.getColoredName()).orElseGet(Component::empty)}
         );
         default -> Component.translatable("foxablazeultimate.skill.mode.raphael.synthesise");
      };
      player.displayClientMessage(
         Component.translatable("foxablazeultimate.skill.raphael.synthesise.submode.switched", new Object[]{label}).withStyle(ChatFormatting.GOLD), true
      );
   }

   private static boolean canEvolveToUriel(ManasSkillInstance instance, LivingEntity entity) {
      ManasSkill uriel = (ManasSkill)FoxAblazeUltimateSkills.URIEL.get();
      if (uriel == null) {
         return false;
      }

      if (SkillUtils.hasSkill(entity, uriel)) {
         return false;
      }

      ManasSkill prison = (ManasSkill)UniqueSkills.INFINITY_PRISON.get();
      return prison == null ? false : SkillUtils.isSkillMastered(entity, prison);
   }

   private boolean doEvolveToUriel(LivingEntity entity, ManasSkillInstance instance) {
      ManasSkill uriel = (ManasSkill)FoxAblazeUltimateSkills.URIEL.get();
      ManasSkill prison = (ManasSkill)UniqueSkills.INFINITY_PRISON.get();
      if (uriel == null || prison == null) {
         return false;
      }

      if (entity.level().isClientSide()) {
         return false;
      }

      TensuraSkillInstance newInstance = new TensuraSkillInstance(uriel);
      newInstance.setMastery(0.0);
      newInstance.getOrCreateTag().putBoolean("NoMagiculeCost", true);
      newInstance.getOrCreateTag().putBoolean("GiftedInstance", true);
      newInstance.markDirty();
      if (!SkillHelper.learnSkill(entity, newInstance)) {
         return false;
      }

      SkillAPI.getSkillsFrom(entity).forgetSkill(prison);
      instance.getOrCreateTag().putInt("RaphaelSynthSub", 0);
      instance.markDirty();
      return true;
   }

   private void openRefineOrRepeatCrafting(ManasSkillInstance instance, LivingEntity entity) {
      if (entity.isShiftKeyDown()) {
         this.openRefiningMenu(entity, instance);
      } else {
         this.openRepeatCraftingMenu(entity, instance);
      }
   }

   private void openUncraftingOrSynthesis(ManasSkillInstance instance, LivingEntity entity) {
      if (entity instanceof ServerPlayer player) {
         player.closeContainer();
         if (player.isShiftKeyDown()) {
            this.openSynthesisSeparationMenu(player, instance);
         } else {
            player.nextContainerCounter();
            NetworkManager.sendToPlayer(
               player, new OpenDegenerateMenuPayload(MenuType.CRAFTING, player.containerCounter, player.getId(), this.getRegistryName())
            );
            player.containerMenu = new UncraftingMenu(player.containerCounter, player.getInventory(), this);
            player.initMenu(player.containerMenu);
         }

         player.playNotifySound((SoundEvent)TensuraSoundEvents.SPATIAL_STORAGE.get(), SoundSource.PLAYERS, 0.75F, 1.0F);
      }
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onLearnSkill(instance, entity);
      if (!(instance.getMastery() < 0.0) && !instance.isTemporarySkill()) {
         if (entity instanceof ServerPlayer player) {
            this.registerTrulyUnique(player);
            unlockAllSchematics(player);
            if (this.shouldPromptForCustomName(instance, entity)) {
               sendNamingPrompt(player, instance);
            }
         }
      }
   }

   protected boolean shouldPromptForCustomName(ManasSkillInstance instance, LivingEntity entity) {
      return true;
   }

   public void onSkillMastered(ManasSkillInstance instance, LivingEntity entity) {
      super.onSkillMastered(instance, entity);
      if (!instance.isTemporarySkill()) {
         if (instance.isToggled()) {
            removePassiveAttributes(entity);
            applyPassiveAttributes(entity, true);
         }
      }
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOn(instance, entity);
      if (!instance.isTemporarySkill()) {
         applyPassiveAttributes(entity, instance.isMastered(entity));
      }
   }

   public void onToggleOff(ManasSkillInstance instance, LivingEntity entity) {
      super.onToggleOff(instance, entity);
      removePassiveAttributes(entity);
   }

   public void onForgetSkill(ManasSkillInstance instance, LivingEntity entity) {
      super.onForgetSkill(instance, entity);
      removePassiveAttributes(entity);
      if (entity instanceof ServerPlayer player && instance.getMastery() >= 0.0 && !instance.isTemporarySkill()) {
         this.unregisterTrulyUnique(player);
      }
   }

   public void onRespawn(ManasSkillInstance instance, ServerPlayer player, boolean conqueredEnd) {
      super.onRespawn(instance, player, conqueredEnd);
      if (instance.isToggled() && instance.getMastery() >= 0.0 && !instance.isTemporarySkill()) {
         applyPassiveAttributes(player, instance.isMastered(player));
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      return true;
   }

   public boolean onRefining(ManasSkillInstance instance, RefiningMenu<?> menu, Level level, Player player, SimpleContainer input, ResultContainer result) {
      int before = result.getItem(0).getCount();
      boolean refined = super.onRefining(instance, menu, level, player, input, result);
      if (refined && player instanceof ServerPlayer serverPlayer && !CielState.isAwakened(serverPlayer)) {
         int produced = Math.max(0, result.getItem(0).getCount() - before);
         CielState.recordRefinedItems(serverPlayer, produced);
         CielAwakening.tryUnlockNaming(serverPlayer);
      }

      return refined;
   }

   public boolean onRefining(Level level, Player player, ManasSkillInstance instance, SpatialStorageContainer spatialContainer) {
      int outputSlot = this.getSpatialStorageIdOffset() + 8;
      int before = spatialContainer.getItem(outputSlot).getCount();
      boolean refined = super.onRefining(level, player, instance, spatialContainer);
      if (refined && player instanceof ServerPlayer serverPlayer && !CielState.isAwakened(serverPlayer)) {
         SpatialStorageContainer updated = this.getSpatialStorage(instance, player.registryAccess());
         int produced = Math.max(0, updated.getItem(outputSlot).getCount() - before);
         CielState.recordRefinedItems(serverPlayer, produced);
         CielAwakening.tryUnlockNaming(serverPlayer);
      }

      return refined;
   }

   private static BlockPos findTargetedCharybdisCore(LivingEntity entity) {
      if (entity.level().isClientSide()) {
         return null;
      }

      BlockPos pos = ObjectSelectionHelper.getPlayerPOVHitResult(entity.level(), entity, Fluid.NONE, 5.0).getBlockPos();
      return isCharybdisCore(entity.level(), pos) ? pos.immutable() : null;
   }

   private static boolean isCharybdisCore(Level level, BlockPos pos) {
      ResourceLocation id = BuiltInRegistries.BLOCK.getKey(level.getBlockState(pos).getBlock());
      return ResourceLocation.fromNamespaceAndPath("tensura", "charybdis_core").equals(id) && level.getBlockEntity(pos) instanceof CharybdisCoreBlockEntity;
   }

   @NotNull
   public SpatialStorageContainer getSpatialStorage(ManasSkillInstance instance, Provider provide) {
      SpatialStorageContainer container = new SpatialStorageContainer(20, 128);
      container.fromTag(instance.getOrCreateTag().getList("SpatialStorage", 10), provide);
      return container;
   }

   public int getSpatialStorageIdOffset() {
      return 11;
   }

   public boolean isAutoRefiningAllowed() {
      return true;
   }

   public boolean hasAutoCraftingTab() {
      return true;
   }

   public void openSpatialStoragePage(ServerPlayer player, LivingEntity owner, ManasSkillInstance instance, int page) {
      this.openRepeatCraftingMenu(player, owner, instance);
   }

   public int getMaximumBonusLevel() {
      return ((DegenerateSkill)UniqueSkills.DEGENERATE.get()).getMaximumBonusLevel();
   }

   public List<String> getSeparateBlacklistEnchantments() {
      return ((DegenerateSkill)UniqueSkills.DEGENERATE.get()).getSeparateBlacklistEnchantments();
   }

   public List<String> getSynthesisBlacklistEnchantments() {
      return ((DegenerateSkill)UniqueSkills.DEGENERATE.get()).getSynthesisBlacklistEnchantments();
   }

   public List<String> getBonusLevelBlackListEnchantments() {
      return ((DegenerateSkill)UniqueSkills.DEGENERATE.get()).getBonusLevelBlackListEnchantments();
   }

   private void registerTrulyUnique(ServerPlayer player) {
      if (RaphaelFusion.isTrulyUniqueEnabled(player)) {
         ServerLevel overworld = overworldOf(player);
         if (overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            boolean changed = false;

            for (ResourceLocation id : List.of(FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId())) {
               UUID currentOwner = unique.getOwner(id);
               if (currentOwner == null || currentOwner.equals(player.getUUID())) {
                  unique.addSkill(id, player.getUUID());
                  changed = true;
               }
            }

            if (changed) {
               unique.markDirty();
            }
         }
      }
   }

   private void unregisterTrulyUnique(ServerPlayer player) {
      ResourceLocation ownId = this.getRegistryName();
      ResourceLocation otherId = ownId != null && ownId.equals(FoxAblazeUltimateSkills.CIEL.getId())
         ? FoxAblazeUltimateSkills.RAPHAEL.getId()
         : FoxAblazeUltimateSkills.CIEL.getId();
      if (!SkillAPI.getSkillsFrom(player).getSkill(otherId).filter(other -> other.getMastery() >= 0.0 && !other.isTemporarySkill()).isPresent()) {
         ServerLevel overworld = overworldOf(player);
         if (overworld != null) {
            ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(overworld);
            boolean changed = false;

            for (ResourceLocation id : List.of(FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId())) {
               UUID owner = unique.getOwner(id);
               if (owner != null && owner.equals(player.getUUID())) {
                  unique.removeSkill(id);
                  changed = true;
               }
            }

            if (changed) {
               unique.markDirty();
            }
         }
      }
   }

   private static ServerLevel overworldOf(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      return server == null ? null : server.overworld();
   }

   static void maintainSchematicKnowledge(ServerPlayer player) {
      SkillStorage skills = SkillAPI.getSkillsFrom(player);
      boolean hasRaphaelFamily = skills.getSkill(FoxAblazeUltimateSkills.RAPHAEL.getId()).filter(RaphaelSkill::isPermanentInstance).isPresent()
         || skills.getSkill(FoxAblazeUltimateSkills.CIEL.getId()).filter(RaphaelSkill::isPermanentInstance).isPresent();
      if (hasRaphaelFamily) {
         unlockAllSchematics(player);
      }
   }

   private static boolean isPermanentInstance(ManasSkillInstance instance) {
      return instance.getMastery() >= 0.0 && !instance.isTemporarySkill();
   }

   private static void unlockAllSchematics(ServerPlayer player) {
      ITensuraPlayer data = TensuraStorages.getPlayerDataFrom(player);
      if (data != null) {
         Optional<Named<Item>> schematicTag = player.registryAccess().registryOrThrow(Registries.ITEM).getTag(TensuraItemTags.SCHEMATICS);
         if (!schematicTag.isEmpty()) {
            boolean anyUnlocked = false;

            for (Holder<Item> holder : schematicTag.get()) {
               Item item = (Item)holder.value();
               if (!data.hasSchematic(item)) {
                  data.unlockSchematic(item);
                  anyUnlocked = true;
               }
            }

            if (anyUnlocked) {
               data.markDirty();
               ((PlayerTrigger)TensuraCriteriaTriggers.LEARN_ALL_SCHEMATICS.get()).trigger(player);
            }
         }
      }
   }

   private static void applyPassiveAttributes(LivingEntity entity, boolean mastered) {
      RaphaelConfig.RaphaelSettings c = cfg();
      double learning = mastered ? c.learningBonusMastered : c.learningBonus;
      double mastery = mastered ? c.masteryBonusMastered : c.masteryBonus;
      double sense = mastered ? c.presenceSenseBonusMastered : c.presenceSenseBonus;
      double epGain = mastered ? c.epGainBonusMastered : c.epGainBonus;
      addModifier(entity, Attributes.MOVEMENT_SPEED, RAPHAEL_MOVEMENT, c.movementBonus, Operation.ADD_MULTIPLIED_BASE);
      addModifier(entity, Attributes.ATTACK_SPEED, RAPHAEL_ATTACK, c.attackSpeedBonus, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.CHANT_SPEED, RAPHAEL_CHANT, c.chantSpeedBonus, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, RAPHAEL_DODGE_M, c.dodgeChanceBonus, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, RAPHAEL_DODGE_P, c.dodgeChanceBonus, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.ABILITY_LEARNING_GAIN, RAPHAEL_LEARNING, learning, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.ABILITY_MASTERY_GAIN, RAPHAEL_MASTERY, mastery, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.PRESENCE_SENSE, RAPHAEL_PRESENCE_SENSE, sense, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.AURA_GAIN, RAPHAEL_AURA_GAIN, epGain, Operation.ADD_VALUE);
      addModifier(entity, TensuraAttributes.MAGICULE_GAIN, RAPHAEL_MAGICULE_GAIN, epGain, Operation.ADD_VALUE);
   }

   private static void removePassiveAttributes(LivingEntity entity) {
      removeModifier(entity, Attributes.MOVEMENT_SPEED, RAPHAEL_MOVEMENT);
      removeModifier(entity, Attributes.ATTACK_SPEED, RAPHAEL_ATTACK);
      removeModifier(entity, TensuraAttributes.CHANT_SPEED, RAPHAEL_CHANT);
      removeModifier(entity, TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, RAPHAEL_DODGE_M);
      removeModifier(entity, TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, RAPHAEL_DODGE_P);
      removeModifier(entity, TensuraAttributes.ABILITY_LEARNING_GAIN, RAPHAEL_LEARNING);
      removeModifier(entity, TensuraAttributes.ABILITY_MASTERY_GAIN, RAPHAEL_MASTERY);
      removeModifier(entity, TensuraAttributes.PRESENCE_SENSE, RAPHAEL_PRESENCE_SENSE);
      removeModifier(entity, TensuraAttributes.AURA_GAIN, RAPHAEL_AURA_GAIN);
      removeModifier(entity, TensuraAttributes.MAGICULE_GAIN, RAPHAEL_MAGICULE_GAIN);
   }

   private static void addModifier(LivingEntity entity, Holder<Attribute> attr, ResourceLocation id, double amount, Operation op) {
      AttributeInstance inst = entity.getAttribute(attr);
      if (inst != null) {
         inst.addOrReplacePermanentModifier(new AttributeModifier(id, amount, op));
      }
   }

   private static void removeModifier(LivingEntity entity, Holder<Attribute> attr, ResourceLocation id) {
      AttributeInstance inst = entity.getAttribute(attr);
      if (inst != null) {
         inst.removeModifier(id);
      }
   }

   private static ResourceLocation id(String path) {
      return ResourceLocation.fromNamespaceAndPath("foxablazeultimate", "raphael/" + path);
   }

   private enum AnalysisOutcome {
      SUCCESS,
      FAIL_COOLDOWN,
      FAIL_NO_COOLDOWN;
   }

   private static final class ClientCustomNameAccessor {
      static MutableComponent tryGetLocalPlayerCustomName() {
         try {
            LocalPlayer local = Minecraft.getInstance().player;
            if (local == null) {
               return null;
            }

            ManasSkill raphael = (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get();
            if (raphael == null) {
               return null;
            }

            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(local).getSkill(raphael);
            if (opt.isEmpty()) {
               return null;
            }

            String custom = RaphaelSkill.readCustomName(opt.get());
            return custom == null ? null : Component.literal(custom);
         } catch (Throwable ignored) {
            return null;
         }
      }
   }
}
