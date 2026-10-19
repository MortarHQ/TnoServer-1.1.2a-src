package org.crypticdev.stextras.integration.FTB.Quests.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.tensura.storage.TensuraStorages;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasTaskTypes;

public class ReachPrestigeLevelTask extends AbstractSTExtrasTask {
   private static final String PRESTIGE_LEVEL_KEY = "prestige_level";
   private int prestigeLevel = 1;

   public ReachPrestigeLevelTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.REACH_PRESTIGE_LEVEL;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, member -> TensuraStorages.getPlayerDataFrom(member).getResetCounter() >= this.prestigeLevel);
   }

   public boolean checkOnLogin() {
      return true;
   }

   public Component getAltTitle() {
      return Component.translatable("ftbquests.task.stextras.reach_prestige_level")
         .append(": ")
         .append(Component.literal(Integer.toString(this.prestigeLevel)).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putInt("prestige_level", this.prestigeLevel);
   }

   @Override
   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.prestigeLevel = Math.max(1, tag.getInt("prestige_level"));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.prestigeLevel);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.prestigeLevel = Math.max(1, buffer.readVarInt());
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("prestige_level", this.prestigeLevel, value -> this.prestigeLevel = Math.max(1, value), 1, 1, Integer.MAX_VALUE)
         .setNameKey("ftbquests.task.stextras.config.prestige_level");
   }
}
