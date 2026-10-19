package team.creative.creativecore.common.config.sync;

import team.creative.creativecore.Side;

public enum ConfigSynchronization {
   CLIENT {
      @Override
      public boolean useFolder(boolean forceSynchronization, Side side) {
         return forceSynchronization ? side.isServer() : side.isClient();
      }

      @Override
      public boolean useValue(boolean forceSynchronization, Side side) {
         return forceSynchronization ? side.isServer() : side.isClient();
      }
   },
   UNIVERSAL {
      @Override
      public boolean useFolder(boolean forceSynchronization, Side side) {
         return true;
      }

      @Override
      public boolean useValue(boolean forceSynchronization, Side side) {
         return side.isServer();
      }
   },
   SERVER {
      @Override
      public boolean useFolder(boolean forceSynchronization, Side side) {
         return side.isServer();
      }

      @Override
      public boolean useValue(boolean forceSynchronization, Side side) {
         return side.isServer();
      }
   };

   public abstract boolean useFolder(boolean var1, Side var2);

   public abstract boolean useValue(boolean var1, Side var2);
}
