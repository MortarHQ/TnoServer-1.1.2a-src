package com.blamejared.crafttweaker.api.action.loot;

import com.blamejared.crafttweaker.api.loot.modifier.ILootModifier;
import com.google.common.base.Suppliers;
import java.util.Map;
import java.util.function.Supplier;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.Logger;

public class ActionRegisterLootModifier extends ActionLootModifier {
   private final ResourceLocation name;
   private final Supplier<ILootModifier> modifier;

   public ActionRegisterLootModifier(ResourceLocation name, Supplier<ILootModifier> modifierCreator, Supplier<Map<ResourceLocation, ILootModifier>> mapGetter) {
      super(mapGetter);
      this.name = name;
      this.modifier = Suppliers.memoize(modifierCreator::get);
   }

   @Override
   public void apply() {
      this.modifiersMap().put(this.name, this.modifier.get());
   }

   @Override
   public String describe() {
      return "Registering loot modifier with name '" + this.name + "'";
   }

   @Override
   public boolean validate(Logger logger) {
      if (!super.validate(logger)) {
         return false;
      } else if (this.modifier.get() == null) {
         logger.error("Unable to register a null loot modifier", new NullPointerException("Null loot modifier"));
         return false;
      } else if (this.modifiersMap().containsKey(this.name)) {
         Throwable throwable = new IllegalStateException("'" + this.name.toString() + "' is already in use");
         logger.error("Unable to register a loot modifier with name '" + this.name + "' since it already exists", throwable);
         return false;
      } else {
         return true;
      }
   }
}
