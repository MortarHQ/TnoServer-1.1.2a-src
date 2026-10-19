package net.darkhax.bookshelf.common.api.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.darkhax.bookshelf.common.api.PhysicalSide;
import net.darkhax.bookshelf.common.api.annotation.OnlyFor;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.bookshelf.common.api.text.unit.Units;
import net.darkhax.bookshelf.common.mixin.access.client.AccessorFontManager;
import net.darkhax.bookshelf.common.mixin.access.client.AccessorMinecraft;
import net.darkhax.bookshelf.common.mixin.access.entity.AccessorEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.HoverEvent.Action;
import net.minecraft.network.chat.HoverEvent.EntityTooltipInfo;
import net.minecraft.network.chat.HoverEvent.ItemStackInfo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringUtil;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.Nullable;

public class TextHelper {
   public static MutableComponent fromResourceLocation(@Nullable String prefix, @Nullable String suffix, ResourceLocation location, Object... args) {
      StringBuilder builder = new StringBuilder();
      if (prefix != null) {
         builder.append(prefix).append(".");
      }

      builder.append(location.getNamespace()).append(".").append(location.getPath());
      if (suffix != null) {
         builder.append(".").append(suffix);
      }

      return Component.translatable(builder.toString(), args);
   }

   public static MutableComponent formatDuration(int ticks) {
      return formatDuration(ticks, true, 1.0F);
   }

   public static MutableComponent formatDuration(int ticks, Level level) {
      return formatDuration(ticks, true, level);
   }

   public static MutableComponent formatDuration(int ticks, boolean includeHover, Level level) {
      return formatDuration(ticks, includeHover, level.tickRateManager().tickrate());
   }

   public static MutableComponent formatDuration(int ticks, boolean showTicksOnHover, float tickRate) {
      MutableComponent timeText = Component.literal(StringUtil.formatTickDuration(ticks, tickRate));
      if (showTicksOnHover) {
         timeText = Units.TICK.format(ticks);
      }

      return timeText;
   }

   public static MutableComponent withHover(Component base, Component hover) {
      return withHover(base, new HoverEvent(Action.SHOW_TEXT, hover));
   }

   public static MutableComponent withHover(Component base, Entity hover) {
      return withHover(base, hoverEvent(hover));
   }

   public static MutableComponent withHover(Component base, ItemStack hover) {
      return withHover(base, new HoverEvent(Action.SHOW_ITEM, new ItemStackInfo(hover)));
   }

   public static MutableComponent withHover(Component base, HoverEvent hover) {
      return mutable(base).withStyle(style -> style.withHoverEvent(hover));
   }

   public static HoverEvent hoverEvent(Entity entity) {
      return entity instanceof AccessorEntity access
         ? access.bookshelf$createHoverEvent()
         : new HoverEvent(Action.SHOW_ENTITY, new EntityTooltipInfo(entity.getType(), entity.getUUID(), entity.getName()));
   }

   public static MutableComponent mutable(Component component) {
      return component instanceof MutableComponent mutable ? mutable : component.copy();
   }

   public static Component applyFont(Component text, ResourceLocation font) {
      if (text == CommonComponents.EMPTY) {
         return text;
      }

      MutableComponent modified = mutable(text);
      modified.withStyle(style -> style.withFont(font));
      modified.getSiblings().forEach(sib -> applyFont(sib, font));
      return modified;
   }

   @Nullable
   @OnlyFor(PhysicalSide.CLIENT)
   public static MutableComponent lookupTranslationWithAlias(ResourceLocation id, String... keys) {
      for (String key : keys) {
         MutableComponent lookupResult = lookupTranslation(key.formatted(id.getNamespace(), id.getPath()));
         if (lookupResult != null) {
            return lookupResult;
         }
      }

      return null;
   }

   @Nullable
   @OnlyFor(PhysicalSide.CLIENT)
   public static MutableComponent lookupTranslationWithAlias(String[] keys, Object... params) {
      for (String key : keys) {
         MutableComponent lookupResult = lookupTranslation(key, params);
         if (lookupResult != null) {
            return lookupResult;
         }
      }

      return null;
   }

   @Nullable
   @OnlyFor(PhysicalSide.CLIENT)
   public static MutableComponent lookupTranslation(String key, Object... args) {
      return lookupTranslation(key, (s, o) -> null, args);
   }

