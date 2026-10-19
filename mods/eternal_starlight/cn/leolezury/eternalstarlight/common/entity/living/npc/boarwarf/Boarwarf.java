package cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.config.ESConfig;
import cn.leolezury.eternalstarlight.common.data.ESRegistries;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfAvoidGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfChatGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfDoSleepGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfLookAtCustomerGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfMoveToBedGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfRandomStrollNearHomeGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfTradeWithPlayerGoal;
import cn.leolezury.eternalstarlight.common.entity.living.goal.BoarwarfWakeUpGoal;
import cn.leolezury.eternalstarlight.common.entity.living.npc.boarwarf.golem.AstralGolem;
import cn.leolezury.eternalstarlight.common.registry.ESBoarwarfProfessions;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESSoundEvents;
import com.mojang.serialization.DataResult;
import java.util.Set;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.npc.VillagerTrades.ItemListing;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import org.apache.commons.compress.utils.Sets;
import org.jetbrains.annotations.Nullable;

public class Boarwarf extends PathfinderMob implements Npc, Merchant {
   private static final String TAG_OFFERS = "offers";
   private static final String TAG_TYPE = "type";
   private static final String TAG_PROFESSION = "profession";
   private static final String TAG_RESTOCK_COOLDOWN = "restock_cooldown";
   private static final String TAG_CHAT_COOLDOWN = "chat_cooldown";
   private static final String TAG_CHAT_TICKS = "chat_ticks";
   private static final String TAG_AWAKE_TICKS = "awake_ticks";
   private static final String TAG_SLEEP_TICKS = "sleep_ticks";
   private static final String TAG_HOME_POS = "home_pos";
   @Nullable
   private Player customer;
   @Nullable
   protected MerchantOffers offers;
   private int restockCooldown = 0;
   private int chatCooldown = 0;
   public int chatTicks = 0;
   private int awakeTicks = 0;
   private int sleepTicks = 0;
   public GlobalPos homePos = GlobalPos.of(Level.OVERWORLD, BlockPos.ZERO);
   public Boarwarf chatTarget = null;
   public final AnimationState idleAnimationState = new AnimationState();
   protected static final EntityDataAccessor<String> TYPE = SynchedEntityData.defineId(Boarwarf.class, EntityDataSerializers.STRING);
   protected static final EntityDataAccessor<String> PROFESSION = SynchedEntityData.defineId(Boarwarf.class, EntityDataSerializers.STRING);

   public Boarwarf(EntityType<? extends Boarwarf> type, Level level) {
      super(type, level);
   }

   public ResourceLocation getTypeId() {
      return ResourceLocation.parse((String)this.getEntityData().get(TYPE));
   }

   public BoarwarfType getBoarwarfType() {
      return (BoarwarfType)this.level().registryAccess().registryOrThrow(ESRegistries.BOARWARF_TYPE).get(this.getTypeId());
   }

   public void setTypeId(ResourceLocation typeId) {
      this.getEntityData().set(TYPE, typeId.toString());
   }

   public void setBoarwarfType(BoarwarfType type) {
      ResourceLocation key = this.level().registryAccess().registryOrThrow(ESRegistries.BOARWARF_TYPE).getKey(type);
      if (key != null) {
         this.setTypeId(key);
      }
   }

   public ResourceLocation getProfessionId() {
      return ResourceLocation.parse((String)this.getEntityData().get(PROFESSION));
   }

   public AbstractBoarwarfProfession getProfession() {
      return (AbstractBoarwarfProfession)ESBoarwarfProfessions.PROFESSIONS.registry().get(this.getProfessionId());
   }

   public void setProfessionId(ResourceLocation professionId) {
      this.getEntityData().set(PROFESSION, professionId.toString());
   }

   public void setProfession(AbstractBoarwarfProfession profession) {
      ResourceLocation key = ESBoarwarfProfessions.PROFESSIONS.registry().getKey(profession);
      if (key != null) {
         this.setProfessionId(key);
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(TYPE, "null").define(PROFESSION, "null");
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("offers")) {
         DataResult<MerchantOffers> parsed = MerchantOffers.CODEC
            .parse(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), compoundTag.get("offers"));
         parsed.resultOrPartial(Util.prefix("Failed to load offers: ", EternalStarlight.LOGGER::warn))
            .ifPresent(merchantOffers -> this.offers = merchantOffers);
      }

