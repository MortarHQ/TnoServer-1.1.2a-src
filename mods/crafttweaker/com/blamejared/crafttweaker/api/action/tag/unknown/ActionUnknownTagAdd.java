package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class ActionUnknownTagAdd extends ActionUnknownTagModify {
   public ActionUnknownTagAdd(UnknownTag mcTag, List<ResourceLocation> values) {
      super(mcTag, values);
   }

   @Override
   public void apply() {
      this.tag().addAll(this.holderValues());
   }

   @Override
   public String describe() {
      return "Adding: " + this.describeValues() + " to tag: " + this.mcTag();
   }
}
