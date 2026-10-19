package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mortal_cultivation.util.FuelSpeedManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class ReloadFuelConfigCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)Commands.literal("mortalcultivation")
            .then(
               ((LiteralArgumentBuilder)Commands.literal("reload_fuel_config").requires(source -> source.hasPermission(2)))
                  .executes(ReloadFuelConfigCommand::execute)
            )
      );
   }

   private static int execute(CommandContext<CommandSourceStack> context) {
      CommandSourceStack source = (CommandSourceStack)context.getSource();

      try {
         FuelSpeedManager.reload();
         source.sendSuccess(() -> Component.translatable("mortal_cultivation.command.reload_fuel_config.success"), true);
         source.sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.reload_fuel_config.loaded", new Object[]{FuelSpeedManager.getAllFuelSpeeds().size()}),
            false
         );
         return 1;
      } catch (Exception e) {
         source.sendFailure(Component.translatable("mortal_cultivation.command.reload_fuel_config.failed", new Object[]{e.getMessage()}));
         e.printStackTrace();
         return 0;
      }
   }
}
