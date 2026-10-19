package xaero.hud.pushbox.effect;

import java.util.Collection;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import xaero.hud.pushbox.PushBox;

public class PotionEffectsPushBox extends PushBox implements IPotionEffectsPushBox {
   private boolean hasNegative;

   public PotionEffectsPushBox() {
      super(0, 0, 0, 0, 1.0F, 0.0F, 53);
   }

   @Override
   public int getX(int width, int height) {
      return super.getX(width, height) - this.getW(width, height);
   }

   @Override
   public void update() {
      super.update();
      this.hasNegative = false;
      this.w = calculatePotionDisplayWidth(this);
      this.h = this.hasNegative ? 53 : 27;
   }

   @Override
   public void postUpdate() {
      super.postUpdate();
      this.active = false;
   }

   protected static int calculatePotionDisplayWidth(IPotionEffectsPushBox potionEffectBox) {
      Minecraft mc = Minecraft.getInstance();
      Player player = mc.player;
      Collection<MobEffectInstance> collection = player.getActiveEffects();
      if (collection != null && !collection.isEmpty()) {
         int positiveCount = 0;
         int negativeCount = 0;

         for (MobEffectInstance effectInstance : collection) {
            if (effectInstance.showIcon()) {
               Holder<MobEffect> effect = effectInstance.getEffect();
               if (effect != null) {
                  if (!((MobEffect)effect.value()).isBeneficial()) {
                     potionEffectBox.setHasNegative(true);
                     negativeCount++;
                  } else {
                     positiveCount++;
                  }
               }
            }
         }

         return positiveCount + negativeCount == 0 ? 0 : Math.max(positiveCount, negativeCount) * 25 + 1;
      } else {
         return 0;
      }
   }

   @Override
   public void setHasNegative(boolean b) {
      this.hasNegative = b;
   }
}
