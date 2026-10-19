package dev.ftb.mods.ftbquests.quest.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.integration.currency.CurrencyHelper;
import dev.ftb.mods.ftblibrary.integration.currency.CurrencyProvider;
import dev.ftb.mods.ftbquests.net.NotifyRewardMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class CurrencyReward extends Reward {
   private int coinAmount;

   public CurrencyReward(long id, Quest q) {
      this(id, q, 1);
   }

   public CurrencyReward(long id, Quest q, int coinAmount) {
      super(id, q);
      this.coinAmount = coinAmount;
   }

   public int getCoinAmount() {
      return this.coinAmount;
   }

   @Override
   public RewardType getType() {
      return RewardTypes.CURRENCY;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putInt("amount", this.coinAmount);
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.coinAmount = nbt.getInt("amount");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.coinAmount);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.coinAmount = buffer.readVarInt();
   }

   @Override
   public Component getAltTitle() {
      return Component.empty().append(Component.literal("⬤ ").withStyle(ChatFormatting.YELLOW)).append(String.valueOf(this.coinAmount));
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("coins", this.coinAmount, v -> this.coinAmount = v, 100, 1, Integer.MAX_VALUE).setNameKey("ftbquests.reward.ftbquests.currency");
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      CurrencyProvider provider = CurrencyHelper.getInstance().getProvider();
      if (provider.isValidProvider()) {
         provider.giveCurrency(player, this.coinAmount);
         if (notify) {
            Component msg = Component.literal(Integer.toString(this.coinAmount)).append(" ").append(provider.coinName(this.coinAmount > 1));
            NetworkManager.sendToPlayer(player, new NotifyRewardMessage(this.id, msg, Icons.MONEY, this.disableRewardScreenBlur));
         }
      }
   }
}
