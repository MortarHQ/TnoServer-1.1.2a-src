package dev.ftb.mods.ftbquests.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import dev.architectury.networking.NetworkManager;
import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.integration.PermissionsHelper;
import dev.ftb.mods.ftbquests.net.ClearDisplayCacheMessage;
import dev.ftb.mods.ftbquests.net.ClearRepeatCooldownMessage;
import dev.ftb.mods.ftbquests.net.CreateObjectResponseMessage;
import dev.ftb.mods.ftbquests.net.OpenQuestBookMessage;
import dev.ftb.mods.ftbquests.net.SyncEditorPermissionMessage;
import dev.ftb.mods.ftbquests.net.SyncQuestsMessage;
import dev.ftb.mods.ftbquests.net.SyncTranslationMessageToClient;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObject;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.loot.RewardTable;
import dev.ftb.mods.ftbquests.quest.loot.WeightedReward;
import dev.ftb.mods.ftbquests.quest.reward.ItemReward;
import dev.ftb.mods.ftbquests.quest.task.ItemTask;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.util.InventoryUtil;
import dev.ftb.mods.ftbquests.util.ProgressChange;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.coordinates.BlockPosArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;

public class FTBQuestsCommands {
   public static final SimpleCommandExceptionType NO_FILE = new SimpleCommandExceptionType(Component.translatable("commands.ftbquests.command.error.no_file"));
   public static final DynamicCommandExceptionType NO_OBJECT = new DynamicCommandExceptionType(
      object -> Component.translatable("commands.ftbquests.command.error.no_object", new Object[]{object})
   );
   public static final DynamicCommandExceptionType INVALID_ID = new DynamicCommandExceptionType(
      id -> Component.translatable("commands.ftbquests.command.error.invalid_id", new Object[]{id})
   );
   private static final SimpleCommandExceptionType NO_INVENTORY = new SimpleCommandExceptionType(
      Component.translatable("commands.ftbquests.command.error.no_inventory")
   );
   private static final Set<UUID> warnedPlayers = new HashSet<>();

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                                                "ftbquests"
                                             )
                                             .then(
                                                ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("editing_mode")
                                                         .requires(FTBQuestsCommands::isSSPOrEditor))
                                                      .executes(
                                                         c -> editingMode(
                                                            (CommandSourceStack)c.getSource(), ((CommandSourceStack)c.getSource()).getPlayerOrException(), null
                                                         )
                                                      ))
                                                   .then(
                                                      ((RequiredArgumentBuilder)Commands.argument("enabled", BoolArgumentType.bool())
                                                            .executes(
                                                               c -> editingMode(
                                                                  (CommandSourceStack)c.getSource(),
                                                                  ((CommandSourceStack)c.getSource()).getPlayerOrException(),
                                                                  BoolArgumentType.getBool(c, "enabled")
                                                               )
                                                            ))
                                                         .then(
                                                            Commands.argument("player", EntityArgument.player())
                                                               .executes(
                                                                  c -> editingMode(
                                                                     (CommandSourceStack)c.getSource(),
                                                                     EntityArgument.getPlayer(c, "player"),
                                                                     BoolArgumentType.getBool(c, "enabled")
                                                                  )
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("locked")
                                                      .requires(FTBQuestsCommands::hasEditorPermission))
                                                   .executes(
                                                      c -> locked(
                                                         (CommandSourceStack)c.getSource(), ((CommandSourceStack)c.getSource()).getPlayerOrException(), null
                                                      )
                                                   ))
                                                .then(
                                                   ((RequiredArgumentBuilder)Commands.argument("enabled", BoolArgumentType.bool())
                                                         .executes(
                                                            c -> locked(
                                                               (CommandSourceStack)c.getSource(),
                                                               ((CommandSourceStack)c.getSource()).getPlayerOrException(),
                                                               BoolArgumentType.getBool(c, "enabled")
                                                            )
                                                         ))
                                                      .then(
                                                         Commands.argument("player", EntityArgument.player())
                                                            .executes(
                                                               c -> locked(
                                                                  (CommandSourceStack)c.getSource(),
                                                                  EntityArgument.getPlayer(c, "player"),
                                                                  BoolArgumentType.getBool(c, "enabled")
                                                               )
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)Commands.literal("delete_empty_reward_tables")
                                                .requires(FTBQuestsCommands::hasEditorPermission))
                                             .executes(context -> deleteEmptyRewardTables((CommandSourceStack)context.getSource()))
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)Commands.literal("change_progress").requires(FTBQuestsCommands::hasEditorPermission))
                                          .then(
                                             ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
                                                            "players", EntityArgument.players()
                                                         )
                                                         .then(
                                                            Commands.literal("reset")
                                                               .then(Commands.argument("quest_object", StringArgumentType.string()).executes(ctx -> {
                                                                  Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
                                                                  return changeProgress(
                                                                     (CommandSourceStack)ctx.getSource(),
                                                                     players,
                                                                     true,
                                                                     StringArgumentType.getString(ctx, "quest_object")
                                                                  );
                                                               }))
                                                         ))
                                                      .then(Commands.literal("reset-all").executes(ctx -> {
                                                         Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
                                                         return changeProgress((CommandSourceStack)ctx.getSource(), players, true, "1");
                                                      })))
                                                   .then(
                                                      Commands.literal("complete")
                                                         .then(Commands.argument("quest_object", StringArgumentType.string()).executes(ctx -> {
                                                            Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
                                                            return changeProgress(
                                                               (CommandSourceStack)ctx.getSource(),
                                                               players,
                                                               false,
                                                               StringArgumentType.getString(ctx, "quest_object")
                                                            );
                                                         }))
                                                   ))
                                                .then(Commands.literal("complete-all").executes(ctx -> {
                                                   Collection<ServerPlayer> players = EntityArgument.getPlayers(ctx, "players");
                                                   return changeProgress((CommandSourceStack)ctx.getSource(), players, false, "1");
                                                }))
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)Commands.literal("export_reward_table_to_chest").requires(FTBQuestsCommands::hasEditorPermission))
                                       .then(
                                          ((RequiredArgumentBuilder)Commands.argument("reward_table", StringArgumentType.string())
                                                .executes(
                                                   ctx -> exportRewards(
                                                      (CommandSourceStack)ctx.getSource(), StringArgumentType.getString(ctx, "reward_table"), null
                                                   )
                                                ))
                                             .then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> {
                                                BlockPos pos = BlockPosArgument.getSpawnablePos(ctx, "pos");
                                                return exportRewards(
                                                   (CommandSourceStack)ctx.getSource(), StringArgumentType.getString(ctx, "reward_table"), pos
                                                );
                                             }))
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)Commands.literal("import_reward_table_from_chest").requires(FTBQuestsCommands::hasEditorPermission))
                                    .then(((RequiredArgumentBuilder)Commands.argument("name", StringArgumentType.string()).executes(ctx -> {
                                       String name = StringArgumentType.getString(ctx, "name");
                                       return importRewards((CommandSourceStack)ctx.getSource(), name, null);
                                    })).then(Commands.argument("pos", BlockPosArgument.blockPos()).executes(ctx -> {
                                       String name = StringArgumentType.getString(ctx, "name");
                                       BlockPos pos = BlockPosArgument.getSpawnablePos(ctx, "pos");
                                       return importRewards((CommandSourceStack)ctx.getSource(), name, pos);
                                    })))
                              ))
                           .then(
                              ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("generate_chapter")
                                          .requires(FTBQuestsCommands::hasEditorPermission))
                                       .then(
                                          Commands.literal("from_entire_creative_list")
                                             .executes(context -> generateAllItemChapter((CommandSourceStack)context.getSource()))
                                       ))
                                    .then(
                                       Commands.literal("from_player_inventory")
                                          .executes(context -> generateChapterFromPlayerInv((CommandSourceStack)context.getSource()))
                                    ))
                                 .then(
                                    Commands.literal("from_inventory")
                                       .then(
                                          Commands.argument("pos", BlockPosArgument.blockPos())
                                             .executes(
                                                context -> generateChapterFromInv(
                                                   (CommandSourceStack)context.getSource(), BlockPosArgument.getSpawnablePos(context, "pos")
                                                )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("reload")
                                       .requires(FTBQuestsCommands::hasEditorPermission))
                                    .executes(context -> doReload((CommandSourceStack)context.getSource(), true, true)))
                                 .then(Commands.literal("quests").executes(context -> doReload((CommandSourceStack)context.getSource(), true, false))))
                              .then(Commands.literal("team_progress").executes(context -> doReload((CommandSourceStack)context.getSource(), false, true)))
                        ))
                     .then(
                        ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("block_rewards").requires(FTBQuestsCommands::hasEditorPermission))
                              .executes(
                                 c -> toggleRewardBlocking((CommandSourceStack)c.getSource(), ((CommandSourceStack)c.getSource()).getPlayerOrException(), null)
                              ))
                           .then(
                              ((RequiredArgumentBuilder)Commands.argument("enabled", BoolArgumentType.bool())
                                    .executes(
                                       c -> toggleRewardBlocking(
                                          (CommandSourceStack)c.getSource(),
                                          ((CommandSourceStack)c.getSource()).getPlayerOrException(),
                                          BoolArgumentType.getBool(c, "enabled")
                                       )
                                    ))
                                 .then(
                                    ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player())
                                          .requires(FTBQuestsCommands::hasEditorPermission))
                                       .executes(
                                          c -> toggleRewardBlocking(
                                             (CommandSourceStack)c.getSource(), EntityArgument.getPlayer(c, "player"), BoolArgumentType.getBool(c, "enabled")
                                          )
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("open_book")
                           .executes(c -> openQuest(((CommandSourceStack)c.getSource()).getPlayerOrException(), null)))
                        .then(
                           Commands.argument("quest_object", StringArgumentType.string())
                              .executes(
                                 c -> openQuest(((CommandSourceStack)c.getSource()).getPlayerOrException(), StringArgumentType.getString(c, "quest_object"))
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("clear_item_display_cache").requires(FTBQuestsCommands::hasEditorPermission))
                     .executes(c -> clearDisplayCache((CommandSourceStack)c.getSource()))
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("cleanup_stale_translations")
                        .requires(FTBQuestsCommands::hasEditorPermission))
                     .then(Commands.literal("show").executes(c -> cleanupTranslations((CommandSourceStack)c.getSource(), true))))
                  .then(Commands.literal("execute").executes(c -> cleanupTranslations((CommandSourceStack)c.getSource(), false)))
            )
      );
   }

   private static int cleanupTranslations(CommandSourceStack source, boolean dryRun) {
      ServerQuestFile.INSTANCE.getTranslationManager().cleanupTranslations(source, dryRun);
      return 0;
   }

   private static boolean isSSPOrEditor(CommandSourceStack s) {
      return s.getServer() != null && s.getServer().isSingleplayer() || hasEditorPermission(s);
   }

   private static boolean hasEditorPermission(CommandSourceStack stack) {
      return stack.hasPermission(2) || stack.isPlayer() && PermissionsHelper.hasEditorPermission(stack.getPlayer(), false);
   }

   private static QuestObjectBase getQuestObjectForString(String idStr) throws CommandSyntaxException {
      ServerQuestFile file = ServerQuestFile.INSTANCE;
      if (file == null) {
         throw NO_FILE.create();
      }

      if (idStr.startsWith("#")) {
         String val = idStr.substring(1);

         for (QuestObjectBase qob : file.getAllObjects()) {
            if (qob.hasTag(val)) {
               return qob;
            }
         }

         throw NO_OBJECT.create(idStr);
      } else {
         long id = QuestObjectBase.parseHexId(idStr).orElseThrow(() -> INVALID_ID.create(idStr));
         QuestObjectBase qob = file.getBase(id);
         if (qob == null) {
            throw NO_OBJECT.create(idStr);
         } else {
            return qob;
         }
      }
   }

   private static boolean playerCanSeeQuestObject(ServerPlayer player, QuestObject qo) {
      return qo instanceof Chapter ? true : ServerQuestFile.INSTANCE.getTeamData(player).map(data -> {
         Quest quest = qo.getRelatedQuest();
         return quest != null && (data.getCanEdit(player) || !quest.hideDetailsUntilStartable() || data.canStartTasks(quest));
      }).orElse(false);
   }

   private static int openQuest(ServerPlayer player, String qobId) throws CommandSyntaxException {
      if (qobId == null) {
         NetworkManager.sendToPlayer(player, OpenQuestBookMessage.lastOpenedQuest());
         return 1;
      } else if (getQuestObjectForString(qobId) instanceof QuestObject qo && playerCanSeeQuestObject(player, qo)) {
         NetworkManager.sendToPlayer(player, new OpenQuestBookMessage(qo.id));
         return 1;
      } else {
         return 0;
      }
   }

   private static int exportRewards(CommandSourceStack source, String idStr, @Nullable BlockPos pos) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      ServerLevel level = source.getLevel();
      if (getQuestObjectForString(idStr) instanceof RewardTable table) {
         pos = Objects.requireNonNullElse(pos, BlockPos.containing(player.pick(10.0, 1.0F, false).getLocation()));
         if (level.getBlockEntity(pos) == null) {
            throw NO_INVENTORY.create();
         }

         List<ItemStack> items = new ArrayList<>();

         for (WeightedReward wr : table.getWeightedRewards()) {
            if (wr.getReward() instanceof ItemReward itemReward) {
               items.add(itemReward.getItem());
            }
         }

         InventoryUtil.putItemsInInventory(items, level, pos, Direction.UP, true);
         source.sendSuccess(
            () -> Component.translatable("commands.ftbquests.command.feedback.table_exported", new Object[]{table.getTitle(), items.size()}), false
         );
         return 1;
      } else {
         throw NO_OBJECT.create(idStr);
      }
   }

   private static int importRewards(CommandSourceStack source, String name, BlockPos pos) throws CommandSyntaxException {
      ServerPlayer player = source.getPlayerOrException();
      ServerLevel level = source.getLevel();
      ServerQuestFile file = ServerQuestFile.INSTANCE;
      if (pos == null) {
         pos = BlockPos.containing(player.pick(10.0, 1.0F, false).getLocation());
      }

      if (level.getBlockEntity(pos) == null) {
         throw NO_INVENTORY.create();
      }

      RewardTable table = new RewardTable(file.newID(), file);
      table.setRawTitle(name);
      table.setRawIcon(Items.CHEST.getDefaultInstance());

      for (ItemStack stack : InventoryUtil.getItemsInInventory(level, pos, Direction.UP)) {
         if (!stack.isEmpty()) {
            table.addReward(table.makeWeightedItemReward(stack, 1.0F));
         }
      }

      file.addRewardTable(table);
      file.refreshIDMap();
      file.clearCachedData();
      file.markDirty();
      NetworkHelper.sendToAll(level.getServer(), CreateObjectResponseMessage.create(table, null));
      NetworkHelper.sendToAll(level.getServer(), SyncTranslationMessageToClient.create(table, file.getLocale(), TranslationKey.TITLE, name));
      source.sendSuccess(
         () -> Component.translatable("commands.ftbquests.command.feedback.table_imported", new Object[]{name, table.getWeightedRewards().size()}), false
      );
      return 1;
   }

   private static int editingMode(CommandSourceStack source, ServerPlayer player, @Nullable Boolean canEdit) {
      return ServerQuestFile.INSTANCE.getTeamData(player).map(data -> {
         boolean newCanEdit = Objects.requireNonNullElse(canEdit, !data.getCanEdit(player));
         data.setCanEdit(player, newCanEdit);
         if (newCanEdit) {
            source.sendSuccess(() -> Component.translatable("commands.ftbquests.editing_mode.enabled", new Object[]{player.getDisplayName()}), true);
         } else {
            source.sendSuccess(() -> Component.translatable("commands.ftbquests.editing_mode.disabled", new Object[]{player.getDisplayName()}), true);
         }

         return 1;
      }).orElse(0);
   }

   private static int locked(CommandSourceStack source, ServerPlayer player, @Nullable Boolean locked) {
      return ServerQuestFile.INSTANCE.getTeamData(player).map(data -> {
         boolean newLocked = Objects.requireNonNullElse(locked, !data.isLocked());
         data.setLocked(newLocked);
         if (newLocked) {
            source.sendSuccess(() -> Component.translatable("commands.ftbquests.locked.enabled", new Object[]{player.getDisplayName()}), true);
         } else {
            source.sendSuccess(() -> Component.translatable("commands.ftbquests.locked.disabled", new Object[]{player.getDisplayName()}), true);
         }

         return 1;
      }).orElse(0);
   }

   private static int changeProgress(CommandSourceStack source, Collection<ServerPlayer> players, boolean reset, String idStr) throws CommandSyntaxException {
      QuestObjectBase questObject = getQuestObjectForString(idStr);

      for (ServerPlayer player : players) {
         ServerQuestFile.INSTANCE.getTeamData(player).ifPresent(data -> {
            ProgressChange progressChange = new ProgressChange(questObject, player.getUUID()).setReset(reset);
            questObject.forceProgress(data, progressChange);
            if (questObject instanceof Quest quest && reset) {
               data.clearRepeatCooldown(quest);
               ClearRepeatCooldownMessage.sendToTeam(quest, data.getTeamId());
            }
         });
      }

      source.sendSuccess(() -> Component.translatable("commands.ftbquests.change_progress.text"), false);
      return 1;
   }

   private static int deleteEmptyRewardTables(CommandSourceStack source) {
      int removed = ServerQuestFile.INSTANCE.removeEmptyRewardTables(source);
      source.sendSuccess(() -> Component.translatable("commands.ftbquests.command.delete_empty_reward_tables.text", new Object[]{removed}), false);
      return 1;
   }

   private static int generateAllItemChapter(CommandSourceStack source) {
      if (!CreativeModeTabs.searchTab().hasAnyItems()) {
         CreativeModeTabs.tryRebuildTabContents(source.enabledFeatures(), true, source.getLevel().registryAccess());
      }

      return generateMultiItemChapter(source, CreativeModeTabs.searchTab().getSearchTabDisplayItems());
   }

   private static int generateChapterFromInv(CommandSourceStack source, BlockPos pos) {
      return generateMultiItemChapter(source, InventoryUtil.getItemsInInventory(source.getLevel(), pos, Direction.UP));
   }

   private static int generateChapterFromPlayerInv(CommandSourceStack source) throws CommandSyntaxException {
      return generateMultiItemChapter(source, source.getPlayerOrException().getInventory().items);
   }

   private static int generateMultiItemChapter(CommandSourceStack source, Collection<ItemStack> allItems) {
      List<ItemStack> list = allItems.stream()
         .filter(stack -> !stack.isEmpty() && RegistrarManager.getId(stack.getItem(), Registries.ITEM) != null)
         .sorted(Comparator.comparing(a -> RegistrarManager.getId(a.getItem(), Registries.ITEM)))
         .toList();
      if (list.isEmpty()) {
         FTBQuests.LOGGER.warn("No suitable items found for chapter auto-creation");
         return 0;
      } else {
         ServerQuestFile file = ServerQuestFile.INSTANCE;
         long newId = file.newID();
         Chapter chapter = new Chapter(newId, file, file.getDefaultChapterGroup());
         chapter.onCreated();
         FTBQuests.LOGGER.info("Adding {} items to new chapter ID: {}", list.size(), newId);
         chapter.setRawTitle("Auto-generated chapter [" + list.size() + " items]");
         chapter.setRawIcon(new ItemStack(Items.COMPASS));
         chapter.setDefaultQuestShape("rsquare");
         NetworkHelper.sendToAll(source.getServer(), CreateObjectResponseMessage.create(chapter, null));
         NetworkHelper.sendToAll(
            source.getServer(), SyncTranslationMessageToClient.create(chapter, file.getLocale(), TranslationKey.TITLE, chapter.getRawTitle())
         );
         addItemsToChapter(source, list, chapter);
         file.markDirty();
         file.saveNow();
         source.sendSuccess(() -> Component.literal("Done!"), false);
         return 1;
      }
   }

   private static void addItemsToChapter(CommandSourceStack source, Collection<ItemStack> list, Chapter chapter) {
      int col = 0;
      int row = 0;
      String modid = null;

      for (ItemStack stack : list) {
         ResourceLocation id = RegistrarManager.getId(stack.getItem(), Registries.ITEM);
         if (modid == null) {
            modid = id.getNamespace();
         }

         if (!modid.equals(id.getNamespace())) {
            modid = id.getNamespace();
            col = 0;
            row += 2;
         } else if (col >= 40) {
            col = 0;
            row++;
         }

         Quest quest = new Quest(chapter.file.newID(), chapter);
         quest.onCreated();
         quest.setX(col);
         quest.setY(row);
         quest.setRawSubtitle(stack.save(source.registryAccess(), new CompoundTag()).toString());
         NetworkHelper.sendToAll(source.getServer(), CreateObjectResponseMessage.create(quest, null));
         ItemTask task = new ItemTask(chapter.file.newID(), quest);
         task.onCreated();
         task.setStackAndCount(stack, 1);
         NetworkHelper.sendToAll(source.getServer(), CreateObjectResponseMessage.create(task, task.getType().makeExtraNBT()));
         col++;
      }
   }

   private static int doReload(CommandSourceStack source, boolean quests, boolean progression) {
      if (!quests && !progression) {
         return 0;
      }

      ServerQuestFile instance = ServerQuestFile.INSTANCE;
      ServerPlayer sender = source.getPlayer();
      instance.load(quests, progression);
      NetworkHelper.sendToAll(source.getServer(), new SyncQuestsMessage(instance));
      source.getServer().getPlayerList().getPlayers().forEach(p -> {
         NetworkManager.sendToPlayer(p, SyncEditorPermissionMessage.forPlayer(p));
         instance.getTranslationManager().sendTranslationsToPlayer(p);
      });
      String suffix = quests && progression ? "" : (quests ? "_quest" : "_progress");
      source.sendSuccess(() -> Component.translatable("commands.ftbquests.command.feedback.reloaded" + suffix), false);
      UUID id = sender == null ? Util.NIL_UUID : sender.getUUID();
      if (!warnedPlayers.contains(id)) {
         source.sendSuccess(() -> Component.translatable("commands.ftbquests.command.feedback.reloaded.disclaimer").withStyle(ChatFormatting.GOLD), false);
         warnedPlayers.add(id);
      }

      return 1;
   }

   private static int toggleRewardBlocking(CommandSourceStack source, ServerPlayer player, Boolean doBlocking) {
      return ServerQuestFile.INSTANCE
         .getTeamData(player)
         .map(
            data -> {
               boolean shouldBlock = Objects.requireNonNullElse(doBlocking, !data.areRewardsBlocked());
               data.setRewardsBlocked(shouldBlock);
               source.sendSuccess(
                  () -> Component.translatable("commands.ftbquests.command.feedback.rewards_blocked", new Object[]{data, data.areRewardsBlocked()}), false
               );
               return 1;
            }
         )
         .orElse(0);
   }

   private static int clearDisplayCache(CommandSourceStack source) {
      ClearDisplayCacheMessage.clearForAll(source.getServer());
      source.sendSuccess(() -> Component.translatable("commands.ftbquests.command.feedback.clear_display_cache"), false);
      return 1;
   }
}
