package team.creative.creativecore.common.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import java.util.List;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder.Reference;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.CreativeCore;
import team.creative.creativecore.common.gui.event.GuiEvent;
import team.creative.creativecore.common.gui.event.GuiTooltipEvent;
import team.creative.creativecore.common.gui.flow.GuiSizeRule;
import team.creative.creativecore.common.gui.integration.IGuiIntegratedParent;
import team.creative.creativecore.common.gui.style.ControlFormatting;
import team.creative.creativecore.common.gui.style.GuiStyle;
import team.creative.creativecore.common.gui.style.display.StyleDisplay;
import team.creative.creativecore.common.util.math.geo.Rect;
import team.creative.creativecore.common.util.mc.LanguageUtils;
import team.creative.creativecore.common.util.text.TextBuilder;

public abstract class GuiControl {
   public final GuiControlRect rect = new GuiControlRect(this);
   private IGuiParent parent;
   public final String name;
   public boolean enabled = true;
   public GuiSizeRule preferred;
   public boolean expandableX = false;
   public boolean expandableY = false;
   public boolean visible = true;
   private List<Component> customTooltip;

   public GuiControl(String name) {
      this.name = name;
   }

   public Provider provider() {
      Player player = this.getPlayer();
      return player != null ? player.registryAccess() : null;
   }

   public boolean isClient() {
      return this.parent != null ? this.parent.isClient() : CreativeCore.loader().getEffectiveSide().isClient();
   }

   public GuiControl setTooltip(List<Component> tooltip) {
      if (tooltip != null && tooltip.isEmpty()) {
         this.customTooltip = null;
      } else {
         this.customTooltip = tooltip;
      }

      return this;
   }

   public GuiControl setTooltip(String translate) {
      this.setTooltip(new TextBuilder().translate(translate).build());
      return this;
   }

   public GuiControl setVisible(boolean visible) {
      this.visible = visible;
      return this;
   }

   public GuiControl setFixed() {
      this.expandableX = false;
      this.expandableY = false;
      return this;
   }

   public GuiControl setFixedX() {
      this.expandableX = false;
      return this;
   }

   public GuiControl setFixedY() {
      this.expandableY = false;
      return this;
   }

   public GuiControl setExpandable() {
      this.expandableX = true;
      this.expandableY = true;
      return this;
   }

   public GuiControl setUnexpandable() {
      this.expandableX = false;
      this.expandableY = false;
      return this;
   }

   public GuiControl setExpandableX() {
      this.expandableX = true;
      return this;
   }

   public GuiControl setUnexpandableX() {
      this.expandableX = false;
      return this;
   }

   public GuiControl setExpandableY() {
      this.expandableY = true;
      return this;
   }

   public GuiControl setUnexpandableY() {
      this.expandableY = false;
      return this;
   }

   public GuiControl setDim(int width, int height) {
      this.preferred = new GuiSizeRule.GuiFixedDimension(width, height);
      return this;
   }

   public GuiControl setDim(GuiSizeRule dim) {
      this.preferred = dim;
      return this;
   }

   public GuiControl setEnabled(boolean enabled) {
      this.enabled = enabled;
      return this;
   }

   public boolean hasGui() {
      return this.parent != null ? this.parent.hasGui() : false;
   }

   public void setParent(IGuiParent parent) {
      this.parent = parent;
   }

   public boolean isParent(IGuiParent parent) {
      return this.parent == parent ? true : this.parent.isParent(parent);
   }

   public IGuiParent getParent() {
      return this.parent;
   }

   public IGuiIntegratedParent getIntegratedParent() {
      return this.parent.getIntegratedParent();
   }

   public boolean isExpandableX() {
      return this.expandableX;
   }

   public boolean isExpandableY() {
      return this.expandableY;
   }

   public String getNestedName() {
      return this.getParent() instanceof GuiControl control ? control.getNestedName() + "." + this.name : this.name;
   }

   public boolean hasLayer() {
      return this.parent instanceof GuiControl control ? control.hasLayer() : false;
   }

