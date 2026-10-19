package twilightforest.compat.emi.widget;

import dev.emi.emi.api.widget.Bounds;
import dev.emi.emi.api.widget.Widget;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import twilightforest.util.entities.EntityRenderingUtil;

public class EmiEntityWidget extends Widget {
   private final EntityType<?> type;
   private final int size;
   private final Bounds bounds;

   public EmiEntityWidget(EntityType<?> type, int x, int y, int size) {
      this.type = type;
      this.size = size;
      this.bounds = new Bounds(x, y, size, size);
   }

   public Bounds getBounds() {
      return this.bounds;
   }

   public List<ClientTooltipComponent> getTooltip(int mouseX, int mouseY) {
      List<ClientTooltipComponent> tooltip = new ArrayList<>();
      EntityRenderingUtil.getMobTooltip(this.type).forEach(component -> tooltip.add(ClientTooltipComponent.create(component.getVisualOrderText())));
      tooltip.add(
         ClientTooltipComponent.create(
            Component.literal(EntityRenderingUtil.getModIdForTooltip(BuiltInRegistries.ENTITY_TYPE.getKey(this.type).getNamespace()))
               .withStyle(new ChatFormatting[]{ChatFormatting.BLUE, ChatFormatting.ITALIC})
               .getVisualOrderText()
         )
      );
      return tooltip;
   }

   public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
      graphics.pose().pushPose();
      graphics.pose().translate(this.bounds.x(), this.bounds.y(), 0.0);
      EntityRenderingUtil.renderEntity(graphics, this.type, this.size);
      graphics.pose().popPose();
   }
}
