package noobanidus.mods.lootr.common.integration.jade;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import noobanidus.mods.lootr.common.api.LootrTags;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class LootrWailaPlugin implements IWailaPlugin {
   public void registerClient(IWailaClientRegistration registration) {
      registration.addRayTraceCallback((hitResult, accessor, originalAccessor) -> {
         if (accessor instanceof BlockAccessor blockAccessor) {
            BlockState newState;
            if (blockAccessor.getBlockState().is(LootrTags.Blocks.SANDS)) {
               newState = Blocks.SAND.defaultBlockState();
            } else {
               if (!blockAccessor.getBlockState().is(LootrTags.Blocks.GRAVELS)) {
                  return accessor;
               }

               newState = Blocks.GRAVEL.defaultBlockState();
            }

            return registration.blockAccessor().from(blockAccessor).blockState(newState).build();
         } else {
            return accessor;
         }
      });
   }
}
