package com.bobmowzie.mowziesmobs.server.entity.effects.geomancy;

import com.bobmowzie.mowziesmobs.server.block.ICopiedBlockProperties;
import com.bobmowzie.mowziesmobs.server.entity.EntityHandler;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityMagicEffect;
import com.bobmowzie.mowziesmobs.server.potion.EffectGeomancy;
import com.bobmowzie.mowziesmobs.server.sound.MMSounds;
import com.bobmowzie.mowziesmobs.server.tag.TagHandler;
import java.util.Optional;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.util.GeckoLibUtil;

public abstract class EntityGeomancyBase extends EntityMagicEffect implements GeoEntity {
   private static final byte EXPLOSION_PARTICLES_ID = 69;
   protected static final EntityDataAccessor<BlockState> BLOCK_STATE = SynchedEntityData.defineId(EntityGeomancyBase.class, EntityDataSerializers.BLOCK_STATE);
   private static final EntityDataAccessor<Integer> TIER = SynchedEntityData.defineId(EntityGeomancyBase.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Integer> DEATH_TIME = SynchedEntityData.defineId(EntityGeomancyBase.class, EntityDataSerializers.INT);
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   private boolean doRemoveTimer = true;

   public EntityGeomancyBase(EntityType<? extends EntityMagicEffect> type, Level worldIn) {
      super(type, worldIn);
   }

   public EntityGeomancyBase(EntityType<? extends EntityMagicEffect> type, Level worldIn, LivingEntity caster, BlockState blockState, BlockPos pos) {
      super(type, worldIn, caster);
      if (!worldIn.isClientSide && blockState != null && EffectGeomancy.isBlockUseable(blockState)) {
         BlockState newBlock = this.changeBlock(blockState);
         this.setBlock(newBlock);
      }
   }

   public BlockState changeBlock(BlockState blockState) {
      if (!blockState.is(TagHandler.GEOMANCY_USEABLE)) {
         ICopiedBlockProperties properties = (ICopiedBlockProperties)blockState.getBlock().properties();
         Block baseBlock = properties.mowziesMobs$getBaseBlock();
         if (baseBlock != null) {
            blockState = baseBlock.defaultBlockState();
         }
      }

      ResourceKey<Block> blockKey = blockState.getBlock().builtInRegistryHolder().getKey();
      if (blockKey != null) {
         String blockString = blockKey.location().toString();
         if (blockState.getBlock() instanceof SlabBlock && blockString.endsWith("_slab")) {
            String baseBlockString = blockString.substring(0, blockString.lastIndexOf(95));
            if (baseBlockString.endsWith("brick")) {
               baseBlockString = baseBlockString + "s";
            }

            Registry<Block> blockRegistry = this.level().registryAccess().registryOrThrow(Registries.BLOCK);
            Optional<Block> optional = blockRegistry.getOptional(ResourceLocation.tryParse(baseBlockString));
            if (optional.isPresent()) {
               blockState = optional.get().defaultBlockState();
            }
         }
      }

      if (blockState.getBlock() == Blocks.GRASS_BLOCK
         || blockState.getBlock() == Blocks.MYCELIUM
         || blockState.getBlock() == Blocks.PODZOL
         || blockState.getBlock() == Blocks.DIRT_PATH) {
         blockState = Blocks.DIRT.defaultBlockState();
      } else if (blockState.is(net.neoforged.neoforge.common.Tags.Blocks.ORES_IN_GROUND_DEEPSLATE)) {
         blockState = Blocks.DEEPSLATE.defaultBlockState();
      } else if (blockState.is(BlockTags.NYLIUM)) {
         blockState = Blocks.NETHERRACK.defaultBlockState();
      } else if (blockState.is(net.neoforged.neoforge.common.Tags.Blocks.ORES_IN_GROUND_NETHERRACK)) {
         blockState = Blocks.NETHERRACK.defaultBlockState();
      } else if (blockState.is(net.neoforged.neoforge.common.Tags.Blocks.ORES_IN_GROUND_STONE)) {
         blockState = Blocks.STONE.defaultBlockState();
      } else if (blockState.is(net.neoforged.neoforge.common.Tags.Blocks.SANDS_RED)) {
         blockState = Blocks.RED_SANDSTONE.defaultBlockState();
      } else if (blockState.is(net.neoforged.neoforge.common.Tags.Blocks.SANDS_COLORLESS)) {
         blockState = Blocks.SANDSTONE.defaultBlockState();
      } else if (blockState.getBlock() == Blocks.SOUL_SAND) {
         blockState = Blocks.SOUL_SOIL.defaultBlockState();
      }

      return blockState;
   }

   @Override
   public void tick() {
      super.tick();
      if (this.doRemoveTimer()) {
         int newDeathTime = this.getDeathTime() - 1;
         this.setDeathTime(newDeathTime);
         if (newDeathTime < 0) {
            this.explode();
         }
      }
   }

   @Override
   protected void defineSynchedData(@NotNull Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BLOCK_STATE, Blocks.DIRT.defaultBlockState());
      builder.define(DEATH_TIME, 1200);
      builder.define(TIER, 0);
   }

