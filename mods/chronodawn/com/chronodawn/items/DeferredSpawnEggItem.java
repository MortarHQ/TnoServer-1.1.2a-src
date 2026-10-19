package com.chronodawn.items;

import com.chronodawn.ChronoDawn;
import dev.architectury.registry.registries.RegistrySupplier;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.Item.Properties;

public class DeferredSpawnEggItem extends SpawnEggItem {
   private final Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier;
   private final int backgroundColor;
   private final int highlightColor;

   public DeferredSpawnEggItem(Supplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties properties) {
      super(null, backgroundColor, highlightColor, properties);
      this.entityTypeSupplier = entityTypeSupplier;
      this.backgroundColor = backgroundColor;
      this.highlightColor = highlightColor;
   }

   public void initializeSpawnEgg() {
      this.registerColorsToVanillaMap();
   }

   private void registerColorsToVanillaMap() {
      try {
         Field byIdField = null;
         String[] possibleFieldNames = new String[]{"BY_ID", "f_43221_", "byId", "BY_COLOR"};

         for (String fieldName : possibleFieldNames) {
            try {
               byIdField = SpawnEggItem.class.getDeclaredField(fieldName);
               break;
            } catch (NoSuchFieldException var8) {
            }
         }

         if (byIdField == null) {
            Field[] fields = SpawnEggItem.class.getDeclaredFields();

            for (Field field : fields) {
               if (Modifier.isStatic(field.getModifiers()) && field.getType() == Map.class) {
                  byIdField = field;
                  break;
               }
            }
         }

         if (byIdField == null) {
            ChronoDawn.LOGGER.warn("Could not find BY_ID field in SpawnEggItem");
            return;
         }

         byIdField.setAccessible(true);
         Map<EntityType<? extends Mob>, SpawnEggItem> byIdMap = (Map<EntityType<? extends Mob>, SpawnEggItem>)byIdField.get(null);
         EntityType<?> entityType = (EntityType<?>)this.entityTypeSupplier.get();
         if (entityType != null) {
            byIdMap.put((EntityType<? extends Mob>)entityType, this);
         }
      } catch (Exception e) {
         ChronoDawn.LOGGER.warn("Failed to register spawn egg colors to vanilla map via reflection", e);
      }
   }

   public DeferredSpawnEggItem(
      RegistrySupplier<? extends EntityType<? extends Mob>> entityTypeSupplier, int backgroundColor, int highlightColor, Properties properties
   ) {
      this((Supplier<? extends EntityType<? extends Mob>>)entityTypeSupplier, backgroundColor, highlightColor, properties);
   }

   public EntityType<?> getType(ItemStack stack) {
      return (EntityType<?>)this.entityTypeSupplier.get();
   }

   public FeatureFlagSet requiredFeatures() {
      EntityType<?> entityType = (EntityType<?>)this.entityTypeSupplier.get();
      return entityType != null ? entityType.requiredFeatures() : FeatureFlagSet.of();
   }

   public int getColor(int tintIndex) {
      return tintIndex == 0 ? this.backgroundColor : this.highlightColor;
   }
}
