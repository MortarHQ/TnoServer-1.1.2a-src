package dev.ftb.mods.ftbquests.client;

import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.EntityFaceConfig;
import dev.ftb.mods.ftblibrary.config.ImageResourceConfig;
import dev.ftb.mods.ftblibrary.config.ItemStackConfig;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectEntityFaceScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectImageResourceScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectItemStackScreen;
import dev.ftb.mods.ftblibrary.icon.EntityIconLoader;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.ContextMenuItem;
import dev.ftb.mods.ftblibrary.ui.ScreenWrapper;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.ui.input.MouseButton;
import dev.ftb.mods.ftbquests.FTBQuests;
import dev.ftb.mods.ftbquests.item.CustomIconItem;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.registry.ModItems;
import java.util.List;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;

public class ConfigIconItemStack extends ItemStackConfig {
   public ConfigIconItemStack() {
      super(false, true);
   }

   public void onClicked(Widget clickedWidget, MouseButton button, ConfigCallback callback) {
      if (this.getCanEdit()) {
         if (BaseScreen.isShiftKeyDown()) {
            new SelectItemStackScreen(this, callback).openGui();
         } else if (BaseScreen.isCtrlKeyDown()) {
            this.openImageSelector(callback);
         } else if (ScreenWrapper.hasAltDown()) {
            this.openEntitySelector(callback);
         } else {
            clickedWidget.getGui().openContextMenu(this.makeMenu(callback));
         }
      }
   }

   private void openImageSelector(ConfigCallback callback) {
      ImageResourceConfig imageConfig = new ImageResourceConfig();
      FTBQuests.getComponent(this.getValue(), ModDataComponents.CUSTOM_ICON).ifPresent(imageConfig::setCurrentValue);
      new SelectImageResourceScreen(
            imageConfig,
            accepted -> {
               if (accepted) {
                  if (!((ResourceLocation)imageConfig.getValue()).equals(ImageResourceConfig.NONE)) {
                     this.setCurrentValue(
                        (ItemStack)Util.make(
                           new ItemStack((ItemLike)ModItems.CUSTOM_ICON.get()), s -> CustomIconItem.setIcon(s, (ResourceLocation)imageConfig.getValue())
                        )
                     );
                  } else {
                     this.setCurrentValue(ItemStack.EMPTY);
                  }
               }

               callback.save(accepted);
            }
         )
         .openGui();
   }

   private void openEntitySelector(ConfigCallback callback) {
      EntityFaceConfig faceConfig = new EntityFaceConfig();
      FTBQuests.<ResourceLocation>getComponent(this.getValue(), ModDataComponents.ENTITY_FACE_ICON)
         .ifPresent(rl -> faceConfig.setCurrentValue((EntityType)BuiltInRegistries.ENTITY_TYPE.get(rl)));
      new SelectEntityFaceScreen(
            faceConfig,
            accepted -> {
               if (accepted) {
                  if (!((EntityType)faceConfig.getValue()).equals(EntityFaceConfig.NONE)) {
                     this.setCurrentValue(
                        (ItemStack)Util.make(
                           new ItemStack((ItemLike)ModItems.CUSTOM_ICON.get()), s -> CustomIconItem.setFaceIcon(s, (EntityType<?>)faceConfig.getValue())
                        )
                     );
                  } else {
                     this.setCurrentValue(ItemStack.EMPTY);
                  }
               }

               callback.save(accepted);
            }
         )
         .openGui();
   }

   private List<ContextMenuItem> makeMenu(ConfigCallback callback) {
      return List.of(
         new ContextMenuItem(
            Component.translatable("ftbquests.gui.icon_menu.item"),
            ItemIcon.getItemIcon(Items.DIAMOND),
            b -> new SelectItemStackScreen(this, callback).openGui()
         ),
         new ContextMenuItem(Component.translatable("ftbquests.gui.icon_menu.image"), Icons.ART, b -> this.openImageSelector(callback)),
         new ContextMenuItem(
            Component.translatable("ftbquests.gui.icon_menu.entity"), EntityIconLoader.getIcon(EntityType.PIG), b -> this.openEntitySelector(callback)
         )
      );
   }
}
