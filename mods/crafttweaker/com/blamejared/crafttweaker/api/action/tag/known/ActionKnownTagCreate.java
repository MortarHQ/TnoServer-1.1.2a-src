package com.blamejared.crafttweaker.api.action.tag.known;

import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import java.util.ArrayList;
import org.apache.logging.log4j.Logger;

public class ActionKnownTagCreate<T> extends ActionKnownTag<T> {
   public ActionKnownTagCreate(KnownTag<T> theTag) {
      super(theTag);
   }

   @Override
   public void apply() {
      this.manager().addTag(this.mcTag().id(), new ArrayList());
   }

   @Override
   public boolean validate(Logger logger) {
      if (this.manager().exists(this.id())) {
         logger.error(this.getType() + " Tag: " + this.mcTag() + " already exists!");
         return false;
      } else {
         return true;
      }
   }

   @Override
   public String describe() {
      return "Registering new " + this.getType() + " tag with name " + this.mcTag();
   }
}
