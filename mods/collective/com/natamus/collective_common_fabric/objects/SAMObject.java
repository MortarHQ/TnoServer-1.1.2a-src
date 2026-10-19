package com.natamus.collective_common_fabric.objects;

import com.natamus.collective_common_fabric.data.GlobalVariables;
import net.minecraft.class_1299;
import net.minecraft.class_1792;

public class SAMObject {
   public class_1299<?> fromEntityType;
   public class_1299<?> toEntityType;
   public class_1792 itemToHold;
   public double changeChance;
   public boolean onlyFromSpawner;
   public boolean rideNotReplace;
   public boolean onlyOnSurface;
   public boolean onlyBelowSurface;
   public boolean onlyBelowSpecificY;
   public int specificY;

   public SAMObject(
      class_1299<?> fromEntityTypeIn,
      class_1299<?> toEntityTypeIn,
      class_1792 itemToHoldIn,
      double changeChanceIn,
      boolean onlyFromSpawnerIn,
      boolean rideNotReplaceIn,
      boolean onlyOnSurfaceIn
   ) {
      new SAMObject(fromEntityTypeIn, toEntityTypeIn, itemToHoldIn, changeChanceIn, onlyFromSpawnerIn, rideNotReplaceIn, onlyOnSurfaceIn, false);
   }

   public SAMObject(
      class_1299<?> fromEntityTypeIn,
      class_1299<?> toEntityTypeIn,
      class_1792 itemToHoldIn,
      double changeChanceIn,
      boolean onlyFromSpawnerIn,
      boolean rideNotReplaceIn,
      boolean onlyOnSurfaceIn,
      boolean onlyBelowSurfaceIn
   ) {
      new SAMObject(
         fromEntityTypeIn,
         toEntityTypeIn,
         itemToHoldIn,
         changeChanceIn,
         onlyFromSpawnerIn,
         rideNotReplaceIn,
         onlyOnSurfaceIn,
         onlyBelowSurfaceIn,
         false,
         Integer.MAX_VALUE
      );
   }

   public SAMObject(
      class_1299<?> fromEntityTypeIn,
      class_1299<?> toEntityTypeIn,
      class_1792 itemToHoldIn,
      double changeChanceIn,
      boolean onlyFromSpawnerIn,
      boolean rideNotReplaceIn,
      boolean onlyOnSurfaceIn,
      boolean onlyBelowSurfaceIn,
      boolean onlyBelowSpecificYIn,
      int specificYIn
   ) {
      this.fromEntityType = fromEntityTypeIn;
      this.toEntityType = toEntityTypeIn;
      this.itemToHold = itemToHoldIn;
      this.changeChance = changeChanceIn;
      this.onlyFromSpawner = onlyFromSpawnerIn;
      this.rideNotReplace = rideNotReplaceIn;
      this.onlyOnSurface = onlyOnSurfaceIn;
      this.onlyBelowSurface = onlyBelowSurfaceIn;
      this.onlyBelowSpecificY = onlyBelowSpecificYIn;
      this.specificY = specificYIn;
      GlobalVariables.globalSAMs.add(this);
      if (!GlobalVariables.activeSAMEntityTypes.contains(this.fromEntityType)) {
         GlobalVariables.activeSAMEntityTypes.add(this.fromEntityType);
      }
   }
}
