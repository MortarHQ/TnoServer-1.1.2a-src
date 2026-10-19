package com.benji.netherman.common.entity;

import com.benji.netherman.init.ModItems;
import com.benji.netherman.init.ModSounds;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class AzazelGuideBookEntity extends PathfinderMob implements GeoEntity {
   private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
   public static final EntityDataAccessor<Integer> BOOK_STATE = SynchedEntityData.defineId(AzazelGuideBookEntity.class, EntityDataSerializers.INT);
   public static final EntityDataAccessor<Integer> READING_PAGE = SynchedEntityData.defineId(AzazelGuideBookEntity.class, EntityDataSerializers.INT);
   private static final int TICKS_PER_CHAR = 1;
   private static final int LINE_HOLD_TIME = 50;
   private int animationTimer = 0;
   private int localReadingPage = 0;
   private int currentLineIndex = 0;
   private int lineTimerTick = 0;
   private static final int[] PAGE_LINES = new int[]{0, 7, 9, 9, 7, 11, 8};

   public AzazelGuideBookEntity(EntityType<? extends PathfinderMob> type, Level level) {
      super(type, level);
   }

   public static Builder createAttributes() {
      return PathfinderMob.createMobAttributes().add(Attributes.MAX_HEALTH, 10.0).add(Attributes.MOVEMENT_SPEED, 0.0);
   }

   protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
      super.defineSynchedData(builder);
      builder.define(BOOK_STATE, 0);
      builder.define(READING_PAGE, 0);
   }

   protected void registerGoals() {
   }

   public void tick() {
      super.tick();
      if (!this.level().isClientSide() && this.tickCount == 1 && (Integer)this.entityData.get(BOOK_STATE) == 0) {
         this.animationTimer = 15;
         if (this.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.4, this.getZ(), 15, 0.2, 0.2, 0.2, 0.02);
            serverLevel.sendParticles(ParticleTypes.SMOKE, this.getX(), this.getY() + 0.4, this.getZ(), 10, 0.1, 0.1, 0.1, 0.01);
         }
      }

      Player nearestPlayer = this.level().getNearestPlayer(this, 10.0);
      if (nearestPlayer != null) {
         double d0 = nearestPlayer.getX() - this.getX();
         double d1 = nearestPlayer.getZ() - this.getZ();
         float yaw = (float)(Mth.atan2(d1, d0) * (180.0 / Math.PI)) - 90.0F;
         this.setYRot(yaw);
         this.setYBodyRot(yaw);
         this.setYHeadRot(yaw);
         this.yRotO = yaw;
         double d2 = nearestPlayer.getEyeY() - this.getEyeY();
         double d3 = Math.sqrt(d0 * d0 + d1 * d1);
         float pitch = (float)(-(Mth.atan2(d2, d3) * (180.0 / Math.PI)));
         this.setXRot(pitch);
         this.xRotO = pitch;
      }

      if (!this.level().isClientSide() && this.animationTimer > 0) {
         this.animationTimer--;
         if (this.animationTimer == 0) {
            int currentState = (Integer)this.entityData.get(BOOK_STATE);
            if (currentState == 0) {
               this.entityData.set(BOOK_STATE, 1);
            } else if (currentState >= 12 && currentState <= 16) {
               this.entityData.set(BOOK_STATE, currentState - 10);
            } else if (currentState >= 21 && currentState <= 25) {
               this.entityData.set(BOOK_STATE, currentState - 20);
            } else if (currentState == 99) {
               this.spawnAtLocation((ItemLike)ModItems.AZAZEL_GUIDE_BOOK.get());
               if (this.level() instanceof ServerLevel serverLevel) {
                  serverLevel.sendParticles(ParticleTypes.LARGE_SMOKE, this.getX(), this.getY() + 0.5, this.getZ(), 15, 0.2, 0.2, 0.2, 0.02);
               }

               this.discard();
            }
         }
      }

      if (this.level().isClientSide()) {
         int serverReadingPage = (Integer)this.entityData.get(READING_PAGE);
         if (this.localReadingPage != serverReadingPage) {
            this.localReadingPage = serverReadingPage;
            this.currentLineIndex = 0;
            this.lineTimerTick = 0;
            if (this.localReadingPage == 0) {
               Player localPlayer = this.level().getNearestPlayer(this, 10.0);
               if (localPlayer != null) {
                  localPlayer.displayClientMessage(Component.empty(), true);
               }
            }
         }

         if (this.localReadingPage > 0) {
            this.handleClientReading();
         }
      }
   }

   private void handleClientReading() {
      if (this.localReadingPage >= 1 && this.localReadingPage <= 6) {
         int maxLines = PAGE_LINES[this.localReadingPage];
         if (this.currentLineIndex >= maxLines) {
            this.localReadingPage = 0;
         } else {
            String langKey = "book.netherman.page" + this.localReadingPage + ".line" + (this.currentLineIndex + 1);
            String fullText = Language.getInstance().getOrDefault(langKey);
            this.lineTimerTick++;
            int visibleCharsCount = this.lineTimerTick / 1;
            int realTextLength = this.getRealLength(fullText);
            Player localPlayer = this.level().getNearestPlayer(this, 10.0);
            if (localPlayer != null) {
               if (visibleCharsCount <= realTextLength) {
                  String visibleText = this.getSafeSubstring(fullText, visibleCharsCount);
                  localPlayer.displayClientMessage(
                     Component.literal(visibleText).withStyle(new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.BOLD}), true
                  );
                  if (this.lineTimerTick % 2 == 0 && visibleCharsCount > 0) {
                     this.level()
                        .playLocalSound(
                           this.getX(),
                           this.getY(),
                           this.getZ(),
                           (SoundEvent)ModSounds.AZAZEL_VOICE.get(),
                           SoundSource.NEUTRAL,
                           0.8F,
                           1.2F + this.random.nextFloat() * 0.2F,
                           false
                        );
                  }
               } else {
                  localPlayer.displayClientMessage(Component.literal(fullText).withStyle(new ChatFormatting[]{ChatFormatting.WHITE, ChatFormatting.BOLD}), true);
                  if (this.lineTimerTick >= realTextLength * 1 + 50) {
                     this.currentLineIndex++;
                     this.lineTimerTick = 0;
                  }
               }
            }
         }
      }
   }

   private int getRealLength(String text) {
      int len = 0;

      for (int i = 0; i < text.length(); i++) {
         if (text.charAt(i) == 167) {
            i++;
         } else {
            len++;
         }
      }

      return len;
   }

   private String getSafeSubstring(String text, int maxVisibleChars) {
      StringBuilder sb = new StringBuilder();
      int count = 0;

      for (int i = 0; i < text.length(); i++) {
         char c = text.charAt(i);
         if (c == 167) {
            sb.append(c);
            if (i + 1 < text.length()) {
               sb.append(text.charAt(i + 1));
               i++;
            }
         } else {
            sb.append(c);
            count++;
         }

         if (count > maxVisibleChars) {
            break;
         }
      }

      return sb.toString();
   }

   protected InteractionResult mobInteract(Player player, InteractionHand hand) {
      if (!this.level().isClientSide() && hand == InteractionHand.MAIN_HAND) {
         int state = (Integer)this.entityData.get(BOOK_STATE);
         int readingState = (Integer)this.entityData.get(READING_PAGE);
         if (player.isShiftKeyDown()) {
            if (state >= 1 && state <= 6 && readingState == 0 && this.animationTimer == 0) {
               this.entityData.set(READING_PAGE, state);
               return InteractionResult.SUCCESS;
            }
         } else if (state >= 1 && state < 6 && this.animationTimer == 0) {
            this.entityData.set(READING_PAGE, 0);
            int nextPage = state + 1;
            this.entityData.set(BOOK_STATE, 10 + nextPage);
            this.animationTimer = 15;
            this.level().playSound(null, this.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 1.0F + this.random.nextFloat() * 0.1F);
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.sidedSuccess(this.level().isClientSide());
      }
   }

   public boolean hurt(DamageSource source, float amount) {
      if (!this.level().isClientSide() && source.getEntity() instanceof Player player) {
         int state = (Integer)this.entityData.get(BOOK_STATE);
         if (this.animationTimer == 0) {
            this.entityData.set(READING_PAGE, 0);
            if (player.isShiftKeyDown()) {
               if (state >= 1 && state <= 6) {
                  this.entityData.set(BOOK_STATE, 99);
                  this.animationTimer = 10;
                  this.level().playSound(null, this.blockPosition(), SoundEvents.BOOK_PUT, SoundSource.NEUTRAL, 1.2F, 0.8F);
               }
            } else if (state > 1 && state <= 6) {
               int prevPage = state - 1;
               this.entityData.set(BOOK_STATE, 20 + prevPage);
               this.animationTimer = 15;
               this.level()
                  .playSound(null, this.blockPosition(), SoundEvents.BOOK_PAGE_TURN, SoundSource.NEUTRAL, 1.0F, 0.85F + this.random.nextFloat() * 0.1F);
            }
         }

         return false;
      } else {
         return false;
      }
   }

   public boolean isInvulnerableTo(DamageSource source) {
      return true;
   }

   public void travel(Vec3 travelVector) {
      this.setDeltaMovement(Vec3.ZERO);
      if (this.isControlledByLocalInstance()) {
         super.travel(travelVector);
      }
   }

   public boolean isPushable() {
      return false;
   }

   protected void doPush(Entity entity) {
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(new AnimationController(this, "book_controller", 3, event -> {
         int state = (Integer)this.entityData.get(BOOK_STATE);
         switch (state) {
            case 0:
               return event.setAndContinue(RawAnimation.begin().thenPlay("open"));
            case 1:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page1_idle"));
            case 2:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page2_idle"));
            case 3:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page3_idle"));
            case 4:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page4_idle"));
            case 5:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page5_idle"));
            case 6:
               return event.setAndContinue(RawAnimation.begin().thenLoop("page6_idle"));
            case 12:
               return event.setAndContinue(RawAnimation.begin().thenPlay("move_to_page2"));
            case 13:
               return event.setAndContinue(RawAnimation.begin().thenPlay("move_to_page3"));
            case 14:
               return event.setAndContinue(RawAnimation.begin().thenPlay("move_to_page4"));
            case 15:
               return event.setAndContinue(RawAnimation.begin().thenPlay("move_to_page5"));
            case 16:
               return event.setAndContinue(RawAnimation.begin().thenPlay("move_to_page6"));
            case 21:
               return event.setAndContinue(RawAnimation.begin().thenPlay("back_to_page1"));
            case 22:
               return event.setAndContinue(RawAnimation.begin().thenPlay("back_to_page2"));
            case 23:
               return event.setAndContinue(RawAnimation.begin().thenPlay("back_to_page3"));
            case 24:
               return event.setAndContinue(RawAnimation.begin().thenPlay("back_to_page4"));
            case 25:
               return event.setAndContinue(RawAnimation.begin().thenPlay("back_to_page5"));
            case 99:
               return event.setAndContinue(RawAnimation.begin().thenPlay("close"));
            default:
               return event.setAndContinue(RawAnimation.begin().thenLoop("close_idle"));
         }
      }));
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }
}
