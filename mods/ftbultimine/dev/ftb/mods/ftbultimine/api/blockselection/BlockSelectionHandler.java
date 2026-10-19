package dev.ftb.mods.ftbultimine.api.blockselection;

import dev.ftb.mods.ftblibrary.util.OptionalBoolean;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public interface BlockSelectionHandler {
   BlockSelectionHandler.Result customSelectionCheck(Player var1, BlockPos var2, BlockPos var3, BlockState var4, BlockState var5);

   enum Result {
      TRUE,
      FALSE,
      PASS;

      public static BlockSelectionHandler.Result of(boolean b) {
         return b ? TRUE : FALSE;
      }

      public OptionalBoolean asBoolean() {
         return switch (this) {
            case TRUE -> OptionalBoolean.TRUE;
            case FALSE -> OptionalBoolean.FALSE;
            default -> OptionalBoolean.EMPTY;
         };
      }
   }
}
