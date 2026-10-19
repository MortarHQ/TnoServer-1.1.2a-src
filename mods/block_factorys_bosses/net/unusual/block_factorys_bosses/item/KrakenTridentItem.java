package net.unusual.block_factorys_bosses.item;

import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.FieldsAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.AbstractArrow.Pickup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.TridentItem;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.unusual.block_factorys_bosses.entity.projectile.ThrownKrakenTridentEntity;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;

@FieldsAreNonnullByDefault
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class KrakenTridentItem extends TridentItem {
   public KrakenTridentItem(Properties properties) {
      super(properties);
   }

   public static ItemAttributeModifiers createAttributes() {
      return ItemAttributeModifiers.builder()
         .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_ID, 9.0, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .add(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_ID, -2.9F, Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
         .build();
   }

   @OnlyIn(Dist.CLIENT)
   public void appendHoverText(ItemStack itemstack, TooltipContext context, List<Component> list, TooltipFlag flag) {
      super.appendHoverText(itemstack, context, list, flag);
      list.add(Component.translatable("item.block_factorys_bosses.kraken_trident.description_0"));
      list.add(Component.translatable("item.block_factorys_bosses.kraken_trident.description_1"));
   }

   public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
      return stack.is((Item)BossesRiseItems.KRAKEN_TOOTH.get());
   }

   public Projectile asProjectile(Level level, Position pos, ItemStack stack, Direction direction) {
      ThrownKrakenTridentEntity entity = new ThrownKrakenTridentEntity(level, pos.x(), pos.y(), pos.z(), stack.copyWithCount(1));
      entity.pickup = Pickup.ALLOWED;
      return entity;
   }
}
