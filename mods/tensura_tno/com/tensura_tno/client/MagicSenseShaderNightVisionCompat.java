package com.tensura_tno.client;

import com.tensura_tno.TensuraTNOMod;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.lang.reflect.Method;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;

@EventBusSubscriber(modid = "tensura_tno", value = Dist.CLIENT)
public final class MagicSenseShaderNightVisionCompat {
   private static final int COMPAT_NIGHT_VISION_TICKS = 260;
   private static Method irisApiGetInstance;
   private static Method irisApiIsShaderPackInUse;
   private static Method irisQuickShaderCheck;
   private static boolean reflectionInitialized;
   private static boolean reflectionUnavailable;
   private static boolean appliedCompatNightVision;

   private MagicSenseShaderNightVisionCompat() {
   }

   @SubscribeEvent
   public static void onClientTick(Post event) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer player = mc.player;
      if (mc.level != null && player != null) {
         boolean shouldApply = isShaderPackInUse() && hasTensuraVision(player);
         if (shouldApply) {
            applyCompatNightVision(player);
         } else {
            removeCompatNightVision(player);
         }
      } else {
         appliedCompatNightVision = false;
      }
   }

   private static void applyCompatNightVision(LocalPlayer player) {
      MobEffectInstance current = player.getEffect(MobEffects.NIGHT_VISION);
      if (current != null && !isCompatNightVision(current)) {
         appliedCompatNightVision = false;
      } else {
         if (current == null || current.getDuration() < 240) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 260, 0, false, false, false));
         }

         appliedCompatNightVision = true;
      }
   }

   private static boolean hasTensuraVision(LocalPlayer player) {
      return player.getAttributeValue(TensuraAttributes.PRESENCE_SENSE) > 0.0 || player.getAttributeValue(TensuraAttributes.DARK_VISION) > 0.0;
   }

   private static void removeCompatNightVision(LocalPlayer player) {
      if (appliedCompatNightVision) {
         MobEffectInstance current = player.getEffect(MobEffects.NIGHT_VISION);
         if (current != null && isCompatNightVision(current)) {
            player.removeEffect(MobEffects.NIGHT_VISION);
         }

         appliedCompatNightVision = false;
      }
   }

   private static boolean isCompatNightVision(MobEffectInstance instance) {
      return instance.getAmplifier() == 0 && !instance.isAmbient() && !instance.isVisible() && !instance.showIcon() && instance.getDuration() <= 260;
   }

   private static boolean isShaderPackInUse() {
      if (!ModList.get().isLoaded("iris") && !ModList.get().isLoaded("oculus")) {
         return false;
      }

      try {
         ensureShaderReflection();
         if (irisApiGetInstance != null && irisApiIsShaderPackInUse != null) {
            Object irisApi = irisApiGetInstance.invoke(null);
            return Boolean.TRUE.equals(irisApiIsShaderPackInUse.invoke(irisApi));
         }

         if (irisQuickShaderCheck != null) {
            return Boolean.TRUE.equals(irisQuickShaderCheck.invoke(null));
         }
      } catch (ReflectiveOperationException | RuntimeException e) {
         if (!reflectionUnavailable) {
            reflectionUnavailable = true;
            TensuraTNOMod.LOGGER.warn("[TensuraTNO] Magic Sense shader night vision compat disabled: Iris/Oculus reflection failed", e);
         }
      }

      return false;
   }

   private static void ensureShaderReflection() throws ReflectiveOperationException {
      if (!reflectionInitialized && !reflectionUnavailable) {
         reflectionInitialized = true;
         if (!tryInitIrisApi()) {
            if (!tryInitQuickShaderCheck("net.irisshaders.iris.Iris")) {
               if (!tryInitQuickShaderCheck("net.coderbot.iris.Iris")) {
                  reflectionUnavailable = true;
               }
            }
         }
      }
   }

   private static boolean tryInitIrisApi() {
      try {
         Class<?> apiClass = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
         irisApiGetInstance = apiClass.getMethod("getInstance");
         irisApiIsShaderPackInUse = apiClass.getMethod("isShaderPackInUse");
         return true;
      } catch (ReflectiveOperationException ignored) {
         return false;
      }
   }

   private static boolean tryInitQuickShaderCheck(String className) {
      try {
         Class<?> irisClass = Class.forName(className);
         irisQuickShaderCheck = irisClass.getMethod("isPackInUseQuick");
         return true;
      } catch (ReflectiveOperationException ignored) {
         return false;
      }
   }
}
