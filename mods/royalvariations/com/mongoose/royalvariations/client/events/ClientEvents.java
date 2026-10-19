package com.mongoose.royalvariations.client.events;

import com.mongoose.royalvariations.client.audio.ItemLoopSound;
import com.mongoose.royalvariations.client.render.WearRenderer;
import com.mongoose.royalvariations.common.effects.RVBaseEffect;
import com.mongoose.royalvariations.common.items.GloveItem;
import com.mongoose.royalvariations.common.items.RVItems;
import java.util.Optional;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderArmEvent;
import net.neoforged.neoforge.event.entity.living.EffectParticleModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Start;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;

@EventBusSubscriber(modid = "royalvariations", value = Dist.CLIENT)
public class ClientEvents {
   @SubscribeEvent
   public static void onItemUse(Start event) {
      if (event.getEntity().level() instanceof ClientLevel) {
         Minecraft minecraft = Minecraft.getInstance();
         SoundManager soundHandler = minecraft.getSoundManager();
         if (event.getItem().is((Item)RVItems.ROYAL_TELELOCATOR.get())) {
            soundHandler.play(new ItemLoopSound(SoundEvents.PORTAL_TRIGGER, event.getEntity()));
         }
      }
   }

   @SubscribeEvent
   public static void renderGlove(RenderArmEvent event) {
      if (!event.isCanceled()) {
         Optional<SlotResult> slotResult = CuriosApi.getCuriosInventory(event.getPlayer())
            .map(inv -> inv.findFirstCurio(itemStackx -> itemStackx.getItem() instanceof GloveItem))
            .orElse(Optional.empty());
         if (slotResult.isPresent()) {
            ItemStack itemStack = slotResult.get().stack();
            if (slotResult.get().slotContext().visible()) {
               WearRenderer renderer = WearRenderer.getRenderer(itemStack);
               if (renderer != null) {
                  renderer.renderFirstPersonArm(
                     event.getPoseStack(), event.getMultiBufferSource(), event.getPackedLight(), event.getPlayer(), event.getArm(), itemStack.hasFoil()
                  );
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void EffectParticleEvent(EffectParticleModificationEvent event) {
      if (event.getEffect().getEffect().value() instanceof RVBaseEffect effect) {
         event.setVisible(effect.isVisible());
      }
   }
}
