package dev.ftb.mods.ftbultimine.rightclick;

import dev.ftb.mods.ftbultimine.api.rightclick.RightClickHandler;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import dev.ftb.mods.ftbultimine.api.util.ItemCollector;
import dev.ftb.mods.ftbultimine.config.FTBUltimineServerConfig;
import dev.ftb.mods.ftbultimine.crops.CropLikeRegistry;
import dev.ftb.mods.ftbultimine.shape.BlockMatchers;
import java.util.Collection;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.commons.lang3.mutable.MutableInt;

public enum CropHarvesting implements RightClickHandler {
   INSTANCE;

   @Override
   public int handleRightClickBlock(ShapeContext shapeContext, InteractionHand hand, Collection<BlockPos> positions) {
      ServerPlayer player = shapeContext.player();
      if ((Boolean)FTBUltimineServerConfig.RIGHT_CLICK_HARVESTING.get() && shapeContext.matcher() == BlockMatchers.MATCH_BY_CROP_TYPE) {
         MutableInt clicked = new MutableInt();
         ItemCollector itemCollector = new ItemCollector();

         for (BlockPos pos : positions) {
            BlockState state = player.level().getBlockState(pos);
            CropLikeRegistry.INSTANCE.getHandlerFor(player.level(), pos, state).ifPresent(handler -> {
               handler.doHarvesting(player, pos, state, itemCollector);
               clicked.increment();
            });
         }

         itemCollector.drop(player.level(), shapeContext.face() == null ? shapeContext.origPos() : shapeContext.origPos().relative(shapeContext.face()));
         return clicked.intValue();
      } else {
         return 0;
      }
   }
}
