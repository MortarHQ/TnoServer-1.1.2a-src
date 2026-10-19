package dev.ftb.mods.ftbquests.quest.reward;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.net.CustomToastMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ToastReward extends Reward {
   private String description = "";

   public ToastReward(long id, Quest quest) {
      super(id, quest);
   }

   public String getDescription() {
      return this.description;
   }

   @Override
   public RewardType getType() {
      return RewardTypes.TOAST;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("description", this.description);
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.description = nbt.getString("description");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.description, 32767);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.description = buffer.readUtf(32767);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("description", this.description, v -> this.description = v, "");
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      NetworkManager.sendToPlayer(player, new CustomToastMessage(this.getId()));
   }
}
