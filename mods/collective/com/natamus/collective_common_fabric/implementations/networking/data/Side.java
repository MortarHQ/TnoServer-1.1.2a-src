package com.natamus.collective_common_fabric.implementations.networking.data;

public enum Side {
   CLIENT,
   SERVER;

   public Side opposite() {
      return CLIENT.equals(this) ? SERVER : CLIENT;
   }
}
