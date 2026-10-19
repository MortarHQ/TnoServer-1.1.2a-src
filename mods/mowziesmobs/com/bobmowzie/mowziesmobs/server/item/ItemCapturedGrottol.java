package com.bobmowzie.mowziesmobs.server.item;

import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class ItemCapturedGrottol extends Item {
   public ItemCapturedGrottol(Properties properties) {
      super(properties);
   }

   public int getMaxStackSize(ItemStack stack) {
      return 1;
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      BlockPos pos = context.getClickedPos();
      Direction facing = context.getClickedFace();
      InteractionHand hand = context.getHand();
      Level world = context.getLevel();
      if (context.getClickedFace() == Direction.DOWN) {
         return InteractionResult.FAIL;
      }

      BlockPos location = pos.relative(facing);
      ItemStack stack = player.getItemInHand(hand);
      if (!player.mayUseItemAt(location, facing, stack)) {
         return InteractionResult.FAIL;
      }

      if (!world.isClientSide) {
         EntityGrottol grottol = new EntityGrottol((EntityType<? extends EntityGrottol>)EntityHandler.GROTTOL.get(), world);
         CustomData data = (CustomData)stack.get(DataComponents.CUSTOM_DATA);
         if (data != null) {
            this.setData(grottol, data.copyTag().getCompound("EntityTag"));
         }

         grottol.moveTo(location, 0.0F, 0.0F);
         this.lookAtPlayer(grottol, player);
         grottol.finalizeSpawn((ServerLevelAccessor)world, world.getCurrentDifficultyAt(location), MobSpawnType.MOB_SUMMONED, null);
         world.addFreshEntity(grottol);
         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }
      }

      return InteractionResult.SUCCESS;
   }

   private void setData(EntityGrottol grottol, CompoundTag compound) {
      CompoundTag data = grottol.serializeNBT(grottol.registryAccess());
      UUID id = grottol.getUUID();
      data.merge(compound);
      grottol.deserializeNBT(grottol.registryAccess(), data);
      grottol.setUUID(id);
   }

   private void lookAtPlayer(EntityGrottol grottol, Player player) {
      LookControl helper = new LookControl(grottol);
      helper.setLookAt(player, 180.0F, 90.0F);
      helper.tick();
   }

   public ItemStack create(EntityGrottol grottol) {
      ItemStack stack = new ItemStack(this);
      ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY))
         .update(tag -> tag.put("EntityTag", grottol.serializeNBT(grottol.registryAccess())));
      return stack;
   }
}
