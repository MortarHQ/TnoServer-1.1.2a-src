package artifacts.attribute;

import artifacts.Artifacts;
import artifacts.item.UmbrellaItem;
import artifacts.mixin.accessors.EntityAccessor;
import artifacts.registry.ModAttributes;
import artifacts.registry.ModTags;
import java.util.Objects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.Saddleable;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;

public abstract class DynamicAttributeModifier {
   private static final DynamicAttributeModifier MOUNT_SPEED = new DynamicAttributeModifier(
      Artifacts.id("mount_speed"), Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE
   ) {
      @Override
      protected boolean shouldApply(LivingEntity entity) {
         return entity.getControllingPassenger() != null;
      }

      @Override
      protected double getAmount(LivingEntity entity) {
         return Objects.requireNonNull(entity.getControllingPassenger()).getAttributeValue(ModAttributes.MOUNT_SPEED) - 1.0;
      }
   };
   private static final DynamicAttributeModifier SPRINTING_SPEED = new DynamicAttributeModifier(
      Artifacts.id("sprinting_speed"), Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE
   ) {
      @Override
      protected boolean shouldApply(LivingEntity entity) {
         return entity.isSprinting();
      }

      @Override
      protected double getAmount(LivingEntity entity) {
         return entity.getAttributeValue(ModAttributes.SPRINTING_SPEED) - 1.0;
      }
   };
   private static final DynamicAttributeModifier SPRINTING_STEP_HEIGHT = new DynamicAttributeModifier(
      Artifacts.id("sprinting_step_height"), Attributes.STEP_HEIGHT, Operation.ADD_VALUE
   ) {
      @Override
      protected boolean shouldApply(LivingEntity entity) {
         return entity.isSprinting();
      }

      @Override
      protected double getAmount(LivingEntity entity) {
         return entity.getAttributeValue(ModAttributes.SPRINTING_STEP_HEIGHT);
      }
   };
   private static final DynamicAttributeModifier UMBRELLA_GRAVITY = new DynamicAttributeModifier(
      Artifacts.id("umbrella_gravity"), Attributes.GRAVITY, Operation.ADD_MULTIPLIED_TOTAL
   ) {
      @Override
      protected boolean shouldApply(LivingEntity entity) {
         return UmbrellaItem.shouldGlide(entity);
      }

      @Override
      protected double getAmount(LivingEntity entity) {
         return -0.875;
      }
   };
   private static final DynamicAttributeModifier MOVEMENT_SPEED_ON_SNOW = new DynamicAttributeModifier(
      Artifacts.id("movement_speed_on_snow"), Attributes.MOVEMENT_SPEED, Operation.ADD_MULTIPLIED_BASE
   ) {
      @Override
      protected boolean shouldApply(LivingEntity entity) {
         if (entity.onGround() && entity instanceof EntityAccessor entityAccessor && entity.getAttributeValue(ModAttributes.MOVEMENT_SPEED_ON_SNOW) != 1.0) {
            BlockState onState = entity.level().getBlockState(entityAccessor.callGetBlockPosBelowThatAffectsMyMovement());
            if (onState.is(BlockTags.SNOW)) {
               return true;
            }

            BlockState aboveState = entity.level().getBlockState(entityAccessor.callGetBlockPosBelowThatAffectsMyMovement().above());
            return !aboveState.isAir() && aboveState.is(ModTags.SNOW_LAYERS);
         } else {
            return false;
         }
      }

      @Override
      protected double getAmount(LivingEntity entity) {
         return entity.getAttributeValue(ModAttributes.MOVEMENT_SPEED_ON_SNOW) - 1.0;
      }
   };
   private final Holder<Attribute> attribute;
   private final Operation operation;
   private final ResourceLocation id;

   public DynamicAttributeModifier(ResourceLocation id, Holder<Attribute> attribute, Operation operation) {
      this.attribute = attribute;
      this.operation = operation;
      this.id = id;
   }

   protected abstract boolean shouldApply(LivingEntity var1);

   protected abstract double getAmount(LivingEntity var1);

   private void tick(LivingEntity entity) {
      AttributeInstance attributeInstance = entity.getAttribute(this.attribute);
      if (attributeInstance != null) {
         if (this.shouldApply(entity)) {
            double amount = this.getAmount(entity);
            AttributeModifier modifier = attributeInstance.getModifier(this.id);
            if (modifier == null || modifier.amount() != amount) {
               attributeInstance.addOrUpdateTransientModifier(new AttributeModifier(this.id, amount, this.operation));
            }
         } else if (attributeInstance.hasModifier(this.id)) {
            attributeInstance.removeModifier(this.id);
         }
      }
   }

   private static boolean shouldUpdateModifiers(LivingEntity entity) {
      if (entity.tickCount % 10 != 0) {
         return entity instanceof Player || entity.getControllingPassenger() instanceof Player;
      } else {
         return !(entity instanceof Mob mob && mob.getTarget() != null) && !(entity instanceof OwnableEntity ownable && ownable.getOwner() instanceof Player)
            ? entity.tickCount % 20 == 0
            : true;
      }
   }

   public static void tickModifiers(LivingEntity entity) {
      if (shouldUpdateModifiers(entity)) {
         if (entity.canSprint()) {
            SPRINTING_SPEED.tick(entity);
            SPRINTING_STEP_HEIGHT.tick(entity);
         }

         if (entity instanceof Player player && player.getAbilities().flying || entity.onGround() || entity.isFallFlying()) {
            MOVEMENT_SPEED_ON_SNOW.tick(entity);
         }

         if (entity instanceof Player) {
            UMBRELLA_GRAVITY.tick(entity);
         }

         if (entity instanceof Saddleable) {
            MOUNT_SPEED.tick(entity);
         }
      }
   }
}
