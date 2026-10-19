package dev.ftb.mods.ftbultimine.api.shape;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

public interface Shape {
   ResourceLocation getName();

   List<BlockPos> getBlocks(ShapeContext var1);

   default boolean isIndeterminateShape() {
      return false;
   }

   @NonExtendable
   default MutableComponent getDisplayName() {
      return Component.translatable("ftbultimine.shape." + this.getName().toLanguageKey());
   }

   @NonExtendable
   default boolean isDefault() {
      return false;
   }
}
