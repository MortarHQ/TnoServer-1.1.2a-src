package com.blamejared.crafttweaker.api.action.misc;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.cauldron.CauldronInteraction.InteractionMap;
import net.minecraft.world.item.Item;

public class ActionSetCauldronInteraction extends CraftTweakerAction implements IUndoableAction {
   private final InteractionMap map;
   private final Item key;
   private final CauldronInteraction interaction;
   private CauldronInteraction previous;

   public ActionSetCauldronInteraction(InteractionMap map, Item key, CauldronInteraction interaction) {
      this.map = map;
      this.key = key;
      this.interaction = interaction;
   }

   @Override
   public void apply() {
      if (this.interaction == null) {
         this.previous = (CauldronInteraction)this.map.map().remove(this.key);
      } else {
         this.previous = this.map.map().put(this.key, this.interaction);
      }
   }

   @Override
   public String describe() {
      return "Set Cauldron interaction for " + this.key;
   }

   @Override
   public void undo() {
      if (this.previous == null) {
         this.map.map().remove(this.key);
      } else {
         this.map.map().put(this.key, this.previous);
      }
   }

   @Override
   public String describeUndo() {
      return "Undoing setting of Cauldron interaction for " + this.key;
   }
}
