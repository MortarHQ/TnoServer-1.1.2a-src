package com.finderfeed.fdlib.data_structures;

public class SOTriple<T> {
   private T left;
   private T center;
   private T right;

   public SOTriple(T left, T center, T right) {
      this.left = left;
      this.center = center;
      this.right = right;
   }

   public T getLeft() {
      return this.left;
   }

   public T getCenter() {
      return this.center;
   }

   public T getRight() {
      return this.right;
   }
}
