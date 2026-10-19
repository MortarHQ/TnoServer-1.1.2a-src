package org.popcraft.chunky;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.DimensionArgument;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerBossEvent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.popcraft.chunky.command.ChunkyCommand;
import org.popcraft.chunky.command.CommandArguments;
import org.popcraft.chunky.command.suggestion.SuggestionProviders;
import org.popcraft.chunky.event.task.GenerationTaskFinishEvent;
import org.popcraft.chunky.event.task.GenerationTaskUpdateEvent;
import org.popcraft.chunky.listeners.bossbar.BossBarTaskFinishListener;
import org.popcraft.chunky.listeners.bossbar.BossBarTaskUpdateListener;
import org.popcraft.chunky.platform.NeoForgePlayer;
import org.popcraft.chunky.platform.NeoForgeSender;
import org.popcraft.chunky.platform.NeoForgeServer;
import org.popcraft.chunky.platform.Sender;
import org.popcraft.chunky.platform.impl.GsonConfig;

@Mod("chunky")
public class ChunkyNeoForge {
   public static final String MOD_ID = "chunky";
   private Chunky chunky;
   private final Map<ResourceLocation, ServerBossEvent> bossBars = new ConcurrentHashMap<>();

   public ChunkyNeoForge() {
      NeoForge.EVENT_BUS.register(this);
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
      MinecraftServer server = event.getServer();
      Path configPath = FMLPaths.CONFIGDIR.get().resolve("chunky/config.json");
      this.chunky = new Chunky(new NeoForgeServer(this, server), new GsonConfig(configPath));
      if (this.chunky.getConfig().getContinueOnRestart()) {
         this.chunky.getCommands().get("continue").execute(this.chunky.getServer().getConsole(), CommandArguments.empty());
      }

      this.chunky.getEventBus().subscribe(GenerationTaskUpdateEvent.class, new BossBarTaskUpdateListener(this.bossBars));
      this.chunky.getEventBus().subscribe(GenerationTaskFinishEvent.class, new BossBarTaskFinishListener(this.bossBars));
   }

