package com.finderfeed.fdlib.systems.cutscenes;

import com.finderfeed.fdlib.systems.FDRegistries;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffect;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectData;
import com.finderfeed.fdlib.systems.screen.screen_effect.ScreenEffectType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public class CutsceneScreenEffectData {
   private HashMap<Integer, List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>>> screenEffects = new HashMap<>();

   public <A extends ScreenEffectData, B extends ScreenEffect<A>> void putScreenEffectOnTick(
      int tick, ScreenEffectType<A, B> type, A data, int inTime, int stayTime, int outTime
   ) {
      this.screenEffects
         .computeIfAbsent(tick, v -> new ArrayList<>())
         .add(new CutsceneScreenEffectData.ScreenEffectInstance<>(type, data, inTime, stayTime, outTime));
   }

   public List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>> getAllEffectsOnTick(int tick) {
      return this.screenEffects.get(tick);
   }

   public void encode(RegistryFriendlyByteBuf buf) {
      buf.writeInt(this.screenEffects.size());
      RegistryAccess access = buf.registryAccess();
      Registry<ScreenEffectType<?, ?>> registry = access.registryOrThrow(FDRegistries.SCREEN_EFFECTS_KEY);

      for (Entry<Integer, List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>>> entry : this.screenEffects.entrySet()) {
         Integer tick = entry.getKey();
         List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>> pairs = entry.getValue();
         int pairsCount = pairs.size();
         buf.writeInt(pairsCount);
         buf.writeInt(tick);

         for (CutsceneScreenEffectData.ScreenEffectInstance<?, ?> pair : pairs) {
            ScreenEffectType<?, ? extends ScreenEffect<?>> type = (ScreenEffectType<?, ? extends ScreenEffect<?>>)pair.type;
            ScreenEffectData data = pair.data;
            ResourceLocation location = registry.getKey(type);
            buf.writeUtf(location.toString());
            this.encodeScreenEffectData(buf, type, data);
            buf.writeInt(pair.inTime);
            buf.writeInt(pair.stayTime);
            buf.writeInt(pair.outTime);
         }
      }
   }

   private <A extends ScreenEffectData, B extends ScreenEffect<A>> void encodeScreenEffectData(
      RegistryFriendlyByteBuf buf, ScreenEffectType<A, B> type, ScreenEffectData data
   ) {
      StreamCodec<RegistryFriendlyByteBuf, A> codec = type.dataCodec;
      codec.encode(buf, data);
   }

   public static CutsceneScreenEffectData decode(RegistryFriendlyByteBuf buf) {
      HashMap<Integer, List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>>> screenEffects = new HashMap<>();
      int effects = buf.readInt();
      RegistryAccess access = buf.registryAccess();
      Registry<ScreenEffectType<?, ?>> registry = access.registryOrThrow(FDRegistries.SCREEN_EFFECTS_KEY);

      for (int i = 0; i < effects; i++) {
         int pairs = buf.readInt();
         int tick = buf.readInt();

         for (int k = 0; k < pairs; k++) {
            String location = buf.readUtf();
            ScreenEffectType<?, ?> t = (ScreenEffectType<?, ?>)registry.get(ResourceLocation.parse(location));
            ScreenEffectData effectData = (ScreenEffectData)t.dataCodec.decode(buf);
            ScreenEffectData data = effectData;
            int inTime = buf.readInt();
            int stayTime = buf.readInt();
            int outTime = buf.readInt();
            putInMap(screenEffects, t, data, inTime, stayTime, outTime, tick);
         }
      }

      CutsceneScreenEffectData cutsceneScreenEffectData = new CutsceneScreenEffectData();
      cutsceneScreenEffectData.screenEffects = screenEffects;
      return cutsceneScreenEffectData;
   }

   private static <A extends ScreenEffectData, B extends ScreenEffect<A>> void putInMap(
      HashMap<Integer, List<CutsceneScreenEffectData.ScreenEffectInstance<?, ?>>> map,
      ScreenEffectType<A, B> type,
      ScreenEffectData data,
      int inTime,
      int stayTime,
      int outTime,
      int tick
   ) {
      map.computeIfAbsent(tick, v -> new ArrayList<>()).add(new CutsceneScreenEffectData.ScreenEffectInstance<>(type, (A)data, inTime, stayTime, outTime));
   }

   public record ScreenEffectInstance<A extends ScreenEffectData, B extends ScreenEffect<A>>(
      ScreenEffectType<A, B> type, A data, int inTime, int stayTime, int outTime
   ) {
   }
}
