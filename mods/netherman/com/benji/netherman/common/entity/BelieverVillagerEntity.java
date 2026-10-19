package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModBlocks;
import com.benji.netherman.init.ModEntities;
import com.benji.netherman.init.ModItems;
import java.util.OptionalInt;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.RegistryOps;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.npc.Npc;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MerchantMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.Merchant;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BelieverVillagerEntity extends PathfinderMob implements GeoEntity, Merchant, Npc {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   @Nullable
   private Player tradingPlayer;
   @Nullable
   private MerchantOffers offers;
   private int villagerLevel = 1;
   private int villagerXp = 0;
   private int soundCooldown = 0;
   private static final RawAnimation RUN_ANIM = RawAnimation.begin().thenLoop("run");
   private static final RawAnimation WALK_ANIM = RawAnimation.begin().thenLoop("walk");
   private static final RawAnimation IDLE_ANIM = RawAnimation.begin().thenLoop("idle");

   public BelieverVillagerEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 20.0).add(Attributes.MOVEMENT_SPEED, 0.25);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
   }

   protected void registerGoals() {
      this.goalSelector.addGoal(0, new FloatGoal(this));
      this.goalSelector.addGoal(1, new PanicGoal(this, 1.5) {
         public boolean canUse() {
            return BelieverVillagerEntity.this.tradingPlayer == null && super.canUse();
         }
      });
      this.goalSelector.addGoal(2, new WaterAvoidingRandomStrollGoal(this, 1.0) {
         public boolean canUse() {
            return BelieverVillagerEntity.this.tradingPlayer == null && super.canUse();
         }
      });
      this.goalSelector.addGoal(3, new LookAtPlayerGoal(this, Player.class, 6.0F));
      this.goalSelector.addGoal(4, new RandomLookAroundGoal(this));
   }

   public void tick() {
      super.tick();
      if (this.soundCooldown > 0) {
         this.soundCooldown--;
      }
   }

   public void travel(Vec3 travelVector) {
      super.travel(travelVector);
   }

   public boolean removeWhenFarAway(double distanceToClosestPlayer) {
      return false;
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (hand == InteractionHand.MAIN_HAND && this.isAlive() && this.getTradingPlayer() == null) {
         if (!this.level().isClientSide()) {
            this.setTradingPlayer(player);
            this.openTradingScreen(player, this.getDisplayName(), this.villagerLevel);
         }

         return InteractionResult.sidedSuccess(this.level().isClientSide());
      } else {
         return super.mobInteract(player, hand);
      }
   }

   public void openTradingScreen(Player player, Component displayName, int level) {
      OptionalInt optionalInt = player.openMenu(
         new SimpleMenuProvider((containerId, playerInv, p) -> new MerchantMenu(containerId, playerInv, this), displayName)
      );
      if (optionalInt.isPresent()) {
         MerchantOffers merchantOffers = this.getOffers();
         if (!merchantOffers.isEmpty()) {
            player.sendMerchantOffers(optionalInt.getAsInt(), merchantOffers, level, this.villagerXp, this.showProgressBar(), true);
         }
      }
   }

   public MerchantOffers getOffers() {
      if (this.offers == null) {
         this.offers = new MerchantOffers();
         this.generateLevelTrades(1);
      }

      return this.offers;
   }

   private void generateLevelTrades(int level) {
      if (level == 1) {
         this.offers.add(new MerchantOffer(new ItemCost(Items.COOKED_PORKCHOP, 4), new ItemStack((ItemLike)ModItems.AZAZEL_GUIDE_BOOK.get(), 1), 10, 2, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.COOKED_BEEF, 5), new ItemStack(Items.EMERALD, 1), 10, 2, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.BREAD, 10), new ItemStack((ItemLike)ModItems.AZAZEL_GUIDE_BOOK.get(), 1), 10, 2, 0.05F));
      } else if (level == 2) {
         this.offers.add(new MerchantOffer(new ItemCost(Items.LEATHER, 4), new ItemStack((ItemLike)ModBlocks.CRIMSON_WEB.get(), 2), 10, 10, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.STRING, 2), new ItemStack((ItemLike)ModBlocks.CRIMSON_WEB.get(), 2), 10, 10, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.HONEY_BLOCK, 2), new ItemStack((ItemLike)ModBlocks.CRIMSON_HONEY_BLOCK.get(), 1), 10, 10, 0.05F));
      } else if (level == 3) {
         this.offers.add(new MerchantOffer(new ItemCost(Items.EMERALD, 2), new ItemStack((ItemLike)ModBlocks.ENTRANCE.get(), 3), 10, 15, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.IRON_INGOT, 4), new ItemStack((ItemLike)ModBlocks.ENTRANCE.get(), 3), 10, 15, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.GLASS_PANE, 4), new ItemStack((ItemLike)ModBlocks.MOSAIC_CHURCH.get(), 2), 10, 15, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.GLASS, 2), new ItemStack((ItemLike)ModBlocks.MOSAIC_CHURCH.get(), 2), 10, 15, 0.05F));
      } else if (level == 4) {
         this.offers.add(new MerchantOffer(new ItemCost(Items.COBBLESTONE, 10), new ItemStack((ItemLike)ModBlocks.COBBLED_SAMSONIT.get(), 20), 10, 20, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.STONE, 5), new ItemStack((ItemLike)ModBlocks.COBBLED_SAMSONIT.get(), 20), 10, 20, 0.05F));
      } else if (level == 5) {
         this.offers.add(new MerchantOffer(new ItemCost(Items.EMERALD, 1), new ItemStack((ItemLike)ModBlocks.BLACKSTONE_PLANT.get(), 1), 5, 30, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.EMERALD, 1), new ItemStack((ItemLike)ModBlocks.BLACKSTONE_AXON.get(), 1), 5, 30, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.IRON_INGOT, 2), new ItemStack((ItemLike)ModBlocks.BLACKSTONE_PLANT.get(), 1), 5, 30, 0.05F));
         this.offers.add(new MerchantOffer(new ItemCost(Items.IRON_INGOT, 2), new ItemStack((ItemLike)ModBlocks.BLACKSTONE_AXON.get(), 1), 5, 30, 0.05F));
      }
   }

   public void notifyTrade(MerchantOffer offer) {
      offer.increaseUses();
      int xp = offer.getXp();
      if (xp > 0) {
         this.villagerXp += xp;
         this.checkLevelUp();
      }

      if (!this.level().isClientSide()) {
         this.level().playSound(null, this.blockPosition(), SoundEvents.VILLAGER_YES, SoundSource.NEUTRAL, 1.0F, this.getVoicePitch());
      }
   }

   private void checkLevelUp() {
      int targetXp = this.getExperienceForLevel(this.villagerLevel + 1);
      if (this.villagerLevel < 5 && this.villagerXp >= targetXp) {
         this.villagerLevel++;
         this.generateLevelTrades(this.villagerLevel);
         if (!this.level().isClientSide()) {
            this.level().playSound(null, this.blockPosition(), SoundEvents.PLAYER_LEVELUP, SoundSource.NEUTRAL, 0.5F, 1.0F);
         }
      }
   }

   private int getExperienceForLevel(int level) {
      return switch (level) {
         case 2 -> 10;
         case 3 -> 50;
         case 4 -> 100;
         case 5 -> 200;
         default -> 0;
      };
   }

   public void notifyTradeUpdated(ItemStack stack) {
      if (!this.level().isClientSide() && this.soundCooldown <= 0 && !stack.isEmpty()) {
         this.playSound(this.getNotifyTradeSound(), this.getSoundVolume(), this.getVoicePitch());
         this.soundCooldown = 20;
      }
   }

   public void setTradingPlayer(@Nullable Player player) {
      this.tradingPlayer = player;
   }

   @Nullable
   public Player getTradingPlayer() {
      return this.tradingPlayer;
   }

   public void overrideOffers(@Nullable MerchantOffers offers) {
      this.offers = offers;
   }

   public int getVillagerXp() {
      return this.villagerXp;
   }

   public void overrideXp(int xp) {
      this.villagerXp = xp;
   }

   public boolean showProgressBar() {
      return true;
   }

   public SoundEvent getNotifyTradeSound() {
      return SoundEvents.VILLAGER_TRADE;
   }

   public boolean isClientSide() {
      return this.level().isClientSide();
   }

   public void addAdditionalSaveData(CompoundTag tag) {
      super.addAdditionalSaveData(tag);
      tag.putInt("VillagerLevel", this.villagerLevel);
      tag.putInt("VillagerXp", this.villagerXp);
      if (this.offers != null && !this.offers.isEmpty()) {
         RegistryOps<Tag> ops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
         MerchantOffers.CODEC.encodeStart(ops, this.offers).resultOrPartial().ifPresent(offersTag -> tag.put("Offers", offersTag));
      }
   }

   public void readAdditionalSaveData(CompoundTag tag) {
      super.readAdditionalSaveData(tag);
      if (tag.contains("VillagerLevel")) {
         this.villagerLevel = tag.getInt("VillagerLevel");
      }

      if (tag.contains("VillagerXp")) {
         this.villagerXp = tag.getInt("VillagerXp");
      }

      if (tag.contains("Offers")) {
         RegistryOps<Tag> ops = this.registryAccess().createSerializationContext(NbtOps.INSTANCE);
         MerchantOffers.CODEC.parse(ops, tag.get("Offers")).resultOrPartial().ifPresent(parsedOffers -> this.offers = parsedOffers);
      }
   }

   public void die(DamageSource cause) {
      if (!this.level().isClientSide()) {
         ServerLevel serverLevel = (ServerLevel)this.level();
         if (cause.getEntity() instanceof Player killer) {
            killer.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 36000, 0));
         }

         serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 1.0, this.getZ(), 30, 0.3, 0.5, 0.3, 0.05);
         EntityType<?> type = (EntityType<?>)ModEntities.STATUE_BOSSUNIT.get();
         if (type.create(serverLevel) instanceof StatueBossunitEntity statueBossunitEntity) {
            statueBossunitEntity.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), this.getXRot());
            serverLevel.addFreshEntity(statueBossunitEntity);
         }
      }

      super.die(cause);
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "controller", 3, event -> {
         if (event.isMoving()) {
            return this.getDeltaMovement().horizontalDistanceSqr() > 0.015 ? event.setAndContinue(RUN_ANIM) : event.setAndContinue(WALK_ANIM);
         } else {
            return event.setAndContinue(IDLE_ANIM);
         }
      }));
   }

   @Nullable
   protected SoundEvent getAmbientSound() {
      return SoundEvents.VILLAGER_AMBIENT;
   }

   @Nullable
   protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
      return SoundEvents.VILLAGER_HURT;
   }

   @Nullable
   protected SoundEvent getDeathSound() {
      return SoundEvents.VILLAGER_DEATH;
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
