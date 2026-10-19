package com.foxablazeultimate.command;

import com.foxablazeultimate.ability.skill.beelzebub.BeelzebubSkill;
import com.foxablazeultimate.ability.skill.ciel.CielSkill;
import com.foxablazeultimate.ability.skill.ciel.CielState;
import com.foxablazeultimate.ability.skill.raphael.RaphaelSkill;
import com.foxablazeultimate.ability.skill.uriel.UrielSkill;
import com.foxablazeultimate.ability.skill.velgrynd.VelgryndSkill;
import com.foxablazeultimate.ability.skill.yogsothoth.YogSothothSkill;
import com.foxablazeultimate.config.AzathothConfig;
import com.foxablazeultimate.config.BeelzebubConfig;
import com.foxablazeultimate.config.CielConfig;
import com.foxablazeultimate.config.FortunaConfig;
import com.foxablazeultimate.config.HellSniperConfig;
import com.foxablazeultimate.config.MammonConfig;
import com.foxablazeultimate.config.RaphaelConfig;
import com.foxablazeultimate.config.SatanaelConfig;
import com.foxablazeultimate.config.ShubNiggurathConfig;
import com.foxablazeultimate.config.VelgryndConfig;
import com.foxablazeultimate.event.AzathothAcquisitionHandler;
import com.foxablazeultimate.event.VelgryndAcquisitionHandler;
import com.foxablazeultimate.registry.FoxAblazeUltimateSkills;
import com.foxablazeultimate.world.FoxAblazeGameRules;
import com.foxablazeultimate.world.SatanaelAcquisitionData;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.skill.unique.DegenerateSkill;
import io.github.manasmods.tensura.ability.skill.unique.GluttonySkill;
import io.github.manasmods.tensura.ability.skill.unique.GreatSageSkill;
import io.github.manasmods.tensura.ability.skill.unique.GreedSkill;
import io.github.manasmods.tensura.ability.skill.unique.InfinityPrisonSkill;
import io.github.manasmods.tensura.ability.skill.unique.MathematicianSkill;
import io.github.manasmods.tensura.ability.skill.unique.MercilessSkill;
import io.github.manasmods.tensura.ability.skill.unique.SniperSkill;
import io.github.manasmods.tensura.ability.skill.unique.UsurperSkill;
import io.github.manasmods.tensura.ability.skill.unique.WrathSkill;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.skill.UniqueSkills;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.unique.ITrulyUnique;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.level.GameRules.BooleanValue;
import net.minecraft.world.level.GameRules.Key;

public final class UltimateSkillRequirementDebugCommand {
   private static final String VELDORA_DRAGON_KILLED_TAG = "FoxAblazeUltimateVeldoraDragonKilled";
   private static final String VELDORA_INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateVeldoraInactivityReclaimed";
   private static final String RAPHAEL_INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateRaphaelInactivityReclaimed";
   private static final String BEELZEBUB_INACTIVITY_RECLAIMED_TAG = "FoxAblazeUltimateBeelzebubInactivityReclaimed";
   private static final ResourceLocation END_DRAGON_ADVANCEMENT = ResourceLocation.fromNamespaceAndPath("minecraft", "end/kill_dragon");
   private static final List<String> BASE_SKILLS = List.of(
      "raphael", "ciel", "beelzebuth", "uriel", "veldora", "satanael", "shub_niggurath", "fortuna", "mammon", "hell_sniper", "velgrynd", "azathoth"
   );

   private UltimateSkillRequirementDebugCommand() {
   }

   public static void appendTo(LiteralArgumentBuilder<CommandSourceStack> debug) {
      LiteralArgumentBuilder<CommandSourceStack> requirements = Commands.literal("requirements");

      for (String skill : availableSkills()) {
         LiteralArgumentBuilder<CommandSourceStack> skillNode = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal(skill)
               .executes(ctx -> reportForSource((CommandSourceStack)ctx.getSource(), skill)))
            .then(
               Commands.argument("player", EntityArgument.player())
                  .executes(ctx -> report((CommandSourceStack)ctx.getSource(), skill, EntityArgument.getPlayer(ctx, "player")))
            );
         requirements.then(skillNode);
      }

