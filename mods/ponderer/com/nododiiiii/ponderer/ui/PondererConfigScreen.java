package com.nododiiiii.ponderer.ui;

import com.nododiiiii.ponderer.Config;
import com.nododiiiii.ponderer.ui.catnip.AbstractDeclarativeConfigListScreen;
import com.nododiiiii.ponderer.ui.catnip.ConfigEntries;
import com.nododiiiii.ponderer.ui.catnip.DeclarativeFormEntry;
import java.util.List;
import javax.annotation.Nullable;
import net.createmod.catnip.gui.ConfirmationScreen.Response;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;

public class PondererConfigScreen extends AbstractDeclarativeConfigListScreen {
   @Nullable
   private final PonderScreenNavigation.ReturnState ponderReturnState;

   public PondererConfigScreen(Screen parent) {
      this(parent, null);
   }

   public PondererConfigScreen(Screen parent, @Nullable PonderScreenNavigation.ReturnState ponderReturnState) {
      super(
         parent,
         "ponderer",
         "ponderer.ui.scope.client",
         "ponderer.ui.mod_config.title",
         AbstractDeclarativeConfigListScreen.ConfigScope.CLIENT,
         Config.CLIENT_SPEC
      );
      this.ponderReturnState = ponderReturnState;
   }

   @Override
   protected void collectFormEntries(List<DeclarativeFormEntry> entries) {
      entries.add(
         ConfigEntries.booleanEntry("ponderer.ui.mod_config.default_editable", "ponderer.ui.mod_config.default_editable.tooltip", Config.DEFAULT_EDITABLE)
      );
      entries.add(ConfigEntries.booleanEntry("ponderer.ui.mod_config.developer_mode", "ponderer.ui.mod_config.developer_mode.tooltip", Config.DEVELOPER_MODE));
   }

   @Override
   protected void attemptBackToParent() {
      if (this.ponderReturnState == null) {
         super.attemptBackToParent();
      } else if (!this.hasUnsavedChanges()) {
         this.restoreAndOpenParent();
      } else {
         this.showLeavingPrompt(response -> {
            if (response != Response.Cancel) {
               if (response == Response.Confirm) {
                  if (!this.saveEdits()) {
                     return;
                  }
               } else {
                  this.discardEdits();
               }

               this.restoreAndOpenParent();
            }
         });
      }
   }

   private void restoreAndOpenParent() {
      PonderScreenNavigation.restoreReturnState(this.ponderReturnState);
      Minecraft.getInstance().setScreen(this.parent);
   }
}
