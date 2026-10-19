package listeners;

import java.util.function.Consumer;

public final class ListenerList<T> {
   public static final int PRIORITY_HIGH = 100;
   public static final int PRIORITY_DEFAULT = 0;
   public static final int PRIORITY_LOW = -100;
   private ListenerList<T>.EventListenerNode first = null;
   private ListenerList<T>.EventListenerNode last = null;

   public ListenerHandle<T> add(T listener) {
      return this.add(listener, 0);
   }

   public ListenerHandle<T> add(T listener, int priority) {
      ListenerList<T>.EventListenerNode node = new ListenerList.EventListenerNode(listener, priority);
      if (this.first == null) {
         this.first = this.last = node;
      } else {
         ListenerList<T>.EventListenerNode previousNode = this.last;

         while (previousNode != null && priority > previousNode.priority) {
            previousNode = previousNode.prev;
         }

         if (previousNode == null) {
            node.next = this.first;
            this.first.prev = previousNode;
            this.first = node;
         } else {
            if (previousNode.next == null) {
               this.last = node;
            } else {
               previousNode.next.prev = node;
            }

            previousNode.next = node;
            node.prev = previousNode;
         }
      }

      return node;
   }

   public void clear() {
      this.first = this.last = null;
   }

   public void accept(Consumer<T> consumer) {
      for (ListenerList<T>.EventListenerNode current = this.first; current != null; current = current.next) {
         consumer.accept((T)current.listener);
      }
   }

   public boolean getIsEmpty() {
      return this.first == null;
   }

   private final class EventListenerNode implements ListenerHandle<T>, AutoCloseable {
      public final Object listener;
      private final int priority;
      private ListenerList<T>.EventListenerNode next = null;
      private ListenerList<T>.EventListenerNode prev = null;

      public EventListenerNode(T listener, int priority) {
         this.listener = listener;
         this.priority = priority;
      }

      @Override
      public void close() {
         if (this.prev == null) {
            ListenerList.this.first = this.next;
         } else {
            this.prev.next = this.next;
         }

         if (this.next == null) {
            ListenerList.this.last = this.prev;
         } else {
            this.next.prev = this.prev;
         }
      }

      @Override
      public T getListener() {
         return (T)this.listener;
      }
   }
}
