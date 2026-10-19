package dev.ftb.mods.ftbquests.quest;

import com.mojang.util.UndashedUuid;
import dev.architectury.networking.NetworkManager;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.ftb.mods.ftblibrary.snbt.SNBT;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.events.QuestProgressEventData;
import dev.ftb.mods.ftbquests.integration.PermissionsHelper;
import dev.ftb.mods.ftbquests.net.CreateOtherTeamDataMessage;
import dev.ftb.mods.ftbquests.net.DeleteObjectResponseMessage;
import dev.ftb.mods.ftbquests.net.MoveChapterGroupResponseMessage;
import dev.ftb.mods.ftbquests.net.SyncEditorPermissionMessage;
import dev.ftb.mods.ftbquests.net.SyncQuestsMessage;
import dev.ftb.mods.ftbquests.net.SyncTeamDataMessage;
import dev.ftb.mods.ftbquests.net.TeamDataChangedMessage;
import dev.ftb.mods.ftbquests.net.TeamDataUpdate;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.quest.task.TaskTypes;
import dev.ftb.mods.ftbquests.util.FTBQuestsInventoryListener;
import dev.ftb.mods.ftbquests.util.FileUtils;
import dev.ftb.mods.ftbquests.util.PlayerInventorySummary;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.Team;
import dev.ftb.mods.ftbteams.api.event.PlayerChangedTeamEvent;
import dev.ftb.mods.ftbteams.api.event.PlayerLoggedInAfterTeamEvent;
import dev.ftb.mods.ftbteams.api.event.TeamCreatedEvent;
import dev.ftb.mods.ftbteams.data.PartyTeam;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Date;
import java.util.Deque;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.LevelResource;

public class ServerQuestFile extends BaseQuestFile {
   public static final LevelResource FTBQUESTS_DATA = new LevelResource("ftbquests");
   public static ServerQuestFile INSTANCE;
   public final MinecraftServer server;
   private boolean shouldSave;
   private boolean isLoading;
   private Path folder;
   private final Deque<ServerPlayer> playerContextStack = new ArrayDeque<>();

   public ServerQuestFile(MinecraftServer s) {
      this.server = s;
      this.shouldSave = false;
      this.isLoading = false;
      int taskTypeId = 0;

      for (TaskType type : TaskTypes.TYPES.values()) {
         type.internalId = ++taskTypeId;
         this.taskTypeIds.put(type.internalId, type);
      }

      int rewardTypeId = 0;

      for (RewardType type : RewardTypes.TYPES.values()) {
         type.intId = ++rewardTypeId;
         this.rewardTypeIds.put(type.intId, type);
      }
   }

   public void load() {
      this.load(true, true);
   }

   public void load(boolean quests, boolean progression) {
      this.folder = Platform.getConfigFolder().resolve("ftbquests/quests");
      if (quests && Files.exists(this.folder)) {
         FTBQuests.LOGGER.info("Loading quests from {}", this.folder);
         this.isLoading = true;
         this.readDataFull(this.folder, this.server.registryAccess());
         this.isLoading = false;
      }

      if (progression) {
         Path path = this.server.getWorldPath(FTBQUESTS_DATA);
         if (Files.exists(path)) {
            try (Stream<Path> s = Files.list(path)) {
               s.filter(p -> p.getFileName().toString().contains("-") && p.getFileName().toString().endsWith(".snbt")).forEach(path1 -> {
                  SNBTCompoundTag nbt = SNBT.read(path1);
                  if (nbt != null) {
                     try {
                        UUID uuid = UndashedUuid.fromString(nbt.getString("uuid"));
                        TeamData data = new TeamData(uuid, this);
                        this.addData(data, true);
                        data.deserializeNBT(nbt);
                     } catch (Exception ex) {
                        FTBQuests.LOGGER.error("can't parse progression data for {}: {}", path1, exx.getMessage());
                     }
                  }
               });
            } catch (Exception ex) {
               FTBQuests.LOGGER.error("can't read directory {}: {}", path, ex.getMessage());
            }
         }
      }
   }

   @Override
   public Env getSide() {
      return Env.SERVER;
   }

   @Override
   public Provider holderLookup() {
      return this.server.registryAccess();
   }

   @Override
   public boolean isLoading() {
      return this.isLoading;
   }

   @Override
   public Path getFolder() {
      return this.folder;
   }

   @Override
   public void deleteObject(long id) {
      QuestObjectBase object = this.getBase(id);
      if (object != null) {
         this.getTranslationManager().removeAllTranslations(object);
         object.deleteChildren();
         object.deleteSelf();
         this.refreshIDMap();
         this.markDirty();
         object.getPath().ifPresent(path -> FileUtils.delete(this.getFolder().resolve(path).toFile()));
      }

      NetworkHelper.sendToAll(this.server, new DeleteObjectResponseMessage(id));
   }

   @Override
   public void markDirty() {
      this.shouldSave = true;
   }

   public void saveNow() {
      if (this.getFolder() != null) {
         if (this.shouldSave) {
            this.writeDataFull(this.getFolder(), this.server.registryAccess());
            this.shouldSave = false;
         }

         this.getTranslationManager().saveToNBT(this.getFolder().resolve("lang"), false);
         this.getAllTeamData().forEach(TeamData::saveIfChanged);
      }
   }

