package mezz.jei.library.helpers;

import com.google.common.collect.ImmutableSetMultimap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.helpers.IModIdHelper;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.ITypedIngredient;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.common.platform.IPlatformModHelper;
import mezz.jei.common.platform.Services;
import mezz.jei.library.config.IModIdFormatConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.apache.commons.lang3.StringUtils;

public final class ModIdHelper implements IModIdHelper {
   private final IModIdFormatConfig modIdFormattingConfig;
   private final IIngredientManager ingredientManager;
   private final ImmutableSetMultimap<String, String> modAliases;

   public ModIdHelper(IModIdFormatConfig modIdFormattingConfig, IIngredientManager ingredientManager, ImmutableSetMultimap<String, String> modAliases) {
      this.modIdFormattingConfig = modIdFormattingConfig;
      this.ingredientManager = ingredientManager;
      this.modAliases = modAliases;
   }

   @Override
   public boolean isDisplayingModNameEnabled() {
      String modNameFormat = this.modIdFormattingConfig.getModNameFormat();
      return !modNameFormat.isEmpty();
   }

   @Override
   public <T> List<Component> addModNameToIngredientTooltip(List<Component> tooltip, T ingredient, IIngredientHelper<T> ingredientHelper) {
      if (!this.isDisplayingModNameEnabled()) {
         return tooltip;
      }

      if (this.modIdFormattingConfig.isModNameFormatOverrideActive() && ingredient instanceof ItemStack) {
         return tooltip;
      }

      String modId = ingredientHelper.getDisplayModId(ingredient);
      String modName = this.getFormattedModNameForModId(modId);
      List<Component> tooltipCopy = new ArrayList<>(tooltip);
      tooltipCopy.add(Component.literal(modName));
      return tooltipCopy;
   }

   @Override
   public <T> Optional<Component> getModNameForTooltip(ITypedIngredient<T> typedIngredient) {
      if (!this.isDisplayingModNameEnabled()) {
         return Optional.empty();
      }

      IIngredientType<T> type = typedIngredient.getType();
      if (this.modIdFormattingConfig.isModNameFormatOverrideActive() && type == VanillaTypes.ITEM_STACK) {
         return Optional.empty();
      }

      T ingredient = typedIngredient.getIngredient();
      IIngredientHelper<T> ingredientHelper = this.ingredientManager.getIngredientHelper(type);
      String modId = ingredientHelper.getDisplayModId(ingredient);
      String modName = this.getFormattedModNameForModId(modId);
      return Optional.of(Component.literal(modName));
   }

   @Override
   public <T> List<Component> addModNameToIngredientTooltip(List<Component> tooltip, ITypedIngredient<T> typedIngredient) {
      IIngredientType<T> type = typedIngredient.getType();
      T ingredient = typedIngredient.getIngredient();
      IIngredientHelper<T> ingredientHelper = this.ingredientManager.getIngredientHelper(type);
      return this.addModNameToIngredientTooltip(tooltip, ingredient, ingredientHelper);
   }

   @Override
   public String getFormattedModNameForModId(String modId) {
      String modName = this.getModNameForModId(modId);
      modName = ChatFormatting.stripFormatting(modName);
      String modNameFormat = this.modIdFormattingConfig.getModNameFormat();
      if (!modNameFormat.isEmpty()) {
         return modNameFormat.contains("%MODNAME%") ? StringUtils.replaceOnce(modNameFormat, "%MODNAME%", modName) : modNameFormat + modName;
      } else {
         return modName;
      }
   }

   @Override
   public Set<String> getModAliases(String modId) {
      return this.modAliases.get(modId);
   }

   @Override
   public String getModNameForModId(String modId) {
      IPlatformModHelper modHelper = Services.PLATFORM.getModHelper();
      return modHelper.getModNameForModId(modId);
   }
}
