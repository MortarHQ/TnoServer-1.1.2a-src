package io.github.Memoires.mysticism.network;

import io.github.Memoires.mysticism.network.c2s.RequestButcheringEffects;
import io.github.Memoires.mysticism.network.c2s.RequestChangeEnchantmentLevelPacket;
import io.github.Memoires.mysticism.network.c2s.RequestRuptureOnTarget;
import io.github.Memoires.mysticism.network.s2c.OpenButcheringMenuPayload;
import io.github.Memoires.mysticism.network.s2c.OpenCustomStorageMenuPayload;
import io.github.Memoires.mysticism.network.s2c.OpenFlarefrostStorehouseMenuPayload;
import io.github.Memoires.mysticism.network.s2c.OpenProjectileEjectionMenuPayload;
import io.github.Memoires.mysticism.network.s2c.OpenRupturingMenuPayload;
import io.github.Memoires.mysticism.network.s2c.SyncLieModePacket;
import io.github.manasmods.manascore.network.api.util.NetworkUtils;

public class MysticismNetwork {
   public static void init() {
      NetworkUtils.registerC2SPayload(RequestButcheringEffects.TYPE, RequestButcheringEffects.STREAM_CODEC, RequestButcheringEffects::handle);
      NetworkUtils.registerC2SPayload(RequestRuptureOnTarget.TYPE, RequestRuptureOnTarget.STREAM_CODEC, RequestRuptureOnTarget::handle);
      NetworkUtils.registerC2SPayload(
         RequestChangeEnchantmentLevelPacket.TYPE, RequestChangeEnchantmentLevelPacket.STREAM_CODEC, RequestChangeEnchantmentLevelPacket::handle
      );
      NetworkUtils.registerS2CPayload(OpenButcheringMenuPayload.TYPE, OpenButcheringMenuPayload.STREAM_CODEC, OpenButcheringMenuPayload::handle);
      NetworkUtils.registerS2CPayload(
         OpenFlarefrostStorehouseMenuPayload.TYPE, OpenFlarefrostStorehouseMenuPayload.STREAM_CODEC, OpenFlarefrostStorehouseMenuPayload::handle
      );
      NetworkUtils.registerS2CPayload(OpenRupturingMenuPayload.TYPE, OpenRupturingMenuPayload.STREAM_CODEC, OpenRupturingMenuPayload::handle);
      NetworkUtils.registerS2CPayload(
         OpenProjectileEjectionMenuPayload.TYPE, OpenProjectileEjectionMenuPayload.STREAM_CODEC, OpenProjectileEjectionMenuPayload::handle
      );
      NetworkUtils.registerS2CPayload(OpenCustomStorageMenuPayload.TYPE, OpenCustomStorageMenuPayload.STREAM_CODEC, OpenCustomStorageMenuPayload::handle);
      NetworkUtils.registerS2CPayload(SyncLieModePacket.TYPE, SyncLieModePacket.STREAM_CODEC, SyncLieModePacket::handle);
   }
}
