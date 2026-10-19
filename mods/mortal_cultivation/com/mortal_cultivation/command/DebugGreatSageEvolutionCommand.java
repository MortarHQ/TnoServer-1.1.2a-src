package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mortal_cultivation.ability.skill.unique.GreatSageEvolutionTracker;
import com.mortal_cultivation.registry.skill.CultivationSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DebugGreatSageEvolutionCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)Commands.literal("mortalcultivation")
            .then(
               Commands.literal("debug")
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("GreatSageEvolutionTracker")
                              .requires(source -> source.hasPermission(2)))
                           .then(Commands.argument("player", EntityArgument.player()).executes(DebugGreatSageEvolutionCommand::showPlayerProgress)))
                        .executes(DebugGreatSageEvolutionCommand::showSelfProgress)
                  )
            )
      );
   }

   private static int showPlayerProgress(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer targetPlayer = EntityArgument.getPlayer(context, "player");
      return showEvolutionProgress((CommandSourceStack)context.getSource(), targetPlayer);
   }

   private static int showSelfProgress(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      return showEvolutionProgress((CommandSourceStack)context.getSource(), player);
   }

   private static int showEvolutionProgress(CommandSourceStack source, ServerPlayer player) {
      int mischiefMastery = 0;
      boolean hasMischief = false;
      SkillStorage skills = SkillAPI.getSkillsFrom(player);
      Optional<ManasSkillInstance> mischiefOpt = skills.getSkill((ManasSkill)CultivationSkills.MISCHIEF.get());
      if (mischiefOpt.isPresent()) {
         ManasSkillInstance mischiefInstance = mischiefOpt.get();
         mischiefMastery = (int)mischiefInstance.getMastery();
         hasMischief = true;
      }

      boolean hasGreatSage = skills.getSkill((ManasSkill)CultivationSkills.GREAT_SAGE.get()).isPresent();
      int[] progress = GreatSageEvolutionTracker.getEvolutionProgress(player);
      int tntCount = progress[0];
      int goldenAppleCount = progress[1];
      int walkDistance = progress[2];
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.title")
            .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}),
         false
      );
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.player", new Object[]{player.getName().getString()})
            .withStyle(ChatFormatting.WHITE),
         false
      );
      if (hasGreatSage) {
         source.sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.status.evolved")
               .withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD}),
            false
         );
      } else if (hasMischief) {
         source.sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.status.has_mischief").withStyle(ChatFormatting.AQUA), false
         );
      } else {
         source.sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.status.no_mischief").withStyle(ChatFormatting.RED), false
         );
      }

      source.sendSuccess(() -> Component.literal(""), false);
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.requirements").withStyle(ChatFormatting.YELLOW), false
      );
      ChatFormatting masteryColor = mischiefMastery >= 1000 ? ChatFormatting.GREEN : ChatFormatting.RED;
      String masteryStatus = mischiefMastery >= 1000 ? " ✓" : " ✗";
      int finalMischiefMastery = mischiefMastery;
      String finalMasteryStatus = masteryStatus;
      source.sendSuccess(
         () -> Component.translatable(
               "mortal_cultivation.command.debug.great_sage_evolution.mastery", new Object[]{finalMischiefMastery, 1000, finalMasteryStatus}
            )
            .withStyle(masteryColor),
         false
      );
      ChatFormatting tntColor = tntCount >= 100 ? ChatFormatting.GREEN : ChatFormatting.RED;
      String tntStatus = tntCount >= 100 ? " ✓" : " ✗";
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.tnt", new Object[]{tntCount, 100, tntStatus}).withStyle(tntColor),
         false
      );
      ChatFormatting goldenAppleColor = goldenAppleCount >= 20 ? ChatFormatting.GREEN : ChatFormatting.RED;
      String goldenAppleStatus = goldenAppleCount >= 20 ? " ✓" : " ✗";
      source.sendSuccess(
         () -> Component.translatable(
               "mortal_cultivation.command.debug.great_sage_evolution.golden_apples", new Object[]{goldenAppleCount, 20, goldenAppleStatus}
            )
            .withStyle(goldenAppleColor),
         false
      );
      ChatFormatting walkColor = walkDistance >= 500000 ? ChatFormatting.GREEN : ChatFormatting.RED;
      String walkStatus = walkDistance >= 500000 ? " ✓" : " ✗";
      source.sendSuccess(
         () -> Component.translatable(
               "mortal_cultivation.command.debug.great_sage_evolution.walk_distance",
               new Object[]{formatDistance(walkDistance), formatDistance(500000), walkStatus}
            )
            .withStyle(walkColor),
         false
      );
      source.sendSuccess(() -> Component.literal(""), false);
      int conditionsMet = 0;
      if (mischiefMastery >= 1000) {
         conditionsMet++;
      }

      if (tntCount >= 100) {
         conditionsMet++;
      }

      if (goldenAppleCount >= 20) {
         conditionsMet++;
      }

      if (walkDistance >= 500000) {
         conditionsMet++;
      }

      ChatFormatting progressColor = conditionsMet == 4 ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
      int finalConditionsMet = conditionsMet;
      source.sendSuccess(
         () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.overall", new Object[]{finalConditionsMet, 4})
            .withStyle(progressColor),
         false
      );
      if (finalConditionsMet == 4 && !hasGreatSage) {
         source.sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.debug.great_sage_evolution.ready")
               .withStyle(new ChatFormatting[]{ChatFormatting.GOLD, ChatFormatting.BOLD}),
            false
         );
      }

      source.sendSuccess(() -> Component.literal("==========================================").withStyle(ChatFormatting.GOLD), false);
      return 1;
   }

   private static String formatDistance(int distance) {
      if (distance >= 1000000) {
         return String.format("%.2fM", distance / 1000000.0);
      } else {
         return distance >= 1000 ? String.format("%.1fK", distance / 1000.0) : String.valueOf(distance);
      }
   }
}
