package team.creative.creativecore.common.util.type.list;

import java.util.Arrays;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class CopyArrayCollection<E> implements Collection<E>, Cloneable {
   private static final int DEFAULT_CAPACITY = 10;
   private static final Object[] EMPTY_ELEMENTDATA = new Object[0];
   private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = new Object[0];
   public static final int SOFT_MAX_ARRAY_LENGTH = 2147483639;
   private Object[] content;
   private int size;
   private boolean hadIterator = false;

   public static int newLength(int oldLength, int minGrowth, int prefGrowth) {
      int prefLength = oldLength + Math.max(minGrowth, prefGrowth);
      return 0 < prefLength && prefLength <= 2147483639 ? prefLength : hugeLength(oldLength, minGrowth);
   }

   private static int hugeLength(int oldLength, int minGrowth) {
      int minLength = oldLength + minGrowth;
      if (minLength < 0) {
         throw new OutOfMemoryError("Required array length " + oldLength + " + " + minGrowth + " is too large");
      } else {
         return minLength <= 2147483639 ? 2147483639 : minLength;
      }
   }

   public CopyArrayCollection() {
      this.content = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
      this.size = 0;
   }

   public CopyArrayCollection(int initialCapacity) {
      if (initialCapacity > 0) {
         this.content = new Object[initialCapacity];
      } else {
         if (initialCapacity != 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
         }

         this.content = EMPTY_ELEMENTDATA;
      }

      this.size = 0;
   }

   public CopyArrayCollection(E element) {
      this(1);
      this.content[0] = element;
      this.size = 1;
   }

   public CopyArrayCollection(Collection<? extends E> c) {
      if (c instanceof CopyArrayCollection) {
         this.content = Arrays.copyOf(((CopyArrayCollection)c).content, ((CopyArrayCollection)c).content.length, Object[].class);
      } else {
         this.content = c.toArray(new Object[0]);
      }

      this.size = c.size();
   }

   public CopyArrayCollection(Iterable<? extends E> c) {
      this();
      c.forEach(this::add);
   }

   public CopyArrayCollection(E... toCopyIn) {
      this.content = Arrays.copyOf(toCopyIn, toCopyIn.length, Object[].class);
      this.size = this.content.length;
   }

   public synchronized void trimToSize() {
      if (this.size < this.content.length) {
         this.hadIterator = false;
         this.content = this.size == 0 ? EMPTY_ELEMENTDATA : Arrays.copyOf(this.content, this.size);
      }
   }

   public void ensureCapacity(int minCapacity) {
      if (minCapacity > this.content.length && (this.content != DEFAULTCAPACITY_EMPTY_ELEMENTDATA || minCapacity > 10)) {
         this.grow(minCapacity);
      }
   }

   private Object[] grow(int minCapacity) {
      int oldCapacity = this.content.length;
      if (oldCapacity <= 0 && this.content == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
         this.hadIterator = false;
         return this.content = new Object[Math.max(10, minCapacity)];
      } else {
         int newCapacity = newLength(oldCapacity, minCapacity - oldCapacity, oldCapacity >> 1);
         this.hadIterator = false;
         return this.content = Arrays.copyOf(this.content, newCapacity);
      }
   }

   private Object[] grow() {
      return this.grow(this.size + 1);
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public boolean isEmpty() {
      return this.size == 0;
   }

   public E first() {
      Object[] es;
      int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
      }

      return (E)(size > 0 ? es[0] : null);
   }

   @Override
   public boolean contains(Object o) {
      Object[] es;
      int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
      }

      return this.indexOfRange(o, es, 0, size) >= 0;
   }

   int indexOfRange(Object o, Object[] es, int start, int end) {
      if (o == null) {
         for (int i = start; i < end; i++) {
            if (es[i] == null) {
               return i;
            }
         }
      } else {
         for (int i = start; i < end; i++) {
            if (o.equals(es[i])) {
               return i;
            }
         }
      }

      return -1;
   }

   @Override
   public Object clone() {
      return new CopyArrayCollection<>(this);
   }

   @Override
   public Object[] toArray() {
      Object[] es;
      int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
      }

      return Arrays.copyOf(es, size);
   }

   @Override
   public <T> T[] toArray(T[] a) {
      Object[] es;
      int len;
      synchronized (this) {
         es = this.content;
         len = es.length;
      }

      if (a.length < len) {
         return (T[])Arrays.copyOf(es, len, (Class<? extends T[]>)a.getClass());
      }

      System.arraycopy(es, 0, a, 0, len);
      if (a.length > len) {
         a[len] = null;
      }

      return a;
   }

   @Override
   public boolean add(E e) {
      synchronized (this) {
         Object[] es = this.content;
         int size = this.size;
         if (size == es.length) {
            es = this.grow();
         }

         es[size] = e;
         this.size = size + 1;
         return true;
      }
   }

   @Override
   public void clear() {
      synchronized (this) {
         if (this.hadIterator) {
            this.hadIterator = false;
            this.content = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
            this.size = 0;
         } else {
            Object[] es = this.content;
            int to = this.size;

            for (int i = this.size = 0; i < to; i++) {
               es[i] = null;
            }
         }
      }
   }

   @Override
   public boolean addAll(Collection<? extends E> c) {
      if (c.isEmpty()) {
         return false;
      }

      synchronized (this) {
         if (this.hadIterator) {
            Object[] cs = c.toArray();
            Object[] es = this.content;
            int len = this.size;
            int newSize = len + cs.length;
            Object[] newElements = Arrays.copyOf(es, newSize);
            System.arraycopy(cs, 0, newElements, len, cs.length);
            this.hadIterator = false;
            this.content = newElements;
            this.size = newSize;
         } else {
            Object[] a = c.toArray();
            int numNew = a.length;
            Object[] elementData = this.content;
            int var10001 = this.content.length;
            int s = this.size;
            if (numNew > var10001 - this.size) {
               elementData = this.grow(s + numNew);
            }

            System.arraycopy(a, 0, elementData, s, numNew);
            this.content = elementData;
            this.size = s + numNew;
         }

         return true;
      }
   }

   @Override
   public boolean containsAll(Collection<?> c) {
      Object[] es;
      int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
      }

      for (Object o : c) {
         if (this.indexOfRange(o, es, 0, size) < 0) {
            return false;
         }
      }

      return true;
   }

   @Override
   public Iterator<E> iterator() {
      final Object[] es;
      final int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
         this.hadIterator = true;
      }

      return new Iterator<E>() {
         int index = 0;

         @Override
         public boolean hasNext() {
            return this.index < size;
         }

         @Override
         public E next() {
            E result = (E)es[this.index];
            this.index++;
            return result;
         }

         @Override
         public void remove() {
            CopyArrayCollection.this.remove(es[this.index]);
         }
      };
   }

   @Override
   public boolean remove(Object o) {
      synchronized (this) {
         Object[] snapshot = this.content;
         int index = this.indexOfRange(o, snapshot, 0, snapshot.length);
         return index >= 0 && this.remove(o, snapshot, index);
      }
   }

   private boolean remove(Object o, Object[] snapshot, int index) {
      if (this.hadIterator) {
         Object[] current = this.content;
         int len = this.size;
         Object[] newElements = new Object[len - 1];
         System.arraycopy(current, 0, newElements, 0, index);
         System.arraycopy(current, index + 1, newElements, index, len - index - 1);
         this.size--;
         this.hadIterator = false;
         this.content = newElements;
      } else {
         Object[] es = this.content;
         this.fastRemove(es, index);
      }

      return true;
   }

   private synchronized void fastRemove(Object[] es, int i) {
      int newSize;
      if ((newSize = this.size - 1) > i) {
         System.arraycopy(es, i + 1, es, i, newSize - i);
      }

      es[--this.size] = null;
   }

   @Override
   public boolean removeAll(Collection<?> c) {
      return this.bulkRemove(c::contains);
   }

   @Override
   public boolean retainAll(Collection<?> c) {
      return this.bulkRemove(e -> !c.contains(e));
   }

   @Override
   public boolean removeIf(Predicate<? super E> filter) {
      Objects.requireNonNull(filter);
      return this.bulkRemove(filter);
   }

   private boolean bulkRemove(Predicate<? super E> filter) {
      synchronized (this) {
         return this.bulkRemove(filter, 0, this.size);
      }
   }

   private static long[] nBits(int n) {
      return new long[(n - 1 >> 6) + 1];
   }

   private static void setBit(long[] bits, int i) {
      bits[i >> 6] = bits[i >> 6] | 1L << i;
   }

   private static boolean isClear(long[] bits, int i) {
      return (bits[i >> 6] & 1L << i) == 0L;
   }

   boolean bulkRemove(Predicate<? super E> filter, int i, int end) {
      Object[] es = this.content;

      while (i < end && !filter.test((E)es[i])) {
         i++;
      }

      if (i < end) {
         int beg = i;
         long[] deathRow = nBits(end - beg);
         int deleted = 1;
         deathRow[0] = 1L;

         for (int var10 = beg + 1; var10 < end; var10++) {
            if (filter.test((E)es[var10])) {
               setBit(deathRow, var10 - beg);
               deleted++;
            }
         }

         if (es != this.content) {
            throw new ConcurrentModificationException();
         }

         Object[] newElts = Arrays.copyOf(es, es.length - deleted);
         int w = beg;

         for (i = beg; i < end; i++) {
            if (isClear(deathRow, i - beg)) {
               newElts[w++] = es[i];
            }
         }

         System.arraycopy(es, i, newElts, w, es.length - i);
         this.hadIterator = false;
         this.content = newElts;
         this.size = newElts.length;
         return true;
      } else if (es != this.content) {
         throw new ConcurrentModificationException();
      } else {
         return false;
      }
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof List<?> list)) {
         return false;
      } else {
         Iterator it = list.iterator();

         for (Object element : this.content) {
            if (!it.hasNext() || !Objects.equals(element, it.next())) {
               return false;
            }
         }

         return !it.hasNext();
      }
   }

   private static int hashCodeOfRange(Object[] es, int from, int to) {
      int hashCode = 1;

      for (int i = from; i < to; i++) {
         Object x = es[i];
         hashCode = 31 * hashCode + (x == null ? 0 : x.hashCode());
      }

      return hashCode;
   }

   @Override
   public int hashCode() {
      Object[] es;
      int size;
      synchronized (this) {
         es = this.content;
         size = this.size;
      }

      return hashCodeOfRange(es, 0, size);
   }

   @Override
   public String toString() {
      Iterator<E> it = this.iterator();
      if (!it.hasNext()) {
         return "[]";
      }

      StringBuilder sb = new StringBuilder();
      sb.append('[');

      while (true) {
         E e = it.next();
         sb.append(e == this ? "(this Collection)" : e);
         if (!it.hasNext()) {
            return sb.append(']').toString();
         }

         sb.append(',').append(' ');
      }
   }
}
