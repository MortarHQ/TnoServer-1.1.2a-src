package com.foxablazeultimate.command;

import com.foxablazeultimate.network.ShowWisdomCrystalEasterEggPayload;
import com.foxablazeultimate.predation.PredationFilterHelper;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.networking.NetworkManager;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

@EventBusSubscriber(modid = "foxablazeultimate", bus = Bus.GAME)
public final class PredationFilterCommand {
   private PredationFilterCommand() {
   }

   @SubscribeEvent
   public static void register(RegisterCommandsEvent event) {
      LiteralArgumentBuilder<CommandSourceStack> root = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("foxablazeultimate")
         .requires(source -> source.hasPermission(2));
      root.then(
         Commands.literal("predation_filter")
            .then(
               Commands.literal("add_slots")
                  .then(
                     Commands.argument("player", EntityArgument.player())
                        .then(
                           Commands.argument("amount", IntegerArgumentType.integer(1, 42))
                              .executes(
                                 ctx -> addSlots(
                                    (CommandSourceStack)ctx.getSource(), EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount")
                                 )
                              )
                        )
                  )
            )
      );
      LiteralArgumentBuilder<CommandSourceStack> debug = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("debug")
         .then(
            ((LiteralArgumentBuilder)Commands.literal("wisdom_crystal_easter_egg").executes(ctx -> triggerForSource((CommandSourceStack)ctx.getSource())))
               .then(
                  Commands.argument("player", EntityArgument.player())
                     .executes(ctx -> triggerEasterEgg((CommandSourceStack)ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))
               )
         );
      UltimateSkillRequirementDebugCommand.appendTo(debug);
      root.then(debug);
      event.getDispatcher().register(root);
   }

   private static int triggerForSource(CommandSourceStack source) {
      if (source.getEntity() instanceof ServerPlayer player) {
         return triggerEasterEgg(source, player);
      } else {
         source.sendFailure(Component.translatable("foxablazeultimate.debug.wisdom_crystal_easter_egg.player_required").withStyle(ChatFormatting.RED));
         return 0;
      }
   }

   private static int triggerEasterEgg(CommandSourceStack source, ServerPlayer player) {
      NetworkManager.sendToPlayer(player, new ShowWisdomCrystalEasterEggPayload());
      source.sendSuccess(
         () -> Component.translatable("foxablazeultimate.debug.wisdom_crystal_easter_egg.sent", new Object[]{player.getDisplayName()})
            .withStyle(ChatFormatting.GREEN),
         true
      );
      return 1;
   }

   private static int addSlots(CommandSourceStack source, ServerPlayer player, int amount) {
      return PredationFilterHelper.getRaphael(player)
         .map(
            instance -> {
               int extra = PredationFilterHelper.addExtraSlots(instance, amount);
               int total = PredationFilterHelper.getSlotCount(player, instance);
               source.sendSuccess(
                  () -> Component.translatable("foxablazeultimate.predation_filter.command.added", new Object[]{player.getDisplayName(), amount, total, extra})
                     .withStyle(ChatFormatting.GREEN),
                  true
               );
               return total;
            }
         )
         .orElseGet(
            () -> {
               source.sendFailure(
                  Component.translatable("foxablazeultimate.predation_filter.command.no_raphael", new Object[]{player.getDisplayName()})
                     .withStyle(ChatFormatting.RED)
               );
               return 0;
            }
         );
   }
}
