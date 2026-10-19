package twilightforest.compat.jei.renderers;

import java.util.Objects;
import mezz.jei.api.ingredients.IIngredientHelper;
import mezz.jei.api.ingredients.IIngredientType;
import mezz.jei.api.ingredients.subtypes.UidContext;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import twilightforest.compat.jei.FakeEntityType;
import twilightforest.compat.jei.JEICompat;

public class EntityHelper implements IIngredientHelper<FakeEntityType> {
   public IIngredientType<FakeEntityType> getIngredientType() {
      return JEICompat.ENTITY_TYPE;
   }

   public String getDisplayName(FakeEntityType type) {
      return type.type().getDescription().getString();
   }

   public String getUniqueId(FakeEntityType type, UidContext context) {
      return Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(type.type())).toString();
   }

   public ResourceLocation getResourceLocation(FakeEntityType type) {
      return Objects.requireNonNull(BuiltInRegistries.ENTITY_TYPE.getKey(type.type()));
   }

   public FakeEntityType copyIngredient(FakeEntityType type) {
      return type;
   }

   public String getErrorInfo(@Nullable FakeEntityType type) {
      if (type == null) {
         return "null";
      }

      ResourceLocation name = BuiltInRegistries.ENTITY_TYPE.getKey(type.type());
      return name == null ? "unnamed sadface :(" : name.toString();
   }
}
