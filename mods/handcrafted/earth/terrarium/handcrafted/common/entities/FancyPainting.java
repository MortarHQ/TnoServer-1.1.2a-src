package earth.terrarium.handcrafted.common.entities;

import earth.terrarium.handcrafted.common.registry.ModEntityTypes;
import earth.terrarium.handcrafted.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.Painting;
import net.minecraft.world.entity.decoration.PaintingVariant;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;

public class FancyPainting extends Painting {
   public FancyPainting(EntityType<? extends Painting> type, Level level) {
      super(type, level);
   }

   public FancyPainting(Level level, BlockPos pos) {
      super((EntityType)ModEntityTypes.FANCY_PAINTING.get(), level);
      this.pos = pos;
   }

   public ItemEntity spawnAtLocation(ItemLike item) {
      return super.spawnAtLocation((ItemLike)ModItems.FANCY_PAINTING.get());
   }

   public ItemStack getPickResult() {
      return ((Item)ModItems.FANCY_PAINTING.get()).getDefaultInstance();
   }

   public void setDirection(Direction direction) {
      this.direction = direction;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      PaintingVariant variant = (PaintingVariant)this.getVariant().value();
      compound.putString("variant", variant.assetId().getPath());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      if (compound.contains("variant", 8)) {
         ResourceLocation location = ResourceLocation.fromNamespaceAndPath("handcrafted", compound.getString("variant"));
         this.registryAccess()
            .lookupOrThrow(Registries.PAINTING_VARIANT)
            .get(ResourceKey.create(Registries.PAINTING_VARIANT, location))
            .ifPresent(this::setVariant);
      }
   }
}
