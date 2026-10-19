package team.creative.creativecore.common.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.ArrayUtils;
import team.creative.creativecore.common.gui.event.GuiControlChangedEvent;
import team.creative.creativecore.common.gui.event.GuiControlClickEvent;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.event.GuiEventManager;
import team.creative.creativecore.common.gui.event.GuiTooltipEvent;
import team.creative.creativecore.common.gui.flow.GuiFlow;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.type.itr.ConsecutiveIterator;

public class GuiParent extends GuiControl implements IGuiParent, Iterable<GuiControl> {
   private GuiEventManager eventManager;
   protected List<GuiControl> controls = new CopyOnWriteArrayList<>();
   protected List<GuiControl> hoverControls = new CopyOnWriteArrayList<>();
   public GuiFlow flow;
   public Align align = Align.LEFT;
   public VAlign valign = VAlign.TOP;
   public int spacing = 2;
   private double scale = 1.0;
   private double scaleInv = 1.0;

   private static GuiControl get(String name, List<GuiControl> collection) {
      for (int i = 0; i < collection.size(); i++) {
         GuiControl control = collection.get(i);
         if (control.name.equalsIgnoreCase(name)) {
            return control;
         }

         if (control instanceof GuiParent parent) {
            if (control.name.isBlank()) {
               GuiControl result = parent.get(name);
               if (result != null) {
                  return result;
               }
            } else if (name.startsWith(control.name + ".")) {
               return parent.get(name.substring(control.name.length() + 1));
            }
         }
      }

      return null;
   }

   public GuiParent(String name, GuiFlow flow) {
      super(name);
      this.flow = flow;
   }

   public GuiParent(String name, GuiFlow flow, VAlign valign) {
      this(name, flow, Align.LEFT, valign);
   }

   public GuiParent(String name, GuiFlow flow, Align align) {
      this(name, flow, align, VAlign.TOP);
   }

   public GuiParent(String name, GuiFlow flow, Align align, VAlign valign) {
      this(name, flow);
      this.align = align;
      this.valign = valign;
   }

   public GuiParent(String name) {
      this(name, GuiFlow.STACK_X);
   }

   public GuiParent() {
      this("");
   }

   public GuiParent(GuiFlow flow) {
      this("", flow);
   }

   @Override
   public boolean isClient() {
      return this.getParent().isClient();
   }

   public GuiParent setScale(double scale) {
      this.scale = scale;
      this.scaleInv = 1.0 / scale;
      return this;
   }

   public final double scaleFactor() {
      return this.scale;
   }

   public final double scaleFactorInv() {
      return this.scaleInv;
   }

   public double getOffsetY() {
      return 0.0;
   }

   public double getOffsetX() {
      return 0.0;
   }

   public GuiParent setAlign(Align align) {
      this.align = align;
      return this;
   }

   public GuiParent setVAlign(VAlign valign) {
      this.valign = valign;
      return this;
   }

   public GuiParent setSpacing(int spacing) {
      this.spacing = spacing;
      return this;
   }

   public GuiParent setFlow(GuiFlow flow) {
      this.flow = flow;
      return this;
   }

