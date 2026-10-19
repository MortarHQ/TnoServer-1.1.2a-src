package io.github.Memoires.mysticism.util.effect;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.WeakHashMap;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;

public class EffectValues {
   private static final Map<LivingEntity, Map<ResourceLocation, EffectValuesData>> BY_ENTITY = new WeakHashMap<>();

   private EffectValues() {
   }

   private static ResourceLocation idOf(Holder<MobEffect> holder) {
      Optional<ResourceLocation> key = holder.unwrapKey().map(ResourceKey::location);
      return key.orElseThrow(() -> new IllegalStateException("Unregistered MobEffect holder"));
   }

   private static Map<ResourceLocation, EffectValuesData> getOrCreateEntityMap(LivingEntity target) {
      return BY_ENTITY.computeIfAbsent(target, e -> new Object2ObjectOpenHashMap());
   }

   private static EffectValuesData getOrCreateData(LivingEntity target, Holder<MobEffect> effect) {
      Map<ResourceLocation, EffectValuesData> map = getOrCreateEntityMap(target);
      return map.computeIfAbsent(idOf(effect), e -> new EffectValuesData());
   }

   public static void set(LivingEntity target, Holder<MobEffect> effect, String key, Object value) {
      EffectValuesData data = getOrCreateData(target, effect);
      data.set(key, value);
      if (data.isEmpty()) {
         clear(target, effect);
      }
   }

   public static Object get(LivingEntity target, Holder<MobEffect> effect, String key) {
      EffectValuesData data = getData(target, effect);
      return data != null ? data.get(key) : null;
   }

   public static boolean has(LivingEntity target, Holder<MobEffect> effect, String key) {
      EffectValuesData data = getData(target, effect);
      return data != null && data.has(key);
   }

   public static void remove(LivingEntity target, Holder<MobEffect> effect, String key) {
      EffectValuesData data = getData(target, effect);
      if (data != null) {
         data.remove(key);
         if (data.isEmpty()) {
            clear(target, effect);
         }
      }
   }

   public static EffectValuesData getData(LivingEntity target, Holder<MobEffect> effect) {
      Map<ResourceLocation, EffectValuesData> map = BY_ENTITY.get(target);
      return map == null ? null : map.get(idOf(effect));
   }

   public static void clear(LivingEntity target, Holder<MobEffect> effect) {
      Map<ResourceLocation, EffectValuesData> map = BY_ENTITY.get(target);
      if (map != null) {
         map.remove(idOf(effect));
         if (map.isEmpty()) {
            BY_ENTITY.remove(target);
         }
      }
   }
}