      LiteralArgumentBuilder<CommandSourceStack> all = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("all")
            .executes(ctx -> reportAllForSource((CommandSourceStack)ctx.getSource())))
         .then(
            Commands.argument("player", EntityArgument.player())
               .executes(ctx -> reportAll((CommandSourceStack)ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))
         );
      requirements.then(all);
      debug.then(requirements);
   }

   private static int reportForSource(CommandSourceStack source, String skill) {
      return source.getEntity() instanceof ServerPlayer player ? report(source, skill, player) : playerRequired(source);
   }

   private static int reportAllForSource(CommandSourceStack source) {
      return source.getEntity() instanceof ServerPlayer player ? reportAll(source, player) : playerRequired(source);
   }

   private static int playerRequired(CommandSourceStack source) {
      source.sendFailure(Component.translatable("foxablazeultimate.debug.requirements.player_required"));
      return 0;
   }

   private static int reportAll(CommandSourceStack source, ServerPlayer player) {
      for (String skill : availableSkills()) {
         report(source, skill, player);
      }

      return 1;
   }

   private static int report(CommandSourceStack source, String skill, ServerPlayer player) {
      Component skillName = skillName(skill);
      sendHeader(source, skillName, player);
      switch (skill) {
         case "raphael":
            reportRaphael(source, player, skillName);
            break;
         case "ciel":
            reportCiel(source, player, skillName);
            break;
         case "beelzebuth":
            reportBeelzebub(source, player, skillName);
            break;
         case "uriel":
            reportUriel(source, player, skillName);
            break;
         case "veldora":
            reportVeldora(source, player, skillName);
            break;
         case "satanael":
            reportSatanael(source, player, skillName);
            break;
         case "shub_niggurath":
            reportShubNiggurath(source, player, skillName);
            break;
         case "fortuna":
            reportFortuna(source, player, skillName);
            break;
         case "mammon":
            reportMammon(source, player, skillName);
            break;
         case "hell_sniper":
            reportHellSniper(source, player, skillName);
            break;
         case "velgrynd":
            reportVelgrynd(source, player, skillName);
            break;
         case "azathoth":
            reportAzathoth(source, player, skillName);
            break;
         case "yog_sothoth":
            reportYogSothoth(source, player, skillName);
            break;
         default:
            source.sendFailure(Component.translatable("foxablazeultimate.debug.requirements.unknown_skill", new Object[]{skill}));
      }

      return 1;
   }

   private static void reportRaphael(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (!hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
         && !hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())
         && !CielState.peekAwakened(player)) {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.GREAT_SAGE.get()),
               ((GreatSageSkill)UniqueSkills.GREAT_SAGE.get()).getColoredName()
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.DEGENERATE.get()),
               ((DegenerateSkill)UniqueSkills.DEGENERATE.get()).getColoredName()
            )
         );
         RaphaelConfig.RaphaelSettings config = RaphaelConfig.get() == null ? new RaphaelConfig.RaphaelSettings() : RaphaelConfig.get().Raphael;
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         boolean race = !config.fusionRequireSpecialRace || isSpecialRace(existence);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.special_race", race));
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.not_reclaimed",
               !player.getPersistentData().getBoolean("FoxAblazeUltimateRaphaelInactivityReclaimed"),
               skillName
            )
         );
         sendCondition(
            source,
            uniqueCondition(
               player, FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId()
            )
         );
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      } else {
         sendAlreadyOwned(source, skillName);
      }
   }

   private static void reportCiel(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (!CielState.peekAwakened(player) && !hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get())) {
         CielConfig.CielSettings config = CielConfig.settings();
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         boolean raphael = permanentMastered(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get());
         if (!config.requireMasteredRaphael) {
            raphael = permanent(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get());
         }

         sendCondition(
            source,
            condition(
               config.requireMasteredRaphael
                  ? "foxablazeultimate.debug.requirements.condition.permanent_mastered"
                  : "foxablazeultimate.debug.requirements.condition.permanent",
               raphael,
               ((RaphaelSkill)FoxAblazeUltimateSkills.RAPHAEL.get()).getColoredName()
            )
         );
         boolean race = !config.requireTrueDemonLord || isTrueDemonLordOrHero(existence);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.true_demon_lord_or_hero", race));
         int requiredCores = Math.max(0, config.requiredCharybdisCores);
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.charybdis_cores",
               CielState.peekIntegratedCharybdisCores(player) >= requiredCores,
               CielState.peekIntegratedCharybdisCores(player),
               requiredCores
            )
         );
         int requiredRefined = Math.max(0, config.requiredRefinedItemCount);
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.refined_items",
               CielState.peekRefinedItemCount(player) >= requiredRefined,
               CielState.peekRefinedItemCount(player),
               requiredRefined
            )
         );
         double threshold = Math.max(0.0, config.awakeningHealthPercent);
         double health = player.getMaxHealth() <= 0.0F ? 100.0 : player.getHealth() * 100.0 / player.getMaxHealth();
         sendCondition(
            source,
            condition("foxablazeultimate.debug.requirements.condition.health_below", health < threshold, formatPercent(health), formatPercent(threshold))
         );
         int timeout = Math.max(0, config.combatTimeoutTicks);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.in_combat", CielState.peekInCombat(player, timeout), timeout / 20.0));
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.naming_unlocked", CielState.peekNamingUnlocked(player)));
         sendCondition(
            source,
            uniqueCondition(
               player, FoxAblazeGameRules.RAPHAEL_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.RAPHAEL.getId(), FoxAblazeUltimateSkills.CIEL.getId()
            )
         );
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      } else {
         sendAlreadyOwned(source, skillName);
      }
   }

   private static void reportBeelzebub(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.GLUTTONY.get()),
               ((GluttonySkill)UniqueSkills.GLUTTONY.get()).getColoredName()
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.MERCILESS.get()),
               ((MercilessSkill)UniqueSkills.MERCILESS.get()).getColoredName()
            )
         );
         BeelzebubConfig.BeelzebubSettings config = BeelzebubConfig.get() == null ? new BeelzebubConfig.BeelzebubSettings() : BeelzebubConfig.get().Beelzebub;
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.special_race",
               !config.fusionRequireSpecialRace || isSpecialRace(TensuraStorages.getExistenceFrom(player))
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.not_reclaimed",
               !player.getPersistentData().getBoolean("FoxAblazeUltimateBeelzebubInactivityReclaimed"),
               skillName
            )
         );
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.BEELZEBUB_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.BEELZEBUB.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportUriel(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.URIEL.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         boolean holder = hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
            || hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.raphael_or_ciel", holder));
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.INFINITY_PRISON.get()),
               ((InfinityPrisonSkill)UniqueSkills.INFINITY_PRISON.get()).getColoredName()
            )
         );
         sendInfo(source, "foxablazeultimate.debug.requirements.note.uriel_manual");
      }
   }

   private static void reportVeldora(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.VELDORA.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         boolean dragon = player.getPersistentData().getBoolean("FoxAblazeUltimateVeldoraDragonKilled") || hasEndDragonAdvancement(player);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.ender_dragon", dragon));
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.not_reclaimed",
               !player.getPersistentData().getBoolean("FoxAblazeUltimateVeldoraInactivityReclaimed"),
               skillName
            )
         );
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         double ep = existence == null ? 0.0 : existence.getEP();
         double magicule = existence == null ? 0.0 : existence.getMagicule();
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.ep", ep >= 400000.0, formatWhole(ep), "400000"));
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.magicule", magicule >= 400000.0, formatWhole(magicule), "400000"));
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.wind_skill", hasMasteredWindSkill(player)));
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.VELDORA_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.VELDORA.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportSatanael(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SATANAEL.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.mastered",
               mastered(player, (ManasSkill)UniqueSkills.WRATH.get()),
               ((WrathSkill)UniqueSkills.WRATH.get()).getColoredName()
            )
         );
         SatanaelAcquisitionData data = SatanaelAcquisitionData.peek(player.serverLevel());
         boolean witnessedNamedDeath = data != null && data.hasWitnessedNamedDeath(player.getUUID());
         boolean inactivityReclaimed = data != null && data.isInactivityReclaimed(player.getUUID());
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.named_death", witnessedNamedDeath));
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.not_reclaimed", !inactivityReclaimed, skillName));
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         int required = SatanaelConfig.get() == null ? 25000 : Math.max(0, SatanaelConfig.get().Satanael.requiredSoulPoints);
         double soul = existence == null ? 0.0 : existence.getSoulPoints();
         sendCondition(
            source, condition("foxablazeultimate.debug.requirements.condition.soul_points", soul >= required, formatWhole(soul), Integer.toString(required))
         );
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.SATANAEL_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.SATANAEL.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportShubNiggurath(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.SHUB_NIGGURATH.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.true_demon_lord", existence != null && existence.isTrueDemonLord()));
         boolean raphael = nonTemporaryMastered(player, (ManasSkill)FoxAblazeUltimateSkills.RAPHAEL.get())
            || nonTemporaryMastered(player, (ManasSkill)FoxAblazeUltimateSkills.CIEL.get());
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.permanent_mastered_raphael_or_ciel", raphael));
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               nonTemporaryMastered(player, (ManasSkill)FoxAblazeUltimateSkills.URIEL.get()),
               ((UrielSkill)FoxAblazeUltimateSkills.URIEL.get()).getColoredName()
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               nonTemporaryMastered(player, (ManasSkill)FoxAblazeUltimateSkills.BEELZEBUB.get()),
               ((BeelzebubSkill)FoxAblazeUltimateSkills.BEELZEBUB.get()).getColoredName()
            )
         );
         ShubNiggurathConfig.ShubNiggurathSettings config = ShubNiggurathConfig.get() == null
            ? new ShubNiggurathConfig.ShubNiggurathSettings()
            : ShubNiggurathConfig.get().ShubNiggurath;
         long mastered = SkillAPI.getSkillsFrom(player)
            .getLearnedSkills()
            .stream()
            .filter(instance -> instance.getMastery() >= 0.0)
            .filter(instance -> instance.isMastered(player))
            .count();
         int requiredSkills = Math.max(0, config.requiredMasteredSkillCount);
         sendCondition(
            source, condition("foxablazeultimate.debug.requirements.condition.mastered_skill_count", mastered >= requiredSkills, mastered, requiredSkills)
         );
         double maxEp = EnergyHelper.getMaxEP(player);
         double requiredEp = Math.max(0.0, config.requiredMaxEP);
         sendCondition(
            source, condition("foxablazeultimate.debug.requirements.condition.max_ep", maxEp >= requiredEp, formatWhole(maxEp), formatWhole(requiredEp))
         );
         double magicule = existence == null ? 0.0 : existence.getMagicule();
         double requiredMagicule = Math.max(0.0, config.requiredMagicule);
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.magicule", magicule >= requiredMagicule, formatWhole(magicule), formatWhole(requiredMagicule)
            )
         );
         sendCondition(
            source, uniqueCondition(player, FoxAblazeGameRules.SHUB_NIGGURATH_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.SHUB_NIGGURATH.getId())
         );
         sendInfo(source, "foxablazeultimate.debug.requirements.note.shub_manual");
      }
   }

   private static void reportFortuna(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.FORTUNA.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               permanentMastered(player, (ManasSkill)UniqueSkills.MATHEMATICIAN.get()),
               ((MathematicianSkill)UniqueSkills.MATHEMATICIAN.get()).getColoredName()
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               permanentMastered(player, (ManasSkill)UniqueSkills.USURPER.get()),
               ((UsurperSkill)UniqueSkills.USURPER.get()).getColoredName()
            )
         );
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.true_hero_or_demon_lord", isTrueDemonLordOrHero(existence)));
         double magicule = existence == null ? 0.0 : existence.getMagicule();
         double required = FortunaConfig.get() == null ? 500000.0 : Math.max(0.0, FortunaConfig.get().Fortuna.acquisitionMagiculeCost);
         sendCondition(
            source, condition("foxablazeultimate.debug.requirements.condition.magicule", magicule >= required, formatWhole(magicule), formatWhole(required))
         );
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.FORTUNA_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.FORTUNA.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportMammon(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.MAMMON.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               permanentMastered(player, (ManasSkill)UniqueSkills.GREED.get()),
               ((GreedSkill)UniqueSkills.GREED.get()).getColoredName()
            )
         );
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.true_hero_or_demon_lord", isTrueDemonLordOrHero(existence)));
         AttributeInstance maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
         double minimum = ((RangedAttribute)TensuraAttributes.MAX_MAGICULE.value()).getMinValue();
         double spendableCapacity = maximumMagicule == null ? 0.0 : Math.max(0.0, maximumMagicule.getBaseValue() - minimum);
         double required = Math.max(0.0, MammonConfig.settings().acquisitionMaximumMagiculeCost);
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.maximum_magicule_capacity",
               spendableCapacity >= required,
               formatWhole(spendableCapacity),
               formatWhole(required)
            )
         );
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.MAMMON_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.MAMMON.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportHellSniper(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.HELL_SNIPER.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.permanent_mastered",
               permanentMastered(player, (ManasSkill)UniqueSkills.SNIPER.get()),
               ((SniperSkill)UniqueSkills.SNIPER.get()).getColoredName()
            )
         );
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.true_hero_or_demon_lord", isTrueDemonLordOrHero(existence)));
         AttributeInstance maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
         double minimum = ((RangedAttribute)TensuraAttributes.MAX_MAGICULE.value()).getMinValue();
         double permanentCapacity = maximumMagicule == null ? 0.0 : Math.max(0.0, maximumMagicule.getBaseValue());
         HellSniperConfig.HellSniperSettings config = HellSniperConfig.settings();
         double requiredCapacity = Math.max(0.0, config.acquisitionRequiredMaximumMagicule);
         double capacityCost = Math.max(0.0, config.acquisitionMaximumMagiculeCost);
         double currentMagicule = existence == null ? 0.0 : Math.max(0.0, existence.getMagicule());
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.magicule",
               currentMagicule >= requiredCapacity,
               formatWhole(currentMagicule),
               formatWhole(requiredCapacity)
            )
         );
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.max_magicule",
               permanentCapacity >= requiredCapacity && maximumMagicule != null && maximumMagicule.getBaseValue() - minimum >= capacityCost,
               formatWhole(permanentCapacity),
               formatWhole(requiredCapacity)
            )
         );
         sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.HELL_SNIPER_TRULY_UNIQUE, skillName, FoxAblazeUltimateSkills.HELL_SNIPER.getId()));
         sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
      }
   }

   private static void reportVelgrynd(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.VELGRYND.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         VelgryndConfig.VelgryndSettings config = VelgryndConfig.settings();
         sendCondition(source, condition("foxablazeultimate.debug.requirements.condition.automatic_enabled", config.acquisitionEnabled));
         if (config.acquisitionRequiredSkills != null) {
            for (String value : config.acquisitionRequiredSkills) {
               ResourceLocation id = value == null ? null : ResourceLocation.tryParse(value);
               ManasSkill required = id == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               Component name = required == null ? Component.literal(String.valueOf(value)) : required.getChatDisplayName(false);
               boolean satisfied = required != FoxAblazeUltimateSkills.VELGRYND.get()
                  && VelgryndAcquisitionHandler.prerequisite(player, required, config.acquisitionRequiresMastery).isPresent();
               sendCondition(
                  source,
                  condition(
                     config.acquisitionRequiresMastery
                        ? "foxablazeultimate.debug.requirements.condition.permanent_mastered"
                        : "foxablazeultimate.debug.requirements.condition.permanent",
                     satisfied,
                     name
                  )
               );
            }
         }

         AttributeInstance maximum = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
         double minimum = TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged ? ranged.getMinValue() : 0.0;
         double spendable = maximum == null ? 0.0 : Math.max(0.0, maximum.getBaseValue() - minimum);
         double cost = VelgryndSkill.acquisitionMagiculeCost();
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.maximum_magicule_capacity",
               maximum != null && Double.isFinite(spendable) && spendable >= cost,
               formatWhole(spendable),
               formatWhole(cost)
            )
         );
         if (config.acquisitionEnabled) {
            sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
         }
      }
   }

   private static void reportAzathoth(CommandSourceStack source, ServerPlayer player, Component skillName) {
      if (hasSkill(player, (ManasSkill)FoxAblazeUltimateSkills.AZATHOTH.get())) {
         sendAlreadyOwned(source, skillName);
      } else {
         AzathothConfig.Settings config = AzathothConfig.settings();
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.automatic_enabled",
               config.acquisitionEnabled && config.acquisitionRequiredSkills != null && !config.acquisitionRequiredSkills.isEmpty()
            )
         );
         if (config.acquisitionRequiredSkills != null) {
            for (String value : config.acquisitionRequiredSkills) {
               ResourceLocation id = value == null ? null : ResourceLocation.tryParse(value);
               ManasSkill required = id == null ? null : (ManasSkill)SkillAPI.getSkillRegistry().get(id);
               Optional<ManasSkillInstance> matched = AzathothAcquisitionHandler.requiredSkill(player, id, config.acquisitionRequiresMastery);
               Component name = required == null ? Component.literal(String.valueOf(value)) : required.getChatDisplayName(false);
               if (FoxAblazeUltimateSkills.RAPHAEL.getId().equals(id)) {
                  name = Component.empty().append(name).append(" / ").append(((CielSkill)FoxAblazeUltimateSkills.CIEL.get()).getChatDisplayName(false));
               }

               boolean satisfied = required != FoxAblazeUltimateSkills.AZATHOTH.get() && matched.isPresent();
               sendCondition(
                  source,
                  condition(
                     config.acquisitionRequiresMastery
                        ? "foxablazeultimate.debug.requirements.condition.permanent_mastered"
                        : "foxablazeultimate.debug.requirements.condition.permanent",
                     satisfied,
                     name
                  )
               );
            }
         }

         IExistence existence = TensuraStorages.getExistenceFrom(player);
         double magicule = existence == null ? 0.0 : existence.getMagicule();
         double cost = config.acquisitionMagiculeCost;
         sendCondition(
            source,
            condition(
               "foxablazeultimate.debug.requirements.condition.magicule",
               existence != null && Double.isFinite(magicule) && Double.isFinite(cost) && cost >= 0.0 && magicule >= cost,
               formatWhole(magicule),
               formatWhole(cost)
            )
         );
         if (config.acquisitionEnabled) {
            sendInfo(source, "foxablazeultimate.debug.requirements.note.automatic");
         }
      }
   }

   private static void reportYogSothoth(CommandSourceStack source, ServerPlayer player, Component skillName) {
      Optional<YogSothothSkill> optionalYog = FoxAblazeUltimateSkills.yogSothoth();
      if (!optionalYog.isEmpty()) {
         ManasSkill yog = (ManasSkill)optionalYog.get();
         if (hasSkill(player, yog)) {
            sendAlreadyOwned(source, skillName);
         } else {
            for (ResourceLocation prerequisite : List.of(
               YogSothothSkill.INFINITY_PRISON, YogSothothSkill.ABSOLUTE_SEVERANCE, YogSothothSkill.USURPER, YogSothothSkill.TIME_TRAVELER
            )) {
               ManasSkill requiredSkill = (ManasSkill)SkillAPI.getSkillRegistry().get(prerequisite);
               Component requiredName = requiredSkill == null ? Component.literal(prerequisite.toString()) : requiredSkill.getChatDisplayName(false);
               sendCondition(
                  source,
                  condition("foxablazeultimate.debug.requirements.condition.permanent_mastered", permanentMastered(player, requiredSkill), requiredName)
               );
            }

            AttributeInstance maximumMagicule = player.getAttribute(TensuraAttributes.MAX_MAGICULE);
            double minimum = TensuraAttributes.MAX_MAGICULE.value() instanceof RangedAttribute ranged ? ranged.getMinValue() : 0.0;
            double permanentCapacity = maximumMagicule == null ? 0.0 : Math.max(0.0, maximumMagicule.getBaseValue());
            double requiredCapacity = YogSothothSkill.acquisitionMagiculeCost();
            sendCondition(
               source,
               condition(
                  "foxablazeultimate.debug.requirements.condition.max_magicule",
                  maximumMagicule != null && permanentCapacity - minimum >= requiredCapacity,
                  formatWhole(permanentCapacity),
                  formatWhole(requiredCapacity)
               )
            );
            sendCondition(source, uniqueCondition(player, FoxAblazeGameRules.YOG_SOTHOTH_TRULY_UNIQUE, skillName, yog.getRegistryName()));
            sendInfo(source, "foxablazeultimate.debug.requirements.note.yog_sothoth_manual");
         }
      }
   }

   private static List<String> availableSkills() {
      List<String> skills = new ArrayList<>(BASE_SKILLS);
      if (FoxAblazeUltimateSkills.yogSothothHolder().isPresent()) {
         skills.add("yog_sothoth");
      }

      return skills;
   }

   private static boolean hasSkill(ServerPlayer player, ManasSkill skill) {
      return skill != null && SkillAPI.getSkillsFrom(player).getSkill(skill).isPresent();
   }

   private static boolean permanent(ServerPlayer player, ManasSkill skill) {
      return skill != null
         && SkillAPI.getSkillsFrom(player).getSkill(skill).filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance()).isPresent();
   }

   private static boolean permanentMastered(ServerPlayer player, ManasSkill skill) {
      return skill != null
         && SkillAPI.getSkillsFrom(player)
            .getSkill(skill)
            .filter(instance -> !instance.isTemporarySkill() && !instance.isSubInstance())
            .filter(instance -> instance.isMastered(player))
            .isPresent();
   }

   private static boolean nonTemporaryMastered(ServerPlayer player, ManasSkill skill) {
      return skill != null
         && SkillAPI.getSkillsFrom(player)
            .getSkill(skill)
            .filter(instance -> !instance.isTemporarySkill())
            .filter(instance -> instance.isMastered(player))
            .isPresent();
   }

   private static boolean mastered(ServerPlayer player, ManasSkill skill) {
      return skill != null && SkillUtils.isSkillMastered(player, skill);
   }

   private static boolean isSpecialRace(IExistence existence) {
      return existence != null && (existence.isHeroEgg() || existence.isTrueHero() || existence.isDemonLordSeed() || existence.isTrueDemonLord());
   }

   private static boolean isTrueDemonLordOrHero(IExistence existence) {
      return existence != null && (existence.isTrueDemonLord() || existence.isTrueHero());
   }

   private static boolean hasMasteredWindSkill(ServerPlayer player) {
      return SkillAPI.getSkillsFrom(player).getLearnedSkills().stream().anyMatch(instance -> {
         if (!instance.isMastered(player)) {
            return false;
         }

         ResourceLocation id = instance.getSkill().getRegistryName();
         if (id == null) {
            return false;
         }

         String path = id.getPath().toLowerCase(Locale.ROOT);
         return path.contains("wind") && (path.contains("manipulation") || path.contains("domination"));
      });
   }

   private static boolean hasEndDragonAdvancement(ServerPlayer player) {
      MinecraftServer server = player.getServer();
      if (server == null) {
         return false;
      }

      AdvancementHolder advancement = server.getAdvancements().get(END_DRAGON_ADVANCEMENT);
      return advancement != null && player.getAdvancements().getOrStartProgress(advancement).isDone();
   }

   private static UltimateSkillRequirementDebugCommand.Condition uniqueCondition(
      ServerPlayer player, Key<BooleanValue> rule, Component skillName, ResourceLocation... skillIds
   ) {
      if (rule != null && player.level().getGameRules().getBoolean(rule)) {
         ITrulyUnique unique = TensuraStorages.getUniqueStorageFrom(player.getServer().overworld());
         UUID owner = null;

         for (ResourceLocation skillId : skillIds) {
            if (skillId != null && unique.hasSkill(skillId)) {
               UUID candidate = unique.getOwner(skillId);
               if (candidate != null) {
                  owner = candidate;
                  break;
               }
            }
         }

         if (owner == null) {
            return condition("foxablazeultimate.debug.requirements.condition.unique.free", true, skillName);
         } else {
            return owner.equals(player.getUUID())
               ? condition("foxablazeultimate.debug.requirements.condition.unique.self", true, skillName)
               : condition("foxablazeultimate.debug.requirements.condition.unique.taken", false, skillName, owner.toString());
         }
      } else {
         return condition("foxablazeultimate.debug.requirements.condition.unique.disabled", true, skillName);
      }
   }

   private static Component skillName(String path) {
      return Component.translatable("foxablazeultimate.skill." + path);
   }

   private static UltimateSkillRequirementDebugCommand.Condition condition(String key, boolean complete, Object... args) {
      return new UltimateSkillRequirementDebugCommand.Condition(key, complete, args);
   }

   private static void sendHeader(CommandSourceStack source, Component skillName, ServerPlayer player) {
      source.sendSuccess(
         () -> Component.translatable("foxablazeultimate.debug.requirements.header", new Object[]{skillName, player.getDisplayName()})
            .withStyle(ChatFormatting.GOLD),
         false
      );
   }

   private static void sendAlreadyOwned(CommandSourceStack source, Component skillName) {
      source.sendSuccess(
         () -> Component.translatable("foxablazeultimate.debug.requirements.already_owned", new Object[]{skillName}).withStyle(ChatFormatting.GREEN), false
      );
   }

   private static void sendCondition(CommandSourceStack source, UltimateSkillRequirementDebugCommand.Condition condition) {
      String lineKey = condition.complete ? "foxablazeultimate.debug.requirements.line.complete" : "foxablazeultimate.debug.requirements.line.missing";
      source.sendSuccess(
         () -> Component.translatable(lineKey, new Object[]{Component.translatable(condition.key, condition.args)})
            .withStyle(condition.complete ? ChatFormatting.GREEN : ChatFormatting.RED),
         false
      );
   }

   private static void sendInfo(CommandSourceStack source, String key) {
      source.sendSuccess(() -> Component.translatable(key).withStyle(ChatFormatting.YELLOW), false);
   }

   private static String formatWhole(double value) {
      return String.format(Locale.ROOT, "%.0f", Math.max(0.0, value));
   }

   private static String formatPercent(double value) {
      return String.format(Locale.ROOT, "%.2f", Math.max(0.0, value));
   }

   private record Condition(String key, boolean complete, Object[] args) {
   }
}
