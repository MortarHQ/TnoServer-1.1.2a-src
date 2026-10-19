package noobanidus.mods.lootr.neoforge.network;

import net.neoforged.neoforge.network.handling.IPayloadContext;
import noobanidus.mods.lootr.common.api.network.ILootrPacket;

public interface ILootrNeoForgePacket extends ILootrPacket {
   void handle(IPayloadContext var1);
}
