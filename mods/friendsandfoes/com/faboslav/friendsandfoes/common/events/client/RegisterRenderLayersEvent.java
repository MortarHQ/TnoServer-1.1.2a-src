package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.BiConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public record RegisterRenderLayersEvent(BiConsumer<Fluid, RenderType> fluid, BiConsumer<Block, RenderType> block) {
   public static final EventHandler<RegisterRenderLayersEvent> EVENT = new EventHandler<>();

   public void register(RenderType layer, Fluid... fluids) {
      for (Fluid fluid : fluids) {
         this.fluid.accept(fluid, layer);
      }
   }

   public void register(RenderType layer, Block... blocks) {
      for (Block block : blocks) {
         this.block.accept(block, layer);
      }
   }
}