   public void unload() {
      this.saveNow();
      this.deleteChildren();
      this.deleteSelf();
   }

   public ServerPlayer getCurrentPlayer() {
      return this.playerContextStack.peek();
   }

   public void withPlayerContext(ServerPlayer player, Runnable toDo) {
      this.playerContextStack.push(player);

      try {
         toDo.run();
      } finally {
         this.playerContextStack.pop();
      }
   }

   public void playerLoggedIn(PlayerLoggedInAfterTeamEvent event) {
      ServerPlayer player = event.getPlayer();
      TeamData data = this.getOrCreateTeamData(event.getTeam());
      NetworkManager.sendToPlayer(player, new SyncQuestsMessage(this));
      NetworkManager.sendToPlayer(player, new SyncEditorPermissionMessage(PermissionsHelper.hasEditorPermission(player, false)));
      this.getTranslationManager().sendTranslationsToPlayer(player);
      player.inventoryMenu.addSlotListener(new FTBQuestsInventoryListener(player));
      this.checkQuestBookOnLogin(data, player);
   }

   private void checkQuestBookOnLogin(TeamData data, ServerPlayer player) {
      if (!data.isLocked()) {
         this.withPlayerContext(player, () -> {
            Collection<ServerPlayer> onlineMembers = data.getOnlineMembers();
            List<ServerPlayer> pList = List.of(player);
            Date now = new Date();
            PlayerInventorySummary.build(player);
            this.forAllQuests(quest -> {
               if (!data.isCompleted(quest) && quest.getProgressionMode() == ProgressionMode.FLEXIBLE && data.areDependenciesComplete(quest)) {
                  for (Task task : quest.getTasks()) {
                     if (!data.isCompleted(task) && data.getProgress(task) >= task.getMaxProgress()) {
                        data.markTaskCompleted(task);
                     }
                  }
               }

               if (!data.isCompleted(quest) && quest.isCompletedRaw(data)) {
                  quest.onCompleted(new QuestProgressEventData<>(now, data, quest, onlineMembers, pList));
               }

               data.checkAutoCompletion(quest);
               if (data.canStartTasks(quest)) {
                  quest.getTasks().stream().filter(Task::checkOnLogin).forEach(taskx -> taskx.submitTask(data, player));
               }
            });
         });
      }
   }

   public void teamCreated(TeamCreatedEvent event) {
      UUID id = event.getTeam().getId();
      TeamData data = this.teamDataMap.computeIfAbsent(id, k -> {
         TeamData newTeamData = new TeamData(id, this);
         newTeamData.markDirty();
         return newTeamData;
      });
      data.setName(event.getTeam().getShortName());
      this.addData(data, false);
      if (event.getTeam() instanceof PartyTeam) {
         FTBTeamsAPI.api().getManager().getPlayerTeamForPlayerID(event.getCreator().getUUID()).ifPresent(playerTeam -> {
            TeamData oldTeamData = this.getOrCreateTeamData(playerTeam);
            data.copyData(oldTeamData);
         });
      }

      NetworkHelper.sendToAll(this.server, new CreateOtherTeamDataMessage(TeamDataUpdate.forTeamData(data)));
   }

   public void playerChangedTeam(PlayerChangedTeamEvent event) {
      event.getPreviousTeam().ifPresent(prevTeam -> {
         Team curTeam = event.getTeam();
         TeamData oldTeamData = this.getOrCreateTeamData(prevTeam);
         TeamData newTeamData = this.getOrCreateTeamData(curTeam);
         if (prevTeam.isPlayerTeam() && curTeam.isPartyTeam() && !curTeam.getOwner().equals(event.getPlayerId())) {
            newTeamData.mergeData(oldTeamData);
            if (event.getPlayer() != null) {
               this.withPlayerContext(event.getPlayer(), () -> this.forAllQuests(newTeamData::checkAutoCompletion));
            }
         } else if (prevTeam.isPartyTeam() && curTeam.isPlayerTeam()) {
            newTeamData.mergeClaimedRewards(oldTeamData);
         }

         NetworkHelper.sendToAll(this.server, new TeamDataChangedMessage(TeamDataUpdate.forTeamData(oldTeamData), TeamDataUpdate.forTeamData(newTeamData)));
         NetworkManager.sendToPlayers(curTeam.getOnlineMembers(), new SyncTeamDataMessage(newTeamData));
      });
   }

   @Override
   public boolean isPlayerOnTeam(Player player, TeamData teamData) {
      return FTBTeamsAPI.api().getManager().getTeamForPlayerID(player.getUUID()).map(team -> team.getTeamId().equals(teamData.getTeamId())).orElse(false);
   }

   @Override
   public boolean moveChapterGroup(long id, boolean movingUp) {
      if (super.moveChapterGroup(id, movingUp)) {
         this.markDirty();
         this.clearCachedData();
         NetworkHelper.sendToAll(this.server, new MoveChapterGroupResponseMessage(id, movingUp));
         return true;
      } else {
         return false;
      }
   }

   @Override
   public String getLocale() {
      return this.getFallbackLocale();
   }
}
