package net.blay09.mods.waystones.network.message;

import net.blay09.mods.balm.api.network.SyncConfigMessage;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;

public class SyncWaystonesConfigMessage extends SyncConfigMessage<WaystonesConfigData> {
   public static final Type<SyncWaystonesConfigMessage> TYPE = new Type(ResourceLocation.fromNamespaceAndPath("waystones", "sync_config"));

   public SyncWaystonesConfigMessage(WaystonesConfigData data) {
      super(TYPE, data);
   }
}
