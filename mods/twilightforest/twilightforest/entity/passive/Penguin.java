package twilightforest.entity.passive;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.RandomSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.FollowParentGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.Nullable;
import twilightforest.data.tags.BlockTagGenerator;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class Penguin extends Bird {
   public Penguin(EntityType<? extends Penguin> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.75));
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new TemptGoal(this, 0.75, Ingredient.of(ItemTagGenerator.PENGUIN_TEMPT_ITEMS), false));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.15F));
      this.goalSelector.addGoal(5, new RandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Penguin.class, 5.0F, 0.02F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   @Override
   public Animal getBreedOffspring(ServerLevel level, AgeableMob ageableMob) {
      return (Animal)((EntityType)TFEntities.PENGUIN.get()).create(level);
   }

   @Override
   public boolean isFood(ItemStack stack) {
      return stack.is(ItemTagGenerator.PENGUIN_TEMPT_ITEMS);
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.PENGUIN_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.PENGUIN_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.PENGUIN_DEATH.get();
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.2);
   }

   public boolean checkSpawnRules(LevelAccessor accessor, MobSpawnType type) {
      return true;
   }

   public static boolean canSpawn(EntityType<? extends Penguin> type, LevelAccessor accessor, MobSpawnType reason, BlockPos pos, RandomSource rand) {
      return accessor.getBlockState(pos.below()).is(BlockTagGenerator.PENGUINS_SPAWNABLE_ON);
   }
}
