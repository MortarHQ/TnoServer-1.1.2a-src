package dev.ftb.mods.ftbchunks.client.gui;

import dev.ftb.mods.ftbchunks.client.FTBChunksClientConfig;
import dev.ftb.mods.ftbchunks.client.mapicon.EntityIconUtils;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader.EntityIconSettings;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.ui.SimpleButton;
import dev.ftb.mods.ftblibrary.ui.TextField;
import dev.ftb.mods.ftblibrary.ui.Theme;
import dev.ftb.mods.ftblibrary.ui.ToggleableButton;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen;
import dev.ftb.mods.ftblibrary.ui.misc.AbstractGroupedButtonListScreen.GroupData;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class EntityIconSettingsScreen extends AbstractGroupedButtonListScreen<MobCategory, EntityType<?>> {
   private final boolean showCreationButton;

   public EntityIconSettingsScreen(boolean showCreationButton) {
      super(Component.translatable("ftbchunks.gui.entity_icon_settings"));
      this.showCreationButton = showCreationButton;
   }

   protected List<GroupData<MobCategory, EntityType<?>>> getGroups() {
      List<GroupData<MobCategory, EntityType<?>>> groups = new ArrayList<>();

      for (MobCategory mobCategory : MobCategory.values()) {
         List<EntityType<?>> entityTypes = new ArrayList<>();

         for (EntityType<?> entityType : BuiltInRegistries.ENTITY_TYPE) {
            if (entityType.getCategory() == mobCategory && EntityIconUtils.canTypeRender(entityType)) {
               entityTypes.add(entityType);
            }
         }

         groups.add(new GroupData(mobCategory, false, Component.translatable("mob_category." + mobCategory.getName()), entityTypes));
      }

      return groups;
   }

   protected AbstractGroupedButtonListScreen<MobCategory, EntityType<?>>.RowPanel createRowPanel(Panel panel, EntityType<?> value) {
      return new EntityIconSettingsScreen.RowPanel(panel, value);
   }

   private class RowPanel extends AbstractGroupedButtonListScreen<MobCategory, EntityType<?>>.RowPanel {
      private TextField nameField;
      private SimpleButton hideButton;
      private SimpleButton createButton;
      private final Icon icon;
      private final ResourceKey<EntityType<?>> resourceKey;

      public RowPanel(Panel panel, EntityType<?> entityType) {
         super(EntityIconSettingsScreen.this, panel, entityType);
         this.icon = EntityIconLoader.getIcon(entityType);
         this.resourceKey = ResourceKey.create(Registries.ENTITY_TYPE, BuiltInRegistries.ENTITY_TYPE.getKey(entityType));
         this.setHeight(18);
      }

      public void addWidgets() {
         boolean hiddenState = ((Map)FTBChunksClientConfig.ENTITY_ICON.get()).getOrDefault(this.resourceKey, true);
         this.add(this.hideButton = new ToggleableButton(this, hiddenState, Icons.ACCEPT, Icons.ACCEPT_GRAY, (hideButton, hidden) -> {
            ((Map)FTBChunksClientConfig.ENTITY_ICON.get()).put(this.resourceKey, hidden);
            FTBChunksClientConfig.saveConfig();
         }));
         if (EntityIconSettingsScreen.this.showCreationButton) {
            Icon icon = EntityIconLoader.isDynamicTexture((EntityType)this.value) ? Icons.BOOK_RED : Icons.BOOK;
            this.add(
               this.createButton = new SimpleButton(
                  this,
                  Component.translatable("ftbchunks.gui.open_creation_gui"),
                  icon,
                  (widget, button) -> new SliceCreationGUI((EntityType<?>)this.value).openGui()
               )
            );
         }

         final EntityIconSettings entityIconSettings = (EntityIconSettings)EntityIconLoader.getSettings((EntityType)this.value).orElseThrow();
         this.add(this.nameField = (new TextField(this) {
            public void draw(GuiGraphics graphics, Theme theme, int x, int y, int w, int h) {
               graphics.pose().pushPose();
               graphics.pose().translate(x - 16, y - 2, 0.0F);
               graphics.pose().scale(0.75F, 0.75F, 0.0F);
               RowPanel.this.icon.draw(graphics, 0, 0, entityIconSettings.widthHeight().width(), entityIconSettings.widthHeight().height());
               graphics.pose().popPose();
               super.draw(graphics, theme, x, y, w, h);
            }

            public void addMouseOverText(TooltipList list) {
               list.add(Component.literal(((EntityType)RowPanel.this.value).arch$registryName().toString()));
            }
         }).setTrim().addFlags(2));
      }

      public void alignWidgets() {
      }

      public void setWidth(int newWidth) {
         super.setWidth(newWidth);
         if (newWidth > 0) {
            int farRight = newWidth - 8;
            int yOff = (this.height - EntityIconSettingsScreen.this.getTheme().getFontHeight()) / 2 + 1;
            double size = 16.0;
            this.hideButton.setForceButtonSize(false);
            this.hideButton.setPosAndSize(farRight - 8, 1, (int)size, (int)size);
            if (EntityIconSettingsScreen.this.showCreationButton) {
               this.createButton.setPosAndSize(farRight - 8 - (int)size, 1, (int)size, (int)size);
            }

            this.nameField.setPos(17, yOff);
            this.nameField.setText(Component.translatable(((EntityType)this.value).getDescriptionId()));
            this.nameField.setHeight(EntityIconSettingsScreen.this.getTheme().getFontHeight() + 2);
         }
      }
   }
}
