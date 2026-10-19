package cn.leolezury.eternalstarlight.common.command;

import cn.leolezury.eternalstarlight.common.data.ESDimensions;
import cn.leolezury.eternalstarlight.common.registry.ESWeathers;
import cn.leolezury.eternalstarlight.common.util.ESWeatherUtil;
import cn.leolezury.eternalstarlight.common.weather.AbstractWeather;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.ResourceArgument;
import net.minecraft.commands.arguments.TimeArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;

public class ESWeatherCommand {
   public static ArgumentBuilder<CommandSourceStack, ?> register(CommandBuildContext commandBuildContext) {
      return ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("weather").requires(commandSourceStack -> commandSourceStack.hasPermission(2)))
            .then(
               ((RequiredArgumentBuilder)Commands.argument("weather", ResourceArgument.resource(commandBuildContext, ESWeathers.REGISTRY_KEY))
                     .executes(
                        commandContext -> setWeather(
                           (CommandSourceStack)commandContext.getSource(),
                           (AbstractWeather)ResourceArgument.getResource(commandContext, "weather", ESWeathers.REGISTRY_KEY).value(),
                           -1
                        )
                     ))
                  .then(
                     Commands.argument("duration", TimeArgument.time(1))
                        .executes(
                           commandContext -> setWeather(
                              (CommandSourceStack)commandContext.getSource(),
                              (AbstractWeather)ResourceArgument.getResource(commandContext, "weather", ESWeathers.REGISTRY_KEY).value(),
                              IntegerArgumentType.getInteger(commandContext, "duration")
                           )
                        )
                  )
            ))
         .then(
            ((LiteralArgumentBuilder)Commands.literal("clear").executes(commandContext -> setClear((CommandSourceStack)commandContext.getSource(), -1)))
               .then(
                  Commands.argument("duration", TimeArgument.time(1))
                     .executes(
                        commandContext -> setClear((CommandSourceStack)commandContext.getSource(), IntegerArgumentType.getInteger(commandContext, "duration"))
                     )
               )
         );
   }

   private static int getDuration(CommandSourceStack commandSourceStack, int i, IntProvider intProvider) {
      return i == -1 ? intProvider.sample(commandSourceStack.getLevel().getRandom()) : i;
   }

   private static int setWeather(CommandSourceStack commandSourceStack, AbstractWeather weather, int duration) {
      int trueDuration = getDuration(commandSourceStack, duration, weather.weatherProperties().duration());
      if (trueDuration > 0 && commandSourceStack.getLevel().dimension() == ESDimensions.STARLIGHT_KEY) {
         ESWeatherUtil.getOrCreateWeathers(commandSourceStack.getLevel()).setActiveWeather(weather, trueDuration);
         commandSourceStack.sendSuccess(() -> Component.translatable("commands.eternal_starlight.weather.set", new Object[]{weather.getDescription()}), true);
      } else {
         commandSourceStack.sendFailure(Component.translatable("commands.eternal_starlight.weather.fail", new Object[]{weather.getDescription()}));
      }

      return duration;
   }

   private static int setClear(CommandSourceStack commandSourceStack, int duration) {
      int trueDuration = getDuration(commandSourceStack, duration, ServerLevel.RAIN_DELAY);
      if (trueDuration > 0 && commandSourceStack.getLevel().dimension() == ESDimensions.STARLIGHT_KEY) {
         ESWeatherUtil.getOrCreateWeathers(commandSourceStack.getLevel()).clearAllWeathers(trueDuration);
         commandSourceStack.sendSuccess(() -> Component.translatable("commands.eternal_starlight.weather.clear"), true);
      } else {
         commandSourceStack.sendFailure(Component.translatable("commands.eternal_starlight.weather.clear_fail"));
      }

      return duration;
   }
}
