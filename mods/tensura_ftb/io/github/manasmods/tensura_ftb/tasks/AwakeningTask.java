package io.github.manasmods.tensura_ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.storage.Alignment;
import io.github.manasmods.tensura.storage.TensuraStorages;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AwakeningTask extends AbstractBooleanTask {
   public static TaskType AWAKENING;
   private AwakeningTask.AwakeningStatus status = AwakeningTask.AwakeningStatus.ANY;

   public AwakeningTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return AWAKENING;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("status", this.status.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.status = AwakeningTask.AwakeningStatus.valueOf(nbt.getString("status"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeEnum(this.status);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.status = (AwakeningTask.AwakeningStatus)buffer.readEnum(AwakeningTask.AwakeningStatus.class);
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("status", this.status.toString(), v -> this.status = AwakeningTask.AwakeningStatus.valueOf(v), "ANY");
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return switch (this.status) {
         case ANY -> TensuraStorages.getExistenceFrom(player).isTrueDemonLord() || TensuraStorages.getExistenceFrom(player).isTrueHero();
         case DEMON_LORD -> TensuraStorages.getExistenceFrom(player).isTrueDemonLord();
         case DEMON_LORD_SEED -> TensuraStorages.getExistenceFrom(player).isDemonLordSeed();
         case MAJIN -> TensuraStorages.getExistenceFrom(player).getAlignment().equals(Alignment.MAJIN);
         case CHAOS -> TensuraStorages.getExistenceFrom(player).getAlignment().equals(Alignment.CHAOS);
         case HERO -> TensuraStorages.getExistenceFrom(player).isTrueHero();
         case HERO_EGG -> TensuraStorages.getExistenceFrom(player).isHeroEgg();
         case BLESSED -> TensuraStorages.getExistenceFrom(player).isBlessed();
         case FINAL_RACE -> {
            Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(player).getRace();
            yield optional.isEmpty() ? false : optional.get().getRace().getNextEvolutions(optional.get(), player).isEmpty();
         }
      };
   }

   public enum AwakeningStatus {
      ANY,
      DEMON_LORD,
      DEMON_LORD_SEED,
      MAJIN,
      CHAOS,
      HERO,
      HERO_EGG,
      BLESSED,
      FINAL_RACE;
   }
}
