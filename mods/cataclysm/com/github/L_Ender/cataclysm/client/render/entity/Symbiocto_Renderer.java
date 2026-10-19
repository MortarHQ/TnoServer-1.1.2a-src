package com.github.L_Ender.cataclysm.client.render.entity;

import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.entity.Symbiocto_Model;
import com.github.L_Ender.cataclysm.entity.InternalAnimationMonster.AcropolisMonsters.Symbiocto_Entity;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Symbiocto_Renderer extends MobRenderer<Symbiocto_Entity, Symbiocto_Model> {
   private static final ResourceLocation OPEN = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/sea/symbiocto_open.png");
   private static final ResourceLocation CLOSE = ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/sea/symbiocto_close.png");

   public Symbiocto_Renderer(Context renderManagerIn) {
      super(renderManagerIn, new Symbiocto_Model(renderManagerIn.bakeLayer(CMModelLayers.OCTOSITE_MODEL)), 0.25F);
   }

   public ResourceLocation getTextureLocation(Symbiocto_Entity entity) {
      return entity.isCloseEye() ? CLOSE : OPEN;
   }
}
