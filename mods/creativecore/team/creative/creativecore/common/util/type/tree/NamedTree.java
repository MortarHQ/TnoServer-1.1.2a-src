package team.creative.creativecore.common.util.type.tree;

import com.google.common.base.Objects;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Set;
import java.util.Map.Entry;
import team.creative.creativecore.common.util.type.itr.ConsecutiveIterator;
import team.creative.creativecore.common.util.type.itr.FilterIterator;
import team.creative.creativecore.common.util.type.itr.NestedFunctionIterator;
import team.creative.creativecore.common.util.type.itr.SingleIterator;

public class NamedTree<T> {
   private final NamedTree<T> parent;
   private final String name;
   private final LinkedHashMap<String, NamedTree<T>> children = new LinkedHashMap<>();
   public T value;

   public NamedTree() {
      this.parent = null;
      this.name = null;
   }

   private NamedTree(NamedTree<T> parent, String name) {
      this.parent = parent;
      this.name = name;
   }

   public T add(String path, T value) {
      String[] parts = path.split("\\.");
      this.folderForce(parts, 0).value = value;
      return value;
   }

   public Collection<NamedTree<T>> children() {
      return this.children.values();
   }

   public Set<Entry<String, NamedTree<T>>> entries() {
      return this.children.entrySet();
   }

   public T get(String path) {
      NamedTree<T> result = this.folder(path);
      return result != null ? result.value : null;
   }

   public NamedTree<T> folder(String path) {
      return this.folder(path.split("\\."), 0);
   }

   private NamedTree<T> folder(String[] path, int index) {
      NamedTree<T> folder = this.children.get(path[index]);
      if (folder == null) {
         return null;
      }

      index++;
      return path.length <= index ? folder : folder.folder(path, index);
   }

   public NamedTree<T> folderForce(String path) {
      return this.folderForce(path.split("\\."), 0);
   }

   private NamedTree<T> folderForce(String[] path, int index) {
      return this.folderForce(path, index, path.length);
   }

   private NamedTree<T> folderForce(String[] path, int index, int end) {
      NamedTree<T> folder = this.children.get(path[index]);
      if (folder == null) {
         folder = new NamedTree<>(this, path[index]);
         this.children.put(path[index], folder);
      }

      index++;
      return end <= index ? folder : folder.folderForce(path, index);
   }

   public String path() {
      String path = "";
      if (this.parent != null) {
         path = this.parent.path();
         if (!path.isBlank() && this.name != null) {
            path = path + ".";
         }
      }

      return path + (this.name != null ? this.name : "");
   }

   public String findPath(T value) {
      if (Objects.equal(this.value, value)) {
         return this.name;
      }

      for (NamedTree<T> child : this.children.values()) {
         String path = child.findPath(value);
         if (path != null) {
            return (this.name != null ? this.name + "." : "") + path;
         }
      }

      return null;
   }

   @Override
   public String toString() {
      return "[" + this.value + "|" + this.children + "]";
   }

   public Iterable<T> values() {
      return FilterIterator.skipNull(
         new ConsecutiveIterator<>(new SingleIterator<>(this.value), new NestedFunctionIterator<>(this.children.values(), x -> x.values()))
      );
   }

   public T first() {
      if (this.value != null) {
         return this.value;
      }

      for (Entry<String, NamedTree<T>> entry : this.children.entrySet()) {
         T selected = entry.getValue().first();
         if (selected != null) {
            return selected;
         }
      }

      return null;
   }

   public boolean hasChildren() {
      return !this.children.isEmpty();
   }
}
