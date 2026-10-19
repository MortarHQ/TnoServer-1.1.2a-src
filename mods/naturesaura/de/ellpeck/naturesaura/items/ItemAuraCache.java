package de.ellpeck.naturesaura.items;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.item.IAuraRecharge;
import de.ellpeck.naturesaura.api.render.ITrinketItem;
import de.ellpeck.naturesaura.reg.ICustomCreativeTab;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemAuraCache extends ItemImpl implements ITrinketItem, ICustomCreativeTab {
   public ItemAuraCache(String name) {
      super(name, new Properties().stacksTo(1));
   }

   public void inventoryTick(ItemStack stackIn, Level levelIn, Entity entityIn, int itemSlot, boolean isSelected) {
      if (!levelIn.isClientSide && entityIn instanceof Player player && player.isShiftKeyDown()) {
         IAuraContainer container = (IAuraContainer)stackIn.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
         if (container == null || container.getStoredAura() <= 0) {
            return;
         }

         for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack stack = player.getInventory().getItem(i);
            IAuraRecharge recharge = (IAuraRecharge)stack.getCapability(NaturesAuraAPI.AURA_RECHARGE_CAPABILITY);
            if (recharge != null && recharge.rechargeFromContainer(container, itemSlot, i, player.getInventory().selected == i)) {
               break;
            }
         }
      }
   }

   @Override
   public List<ItemStack> getCreativeTabItems() {
      ArrayList<ItemStack> ret = new ArrayList<>();
      ret.add(new ItemStack(this));
      ItemStack full = new ItemStack(this);
      IAuraContainer container = (IAuraContainer)full.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
      container.storeAura(container.getMaxAura(), false);
      ret.add(full);
      return ret;
   }

   public boolean isBarVisible(ItemStack stack) {
      return true;
   }

   public int getBarWidth(ItemStack stack) {
      IAuraContainer container = (IAuraContainer)stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
      return container != null ? Math.round((float)container.getStoredAura() / container.getMaxAura() * 13.0F) : 0;
   }

   public int getBarColor(ItemStack stack) {
      IAuraContainer cap = (IAuraContainer)stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
      return cap != null ? cap.getAuraColor() : super.getBarColor(stack);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void render(
      ItemStack stack, Player player, ITrinketItem.RenderType type, PoseStack matrices, MultiBufferSource buffer, int packedLight, boolean isHolding
   ) {
      if (type == ITrinketItem.RenderType.BODY && !isHolding) {
         boolean chest = !((ItemStack)player.getInventory().armor.get(EquipmentSlot.CHEST.getIndex())).isEmpty();
         boolean legs = !((ItemStack)player.getInventory().armor.get(EquipmentSlot.LEGS.getIndex())).isEmpty();
         matrices.translate(-0.15F, 0.65F, chest ? -0.195F : (legs ? -0.165F : -0.1475F));
         matrices.scale(0.5F, 0.5F, 0.5F);
         matrices.mulPose(Axis.XP.rotationDegrees(180.0F));
         Minecraft.getInstance()
            .getItemRenderer()
            .renderStatic(stack, ItemDisplayContext.GROUND, packedLight, OverlayTexture.NO_OVERLAY, matrices, buffer, player.level(), 0);
      }
   }
}
