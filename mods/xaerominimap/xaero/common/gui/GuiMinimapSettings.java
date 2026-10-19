package xaero.common.gui;

import com.mojang.blaze3d.platform.InputConstants.Type;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.HudMod;
import xaero.common.minimap.MinimapInterface;
import xaero.hud.minimap.common.config.option.MinimapProfiledConfigOptions;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.lib.client.controls.util.KeyMappingUtils;
import xaero.lib.client.gui.config.EditConfigScreen;
import xaero.lib.client.gui.config.context.IEditConfigScreenContext;
import xaero.lib.common.config.profile.ConfigProfile;

public abstract class GuiMinimapSettings extends EditConfigScreen {
   public GuiMinimapSettings(Component title, Screen par1Screen, Screen escScreen, IEditConfigScreenContext context) {
      super(title, par1Screen, escScreen, context, HudMod.INSTANCE.getHudConfigs());
      if (!(par1Screen instanceof GuiMinimapSettings) && !(par1Screen instanceof GuiEntityRadarCategoryEditor)) {
         HudMod.INSTANCE.getEntityRadarCategoryManager().forgetEditedCategory();
      }

      this.canSkipWorldRender = false;
      this.shouldRenderEscapeScreen = false;
   }

   protected void onEditedProfileSwitch() {
      super.onEditedProfileSwitch();
      EntityRadarCategoryManager categoryManager = HudMod.INSTANCE.getEntityRadarCategoryManager();
      if (this.context.hasPermission(this.channel) && this.context.getSyncStatus(this.channel)) {
         ConfigProfile newConfig = this.getProfileOnUpdate();
         if (categoryManager.getEditedCategoryConfig() != newConfig) {
            categoryManager.loadEditedCategory(newConfig, this.context.isClientSide());
         }
      } else {
         if (categoryManager.getEditedCategory() != null) {
            categoryManager.forgetEditedCategory();
         }
      }
   }

   public void render(GuiGraphics guiGraphics, int par1, int par2, float par3) {
      super.render(guiGraphics, par1, par2, par3);
      MinimapInterface minimapInterface = HudMod.INSTANCE.getInterfaces().getMinimapInterface();
      boolean mapSafeMode = (Boolean)HudMod.INSTANCE.getHudConfigs().getClientConfigManager().getEffective(MinimapProfiledConfigOptions.SAFE_MODE);
      if (!mapSafeMode && minimapInterface.getMinimapFBORenderer().isTriedFBO() && !minimapInterface.getMinimapFBORenderer().isLoadedFBO()) {
         guiGraphics.drawCenteredString(this.font, "§4You've been forced into safe mode! :(", this.width / 2, 11, 16777215);
      }
   }

   protected void handleChanges() {
      this.saveEditedCategoryIfNeeded();
      super.handleChanges();
   }

   private void saveEditedCategoryIfNeeded() {
      EntityRadarCategoryManager categoryManager = HudMod.INSTANCE.getEntityRadarCategoryManager();
      if (categoryManager.editedCategoryNeedsSaving()) {
         categoryManager.storeEditedCategory(this.context.isClientSide());
         this.handleChangesOnExit();
      }
   }

   public boolean keyPressed(int key, int scancode, int mods) {
      if (super.keyPressed(key, scancode, mods)) {
         return true;
      }

      if ((!this.context.isClientSide() || !KeyMappingUtils.inputMatches(Type.KEYSYM, key, (KeyMapping)HudMod.INSTANCE.getSettingsKey(), 0))
         && (this.context.isClientSide() || !KeyMappingUtils.inputMatches(Type.KEYSYM, key, (KeyMapping)HudMod.INSTANCE.getServerSettingsKey(), 0))) {
         return false;
      }

      this.onExit(this.escape);
      return true;
   }
}
