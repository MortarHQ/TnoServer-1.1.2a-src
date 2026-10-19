package dev.ftb.mods.ftbultimine.shape;

import dev.ftb.mods.ftbultimine.api.FTBUltimineAPI;
import dev.ftb.mods.ftbultimine.api.shape.Shape;
import dev.ftb.mods.ftbultimine.api.shape.ShapeContext;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class LargeTunnelShape implements Shape {
   private static final ResourceLocation ID = FTBUltimineAPI.id("large_tunnel");

   @Override
   public ResourceLocation getName() {
      return ID;
   }

   @Override
   public List<BlockPos> getBlocks(ShapeContext context) {
      List<BlockPos> list = new ArrayList<>(9);
      BlockPos basePos = context.origPos();
      list.add(basePos);

      for (int depth = 0; depth < this.maxDepth() && list.size() < context.maxBlocks(); depth++) {
         int size = list.size();

         label53:
         for (int a = -1; a <= 1; a++) {
            for (int b = -1; b <= 1; b++) {
               if (depth > 0 || a != 0 || b != 0) {
                  BlockPos pos = switch (context.face().getAxis()) {
                     case X -> basePos.offset(0, a, b);
                     case Y -> basePos.offset(a, 0, b);
                     case Z -> basePos.offset(a, b, 0);
                     default -> throw new MatchException(null, null);
                  };
                  if (context.check(pos)) {
                     list.add(pos);
                     if (list.size() >= context.maxBlocks()) {
                        break label53;
                     }
                  }
               }
            }
         }

         if (list.size() == size) {
            break;
         }

         basePos = basePos.relative(context.face().getOpposite());
      }

      return list;
   }

   protected int maxDepth() {
      return Integer.MAX_VALUE;
   }
}
