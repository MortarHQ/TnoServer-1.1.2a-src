package com.blamejared.crafttweaker.natives.world.clip;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.BracketEnum;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.level.ClipContext.Block;

@ZenRegister
@Document("vanilla/api/world/clip/BlockClipContext")
@NativeTypeRegistration(value = Block.class, zenCodeName = "crafttweaker.api.world.BlockClipContext")
@BracketEnum("minecraft:world/clip/blockcontext")
public class ExpandClipContextBlock {
}
