package org.crypticdev.stextras.integration.FTB.Quests.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftbquests.net.NotifyRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.Stextras;
import org.crypticdev.stextras.net.STNetworkGuards;

public abstract class AbstractAmountReward extends Reward {
   private static final String AMOUNT_KEY = "amount";
   protected int amount;

   protected AbstractAmountReward(long id, Quest quest, int defaultAmount) {
      super(id, quest);
      this.amount = defaultAmount;
   }

   protected abstract String getTranslationKey();

   public void writeData(CompoundTag tag, Provider provider) {
      super.writeData(tag, provider);
      tag.putInt("amount", this.amount);
   }

   public void readData(CompoundTag tag, Provider provider) {
      super.readData(tag, provider);
      this.amount = Math.max(0, tag.getInt("amount"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.amount);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.amount = Math.max(0, buffer.readVarInt());
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("amount", this.amount, value -> this.amount = Math.max(0, value), 1, 0, Integer.MAX_VALUE)
         .setNameKey("ftbquests.reward.stextras.config.amount");
   }

   public MutableComponent getAltTitle() {
      return Component.translatable(this.getTranslationKey()).append(": ").append(Component.literal("+" + this.amount).withStyle(ChatFormatting.GREEN));
   }

   public String getButtonText() {
      return "+" + this.amount;
   }

   protected void notifyPlayer(ServerPlayer player, boolean notify) {
      if (notify) {
         if (!STNetworkGuards.canSendTo(player)) {
            Stextras.LOGGER
               .warn("[NETWORK_GUARD] Skipped reward notification because player cannot receive packets | player={}", STNetworkGuards.playerName(player));
         } else {
            NetworkManager.sendToPlayer(player, new NotifyRewardMessage(this.id, this.getAltTitle(), Color4I.empty(), this.disableRewardScreenBlur));
         }
      }
   }
}
