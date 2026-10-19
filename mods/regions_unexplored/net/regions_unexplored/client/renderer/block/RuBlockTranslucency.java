package net.regions_unexplored.client.renderer.block;

import net.minecraft.client.renderer.RenderType;
import net.regions_unexplored.client.util.RenderUtil;
import net.regions_unexplored.registry.RUBlocks;

public class RuBlockTranslucency {
   public static void init() {
      RenderUtil.putBlocks(
         RenderType.translucent(),
         RUBlocks.PRISMARITE_CLUSTER.get(),
         RUBlocks.LARGE_PRISMARITE_CLUSTER.get(),
         RUBlocks.HANGING_PRISMARITE.get(),
         RUBlocks.PRISMAGLASS.get()
      );
   }
}
