package com.wgolden;

public record ExecutionParameter<T>(int objectType, String parameterName, T parameterValue) {
}
