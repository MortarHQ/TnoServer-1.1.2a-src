package team.creative.creativecore.common.util.type;

public enum ColorType {
   RED {
      @Override
      public int getBrightest() {
         return -16777216;
      }
   },
   GREEN {
      @Override
      public int getBrightest() {
         return 16711680;
      }
   },
   BLUE {
      @Override
      public int getBrightest() {
         return 65280;
      }
   },
   ALPHA {
      @Override
      public int getBrightest() {
         return 255;
      }
   };

   public abstract int getBrightest();
}
