package com.github.b4ndithelps.tennogamenolife.event;

import com.github.b4ndithelps.tennogamenolife.entity.DragonRoarCircle;
import com.github.b4ndithelps.tennogamenolife.registry.skill.TenreincarnationSkill;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.TickingSkill;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.PlayerTickEvent.Post;

@EventBusSubscriber(modid = "tennogamenolife")
public final class DragonRoarChargeLockEvents {
   private DragonRoarChargeLockEvents() {
   }

   @SubscribeEvent
   public static void onPlayerTick(Post event) {
      Player player = event.getEntity();
      if (!player.level().isClientSide()) {
         clearStaleLock(
            player, (ManasSkill)TenreincarnationSkill.FAR_CRY.get(), 0, "FarCryLocked", "FarCryChargeTicks", "FarCryCircleID", "FarCryX", "FarCryY", "FarCryZ"
         );
         clearStaleLock(
            player,
            (ManasSkill)TenreincarnationSkill.GODLY_SMITE.get(),
            0,
            "GodlySmiteLocked",
            "GodlySmiteChargeTicks",
            "GodlySmiteVfxId",
            "GodlySmiteX",
            "GodlySmiteY",
            "GodlySmiteZ"
         );
         clearStaleLock(
            player,
            (ManasSkill)TenreincarnationSkill.REISEN.get(),
            1,
            "DragonRoarLocked",
            "chargeTime",
            "DragonRoarCircleID",
            "dragonRoarX",
            "dragonRoarY",
            "dragonRoarZ"
         );
      }
   }

   private static void clearStaleLock(
      Player player, ManasSkill skill, int mode, String lockedKey, String chargeKey, String circleIdKey, String xKey, String yKey, String zKey
   ) {
      ManasSkillInstance instance = (ManasSkillInstance)SkillAPI.getSkillsFrom(player).getSkill(skill).orElse(null);
      if (instance != null) {
         CompoundTag tag = instance.getTag();
         if (tag != null && tag.getBoolean(lockedKey) && !TickingSkill.isTickingSkill(player, skill, mode)) {
            if (player.level().getEntity(tag.getInt(circleIdKey)) instanceof DragonRoarCircle circle) {
               circle.setLife(10);
            }

            tag.putBoolean(lockedKey, false);
            tag.putInt(chargeKey, 0);
            tag.putInt(circleIdKey, 0);
            tag.putDouble(xKey, 0.0);
            tag.putDouble(yKey, 0.0);
            tag.putDouble(zKey, 0.0);
            instance.markDirty();
         }
      }
   }
}
