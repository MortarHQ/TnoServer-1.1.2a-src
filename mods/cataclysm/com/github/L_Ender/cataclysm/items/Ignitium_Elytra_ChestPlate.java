package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.init.ModItems;
import javax.annotation.Nonnull;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;

public class Ignitium_Elytra_ChestPlate extends ArmorItem {
   public Ignitium_Elytra_ChestPlate(Properties props, Holder<ArmorMaterial> material) {
      super(material, Type.CHESTPLATE, props);
   }

   public boolean isValidRepairItem(ItemStack p_41134_, ItemStack p_41135_) {
      return p_41135_.is((Item)ModItems.IGNITIUM_INGOT.get());
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      return this.swapWithEquipmentSlot(this, level, player, hand);
   }

   public boolean canElytraFly(ItemStack stack, LivingEntity entity) {
      return true;
   }

   public boolean elytraFlightTick(ItemStack stack, LivingEntity entity, int flightTicks) {
      if (!entity.level().isClientSide) {
         int nextFlightTick = flightTicks + 1;
         if (nextFlightTick % 10 == 0) {
            entity.gameEvent(GameEvent.ELYTRA_GLIDE);
         }
      }

      return true;
   }

   public ResourceLocation getArmorTexture(
      @Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EquipmentSlot slot, @Nonnull Layer layer, boolean isInnerModel
   ) {
      return ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/armor/ignitium_elytra_chestplate.png");
   }
}
