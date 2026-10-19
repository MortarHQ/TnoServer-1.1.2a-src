package dev.ftb.mods.ftbultimine.rightclick;

import dev.ftb.mods.ftbultimine.FTBUltiminePlayerData;
import dev.ftb.mods.ftbultimine.api.rightclick.RegisterRightClickHandlerEvent;
import dev.ftb.mods.ftbultimine.api.rightclick.RightClickHandler;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.world.InteractionHand;

public enum RightClickDispatcher implements RegisterRightClickHandlerEvent.Dispatcher {
   INSTANCE;

   private final List<RightClickHandler> handlers = new CopyOnWriteArrayList<>();

   public static RightClickDispatcher getInstance() {
      return INSTANCE;
   }

   public int dispatchRightClick(ShapeContext shapeContext, InteractionHand hand, FTBUltiminePlayerData playerData) {
      for (RightClickHandler handler : this.handlers) {
         int nBlocks = handler.handleRightClickBlock(shapeContext, hand, playerData.cachedPositions());
         if (nBlocks > 0) {
            return nBlocks;
         }
      }

      return 0;
   }

   @Override
   public void registerHandler(RightClickHandler handler) {
      this.handlers.add(handler);
   }

   public void clear() {
      this.handlers.clear();
   }
}
