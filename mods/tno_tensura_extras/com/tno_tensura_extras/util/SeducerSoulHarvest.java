package com.tno_tensura_extras.util;

import io.github.manasmods.manascore.storage.api.StorageHolder;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.race.TensuraRace;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.SubordinateHelper;
import io.github.manasmods.tensura.world.TensuraGameRules;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.GameRules;

public final class SeducerSoulHarvest {
   private SeducerSoulHarvest() {
   }

   public static int captureUnclaimedSouls(ServerPlayer player, LivingEntity target) {
      if (!target.getType().is(TensuraEntityTags.SOUL_DROP_EXCLUDED)
         && !target.getType().is(TensuraEntityTags.EP_DROP_EXCLUDED)
         && !TensuraStorages.getExistenceFrom(target).isSkippingEPDrop()
         && !hasNativeSoulCollector(player)) {
         GameRules rules = player.level().getGameRules();
         return calculateSoulPoints(
            EnergyHelper.getEPGain(target, player, false),
            TensuraRace.BASE_CONFIG.epToSoulRate,
            rules.getInt(TensuraGameRules.MAX_MP_GAIN),
            rules.getInt(TensuraGameRules.MAX_AP_GAIN)
         );
      } else {
         return 0;
      }
   }

   public static void awardUnclaimedSouls(ServerPlayer player, int points) {
      if (points > 0 && !hasNativeSoulCollector(player)) {
         IExistence existence = TensuraStorages.getExistenceFrom(player);
         int previous = existence.getSoulPoints();
         int updated = addSoulPoints(previous, points);
         if (updated != previous) {
            existence.setSoulPoints(updated);
            existence.markDirty();
            if (player instanceof StorageHolder holder) {
               holder.manasCore$sync(player);
            }
         }
      }
   }

   private static boolean hasNativeSoulCollector(ServerPlayer player) {
      LivingEntity owner = SubordinateHelper.getSubordinateOwner(player);
      LivingEntity recipient = (LivingEntity)(owner != null ? owner : player);
      return TensuraStorages.getExistenceFrom(recipient).isDemonLordSeed();
   }

   static int calculateSoulPoints(double ep, int rate, int maxMagiculeGain, int maxAuraGain) {
      if (Double.isFinite(ep) && !(ep <= 0.0) && rate > 0) {
         double cap = Math.max(0.0, (double)maxMagiculeGain + maxAuraGain);
         return (int)Math.min(2.147483647E9, Math.min(ep * rate / 100.0, cap));
      } else {
         return 0;
      }
   }

   static int addSoulPoints(int existing, int gained) {
      return gained <= 0 ? existing : (int)Math.min(2147483647L, (long)existing + gained);
   }
}
