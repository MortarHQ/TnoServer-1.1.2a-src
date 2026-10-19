package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.storage.player.ITensuraPlayer;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import io.github.manasmods.tensura_ftb.tasks.ExistenceTask;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ExistenceReward extends Reward {
   public static RewardType EXISTENCE;
   private int value;
   private ExistenceTask.ExistenceType existence = ExistenceTask.ExistenceType.MAX_EP;

   public ExistenceReward(long id, Quest quest, int xp) {
      super(id, quest);
      this.value = xp;
   }

   public ExistenceReward(long id, Quest quest) {
      this(id, quest, 1);
   }

   public RewardType getType() {
      return EXISTENCE;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putInt("value", this.value);
      nbt.putString("existence", this.existence.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.value = nbt.getInt("value");
      this.existence = ExistenceTask.ExistenceType.valueOf(nbt.getString("existence"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarInt(this.value);
      buffer.writeEnum(this.existence);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.value = buffer.readVarInt();
      this.existence = (ExistenceTask.ExistenceType)buffer.readEnum(ExistenceTask.ExistenceType.class);
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addInt("value", this.value, v -> this.value = v, 1000, Integer.MIN_VALUE, Integer.MAX_VALUE).setNameKey("tensura_ftb.task.existence_value.value");
      config.addString("existence", this.existence.toString(), v -> this.existence = ExistenceTask.ExistenceType.valueOf(v), "MAX_EP")
         .setNameKey("tensura_ftb.task.existence_value.type");
   }

   public void claim(ServerPlayer player, boolean notify) {
      switch (this.existence) {
         case EP:
            EnergyHelper.gainMagicule(player, this.value / 2.0, GainType.NORMAL);
            EnergyHelper.gainAura(player, this.value / 2.0, GainType.NORMAL);
            break;
         case MAGICULE:
            EnergyHelper.gainMagicule(player, this.value, GainType.NORMAL);
            break;
         case AURA:
            EnergyHelper.gainAura(player, this.value, GainType.NORMAL);
            break;
         case MAX_EP:
            EnergyHelper.gainMagicule(player, this.value / 2.0, GainType.MAX);
            EnergyHelper.gainAura(player, this.value / 2.0, GainType.MAX);
            break;
         case MAX_MAGICULE:
            EnergyHelper.gainMagicule(player, this.value, GainType.MAX);
            break;
         case MAX_AURA:
            EnergyHelper.gainAura(player, this.value, GainType.MAX);
            break;
         case SOUL: {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            existence.setSoulPoints(existence.getSoulPoints() + this.value * 1000);
            existence.markDirty();
            break;
         }
         case HUMAN_KILL: {
            IExistence existence = TensuraStorages.getExistenceFrom(player);
            existence.setHumanKill(existence.getHumanKill() + this.value);
            existence.markDirty();
            break;
         }
         case RESET_COUNTER: {
            ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
            playerData.setResetCounter(playerData.getResetCounter() + this.value);
            playerData.markDirty();
            break;
         }
         case REPUTATION: {
            ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
            playerData.setReputation(HumanEntityTypes.DWARF.getId(), playerData.getReputation(HumanEntityTypes.DWARF.getId()) + this.value);
            playerData.markDirty();
            break;
         }
         case BONUS_SKILL_LOCK: {
            ITensuraPlayer playerData = TensuraStorages.getPlayerDataFrom(player);
            playerData.setBonusSkillLock(playerData.getBonusSkillLock() + this.value);
            playerData.markDirty();
         }
      }

      if (notify) {
         player.playNotifySound((SoundEvent)TensuraSoundEvents.ENERGY_DRAIN.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
      }
   }

   @OnlyIn(Dist.CLIENT)
   public String getButtonText() {
      return "+" + this.value;
   }
}
