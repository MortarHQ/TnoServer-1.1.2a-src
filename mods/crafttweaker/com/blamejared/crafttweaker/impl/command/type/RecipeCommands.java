package com.blamejared.crafttweaker.impl.command.type;

import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.command.CommandUtilities;
import com.blamejared.crafttweaker.api.command.argument.RecipeTypeArgument;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.handler.IRecipeHandlerRegistry;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.util.ItemStackUtil;
import com.blamejared.crafttweaker.impl.helper.AccessibleElementsProvider;
import com.blamejared.crafttweaker.mixin.common.access.recipe.AccessRecipeManager;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeType;

public final class RecipeCommands {
   private RecipeCommands() {
   }

   public static void registerCommands(ICommandRegistrationHandler handler) {
      handler.registerRootCommand(
         "recipes",
         Component.translatable("crafttweaker.command.description.recipes"),
         builder -> builder.executes(context -> dumpRecipes((CommandSourceStack)context.getSource()))
      );
      handler.registerSubCommand(
         "recipes", "hand", Component.translatable("crafttweaker.command.description.recipes.hand"), builder -> builder.executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            ServerPlayer player = source.getPlayerOrException();
            return dumpHand(source, player.getMainHandItem());
         })
      );
      handler.registerSubCommand(
         "recipes",
         "inventory",
         Component.translatable("crafttweaker.command.description.recipes.inventory"),
         builder -> builder.executes(
            context -> {
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               ServerPlayer player = source.getPlayerOrException();
               List<ItemStack> stacks = new ArrayList<>();
               IntStream.range(0, player.getInventory().getContainerSize())
                  .<ItemStack>mapToObj(player.getInventory()::getItem)
                  .filter(itemStack -> !itemStack.isEmpty())
                  .forEach(itemStack -> {
                     if (stacks.stream().noneMatch(stack -> ItemStack.isSameItem(itemStack, stack))) {
                        stacks.add(itemStack);
                     }
                  });
               return dump(source, stacks);
            }
         )
      );
      handler.registerSubCommand(
         "recipes",
         "manager",
         Component.translatable("crafttweaker.command.description.recipes.manager"),
         builder -> builder.then(Commands.argument("type", RecipeTypeArgument.get()).executes(context -> {
            CommandSourceStack source = (CommandSourceStack)context.getSource();
            return dumpRecipes(source, (IRecipeManager<?>)context.getArgument("type", IRecipeManager.class));
         }))
      );
   }

   private static int dumpRecipes(CommandSourceStack source) {
      CommandUtilities.COMMAND_LOGGER.info("Dumping all recipes!");
      dumpRecipes(source, it -> true);
      CommandUtilities.openLogFile(
         source,
         Component.translatable(
               "crafttweaker.command.list.check.log",
               new Object[]{
                  CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile()
               }
            )
            .withStyle(ChatFormatting.GREEN)
      );
      return 1;
   }

   private static int dumpRecipes(CommandSourceStack source, IRecipeManager<?> manager) {
      CommandUtilities.COMMAND_LOGGER.info("Dumping recipes for manager " + manager.getCommandString() + "!");
      RecipeType<?> type = manager.getRecipeType();
      dumpRecipes(source, it -> Objects.equals(it, type));
      CommandUtilities.openLogFile(
         source,
         Component.translatable(
               "crafttweaker.command.list.check.log",
               new Object[]{
                  CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile()
               }
            )
            .withStyle(ChatFormatting.GREEN)
      );
      return 1;
   }

   private static int dumpHand(CommandSourceStack source, ItemStack stack) {
      return dump(source, List.of(stack));
   }

   private static int dump(CommandSourceStack source, List<ItemStack> stacks) {
      for (ItemStack stack : stacks) {
         if (stack.isEmpty()) {
            CommandUtilities.send(source, Component.translatable("crafttweaker.command.recipes.hand.empty").withStyle(ChatFormatting.RED));
            return 1;
         }

         IItemStack workingStack = IItemStack.of(stack.copy()).withAmount(1);
         CommandUtilities.COMMAND_LOGGER.info("Dumping all recipes that output {}!", ItemStackUtil.getCommandString(workingStack.getInternal()));
         ((AccessRecipeManager)source.getLevel().getRecipeManager())
            .crafttweaker$getByType()
            .asMap()
            .forEach(
               (recipeType, recipes) -> dumpRecipe(
                  (RecipeType<?>)recipeType,
                  (Collection<RecipeHolder<?>>)recipes,
                  it -> workingStack.matches(IItemStack.of(AccessibleElementsProvider.get().registryAccess(it.value()::getResultItem))),
                  true
               )
            );
      }

      CommandUtilities.openLogFile(
         source,
         Component.translatable(
               "crafttweaker.command.list.check.log",
               new Object[]{
                  CommandUtilities.makeNoticeable(Component.translatable("crafttweaker.command.misc.recipes.list")), CommandUtilities.getFormattedLogFile()
               }
            )
            .withStyle(ChatFormatting.GREEN)
      );
      return 1;
   }

   private static void dumpRecipes(CommandSourceStack source, Predicate<RecipeType<?>> typeFilter) {
      ((AccessRecipeManager)source.getLevel().getRecipeManager())
         .crafttweaker$getByType()
         .asMap()
         .entrySet()
         .stream()
         .filter(it -> typeFilter.test((RecipeType<?>)it.getKey()))
         .forEach(it -> dumpRecipe((RecipeType<?>)it.getKey(), (Collection<RecipeHolder<?>>)it.getValue(), recipe -> true, false));
   }

   private static void dumpRecipe(RecipeType<?> type, Collection<RecipeHolder<?>> recipes, Predicate<RecipeHolder<?>> filter, boolean hideEmpty) {
      IRecipeManager<?> manager = RecipeTypeBracketHandler.getOrDefault(type);
      if (manager != null) {
         String dumpResult = recipes.stream()
            .filter(filter)
            .sorted(Comparator.comparing(RecipeCommands::serializer).thenComparing(RecipeHolder::id))
            .map(it -> dump(GenericUtil.uncheck(manager), it))
            .collect(Collectors.joining("\n  "));
         if (!hideEmpty || !dumpResult.isEmpty()) {
            CommandUtilities.COMMAND_LOGGER
               .info("Recipe type: '{}'\n  {}\n", manager.getCommandString(), dumpResult.isEmpty() ? "No recipe found" : dumpResult);
         }
      }
   }

   private static ResourceLocation serializer(RecipeHolder<?> recipe) {
      return Objects.requireNonNull(BuiltInRegistries.RECIPE_SERIALIZER.getKey(recipe.value().getSerializer()));
   }

   private static <T extends Recipe<?>> String dump(IRecipeManager<? super T> manager, RecipeHolder<T> recipe) {
      return IRecipeHandlerRegistry.<T>getHandlerFor(recipe).dumpToCommandString(manager, AccessibleElementsProvider.get().registryAccess(), recipe);
   }
}