   @SubscribeEvent
   public void onRegisterCommands(RegisterCommandsEvent event) {
      LiteralArgumentBuilder<CommandSourceStack> command = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("chunky")
            .requires(serverCommandSource -> {
               MinecraftServer server = serverCommandSource.getServer();
               return server != null && server.isSingleplayer() ? true : serverCommandSource.hasPermission(2);
            }))
         .executes(context -> {
            Sender sender;
            if (((CommandSourceStack)context.getSource()).getEntity() instanceof ServerPlayer player) {
               sender = new NeoForgePlayer(player);
            } else {
               sender = new NeoForgeSender((CommandSourceStack)context.getSource());
            }

            Map<String, ChunkyCommand> commands = this.chunky.getCommands();
            String input = context.getInput().substring(((ParsedCommandNode)context.getLastChild().getNodes().get(0)).getRange().getStart());
            String[] tokens = input.split(" ");
            String subCommand = tokens.length > 1 && commands.containsKey(tokens[1]) ? tokens[1] : "help";
            CommandArguments arguments = tokens.length > 2 ? CommandArguments.of(Arrays.copyOfRange(tokens, 2, tokens.length)) : CommandArguments.empty();
            commands.get(subCommand).execute(sender, arguments);
            return 1;
         });
      this.registerArguments(command, Commands.literal("cancel"), Commands.argument("world", DimensionArgument.dimension()));
      this.registerArguments(
         command, Commands.literal("center"), Commands.argument("x", StringArgumentType.word()), Commands.argument("z", StringArgumentType.word())
      );
      this.registerArguments(command, Commands.literal("confirm"));
      this.registerArguments(command, Commands.literal("continue"), Commands.argument("world", DimensionArgument.dimension()));
      this.registerArguments(
         command,
         Commands.literal("corners"),
         Commands.argument("x1", StringArgumentType.word()),
         Commands.argument("z1", StringArgumentType.word()),
         Commands.argument("x2", StringArgumentType.word()),
         Commands.argument("z2", StringArgumentType.word())
      );
      this.registerArguments(command, Commands.literal("help"), Commands.argument("page", IntegerArgumentType.integer()));
      this.registerArguments(
         command,
         Commands.literal("pattern"),
         Commands.argument("pattern", StringArgumentType.string()).suggests(SuggestionProviders.PATTERNS),
         Commands.argument("value", StringArgumentType.string())
      );
      this.registerArguments(command, Commands.literal("pause"), Commands.argument("world", DimensionArgument.dimension()));
      this.registerArguments(command, Commands.literal("progress"));
      this.registerArguments(command, Commands.literal("quiet"), Commands.argument("interval", IntegerArgumentType.integer()));
      this.registerArguments(
         command, Commands.literal("radius"), Commands.argument("radius", StringArgumentType.word()), Commands.argument("radius", StringArgumentType.word())
      );
      this.registerArguments(command, Commands.literal("reload"), Commands.argument("type", StringArgumentType.word()));
      this.registerArguments(command, Commands.literal("selection"));
      this.registerArguments(command, Commands.literal("shape"), Commands.argument("shape", StringArgumentType.string()).suggests(SuggestionProviders.SHAPES));
      this.registerArguments(command, Commands.literal("silent"));
      this.registerArguments(command, Commands.literal("spawn"));
      this.registerArguments(
         command,
         Commands.literal("start"),
         Commands.argument("world", DimensionArgument.dimension()),
         Commands.argument("shape", StringArgumentType.string()).suggests(SuggestionProviders.SHAPES),
         Commands.argument("centerX", StringArgumentType.word()),
         Commands.argument("centerZ", StringArgumentType.word()),
         Commands.argument("radiusX", StringArgumentType.word()),
         Commands.argument("radiusZ", StringArgumentType.word())
      );
      this.registerArguments(
         command,
         Commands.literal("trim"),
         Commands.argument("world", DimensionArgument.dimension()),
         Commands.argument("shape", StringArgumentType.string()).suggests(SuggestionProviders.SHAPES),
         Commands.argument("centerX", StringArgumentType.word()),
         Commands.argument("centerZ", StringArgumentType.word()),
         Commands.argument("radiusX", StringArgumentType.word()),
         Commands.argument("radiusZ", StringArgumentType.word()),
         Commands.argument("trimMode", StringArgumentType.string()).suggests(SuggestionProviders.TRIM_MODES),
         Commands.argument("inhabited", StringArgumentType.word())
      );
      this.registerArguments(command, Commands.literal("worldborder"));
      this.registerArguments(command, Commands.literal("world"), Commands.argument("world", DimensionArgument.dimension()));
      LiteralArgumentBuilder<CommandSourceStack> borderCommand = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("border")
            .requires(serverCommandSource -> this.chunky != null && this.chunky.getCommands().containsKey("border")))
         .executes(command.getCommand());
      this.registerArguments(
         borderCommand,
         Commands.literal("add"),
         Commands.argument("world", DimensionArgument.dimension()),
         Commands.argument("shape", StringArgumentType.string()).suggests(SuggestionProviders.SHAPES),
         Commands.argument("centerX", StringArgumentType.word()),
         Commands.argument("centerZ", StringArgumentType.word()),
         Commands.argument("radiusX", StringArgumentType.word()),
         Commands.argument("radiusZ", StringArgumentType.word())
      );
      this.registerArguments(borderCommand, Commands.literal("bypass"), Commands.argument("player", EntityArgument.player()));
      this.registerArguments(borderCommand, Commands.literal("help"));
      this.registerArguments(borderCommand, Commands.literal("list"));
      this.registerArguments(borderCommand, Commands.literal("load"), Commands.argument("world", DimensionArgument.dimension()));
      this.registerArguments(borderCommand, Commands.literal("remove"), Commands.argument("world", DimensionArgument.dimension()));
      this.registerArguments(borderCommand, Commands.literal("wrap"), Commands.argument("wrap", StringArgumentType.word()));
      this.registerArguments(command, borderCommand);
      event.getDispatcher().register(command);
   }

   @SafeVarargs
   private <S> void registerArguments(LiteralArgumentBuilder<S> command, ArgumentBuilder<S, ?>... arguments) {
      for (int i = arguments.length - 1; i > 0; i--) {
         arguments[i - 1].then(arguments[i].executes(command.getCommand()));
      }

      command.then(arguments[0].executes(command.getCommand()));
   }

   @SubscribeEvent
   public void onServerStopping(ServerStoppingEvent event) {
      if (this.chunky != null) {
         this.chunky.disable();
      }
   }

   public Chunky getChunky() {
      return this.chunky;
   }
}
