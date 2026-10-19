package xaero.common.gui;

import java.io.IOException;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.HudMod;
import xaero.common.IXaeroMinimap;
import xaero.common.XaeroMinimapSession;
import xaero.hud.module.HudModule;
import xaero.hud.module.ModuleManager;
import xaero.hud.module.ModuleSession;
import xaero.hud.module.ModuleTransform;
import xaero.hud.preset.HudPreset;
import xaero.hud.pushbox.PushboxHandler;
import xaero.lib.client.gui.ScreenBase;
import xaero.lib.client.gui.widget.MySmallButton;
import xaero.lib.client.gui.widget.Tooltip;

public class GuiEditMode extends ScreenBase {
   public static final Component CENTERED_COMPONENT = Component.translatable("gui.xaero_centered");
   public static final Component FLIPPED_COMPONENT = Component.translatable("gui.xaero_flipped");
   public static final Component TRUE_COMPONENT = Component.translatable("gui.yes");
   public static final Component FALSE_COMPONENT = Component.translatable("gui.no");
   public static final Component PRESS_C_COMPONENT = Component.translatable("gui.xaero_press_c");
   public static final Component PRESS_F_COMPONENT = Component.translatable("gui.xaero_press_f");
   public static final Component NOT_INGAME = Component.translatable("gui.xaero_not_ingame");
   private final int NORMAL_COLOR = 1354612157;
   private final int HOVERED_COLOR = 1694498815;
   private final int SELECTED_COLOR = -2097152001;
   private final boolean instructions;
   private final Component message;
   private HudModule<?> draggedModule;
   private HudModule<?> selectedModule;
   private HudModule<?> lastFrameHoveredModule;
   private int dragOffsetX;
   private int dragOffsetY;
   private final IXaeroMinimap modMain;

   public GuiEditMode(IXaeroMinimap modMain, Screen parent, Screen escape, boolean instructions, Component message) {
      super(parent, escape, Component.translatable("gui.xaero_edit_mode"));
      this.modMain = modMain;
      this.instructions = instructions;
      this.message = message;
   }

