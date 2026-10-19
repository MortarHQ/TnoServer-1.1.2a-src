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

public class LockSkillTask extends AbstractSTExtrasTask {
   private static final String SKILL_ID_KEY = "skill_id";
   private String skillId = "";

   public LockSkillTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return STExtrasTaskTypes.LOCK_SKILL;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.matchesAnyMember(teamData, player, this::hasLockedSkill);
   }

   public boolean checkOnLogin() {
      return true;
   }

   public boolean matchesSkill(ResourceLocation actualSkillId) {
      return matchesConfiguredId(this.skillId, actualSkillId);
   }

   public Component getAltTitle() {
      return this.skillId.isBlank()
         ? Component.translatable("ftbquests.task.stextras.lock_skill")
         : Component.translatable("ftbquests.task.stextras.lock_skill").append(": ").append(Component.literal(this.skillId).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putString("skill_id", this.skillId);
   }

   @Override
   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.skillId = cleanConfiguredId(tag.getString("skill_id"));
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.skillId);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.skillId = cleanConfiguredId(buffer.readUtf());
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("skill_id", this.skillId, value -> this.skillId = cleanConfiguredId(value), "").setNameKey("ftbquests.task.stextras.config.skill_id");
   }

   private boolean hasLockedSkill(ServerPlayer player) {
      return FTBQuestsEventBridge.isSkillLocking(player, this.skillId)
         ? true
         : containsConfiguredId(STExtarsStorage.Player.getLockedSkills(player), this.skillId);
   }
}
