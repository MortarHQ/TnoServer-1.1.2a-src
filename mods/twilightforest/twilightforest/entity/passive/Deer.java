package twilightforest.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFSounds;

public class Deer extends Animal {
   public Deer(EntityType<? extends Deer> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.25, Ingredient.of(ItemTagGenerator.DEER_TEMPT_ITEMS), false));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Player.class, 16.0F, 1.5, 1.8));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.2);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      InteractionResult result = super.mobInteract(player, hand);
      ItemStack stack = player.getItemInHand(hand);
      if (result == InteractionResult.PASS && stack.is(TFItems.SHIKA_SENBEI) && this.getHealth() < this.getMaxHealth()) {
         this.usePlayerItem(player, hand, stack);
         return InteractionResult.SUCCESS;
      } else {
         return result;
      }
   }

   public void usePlayerItem(Player player, InteractionHand hand, ItemStack stack) {
      if (stack.is(TFItems.SHIKA_SENBEI)) {
         if (!this.level().isClientSide()) {
            this.heal(4.0F);
         }

         this.level()
            .playSound(
               null,
               this.getX(),
               this.getY(),
               this.getZ(),
               (SoundEvent)TFSounds.DEER_EAT.get(),
               this.getSoundSource(),
               1.0F,
               1.0F + (this.random.nextFloat() - this.random.nextFloat()) * 0.2F
            );
      }

      super.usePlayerItem(player, hand, stack);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.DEER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.DEER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.DEER_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
   }

   public Deer getBreedOffspring(ServerLevel level, AgeableMob mate) {
      return (Deer)((EntityType)TFEntities.DEER.get()).create(level);
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(ItemTagGenerator.DEER_TEMPT_ITEMS);
   }
}
