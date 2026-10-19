package fuzs.tinyskeletons.world.entity.monster;

import fuzs.tinyskeletons.world.entity.monster.projectile.Mushroom;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.entity.monster.Bogged;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BabyBogged extends Bogged {
   public BabyBogged(EntityType<? extends Bogged> entityType, Level level) {
      super(entityType, level);
      this.xpReward = (int)(this.xpReward * 2.5F);
      this.refreshDimensions();
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(1, new RangedAttackGoal(this, 1.0, 30, 15.0F) {
         public boolean canUse() {
            return super.canUse() && BabyBogged.this.getMainHandItem().isEmpty();
         }
      });
      super.registerGoals();
   }

   public float getPickRadius() {
      return 0.3F;
   }

   public boolean isBaby() {
      return true;
   }

   protected EntityDimensions getDefaultDimensions(Pose pose) {
      return super.getDefaultDimensions(pose).withEyeHeight(this.getType().getDimensions().eyeHeight() * (this.isBaby() ? 0.534F : 1.0F));
   }

   protected void populateDefaultEquipmentSlots(RandomSource random, DifficultyInstance difficulty) {
      super.populateDefaultEquipmentSlots(random, difficulty);
      this.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
   }

   public void reassessWeaponGoal() {
   }

   public void performRangedAttack(LivingEntity target, float velocity) {
      Mushroom mushroom = new Mushroom(this.level(), this);
      double d0 = target.getEyeY() - 1.1F;
      double d1 = target.getX() - this.getX();
      double d2 = d0 - mushroom.getY();
      double d3 = target.getZ() - this.getZ();
      double f = Math.sqrt(d1 * d1 + d3 * d3) * 0.2;
      mushroom.shoot(d1, d2 + f, d3, 1.6F, 14.0F - this.level().getDifficulty().getId() * 2.0F);
      this.playSound(SoundEvents.SNOW_GOLEM_SHOOT, 1.0F, 0.4F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
      this.level().addFreshEntity(mushroom);
      this.swing(InteractionHand.MAIN_HAND);
   }
}
