package net.regions_unexplored.sound;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.SoundType;
import net.regions_unexplored.registry.RUSoundEvents;

public record WoodSoundSet(
   SoundEvent buttonOff,
   SoundEvent buttonOn,
   SoundEvent doorClose,
   SoundEvent doorOpen,
   SoundEvent fenceGateClose,
   SoundEvent fenceGateOpen,
   SoundEvent hangingSignPlace,
   SoundEvent hangingSignStep,
   SoundEvent trapdoorClose,
   SoundEvent trapdoorOpen,
   SoundEvent basePlace,
   SoundEvent baseStep,
   SoundEvent pressurePlateOff,
   SoundEvent pressurePlateOn
) {
   public static WoodSoundSet create(String name) {
      return new WoodSoundSet(
         RUSoundEvents.register(id(name, ".button_click_off")),
         RUSoundEvents.register(id(name, ".button_click_on")),
         RUSoundEvents.register(id(name, ".door_close")),
         RUSoundEvents.register(id(name, ".door_open")),
         RUSoundEvents.register(id(name, ".fence_gate_close")),
         RUSoundEvents.register(id(name, ".fence_gate_open")),
         RUSoundEvents.register(id(name, ".hanging_sign_place")),
         RUSoundEvents.register(id(name, ".hanging_sign_step")),
         RUSoundEvents.register(id(name, ".trapdoor_close")),
         RUSoundEvents.register(id(name, ".trapdoor_open")),
         RUSoundEvents.register(id(name, ".place")),
         RUSoundEvents.register(id(name, ".step")),
         RUSoundEvents.register(id(name, ".pressure_plate_click_off")),
         RUSoundEvents.register(id(name, ".pressure_plate_click_on"))
      );
   }

   private static String id(String baseName, String suffix) {
      return "block." + baseName + suffix;
   }

   public SoundType baseType() {
      return new SoundType(1.0F, 1.0F, this.basePlace, this.baseStep, this.basePlace, this.baseStep, this.baseStep);
   }

   public SoundType hangingSignType() {
      return new SoundType(1.0F, 1.0F, this.hangingSignPlace, this.hangingSignStep, this.hangingSignPlace, this.hangingSignStep, this.hangingSignStep);
   }
}
