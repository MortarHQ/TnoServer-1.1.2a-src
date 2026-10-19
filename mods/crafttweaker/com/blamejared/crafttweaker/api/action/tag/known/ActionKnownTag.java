package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.action.tag.ActionTag;
import com.blamejared.crafttweaker.api.tag.manager.type.KnownTagManager;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import java.util.Collection;
import net.minecraft.core.Holder;

public abstract class ActionKnownTag<T> extends ActionTag<KnownTag<T>, KnownTagManager<T>> {
   public ActionKnownTag(KnownTag<T> mcTag) {
      super(mcTag);
   }

   public Collection<Holder<T>> tag() {
      return GenericUtil.uncheck(this.mcTag().getInternal());
   }
}
