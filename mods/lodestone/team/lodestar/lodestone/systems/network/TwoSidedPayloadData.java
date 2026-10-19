package team.lodestar.lodestone.systems.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;

public abstract class TwoSidedPayloadData extends LodestoneNetworkPayloadData {
   public abstract void handleClient(IPayloadContext var1);

   public abstract void handleServer(IPayloadContext var1);
}
