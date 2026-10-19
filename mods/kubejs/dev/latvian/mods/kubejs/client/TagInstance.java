package dev.latvian.mods.kubejs.client;

import dev.latvian.mods.kubejs.plugin.builtin.wrapper.TextIcons;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.TextWrapper;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import org.jetbrains.annotations.NotNull;

public class TagInstance implements Comparable<TagInstance> {
   public final ResourceLocation tag;
   public final Set<TagInstance.Type> registries;

   public TagInstance(ResourceLocation tag) {
      this.tag = tag;
      this.registries = new LinkedHashSet<>(2);
   }

   public Component toText() {
      StringBuilder sb = new StringBuilder(this.registries.size() + 1);
      sb.append('.');

      for (TagInstance.Type type : this.registries) {
         sb.append(type.character);
      }

      return Component.empty()
         .append(TextIcons.icon(Component.literal("T.")))
         .append(TextWrapper.darkGray(Component.literal("#" + this.tag)))
         .append(TextIcons.icon(Component.literal(sb.toString())));
   }

   public int compareTo(@NotNull TagInstance o) {
      return this.tag.compareNamespaced(o.tag);
   }

   public enum Type {
      BLOCK('B'),
      ITEM('J'),
      FLUID('F'),
      ENTITY('E');

      public final char character;

      Type(char c) {
         this.character = c;
      }

      public void append(Map<ResourceLocation, TagInstance> map, Stream<? extends TagKey<?>> tags) {
         tags.forEach(tag -> map.computeIfAbsent(tag.location(), TagInstance::new).registries.add(this));
      }
   }
}
