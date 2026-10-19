package it.hurts.octostudios.immersiveui;

import it.hurts.octostudios.octolib.module.config.annotation.Prop;
import it.hurts.octostudios.octolib.module.config.impl.OctoConfig;

public class Config implements OctoConfig {
   @Prop(comment = "Enables the hotbar selector animation.")
   private boolean enableHotbarSelectorAnimation = true;
   @Prop(comment = "Enables floating item rotation.")
   private boolean enableFloatingItemRotation = true;
   @Prop(comment = "Enables hovering of matching items.")
   private boolean enableMatchingItemHovering = true;
   @Prop(comment = "Enables vanilla slot highlighting")
   private boolean enableVanillaSlotHighlighting = true;
   @Prop(comment = "Enables particles for rare items.")
   private boolean enableRarityParticles = true;
   @Prop(comment = "Affects the speed of the hotbar selector.")
   private double hotbarSelectorSpeed = 3.0;
   @Prop(comment = "Affects the size of the hovered item.")
   private float hoveredItemScale = 1.4F;
   @Prop(comment = "Affects the size of the floating item.")
   private float floatingItemScale = 1.4F;
   @Prop(comment = "Affects the hover amplitude of items, that match to the item that is carried in the cursor.")
   private float matchingItemHoverAmplitude = 0.8F;
   @Prop(comment = "Affects the easing speed, that is applied to the rotation of the floating item.")
   private float floatingItemEasingSpeed = 0.75F;
   @Prop(comment = "Affects the rotation amplitude of the floating item.")
   private float floatingItemRotationAmplitude = 1.0F;
   @Prop(
      comment = "Moves the hotbar selector above the items. You might want to disable this, if you use resource packs that change the default selector texture."
   )
   private boolean renderHotbarSelectorAboveItems = false;
   @Prop(comment = "Enables screen shake.")
   private boolean enableScreenShake = true;
   @Prop(comment = "Screen shake timer in ticks.")
   private int shakeTimer = 8;
   @Prop(comment = "Screen shake amplitude.")
   private float shakeAmplitude = 1.25F;
   @Prop(comment = "Enables particles in the enchant(ing/ment) table.")
   private boolean enableEnchantParticles = true;
   @Prop(comment = "Enables special formatting for items with cursed enchantments.")
   private boolean enableCurseFormatting = true;
   @Prop(comment = "Enables wobbly items in advancement toasts.")
   private boolean enableAdvancementToastItems = true;

   public boolean isEnableHotbarSelectorAnimation() {
      return this.enableHotbarSelectorAnimation;
   }

   public boolean isEnableFloatingItemRotation() {
      return this.enableFloatingItemRotation;
   }

   public boolean isEnableMatchingItemHovering() {
      return this.enableMatchingItemHovering;
   }

   public boolean isEnableVanillaSlotHighlighting() {
      return this.enableVanillaSlotHighlighting;
   }

   public boolean isEnableRarityParticles() {
      return this.enableRarityParticles;
   }

   public double getHotbarSelectorSpeed() {
      return this.hotbarSelectorSpeed;
   }

   public float getHoveredItemScale() {
      return this.hoveredItemScale;
   }

   public float getFloatingItemScale() {
      return this.floatingItemScale;
   }

   public float getMatchingItemHoverAmplitude() {
      return this.matchingItemHoverAmplitude;
   }

   public float getFloatingItemEasingSpeed() {
      return this.floatingItemEasingSpeed;
   }

   public float getFloatingItemRotationAmplitude() {
      return this.floatingItemRotationAmplitude;
   }

   public boolean isRenderHotbarSelectorAboveItems() {
      return this.renderHotbarSelectorAboveItems;
   }

   public boolean isEnableScreenShake() {
      return this.enableScreenShake;
   }

   public int getShakeTimer() {
      return this.shakeTimer;
   }

   public float getShakeAmplitude() {
      return this.shakeAmplitude;
   }

   public boolean isEnableEnchantParticles() {
      return this.enableEnchantParticles;
   }

   public boolean isEnableCurseFormatting() {
      return this.enableCurseFormatting;
   }

   public boolean isEnableAdvancementToastItems() {
      return this.enableAdvancementToastItems;
   }

