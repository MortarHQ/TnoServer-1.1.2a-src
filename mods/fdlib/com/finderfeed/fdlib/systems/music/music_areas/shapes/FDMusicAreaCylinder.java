package com.finderfeed.fdlib.systems.music.music_areas.shapes;

import com.finderfeed.fdlib.util.FDTargetFinder;
import java.util.List;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.phys.Vec3;

public class FDMusicAreaCylinder extends FDMusicAreaShape {
   private float radius;
   private float height;

   public FDMusicAreaCylinder(float radius, float height) {
      this.radius = radius;
      this.height = height;
   }

   @Override
   public List<ServerPlayer> getPlayersInside(ServerLevel level, Vec3 position) {
      PlayerList playerList = level.getServer().getPlayerList();
      return playerList.getPlayers()
         .stream()
         .filter(
            player -> FDTargetFinder.isPointInCylinder(player.position(), position, this.height, this.radius)
               && player.level().dimension().equals(level.dimension())
         )
         .toList();
   }
}
