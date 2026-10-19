package com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item;

import com.finderfeed.fdlib.systems.bedrock.animations.animation_system.item.animated_item.AnimatedItemStackContext;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;

@EventBusSubscriber(modid = "fdlib")
public class FDItemAnimationHandler {
   public static AnimatedItemStackContext currentRenderedContext = null;
   private static final HashMap<AnimatedItemStackContext, FDItemAnimationHandler.AnimatedItemContainer> ANIMATED_ITEMS = new HashMap<>();

   @SubscribeEvent
   public static void tickItems(Pre event) {
      tickAnimatedItems();
   }

   public static FDItemAnimationSystem getItemAnimationSystem(AnimatedItemStackContext itemStack) {
      if (ANIMATED_ITEMS.containsKey(itemStack)) {
         FDItemAnimationHandler.AnimatedItemContainer animatedItem = ANIMATED_ITEMS.get(itemStack);
         return animatedItem.animationSystem;
      } else {
         return null;
      }
   }

   public static void tickAnimatedItems() {
      Iterator<Entry<AnimatedItemStackContext, FDItemAnimationHandler.AnimatedItemContainer>> iterator = ANIMATED_ITEMS.entrySet().iterator();

      while (iterator.hasNext()) {
         Entry<AnimatedItemStackContext, FDItemAnimationHandler.AnimatedItemContainer> item = iterator.next();
         FDItemAnimationHandler.AnimatedItemContainer animatedItem = item.getValue();
         animatedItem.tick();
         if (animatedItem.shouldBeRemoved()) {
            iterator.remove();
         }
      }
   }

   public static void tellIAmCurrentlyRendering(AnimatedItemStackContext ctx, boolean setCurrentRenderingContext) {
      if (ANIMATED_ITEMS.containsKey(ctx)) {
         FDItemAnimationHandler.AnimatedItemContainer item = ANIMATED_ITEMS.get(ctx);
         item.tellThatIAmAlive();
      } else {
         ANIMATED_ITEMS.put(ctx, new FDItemAnimationHandler.AnimatedItemContainer(ctx));
      }

      if (setCurrentRenderingContext) {
         currentRenderedContext = ctx;
      }
   }

   private static class AnimatedItemContainer {
      public FDItemAnimationSystem animationSystem;
      public AnimatedItemStackContext itemStack;
      public int age;

      public AnimatedItemContainer(AnimatedItemStackContext itemStack) {
         this.itemStack = itemStack;
         this.animationSystem = new FDItemAnimationSystem();
      }

      public void tick() {
         if (this.itemStack.getItemStack().getItem() instanceof AnimatedItem animatedItemTickListener) {
            animatedItemTickListener.animatedItemTick(this.itemStack);
         }

         this.animationSystem.tick();
         this.age++;
      }

      public boolean shouldBeRemoved() {
         return this.age > 200 || this.itemStack.getItemStack().isEmpty();
      }

      public void tellThatIAmAlive() {
         this.age = 0;
      }
   }
}
