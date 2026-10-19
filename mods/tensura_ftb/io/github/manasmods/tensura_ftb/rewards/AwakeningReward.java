package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.tensura.race.RaceHelper;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AwakeningReward extends Reward {
   public static RewardType AWAKENING;
   private AwakeningReward.AwakeningStatus status = AwakeningReward.AwakeningStatus.RACE_EVOLVE;

   public AwakeningReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return AWAKENING;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("status", this.status.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.status = AwakeningReward.AwakeningStatus.valueOf(nbt.getString("status"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeEnum(this.status);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.status = (AwakeningReward.AwakeningStatus)buffer.readEnum(AwakeningReward.AwakeningStatus.class);
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("status", this.status.toString(), v -> this.status = AwakeningReward.AwakeningStatus.valueOf(v), "RACE_EVOLVE")
         .setNameKey("tensura_ftb.task.ability.status");
   }

   public void claim(ServerPlayer player, boolean notify) {
      if (this.status == AwakeningReward.AwakeningStatus.RACE_EVOLVE) {
         RaceHelper.evolveRace(player, true);
      } else {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         switch (this.status) {
            case DEMON_LORD:
               existence.setTrueDemonLord(true);
               RaceHelper.awakening(player, false);
               break;
            case DEMON_LORD_SEED:
               existence.setDemonLordSeed(true);
               break;
            case MAJIN:
               if (existence.getAlignment().equals(Alignment.DEFAULT)) {
                  existence.setAlignment(Alignment.MAJIN);
               }
               break;
            case CHAOS:
               existence.setAlignment(Alignment.CHAOS);
               break;
            case HERO:
               existence.setTrueHero(true);
               RaceHelper.awakening(player, true);
               break;
            case HERO_EGG:
               existence.setHeroEgg(true);
               break;
            case BLESSED:
               existence.setBlessed(true);
         }

         existence.markDirty();
      }
   }

   public enum AwakeningStatus {
      RACE_EVOLVE,
      DEMON_LORD,
      DEMON_LORD_SEED,
      MAJIN,
      CHAOS,
      HERO,
      HERO_EGG,
      BLESSED;
   }
}
