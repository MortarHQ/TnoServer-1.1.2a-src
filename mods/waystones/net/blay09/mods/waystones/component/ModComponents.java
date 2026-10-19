package net.blay09.mods.waystones.component;

import java.util.UUID;
import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.component.BalmComponents;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.resources.ResourceLocation;

public class ModComponents {
   public static DeferredObject<DataComponentType<UUID>> waystone;
   public static DeferredObject<DataComponentType<UUID>> attunement;

   public static void initialize(BalmComponents components) {
      waystone = components.registerComponent(
         () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).build(), ResourceLocation.fromNamespaceAndPath("waystones", "waystone")
      );
      attunement = components.registerComponent(
         () -> DataComponentType.builder().persistent(UUIDUtil.CODEC).build(), ResourceLocation.fromNamespaceAndPath("waystones", "attunement")
      );
   }
}
