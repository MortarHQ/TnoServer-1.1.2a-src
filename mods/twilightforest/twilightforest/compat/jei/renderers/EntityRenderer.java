package twilightforest.compat.jei.renderers;

import java.util.List;
import mezz.jei.api.gui.builder.ITooltipBuilder;
import mezz.jei.api.ingredients.IIngredientRenderer;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.jei.FakeEntityType;
import twilightforest.util.entities.EntityRenderingUtil;

public class EntityRenderer implements IIngredientRenderer<FakeEntityType> {
   private final int size;

   public EntityRenderer(int size) {
      this.size = size;
   }

   public int getWidth() {
      return this.size;
   }

   public int getHeight() {
      return this.size;
   }

   public void render(GuiGraphics graphics, @Nullable FakeEntityType type) {
      if (type != null) {
         EntityRenderingUtil.renderEntity(graphics, type.type(), this.size);
      }
   }

   public List<Component> getTooltip(FakeEntityType type, TooltipFlag flag) {
      return List.of();
   }

   public void getTooltip(ITooltipBuilder tooltip, FakeEntityType type, TooltipFlag flag) {
      tooltip.addAll(EntityRenderingUtil.getMobTooltip(type.type()));
   }
}
