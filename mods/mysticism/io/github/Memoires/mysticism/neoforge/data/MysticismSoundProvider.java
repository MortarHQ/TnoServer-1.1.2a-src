package io.github.Memoires.mysticism.neoforge.data;

import io.github.Memoires.mysticism.registry.sound.MysticismSoundEvents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;
import net.neoforged.neoforge.common.data.SoundDefinition.Sound;
import net.neoforged.neoforge.common.data.SoundDefinition.SoundType;

public class MysticismSoundProvider extends SoundDefinitionsProvider {
   public MysticismSoundProvider(PackOutput output, ExistingFileHelper helper) {
      super(output, "mysticism", helper);
   }

   private ResourceLocation location(String location) {
      return ResourceLocation.fromNamespaceAndPath("mysticism", location);
   }

   public static String getSubtitle(SoundEvent event) {
      ResourceLocation location = event.getLocation();
      String modId = location.getNamespace();
      return "subtitles." + modId + "." + location.getPath();
   }

   protected void addWithSubtitle(SoundEvent soundEvent, SoundDefinition definition) {
      super.add(soundEvent, definition.subtitle(getSubtitle(soundEvent)));
   }

   public void registerSounds() {
      this.registerAbilities();
   }

   public void registerAbilities() {
      this.addWithSubtitle((SoundEvent)MysticismSoundEvents.COUNTER.get(), definition().with(defaultSound(this.location("skill/counter")).volume(1.0F)));
      this.addWithSubtitle((SoundEvent)MysticismSoundEvents.BULLET.get(), definition().with(defaultSound(this.location("skill/bullet")).volume(1.0F)));
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.BULLET_PUNCH.get(), definition().with(defaultSound(this.location("skill/bullet_punch")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.BUTCHER_GUI_CONFIRM.get(), definition().with(defaultSound(this.location("skill/butcher_gui_confirm")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.BUTCHER_STAB.get(), definition().with(defaultSound(this.location("skill/butcher_stab")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.CLANGOROUS_SOUL.get(), definition().with(defaultSound(this.location("skill/clangorous_soul")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.EYE_OF_INSIGHT.get(), definition().with(defaultSound(this.location("skill/eye_of_insight")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.LIGHTNING_MODE.get(), definition().with(defaultSound(this.location("skill/lightning_mode")).volume(1.0F))
      );
      this.addWithSubtitle(
         (SoundEvent)MysticismSoundEvents.SCYTHE_SLASH.get(), definition().with(defaultSound(this.location("skill/scythe_slash")).volume(1.0F))
      );
   }

   protected static Sound defaultSound(ResourceLocation name) {
      return sound(name, SoundType.SOUND);
   }
}
