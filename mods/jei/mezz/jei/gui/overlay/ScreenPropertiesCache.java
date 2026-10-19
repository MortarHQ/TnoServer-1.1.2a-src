package mezz.jei.gui.overlay;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import mezz.jei.api.gui.handlers.IGuiProperties;
import mezz.jei.api.runtime.IScreenHelper;
import mezz.jei.common.util.ImmutablePoint2i;
import mezz.jei.common.util.ImmutableRect2i;
import mezz.jei.gui.GuiProperties;
import net.minecraft.client.gui.screens.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

public class ScreenPropertiesCache {
   private final IScreenHelper screenHelper;
   @Nullable
   private IGuiProperties previousGuiProperties;
   private boolean guiPropertiesAreValid = false;
   private Set<ImmutableRect2i> previousGuiExclusionAreas = Set.of();
   @Nullable
   private ImmutablePoint2i mouseExclusionArea;

   public ScreenPropertiesCache(IScreenHelper screenHelper) {
      this.screenHelper = screenHelper;
   }

   public ScreenPropertiesCache.Updater getUpdater(Runnable onChange) {
      return new ScreenPropertiesCache.Updater(this, onChange);
   }

   public boolean hasValidScreen() {
      return this.guiPropertiesAreValid;
   }

   public Optional<IGuiProperties> getGuiProperties() {
      return !this.guiPropertiesAreValid ? Optional.empty() : Optional.ofNullable(this.previousGuiProperties);
   }

   public Set<ImmutableRect2i> getGuiExclusionAreas() {
      return this.previousGuiExclusionAreas;
   }

   @Nullable
   public ImmutablePoint2i getMouseExclusionArea() {
      return this.mouseExclusionArea;
   }

   public static class Updater {
      private static final Logger LOGGER = LogManager.getLogger();
      private static final int MIN_GUI_DIMENSION = -1000000000;
      private static final int MAX_GUI_DIMENSION = 1000000000;
      private final ScreenPropertiesCache cache;
      private final Runnable onChange;
      private boolean changed = false;

      public Updater(ScreenPropertiesCache cache, Runnable onChange) {
         this.cache = cache;
         this.onChange = onChange;
      }

      public ScreenPropertiesCache.Updater updateScreen(@Nullable Screen guiScreen) {
         IGuiProperties currentGuiProperties = Optional.ofNullable(guiScreen).flatMap(this.cache.screenHelper::getGuiProperties).orElse(null);
         if (!GuiProperties.areEqual(this.cache.previousGuiProperties, currentGuiProperties)) {
            boolean previouslyValid = this.cache.guiPropertiesAreValid;
            this.cache.guiPropertiesAreValid = validateGuiProperties(currentGuiProperties);
            this.cache.previousGuiProperties = currentGuiProperties;
            if (previouslyValid || this.cache.guiPropertiesAreValid) {
               this.changed = true;
            }
         }

         return this;
      }

      public ScreenPropertiesCache.Updater updateExclusionAreas(Set<ImmutableRect2i> updatedGuiExclusionAreas) {
         if (!this.cache.previousGuiExclusionAreas.equals(updatedGuiExclusionAreas)) {
            this.cache.previousGuiExclusionAreas = updatedGuiExclusionAreas;
            this.changed = true;
         }

         return this;
      }

      public ScreenPropertiesCache.Updater updateMouseExclusionArea(@Nullable ImmutablePoint2i mouseExclusionArea) {
         if (!Objects.equals(this.cache.mouseExclusionArea, mouseExclusionArea)) {
            this.cache.mouseExclusionArea = mouseExclusionArea;
            this.changed = true;
         }

         return this;
      }

      public void update() {
         if (this.changed) {
            this.onChange.run();
         }
      }

      private static void validate(List<String> errors, String property, int min, int max, int value) {
         if (value < min || value > max) {
            errors.add(String.format("%s must be greater than %s and less than %s: %s", property, min, max, value));
         }
      }

      private static boolean validateGuiProperties(@Nullable IGuiProperties guiProperties) {
         if (guiProperties == null) {
            return false;
         } else {
            List<String> errors = new ArrayList<>();
            validate(errors, "guiXSize", 1, 1000000000, guiProperties.guiXSize());
            validate(errors, "guiYSize", 1, 1000000000, guiProperties.guiYSize());
            validate(errors, "screenWidth", 1, 1000000000, guiProperties.screenWidth());
            validate(errors, "screenHeight", 1, 1000000000, guiProperties.screenHeight());
            validate(errors, "guiLeft", -1000000000, 1000000000, guiProperties.guiLeft());
            validate(errors, "guiTop", -1000000000, 1000000000, guiProperties.guiTop());
            if (!errors.isEmpty()) {
               LOGGER.error("Received invalid gui properties for screen: {}\n{}", guiProperties.screenClass(), String.join("\n", errors));
               return false;
            } else {
               return true;
            }
         }
      }
   }
}
