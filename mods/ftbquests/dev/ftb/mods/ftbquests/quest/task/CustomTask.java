package dev.ftb.mods.ftbquests.quest.task;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.net.EditObjectResponseMessage;
import dev.ftb.mods.ftbquests.net.SubmitTaskMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import java.util.function.Predicate;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CustomTask extends Task {
   public static final Predicate<QuestObjectBase> PREDICATE = object -> object instanceof CustomTask;
   private CustomTask.Check check = null;
   private int checkTimer = 1;
   private long maxProgress = 1L;
   private boolean enableButton = false;

   public CustomTask(long id, Quest quest) {
      super(id, quest);
   }

   public void setCheck(CustomTask.Check check) {
      this.check = check;
   }

   @Override
   public TaskType getType() {
      return TaskTypes.CUSTOM;
   }

   @Override
   public long getMaxProgress() {
      return this.maxProgress;
   }

   public void setCheckTimer(int checkTimer) {
      if (this.checkTimer != checkTimer) {
         this.checkTimer = checkTimer;
         CustomTask.TaskSync.toSync.add(this.id);
      }
   }

   public void setMaxProgress(long maxProgress) {
      if (this.maxProgress != maxProgress) {
         this.maxProgress = maxProgress;
         CustomTask.TaskSync.toSync.add(this.id);
      }
   }

   public void setEnableButton(boolean enableButton) {
      if (this.enableButton != enableButton) {
         this.enableButton = enableButton;
         CustomTask.TaskSync.toSync.add(this.id);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onButtonClicked(Button button, boolean canClick) {
      if (this.enableButton && canClick) {
         button.playClickSound();
         NetworkManager.sendToServer(new SubmitTaskMessage(this.id));
      }
   }

   @Override
   public int autoSubmitOnPlayerTick() {
      return this.check == null ? 0 : this.checkTimer;
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.checkTimer);
      buffer.writeVarLong(this.maxProgress);
      buffer.writeBoolean(this.enableButton);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.checkTimer = buffer.readVarInt();
      this.maxProgress = buffer.readVarLong();
      this.enableButton = buffer.readBoolean();
   }

   @Override
   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      if (this.check != null && this.checkTaskSequence(teamData) && !teamData.isCompleted(this)) {
         this.check.check(new CustomTask.Data(this, teamData), player);
      }
   }

   @Override
   public boolean checkOnLogin() {
      return false;
   }

   @FunctionalInterface
   public interface Check {
      void check(CustomTask.Data var1, ServerPlayer var2);
   }

   public record Data(CustomTask task, TeamData teamData) {
      public long getProgress() {
         return this.teamData.getProgress(this.task);
      }

      public void setProgress(long l) {
         this.teamData.setProgress(this.task, l);
      }

      public void addProgress(long l) {
         this.teamData.addProgress(this.task, l);
      }
   }

   public static class TaskSync {
      private static final LongSet toSync = new LongOpenHashSet();

      public static void tick(MinecraftServer server) {
         if (!toSync.isEmpty() && ServerQuestFile.INSTANCE != null) {
            toSync.forEach(id -> {
               if (ServerQuestFile.INSTANCE.get(id) instanceof CustomTask c) {
                  NetworkHelper.sendToAll(server, new EditObjectResponseMessage(c));
               }
            });
            toSync.clear();
         }
      }
   }
}
