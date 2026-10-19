package com.tensura_tno.event;

import com.tensura_tno.block.RimuruPlushBlock;
import net.minecraft.world.InteractionHand;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

@EventBusSubscriber(modid = "tensura_tno")
public final class RimuruPlushInteractionEvents {
   private RimuruPlushInteractionEvents() {
   }

   @SubscribeEvent
   public static void onRightClickBlock(RightClickBlock event) {
      if (event.getHand() == InteractionHand.MAIN_HAND
         && event.getEntity().isShiftKeyDown()
         && event.getEntity().mayBuild()
         && !event.getEntity().isSpectator()
         && event.getLevel().getBlockState(event.getPos()).getBlock() instanceof RimuruPlushBlock) {
         if (event.getUseBlock() != TriState.FALSE) {
            event.setUseBlock(TriState.TRUE);
            event.setUseItem(TriState.FALSE);
         }
      }
   }
}
