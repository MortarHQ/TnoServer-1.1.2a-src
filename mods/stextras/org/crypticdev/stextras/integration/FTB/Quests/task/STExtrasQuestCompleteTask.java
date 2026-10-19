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
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.storage.STExtarsStorage;

public class STExtrasQuestCompleteTask extends AbstractSTExtrasTask {
   private static final String QUEST_ID_KEY = "quest_id";
   private String questId = "";

   public STExtrasQuestCompleteTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.STEXTRAS_QUEST_COMPLETE;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, this::hasCompletedQuest);
   }

   public boolean checkOnLogin() {
      return true;
   }

   public boolean matchesQuest(ResourceLocation actualQuestId) {
      return matchesConfiguredId(this.questId, actualQuestId);
   }

   public Component getAltTitle() {
      return this.questId.isBlank()
         ? Component.translatable("ftbquests.task.stextras.stextras_quest_complete")
         : Component.translatable("ftbquests.task.stextras.stextras_quest_complete")
            .append(": ")
            .append(this.resolveQuestDisplayName().copy().withStyle(ChatFormatting.YELLOW));
   }

   private Component resolveQuestDisplayName() {
      ResourceLocation id = ResourceLocation.tryParse(this.questId);
      if (id == null) {
         return Component.literal(this.questId);
      }

      QuestDefinition quest = QuestRegistry.INSTANCE.byIdClient(id);
      return quest != null && quest.name != null && !quest.name.isBlank() ? Component.literal(quest.name) : Component.literal(this.questId);
   }

   @Override
   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putString("quest_id", this.questId);
   }

   @Override
   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.questId = cleanConfiguredId(tag.getString("quest_id"));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.questId);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.questId = cleanConfiguredId(buffer.readUtf());
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("quest_id", this.questId, value -> this.questId = cleanConfiguredId(value), "").setNameKey("ftbquests.task.stextras.config.quest_id");
   }

   private boolean hasCompletedQuest(ServerPlayer player) {
      return FTBQuestsEventBridge.isQuestCompleting(player, this.questId) ? true : containsConfiguredId(STExtarsStorage.Quests.completed(player), this.questId);
   }
}
