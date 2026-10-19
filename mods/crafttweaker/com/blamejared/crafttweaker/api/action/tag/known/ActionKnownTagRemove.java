package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import java.util.List;

public class ActionKnownTagRemove<T> extends ActionKnownTagModify<T> {
   public ActionKnownTagRemove(KnownTag<T> mcTag, List<T> values) {
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
