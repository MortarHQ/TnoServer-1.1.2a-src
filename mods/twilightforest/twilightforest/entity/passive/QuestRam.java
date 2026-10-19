package twilightforest.entity.passive;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Optional;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import twilightforest.advancements.SimpleAdvancementTrigger;
import twilightforest.beanification.Autowired;
import twilightforest.entity.EnforcedHomePoint;
import twilightforest.entity.ai.goal.QuestRamEatWoolGoal;
import twilightforest.entity.passive.quest.ram.QuestingRamCurrentContext;
import twilightforest.init.TFAdvancements;
import twilightforest.init.TFSounds;
import twilightforest.init.TFStructures;
import twilightforest.network.ParticlePacket;
import twilightforest.util.landmarks.LandmarkUtil;

public class QuestRam extends Animal implements EnforcedHomePoint {
   @Autowired
   private static QuestingRamCurrentContext questingRamCurrentContext;
   private static final EntityDataAccessor<Integer> DATA_COLOR = SynchedEntityData.defineId(QuestRam.class, EntityDataSerializers.INT);
   private static final EntityDataAccessor<Boolean> DATA_REWARDED = SynchedEntityData.defineId(QuestRam.class, EntityDataSerializers.BOOLEAN);
   private static final EntityDataAccessor<Optional<GlobalPos>> HOME_POINT = SynchedEntityData.defineId(
      QuestRam.class, EntityDataSerializers.OPTIONAL_GLOBAL_POS
   );
   private int randomTickDivider = 0;

