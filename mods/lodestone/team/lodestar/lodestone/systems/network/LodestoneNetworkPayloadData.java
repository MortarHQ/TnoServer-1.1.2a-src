package team.lodestar.lodestone.systems.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.registry.common.LodestoneNetworkPayloads;

public abstract class LodestoneNetworkPayloadData implements CustomPacketPayload {
   @NotNull
   public Type<? extends CustomPacketPayload> type() {
      return LodestoneNetworkPayloads.PayloadRegistryHelper.PAYLOAD_TO_TYPE.get(this.getClass());
   }

   public abstract void serialize(FriendlyByteBuf var1);
}
