package com.blamejared.crafttweaker.api.action.tag.unknown;

import com.blamejared.crafttweaker.api.tag.type.UnknownTag;
import java.util.List;
import net.minecraft.resources.ResourceLocation;

public class ActionUnknownTagRemove extends ActionUnknownTagModify {
   public ActionUnknownTagRemove(UnknownTag mcTag, List<ResourceLocation> values) {
      super(mcTag, values);
   }

   @Override
   public void apply() {
      this.tag().removeAll(this.holderValues());
   }

   @Override
   public String describe() {
      return "Removing: " + this.describeValues() + " from tag: " + this.mcTag();
   }
}
