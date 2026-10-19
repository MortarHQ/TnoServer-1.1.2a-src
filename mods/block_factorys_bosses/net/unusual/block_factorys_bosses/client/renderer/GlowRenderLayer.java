package net.unusual.block_factorys_bosses.client.renderer;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.Entity;

@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class GlowRenderLayer<EntityT extends Entity, ModelT extends EntityModel<EntityT>> extends EyesLayer<EntityT, ModelT> {
   private final RenderType renderType;

   public GlowRenderLayer(RenderLayerParent<EntityT, ModelT> renderer, RenderType renderType) {
      super(renderer);
      this.renderType = renderType;
   }

   public RenderType renderType() {
      return this.renderType;
   }
}
