package com.tensura_tno.ftb.tasks;

import com.tensura_tno.ftb.STExtrasHelper;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public class STExtrasQuestTask extends AbstractBooleanTask {
   public static TaskType STEXTRAS_QUEST;
   private String category = "DAILY";
   private boolean autoReset = true;

   public STExtrasQuestTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STEXTRAS_QUEST;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      if (player == null) {
         return false;
      }

      if (!STExtrasHelper.isLoaded()) {
         return false;
      }

      Set<ResourceLocation> active = STExtrasHelper.getActiveQuests(player, this.category);
      Set<ResourceLocation> completed = STExtrasHelper.getCompletedQuests(player, this.category);
      return active != null && completed != null ? active.isEmpty() && !completed.isEmpty() : false;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("category", this.category);
      nbt.putBoolean("autoReset", this.autoReset);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      String cat = nbt.getString("category");
      this.category = cat.isEmpty() ? "DAILY" : cat;
      this.autoReset = nbt.getBoolean("autoReset");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.category);
      buffer.writeBoolean(this.autoReset);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.category = buffer.readUtf();
      this.autoReset = buffer.readBoolean();
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("category", this.category, v -> this.category = v, "DAILY").setNameKey("tensura_tno.task.stextras_quest.category");
      config.addBool("autoReset", this.autoReset, v -> this.autoReset = v, true).setNameKey("tensura_tno.task.stextras_quest.auto_reset");
   }

   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   public String getCategory() {
      return this.category;
   }

   public boolean isAutoReset() {
      return this.autoReset;
   }
}
