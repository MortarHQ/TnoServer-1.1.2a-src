package cn.leolezury.eternalstarlight.common.entity.living.animal;

import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESEntVariants;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import cn.leolezury.eternalstarlight.common.util.ESTags;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.Holder.Reference;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.VariantHolder;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.jetbrains.annotations.Nullable;

public class Ent extends Animal implements VariantHolder<Holder<EntVariant>> {
   private static final String TAG_HAS_LEAVES = "has_leaves";
   private static final String TAG_VARIANT = "variant";
   private static final Ingredient FOOD_ITEMS = Ingredient.of(ESTags.Items.ENT_FOOD);
   protected static final EntityDataAccessor<Boolean> HAS_LEAVES = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.BOOLEAN);
   protected static final EntityDataAccessor<String> VARIANT = SynchedEntityData.defineId(Ent.class, EntityDataSerializers.STRING);

   public Ent(EntityType<? extends Ent> type, Level level) {
      super(type, level);
   }

   public boolean hasLeaves() {
      return (Boolean)this.getEntityData().get(HAS_LEAVES);
   }

   public void setHasLeaves(boolean hasLeaves) {
      this.getEntityData().set(HAS_LEAVES, hasLeaves);
   }

   public ResourceLocation getVariantId() {
      return ResourceLocation.parse((String)this.getEntityData().get(VARIANT));
   }

   public void setVariantId(ResourceLocation variant) {
      this.getEntityData().set(VARIANT, variant.toString());
   }

   public void setVariant(Holder<EntVariant> variant) {
      if (variant.isBound()) {
         ResourceLocation key = this.level().registryAccess().registryOrThrow(ESRegistries.ENT_VARIANT).getKey((EntVariant)variant.value());
         if (key != null) {
            this.setVariantId(key);
         }
      }
   }

   public Holder<EntVariant> getVariant() {
      ResourceLocation key = this.getVariantId();
      Registry<EntVariant> variants = this.level().registryAccess().registryOrThrow(ESRegistries.ENT_VARIANT);
      Optional<Reference<EntVariant>> optional = variants.getHolder(key);
      return (Holder<EntVariant>)optional.orElse((Reference<EntVariant>)variants.getHolder(ESEntVariants.LUNAR).orElseThrow());
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(HAS_LEAVES, true).define(VARIANT, ESEntVariants.LUNAR.location().toString());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.25));
      this.goalSelector.addGoal(2, new BreedGoal(this, 1.0));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.2, FOOD_ITEMS, false));
      this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.1));
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("has_leaves", 1)) {
         this.setHasLeaves(compoundTag.getBoolean("has_leaves"));
      }

      this.setVariantId((ResourceLocation)ResourceLocation.read(compoundTag.getString("variant")).getOrThrow());
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putBoolean("has_leaves", this.hasLeaves());
      compoundTag.putString("variant", this.getVariantId().toString());
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.ent.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.ent.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      this.setVariant(EntVariant.getSpawnVariant(level.registryAccess(), level.getBiome(this.blockPosition())));
      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   public InteractionResult mobInteract(Player player, InteractionHand hand) {
      ItemStack stack = player.getItemInHand(hand);
      boolean flag = this.isFood(stack);
      if (!flag) {
         if (ESPlatform.INSTANCE.isShears(stack) && this.hasLeaves()) {
            this.setHasLeaves(false);
            this.spawnAtLocation((ItemLike)((EntVariant)this.getVariant().value()).leaves().value());
            stack.hurtAndBreak(1, player, player.getEquipmentSlotForItem(stack));
            this.playSound(SoundEvents.SHEEP_SHEAR);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         }

         if (stack.is(ESTags.Items.ENT_FERTILIZERS) && !this.hasLeaves()) {
            this.setHasLeaves(true);
            this.usePlayerItem(player, hand, stack);
            this.playSound(SoundEvents.BONE_MEAL_USE);
            player.swing(hand);
            return InteractionResult.sidedSuccess(this.level().isClientSide);
         }
      }

      return super.mobInteract(player, hand);
   }

   protected void dropCustomDeathLoot(ServerLevel level, DamageSource source, boolean recentlyHit) {
      super.dropCustomDeathLoot(level, source, recentlyHit);
      this.spawnAtLocation((ItemLike)((EntVariant)this.getVariant().value()).leaves().value());
   }

   public boolean isFood(ItemStack stack) {
      return FOOD_ITEMS.test(stack);
   }

   @Nullable
   public AgeableMob getBreedOffspring(ServerLevel level, AgeableMob mob) {
      Ent ent = (Ent)ESEntities.ENT.get().create(level);
      if (ent != null && mob instanceof Ent partner) {
         if (this.random.nextBoolean()) {
            ent.setVariant(this.getVariant());
         } else {
            ent.setVariant(partner.getVariant());
         }
      }

      return ent;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource source) {
      return ESSoundEvents.ENT_HURT.get();
   }

   public static boolean checkEntSpawnRules(EntityType<? extends Ent> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random) {
      return level.getBlockState(pos.below()).is(BlockTags.ANIMALS_SPAWNABLE_ON) && ESConfig.INSTANCE.mobsConfig.ent.canSpawn();
   }
}
