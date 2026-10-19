package com.finderfeed.fdlib.systems.screen.screen_effect;

import com.finderfeed.fdlib.network.FDPacket;
import com.finderfeed.fdlib.network.RegisterFDPacket;
import com.finderfeed.fdlib.systems.FDRegistries;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

@RegisterFDPacket("fdlib:screen_effect")
public class SendScreenEffectPacket<D extends ScreenEffectData, T extends ScreenEffect<D>> extends FDPacket {
   private D data;
   private ScreenEffectType<D, T> type;
   private int inTime;
   private int stayTime;
   private int outTime;

   public SendScreenEffectPacket(D data, ScreenEffectType<D, T> type, int inTime, int stayTime, int outTime) {
      this.data = data;
      this.type = type;
      this.stayTime = stayTime;
      this.outTime = outTime;
      this.inTime = inTime;
   }

   public SendScreenEffectPacket(RegistryFriendlyByteBuf buf) {
      RegistryAccess access = buf.registryAccess();
      Registry<ScreenEffectType<?, ?>> registry = access.registryOrThrow(FDRegistries.SCREEN_EFFECTS_KEY);
      String location = buf.readUtf();
      ScreenEffectType<?, ?> t = (ScreenEffectType<?, ?>)registry.get(ResourceLocation.parse(location));
      ScreenEffectData effectData = (ScreenEffectData)t.dataCodec.decode(buf);
      this.data = (D)effectData;
      this.type = (ScreenEffectType<D, T>)t;
      this.inTime = buf.readInt();
      this.stayTime = buf.readInt();
      this.outTime = buf.readInt();
   }

   @Override
   public void write(RegistryFriendlyByteBuf buf) {
      RegistryAccess access = buf.registryAccess();
      Registry<ScreenEffectType<?, ?>> registry = access.registryOrThrow(FDRegistries.SCREEN_EFFECTS_KEY);
      ResourceLocation location = registry.getKey(this.type);
      buf.writeUtf(location.toString());
      this.type.dataCodec.encode(buf, this.data);
      buf.writeInt(this.inTime);
      buf.writeInt(this.stayTime);
      buf.writeInt(this.outTime);
   }

   @Override
   public void clientAction(IPayloadContext context) {
      ScreenEffect<?> effect = this.type.factory.create(this.data, this.inTime, this.stayTime, this.outTime);
      ScreenEffectOverlay.addScreenEffect(effect);
   }

   @Override
   public void serverAction(IPayloadContext context) {
   }
}
