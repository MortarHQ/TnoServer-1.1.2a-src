package cc.sighs.oelib.config.api;

import cc.sighs.oelib.config.ConfigUnit;

public record ConfigLoadEvent<T>(ConfigUnit<T> unit, T value) {
}
