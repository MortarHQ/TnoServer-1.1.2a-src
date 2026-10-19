package com.natamus.collective_common_neoforge.objects;

import com.natamus.collective_common_neoforge.data.GlobalVariables;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;

public class SAMObject {
   public EntityType<?> fromEntityType;
   public EntityType<?> toEntityType;
   public Item itemToHold;
   public double changeChance;
   public boolean onlyFromSpawner;
   public boolean rideNotReplace;
   public boolean onlyOnSurface;
   public boolean onlyBelowSurface;
   public boolean onlyBelowSpecificY;
   public int specificY;

   public SAMObject(
      EntityType<?> fromEntityTypeIn,
      EntityType<?> toEntityTypeIn,
      Item itemToHoldIn,
      double changeChanceIn,
      boolean onlyFromSpawnerIn,
      boolean rideNotReplaceIn,
      boolean onlyOnSurfaceIn
   ) {
      new SAMObject(fromEntityTypeIn, toEntityTypeIn, itemToHoldIn, changeChanceIn, onlyFromSpawnerIn, rideNotReplaceIn, onlyOnSurfaceIn, false);
   }

   public SAMObject(
      EntityType<?> fromEntityTypeIn,
      EntityType<?> toEntityTypeIn,
      Item itemToHoldIn,
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
      EntityType<?> fromEntityTypeIn,
      EntityType<?> toEntityTypeIn,
      Item itemToHoldIn,
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
