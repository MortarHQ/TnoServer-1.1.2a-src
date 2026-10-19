package twilightforest.entity.passive;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.BreedGoal;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.animal.Fox;
import net.minecraft.world.entity.animal.Ocelot;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import twilightforest.TFRegistries;
import twilightforest.data.tags.ItemTagGenerator;
import twilightforest.init.TFDataSerializers;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;
import twilightforest.init.custom.DwarfRabbitVariants;

public class DwarfRabbit extends Animal implements VariantHolder<Holder<DwarfRabbitVariant>> {
   private static final EntityDataAccessor<Holder<DwarfRabbitVariant>> VARIANT = SynchedEntityData.defineId(
      DwarfRabbit.class, (EntityDataSerializer)TFDataSerializers.DWARF_RABBIT_VARIANT.get()
   );

   public DwarfRabbit(EntityType<? extends DwarfRabbit> type, Level world) {
      super(type, world);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 2.0));
      this.goalSelector.addGoal(2, new BreedGoal(this, 0.8));
      this.goalSelector.addGoal(2, new TemptGoal(this, 1.0, Ingredient.of(ItemTagGenerator.DWARF_RABBIT_TEMPT_ITEMS), false));
      this.goalSelector.addGoal(3, new AvoidEntityGoal(this, Player.class, 2.0F, 0.8F, 1.33F));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Ocelot.class, 8.0F, 0.8F, 1.1F));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Cat.class, 8.0F, 0.8F, 1.1F));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Wolf.class, 8.0F, 0.8F, 1.1F));
      this.goalSelector.addGoal(4, new AvoidEntityGoal(this, Fox.class, 8.0F, 0.8F, 1.1F));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 0.8F));
      this.goalSelector.addGoal(6, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 3.0).add(Attributes.MOVEMENT_SPEED, 0.3).add(Attributes.STEP_HEIGHT, 1.0);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
      DwarfRabbit dwarf = (DwarfRabbit)((EntityType)TFEntities.DWARF_RABBIT.get()).create(level);
      Holder<DwarfRabbitVariant> variant = DwarfRabbitVariant.getRandomCommonVariant(level.registryAccess(), level.getRandom());
      if (dwarf != null && mob instanceof DwarfRabbit parent) {
         if (this.getRandom().nextInt(20) != 0) {
            if (this.getRandom().nextBoolean()) {
               variant = this.getVariant();
            } else {
               variant = parent.getVariant();
            }
         }

         dwarf.setVariant(variant);
      }

      return dwarf;
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(VARIANT, this.registryAccess().registryOrThrow(TFRegistries.Keys.DWARF_RABBIT_VARIANT).getHolderOrThrow(DwarfRabbitVariants.BROWN));
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putString("variant", this.getVariant().unwrapKey().orElse(DwarfRabbitVariants.BROWN).location().toString());
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      Optional.ofNullable(ResourceLocation.tryParse(compound.getString("variant")))
         .map(location -> ResourceKey.create(TFRegistries.Keys.DWARF_RABBIT_VARIANT, location))
         .flatMap(key -> this.registryAccess().registryOrThrow(TFRegistries.Keys.DWARF_RABBIT_VARIANT).getHolder(key))
         .ifPresent(this::setVariant);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType type, @Nullable SpawnGroupData data) {
      data = super.finalizeSpawn(accessor, difficulty, type, data);
      this.setVariant(DwarfRabbitVariant.getVariant(accessor.registryAccess(), accessor.getBiome(this.blockPosition()), this.getRandom()));
      return data;
   }

   public Holder<DwarfRabbitVariant> getVariant() {
      return (Holder<DwarfRabbitVariant>)this.getEntityData().get(VARIANT);
   }

   public void setVariant(Holder<DwarfRabbitVariant> variant) {
      this.getEntityData().set(VARIANT, variant);
   }

   public boolean removeWhenFarAway(double distance) {
      return false;
   }

   public float getWalkTargetValue(BlockPos pos) {
      BlockState underMaterial = this.level().getBlockState(pos.below());
      if (underMaterial.is(BlockTags.LEAVES) || underMaterial.is(BlockTags.LOGS)) {
         return -1.0F;
      } else {
         return underMaterial.is(BlockTags.DIRT) ? 10.0F : this.level().getMaxLocalRawBrightness(pos) - 0.5F;
      }
   }

   public boolean isFood(ItemStack stack) {
      return stack.is(ItemTagGenerator.DWARF_RABBIT_TEMPT_ITEMS);
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.DWARF_RABBIT_DEATH.get();
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.DWARF_RABBIT_HURT.get();
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.DWARF_RABBIT_AMBIENT.get();
   }
}
