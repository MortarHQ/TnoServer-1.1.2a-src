package team.lodestar.lodestone.command.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.UuidArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.server.ServerLifecycleHooks;
import team.lodestar.lodestone.attachment.WorldEventAttachment;
import team.lodestar.lodestone.registry.common.LodestoneAttachmentTypes;
import team.lodestar.lodestone.systems.worldevent.WorldEventInstance;

public class WorldEventInstanceArgument implements ArgumentType<WorldEventInstance> {
   private static final DynamicCommandExceptionType ERROR_UNKNOWN_WORLD_EVENT_INSTANCE = new DynamicCommandExceptionType(
      object -> Component.translatable("argument.lodestone.id.invalid", new Object[]{object.toString()})
   );
   private static final Pattern ALLOWED_CHARACTERS = Pattern.compile("^([-A-Fa-f0-9]+)");

   protected WorldEventInstanceArgument() {
   }

   public static WorldEventInstanceArgument worldEventInstance() {
      return new WorldEventInstanceArgument();
   }

   public static WorldEventInstance getEventInstance(CommandContext<?> context, String name) {
      return (WorldEventInstance)context.getArgument(name, WorldEventInstance.class);
   }

   public WorldEventInstance parse(StringReader reader) throws CommandSyntaxException {
      String s = reader.readString();
      Matcher matcher = ALLOWED_CHARACTERS.matcher(s);
      AtomicReference<WorldEventInstance> eventInstance = new AtomicReference<>();
      if (matcher.find()) {
         String s1 = matcher.group(1);

         try {
            UUID uuid = UUID.fromString(s1);
            MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
            if (server == null) {
               return null;
            } else {
               Set<ResourceKey<Level>> levels = ((Registry)server.registryAccess().registry(Registries.DIMENSION).get()).registryKeySet();
               levels.forEach(
                  levelResourceKey -> {
                     if (levelResourceKey != null) {
                        Level level = server.getLevel(levelResourceKey);
                        if (level != null) {
                           WorldEventAttachment data = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
                           data.activeWorldEvents
                              .stream()
                              .filter(worldEventInstance -> worldEventInstance.uuid.equals(uuid))
                              .findFirst()
                              .ifPresent(eventInstance::set);
                        }
                     }
                  }
               );
               if (eventInstance.get() == null) {
                  throw ERROR_UNKNOWN_WORLD_EVENT_INSTANCE.create(uuid);
               } else {
                  return eventInstance.get();
               }
            }
         } catch (IllegalArgumentException illegalArgumentException) {
            throw UuidArgument.ERROR_INVALID_UUID.create();
         }
      } else {
         return eventInstance.get();
      }
   }

   @OnlyIn(Dist.CLIENT)
   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
      if (context.getSource() instanceof SharedSuggestionProvider sharedsuggestionprovider) {
         sharedsuggestionprovider.levels().forEach(levelResourceKey -> {
            if (levelResourceKey != null) {
               Level level = Minecraft.getInstance().level;
               if (level != null) {
                  WorldEventAttachment data = (WorldEventAttachment)level.getData(LodestoneAttachmentTypes.WORLD_EVENT_DATA);
                  data.activeWorldEvents.forEach(worldEventInstance -> builder.suggest(worldEventInstance.uuid.toString()));
               }
            }
         });
      }

      return builder.buildFuture();
   }
}
