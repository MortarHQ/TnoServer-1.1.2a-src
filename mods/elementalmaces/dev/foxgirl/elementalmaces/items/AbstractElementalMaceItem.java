package dev.foxgirl.elementalmaces.items;

import java.util.List;
import java.util.function.Function;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MaceItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public abstract class AbstractElementalMaceItem extends MaceItem {
   public AbstractElementalMaceItem(
      Function<Properties, Properties> propertiesFunction, Function<ItemAttributeModifiers, ItemAttributeModifiers> attributesFunction
   ) {
      super(
         propertiesFunction.apply(
            new Properties()
               .stacksTo(1)
               .rarity(Rarity.EPIC)
               .durability(500)
               .component(DataComponents.TOOL, MaceItem.createToolProperties())
               .attributes(attributesFunction.apply(MaceItem.createAttributes()))
         )
      );
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);
      if (entity instanceof ServerPlayer player) {
         this.onTick(stack, player, slotId, isSelected, player.getMainHandItem() == stack || player.getOffhandItem() == stack);
      }
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (super.hurtEnemy(stack, target, attacker)) {
         return attacker instanceof ServerPlayer && canSmashAttack(attacker) ? this.onSmashAttack(stack, (ServerPlayer)attacker, target) : true;
      } else {
         return false;
      }
   }

   protected static List<LivingEntity> getNearbyTargets(ServerPlayer player, LivingEntity target, double range) {
      return player.level()
         .getEntitiesOfClass(LivingEntity.class, AABB.ofSize(target.position(), 0.0, 0.0, 0.0).inflate(range), entity -> entity != player && entity.isAlive());
   }

   protected abstract void onTick(ItemStack var1, ServerPlayer var2, int var3, boolean var4, boolean var5);

   protected abstract boolean onSmashAttack(ItemStack var1, ServerPlayer var2, LivingEntity var3);
}
