package bigchadguys.sellingbin.data.nbt;

import bigchadguys.sellingbin.data.entity.EntityPlacement;
import bigchadguys.sellingbin.data.tile.PartialBlockState;
import bigchadguys.sellingbin.data.tile.TilePlacement;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CollectionTag;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelWriter;
import net.minecraft.world.phys.Vec3;

public class PartialListNbt implements TilePlacement<PartialListNbt>, EntityPlacement<PartialListNbt> {
   private CollectionTag<?> nbt;

   protected PartialListNbt(CollectionTag<?> nbt) {
      this.nbt = nbt;
   }

   public static PartialListNbt empty() {
      return new PartialListNbt(new ListTag());
   }

   public static PartialListNbt of(CollectionTag<?> nbt) {
      return new PartialListNbt(nbt);
   }

   public boolean isSubsetOf(PartialListNbt other) {
      if (this.nbt == null) {
         return true;
      }

      if (other.nbt != null && this.nbt.size() <= other.nbt.size()) {
         for (Tag e1 : this.nbt) {
            boolean matched = false;

            for (Tag e2 : other.nbt) {
               if (e1.getId() == e2.getId()) {
                  if (e1.getId() == 10) {
                     if (PartialCompoundNbt.of((CompoundTag)e1).isSubsetOf(PartialCompoundNbt.of((CompoundTag)e2))) {
                        matched = true;
                        break;
                     }
                  } else if (e1 instanceof CollectionTag) {
                     if (of((CollectionTag<?>)e1).isSubsetOf(of((CollectionTag<?>)e2))) {
                        matched = true;
                        break;
                     }
                  } else if (e1.equals(e2)) {
                     matched = true;
                     break;
                  }
               }
            }

            if (!matched) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @Override
   public boolean isSubsetOf(BlockGetter world, BlockPos pos) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean isSubsetOf(Entity entity) {
      throw new UnsupportedOperationException();
   }

   public void fillInto(PartialListNbt other) {
      throw new UnsupportedOperationException();
   }

   @Override
   public void place(LevelWriter world) {
   }

   @Override
   public void place(LevelAccessor world, BlockPos pos, int flags) {
      throw new UnsupportedOperationException();
   }

   @Override
   public boolean test(Vec3 pos, BlockPos blockPos, PartialCompoundNbt nbt) {
      return false;
   }

   @Override
   public boolean test(PartialBlockState state, PartialCompoundNbt nbt) {
      return false;
   }

   public Optional<CollectionTag<?>> asWhole() {
      return Optional.ofNullable(this.nbt);
   }

   public PartialListNbt copy() {
      return new PartialListNbt(this.nbt == null ? null : (CollectionTag)this.nbt.copy());
   }
}
