package twilightforest.client.renderer.entity;

import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import twilightforest.client.model.TFModelLayers;
import twilightforest.client.model.entity.MosquitoSwarmModel;
import twilightforest.entity.monster.MosquitoSwarm;

public class MosquitoSwarmRenderer extends TFGenericMobRenderer<MosquitoSwarm, MosquitoSwarmModel> {
   public MosquitoSwarmRenderer(Context context) {
      super(context, new MosquitoSwarmModel(context.bakeLayer(TFModelLayers.MOSQUITO_SWARM)), 0.0F, "mosquitoswarm.png");
   }

   protected float getFlipDegrees(MosquitoSwarm entity) {
      return 0.0F;
   }
}
