package artifacts.registry;

import artifacts.Artifacts;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;

public class ModSoundEvents {
   public static final Register<SoundEvent> SOUND_EVENTS = Register.create(Registries.SOUND_EVENT);
   public static final Holder<SoundEvent> POP = register("generic.pop");
   public static final Holder<SoundEvent> MIMIC_HURT = register("entity.mimic.hurt");
   public static final Holder<SoundEvent> MIMIC_DEATH = register("entity.mimic.death");
   public static final Holder<SoundEvent> MIMIC_OPEN = register("entity.mimic.open");
   public static final Holder<SoundEvent> MIMIC_CLOSE = register("entity.mimic.close");
   public static final Holder<SoundEvent> FART = register("item.whoopee_cushion.fart");
   public static final Holder<SoundEvent> WATER_STEP = register("block.water.step");

   private static Holder<SoundEvent> register(String name) {
      return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(Artifacts.id(name)));
   }
}
