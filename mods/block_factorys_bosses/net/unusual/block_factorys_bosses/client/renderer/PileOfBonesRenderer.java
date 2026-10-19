package net.unusual.block_factorys_bosses.client.renderer;

import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.resources.ResourceLocation;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.ModelCustomModel;
import net.unusual.block_factorys_bosses.entity.decoration.PileOfBonesEntity;
import net.unusual.block_factorys_bosses.procedures.PileOfBonesIsEntityModelShakingProcedure;

@ParametersAreNonnullByDefault
@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PileOfBonesRenderer extends MobRenderer<PileOfBonesEntity, ModelCustomModel<PileOfBonesEntity>> {
   private static final ResourceLocation TEXTURE_LOCATION = BossesRise.prefix("textures/entities/skeleton.png");

   public PileOfBonesRenderer(Context context) {
      super(context, new ModelCustomModel(context.bakeLayer(ModelCustomModel.LAYER_LOCATION)), 0.0F);
   }

   public ResourceLocation getTextureLocation(PileOfBonesEntity entity) {
      return TEXTURE_LOCATION;
   }

   protected boolean isShaking(PileOfBonesEntity entity) {
      return PileOfBonesIsEntityModelShakingProcedure.execute(entity);
   }
}
