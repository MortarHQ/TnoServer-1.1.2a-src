package xaero.common.gui;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import xaero.common.IXaeroMinimap;
import xaero.hud.category.ui.EditorCategoryNodeConverter;
import xaero.hud.category.ui.GuiCategoryEditor;
import xaero.hud.minimap.radar.category.EntityRadarCategory;
import xaero.hud.minimap.radar.category.EntityRadarCategoryManager;
import xaero.hud.minimap.radar.category.ui.EditorEntityRadarCategoryNodeConverter;
import xaero.hud.minimap.radar.category.ui.node.EditorEntityRadarCategoryNode;
import xaero.hud.minimap.radar.category.ui.node.EditorEntityRadarCategorySettingsNode;
import xaero.lib.client.gui.config.EditConfigScreen;

public class GuiEntityRadarCategoryEditor
   extends GuiCategoryEditor<EntityRadarCategory, EditorEntityRadarCategoryNode, EntityRadarCategory.Builder, EditorEntityRadarCategorySettingsNode<?>, EditorEntityRadarCategorySettingsNode.Builder, EditorEntityRadarCategoryNode.Builder> {
   public static final Component SERVER_ENFORCED_COMPONENT = Component.translatable("gui.xaero_entity_category_editor_server_enforced")
      .withStyle(ChatFormatting.YELLOW);
   private final EntityRadarCategoryManager entityRadarCategoryManager;
   private final boolean clientSide;
   private final Runnable onChange;
   private final boolean viewingEnforced;

   public GuiEntityRadarCategoryEditor(IXaeroMinimap modMain, EditConfigScreen parent, Screen escape, Runnable onChange, boolean viewingEnforced) {
      super(
         modMain,
         parent,
         escape,
         Component.translatable("gui.xaero_entity_radar_categories"),
         EditorEntityRadarCategoryNodeConverter.Builder.begin().build(),
         viewingEnforced
      );
      this.entityRadarCategoryManager = modMain.getEntityRadarCategoryManager();
      this.clientSide = parent.getContext().isClientSide();
      this.onChange = onChange;
      this.viewingEnforced = viewingEnforced;
   }

   protected EditorEntityRadarCategoryNode constructEditorData(
      EditorCategoryNodeConverter<EntityRadarCategory, EditorEntityRadarCategoryNode, EntityRadarCategory.Builder, EditorEntityRadarCategorySettingsNode<?>, EditorEntityRadarCategorySettingsNode.Builder, EditorEntityRadarCategoryNode.Builder> dataConverter
   ) {
      EntityRadarCategory editedCategory = this.readOnly
         ? this.modMain.getEntityRadarCategoryManager().getSyncedRootCategory()
         : this.modMain.getEntityRadarCategoryManager().getEditedCategory();
      return (EditorEntityRadarCategoryNode)dataConverter.convert(editedCategory, !this.readOnly && ((EditConfigScreen)this.parent).getContext().isClientSide());
   }

   protected EditorEntityRadarCategoryNode constructDefaultData(
      EditorCategoryNodeConverter<EntityRadarCategory, EditorEntityRadarCategoryNode, EntityRadarCategory.Builder, EditorEntityRadarCategorySettingsNode<?>, EditorEntityRadarCategorySettingsNode.Builder, EditorEntityRadarCategoryNode.Builder> dataConverter
   ) {
      this.modMain.getSettings().resetEntityRadarBackwardsCompatibilityConfig();
      EntityRadarCategory rootCategory = this.clientSide
         ? this.entityRadarCategoryManager.fetchDefaultClientCategory()
         : this.entityRadarCategoryManager.fetchDefaultServerCategory();
      return (EditorEntityRadarCategoryNode)dataConverter.convert(rootCategory, this.clientSide);
   }

   protected void onConfigConfirmed(EntityRadarCategory confirmedRootCategory) {
      this.entityRadarCategoryManager.storeEditedCategory(confirmedRootCategory, this.clientSide);
      this.onChange.run();
   }

   @Override
   public void render(GuiGraphics guiGraphics, int i, int j, float f) {
      super.render(guiGraphics, i, j, f);
      if (this.viewingEnforced) {
         guiGraphics.drawCenteredString(this.font, SERVER_ENFORCED_COMPONENT, this.width / 2, 15, -1);
      }
   }
}
