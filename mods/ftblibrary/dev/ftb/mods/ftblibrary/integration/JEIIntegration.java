package dev.ftb.mods.ftblibrary.integration;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.architectury.platform.Platform;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.FTBLibraryClient;
import dev.ftb.mods.ftblibrary.config.ui.resource.ResourceSearchMode;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectItemStackScreen;
import dev.ftb.mods.ftblibrary.config.ui.resource.SelectableResource;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.integration.neoforge.JEIIntegrationImpl;
import dev.ftb.mods.ftblibrary.sidebar.SidebarGroupGuiButton;
import dev.ftb.mods.ftblibrary.ui.IScreenWrapper;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.handlers.IGlobalGuiHandler;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.runtime.IClickableIngredient;
import mezz.jei.api.runtime.IJeiRuntime;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.NotNull;

@JeiPlugin
public class JEIIntegration implements IModPlugin, IGlobalGuiHandler {
   public static IJeiRuntime runtime = null;
   private static final ResourceSearchMode<ItemStack> JEI_ITEMS = new ResourceSearchMode<ItemStack>() {
      @Override
      public Icon getIcon() {
         return ItemIcon.getItemIcon(Items.APPLE);
      }

      public MutableComponent getDisplayName() {
         return Component.translatable("ftblibrary.select_item.list_mode.jei");
      }

      @Override
      public Collection<? extends SelectableResource<ItemStack>> getAllResources() {
         return JEIIntegration.runtime == null
            ? Collections.emptySet()
            : JEIIntegration.runtime.getIngredientManager().getAllIngredients(VanillaTypes.ITEM_STACK).stream().map(SelectableResource::item).toList();
      }
   };

   @ExpectPlatform
   @Transformed
   public static Optional<IClickableIngredient<?>> handleExtraIngredientTypes(IJeiRuntime runtime, PositionedIngredient underMouse) {
      return JEIIntegrationImpl.handleExtraIngredientTypes(runtime, underMouse);
   }

   public void onRuntimeAvailable(IJeiRuntime r) {
      runtime = r;
   }

   @NotNull
   public ResourceLocation getPluginUid() {
      return FTBLibrary.rl("jei");
   }

   public void registerGuiHandlers(IGuiHandlerRegistration registration) {
      if (!Platform.isModLoaded("roughlyenoughitems")) {
         registration.addGlobalGuiHandler(this);
      }
   }

   @NotNull
   public Collection<Rect2i> getGuiExtraAreas() {
      Screen currentScreen = Minecraft.getInstance().screen;
      return FTBLibraryClient.areButtonsVisible(currentScreen) ? Collections.singleton(SidebarGroupGuiButton.lastDrawnArea) : Collections.emptySet();
   }

   public Optional<IClickableIngredient<?>> getClickableIngredientUnderMouse(double mouseX, double mouseY) {
      if (Minecraft.getInstance().screen instanceof IScreenWrapper wrapper && wrapper.getGui().getIngredientUnderMouse().isPresent()) {
         PositionedIngredient underMouse = wrapper.getGui().getIngredientUnderMouse().get();
         if (!(underMouse.ingredient() instanceof ItemStack stack)) {
            return handleExtraIngredientTypes(runtime, underMouse);
         }

         Optional<ITypedIngredient<ItemStack>> typed = runtime.getIngredientManager().createTypedIngredient(VanillaTypes.ITEM_STACK, stack);
         if (typed.isPresent()) {
            return Optional.of(new JEIIntegration.ClickableIngredient(typed.get(), underMouse.area()));
         }
      }

      return Optional.empty();
   }

   static {
      if (!Platform.isModLoaded("roughlyenoughitems")) {
         SelectItemStackScreen.KNOWN_MODES.prependMode(JEI_ITEMS);
      }
   }

   public record ClickableIngredient<T>(ITypedIngredient<T> typedStack, Rect2i clickedArea) implements IClickableIngredient<T> {
      public ITypedIngredient<T> getTypedIngredient() {
         return this.typedStack;
      }

      public Rect2i getArea() {
         return this.clickedArea;
      }
   }
}