   public void setEnableHotbarSelectorAnimation(boolean enableHotbarSelectorAnimation) {
      this.enableHotbarSelectorAnimation = enableHotbarSelectorAnimation;
   }

   public void setEnableFloatingItemRotation(boolean enableFloatingItemRotation) {
      this.enableFloatingItemRotation = enableFloatingItemRotation;
   }

   public void setEnableMatchingItemHovering(boolean enableMatchingItemHovering) {
      this.enableMatchingItemHovering = enableMatchingItemHovering;
   }

   public void setEnableVanillaSlotHighlighting(boolean enableVanillaSlotHighlighting) {
      this.enableVanillaSlotHighlighting = enableVanillaSlotHighlighting;
   }

   public void setEnableRarityParticles(boolean enableRarityParticles) {
      this.enableRarityParticles = enableRarityParticles;
   }

   public void setHotbarSelectorSpeed(double hotbarSelectorSpeed) {
      this.hotbarSelectorSpeed = hotbarSelectorSpeed;
   }

   public void setHoveredItemScale(float hoveredItemScale) {
      this.hoveredItemScale = hoveredItemScale;
   }

   public void setFloatingItemScale(float floatingItemScale) {
      this.floatingItemScale = floatingItemScale;
   }

   public void setMatchingItemHoverAmplitude(float matchingItemHoverAmplitude) {
      this.matchingItemHoverAmplitude = matchingItemHoverAmplitude;
   }

   public void setFloatingItemEasingSpeed(float floatingItemEasingSpeed) {
      this.floatingItemEasingSpeed = floatingItemEasingSpeed;
   }

   public void setFloatingItemRotationAmplitude(float floatingItemRotationAmplitude) {
      this.floatingItemRotationAmplitude = floatingItemRotationAmplitude;
   }

   public void setRenderHotbarSelectorAboveItems(boolean renderHotbarSelectorAboveItems) {
      this.renderHotbarSelectorAboveItems = renderHotbarSelectorAboveItems;
   }

   public void setEnableScreenShake(boolean enableScreenShake) {
      this.enableScreenShake = enableScreenShake;
   }

   public void setShakeTimer(int shakeTimer) {
      this.shakeTimer = shakeTimer;
   }

   public void setShakeAmplitude(float shakeAmplitude) {
      this.shakeAmplitude = shakeAmplitude;
   }

   public void setEnableEnchantParticles(boolean enableEnchantParticles) {
      this.enableEnchantParticles = enableEnchantParticles;
   }

   public void setEnableCurseFormatting(boolean enableCurseFormatting) {
      this.enableCurseFormatting = enableCurseFormatting;
   }

   public void setEnableAdvancementToastItems(boolean enableAdvancementToastItems) {
      this.enableAdvancementToastItems = enableAdvancementToastItems;
   }

   @Override
   public boolean equals(Object o) {
      if (o == this) {
         return true;
      } else if (!(o instanceof Config other)) {
         return false;
      } else if (!other.canEqual(this)) {
         return false;
      } else if (this.isEnableHotbarSelectorAnimation() != other.isEnableHotbarSelectorAnimation()) {
         return false;
      } else if (this.isEnableFloatingItemRotation() != other.isEnableFloatingItemRotation()) {
         return false;
      } else if (this.isEnableMatchingItemHovering() != other.isEnableMatchingItemHovering()) {
         return false;
      } else if (this.isEnableVanillaSlotHighlighting() != other.isEnableVanillaSlotHighlighting()) {
         return false;
      } else if (this.isEnableRarityParticles() != other.isEnableRarityParticles()) {
         return false;
      } else if (Double.compare(this.getHotbarSelectorSpeed(), other.getHotbarSelectorSpeed()) != 0) {
         return false;
      } else if (Float.compare(this.getHoveredItemScale(), other.getHoveredItemScale()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatingItemScale(), other.getFloatingItemScale()) != 0) {
         return false;
      } else if (Float.compare(this.getMatchingItemHoverAmplitude(), other.getMatchingItemHoverAmplitude()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatingItemEasingSpeed(), other.getFloatingItemEasingSpeed()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatingItemRotationAmplitude(), other.getFloatingItemRotationAmplitude()) != 0) {
         return false;
      } else if (this.isRenderHotbarSelectorAboveItems() != other.isRenderHotbarSelectorAboveItems()) {
         return false;
      } else if (this.isEnableScreenShake() != other.isEnableScreenShake()) {
         return false;
      } else if (this.getShakeTimer() != other.getShakeTimer()) {
         return false;
      } else if (Float.compare(this.getShakeAmplitude(), other.getShakeAmplitude()) != 0) {
         return false;
      } else if (this.isEnableEnchantParticles() != other.isEnableEnchantParticles()) {
         return false;
      } else {
         return this.isEnableCurseFormatting() != other.isEnableCurseFormatting()
            ? false
            : this.isEnableAdvancementToastItems() == other.isEnableAdvancementToastItems();
      }
   }

