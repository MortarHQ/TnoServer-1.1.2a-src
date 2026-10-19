package noobanidus.mods.lootr.common.impl.adapter;

import com.google.auto.service.AutoService;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import noobanidus.mods.lootr.common.api.adapter.ILootrItemFrameAdapter;
import noobanidus.mods.lootr.common.mixin.accessor.AccessorMixinItemFrame;

@AutoService(ILootrItemFrameAdapter.class)
public class ItemFrameAdapter implements ILootrItemFrameAdapter<ItemFrame> {
   @Override
   public Class<ItemFrame> getAssignableClass() {
      return ItemFrame.class;
   }

   public ItemStack getItem(ItemFrame entity) {
      return entity.getItem();
   }

   public int getRotation(ItemFrame object) {
      return object.getRotation();
   }

   public Direction getDirection(ItemFrame entity) {
      return entity.getDirection();
   }

   public BlockPos getPos(ItemFrame entity) {
      return entity.getPos();
   }

   public boolean isFixed(ItemFrame object) {
      return ((AccessorMixinItemFrame)object).lootr$isFixed();
   }

   public boolean isInvisible(ItemFrame object) {
      return object.isInvisible();
   }
}
