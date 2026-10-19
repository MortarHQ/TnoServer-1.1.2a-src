package me.wesley1808.servercore.common.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import me.wesley1808.servercore.common.config.Config;
import me.wesley1808.servercore.common.config.data.CommandConfig;
import me.wesley1808.servercore.common.dynamic.DynamicManager;
import me.wesley1808.servercore.common.dynamic.DynamicSetting;
import me.wesley1808.servercore.common.services.Formatter;
import me.wesley1808.servercore.common.services.Permission;
import me.wesley1808.servercore.common.services.platform.PlatformHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ServerCoreCommand {
   private static final String VALUE = "value";

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      LiteralArgumentBuilder<CommandSourceStack> node = Commands.literal("servercore");
      node.then(reloadConfig());
      node.then(settings());
      if (Config.get().commands().statusCommandEnabled()) {
         node.then(Commands.literal("status").executes(ctx -> getStatus((CommandSourceStack)ctx.getSource())));
      }

      dispatcher.register(node);
      dispatcher.register((LiteralArgumentBuilder)Commands.literal("sc").redirect(node.build()));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> reloadConfig() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("reload").requires(Permission.require("command.config", 2)))
         .executes(ctx -> reload((CommandSourceStack)ctx.getSource()));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> settings() {
      LiteralArgumentBuilder<CommandSourceStack> settings = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("settings")
         .requires(Permission.require("command.settings", 2));

      for (DynamicSetting setting : DynamicSetting.values()) {
         settings.then(
            Commands.literal(setting.name().toLowerCase())
               .then(
                  Commands.argument("value", IntegerArgumentType.integer(setting.getLowerBound(), setting.getUpperBound()))
                     .executes(ctx -> modifyDynamic((CommandSourceStack)ctx.getSource(), IntegerArgumentType.getInteger(ctx, "value"), setting))
               )
         );
      }

      return settings;
   }

   private static int modifyDynamic(CommandSourceStack source, int value, DynamicSetting setting) {
      DynamicManager manager = DynamicManager.getInstance(source.getServer());
      setting.set(value, manager);
      source.sendSuccess(
         () -> Formatter.parse(
            "<c:#secondary>%s <c:#primary>has been set to <c:#secondary>%s".formatted(setting.getFormattedName(), setting.getFormattedValue()),
            source.getServer()
         ),
         false
      );
      return 1;
   }

   private static int reload(CommandSourceStack source) {
      boolean success = Config.reloadConfigs();
      if (success) {
         source.sendSuccess(() -> Component.literal("Config reloaded!").withStyle(ChatFormatting.GREEN), false);
      } else {
         source.sendFailure(Component.literal("Failed to reload config! Check the logs for more info.").withStyle(ChatFormatting.RED));
      }

      return 1;
   }

   private static int getStatus(CommandSourceStack source) {
      CommandConfig config = Config.get().commands();
      source.sendSuccess(
         () -> {
            MutableComponent component = Component.empty();
            Component title = Component.literal("ServerCore").withColor(config.tertiaryValue());
            if (source.isPlayer()) {
               Formatter.addLines(component, 14, config.primaryValue(), title);
            } else {
               component.append(title);
            }

            component.append(Formatter.parse("\n<dark_gray>» <c:#primary>Version: <c:#secondary>%s".formatted(PlatformHelper.getVersion()), source.getServer()));

            for (DynamicSetting setting : DynamicSetting.values()) {
               component.append(
                  Formatter.parse(
                     "\n<dark_gray>» <c:#primary>%s: <c:#secondary>%s".formatted(setting.getFormattedName(), setting.getFormattedValue()), source.getServer()
                  )
               );
            }

            return component;
         },
         false
      );
      return 1;
   }
}
