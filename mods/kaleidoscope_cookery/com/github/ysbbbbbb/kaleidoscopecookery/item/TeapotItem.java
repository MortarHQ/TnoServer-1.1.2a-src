package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.crafting.serializer.TeapotRecipeSerializer;
import com.github.ysbbbbbb.kaleidoscopecookery.init.ModBlocks;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.capabilities.Capabilities.FluidHandler;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.common.SoundActions;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import org.apache.commons.lang3.StringUtils;

public class TeapotItem extends BlockItem {
   public TeapotItem() {
      super((Block)ModBlocks.TEAPOT.get(), new Properties().stacksTo(1));
   }

   public static ItemStack getPourOut(ItemStack stack, Level level) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data == null) {
         return ItemStack.EMPTY;
      }

      CompoundTag tag = data.copyTag();
      int status = tag.getInt("Status");
      return status != 2 ? ItemStack.EMPTY : ItemStack.parseOptional(level.registryAccess(), tag.getCompound("Result"));
   }

   public static void pourOut(ItemStack stack, Level level) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data != null) {
         CompoundTag tag = data.copyTag();
         int status = tag.getInt("Status");
         if (status == 2) {
            ItemStack result = ItemStack.parseOptional(level.registryAccess(), tag.getCompound("Result"));
            if (!result.isEmpty()) {
               result.shrink(1);
               if (result.isEmpty()) {
                  stack.remove(DataComponents.BLOCK_ENTITY_DATA);
               } else {
                  tag.put("Result", result.saveOptional(level.registryAccess()));
                  BlockItem.setBlockEntityData(stack, ModBlocks.TEAPOT_BE.get(), tag);
               }
            }
         }
      }
   }

   public static boolean fillFluid(ItemStack stack, Fluid fluid, LivingEntity user) {
      CustomData data = (CustomData)stack.getOrDefault(DataComponents.BLOCK_ENTITY_DATA, CustomData.EMPTY);
      CompoundTag tag = data.copyTag();
      int status = tag.getInt("Status");
      if (status != 0) {
         return false;
      }

      String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
      if (!fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString())) {
         return false;
      }

      ResourceLocation key = BuiltInRegistries.FLUID.getKey(fluid);
      tag.putString("TeaFluidId", key.toString());
      BlockItem.setBlockEntityData(stack, ModBlocks.TEAPOT_BE.get(), tag);
      SoundEvent sound = fluid.getFluidType().getSound(user, SoundActions.BUCKET_FILL);
      if (sound != null) {
         user.playSound(sound);
      }

      return true;
   }

   public static void clearAll(ItemStack stack, Player player) {
      stack.remove(DataComponents.BLOCK_ENTITY_DATA);
      player.playSound(SoundEvents.BUCKET_EMPTY, 1.0F, 1.0F);
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      return player != null && !player.isSecondaryUseActive() ? InteractionResult.PASS : super.useOn(context);
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data == null) {
         return InteractionResult.PASS;
      }

      CompoundTag tag = data.copyTag();
      Level level = player.level();
      int status = tag.getInt("Status");
      if (status == 2) {
         pourOut(stack, level);
      } else if (status == 0) {
         String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
         if (!"minecraft:lava".equals(fluidId)) {
            return InteractionResult.PASS;
         }

         if (player.getRandom().nextFloat() < 0.3F) {
            clearAll(stack, player);
         }
      }

      RandomSource random = level.random;
      target.hurt(level.damageSources().inFire(), 3.0F);
      double x = target.getX();
      double y = target.getY() + target.getEyeHeight() + 0.25;
      double z = target.getZ();
      player.playSound(SoundEvents.FIRE_EXTINGUISH, 1.0F, 1.0F);

      for (int i = 0; i < 10; i++) {
         level.addParticle(
            ParticleTypes.LAVA,
            x + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
            y + random.nextDouble() / 3.0,
            z + random.nextDouble() / 3.0 * (random.nextBoolean() ? 1 : -1),
            0.3,
            0.1,
            0.3
         );
      }

      return InteractionResult.SUCCESS;
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemInHand = player.getItemInHand(hand);
      CustomData data = (CustomData)itemInHand.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data != null) {
         CompoundTag tag = data.copyTag();
         String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
         if (!fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString())) {
            return InteractionResultHolder.fail(itemInHand);
         }
      }

      BlockHitResult hitResult = getPlayerPOVHitResult(level, player, net.minecraft.world.level.ClipContext.Fluid.SOURCE_ONLY);
      if (hitResult.getType() == Type.MISS) {
         return InteractionResultHolder.pass(itemInHand);
      }

      if (hitResult.getType() != Type.BLOCK) {
         return InteractionResultHolder.pass(itemInHand);
      }

      BlockPos pos = hitResult.getBlockPos();
      Direction direction = hitResult.getDirection();
      BlockPos relative = pos.relative(direction);
      if (level.mayInteract(player, pos) && player.mayUseItemAt(relative, direction, itemInHand)) {
         BlockState blockState = level.getBlockState(pos);
         if (blockState.getBlock() instanceof BucketPickup bucketpickup) {
            ItemStack pickup = bucketpickup.pickupBlock(player, level, pos, blockState);
            if (pickup.isEmpty()) {
               return InteractionResultHolder.fail(itemInHand);
            }

            IFluidHandlerItem capability = (IFluidHandlerItem)pickup.getCapability(FluidHandler.ITEM);
            if (capability == null) {
               return InteractionResultHolder.fail(itemInHand);
            }

            Fluid fluid = capability.getFluidInTank(0).getFluid();
            boolean result = fillFluid(itemInHand, fluid, player);
            return result ? InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide()) : InteractionResultHolder.fail(itemInHand);
         } else {
            return InteractionResultHolder.fail(itemInHand);
         }
      } else {
         return InteractionResultHolder.fail(itemInHand);
      }
   }

   public boolean isBarVisible(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data == null) {
         return false;
      } else {
         CompoundTag tag = data.copyTag();
         int status = tag.getInt("Status");
         if (status == 0) {
            String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
            return !fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
         } else {
            return status == 2;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public int getBarColor(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data == null) {
         return 10352639;
      }

      CompoundTag tag = data.copyTag();
      int status = tag.getInt("Status");
      if (status == 0) {
         String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
         if (fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString())) {
            return 10352639;
         }

         Fluid fluid = (Fluid)BuiltInRegistries.FLUID.get(ResourceLocation.parse(fluidId));
         return fluid.equals(Fluids.LAVA) ? 16492544 : IClientFluidTypeExtensions.of(fluid).getTintColor();
      } else {
         return 10352639;
      }
   }

   public int getBarWidth(ItemStack stack) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data == null) {
         return 0;
      } else {
         CompoundTag tag = data.copyTag();
         int status = tag.getInt("Status");
         if (status == 0) {
            String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
            return fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString()) ? 0 : 13;
         } else if (status == 2) {
            int count = tag.getCompound("Result").getInt("count");
            return count <= 0 ? 0 : Math.round(13.0F * count / 12.0F);
         } else {
            return 0;
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> list, TooltipFlag pFlag) {
      CustomData data = (CustomData)stack.get(DataComponents.BLOCK_ENTITY_DATA);
      if (data != null) {
         CompoundTag tag = data.copyTag();
         int status = tag.getInt("Status");
         if (status == 0) {
            String fluidId = (String)StringUtils.defaultIfBlank(tag.getString("TeaFluidId"), TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString());
            if (fluidId.equals(TeapotRecipeSerializer.EMPTY_TEA_FLUID.toString())) {
               return;
            }

            ResourceLocation key = ResourceLocation.parse(fluidId);
            Fluid fluid = (Fluid)BuiltInRegistries.FLUID.get(key);
            list.add(Component.translatable(fluid.getFluidType().getDescriptionId()).withStyle(ChatFormatting.GRAY));
         }

         ClientLevel level = Minecraft.getInstance().level;
         if (status == 2 && level != null) {
            ItemStack result = ItemStack.parseOptional(level.registryAccess(), tag.getCompound("Result"));
            if (result.isEmpty()) {
               return;
            }

            Component resultComponent = ComponentUtils.formatList(
                  Arrays.asList(result.getHoverName(), Component.literal("x%d".formatted(result.getCount()))), CommonComponents.space(), Function.identity()
               )
               .withStyle(ChatFormatting.GRAY);
            list.add(resultComponent);
         }
      }
   }
}
