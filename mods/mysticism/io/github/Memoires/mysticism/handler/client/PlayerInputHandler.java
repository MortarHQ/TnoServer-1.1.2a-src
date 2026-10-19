package io.github.Memoires.mysticism.handler.client;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientRawInputEvent;
import dev.architectury.event.events.client.ClientScreenInputEvent;
import dev.architectury.event.events.client.ClientRawInputEvent.KeyPressed;
import dev.architectury.event.events.client.ClientRawInputEvent.MouseClicked;
import dev.architectury.event.events.client.ClientRawInputEvent.MouseScrolled;
import dev.architectury.event.events.client.ClientScreenInputEvent.KeyTyped;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;

public class PlayerInputHandler {
   public static void init() {
      ClientRawInputEvent.KEY_PRESSED.register((KeyPressed)(client, keyCode, scanCode, action, modifiers) -> {
         if (client.player == null) {
            return EventResult.pass();
         } else if (keyCode == 256) {
            return EventResult.pass();
         } else {
            return client.player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION)) ? EventResult.interruptFalse() : EventResult.pass();
         }
      });
      ClientRawInputEvent.MOUSE_CLICKED_PRE.register((MouseClicked)(client, button, action, mods) -> {
         if (client.player == null) {
            return EventResult.pass();
         } else {
            return client.player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION)) ? EventResult.interruptFalse() : EventResult.pass();
         }
      });
      ClientRawInputEvent.MOUSE_SCROLLED.register((MouseScrolled)(client, amountX, amountY) -> {
         if (client.player == null) {
            return EventResult.pass();
         } else {
            return client.player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION)) ? EventResult.interruptFalse() : EventResult.pass();
         }
      });
      ClientScreenInputEvent.CHAR_TYPED_PRE.register((KeyTyped)(client, screen, character, keyCode) -> {
         if (client.player == null) {
            return EventResult.pass();
         } else if (screen == null) {
            return EventResult.pass();
         } else {
            return client.player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION)) ? EventResult.interruptFalse() : EventResult.pass();
         }
      });
      ClientScreenInputEvent.MOUSE_SCROLLED_PRE
         .register((dev.architectury.event.events.client.ClientScreenInputEvent.MouseScrolled)(client, screen, mouseX, mouseY, amountX, amountY) -> {
            if (client.player == null) {
               return EventResult.pass();
            } else if (screen == null) {
               return EventResult.pass();
            } else {
               return client.player.hasEffect(TensuraMobEffects.getReference(MysticismMobEffects.FIXATION)) ? EventResult.interruptFalse() : EventResult.pass();
            }
         });
   }
}
