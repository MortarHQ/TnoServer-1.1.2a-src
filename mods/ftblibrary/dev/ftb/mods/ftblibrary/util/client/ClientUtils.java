package dev.ftb.mods.ftblibrary.util.client;

import dev.architectury.event.EventActor;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientChatEvent;
import dev.architectury.event.events.client.ClientChatEvent.Send;
import dev.architectury.fluid.FluidStack;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.ui.CustomClickEvent;
import dev.ftb.mods.ftblibrary.ui.IScreenWrapper;
import dev.ftb.mods.ftblibrary.util.client.neoforge.ClientUtilsImpl;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import net.minecraft.ResourceLocationException;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

public class ClientUtils {
   public static final BooleanSupplier IS_CLIENT_OP = () -> Minecraft.getInstance().player != null && Minecraft.getInstance().player.hasPermissions(1);
   public static final List<Runnable> RUN_LATER = new ArrayList<>();
   private static final MethodType EMPTY_METHOD_TYPE = MethodType.methodType(void.class);
   private static final HashMap<String, Optional<MethodHandle>> staticMethodCache = new HashMap<>();

   public static void execClientCommand(String command, boolean printChat) {
      if (!command.isEmpty() && Minecraft.getInstance().player != null) {
         EventResult res = ((Send)ClientChatEvent.SEND.invoker()).send(command, null);
         if (!res.interruptsFurtherEvaluation()) {
            if (printChat) {
               Minecraft.getInstance().gui.getChat().addRecentChat(command);
            }

            Minecraft.getInstance().player.connection.sendCommand(command.replaceFirst("/", ""));
         }
      }
   }

   public static void runLater(Runnable runnable) {
      RUN_LATER.add(runnable);
   }

   @Nullable
   public static <T> T getGuiAs(Screen gui, Class<T> clazz) {
      if (gui instanceof IScreenWrapper wrapper) {
         BaseScreen guiBase = wrapper.getGui();
         if (clazz.isAssignableFrom(guiBase.getClass())) {
            return (T)guiBase;
         }
      }

      return (T)(clazz.isAssignableFrom(gui.getClass()) ? Minecraft.getInstance().screen : null);
   }

   @Nullable
   public static <T> T getCurrentGuiAs(Class<T> clazz) {
      return Minecraft.getInstance().screen == null ? null : getGuiAs(Minecraft.getInstance().screen, clazz);
   }

   public static boolean handleClick(String scheme, String path) {
      switch (scheme) {
         case "http":
         case "https":
            String uriStr = scheme + ":" + path;

            try {
               URI uri = new URI(uriStr);
               if ((Boolean)Minecraft.getInstance().options.chatLinksPrompt().get()) {
                  Screen currentScreen = Minecraft.getInstance().screen;
                  Minecraft.getInstance().setScreen(new ConfirmLinkScreen(accepted -> {
                     if (accepted) {
                        Util.getPlatform().openUri(uri);
                     }

                     Minecraft.getInstance().setScreen(currentScreen);
                  }, uriStr, false));
               } else {
                  Util.getPlatform().openUri(uri);
               }

               return true;
            } catch (Exception ex) {
               logHandleClickFailure(scheme, uriStr, ex);
               FTBLibrary.LOGGER.warn("handleClick: unexpected exception handling http/https action {}: {}", uriStr, ex.getMessage());
               return false;
            }
         case "file":
            try {
               Util.getPlatform().openUri(new URI("file:" + path));
               return true;
            } catch (Exception ex) {
               logHandleClickFailure(scheme, path, ex);
               return false;
            }
         case "command":
            execClientCommand(path, false);
            return true;
         case "static_method":
            return staticMethodCache.computeIfAbsent(path, k -> {
               String[] s = path.split(":", 2);

               try {
                  Class<?> cls = Class.forName(s[0]);
                  return Optional.ofNullable(MethodHandles.publicLookup().findStatic(cls, s[1], EMPTY_METHOD_TYPE));
               } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | ArrayIndexOutOfBoundsException ex) {
                  logHandleClickFailure(scheme, path, exx);
                  return Optional.empty();
               }
            }).<Boolean>map(handle -> {
               try {
                  handle.invoke();
                  return true;
               } catch (Throwable ex) {
                  logHandleClickFailure(scheme, path, exx);
                  return false;
               }
            }).orElse(false);
         case "custom":
            return trySendCustomClickEvent(path);
         default:
            return trySendCustomClickEvent(scheme + ":" + path);
      }
   }

   private static boolean trySendCustomClickEvent(String name) {
      try {
         ResourceLocation rl = ResourceLocation.parse(name);
         return ((EventActor)CustomClickEvent.EVENT.invoker()).act(new CustomClickEvent(rl)).isPresent();
      } catch (ResourceLocationException ex) {
         logHandleClickFailure("custom", name, ex);
         return false;
      }
   }

   private static void logHandleClickFailure(String scheme, String path, Throwable ex) {
      FTBLibrary.LOGGER.warn("handleClick: unexpected exception handling action {} / {}: {}", scheme, path, ex.getMessage());
   }

   public static Provider registryAccess() {
      return Objects.requireNonNull(Minecraft.getInstance().level).registryAccess();
   }

   @ExpectPlatform
   @Transformed
   public static ResourceLocation getStillTexture(FluidStack stack) {
      return ClientUtilsImpl.getStillTexture(stack);
   }

   @ExpectPlatform
   @Transformed
   public static int getFluidColor(FluidStack stack) {
      return ClientUtilsImpl.getFluidColor(stack);
   }
}
