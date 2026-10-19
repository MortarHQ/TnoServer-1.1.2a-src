package team.creative.creativecore.common.util.type.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class CheckTree<T> {
   public final BiConsumer<T, Boolean> setter;
   public final Function<T, Boolean> getter;
   public final Function<T, Collection<? extends T>> getChildren;
   public final CheckTree<T>.CheckTreeEntry root;

   public CheckTree(T root, BiConsumer<T, Boolean> setter, Function<T, Boolean> getter, Function<T, Collection<? extends T>> getChildren) {
      this.setter = setter;
      this.getter = getter;
      this.getChildren = getChildren;
      this.root = new CheckTree.CheckTreeEntry(null, root);
   }

   public CheckTree(List<T> rootFields, BiConsumer<T, Boolean> setter, Function<T, Boolean> getter, Function<T, Collection<? extends T>> getChildren) {
      this.setter = setter;
      this.getter = getter;
      this.getChildren = getChildren;
      this.root = new CheckTree.CheckTreeEntry(rootFields);
   }

   public void reload() {
      this.reload(this.root);
   }

   private void reload(CheckTree<T>.CheckTreeEntry entry) {
      if (entry.content != null) {
         entry.enabled = this.getter.apply(entry.content);
      }

      if (entry.children != null) {
         for (int i = 0; i < entry.children.size(); i++) {
            this.reload(entry.children.get(i));
         }
      }
   }

   public void apply() {
      this.apply(this.root);
   }

   private void apply(CheckTree<T>.CheckTreeEntry entry) {
      if (entry.content != null) {
         this.setter.accept(entry.content, entry.enabled);
      }

      if (entry.children != null) {
         for (int i = 0; i < entry.children.size(); i++) {
            this.apply(entry.children.get(i));
         }
      }
   }

   public class CheckTreeEntry {
      public final CheckTree<T>.CheckTreeEntry parent;
      public final T content;
      protected boolean enabled;
      public List<CheckTree<T>.CheckTreeEntry> children;

      public CheckTreeEntry(List<T> children) {
         this.parent = null;
         this.children = new ArrayList<>();

         for (T t : children) {
            this.children.add(CheckTree.this.new CheckTreeEntry(this, t));
         }

         this.content = null;
      }

      public CheckTreeEntry(CheckTree<T>.CheckTreeEntry parent, T content) {
         this.parent = parent;
         this.content = content;
         Collection<? extends T> entries = CheckTree.this.getChildren.apply(content);
         if (entries == null) {
            this.children = null;
         } else {
            this.children = new ArrayList<>(entries.size());

            for (T t : entries) {
               this.children.add(CheckTree.this.new CheckTreeEntry(this, t));
            }
         }

         this.enabled = CheckTree.this.getter.apply(content);
      }

      public boolean isEnabled() {
         if (!this.enabled) {
            return false;
         }

         if (this.children != null && !this.children.isEmpty()) {
            for (CheckTree<T>.CheckTreeEntry entry : this.children) {
               if (!entry.isEnabled()) {
                  return false;
               }
            }

            return true;
         } else {
            return true;
         }
      }

      public boolean isChildEnabled() {
         if (this.children != null) {
            for (CheckTree<T>.CheckTreeEntry entry : this.children) {
               if (entry.isEnabled() || entry.isChildEnabled()) {
                  return true;
               }
            }
         }

         return false;
      }

      public void enable() {
         this.enableInteral();
         if (this.parent != null) {
            this.parent.enabled = this.parent.isChildEnabled();
         }
      }

      public void disable() {
         this.disableInteral();
         if (this.parent != null) {
            this.parent.enabled = false;
         }
      }

      protected void enableInteral() {
         if (this.children != null) {
            for (CheckTree<T>.CheckTreeEntry entry : this.children) {
               entry.enableInteral();
            }
         }

         this.enabled = true;
      }

      protected void disableInteral() {
         if (this.children != null) {
            for (CheckTree<T>.CheckTreeEntry entry : this.children) {
               entry.disableInteral();
            }
         }

         this.enabled = false;
      }

      public void toggle() {
         if (this.isEnabled()) {
            this.disable();
         } else {
            this.enable();
         }
      }
   }
}
