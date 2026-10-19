package io.github.Memoires.mysticism.bridge;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import io.github.Memoires.mysticism.bridge.neoforge.MysticismAttributeRegisterImpl;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import org.jetbrains.annotations.NotNull;

public class MysticismAttributeRegister {
   @ExpectPlatform
   @NotNull
   @Transformed
   public static Holder<Attribute> registerPlayerAttribute(
      String modID, String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment
   ) {
      return MysticismAttributeRegisterImpl.registerPlayerAttribute(modID, id, name, amount, min, max, syncable, sentiment);
   }

   @ExpectPlatform
   @NotNull
   @Transformed
   public static Holder<Attribute> registerGenericAttribute(
      String modID, String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment
   ) {
      return MysticismAttributeRegisterImpl.registerGenericAttribute(modID, id, name, amount, min, max, syncable, sentiment);
   }
}
