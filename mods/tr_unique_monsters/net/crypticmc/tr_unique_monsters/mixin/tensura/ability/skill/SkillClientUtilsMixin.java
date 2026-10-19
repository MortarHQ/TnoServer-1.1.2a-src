package net.crypticmc.tr_unique_monsters.mixin.tensura.ability.skill;

import io.github.manasmods.tensura.ability.SkillClientUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SkillClientUtils.class, remap = false)
public class SkillClientUtilsMixin {
   private static final String UNIQUE_TAG = "tr_unique_monster";
   private static final ResourceLocation SCAN_RADIUS_ID = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "appraisal_eye_scan_radius");
   private static final ResourceLocation UNIQUE_MARKER = ResourceLocation.fromNamespaceAndPath("tr_unique_monsters", "unique_marker");
   private static final int RED = 16711680;

   @Inject(
      method = {
            "getGlowColor(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/entity/Entity;)I",
            "getGlowColor(Lnet/minecraft/class_1657;Lnet/minecraft/class_1297;)I"
      },
      at = @At("RETURN"),
      cancellable = true,
      remap = false,
      require = 0
   )
   private static void tr_uniqueMonsters$uniqueRed(Player player, Entity target, CallbackInfoReturnable<Integer> cir) {
      int current = (Integer)cir.getReturnValue();
      if (current != -1) {
         AttributeInstance radius = player.getAttribute(TensuraAttributes.PRESENCE_SENSE_RADIUS);
         if (radius != null && radius.hasModifier(SCAN_RADIUS_ID)) {
            if (target instanceof LivingEntity living) {
               boolean isUnique = false;
               AttributeInstance hp = living.getAttribute(Attributes.MAX_HEALTH);
               if (hp != null && hp.hasModifier(UNIQUE_MARKER)) {
                  isUnique = true;
               } else {
                  isUnique = living.getTags().contains("tr_unique_monster");
               }

               if (isUnique) {
                  cir.setReturnValue(16711680);
               }
            }
         }
      }
   }
}
