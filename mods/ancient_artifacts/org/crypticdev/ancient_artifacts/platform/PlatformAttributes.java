package org.crypticdev.ancient_artifacts.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attribute.Sentiment;
import net.minecraft.world.entity.player.Player;
import org.crypticdev.ancient_artifacts.platform.neoforge.PlatformAttributesImpl;

public final class PlatformAttributes {
   private PlatformAttributes() {
   }

   @ExpectPlatform
   @Transformed
   public static Holder<Attribute> register(String id, String name, double amount, double min, double max, boolean syncable, Sentiment sentiment) {
      return PlatformAttributesImpl.register(id, name, amount, min, max, syncable, sentiment);
   }

   @ExpectPlatform
   @Transformed
   public static void update(Player player) {
      PlatformAttributesImpl.update(player);
   }
}
