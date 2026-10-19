package com.iafenvoy.iceandfire.render;

import com.iafenvoy.iceandfire.registry.IafBlocks;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientTickEvent.Client;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class PortalRenderHelper {
   private static int TICK = 0;

   public static int getTick() {
      return TICK;
   }

   public static void init() {
      ClientTickEvent.CLIENT_POST.register((Client)client -> {
         Level world = client.level;
         Player player = client.player;
         if (world != null && player != null) {
            if (world.getBlockState(player.blockPosition()).is((Block)IafBlocks.DREAD_PORTAL.get())) {
               TICK++;
            } else if (TICK > 0) {
               TICK--;
            }
         }
      });
   }
}
