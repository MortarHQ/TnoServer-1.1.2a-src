package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.ftb.mods.ftblibrary.config.ConfigCallback;
import dev.ftb.mods.ftblibrary.config.FTBLibraryClientConfig;
import dev.ftb.mods.ftblibrary.config.ImageResourceConfig;
import dev.ftb.mods.ftblibrary.config.ResourceConfigValue;
import dev.ftb.mods.ftblibrary.ui.Panel;
import dev.ftb.mods.ftblibrary.util.ModUtils;
import dev.ftb.mods.ftblibrary.util.SearchTerms;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import org.jetbrains.annotations.Nullable;

public class SelectImageResourceScreen extends ResourceSelectorScreen<ResourceLocation> {
   private static final SelectableResource<ResourceLocation> NO_IMAGE = new ImageResource(ImageResourceConfig.NONE);
   private static final SearchModeIndex<ResourceSearchMode<ResourceLocation>> KNOWN_MODES = (SearchModeIndex<ResourceSearchMode<ResourceLocation>>)Util.make(
      new SearchModeIndex(), idx -> idx.appendMode(ResourceSearchMode.IMAGES)
   );

   public SelectImageResourceScreen(ResourceConfigValue<ResourceLocation> config, ConfigCallback callback) {
      super(config, callback);
   }

   @Override
   protected ResourceSelectorScreen<ResourceLocation>.ResourceButton makeResourceButton(Panel panel, @Nullable SelectableResource<ResourceLocation> resource) {
      return new SelectImageResourceScreen.ImageButton(panel, Objects.requireNonNullElse(resource, NO_IMAGE));
   }

   @Override
   protected SearchModeIndex<ResourceSearchMode<ResourceLocation>> getSearchModeIndex() {
      return KNOWN_MODES;
   }

   private class ImageButton extends ResourceSelectorScreen<ResourceLocation>.ResourceButton {
      protected ImageButton(Panel panel, SelectableResource<ResourceLocation> resource) {
         super(panel, resource);
      }

      @Override
      public boolean shouldAdd(SearchTerms searchTerms) {
         return searchTerms.match((ResourceLocation)this.getResource(), ((ResourceLocation)this.getResource()).toString(), id -> false);
      }

      @Override
      public void addMouseOverText(TooltipList list) {
         Component text = Component.literal(((ResourceLocation)this.getResource()).getNamespace())
            .withStyle(ChatFormatting.GOLD)
            .append(":")
            .append(Component.literal(((ResourceLocation)this.getResource()).getPath()).withStyle(ChatFormatting.YELLOW));
         list.add(text);
         if (FTBLibraryClientConfig.IMAGE_MODNAME.get()) {
            ModUtils.getModName(((ResourceLocation)this.getResource()).getNamespace())
               .ifPresent(name -> list.add(Component.literal(name).withStyle(new ChatFormatting[]{ChatFormatting.BLUE, ChatFormatting.ITALIC})));
         }
      }
   }

   public enum ResourceListener implements ResourceManagerReloadListener {
      INSTANCE;

      public void onResourceManagerReload(ResourceManager resourceManager) {
         ResourceSearchMode.IMAGES.clearCache();
      }
   }
}
