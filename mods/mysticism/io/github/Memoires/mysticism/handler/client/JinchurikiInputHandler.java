package io.github.Memoires.mysticism.handler.client;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.client.ClientRawInputEvent.KeyPressed;
import dev.architectury.event.events.client.ClientRawInputEvent.MouseClicked;
import dev.architectury.event.events.client.ClientRawInputEvent.MouseScrolled;
import dev.architectury.event.events.client.ClientTickEvent.Client;
import io.github.Memoires.mysticism.entity.skill.UncontrolledJinchurikiEntity;
import net.minecraft.client.CameraType;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

public class JinchurikiInputHandler {
   private static boolean wasCloakActive;

   public static void init() {
      ClientRawInputEvent.KEY_PRESSED.register((KeyPressed)(client, keyCode, scanCode, action, modifiers) -> {
         if (!isCloakActive(client)) {
            return EventResult.pass();
         } else if (keyCode == 256) {
            return EventResult.pass();
         } else {
            return client.screen != null ? EventResult.pass() : EventResult.interruptFalse();
         }
      });
      ClientRawInputEvent.MOUSE_CLICKED_PRE
         .register(
            (MouseClicked)(client, button, action, modifiers) -> isCloakActive(client) && client.screen == null
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
      ClientRawInputEvent.MOUSE_SCROLLED
         .register(
            (MouseScrolled)(client, amountX, amountY) -> isCloakActive(client) && client.screen == null ? EventResult.interruptFalse() : EventResult.pass()
         );
      ClientTickEvent.CLIENT_PRE.register((Client)client -> {
         boolean cloakActive = isCloakActive(client);
         if (cloakActive) {
            client.options.setCameraType(CameraType.THIRD_PERSON_BACK);
            releaseKey(client.options.keyShift);
            releaseKey(client.options.keyUp);
            releaseKey(client.options.keyDown);
            releaseKey(client.options.keyLeft);
            releaseKey(client.options.keyRight);
            releaseKey(client.options.keyJump);
            releaseKey(client.options.keySprint);
            releaseKey(client.options.keyAttack);
            releaseKey(client.options.keyUse);
            releaseKey(client.options.keyPickItem);
            releaseKey(client.options.keyTogglePerspective);

            for (KeyMapping hotbarKey : client.options.keyHotbarSlots) {
               releaseKey(hotbarKey);
            }
         } else if (wasCloakActive) {
            client.options.setCameraType(CameraType.FIRST_PERSON);
         }

         wasCloakActive = cloakActive;
      });
   }

   private static void releaseKey(KeyMapping key) {
      key.setDown(false);

      while (key.consumeClick()) {
      }
   }

   private static boolean isCloakActive(Minecraft client) {
      return client.player != null && client.player.isSpectator() && client.getCameraEntity() instanceof UncontrolledJinchurikiEntity;
   }
}
