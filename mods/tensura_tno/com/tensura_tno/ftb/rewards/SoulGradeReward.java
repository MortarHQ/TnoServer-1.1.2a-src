package com.tensura_tno.ftb.rewards;

import com.tensura_tno.ftb.STExtrasHelper;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class SoulGradeReward extends Reward {
   public static RewardType SOUL_GRADE_REWARD;
   private int value = 20;

   public SoulGradeReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return SOUL_GRADE_REWARD;
   }

   public void claim(ServerPlayer player, boolean notify) {
      int current = STExtrasHelper.getSoulGrade(player);
      STExtrasHelper.setSoulGrade(player, current + this.value);
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putInt("value", this.value);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.value = nbt.getInt("value");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.value);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.value = buffer.readVarInt();
   }

   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("value", this.value, v -> this.value = v, 20, 1, Integer.MAX_VALUE).setNameKey("tensura_tno.reward.soul_grade.value");
   }
}
