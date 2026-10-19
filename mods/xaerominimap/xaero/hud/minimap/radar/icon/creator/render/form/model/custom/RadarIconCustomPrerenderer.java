package xaero.hud.minimap.radar.icon.creator.render.form.model.custom;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.radar.icon.creator.render.form.model.part.RadarIconModelPartPrerenderer;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;
import xaero.hud.minimap.radar.icon.definition.form.model.config.RadarIconModelConfig;

public abstract class RadarIconCustomPrerenderer {
   public abstract <T extends Entity> ModelPart render(
      PoseStack var1,
      BufferSource var2,
      EntityRenderer<? super T> var3,
      T var4,
      EntityModel<T> var5,
      RadarIconModelPartPrerenderer var6,
      List<ModelPart> var7,
      ModelPart var8,
      RadarIconModelConfig var9,
      ModelRenderTrace var10
   );
}
