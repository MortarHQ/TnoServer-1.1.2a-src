package com.illusivesoulworks.polymorph.common;

import com.illusivesoulworks.polymorph.api.PolymorphApi;
import com.illusivesoulworks.polymorph.api.common.capability.IBlockEntityRecipeData;
import com.illusivesoulworks.polymorph.api.common.capability.IRecipeData;
import com.illusivesoulworks.polymorph.common.capability.PlayerRecipeData;
import java.util.function.Supplier;
import javax.annotation.Nonnull;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class PolymorphNeoForgeCapabilities {
   private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, "polymorph");
   public static final Supplier<AttachmentType<PolymorphNeoForgeCapabilities.RecipeDataAttachment>> RECIPE_DATA = ATTACHMENT_TYPES.register(
      "recipe_data", () -> AttachmentType.serializable(PolymorphNeoForgeCapabilities.RecipeDataAttachment::new).build()
   );

   public static void setup(IEventBus eventBus) {
      ATTACHMENT_TYPES.register(eventBus);
   }

   public static class RecipeDataAttachment implements INBTSerializable<CompoundTag> {
      private IRecipeData<?> recipeData;

      public RecipeDataAttachment(IAttachmentHolder attachmentHolder) {
         if (attachmentHolder instanceof Player player) {
            this.recipeData = new PlayerRecipeData(player);
         } else if (attachmentHolder instanceof BlockEntity blockEntity) {
            IBlockEntityRecipeData data = PolymorphApi.getInstance().createBlockEntityRecipeData(blockEntity);
            if (data != null) {
               this.recipeData = data;
            }
         }
      }

      public IRecipeData<?> getRecipeData() {
         return this.recipeData;
      }

      public CompoundTag serializeNBT(@Nonnull Provider provider) {
         return this.recipeData != null ? this.recipeData.writeNBT(provider) : null;
      }

      public void deserializeNBT(@Nonnull Provider provider, @Nonnull CompoundTag nbt) {
         if (this.recipeData != null) {
            this.recipeData.readNBT(provider, nbt);
         }
      }
   }
}
