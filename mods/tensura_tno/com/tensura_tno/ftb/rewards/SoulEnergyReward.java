package com.tensura_tno.ftb.rewards;

import com.tensura_tno.TensuraTNOMod;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import java.lang.reflect.Method;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;

public class SoulEnergyReward extends Reward {
   public static RewardType SOUL_ENERGY_REWARD;
   private int value = 100000;

   public SoulEnergyReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
      return SOUL_ENERGY_REWARD;
   }

   public void claim(ServerPlayer player, boolean notify) {
      if (!SoulEnergyReward.SoulEnergyAccess.add(player, this.value)) {
         TensuraTNOMod.LOGGER.warn("[TNO] Failed to apply Mysticism soul energy reward to {}", player.getGameProfile().getName());
      }
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
      config.addInt("value", this.value, v -> this.value = v, 100000, 1, Integer.MAX_VALUE).setNameKey("tensura_tno.reward.soul_energy.value");
   }

   private static final class SoulEnergyAccess {
      private static Method getSoulEnergyStorage;
      private static Method getSoulEnergy;
      private static Method setSoulEnergy;
      private static Method markDirty;
      private static boolean initialized;
      private static boolean available;

      private static boolean add(LivingEntity entity, int amount) {
         if (!init()) {
            return false;
         }

         try {
            Object storage = getSoulEnergyStorage.invoke(null, entity);
            if (storage == null) {
               return false;
            }

            double current = ((Number)getSoulEnergy.invoke(storage)).doubleValue();
            setSoulEnergy.invoke(storage, current + amount);
            if (markDirty != null) {
               markDirty.invoke(storage);
            }

            return true;
         } catch (ReflectiveOperationException | RuntimeException e) {
            TensuraTNOMod.LOGGER.warn("[TNO] Mysticism soul energy reward reflection failed: {}", e.toString());
            return false;
         }
      }

      private static boolean init() {
         if (initialized) {
            return available;
         }

         initialized = true;

         try {
            Class<?> storages = Class.forName("io.github.Memoires.mysticism.storage.MysticismStorages");
            Class<?> soulEnergy = Class.forName("io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy");
            getSoulEnergyStorage = storages.getMethod("getSoulEnergyFrom", LivingEntity.class);
            getSoulEnergy = soulEnergy.getMethod("getSoulEnergy");
            setSoulEnergy = soulEnergy.getMethod("setSoulEnergy", double.class);

            try {
               Class<?> storage = Class.forName("io.github.manasmods.manascore.storage.api.Storage");
               markDirty = storage.getMethod("markDirty");
            } catch (ReflectiveOperationException ignored) {
               markDirty = null;
            }

            available = true;
         } catch (ReflectiveOperationException e) {
            TensuraTNOMod.LOGGER.warn("[TNO] Mysticism soul energy storage unavailable: {}", e.toString());
            available = false;
         }

         return available;
      }
   }
}