   @Nullable
   @OnlyFor(PhysicalSide.CLIENT)
   public static MutableComponent lookupTranslation(String key, MutableComponent fallback, Object... args) {
      return lookupTranslation(key, (s, o) -> fallback, args);
   }

   @Nullable
   @OnlyFor(PhysicalSide.CLIENT)
   public static MutableComponent lookupTranslation(String key, @Nullable BiFunction<String, Object[], MutableComponent> fallback, Object... args) {
      if (!Services.PLATFORM.isPhysicalClient()) {
         throw new IllegalStateException("Text can not be translated on the server.");
      } else {
         return I18n.exists(key) ? Component.translatable(key, args) : (fallback != null ? fallback.apply(key, args) : null);
      }
   }

   public static MutableComponent copyText(String text) {
      return setCopyText(Component.literal(text), text);
   }

   public static MutableComponent setCopyText(MutableComponent component, String copy) {
      return component.withStyle(style -> style.withClickEvent(new ClickEvent(net.minecraft.network.chat.ClickEvent.Action.COPY_TO_CLIPBOARD, copy)));
   }

   public static MutableComponent join(Component separator, Component... toJoin) {
      return join(separator, Arrays.stream(toJoin).iterator());
   }

   public static MutableComponent join(Component separator, Collection<Component> toJoin) {
      return join(separator, toJoin.iterator());
   }

   public static MutableComponent joinUnique(Component separator, Collection<Component> toJoin) {
      Set<Component> entries = new HashSet<>();

      for (Component toAdd : toJoin) {
         addUnique(entries, toAdd);
      }

      return join(separator, entries);
   }

   public static boolean addUnique(Collection<Component> components, Component toAdd) {
      for (Component existing : components) {
         if (Objects.equals(existing, toAdd) || existing.getContents().equals(toAdd.getContents())) {
            return false;
         }
      }

      components.add(toAdd);
      return true;
   }

   public static MutableComponent join(Component separator, Iterator<Component> toJoin) {
      MutableComponent joined = Component.literal("");

      while (toJoin.hasNext()) {
         joined.append(toJoin.next());
         if (toJoin.hasNext()) {
            joined.append(separator);
         }
      }

      return joined;
   }

   public static Set<String> getPossibleMatches(String input, Iterable<String> candidates) {
      return getPossibleMatches(input, candidates, Integer.MAX_VALUE);
   }

   public static Set<String> getPossibleMatches(String input, Iterable<String> candidates, int threshold) {
      HashSet<String> bestMatches = new HashSet<>();
      int distance = threshold;

      for (String candidate : candidates) {
         int currentDistance = StringUtils.getLevenshteinDistance(input, candidate);
         if (currentDistance < distance) {
            distance = currentDistance;
            bestMatches.clear();
            bestMatches.add(candidate);
         } else if (currentDistance == distance) {
            bestMatches.add(candidate);
         }
      }

      return bestMatches;
   }

   public static <T> String formatCollection(Collection<T> collection) {
      return formatCollection(collection, entry -> "\"" + entry.toString() + "\"", ", ");
   }

   public static <T> String formatCollection(Collection<T> collection, Function<T, String> formatter, String delimiter) {
      return collection.size() == 1
         ? formatter.apply(collection.stream().findFirst().get())
         : collection.stream().map(formatter).collect(Collectors.joining(delimiter));
   }

   @OnlyFor(PhysicalSide.CLIENT)
   public static Set<ResourceLocation> getRegisteredFonts() {
      return !Services.PLATFORM.isPhysicalClient()
         ? Collections.emptySet()
         : ((AccessorFontManager)((AccessorMinecraft)Minecraft.getInstance()).bookshelf$getFontManager()).bookshelf$getFonts().keySet();
   }

   public static String getTagName(TagKey<?> tag) {
      StringBuilder builder = new StringBuilder();
      builder.append("tag.");
      ResourceLocation regId = tag.registry().location();
      ResourceLocation tagId = tag.location();
      if (!regId.getNamespace().equals("minecraft")) {
         builder.append(regId.getNamespace()).append(".");
      }

      builder.append(regId.getPath().replace("/", "."))
         .append(".")
         .append(tagId.getNamespace())
         .append(".")
         .append(tagId.getPath().replace("/", ".").replace(":", "."));
      return builder.toString();
   }
}
