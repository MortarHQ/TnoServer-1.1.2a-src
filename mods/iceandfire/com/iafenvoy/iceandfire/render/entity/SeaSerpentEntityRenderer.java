package com.iafenvoy.iceandfire.render.entity;

import com.iafenvoy.iceandfire.IceAndFire;
import com.iafenvoy.iceandfire.data.SeaSerpentType;
import com.iafenvoy.iceandfire.entity.SeaSerpentEntity;
import com.iafenvoy.iceandfire.registry.IafRegistries;
import com.iafenvoy.iceandfire.registry.IafRenderers;
import com.iafenvoy.iceandfire.render.entity.feature.SeaSerpentAncientFeatureRenderer;
import com.iafenvoy.iceandfire.render.model.animator.SeaSerpentTabulaModelAnimator;
import com.iafenvoy.uranus.client.model.AdvancedEntityModel;
import com.iafenvoy.uranus.client.model.util.TabulaModelHandlerHelper;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;

public class SeaSerpentEntityRenderer extends MobRenderer<SeaSerpentEntity, AdvancedEntityModel<SeaSerpentEntity>> {
   public SeaSerpentEntityRenderer(Context context) {
      super(context, TabulaModelHandlerHelper.getModel(IafRenderers.SEA_SERPENT, SeaSerpentTabulaModelAnimator::new), 1.6F);
      this.layers.add(new SeaSerpentAncientFeatureRenderer(this));
   }

   protected void scale(SeaSerpentEntity entity, PoseStack matrixStackIn, float partialTickTime) {
      this.shadowRadius = entity.getSeaSerpentScale();
      matrixStackIn.scale(this.shadowRadius, this.shadowRadius, this.shadowRadius);
   }

   public ResourceLocation getTexture(SeaSerpentEntity serpent) {
      return ((SeaSerpentType)IafRegistries.SEA_SERPENT_TYPE.get(IceAndFire.id(serpent.getVariant()))).getTexture(serpent.isBlinking());
   }
}
