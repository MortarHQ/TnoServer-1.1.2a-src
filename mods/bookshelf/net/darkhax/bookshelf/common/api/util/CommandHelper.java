package net.darkhax.bookshelf.common.api.util;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Supplier;
import net.darkhax.bookshelf.common.api.commands.IEnumCommand;
import net.darkhax.bookshelf.common.api.commands.PermissionLevel;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.world.entity.Entity;

public class CommandHelper {
   public static <T extends Enum<T> & IEnumCommand> LiteralArgumentBuilder<CommandSourceStack> buildFromEnum(String parent, Class<T> enumClass) {
      LiteralArgumentBuilder<CommandSourceStack> parentNode = LiteralArgumentBuilder.literal(parent);
      parentNode.requires(getLowestLevel(enumClass));
      buildFromEnum(parentNode, enumClass);
      return parentNode;
   }

   public static <T extends Enum<T> & IEnumCommand> void buildFromEnum(ArgumentBuilder<CommandSourceStack, ?> parent, Class<T> enumClass) {
      if (!enumClass.isEnum()) {
         throw new IllegalStateException("Class '" + enumClass.getCanonicalName() + "' is not an enum!");
      }

      for (T enumEntry : (Enum[])enumClass.getEnumConstants()) {
         LiteralArgumentBuilder<CommandSourceStack> literal = LiteralArgumentBuilder.literal(enumEntry.getCommandName());
         ((LiteralArgumentBuilder)literal.requires(enumEntry.requiredPermissionLevel())).executes(enumEntry);
         parent.then(literal);
      }
   }

   public static <T extends Enum<T> & IEnumCommand> PermissionLevel getLowestLevel(Class<T> enumClass) {
      if (!enumClass.isEnum()) {
         throw new IllegalStateException("Class '" + enumClass.getCanonicalName() + "' is not an enum!");
      }

      PermissionLevel level = PermissionLevel.OWNER;

      for (T enumEntry : (Enum[])enumClass.getEnumConstants()) {
         if (enumEntry.requiredPermissionLevel().get() < level.get()) {
            level = enumEntry.requiredPermissionLevel();
         }
      }

      return level;
   }

   @Deprecated
   public static <T> boolean hasArgument(String argument, CommandContext<T> context) {
      return hasArgument(argument, context, Object.class);
   }

   public static <T, C> boolean hasArgument(String argument, CommandContext<C> context, Class<T> argType) {
      try {
         return context.getArgument(argument, argType) != null;
      } catch (Exception e) {
         return false;
      }
   }

   public static <T, C> T getArgument(String argument, CommandContext<C> context, Class<T> argType, Supplier<T> fallback) {
      return (T)(hasArgument(argument, context, argType) ? context.getArgument(argument, argType) : fallback.get());
   }

   public static Entity getEntity(String argName, CommandContext<CommandSourceStack> ctx, Supplier<Entity> fallback) throws CommandSyntaxException {
      return hasArgument(argName, ctx, EntitySelector.class) ? EntityArgument.getEntity(ctx, argName) : fallback.get();
   }

   public static Entity getEntityOrSender(String argName, CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      return hasArgument(argName, ctx, EntitySelector.class) ? EntityArgument.getEntity(ctx, argName) : ((CommandSourceStack)ctx.getSource()).getEntity();
   }

   public static boolean getBooleanArg(String argName, CommandContext<CommandSourceStack> ctx, Supplier<Boolean> fallback) {
      return hasArgument(argName, ctx, Boolean.class) ? BoolArgumentType.getBool(ctx, argName) : fallback.get();
   }

   public static boolean getBooleanArg(String argName, CommandContext<CommandSourceStack> ctx) {
      return getBooleanArg(argName, ctx, () -> false);
   }
}
