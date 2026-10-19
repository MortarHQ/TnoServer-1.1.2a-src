package team.creative.creativecore.common.gui.control.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.TooltipFlag.Default;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.client.render.GuiRenderHelper;
import team.creative.creativecore.common.gui.GuiControl;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.display.DisplayColor;
import team.creative.creativecore.common.util.math.geo.Rect;

public abstract class GuiSlotBase extends GuiControl {
   public static final int SLOT_SIZE = 18;
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private static DisplayColor HOVER;
   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   private boolean hovered;

   public GuiSlotBase(String name) {
      super(name);
   }

   @Override
   public void init() {
   }

   @Override
   public void closed() {
   }

   @Override
   public void tick() {
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.SLOT;
   }

   public abstract ItemStack getStack();

   protected abstract ItemStack getStackToRender();

   @Override
   public List<Component> getTooltip() {
      return this.getStack().isEmpty()
         ? super.getTooltip()
         : this.getStack().getTooltipLines(TooltipContext.of(this.provider()), this.getPlayer(), Default.NORMAL);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public boolean isHovered() {
      return this.hovered;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      if (HOVER == null) {
         HOVER = new DisplayColor(1.0F, 1.0F, 1.0F, 0.3F);
      }

      PoseStack pose = graphics.pose();
      pose.translate(1.0F, 1.0F, 10.0F);
      ItemStack stack = this.getStackToRender();
      GuiRenderHelper.drawItemStack(graphics, stack, 1.0F);
      graphics.renderItemDecorations(GuiRenderHelper.getFont(), stack, 0, 0);
      pose.translate(-1.0F, -1.0F, 10.0F);
      if (realRect.inside(mouseX, mouseY) && this.enabled) {
         RenderSystem.enableBlend();
         HOVER.render(graphics, this.rect.getWidth(), this.rect.getHeight());
         this.hovered = true;
      } else {
         this.hovered = false;
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      return true;
   }

   @Override
   public void flowX(int width, int preferred) {
   }

   @Override
   public void flowY(int width, int height, int preferred) {
   }

   @Override
   protected int maxWidth(int availableWidth) {
      return 18;
   }

   @Override
   protected int maxHeight(int width, int availableHeight) {
      return 18;
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return 18;
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return 18;
   }

   @Override
   protected int minWidth(int availableWidth) {
      return 18;
   }

   @Override
   protected int minHeight(int width, int availableHeight) {
      return 18;
   }
}
