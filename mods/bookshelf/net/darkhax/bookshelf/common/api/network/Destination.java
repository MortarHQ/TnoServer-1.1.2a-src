package net.darkhax.bookshelf.common.api.network;

public enum Destination {
   SERVER_TO_CLIENT,
   CLIENT_TO_SERVER,
   BIDIRECTIONAL;

   public boolean handledByServer() {
      return this == CLIENT_TO_SERVER || this == BIDIRECTIONAL;
   }

   public boolean handledByClient() {
      return this == SERVER_TO_CLIENT || this == BIDIRECTIONAL;
   }
}
