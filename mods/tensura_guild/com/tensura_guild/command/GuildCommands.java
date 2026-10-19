package com.tensura_guild.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.tensura_guild.GuildPlayerEvents;
import com.tensura_guild.compat.GuildCompatHooks;
import com.tensura_guild.data.DwarfTaskService;
import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.data.GuildMemberService;
import com.tensura_guild.data.GuildReputationShopService;
import com.tensura_guild.data.GuildTaskBoardData;
import com.tensura_guild.data.GuildTaskDefinitions;
import com.tensura_guild.data.GuildTaskService;
import com.tensura_guild.data.GuildTitleData;
import com.tensura_guild.data.GuildTitleFormatter;
import com.tensura_guild.data.GuildWorldData;
import com.tensura_guild.network.GuildShopRefreshPayload;
import com.tensura_guild.registry.GuildAttachments;
import io.github.manasmods.tensura.entity.human.DwarfEntity;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

public class GuildCommands {
   public static final ResourceLocation YUUKI_INTRO = ResourceLocation.fromNamespaceAndPath("tensura_guild", "yuuki_intro");
   private static final SimpleCommandExceptionType UNKNOWN_TASK = new SimpleCommandExceptionType(Component.translatable("command.tensura_guild.task.unknown"));
   private static final SimpleCommandExceptionType UNKNOWN_DWARF_TASK = new SimpleCommandExceptionType(
      Component.translatable("command.tensura_guild.dwarf_task.unknown")
   );
   private static final SimpleCommandExceptionType NO_DWARF_TARGET = new SimpleCommandExceptionType(
      Component.translatable("command.tensura_guild.dwarf_task.no_target")
   );
   private static final double DWARF_TASK_COMMAND_RANGE = 16.0;