      this.setTypeId((ResourceLocation)ResourceLocation.read(compoundTag.getString("type")).getOrThrow());
      this.setProfessionId((ResourceLocation)ResourceLocation.read(compoundTag.getString("profession")).getOrThrow());
      this.restockCooldown = compoundTag.getInt("restock_cooldown");
      this.chatCooldown = compoundTag.getInt("chat_cooldown");
      this.chatTicks = compoundTag.getInt("chat_ticks");
      this.awakeTicks = compoundTag.getInt("awake_ticks");
      this.sleepTicks = compoundTag.getInt("sleep_ticks");
      if (compoundTag.contains("home_pos")) {
         GlobalPos.CODEC
            .parse(NbtOps.INSTANCE, compoundTag.get("home_pos"))
            .resultOrPartial(s -> EternalStarlight.LOGGER.warn("Failed to parse Boarwarf home pos: {}", s))
            .ifPresent(pos -> this.homePos = pos);
      }

      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.addTrades();
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      if (!this.level().isClientSide) {
         MerchantOffers merchantOffers = this.getOffers();
         if (!merchantOffers.isEmpty()) {
            compoundTag.put(
               "offers", (Tag)MerchantOffers.CODEC.encodeStart(this.registryAccess().createSerializationContext(NbtOps.INSTANCE), merchantOffers).getOrThrow()
            );
         }
      }