   protected void init() {
      super.init();
      this.draggedModule = null;
      this.selectedModule = null;
      this.addRenderableWidget(
         new MySmallButton(200, this.width / 2 - 155, this.height / 6 + 143, Component.translatable("gui.xaero_confirm"), b -> this.confirm())
      );
      this.addRenderableWidget(
         new MySmallButton(
            202,
            this.width / 2 + 5,
            this.height / 6 + 143,
            Component.translatable("gui.xaero_choose_a_preset"),
            b -> this.minecraft.setScreen(new GuiChoosePreset(this.modMain, this, this.escape))
         )
      );
      if (this.instructions) {
         this.addRenderableWidget(
            new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, Component.translatable("gui.xaero_cancel"), b -> this.cancel())
         );
         this.addRenderableWidget(
            new MySmallButton(
               203,
               this.width / 2 - 155,
               this.height / 6 + 168,
               Component.translatable("gui.xaero_instructions"),
               b -> this.minecraft.setScreen(new GuiInstructions(this.modMain, this, this.escape))
            )
         );
      } else {
         this.addRenderableWidget(
            new MySmallButton(201, this.width / 2 + 5, this.height / 6 + 168, Component.translatable("gui.xaero_cancel"), b -> this.cancel())
         );
      }
   }

   private void confirm() {
      for (HudPreset preset : this.modMain.getHud().getPresetManager().getPresets()) {
         preset.confirm();
      }

      ModuleManager manager = this.modMain.getHud().getModuleManager();

      for (HudModule<?> module : manager.getModules()) {
         module.confirmTransform();
      }

      try {
         this.modMain.getSettings().saveSettings();
      } catch (IOException e) {
         HudMod.LOGGER.error("suppressed exception", e);
      }

      this.minecraft.setScreen(this.parent);
   }

   private void cancel() {
      for (HudPreset preset : this.modMain.getHud().getPresetManager().getPresets()) {
         preset.cancel();
      }

      ModuleManager manager = this.modMain.getHud().getModuleManager();

      for (HudModule<?> module : manager.getModules()) {
         module.cancelTransform();
      }

      this.goBack();
   }

   public void renderBackground(GuiGraphics guiGraphics, int mouseX, int mouseY, float partial) {
      if (this.minecraft.player == null) {
         super.renderBackground(guiGraphics, mouseX, mouseY, partial);
         guiGraphics.drawCenteredString(this.font, NOT_INGAME, this.width / 2, this.height / 6 + 128, 16777215);
      } else {
         guiGraphics.drawCenteredString(this.font, this.message, this.width / 2, this.height / 6 + 128, 16777215);
      }
   }

   private void applyPushes() {
      double screenScale = this.minecraft.getWindow().getGuiScale();
      ModuleManager manager = this.modMain.getHud().getModuleManager();

      for (HudModule<?> module : manager.getModules()) {
         ModuleSession<?> session = module.getCurrentSession();
         if (session.isActive()) {
            PushboxHandler.State pushState = module.getPushState();
            pushState.resetForModule(session, this.width, this.height, screenScale);
            this.modMain.getHudRenderer().getPushboxHandler().applyScreenEdges(pushState, this.width, this.height, screenScale);
         }
      }
   }

   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
      if (XaeroMinimapSession.getCurrentSession() == null) {
         super.render(guiGraphics, mouseX, mouseY, partialTicks);
      } else {
         double screenScale = this.minecraft.getWindow().getGuiScale();
         this.handleDraggedModule(mouseX, mouseY, screenScale);
         this.applyPushes();
         ModuleManager manager = this.modMain.getHud().getModuleManager();
         HudModule<?> hoveredModule = this.lastFrameHoveredModule = this.getHoveredModule(mouseX, mouseY);

         for (HudModule<?> module : manager.getModules()) {
            if (module.getCurrentSession().isActive()) {
               this.renderModuleBox(module, hoveredModule, screenScale, guiGraphics);
            }
         }

         super.render(guiGraphics, mouseX, mouseY, partialTicks);
         if (hoveredModule != null && this.draggedModule == null) {
            Tooltip tooltip = new Tooltip(this.getTooltipText(hoveredModule));
            tooltip.setStartWidth(150);
            guiGraphics.pose().translate(0.0F, 0.0F, 1.0F);
            tooltip.drawBox(guiGraphics, mouseX, mouseY, this.width, this.height);
            guiGraphics.pose().translate(0.0F, 0.0F, -1.0F);
         }
      }
   }

   private <MS extends ModuleSession<MS>> void renderModuleBox(HudModule<MS> module, HudModule<?> hoveredModule, double screenScale, GuiGraphics guiGraphics) {
      MS session = module.getCurrentSession();
      int moduleW = session.getWidth(screenScale);
      int moduleH = session.getHeight(screenScale);
      boolean hovered = hoveredModule == module;
      PushboxHandler.State pushState = module.getPushState();
      int boxX = pushState.x;
      int boxY = pushState.y;
      guiGraphics.fill(boxX, boxY, boxX + moduleW, boxY + moduleH, this.selectedModule == module ? -2097152001 : (hovered ? 1694498815 : 1354612157));
   }

   private HudModule<?> getHoveredModule(int mouseX, int mouseY) {
      ModuleManager manager = this.modMain.getHud().getModuleManager();
      HudModule<?> result = null;
      int resultSize = 0;

      for (HudModule<?> module : manager.getModules()) {
         if (module.getCurrentSession().isActive() && this.isHovered(module, mouseX, mouseY)) {
            int moduleW = module.getPushState().w;
            int moduleH = module.getPushState().h;
            if (module == this.selectedModule || result == null || moduleW * moduleH <= resultSize) {
               result = module;
               resultSize = moduleW * moduleH;
            }
         }
      }

      return result;
   }

   private boolean isHovered(HudModule<?> module, int mouseX, int mouseY) {
      PushboxHandler.State pushState = module.getPushState();
      int boxX = pushState.x;
      int boxY = pushState.y;
      return mouseX >= boxX && mouseX < boxX + pushState.w && mouseY >= boxY && mouseY < boxY + pushState.h;
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      boolean clickResult = super.mouseClicked(mouseX, mouseY, button);
      if (!clickResult && XaeroMinimapSession.getCurrentSession() != null) {
         this.applyPushes();
         this.draggedModule = this.getHoveredModule((int)mouseX, (int)mouseY);
         this.selectedModule = this.draggedModule;
         if (this.draggedModule != null) {
            this.dragOffsetX = this.draggedModule.getPushState().x - (int)mouseX;
            this.dragOffsetY = this.draggedModule.getPushState().y - (int)mouseY;
            return true;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.draggedModule = null;
      return super.mouseReleased(mouseX, mouseY, button);
   }

   public boolean keyPressed(int code, int $$1, int $$2) {
      HudModule<?> affectedModule = this.selectedModule != null ? this.selectedModule : this.lastFrameHoveredModule;
      if (affectedModule != null && (code == 67 || code == 70 || code == 83)) {
         ModuleTransform transform = affectedModule.getUnconfirmedTransform();
         switch (code) {
            case 67:
               int oldModuleY = affectedModule.getCurrentSession().getEffectiveY(this.height, this.minecraft.getWindow().getGuiScale());
               transform.centered = !transform.centered;
               if (this.draggedModule != null) {
                  int newModuleY = affectedModule.getCurrentSession().getEffectiveY(this.height, this.minecraft.getWindow().getGuiScale());
                  this.dragOffsetY += newModuleY - oldModuleY;
               }
               break;
            case 70:
               boolean curFlippedHor = transform.flippedHor;
               transform.flippedHor = !transform.flippedVer;
               transform.flippedVer = curFlippedHor;
               break;
            case 83:
               Screen configScreen = affectedModule.getConfigScreenFactory().apply(this);
               if (configScreen != null) {
                  this.minecraft.setScreen(configScreen);
               }
         }
      }

      return super.keyPressed(code, $$1, $$2);
   }

   private void handleDraggedModule(int mouseX, int mouseY, double screenScale) {
      if (this.draggedModule != null) {
         ModuleSession<?> session = this.draggedModule.getCurrentSession();
         if (session != null) {
            ModuleTransform transform = this.draggedModule.getUnconfirmedTransform();
            transform.y = mouseY + this.dragOffsetY;
            transform.fromBottom = false;
            int moduleH = session.getHeight(screenScale);
            int yFromBottom = this.height - transform.y - moduleH;
            if (transform.y > yFromBottom) {
               transform.fromBottom = true;
               transform.y = yFromBottom;
            }

            if (!transform.centered) {
               transform.x = mouseX + this.dragOffsetX;
               transform.fromRight = false;
               int moduleW = session.getWidth(screenScale);
               int xFromRight = this.width - transform.x - moduleW;
               if (transform.x > xFromRight) {
                  transform.fromRight = true;
                  transform.x = xFromRight;
               }
            }
         }
      }
   }

   private Component getTooltipText(HudModule<?> hoveredModule) {
      ModuleTransform transform = hoveredModule.getUnconfirmedTransform();
      Component centeredLine = Component.translatable(
         "%s %s %s", new Object[]{CENTERED_COMPONENT, transform.centered ? TRUE_COMPONENT : FALSE_COMPONENT, PRESS_C_COMPONENT}
      );
      Component flippedLine = Component.translatable(
         "%s %s %s %s",
         new Object[]{
            FLIPPED_COMPONENT,
            transform.flippedHor ? TRUE_COMPONENT : FALSE_COMPONENT,
            transform.flippedVer ? TRUE_COMPONENT : FALSE_COMPONENT,
            PRESS_F_COMPONENT
         }
      );
      return Component.translatable("%s \n %s \n %s", new Object[]{hoveredModule.getDisplayName(), centeredLine, flippedLine});
   }
}