   public static void onRegister(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("tensuraguild")
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("guild")
                                          .then(
                                             ((LiteralArgumentBuilder)Commands.literal("display")
                                                   .then(Commands.literal("enable").executes(ctx -> setGuildPrefixDisplay(ctx, true))))
                                                .then(Commands.literal("disable").executes(ctx -> setGuildPrefixDisplay(ctx, false)))
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)Commands.literal("title").requires(source -> source.hasPermission(2)))
                                             .then(
                                                Commands.literal("set")
                                                   .then(
                                                      Commands.argument("target", EntityArgument.player())
                                                         .then(
                                                            Commands.argument("title", StringArgumentType.greedyString())
                                                               .executes(GuildCommands::setGuildTitle)
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)Commands.literal("experience").requires(source -> source.hasPermission(2)))
                                          .then(
                                             Commands.literal("add")
                                                .then(
                                                   Commands.argument("targets", EntityArgument.players())
                                                      .then(
                                                         Commands.argument("amount", IntegerArgumentType.integer(1))
                                                            .executes(GuildCommands::addGuildExperience)
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("reputation_coin").requires(source -> source.hasPermission(2)))
                                       .then(
                                          Commands.literal("add")
                                             .then(
                                                Commands.argument("targets", EntityArgument.players())
                                                   .then(
                                                      Commands.argument("amount", IntegerArgumentType.integer(1)).executes(GuildCommands::addReputationCoins)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("shop").requires(source -> source.hasPermission(2)))
                                    .then(Commands.literal("refresh").executes(GuildCommands::refreshReputationShop))
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("dialogue").requires(source -> source.hasPermission(2)))
                           .then(Commands.literal("reset").then(Commands.argument("targets", EntityArgument.players()).executes(GuildCommands::resetDialogue)))
                     ))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("task")
                                 .requires(source -> source.hasPermission(2)))
                              .then(
                                 Commands.literal("add")
                                    .then(
                                       Commands.argument("task_id", ResourceLocationArgument.id())
                                          .suggests(
                                             (ctx, builder) -> SharedSuggestionProvider.suggestResource(GuildTaskDefinitions.availableTaskIds(), builder)
                                          )
                                          .executes(GuildCommands::addTask)
                                    )
                              ))
                           .then(Commands.literal("refresh").executes(GuildCommands::refreshTasks)))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("debug")
                                 .then(
                                    Commands.literal("add")
                                       .then(
                                          ((RequiredArgumentBuilder)Commands.argument("targets", EntityArgument.players())
                                                .executes(ctx -> addDebugTaskPaper(ctx, 1)))
                                             .then(
                                                Commands.argument("amount", IntegerArgumentType.integer(1))
                                                   .executes(ctx -> addDebugTaskPaper(ctx, IntegerArgumentType.getInteger(ctx, "amount")))
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.literal("clear")
                                    .then(Commands.argument("targets", EntityArgument.players()).executes(GuildCommands::clearDebugTaskPapers))
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("dwarf").requires(source -> source.hasPermission(2)))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("task")
                              .then(
                                 Commands.literal("set")
                                    .then(
                                       Commands.argument("task_id", ResourceLocationArgument.id())
                                          .suggests(
                                             (ctx, builder) -> SharedSuggestionProvider.suggestResource(DwarfTaskService.availableDwarfTaskIds(), builder)
                                          )
                                          .executes(GuildCommands::setDwarfTask)
                                    )
                              ))
                           .then(Commands.literal("clear").executes(GuildCommands::clearDwarfTask))
                     )
               )
         );
   }

   private static int setGuildPrefixDisplay(CommandContext<CommandSourceStack> ctx, boolean visible) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      GuildMemberData updated = ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).withGuildPrefixVisible(visible);
      GuildMemberService.setMemberData(player, updated);
      GuildPlayerEvents.refreshNames(player);
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(
            () -> Component.translatable(visible ? "command.tensura_guild.guild.display.enabled" : "command.tensura_guild.guild.display.disabled"), false
         );
      return 1;
   }

   private static int addGuildExperience(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");
      int amount = IntegerArgumentType.getInteger(ctx, "amount");

      for (ServerPlayer player : targets) {
         GuildLevelData levelData = (GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL);
         player.setData(GuildAttachments.GUILD_LEVEL, levelData.addExperience(amount));
         player.syncData(GuildAttachments.GUILD_LEVEL);
         GuildTaskService.syncToPlayer(player);
         GuildCompatHooks.onAdventureExperienceChanged(player);
      }

      int count = targets.size();
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.guild.experience.add", new Object[]{count, amount}), true);
      return count;
   }

   private static int addReputationCoins(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");
      int amount = IntegerArgumentType.getInteger(ctx, "amount");

      for (ServerPlayer player : targets) {
         GuildReputationShopService.addCoins(player, amount);
      }

      int count = targets.size();
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.guild.reputation_coin.add", new Object[]{count, amount}), true);
      return count;
   }

   private static int refreshReputationShop(CommandContext<CommandSourceStack> ctx) {
      long refreshNonce = GuildWorldData.get(((CommandSourceStack)ctx.getSource()).getServer()).refreshReputationShop();
      GuildShopRefreshPayload payload = new GuildShopRefreshPayload();

      for (ServerPlayer player : ((CommandSourceStack)ctx.getSource()).getServer().getPlayerList().getPlayers()) {
         PacketDistributor.sendToPlayer(player, payload, new CustomPacketPayload[0]);
      }

      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.guild.shop.refresh", new Object[]{refreshNonce}), true);
      return 1;
   }

   private static int setGuildTitle(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer target = EntityArgument.getPlayer(ctx, "target");
      String title = GuildTitleData.normalizeTitle(StringArgumentType.getString(ctx, "title")).orElse("");
      GuildMemberData updated = ((GuildMemberData)target.getData(GuildAttachments.GUILD_MEMBER)).withTitle(title);
      GuildMemberService.setMemberData(target, updated);
      GuildPlayerEvents.refreshNames(target);
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(
            () -> Component.translatable("command.tensura_guild.guild.title.set", new Object[]{target.getName(), GuildTitleFormatter.format(title)}), true
         );
      return 1;
   }

   private static int resetDialogue(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

      for (ServerPlayer player : targets) {
         Set<ResourceLocation> seen = new HashSet<>((Collection<? extends ResourceLocation>)player.getData(GuildAttachments.SEEN_DIALOGUES));
         if (seen.remove(YUUKI_INTRO)) {
            player.setData(GuildAttachments.SEEN_DIALOGUES, seen);
         }
      }

      int count = targets.size();
      ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("command.tensura_guild.dialogue.reset", new Object[]{count}), true);
      return count;
   }

   private static int addDebugTaskPaper(CommandContext<CommandSourceStack> ctx, int amount) throws CommandSyntaxException {
      Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

      for (ServerPlayer player : targets) {
         GuildTaskBoardData updated = ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD)).addDebugTasks(amount);
         player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
         player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      }

      int count = targets.size();
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.task.debug.add", new Object[]{count, amount}), true);
      return count;
   }

   private static int clearDebugTaskPapers(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      Collection<ServerPlayer> targets = EntityArgument.getPlayers(ctx, "targets");

      for (ServerPlayer player : targets) {
         GuildTaskBoardData updated = ((GuildTaskBoardData)player.getData(GuildAttachments.GUILD_TASK_BOARD)).clearDebugTasks();
         player.setData(GuildAttachments.GUILD_TASK_BOARD, updated);
         player.syncData(GuildAttachments.GUILD_TASK_BOARD);
      }

      int count = targets.size();
      ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("command.tensura_guild.task.debug.clear", new Object[]{count}), true);
      return count;
   }

   private static int addTask(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ResourceLocation taskId = ResourceLocationArgument.getId(ctx, "task_id");
      if (!GuildTaskDefinitions.contains(taskId)) {
         throw UNKNOWN_TASK.create();
      }

      boolean changed = GuildTaskService.addTask(((CommandSourceStack)ctx.getSource()).getServer(), taskId);
      ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("command.tensura_guild.task.add", new Object[]{taskId.toString()}), true);
      return changed ? 1 : 0;
   }

   private static int refreshTasks(CommandContext<CommandSourceStack> ctx) {
      int count = GuildTaskService.refreshTasks(((CommandSourceStack)ctx.getSource()).getServer());
      ((CommandSourceStack)ctx.getSource()).sendSuccess(() -> Component.translatable("command.tensura_guild.task.refresh", new Object[]{count}), true);
      return count;
   }

   private static int setDwarfTask(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ResourceLocation taskId = ResourceLocationArgument.getId(ctx, "task_id");
      if (!DwarfTaskService.isKnownDwarfTask(taskId)) {
         throw UNKNOWN_DWARF_TASK.create();
      }

      ServerPlayer player = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      DwarfEntity dwarf = getLookedAtDwarf(player).orElseThrow(NO_DWARF_TARGET::create);
      DwarfTaskService.assignTaskOffer(dwarf, taskId);
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.dwarf_task.set", new Object[]{dwarf.getName(), taskId.toString()}), true);
      return 1;
   }

   private static int clearDwarfTask(CommandContext<CommandSourceStack> ctx) throws CommandSyntaxException {
      ServerPlayer player = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      DwarfEntity dwarf = getLookedAtDwarf(player).orElseThrow(NO_DWARF_TARGET::create);
      DwarfTaskService.clearTaskOffer(dwarf);
      ((CommandSourceStack)ctx.getSource())
         .sendSuccess(() -> Component.translatable("command.tensura_guild.dwarf_task.clear", new Object[]{dwarf.getName()}), true);
      return 1;
   }

   private static Optional<DwarfEntity> getLookedAtDwarf(ServerPlayer player) {
      Vec3 start = player.getEyePosition();
      Vec3 view = player.getViewVector(1.0F);
      Vec3 end = start.add(view.scale(16.0));
      HitResult blockHit = player.pick(16.0, 1.0F, false);
      double maxDistanceSqr = blockHit.getType() == Type.MISS ? 256.0 : blockHit.getLocation().distanceToSqr(start);
      AABB area = player.getBoundingBox().expandTowards(view.scale(16.0)).inflate(1.0, 1.0, 1.0);
      EntityHitResult hit = ProjectileUtil.getEntityHitResult(
         player, start, end, area, entity -> entity instanceof DwarfEntity && !entity.isSpectator() && entity.isPickable(), maxDistanceSqr
      );
      return (hit == null ? null : hit.getEntity()) instanceof DwarfEntity dwarf ? Optional.of(dwarf) : Optional.empty();
   }
}
