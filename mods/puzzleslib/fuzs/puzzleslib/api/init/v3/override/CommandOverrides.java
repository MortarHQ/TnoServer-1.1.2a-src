package fuzs.puzzleslib.api.init.v3.override;

import com.google.common.collect.Maps;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.entity.ServerEntityLevelEvents;
import fuzs.puzzleslib.api.event.v1.server.ServerLifecycleEvents;
import fuzs.puzzleslib.impl.PuzzlesLibMod;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.function.UnaryOperator;
import net.minecraft.core.Holder;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import org.jetbrains.annotations.ApiStatus.Internal;

public final class CommandOverrides {
   private static final String KEY_PLAYER_SEEN_WORLD = PuzzlesLibMod.id("has_seen_world").toLanguageKey();
   private static final Map<CommandOverrides.CommandEnvironment, Collection<String>> COMMAND_OVERRIDES = Maps.newEnumMap(
      CommandOverrides.CommandEnvironment.class
   );

   private CommandOverrides() {
   }

   public static void registerServerCommand(String command, boolean onlyDedicated) {
      CommandOverrides.CommandEnvironment commandEnvironment = onlyDedicated
         ? CommandOverrides.CommandEnvironment.DEDICATED_SERVER
         : CommandOverrides.CommandEnvironment.SERVER;
      COMMAND_OVERRIDES.computeIfAbsent(commandEnvironment, $ -> new LinkedHashSet<>()).add(command);
   }

   public static void registerEffectCommand(Holder<MobEffect> holder) {
      registerPlayerCommand("effect give @s " + holder.getRegisteredName() + " infinite 127 true", false);
   }

   public static void registerPlayerCommand(String command, boolean onlyDedicated) {
      CommandOverrides.CommandEnvironment commandEnvironment = onlyDedicated
         ? CommandOverrides.CommandEnvironment.DEDICATED_PLAYER
         : CommandOverrides.CommandEnvironment.PLAYER;
      COMMAND_OVERRIDES.computeIfAbsent(commandEnvironment, $ -> new LinkedHashSet<>()).add(command);
   }

   @Internal
   public static void registerHandlers() {
      ServerLifecycleEvents.STARTED
         .register(
            server -> {
               if (server.overworld().getGameTime() == 0L) {
                  executeCommandOverrides(
                     server, CommandOverrides.CommandEnvironment.SERVER, CommandOverrides.CommandEnvironment.DEDICATED_SERVER, UnaryOperator.identity()
                  );
               }
            }
         );
      ServerEntityLevelEvents.LOAD
         .register(
            (entity, level) -> {
               if (entity instanceof ServerPlayer serverPlayer && !serverPlayer.getTags().contains(KEY_PLAYER_SEEN_WORLD)) {
                  serverPlayer.addTag(KEY_PLAYER_SEEN_WORLD);
                  String playerName = serverPlayer.getGameProfile().getName();
                  executeCommandOverrides(
                     serverPlayer.server,
                     CommandOverrides.CommandEnvironment.PLAYER,
                     CommandOverrides.CommandEnvironment.DEDICATED_PLAYER,
                     s -> s.replaceAll("@[sp]", playerName)
                  );
               }

               return EventResult.PASS;
            }
         );
   }

   private static void executeCommandOverrides(
      MinecraftServer server,
      CommandOverrides.CommandEnvironment commandEnvironment,
      CommandOverrides.CommandEnvironment dedicatedCommandEnvironment,
      UnaryOperator<String> formatter
   ) {
      for (String command : COMMAND_OVERRIDES.getOrDefault(commandEnvironment, Collections.emptySet())) {
         server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), formatter.apply(command));
      }

      if (server instanceof DedicatedServer) {
         for (String command : COMMAND_OVERRIDES.getOrDefault(dedicatedCommandEnvironment, Collections.emptySet())) {
            server.getCommands().performPrefixedCommand(server.createCommandSourceStack(), formatter.apply(command));
         }
      }
   }

   private enum CommandEnvironment {
      DEDICATED_SERVER,
      SERVER,
      DEDICATED_PLAYER,
      PLAYER;
   }
}
