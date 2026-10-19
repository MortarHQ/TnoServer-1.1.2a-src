package com.teamresourceful.resourcefullib.common.network.base;

import com.teamresourceful.resourcefullib.common.network.Packet;
import java.util.function.Consumer;
import net.minecraft.world.entity.player.Player;

public interface ServerboundPacketType<T extends Packet<T>> extends PacketType<T> {
   Consumer<Player> handle(T var1);
}
