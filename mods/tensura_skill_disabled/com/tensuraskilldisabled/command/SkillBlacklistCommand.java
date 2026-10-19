package com.tensuraskilldisabled.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.tensuraskilldisabled.config.SkillBlacklistConfig;
import com.tensuraskilldisabled.handler.SkillBlacklistEvents;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

public final class SkillBlacklistCommand {
   private SkillBlacklistCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                          "skillblacklist"
                                       )
                                       .requires(source -> source.hasPermission(2)))
                                    .then(
                                       Commands.literal("add")
                                          .then(
                                             Commands.argument("skill_id", StringArgumentType.greedyString())
                                                .suggests(SkillBlacklistCommand::suggestSkillIds)
                                                .executes(SkillBlacklistCommand::addSkill)
                                          )
                                    ))
                                 .then(
                                    Commands.literal("remove")
                                       .then(
                                          Commands.argument("skill_id", StringArgumentType.greedyString())
                                             .suggests(SkillBlacklistCommand::suggestDisabledSkillIds)
                                             .executes(SkillBlacklistCommand::removeSkill)
                                       )
                                 ))
                              .then(
                                 Commands.literal("addmod")
                                    .then(
                                       Commands.argument("mod_id", StringArgumentType.string())
                                          .suggests(SkillBlacklistCommand::suggestModIds)
                                          .executes(SkillBlacklistCommand::addModSkills)
                                    )
                              ))
                           .then(
                              Commands.literal("removemod")
                                 .then(
                                    Commands.argument("mod_id", StringArgumentType.string())
                                       .suggests(SkillBlacklistCommand::suggestDisabledModIds)
                                       .executes(SkillBlacklistCommand::removeModSkills)
                                 )
                           ))
                        .then(Commands.literal("list").executes(SkillBlacklistCommand::listSkills)))
                     .then(Commands.literal("listmods").executes(SkillBlacklistCommand::listModsWithSkills)))
                  .then(Commands.literal("reload").executes(SkillBlacklistCommand::reloadConfig)))
               .then(Commands.literal("clean").executes(SkillBlacklistCommand::cleanSelf)))
            .executes(SkillBlacklistCommand::showHelp)
      );
   }

   private static int addSkill(CommandContext<CommandSourceStack> context) {
      String skillId = StringArgumentType.getString(context, "skill_id");
      if (!isValidSkillId(skillId)) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Invalid skill id. Use namespace:path, for example tensura:gluttony."));
         return 0;
      } else {
         boolean changed = SkillBlacklistConfig.getInstance().add(skillId);
         if (!changed) {
            ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Skill is already disabled: " + skillId));
            return 0;
         } else {
            ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Disabled skill: " + skillId).withStyle(ChatFormatting.GREEN), true);
            cleanOnlinePlayers((CommandSourceStack)context.getSource());
            return 1;
         }
      }
   }

   private static int removeSkill(CommandContext<CommandSourceStack> context) {
      String skillId = StringArgumentType.getString(context, "skill_id");
      boolean changed = SkillBlacklistConfig.getInstance().remove(skillId);
      if (!changed) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Skill is not disabled: " + skillId));
         return 0;
      } else {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Enabled skill: " + skillId).withStyle(ChatFormatting.GREEN), true);
         return 1;
      }
   }

   private static int addModSkills(CommandContext<CommandSourceStack> context) {
      String modId = StringArgumentType.getString(context, "mod_id");
      SkillBlacklistConfig config = SkillBlacklistConfig.getInstance();
      List<String> added = new ArrayList<>();

      for (ManasSkill skill : SkillAPI.getSkillRegistry()) {
         ResourceLocation id = skill.getRegistryName();
         if (id != null && id.getNamespace().equals(modId) && config.add(id.toString())) {
            added.add(id.toString());
         }
      }

      if (added.isEmpty()) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("No enabled skills found for mod id: " + modId));
         return 0;
      } else {
         ((CommandSourceStack)context.getSource())
            .sendSuccess(() -> Component.literal("Disabled " + added.size() + " skills from " + modId).withStyle(ChatFormatting.GREEN), true);
         cleanOnlinePlayers((CommandSourceStack)context.getSource());
         return added.size();
      }
   }

   private static int removeModSkills(CommandContext<CommandSourceStack> context) {
      String modId = StringArgumentType.getString(context, "mod_id");
      SkillBlacklistConfig config = SkillBlacklistConfig.getInstance();
      List<String> removed = new ArrayList<>();

      for (String skillId : config.getDisabledSkills()) {
         ResourceLocation id = ResourceLocation.tryParse(skillId);
         if (id != null && id.getNamespace().equals(modId) && config.remove(skillId)) {
            removed.add(skillId);
         }
      }

      if (removed.isEmpty()) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("No disabled skills found for mod id: " + modId));
         return 0;
      } else {
         ((CommandSourceStack)context.getSource())
            .sendSuccess(() -> Component.literal("Enabled " + removed.size() + " skills from " + modId).withStyle(ChatFormatting.GREEN), true);
         return removed.size();
      }
   }

   private static int listSkills(CommandContext<CommandSourceStack> context) {
      List<String> disabledSkills = SkillBlacklistConfig.getInstance().getDisabledSkills().stream().sorted().toList();
      if (disabledSkills.isEmpty()) {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("No skills are disabled."), false);
         return 1;
      }

      ((CommandSourceStack)context.getSource())
         .sendSuccess(() -> Component.literal("Disabled skills (" + disabledSkills.size() + "):").withStyle(ChatFormatting.AQUA), false);

      for (String skillId : disabledSkills) {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("- " + skillId), false);
      }

      return disabledSkills.size();
   }

   private static int listModsWithSkills(CommandContext<CommandSourceStack> context) {
      Map<String, Integer> counts = new HashMap<>();

      for (ManasSkill skill : SkillAPI.getSkillRegistry()) {
         ResourceLocation id = skill.getRegistryName();
         if (id != null) {
            counts.merge(id.getNamespace(), 1, Integer::sum);
         }
      }

      if (counts.isEmpty()) {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("No registered skills found."), false);
         return 1;
      } else {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Mods with registered skills:").withStyle(ChatFormatting.AQUA), false);
         counts.entrySet()
            .stream()
            .sorted(Entry.comparingByKey())
            .forEach(
               entry -> ((CommandSourceStack)context.getSource())
                  .sendSuccess(() -> Component.literal((String)entry.getKey() + " (" + entry.getValue() + ")"), false)
            );
         return counts.size();
      }
   }

   private static int reloadConfig(CommandContext<CommandSourceStack> context) {
      SkillBlacklistConfig.getInstance().load();
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Reloaded skill blacklist.").withStyle(ChatFormatting.GREEN), true);
      cleanOnlinePlayers((CommandSourceStack)context.getSource());
      return 1;
   }

   private static int cleanSelf(CommandContext<CommandSourceStack> context) {
      ServerPlayer player;
      try {
         player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      } catch (Exception e) {
         ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("Only players can use this command."));
         return 0;
      }

      int removed = SkillBlacklistEvents.removeDisabledSkills(player);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(() -> Component.literal("Removed " + removed + " disabled skills from you.").withStyle(ChatFormatting.GREEN), true);
      return removed;
   }

   private static int cleanOnlinePlayers(CommandSourceStack source) {
      MinecraftServer server = source.getServer();
      int removed = 0;

      for (ServerPlayer player : server.getPlayerList().getPlayers()) {
         removed += SkillBlacklistEvents.removeDisabledSkills(player);
      }

      return removed;
   }

   private static int showHelp(CommandContext<CommandSourceStack> context) {
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("/skillblacklist add <skill_id>"), false);
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("/skillblacklist remove <skill_id>"), false);
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("/skillblacklist addmod <mod_id>"), false);
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("/skillblacklist removemod <mod_id>"), false);
      ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("/skillblacklist list | listmods | reload | clean"), false);
      return 1;
   }

   private static CompletableFuture<Suggestions> suggestSkillIds(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
      String remaining = builder.getRemaining().toLowerCase();

      for (ManasSkill skill : SkillAPI.getSkillRegistry()) {
         ResourceLocation id = skill.getRegistryName();
         if (id != null && id.toString().toLowerCase().contains(remaining)) {
            builder.suggest(id.toString());
         }
      }

      return builder.buildFuture();
   }

   private static CompletableFuture<Suggestions> suggestDisabledSkillIds(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
      String remaining = builder.getRemaining().toLowerCase();
      SkillBlacklistConfig.getInstance()
         .getDisabledSkills()
         .stream()
         .filter(skillId -> skillId.toLowerCase().contains(remaining))
         .sorted()
         .forEach(builder::suggest);
      return builder.buildFuture();
   }

   private static CompletableFuture<Suggestions> suggestModIds(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
      Set<String> modIds = new HashSet<>();

      for (ManasSkill skill : SkillAPI.getSkillRegistry()) {
         ResourceLocation id = skill.getRegistryName();
         if (id != null) {
            modIds.add(id.getNamespace());
         }
      }

      return suggestStrings(modIds, builder);
   }

   private static CompletableFuture<Suggestions> suggestDisabledModIds(CommandContext<CommandSourceStack> context, SuggestionsBuilder builder) {
      Set<String> modIds = new HashSet<>();

      for (String skillId : SkillBlacklistConfig.getInstance().getDisabledSkills()) {
         ResourceLocation id = ResourceLocation.tryParse(skillId);
         if (id != null) {
            modIds.add(id.getNamespace());
         }
      }

      return suggestStrings(modIds, builder);
   }

   private static CompletableFuture<Suggestions> suggestStrings(Set<String> values, SuggestionsBuilder builder) {
      String remaining = builder.getRemaining().toLowerCase();
      values.stream().filter(value -> value.toLowerCase().contains(remaining)).sorted(Comparator.naturalOrder()).forEach(builder::suggest);
      return builder.buildFuture();
   }

   private static boolean isValidSkillId(String skillId) {
      ResourceLocation id = ResourceLocation.tryParse(skillId);
      return id != null && skillId.contains(":");
   }
}
