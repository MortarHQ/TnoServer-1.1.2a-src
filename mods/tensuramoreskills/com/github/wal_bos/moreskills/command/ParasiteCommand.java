package com.github.wal_bos.moreskills.command;

import com.github.wal_bos.moreskills.handler.ParasiteData;
import com.github.wal_bos.moreskills.handler.ParasiteHandler;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "tensuramoreskills")
public final class ParasiteCommand {
   private ParasiteCommand() {
   }

   @SubscribeEvent
   public static void onRegisterCommands(RegisterCommandsEvent event) {
      register(event.getDispatcher());
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                    "parasite"
                                 )
                                 .requires(source -> source.getEntity() instanceof ServerPlayer))
                              .then(Commands.literal("status").executes(context -> status(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
                           .then(Commands.literal("leave").executes(context -> leave(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
                        .then(Commands.literal("symbiosis").executes(context -> symbiosis(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
                     .then(Commands.literal("reclaim").executes(context -> reclaim(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
                  .then(Commands.literal("release").executes(context -> release(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
               .then(Commands.literal("expel").executes(context -> expel(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
            .then(
               ((LiteralArgumentBuilder)Commands.literal("npc")
                     .then(Commands.literal("toggle").executes(context -> npcToggle(((CommandSourceStack)context.getSource()).getPlayerOrException()))))
                  .then(
                     Commands.literal("random")
                        .then(
                           Commands.argument("enabled", BoolArgumentType.bool())
                              .executes(
                                 context -> npcRandom(
                                    ((CommandSourceStack)context.getSource()).getPlayerOrException(), BoolArgumentType.getBool(context, "enabled")
                                 )
                              )
                        )
                  )
            )
      );
   }

   private static int status(ServerPlayer player) {
      if (ParasiteData.isParasite(player)) {
         LivingEntity host = ParasiteHandler.getBondedHost(player);
         String hostName = host == null ? "unloaded or offline" : host.getName().getString();
         player.sendSystemMessage(
            Component.literal(
                  "Parasite host: "
                     + hostName
                     + " | Relationship: "
                     + ParasiteData.getRelationship(player)
                     + " | Takeover: "
                     + ParasiteData.getTakeover(player)
                     + " | Controlling: "
                     + ParasiteData.tempControlActive(player)
               )
               .withStyle(ChatFormatting.DARK_PURPLE)
         );
         if (host instanceof Mob) {
            player.sendSystemMessage(Component.literal("NPC random Symbiosis: " + ParasiteData.npcRandomEnabled(host)).withStyle(ChatFormatting.LIGHT_PURPLE));
         }

         return 1;
      } else if (ParasiteData.isHost(player)) {
         player.sendSystemMessage(
            Component.literal(
                  "Symbiote relationship: "
                     + ParasiteData.getRelationship(player)
                     + " | Takeover: "
                     + ParasiteData.getTakeover(player)
                     + " | Parasite control: "
                     + ParasiteData.tempControlActive(player)
               )
               .withStyle(ChatFormatting.LIGHT_PURPLE)
         );
         return 1;
      } else {
         player.sendSystemMessage(Component.literal("You are not in a Parasite bond.").withStyle(ChatFormatting.RED));
         return 0;
      }
   }

   private static int leave(ServerPlayer player) {
      if (!ParasiteHandler.leave(player, false, false)) {
         player.sendSystemMessage(Component.literal("You are not currently bonded as a parasite.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         ParasiteHandler.loseBodySkills(player);
         ParasiteHandler.setParasiteRace(player);
         return 1;
      }
   }

   private static int symbiosis(ServerPlayer player) {
      if (!ParasiteHandler.symbiosisUse(player)) {
         player.sendSystemMessage(
            Component.literal("Symbiosis could not be used. The parasite may be offline or the bond is invalid.").withStyle(ChatFormatting.RED)
         );
         return 0;
      } else {
         return 1;
      }
   }

   private static int reclaim(ServerPlayer player) {
      if (!ParasiteHandler.requestHostReclaim(player)) {
         player.sendSystemMessage(Component.literal("You are not a host currently under parasite control.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         return 1;
      }
   }

   private static int release(ServerPlayer player) {
      if (!ParasiteHandler.requestParasiteRelease(player)) {
         player.sendSystemMessage(Component.literal("You are not currently controlling a host.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         return 1;
      }
   }

   private static int expel(ServerPlayer player) {
      if (!ParasiteHandler.expelMerger(player)) {
         player.sendSystemMessage(Component.literal("The parasite cannot be expelled yet.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         return 1;
      }
   }

   private static int npcToggle(ServerPlayer player) {
      if (!ParasiteHandler.toggleNpcFromParasite(player)) {
         player.sendSystemMessage(Component.literal("Your current host is not an available NPC.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         return 1;
      }
   }

   private static int npcRandom(ServerPlayer player, boolean enabled) {
      if (!ParasiteHandler.setNpcRandomFromParasite(player, enabled)) {
         player.sendSystemMessage(Component.literal("Your current host is not an available NPC.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         player.sendSystemMessage(Component.literal("NPC random Symbiosis: " + enabled).withStyle(ChatFormatting.LIGHT_PURPLE));
         return 1;
      }
   }
}
