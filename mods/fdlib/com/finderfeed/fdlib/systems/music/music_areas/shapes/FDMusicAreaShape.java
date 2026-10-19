package com.finderfeed.fdlib.systems.music.music_areas.shapes;

import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.phys.Vec3;

public abstract class FDMusicAreaShape {
   public abstract List<ServerPlayer> getPlayersInside(ServerLevel var1, Vec3 var2);
}
