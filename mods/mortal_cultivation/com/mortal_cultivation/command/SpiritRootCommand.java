package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mortal_cultivation.spiritroot.SpiritRootData;
import com.mortal_cultivation.spiritroot.SpiritRootType;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public final class SpiritRootCommand {
   private static final SpiritRootType HEAVEN_ROOT = SpiritRootType.HEAVEN;
   private static final DynamicCommandExceptionType INVALID_ROOT = new DynamicCommandExceptionType(
      input -> Component.translatable("mortal_cultivation.command.spirit_root.invalid", new Object[]{input})
   );

   private SpiritRootCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(createRootCommand("linggen"));
      dispatcher.register(createRootCommand("spiritroot"));
      dispatcher.register(createHeavenRootCommand("tianlinggen"));
      dispatcher.register(createHeavenRootCommand("heavenroot"));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> createRootCommand(String commandName) {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                        commandName
                     )
                     .requires(source -> source.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("get").executes(SpiritRootCommand::getSelf))
                        .then(Commands.argument("player", EntityArgument.player()).executes(SpiritRootCommand::getOther))
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("set")
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .then(
                                 Commands.argument("root", StringArgumentType.word())
                                    .suggests((context, builder) -> SharedSuggestionProvider.suggest(SpiritRootType.ids(), builder))
                                    .executes(SpiritRootCommand::setOther)
                              )
                        ))
                     .then(
                        Commands.argument("root", StringArgumentType.word())
                           .suggests((context, builder) -> SharedSuggestionProvider.suggest(SpiritRootType.ids(), builder))
                           .executes(SpiritRootCommand::setSelf)
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)Commands.literal("random").executes(SpiritRootCommand::randomSelf))
                  .then(Commands.argument("player", EntityArgument.player()).executes(SpiritRootCommand::randomOther))
            ))
         .then(
            ((LiteralArgumentBuilder)Commands.literal("heaven").executes(SpiritRootCommand::setHeavenSelf))
               .then(Commands.argument("player", EntityArgument.player()).executes(SpiritRootCommand::setHeavenOther))
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> createHeavenRootCommand(String commandName) {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(commandName)
               .requires(source -> source.hasPermission(2)))
            .executes(SpiritRootCommand::setHeavenSelf))
         .then(Commands.argument("player", EntityArgument.player()).executes(SpiritRootCommand::setHeavenOther));
   }

   private static int getSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      return sendRootInfo((CommandSourceStack)context.getSource(), player, false);
   }

   private static int getOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(context, "player");
      return sendRootInfo((CommandSourceStack)context.getSource(), target, true);
   }

   private static int sendRootInfo(CommandSourceStack source, ServerPlayer target, boolean includeName) {
      return SpiritRootData.getPrimaryRoot(target)
         .map(
            root -> {
               Component message = includeName
                  ? Component.translatable(
                     "mortal_cultivation.command.spirit_root.get.other", new Object[]{target.getName(), SpiritRootData.createRootSummary(root)}
                  )
                  : Component.translatable("mortal_cultivation.command.spirit_root.get.self", new Object[]{SpiritRootData.createRootSummary(root)});
               source.sendSuccess(() -> message, false);
               return 1;
            }
         )
         .orElseGet(
            () -> {
               Component message = includeName
                  ? Component.translatable("mortal_cultivation.command.spirit_root.get.none_other", new Object[]{target.getName()})
                  : Component.translatable("mortal_cultivation.command.spirit_root.get.none_self");
               source.sendFailure(message);
               return 0;
            }
         );
   }

   private static int setSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      SpiritRootType root = parseRoot(context);
      SpiritRootData.setRoot(player, root);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(() -> Component.translatable("mortal_cultivation.command.spirit_root.set.success_self", new Object[]{root.getDisplayName()}), false);
      return 1;
   }

   private static int setOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(context, "player");
      SpiritRootType root = parseRoot(context);
      SpiritRootData.setRoot(target, root);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.spirit_root.set.success_other", new Object[]{target.getName(), root.getDisplayName()}),
            true
         );
      target.sendSystemMessage(Component.translatable("mortal_cultivation.command.spirit_root.notification", new Object[]{root.getDisplayName()}));
      return 1;
   }

   private static int randomSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      SpiritRootType root = SpiritRootType.randomCommonRoot(player.getRandom());
      SpiritRootData.setRoot(player, root);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(() -> Component.translatable("mortal_cultivation.command.spirit_root.random.success_self", new Object[]{root.getDisplayName()}), false);
      return 1;
   }

   private static int randomOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(context, "player");
      SpiritRootType root = SpiritRootType.randomCommonRoot(target.getRandom());
      SpiritRootData.setRoot(target, root);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.spirit_root.random.success_other", new Object[]{target.getName(), root.getDisplayName()}),
            true
         );
      target.sendSystemMessage(Component.translatable("mortal_cultivation.command.spirit_root.notification", new Object[]{root.getDisplayName()}));
      return 1;
   }

   private static int setHeavenSelf(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)context.getSource()).getPlayerOrException();
      SpiritRootData.setRoot(player, HEAVEN_ROOT);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(
            () -> Component.translatable("mortal_cultivation.command.spirit_root.heaven.success_self", new Object[]{HEAVEN_ROOT.getDisplayName()}), false
         );
      return 1;
   }

   private static int setHeavenOther(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(context, "player");
      SpiritRootData.setRoot(target, HEAVEN_ROOT);
      ((CommandSourceStack)context.getSource())
         .sendSuccess(
            () -> Component.translatable(
               "mortal_cultivation.command.spirit_root.heaven.success_other", new Object[]{target.getName(), HEAVEN_ROOT.getDisplayName()}
            ),
            true
         );
      target.sendSystemMessage(Component.translatable("mortal_cultivation.command.spirit_root.heaven.notification", new Object[]{HEAVEN_ROOT.getDisplayName()}));
      return 1;
   }

   private static SpiritRootType parseRoot(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      String rootId = StringArgumentType.getString(context, "root");
      return SpiritRootType.byId(rootId).orElseThrow(() -> INVALID_ROOT.create(rootId));
   }
}
