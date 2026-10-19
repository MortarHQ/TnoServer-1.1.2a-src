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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.integration.FTB.Quests.FTBQuestsEventBridge;
import org.crypticdev.stextras.integration.FTB.Quests.STExtrasTaskTypes;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class RacePrestigeTask extends AbstractSTExtrasTask {
   private static final String RACE_ID_KEY = "race_id";
   private String raceId = "";

   public RacePrestigeTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.RACE_PRESTIGE;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, this::hasRacePrestige);
   }

   public boolean checkOnLogin() {
      return true;
   }

   public boolean matchesRace(ResourceLocation actualRaceId) {
      return matchesConfiguredId(this.raceId, actualRaceId);
   }

   public Component getAltTitle() {
      return this.raceId.isBlank()
         ? Component.translatable("ftbquests.task.stextras.race_prestige")
         : Component.translatable("ftbquests.task.stextras.race_prestige").append(": ").append(Component.literal(this.raceId).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putString("race_id", this.raceId);
   }

   @Override
   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.raceId = cleanConfiguredId(tag.getString("race_id"));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.raceId);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.raceId = cleanConfiguredId(buffer.readUtf());
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("race_id", this.raceId, value -> this.raceId = cleanConfiguredId(value), "").setNameKey("ftbquests.task.stextras.config.race_id");
   }

   private boolean hasRacePrestige(ServerPlayer player) {
      return FTBQuestsEventBridge.isRacePrestigeCompleting(player, this.raceId)
         ? true
         : containsConfiguredId(STExtarsStorage.Quests.get(player).getCompletedRaces(), this.raceId);
   }
}
