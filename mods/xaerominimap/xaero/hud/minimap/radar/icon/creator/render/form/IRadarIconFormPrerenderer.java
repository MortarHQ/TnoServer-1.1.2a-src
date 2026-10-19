package xaero.hud.minimap.radar.icon.creator.render.form;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.world.entity.Entity;
import xaero.hud.minimap.radar.icon.creator.RadarIconCreator;
import xaero.hud.minimap.radar.icon.creator.render.trace.ModelRenderTrace;

public interface IRadarIconFormPrerenderer {
   boolean requiresEntityModel();

   boolean isFlipped();

   boolean isOutlined();

   <T extends Entity> boolean prerender(
      GuiGraphics var1,
      EntityRenderer<? super T> var2,
      @Nullable EntityModel<T> var3,
      T var4,
      @Nullable List<ModelRenderTrace> var5,
      RadarIconCreator.Parameters var6
   );
}
