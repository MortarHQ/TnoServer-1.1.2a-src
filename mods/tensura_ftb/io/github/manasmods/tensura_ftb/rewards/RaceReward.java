package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.RaceAPI;
import io.github.manasmods.manascore.race.api.Races;
import io.github.manasmods.tensura.registry.race.TensuraRaces;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class RaceReward extends Reward {
   public static RewardType RACE;
   private ResourceLocation race = TensuraRaces.HUMAN.getId();

   public RaceReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
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
      config.addString("race", this.race.toString(), v -> this.race = ResourceLocation.tryParse(v), TensuraRaces.HUMAN.getId().toString())
         .setNameKey("tensura_ftb.task.race.race");
   }

   public void claim(ServerPlayer player, boolean notify) {
      ManasRace manasRace = (ManasRace)RaceAPI.getRaceRegistry().get(this.race);
      if (manasRace != null) {
         Races races = RaceAPI.getRaceFrom(player);
         races.setRace(manasRace, false);
         races.markDirty();
      }
   }
}
