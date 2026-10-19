package xaero.hud.minimap.radar.icon.creator.render.form.model.custom;

import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.radar.icon.creator.render.form.model.part.RadarIconModelPartPrerenderer;

public abstract class CustomModelIconCustomPrenderer extends RenderTypeIconCustomPrerenderer {
   @Override
   protected List<ModelPart> getRenderedDest(List<ModelPart> rendered) {
      return rendered;
   }

   @Override
   protected abstract <T extends Entity> Iterable<ModelPart> getModelParts(
      RadarIconModelPartPrerenderer var1, List<ModelPart> var2, T var3, EntityModel<T> var4
   );
}
