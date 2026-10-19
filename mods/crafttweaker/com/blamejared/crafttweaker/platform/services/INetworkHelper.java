package com.blamejared.crafttweaker.platform.services;

import com.blamejared.crafttweaker.impl.network.packet.CraftTweakerPacket;
import net.minecraft.server.level.ServerPlayer;

public interface INetworkHelper {
   <T extends CraftTweakerPacket> void sendPacket(ServerPlayer var1, T var2);
}
