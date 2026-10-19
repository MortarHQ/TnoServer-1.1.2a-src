package team.creative.creativecore.common.util.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Predicate;
import net.minecraft.network.chat.Component;
import team.creative.creativecore.client.render.text.CompiledText;
import team.creative.creativecore.common.util.type.list.TupleList;

public class TextMapBuilder<K> implements IComponentMap<K> {
   private final LinkedHashMap<K, List<Component>> lines = new LinkedHashMap<>();
   private Predicate<String> filter;

   public TextMapBuilder<K> setFilter(Predicate<String> predicate) {
      this.filter = predicate;
      return this;
   }

   private void addNewLine(K key, Component line) {
      List<Component> newLine = new ArrayList<>();
      newLine.add(line);
      this.addNewLine(key, newLine);
   }

   private void addNewLine(K key, List<Component> line) {
      if (this.filter != null) {
         StringBuilder builder = new StringBuilder();

         for (Component component : line) {
            builder.append(component.getString());
         }

         if (!this.filter.test(builder.toString())) {
            return;
         }
      }

      this.lines.put(key, line);
   }

   public TextMapBuilder<K> addComponent(K key, Component component) {
      this.addNewLine(key, component);
      return this;
   }

   public TextMapBuilder<K> addComponent(K[] array, Function<K, Component> toComponent) {
      for (int i = 0; i < array.length; i++) {
         this.addNewLine(array[i], toComponent.apply(array[i]));
      }

      return this;
   }

   public TextMapBuilder<K> addComponent(Iterable<K> collection, Function<K, Component> toComponent) {
      for (K t : collection) {
         this.addNewLine(t, toComponent.apply(t));
      }

      return this;
   }

   public TextMapBuilder<K> addComponent(Iterator<K> collection, Function<K, Component> toComponent) {
      Iterator<K> iterator = collection;

      while (iterator.hasNext()) {
         K t = iterator.next();
         this.addNewLine(t, toComponent.apply(t));
      }

      return this;
   }

   public TextMapBuilder<K> addEntrySet(Set<Entry<String, K>> map, Function<Entry<String, K>, Component> toComponent) {
      for (Entry<String, K> entry : map) {
         this.addNewLine(entry.getValue(), toComponent.apply(entry));
      }

      return this;
   }

   public TextMapBuilder<K> addComponents(Iterable<K> collection, Function<K, List<Component>> toComponent) {
      for (K t : collection) {
         this.addNewLine(t, toComponent.apply(t));
      }

      return this;
   }

   public int size() {
      return this.lines.size();
   }

   public K first() {
      return this.lines.keySet().iterator().next();
   }

   @Override
   public TupleList<K, CompiledText> build() {
      TupleList<K, CompiledText> list = new TupleList<>(this.lines.size());

      for (Entry<K, List<Component>> entry : this.lines.entrySet()) {
         CompiledText text = CompiledText.createAnySize();
         text.setText(entry.getValue());
         list.add(entry.getKey(), text);
      }

      return list;
   }

   @Override
   public Set<Entry<K, List<Component>>> entrySet() {
      return this.lines.entrySet();
   }

   @Override
   public Collection<List<Component>> values() {
      return this.lines.values();
   }

   @Override
   public List<K> keys() {
      return new ArrayList<>(this.lines.keySet());
   }
}
