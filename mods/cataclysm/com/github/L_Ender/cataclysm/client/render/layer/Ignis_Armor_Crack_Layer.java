package com.github.L_Ender.cataclysm.client.render.layer;

import com.github.L_Ender.cataclysm.client.model.entity.Ignis_Model;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Renderer;
import com.github.L_Ender.cataclysm.entity.AnimationMonster.BossMonsters.Ignis_Entity;
import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Map;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Ignis_Armor_Crack_Layer extends RenderLayer<Ignis_Entity, Ignis_Model> {
   private static final Map<Ignis_Entity.Crackiness, ResourceLocation> resourceLocations = ImmutableMap.of(
      Ignis_Entity.Crackiness.LOW,
      ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis/ignis_armor_crack1.png"),
      Ignis_Entity.Crackiness.MEDIUM,
      ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis/ignis_armor_crack2.png"),
      Ignis_Entity.Crackiness.HIGH,
      ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/ignis/ignis_armor_crack3.png")
   );

   public Ignis_Armor_Crack_Layer(Ignis_Renderer renderIn) {
      super(renderIn);
   }

   public void render(
      PoseStack matrixStackIn,
      MultiBufferSource bufferIn,
      int packedLightIn,
      Ignis_Entity ignis,
      float p_117152_,
      float p_117153_,
      float p_117154_,
      float p_117155_,
      float p_117156_,
      float p_117157_
   ) {
      if (!ignis.isInvisible() && ignis.getBossPhase() > 0) {
         Ignis_Entity.Crackiness ignis$crackiness = ignis.getCrackiness();
         if (ignis$crackiness != Ignis_Entity.Crackiness.NONE) {
            ResourceLocation resourcelocation = resourceLocations.get(ignis$crackiness);
            renderColoredCutoutModel(this.getParentModel(), resourcelocation, matrixStackIn, bufferIn, packedLightIn, ignis, -1);
         }
      }
   }
}
