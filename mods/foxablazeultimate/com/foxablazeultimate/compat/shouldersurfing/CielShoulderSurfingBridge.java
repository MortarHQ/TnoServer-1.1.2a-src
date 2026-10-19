package com.foxablazeultimate.compat.shouldersurfing;

import com.foxablazeultimate.FoxAblazeUltimateMod;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.minecraft.util.Mth;

public final class CielShoulderSurfingBridge {
   private static volatile CielShoulderSurfingBridge.CameraAccess cameraAccess;
   private static volatile boolean resolutionAttempted;
   private static volatile boolean invocationFailureLogged;

   private CielShoulderSurfingBridge() {
   }

   public static boolean smoothCameraAim(float targetYaw, float targetPitch, float maximumYawChange, float maximumPitchChange) {
      CielShoulderSurfingBridge.CameraAccess access = getCameraAccess();
      if (access == null) {
         return false;
      }

      try {
         if (!(Boolean)access.isShoulderSurfing().invoke(access.instance())) {
            return false;
         }

         Object camera = access.getCamera().invoke(access.instance());
         float currentYaw = (Float)access.getYRot().invoke(camera);
         float currentPitch = (Float)access.getXRot().invoke(camera);
         access.setYRot().invoke(camera, Mth.approachDegrees(currentYaw, targetYaw, Math.max(0.0F, maximumYawChange)));
         access.setXRot().invoke(camera, Mth.approach(currentPitch, targetPitch, Math.max(0.0F, maximumPitchChange)));
         return true;
      } catch (ReflectiveOperationException | LinkageError exception) {
         cameraAccess = null;
         logInvocationFailure(exception);
         return false;
      }
   }

   private static CielShoulderSurfingBridge.CameraAccess getCameraAccess() {
      CielShoulderSurfingBridge.CameraAccess current = cameraAccess;
      if (current == null && !resolutionAttempted) {
         synchronized (CielShoulderSurfingBridge.class) {
            if (cameraAccess == null && !resolutionAttempted) {
               resolutionAttempted = true;

               try {
                  cameraAccess = resolveCameraAccess();
               } catch (ClassNotFoundException var4) {
               } catch (ReflectiveOperationException | LinkageError exception) {
                  FoxAblazeUltimateMod.LOGGER.warn("Could not initialize Shoulder Surfing camera compatibility", exception);
               }

               return cameraAccess;
            } else {
               return cameraAccess;
            }
         }
      } else {
         return current;
      }
   }

   private static CielShoulderSurfingBridge.CameraAccess resolveCameraAccess() throws ReflectiveOperationException {
      ClassLoader loader = CielShoulderSurfingBridge.class.getClassLoader();
      Class<?> shoulderSurfingClass = Class.forName("com.github.exopandora.shouldersurfing.api.client.IShoulderSurfing", false, loader);

      Object instance;
      try {
         instance = shoulderSurfingClass.getMethod("getInstance").invoke(null);
      } catch (NoSuchMethodException ignored) {
         Class<?> legacyApiClass = Class.forName("com.github.exopandora.shouldersurfing.api.client.ShoulderSurfing", false, loader);
         instance = legacyApiClass.getMethod("getInstance").invoke(null);
      }

      Class<?> cameraClass = Class.forName("com.github.exopandora.shouldersurfing.api.client.IShoulderSurfingCamera", false, loader);
      return new CielShoulderSurfingBridge.CameraAccess(
         instance,
         shoulderSurfingClass.getMethod("isShoulderSurfing"),
         shoulderSurfingClass.getMethod("getCamera"),
         cameraClass.getMethod("getYRot"),
         cameraClass.getMethod("setYRot", float.class),
         cameraClass.getMethod("getXRot"),
         cameraClass.getMethod("setXRot", float.class)
      );
   }

   private static void logInvocationFailure(Throwable throwable) {
      if (!invocationFailureLogged) {
         invocationFailureLogged = true;
         Throwable cause = throwable instanceof InvocationTargetException invocation && invocation.getCause() != null ? invocation.getCause() : throwable;
         FoxAblazeUltimateMod.LOGGER.warn("Shoulder Surfing camera compatibility stopped after an API invocation failed", cause);
      }
   }

   private record CameraAccess(Object instance, Method isShoulderSurfing, Method getCamera, Method getYRot, Method setYRot, Method getXRot, Method setXRot) {
   }
}
