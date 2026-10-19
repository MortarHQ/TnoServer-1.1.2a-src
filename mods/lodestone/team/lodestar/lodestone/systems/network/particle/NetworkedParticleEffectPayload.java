package team.lodestar.lodestone.systems.network.particle;

import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import team.lodestar.lodestone.systems.network.OneSidedPayloadData;

public class NetworkedParticleEffectPayload extends OneSidedPayloadData {
   private final NetworkedParticleEffectType<?> effect;
   @Nullable
   private final NetworkedParticleEffectPositionData positionData;
   @Nullable
   private final NetworkedParticleEffectColorData colorData;
   @Nullable
   private final NetworkedParticleEffectExtraData extraData;

   public NetworkedParticleEffectPayload(
      NetworkedParticleEffectType<?> effect,
      @Nullable NetworkedParticleEffectPositionData positionData,
      @Nullable NetworkedParticleEffectColorData colorData,
      @Nullable NetworkedParticleEffectExtraData extraData
   ) {
      this.effect = effect;
      this.positionData = positionData;
      this.colorData = colorData;
      this.extraData = extraData;
   }

   public NetworkedParticleEffectPayload(FriendlyByteBuf buf) {
      this.effect = this.getEffectType(buf.readUtf());
      this.positionData = this.effect.getPositionCodec().isPresent()
         ? (NetworkedParticleEffectPositionData)this.effect.getPositionCodec().get().decode(buf)
         : null;
      this.colorData = this.effect.getColorCodec().isPresent() ? (NetworkedParticleEffectColorData)this.effect.getColorCodec().get().decode(buf) : null;
      this.extraData = this.effect.getExtraCodec().isPresent() ? (NetworkedParticleEffectExtraData)this.effect.getExtraCodec().get().decode(buf) : null;
   }

   @Override
   public void serialize(FriendlyByteBuf buf) {
      buf.writeUtf(this.effect.id);
      if (this.effect.getPositionCodec().isPresent()) {
         if (this.positionData == null) {
            throw new IllegalArgumentException("Networked Particle Effect expected position data, did not receive any.");
         }

         StreamCodec codec = this.effect.getPositionCodec().get();
         codec.encode(buf, this.positionData);
      }

      if (this.effect.getColorCodec().isPresent()) {
         if (this.colorData == null) {
            throw new IllegalArgumentException("Networked Particle Effect expected color data, did not receive any.");
         }

         StreamCodec codec = this.effect.getColorCodec().get();
         codec.encode(buf, this.colorData);
      }

      if (this.effect.getExtraCodec().isPresent()) {
         if (this.extraData == null) {
            throw new IllegalArgumentException("Networked Particle Effect expected extra data, did not receive any.");
         }

         StreamCodec codec = this.effect.getExtraCodec().get();
         codec.encode(buf, this.extraData);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void handle(IPayloadContext iPayloadContext) {
      Minecraft instance = Minecraft.getInstance();
      ClientLevel level = instance.level;
      this.effect.castAndAct(level, level.random, this.positionData, this.colorData, this.extraData);
   }

   public NetworkedParticleEffectType<?> getEffectType(String id) {
      NetworkedParticleEffectType<?> particleEffectType = NetworkedParticleEffectType.EFFECT_TYPES.get(id);
      if (particleEffectType == null) {
         throw new NullPointerException(
            "This shouldn't be happening. The particle effect type with id: \"" + id + "\" does not exist. This is most likely a networking error."
         );
      } else {
         return particleEffectType;
      }
   }
}
