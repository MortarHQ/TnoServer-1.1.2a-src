package dev.ftb.mods.ftbquests.quest.task;

import dev.architectury.hooks.level.entity.PlayerHooks;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbteams.api.FTBTeamsAPI;
import dev.ftb.mods.ftbteams.api.TeamStagesHelper;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class StageTask extends AbstractBooleanTask {
   private String stage = "";
   private boolean teamStage = false;

   public StageTask(long id, Quest quest) {
      super(id, quest);
   }

   @Override
   public TaskType getType() {
      return TaskTypes.STAGE;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("stage", this.stage);
      if (this.teamStage) {
         nbt.putBoolean("team_stage", true);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.stage = nbt.getString("stage");
      this.teamStage = nbt.getBoolean("team_stage");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.stage, 32767);
      buffer.writeBoolean(this.teamStage);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.stage = buffer.readUtf(32767);
      this.teamStage = buffer.readBoolean();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("stage", this.stage, v -> this.stage = v, "").setNameKey("ftbquests.task.ftbquests.gamestage");
      config.addBool("team_stage", this.teamStage, v -> this.teamStage = v, false);
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.task.ftbquests.gamestage").append(": ").append(Component.literal(this.stage).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   @Override
   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return this.teamStage
         ? FTBTeamsAPI.api().getManager().getTeamByID(teamData.getTeamId()).map(team -> TeamStagesHelper.hasTeamStage(team, this.stage)).orElse(false)
         : StageHelper.INSTANCE.getProvider().has(player, this.stage);
   }

   public static void checkStages(ServerPlayer player) {
      if (ServerQuestFile.INSTANCE != null && !PlayerHooks.isFake(player)) {
         ServerQuestFile.INSTANCE.getTeamData(player).ifPresent(data -> {
            if (!data.isLocked()) {
               ServerQuestFile.INSTANCE.withPlayerContext(player, () -> {
                  for (Task task : ServerQuestFile.INSTANCE.getAllTasks()) {
                     if (task instanceof StageTask && data.canStartTasks(task.getQuest())) {
                        task.submitTask(data, player);
                     }
                  }
               });
            }
         });
      }
   }
}
