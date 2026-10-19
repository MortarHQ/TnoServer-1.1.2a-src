package dev.emi.emi.registry;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.stack.EmiIngredient;
import dev.emi.emi.api.stack.EmiRegistryAdapter;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.ListEmiIngredient;
import dev.emi.emi.api.stack.TagEmiIngredient;
import dev.emi.emi.config.EmiConfig;
import dev.emi.emi.data.TagExclusions;
import dev.emi.emi.platform.EmiAgnos;
import dev.emi.emi.runtime.EmiHidden;
import dev.emi.emi.runtime.EmiReloadLog;
import dev.emi.emi.util.InheritanceMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class EmiTags {
   public static final InheritanceMap<EmiRegistryAdapter<?>> ADAPTERS_BY_CLASS = new InheritanceMap<>(Maps.newHashMap());
   public static final Map<Registry<?>, EmiRegistryAdapter<?>> ADAPTERS_BY_REGISTRY = Maps.newHashMap();
   public static final ResourceLocation HIDDEN_FROM_RECIPE_VIEWERS = EmiPort.id("c", "hidden_from_recipe_viewers");
   private static final Map<TagKey<?>, ResourceLocation> MODELED_TAGS = Maps.newHashMap();
   private static final Map<Set<?>, List<TagKey<?>>> CACHED_TAGS = Maps.newHashMap();
   private static final Map<TagKey<?>, List<?>> TAG_CONTENTS = Maps.newHashMap();
   private static final Map<TagKey<?>, List<?>> TAG_VALUES = Maps.newHashMap();
   private static final Map<ResourceLocation, List<TagKey<?>>> SORTED_TAGS = Maps.newHashMap();
   public static final List<TagKey<?>> TAGS = Lists.newArrayList();
   public static TagExclusions exclusions = new TagExclusions();

   public static <T> Registry<T> getRegistry(TagKey<T> key) {
      Minecraft client = Minecraft.getInstance();
      return (Registry<T>)client.level.registryAccess().registry(key.registry()).orElse(null);
   }

   public static <T> List<EmiStack> getValues(TagKey<T> key) {
      if (TAG_VALUES.containsKey(key)) {
         EmiRegistryAdapter adapter = ADAPTERS_BY_REGISTRY.get(getRegistry(key));
         if (adapter != null) {
            List<T> values = (List<T>)TAG_VALUES.getOrDefault(key, List.of());
            return values.stream().map(t -> adapter.of((T)t, EmiPort.emptyExtraData(), 1L)).toList();
         }
      }

      return List.of();
   }

   public static <T> List<EmiStack> getRawValues(TagKey<T> key) {
      if (key.registry().equals(EmiPort.getBlockRegistry().key())) {
         return EmiUtil.values(key).map(e -> EmiStack.of((Block)e.value())).toList();
      } else {
         EmiRegistryAdapter adapter = ADAPTERS_BY_REGISTRY.get(getRegistry(key));
         if (adapter != null) {
            List<T> values = EmiUtil.values(key).<T>map(Holder::value).toList();
            return values.stream().map(t -> adapter.of((T)t, EmiPort.emptyExtraData(), 1L)).toList();
         } else {
            return List.of();
         }
      }
   }

   public static <T> EmiIngredient getIngredient(Class<T> clazz, List<EmiStack> stacks, long amount) {
      Map<T, EmiStack> map = Maps.newHashMap();

      for (EmiStack stack : stacks) {
         if (!stack.isEmpty()) {
            EmiStack existing = map.getOrDefault(stack.getKey(), null);
            if (existing != null && !stack.equals(existing)) {
               return new ListEmiIngredient(stacks, amount);
            }

            map.put((T)stack.getKey(), stack);
         }
      }

      if (map.size() == 0) {
         return EmiStack.EMPTY;
      }

      if (map.size() == 1) {
         return map.values().stream().toList().get(0).copy().setAmount(amount);
      }

      EmiRegistryAdapter<T> adapter = (EmiRegistryAdapter<T>)ADAPTERS_BY_CLASS.get(clazz);
      if (adapter == null) {
         return new ListEmiIngredient(stacks, amount);
      }

      Registry<T> registry = adapter.getRegistry();
      List<TagKey<T>> keys = (List<TagKey<T>>)CACHED_TAGS.get(map.keySet());
      if (keys != null) {
         for (TagKey<T> key : keys) {
            List<T> values = (List<T>)TAG_CONTENTS.get(key);
            values.forEach(map::remove);
         }
      } else {
         keys = Lists.newArrayList();
         Set<T> original = new HashSet<>(map.keySet());

         for (TagKey<T> key : getTags(registry)) {
            List<T> values = (List<T>)TAG_CONTENTS.get(key);
            if (values.size() >= 2) {
               if (map.keySet().containsAll(values)) {
                  values.forEach(map::remove);
                  keys.add(key);
               }

               if (map.isEmpty()) {
                  break;
               }
            }
         }

         CACHED_TAGS.put(original, keys);
      }

      if (keys == null || keys.isEmpty()) {
         return new ListEmiIngredient(stacks.stream().toList(), amount);
      } else if (map.isEmpty()) {
         return keys.size() == 1
            ? tagIngredient(keys.get(0), amount)
            : new ListEmiIngredient(keys.stream().map(k -> tagIngredient((TagKey<?>)k, 1L)).toList(), amount);
      } else {
         return new ListEmiIngredient(
            List.of(map.values().stream().map(i -> i.copy().setAmount(1L)).toList(), keys.stream().map(k -> tagIngredient((TagKey<?>)k, 1L)).toList())
               .stream()
               .flatMap(a -> a.stream())
               .toList(),
            amount
         );
      }
   }

   private static EmiIngredient tagIngredient(TagKey<?> key, long amount) {
      List<?> list = TAG_VALUES.get(key);
      if (list != null && !list.isEmpty()) {
         return list.size() == 1 ? new TagEmiIngredient(key, amount).getEmiStacks().get(0).copy().setAmount(amount) : new TagEmiIngredient(key, amount);
      } else {
         return EmiStack.EMPTY;
      }
   }

   public static <T> List<TagKey<T>> getTags(Registry<T> registry) {
      return (List<TagKey<T>>)SORTED_TAGS.getOrDefault(registry.key().location(), List.of());
   }

   public static Component getTagName(TagKey<?> key) {
      String s = getTagTranslationKey(key);
      return s == null ? EmiPort.literal("#" + key.location()) : EmiPort.translatable(s);
   }

   public static boolean hasTranslation(TagKey<?> key) {
      return getTagTranslationKey(key) != null;
   }

   @Nullable
   private static String getTagTranslationKey(TagKey<?> key) {
      ResourceLocation registry = key.registry().location();
      if (registry.getNamespace().equals("minecraft")) {
         String s = translatePrefix("tag." + registry.getPath().replace("/", ".") + ".", key.location());
         if (s != null) {
            return s;
         }
      } else {
         String s = translatePrefix("tag." + registry.getNamespace() + "." + registry.getPath().replace("/", ".") + ".", key.location());
         if (s != null) {
            return s;
         }
      }

      return translatePrefix("tag.", key.location());
   }

   @Nullable
   private static String translatePrefix(String prefix, ResourceLocation id) {
      String s = EmiUtil.translateId(prefix, id);
      if (I18n.exists(s)) {
         return s;
      }

      if (id.getNamespace().equals("forge")) {
         s = EmiUtil.translateId(prefix, EmiPort.id("c", id.getPath()));
         if (I18n.exists(s)) {
            return s;
         }
      }

      return null;
   }

   @Nullable
   public static ResourceLocation getCustomModel(TagKey<?> key) {
      ResourceLocation rid = key.location();
      if (rid.getNamespace().equals("forge") && !MODELED_TAGS.containsKey(key)) {
         key = TagKey.create(key.registry(), EmiPort.id("c", rid.getPath()));
      }

      return MODELED_TAGS.get(key);
   }

   public static boolean hasCustomModel(TagKey<?> key) {
      return getCustomModel(key) != null;
   }

   public static void registerTagModels(ResourceManager manager, Consumer<ModelResourceLocation> consumer, String variant) {
      MODELED_TAGS.clear();

      for (ResourceLocation id : EmiPort.findResources(manager, "models/tag", s -> s.endsWith(".json"))) {
         String path = id.getPath();
         path = path.substring(11, path.length() - 5);
         String[] parts = path.split("/");
         if (parts.length > 1) {
            TagKey<?> key = TagKey.create(
               ResourceKey.createRegistryKey(EmiPort.id("minecraft", parts[0])), EmiPort.id(id.getNamespace(), path.substring(1 + parts[0].length()))
            );
            ResourceLocation mid = EmiPort.id(id.getNamespace(), "tag/" + path);
            MODELED_TAGS.put(key, mid);
            consumer.accept(new ModelResourceLocation(mid, variant));
         }
      }
   }

   public static void reload() {
      TAGS.clear();
      SORTED_TAGS.clear();
      TAG_CONTENTS.clear();
      TAG_VALUES.clear();
      CACHED_TAGS.clear();

      for (Registry<?> registry : ADAPTERS_BY_REGISTRY.keySet()) {
         reloadTags(registry);
      }
   }

   private static <T> void reloadTags(Registry<T> registry) {
      Set<T> hidden = EmiUtil.values(TagKey.create(registry.key(), HIDDEN_FROM_RECIPE_VIEWERS)).<T>map(Holder::value).collect(Collectors.toSet());
      ResourceLocation rid = registry.key().location();
      List<TagKey<T>> tags = registry.getTagNames()
         .filter(keyx -> !exclusions.contains(rid, keyx.location()) && !hidden.containsAll(EmiUtil.values(keyx).map(Holder::value).toList()))
         .toList();
      logUntranslatedTags(tags);
      tags = consolodateTags(tags);

      for (TagKey<T> key : tags) {
         List<T> contents = EmiUtil.values(key).map(i -> (T)i.value()).toList();
         TAG_CONTENTS.put(key, contents);
         List<T> values = contents.stream().filter(s -> !EmiHidden.isDisabled(stackFromKey(key, (T)s))).toList();
         if (values.isEmpty()) {
            TAG_VALUES.put(key, contents);
         } else {
            TAG_VALUES.put(key, values);
         }
      }

      TAGS.addAll(tags.stream().sorted((a, b) -> a.toString().compareTo(b.toString())).toList());
      tags = tags.stream().sorted((a, b) -> Long.compare(EmiUtil.values((TagKey<T>)b).count(), EmiUtil.values((TagKey<T>)a).count())).toList();
      SORTED_TAGS.put(registry.key().location(), tags);
   }

   private static <T> EmiStack stackFromKey(TagKey<T> key, T t) {
      EmiRegistryAdapter<T> adapter = (EmiRegistryAdapter<T>)ADAPTERS_BY_REGISTRY.get(getRegistry(key));
      if (adapter != null) {
         return adapter.of(t, EmiPort.emptyExtraData(), 1L);
      } else {
         throw new UnsupportedOperationException("Unsupported tag registry " + key);
      }
   }

   private static <T> void logUntranslatedTags(List<TagKey<T>> tags) {
      if (EmiConfig.logUntranslatedTags) {
         List<String> untranslated = Lists.newArrayList();

         for (TagKey<T> tag : tags) {
            if (!hasTranslation(tag)) {
               untranslated.add(tag.location().toString());
            }
         }

         if (!untranslated.isEmpty()) {
            for (String tag : untranslated.stream().sorted().toList()) {
               EmiReloadLog.warn("Untranslated tag #" + tag);
            }

            EmiReloadLog.info(" Tag warning can be disabled in the config, EMI docs describe how to add a translation or exclude tags.");
         }
      }
   }

   private static <T> List<TagKey<T>> consolodateTags(List<TagKey<T>> tags) {
      Map<Set<T>, TagKey<T>> map = Maps.newHashMap();

      for (int i = 0; i < tags.size(); i++) {
         TagKey<T> key = tags.get(i);
         Set<T> values = EmiUtil.values(key).<T>map(Holder::value).collect(Collectors.toSet());
         TagKey<T> original = map.get(values);
         if (original != null) {
            map.put(values, betterTag(key, original));
         } else {
            map.put(values, key);
         }
      }

      return map.values().stream().toList();
   }

   private static <T> TagKey<T> betterTag(TagKey<T> a, TagKey<T> b) {
      if (hasTranslation(a) != hasTranslation(b)) {
         return hasTranslation(a) ? a : b;
      }

      if (hasCustomModel(a) != hasCustomModel(b)) {
         return hasCustomModel(a) ? a : b;
      }

      String an = a.location().getNamespace();
      String bn = b.location().getNamespace();
      if (!an.equals(bn)) {
         if (an.equals("minecraft")) {
            return a;
         }

         if (bn.equals("minecraft")) {
            return b;
         }

         if (an.equals("c")) {
            return a;
         }

         if (bn.equals("c")) {
            return b;
         }

         if (an.equals("fabric")) {
            return EmiAgnos.isModLoaded("forge") ? b : a;
         }

         if (bn.equals("fabric")) {
            return EmiAgnos.isModLoaded("forge") ? a : b;
         }

         if (an.equals("forge")) {
            return EmiAgnos.isModLoaded("forge") ? a : b;
         }

         if (bn.equals("forge")) {
            return EmiAgnos.isModLoaded("forge") ? b : a;
         }
      }

      return a.location().toString().length() <= b.location().toString().length() ? a : b;
   }
}
