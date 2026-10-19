package net.jadenxgamer.netherexp.client.rendering.entity;

import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.core.entity.ShotgunPellet;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class ShotgunPelletRenderer extends PelletRenderer<ShotgunPellet> {
   public ShotgunPelletRenderer(Context context) {
      super(context);
   }

   public ResourceLocation getTextureLocation(ShotgunPellet entity) {
      return NetherExp.netherexpPath("textures/entity/projectiles/shotgun_pellet.png");
   }
}
