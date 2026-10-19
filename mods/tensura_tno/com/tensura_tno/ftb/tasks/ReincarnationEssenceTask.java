package com.tensura_tno.ftb.tasks;

import com.tensura_tno.ftb.STExtrasHelper;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ISingleLongValueTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class ReincarnationEssenceTask extends Task implements ISingleLongValueTask {
   public static TaskType RE_TASK;
   private long value = 500L;

   public ReincarnationEssenceTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return RE_TASK;
   }

   public long getMaxProgress() {
      return this.value;
   }

   public void setValue(long v) {
      this.value = v;
   }

   public String formatMaxProgress() {
      return Long.toUnsignedString(this.value);
   }

   public String formatProgress(TeamData teamData, long progress) {
      return Long.toUnsignedString(progress);
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putLong("value", this.value);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.value = nbt.getLong("value");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarLong(this.value);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.value = buffer.readVarLong();
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addLong("value", this.value, v -> this.value = v, 500L, 1L, Long.MAX_VALUE).setNameKey("tensura_tno.task.re.value");
   }

   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      if (this.checkTaskSequence(teamData)) {
         long currentRE = STExtrasHelper.getRE(player);
         teamData.setProgress(this, Math.min(currentRE, this.value));
      }
   }
}
