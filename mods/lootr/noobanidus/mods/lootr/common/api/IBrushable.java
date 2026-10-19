package noobanidus.mods.lootr.common.api;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

public interface IBrushable {
   boolean IBrushable$brush(long var1, Player var3, Direction var4);

   void IBrushable$checkReset();
}