   @Override
   public boolean isExpandableX() {
      if (super.isExpandableX()) {
         return true;
      }

      for (GuiControl control : this.controls) {
         if (control.isExpandableX()) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean isExpandableY() {
      if (super.isExpandableY()) {
         return true;
      }

      for (GuiControl control : this.controls) {
         if (control.isExpandableY()) {
            return true;
         }
      }

      return false;
   }

   public <T extends GuiControl> T get(String name) {
      if (name.isBlank()) {
         return (T)this;
      }

      GuiControl result = get(name, this.controls);
      return (T)(result != null ? result : get(name, this.hoverControls));
   }

   public <T extends GuiControl> T get(String name, Class<T> clazz) {
      T result = this.get(name);
      return clazz.isInstance(result) ? result : null;
   }

   public boolean has(String name) {
      return this.get(name) != null;
   }

   public <T extends GuiControl> T insertControlBefore(GuiControl reference, T toInsert) {
      int index = this.controls.indexOf(reference);
      if (index == -1) {
         return null;
      }

      toInsert.setParent(this);
      this.controls.add(index, toInsert);
      return toInsert;
   }

   public <T extends GuiControl> T insertControlAfter(GuiControl reference, T toInsert) {
      int index = this.controls.indexOf(reference);
      if (index == -1) {
         return null;
      }

      toInsert.setParent(this);
      if (++index == this.controls.size()) {
         this.controls.add(toInsert);
      } else {
         this.controls.add(index, toInsert);
      }

      return toInsert;
   }

   public GuiParent add(GuiControl control) {
      control.setParent(this);
      this.controls.add(control);
      return this;
   }

   public GuiParent add(GuiControl... controls) {
      for (GuiControl c : controls) {
         this.add(c);
      }

      return this;
   }

   public GuiParent add(boolean conditional, Supplier<GuiControl> controlSupplier) {
      return conditional ? this.add(controlSupplier.get()) : this;
   }

   public GuiParent addHover(GuiControl control) {
      control.setParent(this);
      this.hoverControls.add(control);
      return this;
   }

   public GuiParent addHover(GuiControl... controls) {
      for (GuiControl c : controls) {
         this.addHover(c);
      }

      return this;
   }

   public GuiParent addHover(boolean conditional, Supplier<GuiControl> controlSupplier) {
      return conditional ? this.addHover(controlSupplier.get()) : this;
   }

   public boolean remove(GuiControl control) {
      return this.controls.remove(control) || this.hoverControls.remove(control);
   }

   public boolean replace(GuiControl oldControl, GuiControl newControl) {
      for (int i = 0; i < this.controls.size(); i++) {
         if (this.controls.get(i) == oldControl) {
            newControl.setParent(this);
            this.controls.set(i, newControl);
            return true;
         }
      }

      for (int i = 0; i < this.hoverControls.size(); i++) {
         if (this.hoverControls.get(i) == oldControl) {
            this.hoverControls.set(i, newControl);
            return true;
         }
      }

      return false;
   }

   public void remove(String... include) {
      this.controls.removeIf(x -> ArrayUtils.contains(include, x.name));
      this.hoverControls.removeIf(x -> ArrayUtils.contains(include, x.name));
   }

   public void removeExclude(String... exclude) {
      this.controls.removeIf(x -> !ArrayUtils.contains(exclude, x.name));
      this.hoverControls.removeIf(x -> !ArrayUtils.contains(exclude, x.name));
   }

   public boolean isEmpty() {
      return this.controls.isEmpty() && this.hoverControls.isEmpty();
   }

   public void clear() {
      this.controls.clear();
      this.hoverControls.clear();
   }

   public int size() {
      return this.controls.size() + this.hoverControls.size();
   }

   @Override
   public Iterator<GuiControl> iterator() {
      return this.hoverControls.isEmpty() ? this.controls.iterator() : new ConsecutiveIterator<>(this.hoverControls, this.controls);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderControls(
      GuiGraphics graphics,
      Rect contentRect,
      Rect realContentRect,
      int mouseX,
      int mouseY,
      ListIterator<GuiControl> collection,
      double scale,
      double xOffset,
      double yOffset,
      boolean hover
   ) {
      PoseStack pose = graphics.pose();

      while (collection.hasPrevious()) {
         GuiControl control = collection.previous();
         if (control.visible) {
            Rect controlContentRect = control.createChildRect(contentRect, scale, xOffset, yOffset);
            Rect realRect = realContentRect.intersection(controlContentRect);
            if (realRect != null || hover) {
               if (hover) {
                  RenderSystem.disableScissor();
               } else {
                  realRect.scissor();
               }

               pose.pushPose();
               pose.translate(control.rect.getX() + xOffset, control.rect.getY() + yOffset, 10.0);
               this.renderControl(graphics, control, controlContentRect, realRect, scale, mouseX, mouseY, hover);
               pose.popPose();
            }
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderControl(
      GuiGraphics graphics, GuiControl control, Rect controlContentRect, Rect realRect, double scale, int mouseX, int mouseY, boolean hover
   ) {
      control.render(graphics, controlContentRect, hover ? controlContentRect : realRect, scale, mouseX, mouseY);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, Rect contentRect, Rect realContentRect, double scale, int mouseX, int mouseY) {
      if (realContentRect != null) {
         PoseStack pose = graphics.pose();
         float controlScale = (float)this.scaleFactor();
         scale *= this.scaleFactor();
         double xOffset = this.getOffsetX();
         double yOffset = this.getOffsetY();
         pose.scale(controlScale, controlScale, 1.0F);
         this.renderControls(
            graphics, contentRect, realContentRect, mouseX, mouseY, this.controls.listIterator(this.controls.size()), scale, xOffset, yOffset, false
         );
         this.renderControls(
            graphics, contentRect, realContentRect, mouseX, mouseY, this.hoverControls.listIterator(this.hoverControls.size()), scale, xOffset, yOffset, true
         );
         super.renderContent(graphics, contentRect, realContentRect, scale, mouseX, mouseY);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   @Override
   protected void renderContent(GuiGraphics graphics, int mouseX, int mouseY) {
   }

   @Override
   public boolean isContainer() {
      return this.getParent().isContainer();
   }

   @Override
   public void init() {
      for (GuiControl control : this) {
         control.init();
      }
   }

   @Override
   public void closed() {
      for (GuiControl control : this) {
         control.closed();
      }
   }

   @Override
   public void tick() {
      for (GuiControl control : this) {
         control.tick();
      }
   }

   @Override
   public void closeTopLayer() {
      this.getParent().closeTopLayer();
   }

   public void closeThisLayer() {
      this.getParent().closeLayer(this.getLayer());
   }

   @Override
   public void closeLayer(GuiLayer layer) {
      this.getParent().closeLayer(layer);
   }

   public boolean isMouseOverHovered(double x, double y) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this.hoverControls) {
         if (control.rect.inside(x, y)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public Rect toLayerRect(GuiControl control, Rect rect) {
      rect.move(control.rect.getX() + this.getOffsetX() + this.getContentOffset(), control.rect.getY() + this.getOffsetY() + this.getContentOffset());
      rect.scale(this.scaleFactor());
      return this.getParent().toLayerRect(this, rect);
   }

   @Override
   public Rect toScreenRect(GuiControl control, Rect rect) {
      rect.move(control.rect.getX() + this.getOffsetX() + this.getContentOffset(), control.rect.getY() + this.getOffsetY() + this.getContentOffset());
      rect.scale(this.scaleFactor());
      return this.getParent().toScreenRect(this, rect);
   }

   @Override
   public GuiTooltipEvent getTooltipEvent(double x, double y) {
      GuiTooltipEvent event = super.getTooltipEvent(x, y);
      if (event != null) {
         return event;
      }

      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable() && control.rect.inside(x, y)) {
            event = control.getTooltipEvent(x - control.rect.getX(), y - control.rect.getY());
            if (event != null) {
               return event;
            }
         }
      }

      return null;
   }

   @Override
   public boolean testForDoubleClick(double x, double y, int button) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable() && control.rect.inside(x, y) && control.testForDoubleClick(x - control.rect.getX(), y - control.rect.getY(), button)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void mouseMoved(double x, double y) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable()) {
            control.mouseMoved(x - control.rect.getX(), y - control.rect.getY());
         }
      }
   }

   @Override
   public boolean mouseClicked(double x, double y, int button) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;
      boolean result = false;

      for (GuiControl control : this) {
         if (!result && control.isInteractable() && control.rect.inside(x, y) && control.mouseClicked(x - control.rect.getX(), y - control.rect.getY(), button)
            )
          {
            this.raiseEvent(new GuiControlClickEvent(control, button, false));
            result = true;
         } else {
            control.looseFocus();
         }
      }

      return result;
   }

   @Override
   public boolean mouseDoubleClicked(double x, double y, int button) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;
      boolean result = false;

      for (GuiControl control : this) {
         if (!result
            && control.isInteractable()
            && control.rect.inside(x, y)
            && control.mouseDoubleClicked(x - control.rect.getX(), y - control.rect.getY(), button)) {
            this.raiseEvent(new GuiControlClickEvent(control, button, false));
            result = true;
         } else {
            control.looseFocus();
         }
      }

      return result;
   }

   @Override
   public void mouseReleased(double x, double y, int button) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable()) {
            control.mouseReleased(x - control.rect.getX(), y - control.rect.getY(), button);
         }
      }
   }

   @Override
   public void mouseDragged(double x, double y, int button, double dragX, double dragY, double time) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable()) {
            control.mouseDragged(x - control.rect.getX(), y - control.rect.getY(), button, dragX, dragY, time);
         }
      }
   }

   @Override
   public boolean mouseScrolled(double x, double y, double delta) {
      x *= this.scaleFactorInv();
      y *= this.scaleFactorInv();
      int offset = this.getContentOffset();
      x += -this.getOffsetX() - offset;
      y += -this.getOffsetY() - offset;

      for (GuiControl control : this) {
         if (control.isInteractable() && control.rect.inside(x, y) && control.mouseScrolled(x - control.rect.getX(), y - control.rect.getY(), delta)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      for (GuiControl control : this) {
         if (control.isInteractable() && control.keyPressed(keyCode, scanCode, modifiers)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      for (GuiControl control : this) {
         if (control.isInteractable() && control.keyReleased(keyCode, scanCode, modifiers)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public boolean charTyped(char codePoint, int modifiers) {
      for (GuiControl control : this) {
         if (control.isInteractable() && control.charTyped(codePoint, modifiers)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void looseFocus() {
      for (GuiControl control : this) {
         control.looseFocus();
      }
   }

   @Override
   public void raiseEvent(GuiEvent event) {
      if (this.getParent() != null) {
         if (this.eventManager != null) {
            this.eventManager.raiseEvent(event);
         }

         if (!event.isCanceled()) {
            this.getParent().raiseEvent(event);
         }
      }
   }

   public void registerEventClick(Consumer<GuiControlClickEvent> consumer) {
      this.registerEvent(GuiControlClickEvent.class, consumer);
   }

   public void registerEventChanged(Consumer<GuiControlChangedEvent> consumer) {
      this.registerEvent(GuiControlChangedEvent.class, consumer);
   }

   public <T extends GuiEvent> void registerEvent(Class<T> clazz, Consumer<T> action) {
      if (this.eventManager == null) {
         this.eventManager = new GuiEventManager();
      }

      this.eventManager.registerEvent(clazz, action);
   }

   public void clearEvents() {
      if (this.eventManager != null) {
         this.eventManager.clear();
      }
   }

   @Override
   public String getNestedName() {
      if (this.name.isBlank()) {
         return this.getParent() instanceof GuiControl ? ((GuiControl)this.getParent()).getNestedName() : "";
      } else {
         return super.getNestedName();
      }
   }

   @Override
   public void flowX(int width, int preferred) {
      this.flow.flowX(this.controls.stream().map(x -> x.rect).toList(), this.spacing, this.align, width, preferred, this.endlessX());
   }

   @Override
   public void flowY(int width, int height, int preferred) {
      this.flow.flowY(this.controls.stream().map(x -> x.rect).toList(), this.spacing, this.valign, width, height, preferred, this.endlessY());
   }

   protected boolean endlessX() {
      return false;
   }

   protected boolean endlessY() {
      return false;
   }

   @Override
   protected int minWidth(int availableWidth) {
      return Mth.ceil(this.flow.minWidth(this.controls.stream().map(x -> x.rect).toList(), this.spacing, availableWidth) * this.scale);
   }

   @Override
   protected int preferredWidth(int availableWidth) {
      return Mth.ceil(this.flow.preferredWidth(this.controls.stream().map(x -> x.rect).toList(), this.spacing, availableWidth) * this.scale);
   }

   @Override
   protected int minHeight(int width, int availableHeight) {
      return Mth.ceil(this.flow.minHeight(this.controls.stream().map(x -> x.rect).toList(), this.spacing, width, availableHeight) * this.scale);
   }

   @Override
   protected int preferredHeight(int width, int availableHeight) {
      return Mth.ceil(this.flow.preferredHeight(this.controls.stream().map(x -> x.rect).toList(), this.spacing, width, availableHeight) * this.scale);
   }

   @Override
   public ControlFormatting getControlFormatting() {
      return ControlFormatting.TRANSPARENT;
   }
}
