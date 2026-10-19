package com.playernbtftb.tasks;

import com.playernbtftb.PlayerNbtFtbMod;
import com.playernbtftb.config.PlayerNbtConfig;
import com.playernbtftb.utils.NBTUtils;
import dev.ftb.mods.ftblibrary.config.BooleanConfig;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.IntConfig;
import dev.ftb.mods.ftblibrary.config.NBTConfig;
import dev.ftb.mods.ftblibrary.ui.Button;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class PlayerNbtTask extends Task {
   private static final Set<String> WARNED_KEYS = new HashSet<>();
   public CompoundTag nbt = new CompoundTag();
   public int checkInterval = 60;
   public boolean exactMatch = false;

   public PlayerNbtTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return ModTaskTypes.PLAYER_NBT;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.put("nbt", this.nbt);
      nbt.putInt("checkInterval", this.checkInterval);
      nbt.putBoolean("exactMatch", this.exactMatch);
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.nbt = nbt.getCompound("nbt");
      this.checkInterval = nbt.getInt("checkInterval");
      if (this.checkInterval <= 0) {
         this.checkInterval = 60;
      }

      this.exactMatch = nbt.getBoolean("exactMatch");
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeNbt(this.nbt);
      buffer.writeInt(this.checkInterval);
      buffer.writeBoolean(this.exactMatch);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.nbt = buffer.readNbt();
      this.checkInterval = buffer.readInt();
      this.exactMatch = buffer.readBoolean();
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.add("nbt", new NBTConfig(), this.nbt, v -> this.nbt = v, new CompoundTag());
      config.add("checkInterval", new IntConfig(1, 1200), this.checkInterval, v -> this.checkInterval = v, 60);
      config.add("exactMatch", new BooleanConfig(), this.exactMatch, v -> this.exactMatch = v, false);
   }

   @OnlyIn(Dist.CLIENT)
   public void onButtonClicked(Button button, boolean canClick) {
   }

   public int autoSubmitOnPlayerTick() {
      return this.checkInterval > 0 ? this.checkInterval : (Integer)PlayerNbtConfig.CHECK_INTERVAL_TICKS.get();
   }

   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      if (!teamData.isCompleted(this)) {
         if ((Boolean)PlayerNbtConfig.ENABLE_DEBUG_LOGGING.get()) {
            PlayerNbtFtbMod.LOGGER.info("Checking PlayerNBT for player: {} with interval: {} ticks", player.getName().getString(), this.checkInterval);
         }

         boolean matches;
         if (!this.exactMatch && !(Boolean)PlayerNbtConfig.EXACT_MATCH_MODE.get()) {
            CompoundTag playerNbt = buildTargetedNbt(player, this.nbt);
            matches = NBTUtils.compoundEquals(this.nbt, playerNbt);
         } else {
            CompoundTag playerNbt = player.saveWithoutId(new CompoundTag());
            matches = this.nbt.equals(playerNbt);
         }

         if (matches) {
            teamData.addProgress(this, 1L);
            if ((Boolean)PlayerNbtConfig.ENABLE_DEBUG_LOGGING.get()) {
               PlayerNbtFtbMod.LOGGER.info("PlayerNBT task completed for player: {}", player.getName().getString());
            }
         }
      }
   }

   private static CompoundTag buildTargetedNbt(ServerPlayer player, CompoundTag requiredNbt) {
      CompoundTag result = new CompoundTag();
      CompoundTag fullNbt = null;

      for (String key : requiredNbt.getAllKeys()) {
         switch (key) {
            case "XpLevel":
               result.putInt("XpLevel", player.experienceLevel);
               break;
            case "XpP":
               result.putFloat("XpP", player.experienceProgress);
               break;
            case "XpTotal":
               result.putInt("XpTotal", player.totalExperience);
               break;
            case "Score":
               result.putInt("Score", player.getScore());
               break;
            case "Health":
               result.putFloat("Health", player.getHealth());
               break;
            case "FoodLevel":
               result.putInt("FoodLevel", player.getFoodData().getFoodLevel());
               break;
            case "FoodSaturationLevel":
               result.putFloat("FoodSaturationLevel", player.getFoodData().getSaturationLevel());
               break;
            case "SelectedItemSlot":
               result.putInt("SelectedItemSlot", player.getInventory().selected);
               break;
            case "sleeping":
               result.putBoolean("sleeping", player.isSleeping());
               break;
            case "abilities":
               player.getAbilities().addSaveData(result);
               break;
            case "Inventory":
               result.put("Inventory", player.getInventory().save(new ListTag()));
               break;
            default:
               if (fullNbt == null) {
                  if (WARNED_KEYS.add(key)) {
                     PlayerNbtFtbMod.LOGGER
                        .warn("PlayerNBT task uses unoptimized NBT key '{}'. Full player serialization required; use supported keys to avoid lag.", key);
                  }

                  fullNbt = player.saveWithoutId(new CompoundTag());
               }

               if (fullNbt.contains(key)) {
                  result.put(key, fullNbt.get(key));
               }
         }
      }

      return result;
   }
}
