package com.sjyueluo.tensura_skill_books.catalog;

import net.minecraft.resources.ResourceLocation;

public record SkillEntry(ResourceLocation id, Object skill, SkillCategory category) {
}
