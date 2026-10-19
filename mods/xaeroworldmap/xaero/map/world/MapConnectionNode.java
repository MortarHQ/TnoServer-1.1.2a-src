package xaero.map.world;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class MapConnectionNode {
   private final ResourceKey<Level> dimId;
   private final String mw;
   private String cachedString;

   public MapConnectionNode(ResourceKey<Level> dimId, String mw) {
      this.dimId = dimId;
      this.mw = mw;
   }

   @Override
   public String toString() {
      if (this.cachedString == null) {
         this.cachedString = this.dimId.location().toString().replace(':', '$') + "/" + this.mw;
      }

      return this.cachedString;
   }

   public String getNamedString(MapWorld mapWorld) {
      MapDimension dim = mapWorld.getDimension(this.dimId);
      return this.dimId.location() + "/" + dim.getMultiworldName(this.mw);
   }

   public static MapConnectionNode fromString(String s) {
      int dividerIndex = s.lastIndexOf(47);
      if (dividerIndex == -1) {
         return null;
      }

      String dimString = s.substring(0, dividerIndex);

      ResourceLocation dimLocation;
      try {
         if (dimString.equals("0")) {
            dimLocation = Level.OVERWORLD.location();
         } else if (dimString.equals("-1")) {
            dimLocation = Level.NETHER.location();
         } else if (dimString.equals("1")) {
            dimLocation = Level.END.location();
         } else {
            dimLocation = ResourceLocation.parse(dimString.replace('$', ':'));
         }
      } catch (Throwable t) {
         return null;
      }

      String mwString = s.substring(dividerIndex + 1);
      return new MapConnectionNode(ResourceKey.create(Registries.DIMENSION, dimLocation), mwString);
   }

   @Override
   public boolean equals(Object another) {
      if (this == another) {
         return true;
      } else {
         return another != null && another instanceof MapConnectionNode anotherNode
            ? this.dimId.equals(anotherNode.dimId) && this.mw.equals(anotherNode.mw)
            : false;
      }
   }

   @Override
   public int hashCode() {
      return this.toString().hashCode();
   }

   public ResourceKey<Level> getDimId() {
      return this.dimId;
   }

   public String getMw() {
      return this.mw;
   }
}
