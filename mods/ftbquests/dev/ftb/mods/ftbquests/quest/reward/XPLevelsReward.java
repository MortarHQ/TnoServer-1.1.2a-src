package dev.ftb.mods.ftbquests.quest.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbquests.net.NotifyRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class XPLevelsReward extends Reward {
   private int xpLevels;

   public XPLevelsReward(long id, Quest quest, int x) {
      super(id, quest);
      this.xpLevels = x;
   }

   public XPLevelsReward(long id, Quest quest) {
      this(id, quest, 5);
   }

   @Override
   public RewardType getType() {
      return RewardTypes.XP_LEVELS;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putInt("xp_levels", this.xpLevels);
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.xpLevels = nbt.getInt("xp_levels");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.xpLevels);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.xpLevels = buffer.readVarInt();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("xp_levels", this.xpLevels, v -> this.xpLevels = v, 1, 1, Integer.MAX_VALUE).setNameKey("ftbquests.reward.ftbquests.xp_levels");
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      player.giveExperienceLevels(this.xpLevels);
      if (notify) {
         Component text = Component.translatable("ftbquests.reward.ftbquests.xp_levels")
            .append(": ")
            .append(Component.literal("+" + this.xpLevels).withStyle(ChatFormatting.GREEN));
         NetworkManager.sendToPlayer(player, new NotifyRewardMessage(this.id, text, Color4I.empty(), this.disableRewardScreenBlur));
      }
   }

   @OnlyIn(Dist.CLIENT)
   public MutableComponent getAltTitle() {
      return Component.translatable("ftbquests.reward.ftbquests.xp_levels")
         .append(": ")
         .append(Component.literal("+" + this.xpLevels).withStyle(ChatFormatting.GREEN));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public String getButtonText() {
      return "+" + this.xpLevels;
   }
}
