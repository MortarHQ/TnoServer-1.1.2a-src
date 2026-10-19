package com.tensura_guild.compat.ftbquests;

import com.tensura_guild.data.GuildLevelData;
import com.tensura_guild.registry.GuildAttachments;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AdventureExperienceTask extends Task {
   private static final String TARGET_EXPERIENCE_KEY = "target_experience";
   private static final long DEFAULT_TARGET_EXPERIENCE = 100L;
   private long targetExperience = 100L;

   public AdventureExperienceTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return GuildFtbQuestTaskTypes.ADVENTURE_EXPERIENCE;
   }

   public long getMaxProgress() {
      return Math.max(1L, this.targetExperience);
   }

   public String formatMaxProgress() {
      return Long.toString(this.getMaxProgress());
   }

   public String formatProgress(TeamData teamData, long progress) {
      return Long.toString(Math.min(this.getMaxProgress(), progress));
   }

   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   public boolean checkOnLogin() {
      return true;
   }

   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      this.recordProgress(teamData, player);
   }

   public void recordProgress(TeamData teamData, ServerPlayer player) {
      if (this.checkTaskSequence(teamData)) {
         long currentProgress = teamData.getProgress(this);
         long playerExperience = ((GuildLevelData)player.getData(GuildAttachments.GUILD_LEVEL)).totalExperience();
         long nextProgress = Math.min(this.getMaxProgress(), Math.max(currentProgress, playerExperience));
         if (nextProgress > currentProgress) {
            teamData.setProgress(this, nextProgress);
         }
      }
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putLong("target_experience", this.getMaxProgress());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      long value = nbt.getLong("target_experience");
      this.targetExperience = value <= 0L ? 100L : value;
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarLong(this.getMaxProgress());
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      long value = buffer.readVarLong();
      this.targetExperience = value <= 0L ? 100L : value;
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addLong("target_experience", this.getMaxProgress(), value -> this.targetExperience = Math.max(1L, value), 100L, 1L, Long.MAX_VALUE)
         .setNameKey("ftbquests.task.tensura_guild.adventure_experience.target_experience");
   }

   public Component getAltTitle() {
      return Component.translatable("ftbquests.task.tensura_guild.adventure_experience")
         .append(": ")
         .append(Component.literal(Long.toString(this.getMaxProgress())));
   }
}
