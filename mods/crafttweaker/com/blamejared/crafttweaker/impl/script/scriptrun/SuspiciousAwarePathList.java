package com.blamejared.crafttweaker.impl.script.scriptrun;

import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;

final class SuspiciousAwarePathList implements List<Path> {
   private final List<Path> delegate;
   private final PathMatcher suspiciousMatcher;
   private final Consumer<Path> logger;

   private SuspiciousAwarePathList(List<Path> delegate, PathMatcher suspiciousMatcher, Consumer<Path> logger) {
      this.delegate = delegate;
      this.suspiciousMatcher = suspiciousMatcher;
      this.logger = logger;
   }

   static SuspiciousAwarePathList of(PathMatcher suspiciousMatcher, Consumer<Path> logger) {
      return new SuspiciousAwarePathList(new ArrayList<>(), suspiciousMatcher, logger);
   }

   public boolean add(Path path) {
      if (this.suspiciousMatcher.matches(path)) {
         this.logger.accept(path);
         return false;
      } else {
         return this.delegate.add(path);
      }
   }

   @Override
   public boolean addAll(@NotNull Collection<? extends Path> c) {
      boolean result = false;

      for (Path p : c) {
         result |= this.add(p);
      }

      return result;
   }

   @Override
   public boolean addAll(int index, @NotNull Collection<? extends Path> c) {
      boolean result = false;
      int idx = index;

      for (Path p : c) {
         boolean r = this.addImpl(idx, p);
         result |= r;
         if (r) {
            idx++;
         }
      }

      return result;
   }

   @Override
   public int size() {
      return this.delegate.size();
   }

   @Override
   public boolean isEmpty() {
      return this.delegate.isEmpty();
   }

   @Override
   public boolean contains(Object o) {
      return this.delegate.contains(o);
   }

   @NotNull
   @Override
   public Iterator<Path> iterator() {
      return this.delegate.iterator();
   }

   @NotNull
   @Override
   public Object[] toArray() {
      return this.delegate.toArray();
   }

   @NotNull
   @Override
   public <T> T[] toArray(@NotNull T[] a) {
      return (T[])this.delegate.toArray(a);
   }

   @Override
   public boolean remove(Object o) {
      return this.delegate.remove(o);
   }

   @Override
   public boolean containsAll(@NotNull Collection<?> c) {
      return this.delegate.containsAll(c);
   }

   @Override
   public boolean removeAll(@NotNull Collection<?> c) {
      return this.delegate.removeAll(c);
   }

   @Override
   public boolean retainAll(@NotNull Collection<?> c) {
      return this.delegate.retainAll(c);
   }

   @Override
   public void clear() {
      this.delegate.clear();
   }

   public Path get(int index) {
      return this.delegate.get(index);
   }

   public Path set(int index, Path element) {
      if (this.suspiciousMatcher.matches(element)) {
         throw new IllegalArgumentException("Suspicious element " + element);
      } else {
         return this.delegate.set(index, element);
      }
   }

   public void add(int index, Path element) {
      if (!this.addImpl(index, element)) {
         throw new IllegalArgumentException("Suspicious element " + element);
      }
   }

   public Path remove(int index) {
      return this.delegate.remove(index);
   }

   @Override
   public int indexOf(Object o) {
      return this.delegate.indexOf(o);
   }

   @Override
   public int lastIndexOf(Object o) {
      return this.delegate.lastIndexOf(o);
   }

   @NotNull
   @Override
   public ListIterator<Path> listIterator() {
      return new SuspiciousAwarePathList.SuspiciousAwarePathListIterator(this.delegate.listIterator(), this.suspiciousMatcher);
   }

   @NotNull
   @Override
   public ListIterator<Path> listIterator(int index) {
      return new SuspiciousAwarePathList.SuspiciousAwarePathListIterator(this.delegate.listIterator(index), this.suspiciousMatcher);
   }

   @NotNull
   @Override
   public List<Path> subList(int fromIndex, int toIndex) {
      return new SuspiciousAwarePathList(this.delegate.subList(fromIndex, toIndex), this.suspiciousMatcher, this.logger);
   }

   @Override
   public void replaceAll(UnaryOperator<Path> operator) {
      this.delegate.replaceAll(it -> {
         Path result = operator.apply(it);
         if (this.suspiciousMatcher.matches(result)) {
            throw new UnsupportedOperationException("Cannot replace element %s with suspicious element %s".formatted(it, result));
         } else {
            return result;
         }
      });
   }

   @Override
   public void sort(Comparator<? super Path> c) {
      this.delegate.sort(c);
   }

   @Override
   public Spliterator<Path> spliterator() {
      return this.delegate.spliterator();
   }

   @Override
   public <T> T[] toArray(IntFunction<T[]> generator) {
      return this.delegate.toArray(generator);
   }

   @Override
   public boolean removeIf(Predicate<? super Path> filter) {
      return this.delegate.removeIf(filter);
   }

   @Override
   public Stream<Path> stream() {
      return this.delegate.stream();
   }

   @Override
   public Stream<Path> parallelStream() {
      return this.delegate.parallelStream();
   }

   @Override
   public void forEach(Consumer<? super Path> action) {
      this.delegate.forEach(action);
   }

   private boolean addImpl(int index, Path p) {
      if (this.suspiciousMatcher.matches(p)) {
         this.logger.accept(p);
         return false;
      }

      try {
         this.delegate.add(index, p);
         return true;
      } catch (IllegalArgumentException e) {
         return false;
      }
   }

   private static final class SuspiciousAwarePathListIterator implements ListIterator<Path> {
      private final ListIterator<Path> delegate;
      private final PathMatcher suspiciousMatcher;

      SuspiciousAwarePathListIterator(ListIterator<Path> delegate, PathMatcher suspiciousMatcher) {
         this.delegate = delegate;
         this.suspiciousMatcher = suspiciousMatcher;
      }

      @Override
      public boolean hasNext() {
         return this.delegate.hasNext();
      }

      public Path next() {
         return this.delegate.next();
      }

      @Override
      public boolean hasPrevious() {
         return this.delegate.hasPrevious();
      }

      public Path previous() {
         return this.delegate.previous();
      }

      @Override
      public int nextIndex() {
         return this.delegate.nextIndex();
      }

      @Override
      public int previousIndex() {
         return this.delegate.previousIndex();
      }

      @Override
      public void remove() {
         this.delegate.remove();
      }

      @Override
      public void forEachRemaining(Consumer<? super Path> action) {
         this.delegate.forEachRemaining(action);
      }

      public void set(Path path) {
         if (this.suspiciousMatcher.matches(path)) {
            throw new IllegalArgumentException("Suspicious element " + path);
         }

         this.delegate.set(path);
      }

      public void add(Path path) {
         if (this.suspiciousMatcher.matches(path)) {
            throw new IllegalArgumentException("Suspicious element " + path);
         }

         this.delegate.add(path);
      }
   }
}