   public QuestRam(EntityType<? extends QuestRam> type, Level level) {
      super(type, level);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.38F));
      this.goalSelector.addGoal(2, new QuestRamEatWoolGoal(this));
      this.goalSelector.addGoal(3, new TemptGoal(this, 1.0, this::isItemTempting, false));
      this.addRestrictionGoals(this, this.goalSelector);
      this.goalSelector.addGoal(5, new WaterAvoidingRandomStrollGoal(this, 1.0));
      this.goalSelector.addGoal(6, new RandomLookAroundGoal(this));
   }

   public boolean isItemTempting(ItemStack stack) {
      for (Entry<DyeColor, Ingredient> questEntry : questingRamCurrentContext.getContext().questItems().entrySet()) {
         if (questEntry.getValue().test(stack)) {
            DyeColor color = questEntry.getKey();
            return color != null && !this.isColorPresent(color);
         }
      }

      return false;
   }

   public boolean isFood(ItemStack stack) {
      return false;
   }

   @Nullable
   public Animal getBreedOffspring(ServerLevel level, AgeableMob mate) {
      return null;
   }

   public static Builder registerAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 70.0).add(Attributes.MOVEMENT_SPEED, 0.23);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(DATA_COLOR, 0);
      builder.define(DATA_REWARDED, false);
      builder.define(HOME_POINT, Optional.empty());
   }

   protected void customServerAiStep() {
      if (--this.randomTickDivider <= 0) {
         this.randomTickDivider = 70 + this.getRandom().nextInt(50);
         if (this.countColorsSet() > 15 && !this.getRewarded()) {
            this.rewardQuest();
            this.setRewarded(true);
         }
      }

      if (this.countColorsSet() > 15 && !this.getRewarded()) {
         this.animateAddColor(DyeColor.byId(this.getRandom().nextInt(16)), 5);
         this.playAmbientSound();
      }

      super.customServerAiStep();
   }

   private void rewardQuest() {
      LootParams ctx = new net.minecraft.world.level.storage.loot.LootParams.Builder((ServerLevel)this.level())
         .withParameter(LootContextParams.THIS_ENTITY, this)
         .create(LootContextParamSets.PIGLIN_BARTER);
      ObjectArrayList<ItemStack> rewards = this.level()
         .getServer()
         .reloadableRegistries()
         .getLootTable(questingRamCurrentContext.getContext().lootTable())
         .getRandomItems(ctx);
      rewards.forEach(stack -> this.spawnAtLocation(stack, 1.0F));

      for (ServerPlayer player : this.level().getEntitiesOfClass(ServerPlayer.class, this.getBoundingBox().inflate(16.0, 16.0, 16.0))) {
         ((SimpleAdvancementTrigger)TFAdvancements.QUEST_RAM_COMPLETED.get()).trigger(player);
      }

      LandmarkUtil.markStructureConquered(this.level(), this, TFStructures.QUEST_GROVE, true);
   }

   public InteractionResult interactAt(Player player, Vec3 vec, InteractionHand hand) {
      ItemStack currentItem = player.getItemInHand(hand);
      if (!this.level().isClientSide() && this.tryAccept(currentItem)) {
         currentItem.consume(1, player);
         return InteractionResult.SUCCESS;
      } else {
         return super.interactAt(player, vec, hand);
      }
   }

   public AABB getBoundingBoxForCulling() {
      return super.getBoundingBoxForCulling().inflate(3.0);
   }

   public boolean tryAccept(ItemStack stack) {
      for (Entry<DyeColor, Ingredient> questEntry : questingRamCurrentContext.getContext().questItems().entrySet()) {
         if (questEntry.getValue().test(stack)) {
            DyeColor color = questEntry.getKey();
            if (color != null && !this.isColorPresent(color)) {
               this.setColorPresent(color);
               this.animateAddColor(color, 50);
               this.playAmbientSound();
               return true;
            }
         }
      }

      return false;
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("ColorFlags", this.getColorFlags());
      compound.putBoolean("Rewarded", this.getRewarded());
      this.saveHomePointToNbt(compound);
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.setColorFlags(compound.getInt("ColorFlags"));
      this.setRewarded(compound.getBoolean("Rewarded"));
      this.loadHomePointFromNbt(compound);
   }

   public int getColorFlags() {
      return (Integer)this.getEntityData().get(DATA_COLOR);
   }

   private void setColorFlags(int flags) {
      this.getEntityData().set(DATA_COLOR, flags);
   }

   public boolean isColorPresent(DyeColor color) {
      return (this.getColorFlags() & 1 << color.getId()) > 0;
   }

   public void setColorPresent(DyeColor color) {
      this.setColorFlags(this.getColorFlags() | 1 << color.getId());
   }

   public boolean getRewarded() {
      return (Boolean)this.getEntityData().get(DATA_REWARDED);
   }

   public void setRewarded(boolean rewarded) {
      this.getEntityData().set(DATA_REWARDED, rewarded);
   }

   private void animateAddColor(DyeColor color, int iterations) {
      int colorVal = color.getTextureDiffuseColor();
      if (!this.level().isClientSide()) {
         for (ServerPlayer serverplayer : ((ServerLevel)this.level()).players()) {
            if (serverplayer.distanceToSqr(Vec3.atCenterOf(this.blockPosition())) < 4096.0) {
               ParticlePacket packet = new ParticlePacket();

               for (int i = 0; i < iterations; i++) {
                  packet.queueParticle(
                     ColorParticleOption.create(ParticleTypes.ENTITY_EFFECT, ARGB32.red(colorVal), ARGB32.green(colorVal), ARGB32.blue(colorVal)),
                     false,
                     this.getX() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 1.5,
                     this.getY() + this.getRandom().nextDouble() * this.getBbHeight() * 1.5,
                     this.getZ() + (this.getRandom().nextDouble() - 0.5) * this.getBbWidth() * 1.5,
                     0.0,
                     0.0,
                     0.0
                  );
               }

               PacketDistributor.sendToPlayer(serverplayer, packet, new CustomPacketPayload[0]);
            }
         }
      }
   }

   public int countColorsSet() {
      return Integer.bitCount(this.getColorFlags());
   }

   protected AABB makeBoundingBox() {
      return super.makeBoundingBox();
   }

   protected boolean canRide(Entity entityIn) {
      return false;
   }

   public float getVoicePitch() {
      return (this.getRandom().nextFloat() - this.getRandom().nextFloat()) * 0.2F + 0.7F;
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.QUEST_RAM_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.QUEST_RAM_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.QUEST_RAM_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound((SoundEvent)TFSounds.QUEST_RAM_STEP.get(), 0.15F, 1.0F);
   }

   @Nullable
   @Override
   public GlobalPos getRestrictionPoint() {
      return (GlobalPos)((Optional)this.getEntityData().get(HOME_POINT)).orElse(null);
   }

   @Override
   public void setRestrictionPoint(@Nullable GlobalPos pos) {
      this.getEntityData().set(HOME_POINT, Optional.ofNullable(pos));
   }

   @Override
   public int getHomeRadius() {
      return 13;
   }
}
