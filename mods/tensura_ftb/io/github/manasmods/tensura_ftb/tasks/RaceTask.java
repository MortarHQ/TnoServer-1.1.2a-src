package io.github.manasmods.tensura_ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class RaceTask extends AbstractBooleanTask {
   public static TaskType RACE;
   private ResourceLocation race = TensuraRaces.HUMAN.getId();

   public RaceTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return RACE;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("race", this.race.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.race = ResourceLocation.tryParse(nbt.getString("race"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.race);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.race = buffer.readResourceLocation();
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("race", this.race.toString(), v -> this.race = ResourceLocation.tryParse(v), TensuraRaces.HUMAN.getId().toString());
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      ManasRace manasRace = (ManasRace)RaceAPI.getRaceRegistry().get(this.race);
      if (manasRace == null) {
         return false;
      }

      Optional<ManasRaceInstance> optional = RaceAPI.getRaceFrom(player).getRace();
      return optional.<Boolean>map(instance -> instance.getRace().equals(manasRace)).orElse(false);
   }
}
