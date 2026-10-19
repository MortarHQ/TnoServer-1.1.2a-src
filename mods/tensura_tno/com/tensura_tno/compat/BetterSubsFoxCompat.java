package com.tensura_tno.compat;

import dev.architectury.networking.NetworkManager;
import io.github.manasmods.manascore.storage.api.Storage;
import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.manascore.storage.api.StorageKey;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.fml.ModList;

public final class BetterSubsFoxCompat {
   private BetterSubsFoxCompat() {
   }

   public static boolean tryOpenSubordinateMainMenu(ServerPlayer player, int targetEntityId) {
      if (!ModList.get().isLoaded("tensura_better_subs")) {
         return false;
      }

      try {
         Class<?> payloadClass = Class.forName("com.trbeyond.tensura_better_subs.network.s2c.OpenHumanoidSkillMenuPayload");
         Object payload = payloadClass.getConstructor(int.class).newInstance(targetEntityId);
         NetworkManager.sendToPlayer(player, (CustomPacketPayload)payload);
         return true;
      } catch (Throwable ignored) {
         return false;
      }
   }

   public static void saveExpandedSkillStorage(LivingEntity fox, CompoundTag statsRoot) {
      if (ModList.get().isLoaded("tensura_better_subs")) {
         try {
            Class<?> essClass = Class.forName("com.trbeyond.tensura_better_subs.entity.ExpandedSkillStorage");
            StorageKey<? extends Storage> key = (StorageKey<? extends Storage>)essClass.getMethod("getKey").invoke(null);
            Storage storage = ((StorageHolder)fox).manasCore$getStorage(key);
            if (storage != null) {
               CompoundTag bett = new CompoundTag();
               storage.save(bett);
               statsRoot.put("TnoBettExpanded", bett);
            }
         } catch (Throwable var6) {
         }
      }
   }

   public static void loadExpandedSkillStorage(LivingEntity fox, CompoundTag statsRoot) {
      if (statsRoot.contains("TnoBettExpanded", 10)) {
         if (ModList.get().isLoaded("tensura_better_subs")) {
            try {
               Class<?> essClass = Class.forName("com.trbeyond.tensura_better_subs.entity.ExpandedSkillStorage");
               StorageKey<? extends Storage> key = (StorageKey<? extends Storage>)essClass.getMethod("getKey").invoke(null);
               Storage storage = ((StorageHolder)fox).manasCore$getStorage(key);
               if (storage != null) {
                  storage.load(statsRoot.getCompound("TnoBettExpanded"));
               }
            } catch (Throwable var5) {
            }
         }
      }
   }
}
