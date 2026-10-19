package com.nododiiiii.ponderer.compat.jei;

import java.lang.reflect.Method;
import javax.annotation.Nullable;

public final class JeiOverlayController {
   private static boolean pushed = false;
   @Nullable
   private static Boolean previousOverlayEnabled = null;

   private JeiOverlayController() {
   }

   public static void pushEnabled() {
      if (!pushed && JeiCompat.isAvailable()) {
         Object toggleState = getClientToggleState();
         if (toggleState != null) {
            try {
               Boolean overlayEnabled = invokeBooleanNoArg(toggleState, "isOverlayEnabled");
               if (overlayEnabled == null) {
                  return;
               }

               previousOverlayEnabled = overlayEnabled;
               if (!overlayEnabled) {
                  invokeNoArg(toggleState, "toggleOverlayEnabled");
               }

               pushed = true;
            } catch (Throwable ignored) {
               pushed = false;
               previousOverlayEnabled = null;
            }
         }
      }
   }

   public static void popEnabled() {
      if (pushed && JeiCompat.isAvailable()) {
         try {
            Object toggleState = getClientToggleState();
            if (toggleState != null && previousOverlayEnabled != null) {
               Boolean currentOverlayEnabled = invokeBooleanNoArg(toggleState, "isOverlayEnabled");
               if (currentOverlayEnabled != null && currentOverlayEnabled != previousOverlayEnabled) {
                  invokeNoArg(toggleState, "toggleOverlayEnabled");
               }
            }
         } catch (Throwable var5) {
         } finally {
            pushed = false;
            previousOverlayEnabled = null;
         }
      }
   }

   @Nullable
   private static Object getClientToggleState() {
      try {
         Class<?> internalClass = Class.forName("mezz.jei.common.Internal");
         Method getClientToggleState = internalClass.getMethod("getClientToggleState");
         return getClientToggleState.invoke(null);
      } catch (Throwable t) {
         return null;
      }
   }

   private static Object invokeNoArg(Object target, String methodName) throws Exception {
      Method m = target.getClass().getMethod(methodName);
      m.setAccessible(true);
      return m.invoke(target);
   }

   @Nullable
   private static Boolean invokeBooleanNoArg(Object target, String methodName) throws Exception {
      return invokeNoArg(target, methodName) instanceof Boolean b ? b : null;
   }
}