      compoundTag.putString("type", this.getTypeId().toString());
      compoundTag.putString("profession", this.getProfessionId().toString());
      compoundTag.putInt("restock_cooldown", this.restockCooldown);
      compoundTag.putInt("chat_cooldown", this.chatCooldown);
      compoundTag.putInt("chat_ticks", this.chatTicks);
      compoundTag.putInt("awake_ticks", this.awakeTicks);
      compoundTag.putInt("sleep_ticks", this.sleepTicks);
      compoundTag.put("home_pos", (Tag)GlobalPos.CODEC.encodeStart(NbtOps.INSTANCE, this.homePos).getOrThrow());
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new BoarwarfDoSleepGoal(this));
      this.goalSelector.addGoal(0, new BoarwarfWakeUpGoal(this));
      this.goalSelector.addGoal(1, new FloatGoal(this));
      this.goalSelector.addGoal(2, new BoarwarfAvoidGoal(this));
      this.goalSelector.addGoal(3, new BoarwarfTradeWithPlayerGoal(this));
      this.goalSelector.addGoal(3, new BoarwarfLookAtCustomerGoal(this));
      this.goalSelector.addGoal(4, new BoarwarfMoveToBedGoal(this, 0.5, 10));
      this.goalSelector.addGoal(5, new BoarwarfChatGoal(this, 0.3, true));
      this.goalSelector.addGoal(6, new BoarwarfRandomStrollNearHomeGoal(this));
      this.goalSelector.addGoal(7, new LookAtPlayerGoal(this, LivingEntity.class, 32.0F));
      this.goalSelector.addGoal(8, new RandomLookAroundGoal(this));
   }

   public static net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder createAttributes() {
      return Mob.createMobAttributes()
         .add(Attributes.MAX_HEALTH, ESConfig.INSTANCE.mobsConfig.boarwarf.maxHealth())
         .add(Attributes.ARMOR, ESConfig.INSTANCE.mobsConfig.boarwarf.armor())
         .add(Attributes.MOVEMENT_SPEED, 0.5);
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance instance, MobSpawnType spawnType, @Nullable SpawnGroupData data) {
      this.homePos = GlobalPos.of(level.getLevel().dimension(), this.blockPosition());
      this.level().registryAccess().registryOrThrow(ESRegistries.BOARWARF_TYPE).forEach(type -> {
         if (type.biome().value() == level.getBiome(this.blockPosition()).value()) {
            this.setBoarwarfType(type);
         }
      });
      return super.finalizeSpawn(level, instance, spawnType, data);
   }

   protected SoundEvent getAmbientSound() {
      if (this.isSleeping()) {
         return null;
      } else {
         return this.hasCustomer() ? ESSoundEvents.BOARWARF_TRADE.get() : ESSoundEvents.BOARWARF_AMBIENT.get();
      }
   }

   protected SoundEvent getHurtSound(DamageSource damageSource) {
      return ESSoundEvents.BOARWARF_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return ESSoundEvents.BOARWARF_DEATH.get();
   }

   protected SoundEvent getYesOrNoSound(boolean yesSound) {
      return yesSound ? ESSoundEvents.BOARWARF_YES.get() : ESSoundEvents.BOARWARF_NO.get();
   }

   public InteractionResult mobInteract(Player player, InteractionHand playerHand) {
      if (this.isAlive() && !this.hasCustomer()) {
         if (!this.getOffers().isEmpty() && !this.level().isClientSide && getBoarwarfCredit(player) >= -30) {
            int credit = getBoarwarfCredit(player);
            if (credit > 0) {
               credit /= 10;
            }

            for (MerchantOffer merchantoffer : this.getOffers()) {
               merchantoffer.setSpecialPriceDiff(-Mth.floor(credit / 2.0F));
            }

            this.setTradingPlayer(player);
            this.openTradingScreen(player, this.getDisplayName(), 1);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide);
      } else {
         return super.mobInteract(player, playerHand);
      }
   }

   public boolean wantsToSleep() {
      return this.awakeTicks > 12000;
   }

   public boolean wantsToWake() {
      return this.sleepTicks > 12000;
   }

   public void angerNearbyAstralGolems(LivingEntity target, boolean replaceCurrentTarget) {
      for (AstralGolem golem : this.level().getEntitiesOfClass(AstralGolem.class, this.getBoundingBox().inflate(30.0))) {
         if ((replaceCurrentTarget || golem.getTarget() == null) && !(target instanceof Player player && player.hasInfiniteMaterials())) {
            golem.setTarget(target);
         }
      }
   }

   public LivingEntity getEntityToAvoid(float maxDist) {
      LivingEntity toAvoid = null;
      float currentDist = maxDist;

      for (LivingEntity entity : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(maxDist))) {
         if (entity instanceof Targeting targeting && targeting.getTarget() != null && targeting.getTarget() == this) {
            this.angerNearbyAstralGolems(entity, false);
            if (currentDist > this.distanceTo(entity)) {
               toAvoid = entity;
               currentDist = this.distanceTo(entity);
            }
         }

         if (entity instanceof Player player && getBoarwarfCredit(player) <= -30) {
            this.angerNearbyAstralGolems(player, false);
            if (currentDist > this.distanceTo(entity)) {
               toAvoid = entity;
               currentDist = this.distanceTo(entity);
            }
         }
      }

      return toAvoid;
   }

   public static int getBoarwarfCredit(Player player) {
      return ESDataAttachments.BOARWARF_CREDIT.getData(player);
   }

   public static void setBoarwarfCredit(Player player, int credit) {
      ESDataAttachments.BOARWARF_CREDIT.setData(player, credit);
   }

   public boolean hurt(DamageSource source, float amount) {
      if (this.chatCooldown < 12000) {
         this.chatCooldown += 12000;
      }

      if (this.chatTicks > 0) {
         this.chatTicks = 0;
      }

      if (source.getEntity() instanceof LivingEntity livingEntity) {
         this.angerNearbyAstralGolems(livingEntity, true);
      }

      if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials()) {
         int credit = (int)(getBoarwarfCredit(player) - amount);
         if (credit > -10000) {
            setBoarwarfCredit(player, credit);
         }
      }

      return super.hurt(source, amount);
   }

   public void die(DamageSource source) {
      if (source.getEntity() instanceof Player player && !player.hasInfiniteMaterials()) {
         int credit = getBoarwarfCredit(player) - 20;
         if (credit > -10000) {
            setBoarwarfCredit(player, credit);
         }
      }

      super.die(source);
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide) {
         if (this.level().getGameTime() % 40L == 0L) {
            this.heal(1.0F);
         }

         if (this.isSleeping()) {
            this.sleepTicks++;
            this.awakeTicks = 0;
         } else {
            this.awakeTicks++;
            this.sleepTicks = 0;
         }

         if (this.restockCooldown > 0) {
            this.restockCooldown--;
         } else {
            this.restockCooldown = 12000;
            this.restockAll();
         }

         if (this.chatCooldown > 0) {
            this.chatCooldown--;
         } else {
            this.chatCooldown = 10000;
            this.chatTicks = 4000;
         }

         if (this.chatTicks > 0) {
            this.chatTicks--;
            if (this.chatTarget == null) {
               for (Boarwarf boarwarf : this.level().getNearbyEntities(Boarwarf.class, TargetingConditions.DEFAULT, this, this.getBoundingBox().inflate(15.0))) {
                  if (boarwarf.chatTarget == null) {
                     boarwarf.chatTarget = this;
                     this.chatTarget = boarwarf;
                  }
               }
            } else if (this.chatTarget.chatTicks <= 0 || this.chatTarget.chatTarget != this) {
               this.chatTarget = null;
            }
         }
      } else {
         this.idleAnimationState.startIfStopped(this.tickCount);
      }
   }

   public void setTradingPlayer(@Nullable Player player) {
      this.customer = player;
   }

   @Nullable
   public Player getTradingPlayer() {
      return this.customer;
   }

   public boolean hasCustomer() {
      return this.customer != null;
   }

   public MerchantOffers getOffers() {
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.addTrades();
      }

      return this.offers;
   }

   protected void addTrades() {
      if (this.getProfession() != null) {
         MerchantOffers merchantoffers = this.getOffers();
         this.addTrades(merchantoffers, this.getProfession().getTrades(this), 5);
      }
   }

   protected void addTrades(MerchantOffers original, ItemListing[] newTrades, int maxNumbers) {
      Set<Integer> set = Sets.newHashSet(new Integer[0]);
      if (newTrades.length > maxNumbers) {
         while (set.size() < maxNumbers) {
            set.add(this.random.nextInt(newTrades.length));
         }
      } else {
         for (int i = 0; i < newTrades.length; i++) {
            set.add(i);
         }
      }

      for (Integer integer : set) {
         ItemListing villagertrades$itrade = newTrades[integer];
         MerchantOffer merchantoffer = villagertrades$itrade.getOffer(this, this.random);
         if (merchantoffer != null) {
            original.add(merchantoffer);
         }
      }
   }

   protected void restockAll() {
      for (MerchantOffer merchantoffer : this.getOffers()) {
         merchantoffer.resetUses();
      }
   }

   public void overrideOffers(@Nullable MerchantOffers offers) {
   }

   public void notifyTrade(MerchantOffer offer) {
      offer.increaseUses();
      this.ambientSoundTime = -this.getAmbientSoundInterval();
      if (offer.shouldRewardExp()) {
         int i = 3 + this.random.nextInt(4);
         this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + 0.5, this.getZ(), i));
      }
   }

   public void notifyTradeUpdated(ItemStack stack) {
      if (!this.level().isClientSide && this.ambientSoundTime > -this.getAmbientSoundInterval() + 20) {
         this.ambientSoundTime = -this.getAmbientSoundInterval();
         this.playSound(this.getYesOrNoSound(!stack.isEmpty()), this.getSoundVolume(), this.getVoicePitch());
      }
   }

   public int getVillagerXp() {
      return 0;
   }

   public void overrideXp(int xp) {
   }

   public boolean showProgressBar() {
      return false;
   }

   public SoundEvent getNotifyTradeSound() {
      return ESSoundEvents.BOARWARF_YES.get();
   }

   public boolean isClientSide() {
      return this.level().isClientSide;
   }

   public static boolean checkBoarwarfSpawnRules(
      EntityType<? extends Boarwarf> type, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource random
   ) {
      return checkMobSpawnRules(type, level, spawnType, pos, random) && ESConfig.INSTANCE.mobsConfig.boarwarf.canSpawn();
   }
}
