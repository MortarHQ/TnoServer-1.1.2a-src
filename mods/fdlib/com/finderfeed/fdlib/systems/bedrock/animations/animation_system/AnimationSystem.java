package com.finderfeed.fdlib.systems.bedrock.animations.animation_system;

import com.finderfeed.fdlib.data_structures.Pair;
import com.finderfeed.fdlib.systems.bedrock.animations.Animation;
import com.finderfeed.fdlib.systems.bedrock.animations.AnimationContext;
import com.finderfeed.fdlib.systems.bedrock.models.FDModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.BiConsumer;

public abstract class AnimationSystem {
   private HashMap<String, AnimationTicker> tickers = new HashMap<>();
   private HashMap<String, Float> variables = new HashMap<>();
   private boolean isFrozen;
   private BiConsumer<FDModel, Float> animationsApplyListener;

   public void tick() {
      if (!this.isFrozen()) {
         Iterator<Entry<String, AnimationTicker>> entryIterator = this.tickers.entrySet().iterator();
         List<Pair<String, AnimationTicker>> nextAnimations = new ArrayList<>();

         while (entryIterator.hasNext()) {
            Entry<String, AnimationTicker> entry = entryIterator.next();
            AnimationTicker ticker = entry.getValue();
            Animation animation = ticker.getAnimation();
            if (ticker.hasEnded()) {
               Animation.LoopMode mode = ticker.getLoopMode();
               if (animation.isToNullTransition()) {
                  entryIterator.remove();
                  continue;
               }

               AnimationContext context = new AnimationContext(animation, mode);
               context.variables = this.variables;
               ticker.addVariables(context, 0.0F);
               switch (mode) {
                  case ONCE:
                     if (ticker.getNext() == null) {
                        int toNullTime = ticker.getToNullTransitionTime();
                        if (toNullTime != 0) {
                           Animation nullTransition = animation.createTransitionTo(context, null, ticker.getTime(0.0F), ticker.getToNullTransitionTime(), false);
                           ticker.resetTime();
                           ticker.setAnimation(nullTransition);
                           break;
                        } else {
                           entryIterator.remove();
                        }
                     } else {
                        nextAnimations.add(new Pair<>(entry.getKey(), ticker.getNext()));
                        break;
                     }
                  case HOLD_ON_LAST_FRAME:
                     continue;
               }
            }

            ticker.tick();
         }

         for (Pair<String, AnimationTicker> pair : nextAnimations) {
            this.startAnimation(pair.first, pair.second);
         }
      }
   }

   public void applyAnimations(FDModel model, float partialTicks) {
      model.resetTransformations();
      if (this.animationsApplyListener != null) {
         this.animationsApplyListener.accept(model, partialTicks);
      }

      for (AnimationTicker entry : this.tickers.values()) {
         Animation animation = entry.getAnimation();
         AnimationContext context = new AnimationContext(animation, entry.getLoopMode());
         context.variables = this.variables;
         entry.addVariables(context, partialTicks);
         animation.applyAnimation(context, model, entry.getTime(partialTicks));
      }
   }

   public void setAnimationsApplyListener(BiConsumer<FDModel, Float> animationsApplyListener) {
      this.animationsApplyListener = animationsApplyListener;
   }

   public void setFrozen(boolean frozen) {
      this.isFrozen = frozen;
      this.onFreeze(frozen);
   }

   public boolean isFrozen() {
      return this.isFrozen;
   }

   public void startAnimation(String name, AnimationTicker ticker) {
      AnimationTicker current = this.getTicker(name);
      if (current == null) {
         this.tickers.put(name, ticker);
      } else {
         if (ticker.equals(current) && !ticker.isImportant()) {
            return;
         }

         Animation anim = current.getAnimation();
         AnimationContext context = new AnimationContext(anim, ticker.getLoopMode());
         context.variables = this.variables;
         current.addVariables(context, 0.0F);
         Animation transition = current.getAnimation()
            .createTransitionTo(context, ticker.getAnimation(), current.getTime(0.0F), ticker.getToNullTransitionTime(), ticker.isReversed());
         AnimationTicker newTicker = new AnimationTicker(ticker);
         newTicker.setAnimation(transition);
         newTicker.resetTime();
         this.tickers.put(name, newTicker);
      }

      this.onAnimationStart(name, ticker);
   }

   public void stopAnimation(String name) {
      AnimationTicker ticker = this.getTicker(name);
      if (ticker != null) {
         Animation animation = ticker.getAnimation();
         if (!animation.isToNullTransition()) {
            if (ticker.getToNullTransitionTime() != 0) {
               AnimationContext context = new AnimationContext(animation, Animation.LoopMode.ONCE);
               context.variables = this.variables;
               ticker.addVariables(context, 0.0F);
               Animation toNull = animation.createTransitionTo(context, null, ticker.getTime(0.0F), ticker.getToNullTransitionTime(), false);
               AnimationTicker newTicker = new AnimationTicker(ticker);
               newTicker.setLoopMode(Animation.LoopMode.ONCE);
               newTicker.setAnimation(toNull);
               newTicker.resetTime();
               this.tickers.put(name, newTicker);
            } else {
               this.tickers.remove(name);
            }
         }
      }

      this.onAnimationStop(name);
   }

   public void setVariable(String name, float variable) {
      this.variables.put(name, variable);
      this.onVariableAdded(name, variable);
   }

   public float getVariable(String name) {
      return this.variables.get(name);
   }

   public AnimationTicker getTicker(String name) {
      return this.tickers.get(name);
   }

   public Animation getTickerAnimation(String ticker) {
      return this.tickers.containsKey(ticker) ? this.getTicker(ticker).getAnimation() : null;
   }

   public HashMap<String, AnimationTicker> getTickers() {
      return this.tickers;
   }

   public abstract void onAnimationStart(String var1, AnimationTicker var2);

   public abstract void onAnimationStop(String var1);

   public abstract void onFreeze(boolean var1);

   public abstract void onVariableAdded(String var1, float var2);
}
