package com.mongoose.royalvariations.init;

import javax.annotation.Nullable;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public interface RVProxy {
   @Nullable
   Player getPlayer();

   @Nullable
   Level getLevel();
}
