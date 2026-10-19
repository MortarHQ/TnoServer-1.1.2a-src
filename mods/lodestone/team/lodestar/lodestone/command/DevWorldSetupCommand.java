package team.lodestar.lodestone.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.GameRules.BooleanValue;

public class DevWorldSetupCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> register() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("devsetup").requires(cs -> cs.hasPermission(2)))
         .executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            MinecraftServer server = source.getServer();
            GameRules rules = server.getGameRules();
            ((BooleanValue)rules.getRule(GameRules.RULE_KEEPINVENTORY)).set(true, server);
            ((BooleanValue)rules.getRule(GameRules.RULE_DOMOBSPAWNING)).set(false, server);
            ((BooleanValue)rules.getRule(GameRules.RULE_DAYLIGHT)).set(false, server);
            ((BooleanValue)rules.getRule(GameRules.RULE_WEATHER_CYCLE)).set(false, server);
            ((BooleanValue)rules.getRule(GameRules.RULE_MOBGRIEFING)).set(false, server);
            source.getLevel().setWeatherParameters(6000, 0, false, false);
            source.getLevel().setDayTime(16000L);
            source.sendSuccess(() -> Component.translatable("lodestone.command.devsetup"), true);
            return 1;
         });
   }
}
