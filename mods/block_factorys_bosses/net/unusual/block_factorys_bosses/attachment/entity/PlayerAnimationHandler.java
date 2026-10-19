package net.unusual.block_factorys_bosses.attachment.entity;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;
import net.neoforged.neoforge.network.PacketDistributor;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.network.PlayerAnimationMessage;
import org.jetbrains.annotations.UnknownNullability;

@EventBusSubscriber
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class PlayerAnimationHandler implements INBTSerializable<CompoundTag> {
   protected final List<PlayerAnimationHandler.TimedPlayerAnimation> queuedAnimations = new ArrayList<>();
   @Nullable
   protected PlayerAnimationHandler.PlayerAnimation currentAnimation = null;
   protected int duration = 0;
   protected int tick = 0;

   public static PlayerAnimationHandler fromPlayer(Player player) {
      return (PlayerAnimationHandler)player.getData(BossesRiseDataAttachments.PLAYER_ANIMATION);
   }

   public int getDuration() {
      return this.duration;
   }

   public int getTick() {
      return this.tick;
   }

   public boolean canLerp() {
      return this.queuedAnimations.isEmpty() && (this.currentAnimation == null || !this.currentAnimation.loop());
   }

   @Nullable
   public PlayerAnimationHandler.PlayerAnimation getCurrentAnimation() {
      return this.tick <= 0 ? null : this.currentAnimation;
   }

   public void startAnimation(Player player, PlayerAnimationHandler.QueuedPlayerAnimation animation) {
      this.queuedAnimations.clear();
      this.queuedAnimations.addAll(animation.queue());
      if (this.queuedAnimations.isEmpty()) {
         this.currentAnimation = null;
      } else {
         PlayerAnimationHandler.TimedPlayerAnimation timedAnimation = this.queuedAnimations.removeFirst();
         this.currentAnimation = timedAnimation.playerAnimation();
         this.duration = timedAnimation.duration();
         this.tick = this.duration;
         if (player instanceof ServerPlayer serverPlayer) {
            PacketDistributor.sendToPlayersTrackingEntityAndSelf(
               serverPlayer, new PlayerAnimationMessage(animation, serverPlayer.getId()), new CustomPacketPayload[0]
            );
         }
      }
   }

   public void startAnimation(Player player, PlayerAnimationHandler.PlayerAnimation animation, int duration) {
      this.queuedAnimations.clear();
      this.currentAnimation = animation;
      this.duration = duration;
      this.tick = this.duration;
      if (player instanceof ServerPlayer serverPlayer) {
         PacketDistributor.sendToPlayersTrackingEntityAndSelf(
            serverPlayer,
            new PlayerAnimationMessage(new PlayerAnimationHandler.TimedPlayerAnimation(animation, duration), serverPlayer.getId()),
            new CustomPacketPayload[0]
         );
      }
   }

   public void tick() {
      if (this.tick > 0 && --this.tick <= 0) {
         if (this.currentAnimation != null && this.currentAnimation.loop) {
            this.tick = this.duration;
         } else if (!this.queuedAnimations.isEmpty()) {
            PlayerAnimationHandler.TimedPlayerAnimation timedAnimation = this.queuedAnimations.removeFirst();
            this.currentAnimation = timedAnimation.playerAnimation();
            this.duration = timedAnimation.duration();
            this.tick = this.duration;
         }
      }
   }

   public @UnknownNullability CompoundTag serializeNBT(Provider provider) {
      CompoundTag tag = new CompoundTag();
      tag.putInt("duration", this.duration);
      tag.putInt("tick", this.tick);
      if (this.currentAnimation != null) {
         tag.putString("path", this.currentAnimation.path().toString());
         tag.putString("name", this.currentAnimation.name());
         tag.putFloat("lerp", this.currentAnimation.lerp());
         tag.putBoolean("loop", this.currentAnimation.loop());
      }

      return tag;
   }

   public void deserializeNBT(Provider provider, CompoundTag tag) {
      this.duration = tag.getInt("duration");
      this.tick = tag.getInt("tick");
      if (tag.contains("path")) {
         ResourceLocation path = ResourceLocation.tryParse(tag.getString("path"));
         if (path != null) {
            this.currentAnimation = new PlayerAnimationHandler.PlayerAnimation(path, tag.getString("name"), tag.getFloat("lerp"), tag.getBoolean("loop"));
         }
      }
   }

   @SubscribeEvent
   private static void tickEvent(Post event) {
      fromPlayer(event.getEntity()).tick();
   }

   public record PlayerAnimation(ResourceLocation path, String name, float lerp, boolean loop) {
      public static final Codec<PlayerAnimationHandler.PlayerAnimation> CODEC = RecordCodecBuilder.create(
         recordCodecBuilder -> recordCodecBuilder.group(
               ResourceLocation.CODEC.fieldOf("path").forGetter(PlayerAnimationHandler.PlayerAnimation::path),
               Codec.STRING.fieldOf("name").forGetter(PlayerAnimationHandler.PlayerAnimation::name),
               Codec.floatRange(0.0F, 1.0F).fieldOf("lerp").forGetter(PlayerAnimationHandler.PlayerAnimation::lerp),
               Codec.BOOL.fieldOf("loop").forGetter(PlayerAnimationHandler.PlayerAnimation::loop)
            )
            .apply(recordCodecBuilder, PlayerAnimationHandler.PlayerAnimation::new)
      );

      public PlayerAnimation(ResourceLocation path, String name, float lerp) {
         this(path, name, lerp, false);
      }

      public PlayerAnimation(ResourceLocation path, String name, boolean loop) {
         this(path, name, 1.0F, loop);
      }

      public PlayerAnimation(ResourceLocation path, String name) {
         this(path, name, 1.0F, false);
      }
   }

   public record QueuedPlayerAnimation(List<PlayerAnimationHandler.TimedPlayerAnimation> queue) {
      public static final Codec<PlayerAnimationHandler.QueuedPlayerAnimation> CODEC = RecordCodecBuilder.create(
         recordCodecBuilder -> recordCodecBuilder.group(
               PlayerAnimationHandler.TimedPlayerAnimation.CODEC.listOf().fieldOf("queue").forGetter(PlayerAnimationHandler.QueuedPlayerAnimation::queue)
            )
            .apply(recordCodecBuilder, PlayerAnimationHandler.QueuedPlayerAnimation::new)
      );
   }

   public record TimedPlayerAnimation(PlayerAnimationHandler.PlayerAnimation playerAnimation, int duration) {
      public static final Codec<PlayerAnimationHandler.TimedPlayerAnimation> CODEC = RecordCodecBuilder.create(
         recordCodecBuilder -> recordCodecBuilder.group(
               PlayerAnimationHandler.PlayerAnimation.CODEC.fieldOf("player_animation").forGetter(PlayerAnimationHandler.TimedPlayerAnimation::playerAnimation),
               Codec.INT.fieldOf("duration").forGetter(PlayerAnimationHandler.TimedPlayerAnimation::duration)
            )
            .apply(recordCodecBuilder, PlayerAnimationHandler.TimedPlayerAnimation::new)
      );
   }
}
