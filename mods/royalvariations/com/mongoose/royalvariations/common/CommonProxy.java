package com.mongoose.royalvariations.common;

import com.mongoose.royalvariations.init.RVProxy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class CommonProxy implements RVProxy {
   @Nullable
   @Override
   public Player getPlayer() {
      return null;
   }

   @Nullable
   @Override
   public Level getLevel() {
      return null;
   }
}
