package com.finderfeed.fdlib.systems.screen;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;
import org.jetbrains.annotations.NotNull;

public class FDSCChildren implements Iterable<FDScreenComponent> {
   private FDScreenComponent owner;
   private HashMap<String, FDScreenComponent> children;
   private FDScreen screen;

   public FDSCChildren(FDScreenComponent parent, FDScreen screen) {
      this.screen = screen;
      this.owner = parent;
      this.children = new LinkedHashMap<>();
   }

   public void tick() {
      Iterator<Entry<String, FDScreenComponent>> iterator = this.children.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<String, FDScreenComponent> entry = iterator.next();
         FDScreenComponent cmp = entry.getValue();
         if (cmp.isRemoved()) {
            iterator.remove();
            cmp.setParent(null);
            this.getScreen().removeComponent(cmp.getUniqueId());
         } else {
            cmp.tick();
         }
      }
   }

   public void removeChild(String id) {
      FDScreenComponent component = this.children.remove(id);
      this.owner.onChildRemoved(component);
      component.setParent(null);
      this.screen.removeComponent(id);
   }

   public void setAsChild(FDScreenComponent child) {
      String id = child.getUniqueId();
      if (this.screen.getComponentById(id) == null) {
         this.screen.addComponent(child);
      }

      FDScreenComponent parent = child.getParent();
      if (parent != null) {
         FDSCChildren c = parent.getChildren();
         c.children.remove(id);
      }

      child.setParent(this.getOwner());
      this.children.put(id, child);
      this.owner.onChildAdded(child);
   }

   public void removeAllChildren() {
      Iterator<Entry<String, FDScreenComponent>> iterator = this.children.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<String, FDScreenComponent> entry = iterator.next();
         iterator.remove();
         this.owner.onChildRemoved(entry.getValue());
         entry.getValue().setParent(null);
         this.screen.removeComponent(entry.getKey());
      }
   }

   public boolean hasChild(String id) {
      return this.children.containsKey(id);
   }

   public FDScreen getScreen() {
      return this.screen;
   }

   public FDScreenComponent getOwner() {
      return this.owner;
   }

   public List<FDScreenComponent> getChildren() {
      return this.children.values().stream().toList();
   }

   @NotNull
   @Override
   public Iterator<FDScreenComponent> iterator() {
      return this.children.values().iterator();
   }
}
