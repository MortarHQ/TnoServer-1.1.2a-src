package dev.ftb.mods.ftbultimine.api.shape;

import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import dev.ftb.mods.ftbultimine.api.blockselection.BlockSelectionHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

public record ShapeContext(ServerPlayer player, BlockPos origPos, Direction face, BlockState origState, ShapeContext.Matcher matcher, int maxBlocks) {
   public BlockState getBlockState(BlockPos pos) {
      return this.player.level().getBlockState(pos);
   }

   @Deprecated
   BlockPos pos() {
      return this.origPos;
   }

   public boolean check(BlockPos pos) {
      BlockState state = this.getBlockState(pos);
      BlockSelectionHandler.Result res = FTBUltimineAPI.api().customSelectionCheck(this.player, this.origPos, pos, this.origState, state);
      return res.asBoolean().orElse(this.check(state));
   }

   @Internal
   public boolean check(BlockState state) {
      return this.matcher.check(this.origState, state);
   }

   @FunctionalInterface
   @NonExtendable
   public interface Matcher {
      boolean check(BlockState var1, BlockState var2);
   }
}
