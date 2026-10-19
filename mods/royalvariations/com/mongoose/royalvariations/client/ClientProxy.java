package com.mongoose.royalvariations.client;

import com.mongoose.royalvariations.init.RVProxy;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class ClientProxy implements RVProxy {
   @Nullable
   @Override
   public Player getPlayer() {
      return Minecraft.getInstance().player;
   }

   @Nullable
   @Override
   public Level getLevel() {
      return Minecraft.getInstance().level;
   }
}
