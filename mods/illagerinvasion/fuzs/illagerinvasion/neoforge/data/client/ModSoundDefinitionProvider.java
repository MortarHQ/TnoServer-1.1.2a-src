package fuzs.illagerinvasion.neoforge.data.client;

import fuzs.illagerinvasion.init.ModSoundEvents;
import fuzs.puzzleslib.neoforge.api.data.v2.client.AbstractSoundDefinitionProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;

public class ModSoundDefinitionProvider extends AbstractSoundDefinitionProvider {
   public ModSoundDefinitionProvider(NeoForgeDataProviderContext context) {
      super(context);
   }

   public void addSoundDefinitions() {
      this.add((SoundEvent)ModSoundEvents.HORN_OF_SIGHT_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/horn_of_sight/blow"))});
      this.add((SoundEvent)ModSoundEvents.LOST_CANDLE_COAL_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/lost_candle/coal"))});
      this.add((SoundEvent)ModSoundEvents.LOST_CANDLE_COPPER_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/lost_candle/copper"))});
      this.add((SoundEvent)ModSoundEvents.LOST_CANDLE_DIAMOND_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/lost_candle/diamond"))});
      this.add((SoundEvent)ModSoundEvents.LOST_CANDLE_GOLD_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/lost_candle/gold"))});
      this.add((SoundEvent)ModSoundEvents.LOST_CANDLE_IRON_SOUND_EVENT.value(), new Sound[]{sound(this.id("item/lost_candle/iron"))});
      this.add(
         (SoundEvent)ModSoundEvents.ARCHIVIST_DEATH_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/archivist/death1")), sound(this.id("mob/archivist/death2")), sound(this.id("mob/archivist/death3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.ARCHIVIST_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/archivist/hurt1")), sound(this.id("mob/archivist/hurt2")), sound(this.id("mob/archivist/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.ARCHIVIST_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/archivist/idle1")), sound(this.id("mob/archivist/idle2")), sound(this.id("mob/archivist/idle3"))}
      );
      this.add((SoundEvent)ModSoundEvents.BASHER_CELEBRATE_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/basher/celebrate"))});
      this.add((SoundEvent)ModSoundEvents.BASHER_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/basher/death"))});
      this.add(
         (SoundEvent)ModSoundEvents.BASHER_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/basher/hurt1")), sound(this.id("mob/basher/hurt2")), sound(this.id("mob/basher/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.BASHER_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/basher/idle1")), sound(this.id("mob/basher/idle2")), sound(this.id("mob/basher/idle3"))}
      );
      this.add((SoundEvent)ModSoundEvents.FIRECALLER_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/firecaller/cast"))});
      this.add(
         (SoundEvent)ModSoundEvents.FIRECALLER_DEATH_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/firecaller/death1")), sound(this.id("mob/firecaller/death2"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.FIRECALLER_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/firecaller/hurt1")), sound(this.id("mob/firecaller/hurt2")), sound(this.id("mob/firecaller/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.FIRECALLER_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{
            sound(this.id("mob/firecaller/idle1")),
            sound(this.id("mob/firecaller/idle2")),
            sound(this.id("mob/firecaller/idle3")),
            sound(this.id("mob/firecaller/idle4"))
         }
      );
      this.add((SoundEvent)ModSoundEvents.ILLAGER_BRUTE_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/illager_brute/death1"))});
      this.add(
         (SoundEvent)ModSoundEvents.ILLAGER_BRUTE_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/illager_brute/hurt1")), sound(this.id("mob/illager_brute/hurt2")), sound(this.id("mob/illager_brute/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.ILLAGER_BRUTE_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{
            sound(this.id("mob/illager_brute/idle1")),
            sound(this.id("mob/illager_brute/idle2")),
            sound(this.id("mob/illager_brute/idle3")),
            sound(this.id("mob/illager_brute/idle4")),
            sound(this.id("mob/illager_brute/idle5"))
         }
      );
      this.add((SoundEvent)ModSoundEvents.INVOKER_BIG_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/big_cast"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_COMPLETE_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/completecast"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/death"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_FANGS_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/fangs_cast"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_FANGS_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/fangs"))});
      this.add(
         (SoundEvent)ModSoundEvents.INVOKER_HURT_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/hurt1")), sound(this.id("mob/invoker/hurt2"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.INVOKER_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{
            sound(this.id("mob/invoker/idle1")), sound(this.id("mob/invoker/idle2")), sound(this.id("mob/invoker/idle3")), sound(this.id("mob/invoker/idle4"))
         }
      );
      this.add((SoundEvent)ModSoundEvents.INVOKER_SHIELD_BREAK_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/shield_break"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_SHIELD_CREATE_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/shield_create"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_SUMMON_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/summon_cast"))});
      this.add((SoundEvent)ModSoundEvents.INVOKER_TELEPORT_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/invoker/teleport_cast"))});
      this.add((SoundEvent)ModSoundEvents.NECROMANCER_SUMMON_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/necromancer/necroraise"))});
      this.add((SoundEvent)ModSoundEvents.PROVOKER_CELEBRATE_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/provoker/celebrate"))});
      this.add((SoundEvent)ModSoundEvents.PROVOKER_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/provoker/death"))});
      this.add(
         (SoundEvent)ModSoundEvents.PROVOKER_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/provoker/hurt1")), sound(this.id("mob/provoker/hurt2")), sound(this.id("mob/provoker/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.PROVOKER_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/provoker/idle1")), sound(this.id("mob/provoker/idle2")), sound(this.id("mob/provoker/idle3"))}
      );
      this.add((SoundEvent)ModSoundEvents.SORCERER_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/sorcerer/cast"))});
      this.add((SoundEvent)ModSoundEvents.SORCERER_CELEBRATE_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/sorcerer/celebrate"))});
      this.add((SoundEvent)ModSoundEvents.SORCERER_COMPLETE_CAST_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/sorcerer/completecast"))});
      this.add((SoundEvent)ModSoundEvents.SORCERER_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/sorcerer/death1"))});
      this.add(
         (SoundEvent)ModSoundEvents.SORCERER_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/sorcerer/hurt1")), sound(this.id("mob/sorcerer/hurt2")), sound(this.id("mob/sorcerer/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.SORCERER_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{
            sound(this.id("mob/sorcerer/idle1")),
            sound(this.id("mob/sorcerer/idle2")),
            sound(this.id("mob/sorcerer/idle3")),
            sound(this.id("mob/sorcerer/idle4"))
         }
      );
      this.add((SoundEvent)ModSoundEvents.SURRENDERED_CHARGE_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/surrendered/charge"))});
      this.add((SoundEvent)ModSoundEvents.SURRENDERED_DEATH_SOUND_EVENT.value(), new Sound[]{sound(this.id("mob/surrendered/death"))});
      this.add(
         (SoundEvent)ModSoundEvents.SURRENDERED_HURT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/surrendered/hurt1")), sound(this.id("mob/surrendered/hurt2")), sound(this.id("mob/surrendered/hurt3"))}
      );
      this.add(
         (SoundEvent)ModSoundEvents.SURRENDERED_AMBIENT_SOUND_EVENT.value(),
         new Sound[]{sound(this.id("mob/surrendered/ambient1")), sound(this.id("mob/surrendered/ambient2")), sound(this.id("mob/surrendered/ambient3"))}
      );
   }
}