   protected boolean canEqual(Object other) {
      return other instanceof Config;
   }

   @Override
   public int hashCode() {
      int PRIME = 59;
      int result = 1;
      result = result * 59 + (this.isEnableHotbarSelectorAnimation() ? 79 : 97);
      result = result * 59 + (this.isEnableFloatingItemRotation() ? 79 : 97);
      result = result * 59 + (this.isEnableMatchingItemHovering() ? 79 : 97);
      result = result * 59 + (this.isEnableVanillaSlotHighlighting() ? 79 : 97);
      result = result * 59 + (this.isEnableRarityParticles() ? 79 : 97);
      long $hotbarSelectorSpeed = Double.doubleToLongBits(this.getHotbarSelectorSpeed());
      result = result * 59 + (int)($hotbarSelectorSpeed >>> 32 ^ $hotbarSelectorSpeed);
      result = result * 59 + Float.floatToIntBits(this.getHoveredItemScale());
      result = result * 59 + Float.floatToIntBits(this.getFloatingItemScale());
      result = result * 59 + Float.floatToIntBits(this.getMatchingItemHoverAmplitude());
      result = result * 59 + Float.floatToIntBits(this.getFloatingItemEasingSpeed());
      result = result * 59 + Float.floatToIntBits(this.getFloatingItemRotationAmplitude());
      result = result * 59 + (this.isRenderHotbarSelectorAboveItems() ? 79 : 97);
      result = result * 59 + (this.isEnableScreenShake() ? 79 : 97);
      result = result * 59 + this.getShakeTimer();
      result = result * 59 + Float.floatToIntBits(this.getShakeAmplitude());
      result = result * 59 + (this.isEnableEnchantParticles() ? 79 : 97);
      result = result * 59 + (this.isEnableCurseFormatting() ? 79 : 97);
      return result * 59 + (this.isEnableAdvancementToastItems() ? 79 : 97);
   }

   @Override
   public String toString() {
      return "Config(enableHotbarSelectorAnimation="
         + this.isEnableHotbarSelectorAnimation()
         + ", enableFloatingItemRotation="
         + this.isEnableFloatingItemRotation()
         + ", enableMatchingItemHovering="
         + this.isEnableMatchingItemHovering()
         + ", enableVanillaSlotHighlighting="
         + this.isEnableVanillaSlotHighlighting()
         + ", enableRarityParticles="
         + this.isEnableRarityParticles()
         + ", hotbarSelectorSpeed="
         + this.getHotbarSelectorSpeed()
         + ", hoveredItemScale="
         + this.getHoveredItemScale()
         + ", floatingItemScale="
         + this.getFloatingItemScale()
         + ", matchingItemHoverAmplitude="
         + this.getMatchingItemHoverAmplitude()
         + ", floatingItemEasingSpeed="
         + this.getFloatingItemEasingSpeed()
         + ", floatingItemRotationAmplitude="
         + this.getFloatingItemRotationAmplitude()
         + ", renderHotbarSelectorAboveItems="
         + this.isRenderHotbarSelectorAboveItems()
         + ", enableScreenShake="
         + this.isEnableScreenShake()
         + ", shakeTimer="
         + this.getShakeTimer()
         + ", shakeAmplitude="
         + this.getShakeAmplitude()
         + ", enableEnchantParticles="
         + this.isEnableEnchantParticles()
         + ", enableCurseFormatting="
         + this.isEnableCurseFormatting()
         + ", enableAdvancementToastItems="
         + this.isEnableAdvancementToastItems()
         + ")";
   }
}
