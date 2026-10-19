package noobanidus.mods.lootr.common.integration.sherdsapi.impl;

import dev.thomasglasser.sherdsapi.impl.StackPotDecorations;
import dev.thomasglasser.sherdsapi.impl.StackPotDecorationsHolder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity.DataComponentInput;
import noobanidus.mods.lootr.common.api.LootrConstants;
import noobanidus.mods.lootr.common.api.PotDecorationsAdapter;
import org.jetbrains.annotations.Nullable;

public class SherdsIntegrationImpl {
   private static DataComponentType<StackPotDecorations> type = null;
   private static boolean checked = false;
   private static DataComponentType<ResourceLocation> type2 = null;
   private static boolean checked2 = false;

   @Nullable
   private static DataComponentType<StackPotDecorations> getSherdsDecorationsComponent() {
      if (!checked) {
         checked = true;
         DataComponentType<?> comp = (DataComponentType<?>)BuiltInRegistries.DATA_COMPONENT_TYPE.get(LootrConstants.SHERDSAPI_POT_DECORATIONS);
         if (comp == null) {
            return null;
         }

         type = (DataComponentType<StackPotDecorations>)comp;
      }

      return type;
   }

   @Nullable
   private static DataComponentType<ResourceLocation> getSherdsTextureComponent() {
      if (!checked2) {
         checked2 = true;
         DataComponentType<?> comp = (DataComponentType<?>)BuiltInRegistries.DATA_COMPONENT_TYPE.get(LootrConstants.SHERDSAPI_SHERD_PATTERN);
         if (comp == null) {
            return null;
         }

         type2 = (DataComponentType<ResourceLocation>)comp;
      }

      return type2;
   }

   @Nullable
   public static PotDecorationsAdapter getAdapterFrom(DataComponentInput stack) {
      DataComponentType<StackPotDecorations> sherdsType = getSherdsDecorationsComponent();
      if (sherdsType == null) {
         return null;
      }

      StackPotDecorations decorations = (StackPotDecorations)stack.get(sherdsType);
      return decorations == null ? null : new PotDecorationsAdapter(decorations.ordered());
   }

   @Nullable
   public static PotDecorationsAdapter getAdapterFrom(ItemStack stack) {
      DataComponentType<StackPotDecorations> sherdsType = getSherdsDecorationsComponent();
      if (sherdsType == null) {
         return null;
      }

      if (!stack.has(sherdsType)) {
         return null;
      }

      StackPotDecorations decorations = (StackPotDecorations)stack.get(sherdsType);
      return decorations == null ? null : new PotDecorationsAdapter(decorations.ordered());
   }

   @Nullable
   public static PotDecorationsAdapter getAdapterFrom(BlockEntity blockEntity) {
      if (blockEntity instanceof StackPotDecorationsHolder holderType) {
         StackPotDecorations decorations = holderType.sherdsapi$getDecorations();
         return decorations == null ? null : new PotDecorationsAdapter(decorations.ordered());
      } else {
         return null;
      }
   }

   @Nullable
   public static ResourceLocation getCustomSideTexture(ItemStack item) {
      DataComponentType<ResourceLocation> textureType = getSherdsTextureComponent();
      if (textureType == null) {
         return null;
      } else {
         return !item.has(textureType) ? null : (ResourceLocation)item.get(textureType);
      }
   }
}
