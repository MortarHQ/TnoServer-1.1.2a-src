package toni.lib.modifiers;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import toni.lib.utils.VersionUtils;

public class ModifierDefinition {
   private static String ID;
   private static ResourceLocation Resource;

   public ModifierDefinition(String modid, String path) {
      ID = path;
      Resource = VersionUtils.resource(modid, path);
   }

   public void removeModifier(AttributeInstance attribute) {
      attribute.removeModifier(Resource);
   }

   public void addPermanentModifier(AttributeInstance attribute, double value) {
      attribute.addPermanentModifier(new AttributeModifier(Resource, value, Operation.ADD_VALUE));
   }
}
