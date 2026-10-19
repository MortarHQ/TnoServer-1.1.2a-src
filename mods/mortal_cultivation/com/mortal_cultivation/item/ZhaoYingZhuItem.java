package com.mortal_cultivation.item;

import com.mortal_cultivation.block.entity.AncientSteleBlockEntity;
import com.mortal_cultivation.client.renderer.ZhaoYingZhuItemRenderer;
import com.mortal_cultivation.entity.ZhaoYingZhuEntity;
import com.mortal_cultivation.registry.BlockRegistry;
import com.mortal_cultivation.registry.CultivationEntities;
import java.util.function.Consumer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoItem;
import software.bernie.geckolib.animatable.client.GeoRenderProvider;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public final class ZhaoYingZhuItem extends Item implements GeoItem {
   private static final String STORED_RUNES_TAG = "StoredRunes";
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

   public ZhaoYingZhuItem(Properties properties) {
      super(properties.stacksTo(1).durability(5));
   }

   public void createGeoRenderer(Consumer<GeoRenderProvider> consumer) {
      consumer.accept(new GeoRenderProvider() {
         private ZhaoYingZhuItemRenderer renderer;

         @NotNull
         public BlockEntityWithoutLevelRenderer getGeoItemRenderer() {
            if (this.renderer == null) {
               this.renderer = new ZhaoYingZhuItemRenderer();
            }

            return this.renderer;
         }
      });
   }

   public void registerControllers(ControllerRegistrar controllers) {
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (!level.getBlockState(context.getClickedPos()).is((Block)BlockRegistry.ANCIENT_STELE.get())) {
         return InteractionResult.PASS;
      } else {
         Player player = context.getPlayer();
         ItemStack heldStack = context.getItemInHand();
         if (player == null
            || !SpiritGlassesItem.isEquipped(player)
            || hasStoredRunes(heldStack)
            || !(level.getBlockEntity(context.getClickedPos()) instanceof AncientSteleBlockEntity stele && stele.canReserveOrb())) {
            return InteractionResult.FAIL;
         } else if (level.isClientSide) {
            return InteractionResult.SUCCESS;
         } else {
            ZhaoYingZhuEntity orb = new ZhaoYingZhuEntity((EntityType<? extends ZhaoYingZhuEntity>)CultivationEntities.ZHAO_YING_ZHU.get(), level);
            ItemStack carriedStack = heldStack.copy();
            carriedStack.setCount(1);
            orb.configure(player, context.getClickedPos(), carriedStack);
            orb.setPos(player.getX(), player.getEyeY() - 0.2, player.getZ());
            if (!stele.reserveOrb(orb.getUUID())) {
               return InteractionResult.FAIL;
            } else if (!level.addFreshEntity(orb)) {
               stele.cancelOrb(orb.getUUID());
               return InteractionResult.FAIL;
            } else {
               heldStack.shrink(1);
               return InteractionResult.SUCCESS;
            }
         }
      }
   }

   public static boolean hasStoredRunes(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      return tag.getBoolean("StoredRunes");
   }

   public static void setStoredRunes(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.putBoolean("StoredRunes", true);
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }

   public static void clearStoredRunes(ItemStack stack) {
      CompoundTag tag = ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
      tag.remove("StoredRunes");
      stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
   }
}
