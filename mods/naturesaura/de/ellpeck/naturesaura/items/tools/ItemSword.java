package de.ellpeck.naturesaura.items.tools;

import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.items.ModItems;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import de.ellpeck.naturesaura.reg.IModItem;
import de.ellpeck.naturesaura.reg.ModRegistry;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import org.jetbrains.annotations.NotNull;

public class ItemSword extends SwordItem implements IModItem, ICustomItemModel {
   private final String baseName;

   public ItemSword(String baseName, Tier material, int damage, float speed) {
      super(material, new Properties().attributes(SwordItem.createAttributes(material, damage, speed)));
      this.baseName = baseName;
      ModRegistry.ALL_ITEMS.add(this);
   }

   @Override
   public String getBaseName() {
      return this.baseName;
   }

   public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
      if (this == ModItems.INFUSED_IRON_SWORD) {
         target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60, 2));
      } else if (this == ModItems.SKY_SWORD) {
         target.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 60, 2));
      } else if (this == ModItems.DEPTH_SWORD && attacker instanceof Player player) {
         float damage = (float)player.getAttributeValue(Attributes.ATTACK_DAMAGE) * 0.75F;

         for (LivingEntity other : player.level().getEntitiesOfClass(LivingEntity.class, stack.getSweepHitBox(player, target))) {
            if (other != player
               && other != target
               && !player.isAlliedTo(other)
               && !(other instanceof ArmorStand stand && stand.isMarker())
               && player.distanceToSqr(other) < Mth.square(player.entityInteractionRange())) {
               other.knockback(0.4F, Mth.sin(player.getYRot() * (float) (Math.PI / 180.0)), -Mth.cos(player.getYRot() * (float) (Math.PI / 180.0)));
               other.hurt(other.damageSources().playerAttack(player), damage);
            }
         }

         player.sweepAttack();
      }

      return super.hurtEnemy(stack, target, attacker);
   }

   @NotNull
   public AABB getSweepHitBox(@NotNull ItemStack stack, @NotNull Player player, @NotNull Entity target) {
      return this == ModItems.DEPTH_SWORD ? target.getBoundingBox().inflate(2.0, 1.0, 2.0) : super.getSweepHitBox(stack, player, target);
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/handheld")).texture("layer0", "item/" + this.getBaseName());
   }
}
