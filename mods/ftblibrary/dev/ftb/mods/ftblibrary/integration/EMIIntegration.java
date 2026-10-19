package dev.ftb.mods.ftblibrary.integration;

import dev.emi.emi.api.EmiApi;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiInitRegistry;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.widget.Bounds;
import dev.ftb.mods.ftblibrary.config.ui.resource.ResourceSearchMode;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectItemStackScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectableResource;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.sidebar.SidebarGroupGuiButton;
import java.util.Collection;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

@EmiEntrypoint
public class EMIIntegration implements EmiPlugin {
   private static final ResourceSearchMode<ItemStack> EMI_ITEMS = new ResourceSearchMode<ItemStack>() {
      @Override
      public Icon getIcon() {
         return Icon.getIcon(ResourceLocation.fromNamespaceAndPath("emi", "textures/gui/widgets.png")).withUV(17.0F, 147.0F, 14.0F, 14.0F, 256.0F, 256.0F);
      }

      public MutableComponent getDisplayName() {
         return Component.translatable("ftblibrary.select_item.list_mode.emi");
      }

      @Override
      public Collection<? extends SelectableResource<ItemStack>> getAllResources() {
         return EmiApi.getIndexStacks().stream().map(item -> SelectableResource.item(item.getItemStack())).toList();
      }
   };

   public void initialize(EmiInitRegistry registry) {
      SelectItemStackScreen.KNOWN_MODES.prependMode(EMI_ITEMS);
   }

   public void register(EmiRegistry registry) {
      registry.addGenericExclusionArea((screen, boundsConsumer) -> {
         Rect2i sidebarButtons = SidebarGroupGuiButton.lastDrawnArea;
         boundsConsumer.accept(new Bounds(sidebarButtons.getX(), sidebarButtons.getY(), sidebarButtons.getWidth(), sidebarButtons.getHeight()));
      });
   }
}
