package dev.ftb.mods.ftbquests.quest.reward;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.integration.stages.StageHelper;
import dev.ftb.mods.ftbquests.quest.Quest;
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

public class StageReward extends Reward {
   private String stage = "";
   private boolean remove = false;

   public StageReward(long id, Quest quest, String stage) {
      super(id, quest);
      this.stage = stage;
      this.autoclaim = RewardAutoClaim.INVISIBLE;
   }

   public StageReward(long id, Quest quest) {
      this(id, quest, "");
   }

   @Override
   public RewardType getType() {
      return RewardTypes.STAGE;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("stage", this.stage);
      if (this.remove) {
         nbt.putBoolean("remove", true);
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.stage = nbt.getString("stage");
      this.remove = nbt.getBoolean("remove");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.stage, 32767);
      buffer.writeBoolean(this.remove);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.stage = buffer.readUtf(32767);
      this.remove = buffer.readBoolean();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("stage", this.stage, v -> this.stage = v, "").setNameKey("ftbquests.reward.ftbquests.gamestage");
      config.addBool("remove", this.remove, v -> this.remove = v, false);
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      if (this.remove) {
         if (this.isTeamReward()) {
            FTBTeamsAPI.api().getManager().getTeamForPlayer(player).ifPresent(team -> TeamStagesHelper.removeTeamStage(team, this.stage));
         } else {
            StageHelper.INSTANCE.getProvider().remove(player, this.stage);
         }
      } else if (this.isTeamReward()) {
         FTBTeamsAPI.api().getManager().getTeamForPlayer(player).ifPresent(team -> TeamStagesHelper.addTeamStage(team, this.stage));
      } else {
         StageHelper.INSTANCE.getProvider().add(player, this.stage);
      }

      if (notify) {
         String key = (this.remove ? "removed" : "added") + (this.isTeamReward() ? "_team" : "");
         player.sendSystemMessage(Component.translatable("ftbquests.reward.ftbquests.gamestage." + key, new Object[]{this.stage}), true);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.reward.ftbquests.gamestage").append(": ").append(Component.literal(this.stage).withStyle(ChatFormatting.YELLOW));
   }

   @Override
   public boolean ignoreRewardBlocking() {
      return true;
   }

   @Override
   protected boolean isIgnoreRewardBlockingHardcoded() {
      return true;
   }
}
