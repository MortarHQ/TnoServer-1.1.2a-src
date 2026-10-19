package com.blamejared.crafttweaker.api.misc;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.misc.ActionSetCauldronInteraction;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.mixin.common.access.block.AccessAbstractCauldronBlock;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AbstractCauldronBlock;
import net.minecraft.world.level.block.Block;
import org.openzen.zencode.java.ZenCodeGlobals;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.misc.Cauldron")
@Document("vanilla/api/misc/Cauldron")
public final class CTCauldron {
   @ZenCodeGlobals.Global("cauldron")
   public static final CTCauldron INSTANCE = new CTCauldron();

   private CTCauldron() {
   }

   @ZenCodeType.Method
   public void addEmptyInteraction(Item item, CTCauldronInteraction interaction) {
      this.addInteraction(CauldronInteraction.EMPTY, item, interaction);
   }

   @ZenCodeType.Method
   public void removeEmptyInteraction(Item item) {
      this.removeInteraction(CauldronInteraction.EMPTY, item);
   }

   @ZenCodeType.Method
   public void addWaterInteraction(Item item, CTCauldronInteraction interaction) {
      this.addInteraction(CauldronInteraction.WATER, item, interaction);
   }

   @ZenCodeType.Method
   public void removeWaterInteraction(Item item) {
      this.removeInteraction(CauldronInteraction.WATER, item);
   }

   @ZenCodeType.Method
   public void addLavaInteraction(Item item, CTCauldronInteraction interaction) {
      this.addInteraction(CauldronInteraction.LAVA, item, interaction);
   }

   @ZenCodeType.Method
   public void removeLavaInteraction(Item item) {
      this.removeInteraction(CauldronInteraction.LAVA, item);
   }

   @ZenCodeType.Method
   public void addPowderSnowInteraction(Item item, CTCauldronInteraction interaction) {
      this.addInteraction(CauldronInteraction.POWDER_SNOW, item, interaction);
   }

   @ZenCodeType.Method
   public void removePowderSnowInteraction(Item item) {
      this.removeInteraction(CauldronInteraction.POWDER_SNOW, item);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("fillWaterInteraction")
   public CTCauldronInteraction getFillWaterInteraction() {
      return CauldronInteraction.FILL_WATER::interact;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("fillLavaInteraction")
   public CTCauldronInteraction getFillLavaInteraction() {
      return CauldronInteraction.FILL_LAVA::interact;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("fillPowderSnowInteraction")
   public CTCauldronInteraction getFillPowderSnowInteraction() {
      return CauldronInteraction.FILL_POWDER_SNOW::interact;
   }

   @ZenCodeType.Method
   public void addInteraction(Block cauldronBlock, Item item, CTCauldronInteraction interaction) {
      if (cauldronBlock instanceof AbstractCauldronBlock cauldron) {
         this.addInteraction(((AccessAbstractCauldronBlock)cauldron).crafttweaker$getInteractions(), item, interaction);
      } else {
         throw new IllegalStateException("Provided block: '" + cauldronBlock + "' is not an AbstractCauldronBlock!");
      }
   }

   @ZenCodeType.Method
   public void removeInteraction(Block cauldronBlock, Item item) {
      if (cauldronBlock instanceof AbstractCauldronBlock cauldron) {
         this.removeInteraction(((AccessAbstractCauldronBlock)cauldron).crafttweaker$getInteractions(), item);
      } else {
         throw new IllegalStateException("Provided block: '" + cauldronBlock + "' is not an AbstractCauldronBlock!");
      }
   }

   public void addInteraction(InteractionMap map, Item item, CTCauldronInteraction interaction) {
      CraftTweakerAPI.apply(new ActionSetCauldronInteraction(map, item, interaction::interact));
   }

   public void removeInteraction(InteractionMap map, Item item) {
      CraftTweakerAPI.apply(new ActionSetCauldronInteraction(map, item, null));
   }
}
