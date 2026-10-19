package com.shin.tensura_kumodesu.bridge;

import com.shin.tensura_kumodesu.bridge.neoforge.KumodesuAttributeRegisterImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import org.jetbrains.annotations.NotNull;

public class KumodesuAttributeRegister {
   @ExpectPlatform
   @NotNull
   @Transformed
   public static Holder<Attribute> registerPlayerAttribute(
      String modID, String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment
   ) {
      return KumodesuAttributeRegisterImpl.registerPlayerAttribute(modID, id, name, amount, min, max, syncable, sentiment);
   }

   @ExpectPlatform
   @NotNull
   @Transformed
   public static Holder<Attribute> registerGenericAttribute(
      String modID, String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment
   ) {
      return KumodesuAttributeRegisterImpl.registerGenericAttribute(modID, id, name, amount, min, max, syncable, sentiment);
   }
}
