package com.github.b4ndithelps.tennogamenolife.command;

import com.github.b4ndithelps.tennogamenolife.util.DamageTestManager;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class JorCommand {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("aqzl").requires(source -> source.hasPermission(0)))
            .then(
               Commands.argument("enabled", BoolArgumentType.bool())
                  .executes(context -> executeJorCommand(context, BoolArgumentType.getBool(context, "enabled")))
            )
      );
   }

   private static int executeJorCommand(CommandContext<CommandSourceStack> context, boolean enabled) {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      if (source.getEntity() instanceof ServerPlayer player) {
         DamageTestManager.setDamageTestMode(player.getUUID(), enabled);
         String message = enabled ? "§a伤害测试模式已启用，你的攻击伤害将会在聊天栏显示" : "§c伤害测试模式已禁用";
         source.sendSuccess(() -> Component.literal(message), false);
         return 1;
      } else {
         source.sendFailure(Component.literal("§c只有玩家才能使用此命令"));
         return 0;
      }
   }
}
