package com.github.wal_bos.moreskills.handler.phainon;

import com.github.wal_bos.moreskills.registry.skill.ShadeSkills;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillHelper;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class PhainonCommand {
   private PhainonCommand() {
   }

   @SubscribeEvent
   public static void register(RegisterCommandsEvent event) {
      register(event.getDispatcher());
   }

   private static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("phainon").requires(source -> source.hasPermission(2)))
               .then(((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("coreflames").then(Commands.literal("all").executes(context -> {
                  ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                  ManasSkillInstance instance = getOrGivePhainon(player);
                  PhainonHandler.grantAllCoreFlames(player, instance);
                  return 1;
               }))).then(Commands.literal("reset").executes(context -> {
                  ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                  Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
                  if (optional.isEmpty()) {
                     ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("You do not have Phainon.").withStyle(ChatFormatting.RED));
                     return 0;
                  } else {
                     PhainonHandler.resetAllCoreFlames(player, optional.get());
                     return 1;
                  }
               }))).then(Commands.literal("show").executes(context -> {
                  ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                  Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
                  if (optional.isEmpty()) {
                     ((CommandSourceStack)context.getSource()).sendFailure(Component.literal("You do not have Phainon.").withStyle(ChatFormatting.RED));
                     return 0;
                  } else {
                     PhainonHandler.sendCoreFlameProgress(player, optional.get());
                     return 1;
                  }
               }))))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("cycles")
                           .then(Commands.literal("all").executes(context -> {
                              ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                              ManasSkillInstance instance = getOrGivePhainon(player);
                              PhainonProgress progress = PhainonProgress.get(instance);
                              progress.setCycles(34);
                              player.sendSystemMessage(Component.literal("Phainon cycles set to " + progress.getCycles() + ".").withStyle(ChatFormatting.GOLD));
                              return 1;
                           })))
                        .then(Commands.literal("set").then(Commands.argument("amount", IntegerArgumentType.integer(0)).executes(context -> {
                           ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                           ManasSkillInstance instance = getOrGivePhainon(player);
                           int amount = IntegerArgumentType.getInteger(context, "amount");
                           PhainonProgress progress = PhainonProgress.get(instance);
                           progress.setCycles(amount);
                           player.sendSystemMessage(Component.literal("Phainon cycles set to " + progress.getCycles() + ".").withStyle(ChatFormatting.GOLD));
                           return 1;
                        }))))
                     .then(Commands.literal("add").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes(context -> {
                        ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                        ManasSkillInstance instance = getOrGivePhainon(player);
                        int amount = IntegerArgumentType.getInteger(context, "amount");
                        PhainonProgress progress = PhainonProgress.get(instance);
                        progress.setCycles(progress.getCycles() + amount);
                        player.sendSystemMessage(Component.literal("Phainon cycles are now " + progress.getCycles() + ".").withStyle(ChatFormatting.GOLD));
                        return 1;
                     }))))
                  .then(
                     Commands.literal("show")
                        .executes(
                           context -> {
                              ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
                              Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
                              if (optional.isEmpty()) {
                                 ((CommandSourceStack)context.getSource())
                                    .sendFailure(Component.literal("You do not have Phainon.").withStyle(ChatFormatting.RED));
                                 return 0;
                              } else {
                                 PhainonProgress progress = PhainonProgress.get(optional.get());
                                 player.sendSystemMessage(
                                    Component.literal("Phainon cycles: " + progress.getCycles() + "/34")
                                       .withStyle(progress.getCycles() >= 34 ? ChatFormatting.GOLD : ChatFormatting.GRAY)
                                 );
                                 return 1;
                              }
                           }
                        )
                  )
            )
      );
   }

   private static ManasSkillInstance getOrGivePhainon(ServerPlayer player) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
      if (optional.isPresent()) {
         return optional.get();
      } else {
         SkillHelper.learnSkill(player, (ManasSkill)ShadeSkills.PHAINON.get());
         Optional<ManasSkillInstance> after = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)ShadeSkills.PHAINON.get());
         if (after.isPresent()) {
            return after.get();
         } else {
            throw new IllegalStateException("Failed to give Phainon to " + player.getGameProfile().getName());
         }
      }
   }
}
