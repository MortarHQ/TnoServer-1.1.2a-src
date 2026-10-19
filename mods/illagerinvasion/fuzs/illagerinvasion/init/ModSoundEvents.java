package fuzs.illagerinvasion.init;

import net.minecraft.core.Holder.Reference;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
   public static final Reference<SoundEvent> HORN_OF_SIGHT_SOUND_EVENT = register("item.horn_of_sight.blow");
   public static final Reference<SoundEvent> LOST_CANDLE_DIAMOND_SOUND_EVENT = register("item.lost_candle.diamond");
   public static final Reference<SoundEvent> LOST_CANDLE_IRON_SOUND_EVENT = register("item.lost_candle.iron");
   public static final Reference<SoundEvent> LOST_CANDLE_COAL_SOUND_EVENT = register("item.lost_candle.coal");
   public static final Reference<SoundEvent> LOST_CANDLE_COPPER_SOUND_EVENT = register("item.lost_candle.copper");
   public static final Reference<SoundEvent> LOST_CANDLE_GOLD_SOUND_EVENT = register("item.lost_candle.gold");
   public static final Reference<SoundEvent> SURRENDERED_AMBIENT_SOUND_EVENT = register("entity.surrendered.ambient");
   public static final Reference<SoundEvent> SURRENDERED_HURT_SOUND_EVENT = register("entity.surrendered.hurt");
   public static final Reference<SoundEvent> SURRENDERED_CHARGE_SOUND_EVENT = register("entity.surrendered.charge");
   public static final Reference<SoundEvent> SURRENDERED_DEATH_SOUND_EVENT = register("entity.surrendered.death");
   public static final Reference<SoundEvent> NECROMANCER_SUMMON_SOUND_EVENT = register("entity.necromancer.summon");
   public static final Reference<SoundEvent> ARCHIVIST_AMBIENT_SOUND_EVENT = register("entity.archivist.ambient");
   public static final Reference<SoundEvent> ARCHIVIST_HURT_SOUND_EVENT = register("entity.archivist.hurt");
   public static final Reference<SoundEvent> ARCHIVIST_DEATH_SOUND_EVENT = register("entity.archivist.death");
   public static final Reference<SoundEvent> INVOKER_FANGS_SOUND_EVENT = register("entity.invoker.fangs");
   public static final Reference<SoundEvent> INVOKER_HURT_SOUND_EVENT = register("entity.invoker.hurt");
   public static final Reference<SoundEvent> INVOKER_DEATH_SOUND_EVENT = register("entity.invoker.death");
   public static final Reference<SoundEvent> INVOKER_AMBIENT_SOUND_EVENT = register("entity.invoker.ambient");
   public static final Reference<SoundEvent> INVOKER_COMPLETE_CAST_SOUND_EVENT = register("entity.invoker.completecast");
   public static final Reference<SoundEvent> INVOKER_TELEPORT_CAST_SOUND_EVENT = register("entity.invoker.teleport_cast");
   public static final Reference<SoundEvent> INVOKER_FANGS_CAST_SOUND_EVENT = register("entity.invoker.fangs_cast");
   public static final Reference<SoundEvent> INVOKER_BIG_CAST_SOUND_EVENT = register("entity.invoker.big_cast");
   public static final Reference<SoundEvent> INVOKER_SUMMON_CAST_SOUND_EVENT = register("entity.invoker.summon_cast");
   public static final Reference<SoundEvent> INVOKER_SHIELD_BREAK_SOUND_EVENT = register("entity.invoker.shield_break");
   public static final Reference<SoundEvent> INVOKER_SHIELD_CREATE_SOUND_EVENT = register("entity.invoker.shield_create");
   public static final Reference<SoundEvent> ILLAGER_BRUTE_AMBIENT_SOUND_EVENT = register("entity.illager_brute.ambient");
   public static final Reference<SoundEvent> ILLAGER_BRUTE_HURT_SOUND_EVENT = register("entity.illager_brute.hurt");
   public static final Reference<SoundEvent> ILLAGER_BRUTE_DEATH_SOUND_EVENT = register("entity.illager_brute.death");
   public static final Reference<SoundEvent> PROVOKER_AMBIENT_SOUND_EVENT = register("entity.provoker.idle");
   public static final Reference<SoundEvent> PROVOKER_HURT_SOUND_EVENT = register("entity.provoker.hurt");
   public static final Reference<SoundEvent> PROVOKER_DEATH_SOUND_EVENT = register("entity.provoker.death");
   public static final Reference<SoundEvent> PROVOKER_CELEBRATE_SOUND_EVENT = register("entity.provoker.celebrate");
   public static final Reference<SoundEvent> BASHER_AMBIENT_SOUND_EVENT = register("entity.basher.idle");
   public static final Reference<SoundEvent> BASHER_HURT_SOUND_EVENT = register("entity.basher.hurt");
   public static final Reference<SoundEvent> BASHER_DEATH_SOUND_EVENT = register("entity.basher.death");
   public static final Reference<SoundEvent> BASHER_CELEBRATE_SOUND_EVENT = register("entity.basher.celebrate");
   public static final Reference<SoundEvent> FIRECALLER_AMBIENT_SOUND_EVENT = register("entity.firecaller.idle");
   public static final Reference<SoundEvent> FIRECALLER_HURT_SOUND_EVENT = register("entity.firecaller.hurt");
   public static final Reference<SoundEvent> FIRECALLER_DEATH_SOUND_EVENT = register("entity.firecaller.death");
   public static final Reference<SoundEvent> FIRECALLER_CAST_SOUND_EVENT = register("entity.firecaller.cast");
   public static final Reference<SoundEvent> SORCERER_CAST_SOUND_EVENT = register("entity.sorcerer.cast");
   public static final Reference<SoundEvent> SORCERER_COMPLETE_CAST_SOUND_EVENT = register("entity.sorcerer.complete_cast");
   public static final Reference<SoundEvent> SORCERER_HURT_SOUND_EVENT = register("entity.sorcerer.hurt");
   public static final Reference<SoundEvent> SORCERER_DEATH_SOUND_EVENT = register("entity.sorcerer.death");
   public static final Reference<SoundEvent> SORCERER_AMBIENT_SOUND_EVENT = register("entity.sorcerer.idle");
   public static final Reference<SoundEvent> SORCERER_CELEBRATE_SOUND_EVENT = register("entity.sorcerer.celebrate");

   private static Reference<SoundEvent> register(String path) {
      return ModRegistry.REGISTRIES.registerSoundEvent(path);
   }

   public static void touch() {
   }
}
