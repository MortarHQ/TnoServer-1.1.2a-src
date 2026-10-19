package com.blamejared.crafttweaker.impl.command.type.conflict;

import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Spliterators;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
import java.util.function.ToIntBiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public final class ConflictCommand {
   private static final ExecutorService OFF_THREAD_SERVICE = Executors.newFixedThreadPool(1, r -> {
      Thread t = new Thread(r, "crafttweaker:conflict_resolution_thread");
      t.setDaemon(true);
      t.setContextClassLoader(ConflictCommand.class.getClassLoader());
      return t;
   });

   private ConflictCommand() {
   }

   public static void registerCommands(ICommandRegistrationHandler handler) {
      handler.registerRootCommand(
         "conflicts",
         Component.translatable("crafttweaker.command.description.conflicts"),
         builder -> ((LiteralArgumentBuilder)((LiteralArgumentBuilder)builder.then(
                  Commands.argument("type", RecipeTypeArgument.get())
                     .executes(
                        context -> conflicts(
                           (CommandSourceStack)context.getSource(), DescriptiveFilter.of((IRecipeManager<?>)context.getArgument("type", IRecipeManager.class))
                        )
                     )
               ))
               .then(
                  Commands.literal("hand")
                     .executes(context -> ifNotEmpty(context, (player, item) -> conflicts((CommandSourceStack)context.getSource(), DescriptiveFilter.of(item))))
               ))
            .executes(context -> conflicts((CommandSourceStack)context.getSource(), DescriptiveFilter.of()))
      );
   }

   private static int ifNotEmpty(CommandContext<CommandSourceStack> context, ToIntBiFunction<Player, ItemStack> command) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer player = source.getPlayerOrException();
      ItemStack stack = player.getMainHandItem();
      if (stack.isEmpty()) {
         CommandUtilities.send(source, Component.translatable("crafttweaker.command.conflict.hand.empty").withStyle(ChatFormatting.RED));
         return -1;
      } else {
         return command.applyAsInt(player, stack);
      }
   }

   private static int conflicts(CommandSourceStack source, DescriptiveFilter filter) {
      CommandUtilities.send(
         source,
         Component.translatable("crafttweaker.command.conflict.begin", new Object[]{filter.description()})
            .withStyle(ChatFormatting.GREEN)
            .append(Component.translatable("crafttweaker.command.conflict.warnings").withStyle(ChatFormatting.RED))
      );
      runConflicts(source, source.getLevel().getRecipeManager(), filter);
      return 0;
   }

   private static void runConflicts(CommandSourceStack source, RecipeManager manager, DescriptiveFilter filter) {
      Multimap<RecipeType<?>, RecipeHolder<?>> recipes = copyAndFilter(((AccessRecipeManager)manager).crafttweaker$getByType(), filter);
      CompletableFuture.<String>supplyAsync(() -> computeConflicts(recipes), OFF_THREAD_SERVICE)
         .thenAcceptAsync(message -> dispatchCompletionTo(message, source), OFF_THREAD_SERVICE)
         .exceptionallyAsync(exception -> dispatchExceptionTo(exception, source), OFF_THREAD_SERVICE);
   }

   private static Multimap<RecipeType<?>, RecipeHolder<?>> copyAndFilter(Multimap<RecipeType<?>, RecipeHolder<?>> original, DescriptiveFilter filter) {
      Multimap<RecipeType<?>, RecipeHolder<?>> build = MultimapBuilder.hashKeys().arrayListValues().build();
      Multimap<RecipeType<?>, RecipeHolder<?>> clone = HashMultimap.create();
      original.asMap().forEach((type, holders) -> build.putAll(type, holders.stream().filter(filter).toList()));
      return clone;
   }

   private static String computeConflicts(Multimap<RecipeType<?>, RecipeHolder<?>> recipes) {
      return recipes.asMap().entrySet().stream().flatMap(ConflictCommand::computeConflictsFor).map(it -> "- " + it).collect(Collectors.joining("\n"));
   }

   private static Stream<String> computeConflictsFor(Entry<RecipeType<?>, Collection<RecipeHolder<?>>> entry) {
      IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(entry.getKey());
      if (manager == null) {
         return Stream.empty();
      }

      List<RecipeHolder<?>> recipes = new ArrayList<>(entry.getValue());
      RecipeLongIterator iterator = new RecipeLongIterator(recipes.size());
      int characteristics = 1300;
      return StreamSupport.longStream(Spliterators.spliterator(iterator, iterator.estimateLength(), 1300), false)
         .filter(it -> conflictsWith(manager, recipes, it))
         .mapToObj(it -> Map.entry(recipes.get(RecipeLongIterator.first(it)), recipes.get(RecipeLongIterator.second(it))))
         .map(it -> formatConflict(manager, (Entry<RecipeHolder<?>, RecipeHolder<?>>)it));
   }

   private static boolean conflictsWith(IRecipeManager<?> manager, List<RecipeHolder<?>> recipes, long id) {
      return conflictsWith(manager, recipes.get(RecipeLongIterator.first(id)), recipes.get(RecipeLongIterator.second(id)));
   }

   private static <T extends Recipe<?>> boolean conflictsWith(IRecipeManager<?> manager, RecipeHolder<T> first, RecipeHolder<?> second) {
      return first != second
         && IRecipeHandlerRegistry.getHandlerFor((T)first.value()).doesConflict(GenericUtil.uncheck(manager), (T)first.value(), second.value());
   }

   private static String formatConflict(IRecipeManager<?> manager, Entry<RecipeHolder<?>, RecipeHolder<?>> conflictNames) {
      ResourceLocation firstName = conflictNames.getKey().id();
      ResourceLocation secondName = conflictNames.getValue().id();
      return String.format("Recipes '%s' and '%s' in type '%s' have conflicting inputs", firstName, secondName, manager.getCommandString());
   }

   private static void dispatchCompletionTo(String message, CommandSourceStack source) {
      onMainThread(source, () -> {
         try {
            CommandUtilities.COMMAND_LOGGER.info(message.isEmpty() ? "No conflicts identified" : message);
            CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.conflict.complete").withStyle(ChatFormatting.GREEN));
         } catch (Exception var5) {
            Exception e = var5;

            try {
               CommandUtilities.COMMAND_LOGGER.error("An error occurred while reporting conflicts, hopefully it does not happen again", e);
            } catch (Exception another) {
               var5.addSuppressed(another);
               var5.printStackTrace(System.err);
            }
         }
      });
   }

   private static Void dispatchExceptionTo(Throwable exception, CommandSourceStack source) {
      onMainThread(source, () -> {
         try {
            CommandUtilities.COMMAND_LOGGER.error("Unable to verify for conflicts due to an exception", exception);
            CommandUtilities.openLogFile(source, Component.translatable("crafttweaker.command.conflict.error").withStyle(ChatFormatting.RED));
         } catch (Exception var5) {
            Exception e = var5;

            try {
               CommandUtilities.COMMAND_LOGGER.error("An error occurred while reporting conflicts, hopefully it does not happen again", e);
            } catch (Exception another) {
               var5.addSuppressed(another);
               var5.printStackTrace(System.err);
            }
         }
      });
      return null;
   }

   private static void onMainThread(CommandSourceStack source, Runnable runnable) {
      Level level = source.getLevel();
      assert !level.isClientSide();
      Objects.requireNonNull(level.getServer(), "Is someone running server-bound commands on the client?").executeIfPossible(() -> {
         try {
            runnable.run();
         } catch (RejectedExecutionException e) {
            OFF_THREAD_SERVICE.submit(runnable);
         }
      });
   }
}
