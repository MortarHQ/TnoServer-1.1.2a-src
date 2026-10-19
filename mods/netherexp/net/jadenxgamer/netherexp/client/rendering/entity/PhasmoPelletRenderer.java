package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.PhasmoPellet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class PhasmoPelletRenderer extends PelletRenderer<PhasmoPellet> {
   public PhasmoPelletRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(PhasmoPellet entity) {
      return NetherExp.netherexpPath("textures/entity/projectiles/phasmo_pellet.png");
   }
}