   public boolean canBeCollidedWith() {
      return true;
   }

   public boolean displayFireAnimation() {
      return false;
   }

   @Override
   public PushReaction getPistonPushReaction() {
      return PushReaction.IGNORE;
   }

   public boolean isSilent() {
      return false;
   }

   public void playSound(SoundEvent soundIn, float volume, float pitch) {
      super.playSound(soundIn, volume, pitch + this.random.nextFloat() * 0.25F - 0.125F);
   }

   public boolean ignoreExplosion(Explosion explosion) {
      return true;
   }

   protected void explode() {
      this.level().broadcastEntityEvent(this, (byte)69);
      EntityGeomancyBase.GeomancyTier tier = this.getTier();
      if (tier == EntityGeomancyBase.GeomancyTier.NONE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.9F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1.5F, 1.0F);
      }

      if (tier == EntityGeomancyBase.GeomancyTier.SMALL) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.9F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK.get(), 1.5F, 1.0F);
      } else if (tier == EntityGeomancyBase.GeomancyTier.MEDIUM) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_SMALL.get(), 1.5F, 0.7F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM_3.get(), 1.5F, 1.5F);
      } else if (tier == EntityGeomancyBase.GeomancyTier.LARGE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 1.0F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_MEDIUM_1.get(), 1.5F, 0.9F);
         EntityCameraShake.cameraShake(this.level(), this.position(), 15.0F, 0.05F, 0, 20);

         for (int i = 0; i < 5.0F * this.fallingBlockCountMultiplier(); i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 2.0F, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.add(new Vec3(0.0, this.getBbHeight() / 4.0F, 0.0));
            EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.level(), 70, this.getBlock());
            fallingBlock.setPos(this.getX() + particlePos.x, this.getY() + 0.5 + particlePos.y, this.getZ() + particlePos.z);
            fallingBlock.setDeltaMovement((float)particlePos.x * 0.3F, 0.2F + this.random.nextFloat() * 0.6F, (float)particlePos.z * 0.3F);
            this.level().addFreshEntity(fallingBlock);
         }
      } else if (tier == EntityGeomancyBase.GeomancyTier.HUGE) {
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_MAGIC_BIG.get(), 1.5F, 0.5F);
         this.playSound((SoundEvent)MMSounds.EFFECT_GEOMANCY_BREAK_LARGE_1.get(), 1.5F, 0.5F);
         EntityCameraShake.cameraShake(this.level(), this.position(), 20.0F, 0.05F, 0, 20);

         for (int i = 0; i < 7.0F * this.fallingBlockCountMultiplier(); i++) {
            Vec3 particlePos = new Vec3(this.random.nextFloat() * 2.5F, 0.0, 0.0);
            particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
            particlePos = particlePos.add(new Vec3(0.0, this.getBbHeight() / 4.0F, 0.0));
            EntityFallingBlock fallingBlock = new EntityFallingBlock((EntityType<?>)EntityHandler.FALLING_BLOCK.get(), this.level(), 70, this.getBlock());
            fallingBlock.setPos(this.getX() + particlePos.x, this.getY() + 0.5 + particlePos.y, this.getZ() + particlePos.z);
            fallingBlock.setDeltaMovement((float)particlePos.x * 0.3F, 0.2F + this.random.nextFloat() * 0.6F, (float)particlePos.z * 0.3F);
            this.level().addFreshEntity(fallingBlock);
         }
      }

      this.discard();
   }

   protected float fallingBlockCountMultiplier() {
      return 1.0F;
   }

   protected void spawnExplosionParticles() {
      for (int i = 0; i < 40.0F * this.getBbWidth(); i++) {
         Vec3 particlePos = new Vec3(this.random.nextFloat() * 0.7 * this.getBbWidth(), 0.0, 0.0);
         particlePos = particlePos.yRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
         particlePos = particlePos.xRot((float)(this.random.nextFloat() * 2.0F * Math.PI));
         particlePos = particlePos.add(0.0, this.getBbHeight() / 2.0, 0.0);
         Camera camera = Minecraft.getInstance().gameRenderer.getMainCamera();
         boolean overrideLimiter = camera.getPosition().distanceToSqr(this.getX(), this.getY(), this.getZ()) < 4096.0;
         this.level()
            .addAlwaysVisibleParticle(
               new BlockParticleOption(ParticleTypes.BLOCK, this.getBlock()),
               overrideLimiter,
               this.getX() + particlePos.x,
               this.getY() + 0.5 + particlePos.y,
               this.getZ() + particlePos.z,
               particlePos.x,
               particlePos.y,
               particlePos.z
            );
      }
   }

   public void handleEntityEvent(byte id) {
      if (id == 69) {
         this.spawnExplosionParticles();
      } else {
         super.handleEntityEvent(id);
      }
   }

   public BlockState getBlock() {
      return (BlockState)this.getEntityData().get(BLOCK_STATE);
   }

   public void setBlock(BlockState block) {
      this.getEntityData().set(BLOCK_STATE, block);
   }

   public EntityGeomancyBase.GeomancyTier getTier() {
      return EntityGeomancyBase.GeomancyTier.values()[this.entityData.get(TIER)];
   }

   public void setTier(EntityGeomancyBase.GeomancyTier size) {
      this.entityData.set(TIER, size.ordinal());
   }

   public int getDeathTime() {
      return (Integer)this.entityData.get(DEATH_TIME);
   }

   public void setDeathTime(int deathTime) {
      this.entityData.set(DEATH_TIME, deathTime);
   }

   @Override
   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      BlockState blockState = this.getBlock();
      if (blockState != null) {
         compound.put("block", NbtUtils.writeBlockState(blockState));
      }

      if (this.doRemoveTimer()) {
         compound.putInt("deathTime", this.getDeathTime());
      }

      compound.putInt("tier", this.getTier().ordinal());
   }

   @Override
   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      Tag blockStateCompound = compound.get("block");
      if (blockStateCompound != null) {
         BlockState blockState = NbtUtils.readBlockState(this.level().holderLookup(Registries.BLOCK), (CompoundTag)blockStateCompound);
         this.setBlock(blockState);
      }

      if (compound.contains("deathTime")) {
         this.doRemoveTimer = true;
         this.setDeathTime(compound.getInt("deathTime"));
      } else {
         this.doRemoveTimer = false;
      }

      this.setTier(EntityGeomancyBase.GeomancyTier.values()[compound.getInt("tier")]);
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public boolean doRemoveTimer() {
      return this.doRemoveTimer;
   }

   public void setDoRemoveTimer(boolean doRemoveTimer) {
      this.doRemoveTimer = doRemoveTimer;
   }

   public enum GeomancyTier {
      NONE(0),
      SMALL(1),
      MEDIUM(2),
      LARGE(3),
      HUGE(4);

      public final int index;

      GeomancyTier(int i) {
         this.index = i;
      }
   }
}
