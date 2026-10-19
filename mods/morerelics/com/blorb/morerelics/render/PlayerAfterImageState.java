package com.blorb.morerelics.render;

import net.minecraft.world.phys.Vec3;

public record PlayerAfterImageState(Vec3 position, float yRot, float yBodyRot, float yHeadRot, float xRot, float limbSwing, float limbSwingAmount) {
}
