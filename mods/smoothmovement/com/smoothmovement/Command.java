package com.smoothmovement;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.smoothmovement.time.ServerTime;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class Command {
   public LiteralArgumentBuilder<CommandSourceStack> build(CommandBuildContext buildContext) {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("smoothmovement")
            .then(
               ((LiteralArgumentBuilder)Commands.literal("setArtificialTPS").requires(stack -> stack.hasPermission(2)))
                  .then(
                     Commands.argument("tps", IntegerArgumentType.integer())
                        .executes(
                           context -> {
                              if (ServerTime.artificialLagTPS == -1) {
                                 ServerTime.artificialLagBaseMS = Math.max(
                                    0, 50 - (int)((CommandSourceStack)context.getSource()).getLevel().getServer().getAverageTickTimeNanos()
                                 );
                              }

                              ServerTime.artificialLagTPS = IntegerArgumentType.getInteger(context, "tps");
                              ((CommandSourceStack)context.getSource())
                                 .sendSystemMessage(
                                    Component.literal("Enable artifical lag for TPS:" + ServerTime.artificialLagTPS).withStyle(ChatFormatting.RED)
                                 );
                              return 1;
                           }
                        )
                  )
            ))
         .then(((LiteralArgumentBuilder)Commands.literal("stopArtificialLag").requires(stack -> stack.hasPermission(2))).executes(context -> {
            ServerTime.artificialLagTPS = -1;
            ((CommandSourceStack)context.getSource()).sendSystemMessage(Component.literal("Disabled artifical lag").withStyle(ChatFormatting.GREEN));
            return 1;
         }));
   }
}
