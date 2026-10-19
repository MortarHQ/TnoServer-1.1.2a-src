package team.lodestar.lodestone.systems.enchanting;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.RecordComponent;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.LevelBasedValue;
import net.minecraft.world.item.enchantment.effects.EnchantmentEntityEffect;
import team.lodestar.lodestone.LodestoneLib;

public class LodestoneEnchantmentEntityEffectHelper {
   public static <T extends EnchantmentEntityEffect> boolean hasEnchantmentEffect(ItemStack item, LodestoneEntityEnchantmentEffectFilter<T> filter) {
      return findEnchantmentEffect(item, filter).isPresent();
   }

   public static <T extends EnchantmentEntityEffect> LocatedEnchantmentEffect<T> findEnchantmentEffect(
      ItemStack item, LodestoneEntityEnchantmentEffectFilter<T> filter
   ) {
      AtomicReference<LocatedEnchantmentEffect<T>> result = new AtomicReference<>(new LocatedEnchantmentEffect.EmptyEnchantmentEffect<>());

      try {
         LodestoneEnchantmentDataHelper.runIterationOnItem(item, filter.getEnchantmentFilter(), (enchantment, enchantmentLevel) -> {
            DataComponentMap componentMap = ((Enchantment)enchantment.value()).effects();
            Optional<T> matchingEffect = findEnchantmentEffect(item, componentMap, filter);
            matchingEffect.ifPresent(t -> result.set(new LocatedEnchantmentEffect<>((T)t, enchantment, enchantmentLevel)));
         }, () -> result.get() != null);
      } catch (Exception ignored) {
         return result.get();
      }

      return result.get();
   }

   public static <T extends EnchantmentEntityEffect> Optional<T> findEnchantmentEffect(
      ItemStack item, DataComponentMap map, LodestoneEntityEnchantmentEffectFilter<T> filter
   ) {
      List<T> effects = getEntityEffects(item, map, filter);
      if (effects.isEmpty()) {
         return Optional.empty();
      } else if (effects.size() > 1) {
         LodestoneLib.LOGGER.warn("Cannot isolate an enchantment effect instance.");
         return Optional.empty();
      } else {
         return Optional.of(effects.getFirst());
      }
   }

   public static <T extends EnchantmentEntityEffect> List<T> getEntityEffects(
      ItemStack item, DataComponentMap map, LodestoneEntityEnchantmentEffectFilter<T> filter
   ) {
      return LodestoneEnchantmentDataHelper.getMatchingEffects(item, map, filter.getClassFilter(), filter.asCondition());
   }

   public static List<LevelBasedValue> findEntityEffectValues(EnchantmentEntityEffect effect) {
      ArrayList<LevelBasedValue> values = new ArrayList<>();
      Class<? extends EnchantmentEntityEffect> clazz = effect.getClass();

      record Accessor(Class<?> type, Supplier<Object> getter) {
      }

      List<Accessor> accessors = new ArrayList<>();
      if (clazz.isRecord()) {
         for (RecordComponent component : clazz.getRecordComponents()) {
            Method accessor = component.getAccessor();
            accessors.add(new Accessor(component.getType(), () -> {
               try {
                  return accessor.invoke(effect);
               } catch (Exception e) {
                  throw new RuntimeException("Failed to access record: " + component.getName(), e);
               }
            }));
         }
      } else {
         for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            accessors.add(new Accessor(field.getType(), () -> {
               try {
                  return field.get(effect);
               } catch (Exception e) {
                  throw new RuntimeException("Failed to access field: " + field.getName(), e);
               }
            }));
         }
      }

      for (Accessor accessor : accessors) {
         Object object = accessor.getter.get();
         if (object instanceof LevelBasedValue value) {
            values.add(value);
         } else if (object instanceof Optional<?> optional && optional.isPresent() && optional.get() instanceof LevelBasedValue value) {
            values.add(value);
         }
      }

      return values;
   }
}
