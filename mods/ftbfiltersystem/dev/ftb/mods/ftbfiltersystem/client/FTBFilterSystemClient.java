package dev.ftb.mods.ftbfiltersystem.client;

import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.client.FTBFilterSystemClientAPI;
import dev.ftb.mods.ftbfiltersystem.api.client.FilterScreenFactory;
import dev.ftb.mods.ftbfiltersystem.api.client.gui.AbstractFilterConfigScreen;
import dev.ftb.mods.ftbfiltersystem.api.event.client.ClientFilterRegistrationEvent;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.client.gui.ComponentConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.CustomConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.DurabilityConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.ExpressionConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.FilterScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.FoodValueConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.ItemConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.ItemTagConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.MaxCountConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.ModConfigScreen;
import dev.ftb.mods.ftbfiltersystem.client.gui.StackSizeConfigScreen;
import dev.ftb.mods.ftbfiltersystem.filter.ComponentFilter;
import dev.ftb.mods.ftbfiltersystem.filter.CustomFilter;
import dev.ftb.mods.ftbfiltersystem.filter.DurabilityFilter;
import dev.ftb.mods.ftbfiltersystem.filter.ExpressionFilter;
import dev.ftb.mods.ftbfiltersystem.filter.FoodValueFilter;
import dev.ftb.mods.ftbfiltersystem.filter.ItemFilter;
import dev.ftb.mods.ftbfiltersystem.filter.ItemTagFilter;
import dev.ftb.mods.ftbfiltersystem.filter.MaxStackSizeFilter;
import dev.ftb.mods.ftbfiltersystem.filter.ModFilter;
import dev.ftb.mods.ftbfiltersystem.filter.StackSizeFilter;
import dev.ftb.mods.ftbfiltersystem.registry.FilterRegistry;
import dev.ftb.mods.ftbfiltersystem.registry.item.SmartFilterItem;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public enum FTBFilterSystemClient implements FTBFilterSystemClientAPI {
   INSTANCE;

   private final Map<ResourceLocation, FilterScreenFactory<?>> screenFactories = new ConcurrentHashMap<>();

   public static Provider registryAccess() {
      return Objects.requireNonNull(Minecraft.getInstance().level).registryAccess();
   }

   public void init() {
      FTBFilterSystemAPI._initClient(this);
      ClientLifecycleEvent.CLIENT_SETUP.register(this::onClientSetup);
      ClientLifecycleEvent.CLIENT_STARTED.register(this::onClientStarted);
      ClientFilterRegistrationEvent.REGISTER.register(this::registerClientFilters);
   }

   private void registerClientFilters(FTBFilterSystemClientAPI api) {
      api.registerFilterScreenFactory(ItemFilter.ID, ItemConfigScreen::new);
      api.registerFilterScreenFactory(DurabilityFilter.ID, DurabilityConfigScreen::new);
      api.registerFilterScreenFactory(MaxStackSizeFilter.ID, MaxCountConfigScreen::new);
      api.registerFilterScreenFactory(StackSizeFilter.ID, StackSizeConfigScreen::new);
      api.registerFilterScreenFactory(FoodValueFilter.ID, FoodValueConfigScreen::new);
      api.registerFilterScreenFactory(ItemTagFilter.ID, ItemTagConfigScreen::new);
      api.registerFilterScreenFactory(ModFilter.ID, ModConfigScreen::new);
      api.registerFilterScreenFactory(ComponentFilter.ID, ComponentConfigScreen::new);
      api.registerFilterScreenFactory(CustomFilter.ID, CustomConfigScreen::new);
      api.registerFilterScreenFactory(ExpressionFilter.ID, ExpressionConfigScreen::new);
   }

   private void onClientStarted(Minecraft minecraft) {
      ((ClientFilterRegistrationEvent)ClientFilterRegistrationEvent.REGISTER.invoker()).registerFilters(FTBFilterSystemAPI.clientApi());
   }

   public void onClientSetup(Minecraft minecraft) {
      FilterRegistry.getInstance().freeze();
   }

   public void openFilterScreen(InteractionHand interactionHand) {
      Player player = Minecraft.getInstance().player;
      if (player != null) {
         ItemStack stack = player.getItemInHand(interactionHand);
         if (stack.getItem() instanceof SmartFilterItem) {
            try {
               Minecraft.getInstance()
                  .setScreen(new FilterScreen(stack.getHoverName(), SmartFilterItem.getFilter(stack, player.registryAccess()), interactionHand));
            } catch (FilterException e) {
               player.displayClientMessage(Component.literal(e.getMessage()).withStyle(ChatFormatting.RED), false);
            }
         }
      }
   }

   public <T extends SmartFilter> void openFilterConfigScreen(T filter, FilterScreen parent, boolean deleteOnCancel) {
      FilterScreenFactory<?> factory = this.screenFactories.get(filter.getId());
      if (factory != null) {
         AbstractFilterConfigScreen<T> screen = (AbstractFilterConfigScreen<T>)((FilterScreenFactory<T>)factory).createScreen(filter, parent);
         screen.setDeleteOnCancel(deleteOnCancel);
         Minecraft.getInstance().setScreen(screen);
      }
   }

   public static boolean isPlayerHolding(ItemStack stack) {
      Player player = Minecraft.getInstance().player;
      return player != null && (player.getMainHandItem() == stack || player.getOffhandItem() == stack);
   }

   public static boolean shouldShowItemTooltip() {
      return Minecraft.getInstance().screen instanceof EffectRenderingInventoryScreen;
   }

   @Override
   public <T extends SmartFilter> void registerFilterScreenFactory(ResourceLocation filterId, FilterScreenFactory<T> factory) {
      this.screenFactories.put(filterId, factory);
   }
}
