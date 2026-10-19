package com.github.L_Ender.cataclysm.items;

import com.github.L_Ender.cataclysm.entity.projectile.Amethyst_Cluster_Projectile_Entity;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.items.CuriosItem.AttributeContainer;
import com.github.L_Ender.cataclysm.message.MessageArmorKey;
import java.util.List;
import javax.annotation.Nonnull;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.ArmorItem.Type;
import net.minecraft.world.item.ArmorMaterial.Layer;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;

public class Bloom_Stone_Pauldrons extends Cataclysm_Armor implements KeybindUsingArmor {
   public Bloom_Stone_Pauldrons(Holder<ArmorMaterial> material, Type slot, Properties properties, AttributeContainer... attributes) {
      super(material, slot, properties, attributes);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltips, TooltipFlag flags) {
      tooltips.add(
         Component.translatable("item.cataclysm.bloom_stone_pauldrons.desc", new Object[]{ModKeybind.CHESTPLATE_KEY_ABILITY.getTranslatedKeyMessage()})
            .withStyle(ChatFormatting.DARK_GREEN)
      );
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int i, boolean held) {
      super.inventoryTick(stack, level, entity, i, held);
      if (entity instanceof Player player && level.isClientSide) {
         if (this.type == Type.CHESTPLATE && player.getItemBySlot(EquipmentSlot.CHEST) == stack && ModKeybind.CHESTPLATE_KEY_ABILITY.consumeClick()) {
            PacketDistributor.sendToServer(new MessageArmorKey(EquipmentSlot.CHEST.ordinal(), player.getId(), 6), new CustomPacketPayload[0]);
            this.onKeyPacket(player, stack, 6);
         }
      }
   }

   @Override
   public void onKeyPacket(Player player, ItemStack itemStack, int Type) {
      if (player != null) {
         if (Type == 6 && !player.getCooldowns().isOnCooldown((Item)ModItems.BLOOM_STONE_PAULDRONS.get())) {
            for (int i = 0; i < 8; i++) {
               float throwAngle = i * (float) Math.PI / 4.0F;
               double sx = player.getX() + Mth.cos(throwAngle) * 1.0F;
               double sy = player.getY() + player.getBbHeight() * 0.5;
               double sz = player.getZ() + Mth.sin(throwAngle) * 1.0F;
               double vx = Mth.cos(throwAngle);
               double vy = 0.0F + player.getRandom().nextFloat() * 0.3F;
               double vz = Mth.sin(throwAngle);
               double v3 = Mth.sqrt((float)(vx * vx + vz * vz));
               Amethyst_Cluster_Projectile_Entity projectile = new Amethyst_Cluster_Projectile_Entity(
                  (EntityType<Amethyst_Cluster_Projectile_Entity>)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), player.level(), player, 11.0F
               );
               projectile.moveTo(sx, sy, sz, i * 11.25F, player.getXRot());
               float speed = 0.8F;
               projectile.shoot(vx, vy + v3 * 0.2F, vz, speed, 1.0F);
               player.level().addFreshEntity(projectile);
            }

            player.getCooldowns().addCooldown((Item)ModItems.BLOOM_STONE_PAULDRONS.get(), 240);
         }
      }
   }

   public ResourceLocation getArmorTexture(
      @Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EquipmentSlot slot, @Nonnull Layer layer, boolean isInnerModel
   ) {
      return ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/armor/bloom_stone_pauldrons.png");
   }
}
