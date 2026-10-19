package net.blay09.mods.trashslot.client;

import net.blay09.mods.kuma.api.InputBinding;
import net.blay09.mods.kuma.api.KeyConflictContext;
import net.blay09.mods.kuma.api.KeyModifier;
import net.blay09.mods.kuma.api.KeyModifiers;
import net.blay09.mods.kuma.api.Kuma;
import net.blay09.mods.kuma.api.ManagedKeyMapping;
import net.minecraft.resources.ResourceLocation;

public class ModKeyMappings {
   public static ManagedKeyMapping keyBindToggleSlot;
   public static ManagedKeyMapping keyBindToggleSlotLock;
   public static ManagedKeyMapping keyBindDelete;
   public static ManagedKeyMapping keyBindDeleteAll;

   public static void initialize() {
      keyBindToggleSlot = Kuma.createKeyMapping(ResourceLocation.fromNamespaceAndPath("trashslot", "toggle"))
         .withDefault(InputBinding.key(84))
         .withContext(KeyConflictContext.SCREEN)
         .build();
      keyBindToggleSlotLock = Kuma.createKeyMapping(ResourceLocation.fromNamespaceAndPath("trashslot", "toggle_lock"))
         .withContext(KeyConflictContext.SCREEN)
         .build();
      keyBindDelete = Kuma.createKeyMapping(ResourceLocation.fromNamespaceAndPath("trashslot", "delete"))
         .withDefault(InputBinding.key(261))
         .withContext(KeyConflictContext.SCREEN)
         .build();
      keyBindDeleteAll = Kuma.createKeyMapping(ResourceLocation.fromNamespaceAndPath("trashslot", "delete_all"))
         .withDefault(InputBinding.key(261, KeyModifiers.of(new KeyModifier[]{KeyModifier.SHIFT})))
         .withContext(KeyConflictContext.SCREEN)
         .build();
   }
}
