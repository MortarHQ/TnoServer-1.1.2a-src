package com.blamejared.crafttweaker.api.action.item;

import com.blamejared.crafttweaker.api.action.base.IUndoableAction;
import com.blamejared.crafttweaker.api.action.internal.CraftTweakerAction;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.api.zencode.IScriptLoadSource;
import com.blamejared.crafttweaker.mixin.common.access.item.AccessItem;
import com.blamejared.crafttweaker.natives.item.ExpandItem;
import com.blamejared.crafttweaker.platform.Services;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.DataComponentMap.Builder.SimpleMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import org.apache.logging.log4j.Logger;

public class ActionSetItemProperty<T> extends CraftTweakerAction implements IUndoableAction {
   private final Item item;
   private final DataComponentType<T> component;
   private final T newValue;
   private T oldValue;

   public ActionSetItemProperty(Item item, DataComponentType<T> component, T newValue) {
      this.item = item;
      this.component = component;
      this.newValue = newValue;
   }

   @Override
   public void apply() {
      ((AccessItem)this.item).crafttweaker$setComponents(DataComponentMap.builder().addAll(this.item.components()).build());
      if (this.item.components() instanceof SimpleMap map) {
         if (this.newValue == null) {
            this.oldValue = GenericUtil.uncheck(map.map().remove(this.component));
         } else {
            this.oldValue = GenericUtil.uncheck(map.map().put(this.component, this.newValue));
         }
      } else {
         throw new IllegalStateException("Unknown DataComponentMap: " + this.item.components().getClass());
      }
   }

   @Override
   public String describe() {
      return this.newValue == null
         ? "Removing '"
            + Services.REGISTRY.keyOrThrow(Registries.DATA_COMPONENT_TYPE, this.component)
            + "' from item '"
            + ExpandItem.getCommandString(this.item)
            + "'"
         : "Setting the value of '"
            + Services.REGISTRY.keyOrThrow(Registries.DATA_COMPONENT_TYPE, this.component)
            + "' to '"
            + this.newValue
            + "' on item '"
            + ExpandItem.getCommandString(this.item)
            + "'";
   }

   @Override
   public void undo() {
      ((AccessItem)this.item).crafttweaker$setComponents(DataComponentMap.builder().addAll(this.item.components()).build());
      if (this.item.components() instanceof SimpleMap map) {
         if (this.oldValue == null) {
            map.map().remove(this.component);
         } else {
            map.map().put(this.component, this.oldValue);
         }
      } else {
         throw new IllegalStateException("Unknown DataComponentMap: " + this.item.components().getClass());
      }
   }

   @Override
   public String describeUndo() {
      return "Reset the value of '"
         + Services.REGISTRY.keyOrThrow(Registries.DATA_COMPONENT_TYPE, this.component)
         + "' on item '"
         + ExpandItem.getCommandString(this.item)
         + "'";
   }

   @Override
   public boolean shouldApplyOn(IScriptLoadSource source, Logger logger) {
      return true;
   }
}