   public GuiLayer getLayer() {
      if (this.parent instanceof GuiControl control) {
         return control.getLayer();
      } else {
         throw new RuntimeException("Invalid layer control");
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public GuiStyle getStyle() {
      if (this.parent instanceof GuiControl control) {
         return control.getStyle();
      } else {
         throw new RuntimeException("Invalid layer control");
      }
   }

   public abstract void init();

   public abstract void closed();

   public abstract void tick();

   public boolean is(String name) {
      return this.name.equalsIgnoreCase(name);
   }

   public boolean is(String... name) {
      for (String s : name) {
         if (this.name.equalsIgnoreCase(s)) {
            return true;
         }
      }

      return false;
   }

   public Rect createChildRect(Rect contentRect, double scale, double xOffset, double yOffset) {
      return contentRect.child(this.rect, scale, xOffset, yOffset);
   }

   protected abstract void flowX(int var1, int var2);

   protected abstract void flowY(int var1, int var2, int var3);

   public void reflow() {
      if (this.parent != null) {
         this.parent.reflow();
      }
   }

   protected int minWidth(int availableWidth) {
      return -1;
   }

   protected abstract int preferredWidth(int var1);

   protected int maxWidth(int availableWidth) {
      return -1;
   }

   protected int minHeight(int width, int availableHeight) {
      return -1;
   }

   protected abstract int preferredHeight(int var1, int var2);

   protected int maxHeight(int width, int availableHeight) {
      return -1;
   }

   public Rect toLayerRect(Rect rect) {
      return this.getParent().toLayerRect(this, rect);
   }

   public Rect toScreenRect(Rect rect) {
      return this.getParent().toScreenRect(this, rect);
   }

   public boolean testForDoubleClick(double x, double y, int button) {
      return false;
   }

   public boolean isInteractable() {
      return this.enabled && this.visible;
   }

   public void mouseMoved(double x, double y) {
   }

   public boolean mouseClicked(double x, double y, int button) {
      return false;
   }

   public boolean mouseDoubleClicked(double x, double y, int button) {
      return this.mouseClicked(x, y, button);
   }

   public void mouseReleased(double x, double y, int button) {
   }

   public void mouseDragged(double x, double y, int button, double dragX, double dragY, double time) {
   }

   public boolean mouseScrolled(double x, double y, double delta) {
      return false;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
      return false;
   }

   public boolean charTyped(char codePoint, int modifiers) {
      return false;
   }

   public void looseFocus() {
   }

   public void raiseEvent(GuiEvent event) {
      if (this.parent != null) {
         this.parent.raiseEvent(event);
      }
   }

   public abstract ControlFormatting getControlFormatting();

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public int getContentOffset() {
      return this.getStyle().getContentOffset(this.getControlFormatting());
   }

   public GuiTooltipEvent getTooltipEvent(double x, double y) {
      List<Component> toolTip = this.getTooltip();
      if (this.customTooltip != null) {
         if (toolTip == null) {
            toolTip = this.customTooltip;
         } else if (toolTip != this.customTooltip) {
            toolTip.addAll(this.customTooltip);
         }
      }

      if (toolTip == null) {
         String langTooltip = translateOrDefault(this.getNestedName() + ".tooltip", null);
         if (langTooltip != null) {
            toolTip = new TextBuilder(langTooltip).build();
         }
      }

      return toolTip != null ? new GuiTooltipEvent(this, toolTip) : null;
   }

   public List<Component> getTooltip() {
      return this.customTooltip;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public StyleDisplay getBorder(GuiStyle style, StyleDisplay display) {
      return display;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public StyleDisplay getBackground(GuiStyle style, StyleDisplay display) {
      return display;
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public void render(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      RenderSystem.clear(256, Minecraft.ON_OSX);
      Rect rectCopy = null;
      if (!this.enabled) {
         rectCopy = controlRect.copy();
      }

      int width = this.rect.getWidth();
      int height = this.rect.getHeight();
      GuiStyle style = this.getStyle();
      ControlFormatting formatting = this.getControlFormatting();
      this.getBorder(style, style.get(formatting.border)).render(graphics, 0.0, 0.0, width, height);
      int borderSize = style.getBorder(formatting.border);
      width -= borderSize * 2;
      height -= borderSize * 2;
      this.getBackground(style, style.get(formatting.face, this.enabled && realRect.inside(mouseX, mouseY)))
         .render(graphics, borderSize, borderSize, width, height);
      controlRect.shrink(borderSize * scale);
      this.renderContent(graphics, formatting, borderSize, controlRect, realRect, scale, mouseX, mouseY);
      if (!this.enabled && formatting.hasDisabledEffect) {
         realRect.scissor();
         RenderSystem.disableDepthTest();
         RenderSystem.enableBlend();
         style.disabled.render(graphics, null, rectCopy);
         RenderSystem.enableDepthTest();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderContent(
      GuiGraphics graphics, ControlFormatting formatting, int borderWidth, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY
   ) {
      PoseStack pose = graphics.pose();
      controlRect.shrink(formatting.padding * scale);
      if (!this.enabled) {
         pose.pushPose();
      }

      pose.translate(borderWidth + formatting.padding, borderWidth + formatting.padding, 0.0F);
      Rect r = controlRect.intersection(realRect);
      if (r != null) {
         this.renderContent(graphics, controlRect, r, scale, mouseX, mouseY);
      }

      if (!this.enabled) {
         pose.popPose();
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected void renderContent(GuiGraphics graphics, Rect controlRect, Rect realRect, double scale, int mouseX, int mouseY) {
      this.renderContent(graphics, mouseX, mouseY);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   protected abstract void renderContent(GuiGraphics var1, int var2, int var3);

   public Player getPlayer() {
      return this.parent.getPlayer();
   }

   public static MutableComponent translatable(String text) {
      return Component.literal(translate(text));
   }

   public static MutableComponent translatable(String text, Object... parameters) {
      return Component.literal(translate(text, parameters));
   }

   public static String translate(String text) {
      return LanguageUtils.translate(text);
   }

   public static String translate(String text, Object... parameters) {
      return LanguageUtils.translate(text, parameters);
   }

   public static String translateOrDefault(String text, String defaultText) {
      return LanguageUtils.translateOr(text, defaultText);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void playSound(SoundInstance sound) {
      Minecraft.getInstance().getSoundManager().play(sound);
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void playSound(Reference<SoundEvent> sound) {
      playSound((SoundEvent)sound.value());
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void playSound(SoundEvent event) {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event, 1.0F));
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void playSound(SoundEvent event, float volume, float pitch) {
      Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event, pitch, volume));
   }

   @OnlyIn(Dist.CLIENT)
   @Environment(EnvType.CLIENT)
   public static void playSound(Reference<SoundEvent> event, float volume, float pitch) {
      playSound((SoundEvent)event.value(), volume, pitch);
   }
}
