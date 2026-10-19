package com.teamresourceful.resourcefullib.client.screens.state;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ScreenStateManager {
   private static final Map<ResourceLocation, ScreenState> SCREEN_STATES = new HashMap<>();

   public static Screen getScreen(ResourceLocation id, Supplier<Screen> defaultScreen) {
      return SCREEN_STATES.getOrDefault(id, defaultScreen::get).createScreen();
   }

   @Nullable
   public static ScreenState getState(ResourceLocation id) {
      return SCREEN_STATES.get(id);
   }

   public static <T extends ScreenState> T updateState(ResourceLocation id, T state) {
      SCREEN_STATES.put(id, state);
      return state;
   }

   public static <T extends ScreenState> T getOrAddState(ResourceLocation id, Supplier<T> defaultState, Class<T> clazz) {
      ScreenState state = getState(id);
      return clazz.isInstance(state) ? clazz.cast(state) : updateState(id, defaultState.get());
   }
}
