package com.cerbon.cerbons_api.api.network.data;

public enum Side {
   CLIENT,
   SERVER;

   public Side opposite() {
      return CLIENT.equals(this) ? SERVER : CLIENT;
   }
}
