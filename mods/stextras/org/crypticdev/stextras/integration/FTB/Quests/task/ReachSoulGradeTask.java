package org.crypticdev.stextras.integration.FTB.Quests.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasTaskTypes;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class ReachSoulGradeTask extends AbstractSTExtrasTask {
   private static final String SOUL_GRADE_LEVEL_KEY = "soul_grade_level";
   private int soulGradeLevel = 1;

   public ReachSoulGradeTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.REACH_SOUL_GRADE;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, member -> STExtarsStorage.Player.getSoulGradeLevel(member) >= this.soulGradeLevel);
   }

   public boolean checkOnLogin() {
      return true;
   }

   public Component getAltTitle() {
      return Component.translatable("ftbquests.task.stextras.reach_soul_grade")
         .append(": ")
         .append(Component.literal(Integer.toString(this.soulGradeLevel)).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putInt("soul_grade_level", this.soulGradeLevel);
   }

   @Override
   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.soulGradeLevel = Math.max(1, tag.getInt("soul_grade_level"));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.soulGradeLevel);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.soulGradeLevel = Math.max(1, buffer.readVarInt());
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("soul_grade_level", this.soulGradeLevel, value -> this.soulGradeLevel = Math.max(1, value), 1, 1, 6)
         .setNameKey("ftbquests.task.stextras.config.soul_grade_level");
   }
}
