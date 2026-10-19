package com.teamresourceful.resourcefullib.client.screens;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectRBTreeMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import java.util.Comparator;
import java.util.List;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

public class PriorityScreen extends BaseCursorScreen {
   private final Int2ObjectMap<List<GuiEventListener>> sortedChildren = new Int2ObjectRBTreeMap(Comparator.naturalOrder());
   private final List<GuiEventListener> cachedChildren = Lists.newArrayList();

   protected PriorityScreen(Component component) {
      super(component);
      this.sortedChildren.put(0, Lists.newArrayList());
   }

   protected <T extends GuiEventListener & NarratableEntry> T addWidget(int priority, T listener) {
      ((List)this.sortedChildren.computeIfAbsent(priority, key -> Lists.newArrayList())).add(listener);
      this.cacheChildren();
      return listener;
   }

   protected <T extends GuiEventListener & Renderable> T addRenderableWidget(int priority, T listener) {
      ((List)this.sortedChildren.computeIfAbsent(priority, key -> Lists.newArrayList())).add(listener);
      this.cacheChildren();
      this.addRenderableOnly(listener);
      return listener;
   }

   @NotNull
   protected <T extends GuiEventListener & NarratableEntry> T addWidget(T listener) {
      T value = (T)super.addWidget(listener);
      this.cacheChildren();
      return value;
   }

   protected void rebuildWidgets() {
      this.sortedChildren.clear();
      this.sortedChildren.put(0, Lists.newArrayList());
      super.rebuildWidgets();
      this.cacheChildren();
   }

   protected void removeWidget(GuiEventListener listener) {
      super.removeWidget(listener);
      ObjectIterator var2 = this.sortedChildren.values().iterator();

      while (var2.hasNext()) {
         List<GuiEventListener> value = (List<GuiEventListener>)var2.next();
         value.remove(listener);
      }

      this.cacheChildren();
   }

   protected void clearWidgets() {
      super.clearWidgets();
   }

   @NotNull
   public List<? extends GuiEventListener> children() {
      return this.cachedChildren;
   }

   private void cacheChildren() {
      this.cachedChildren.clear();
      ObjectIterator var1 = this.sortedChildren.int2ObjectEntrySet().iterator();

      while (var1.hasNext()) {
         Entry<List<GuiEventListener>> entry = (Entry<List<GuiEventListener>>)var1.next();
         int priority = entry.getIntKey();
         List<GuiEventListener> listeners = (List<GuiEventListener>)entry.getValue();
         if (priority == 0) {
            this.cachedChildren.addAll(super.children());
            this.cachedChildren.addAll(listeners);
         } else {
            this.cachedChildren.addAll(listeners);
         }
      }
   }
}
