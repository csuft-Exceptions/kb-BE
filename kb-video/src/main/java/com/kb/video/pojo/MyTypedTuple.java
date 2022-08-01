package com.kb.video.pojo;

import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;

@NoArgsConstructor
public class MyTypedTuple<V> implements ZSetOperations.TypedTuple<V> {
    @Nullable
    private Double score;
    @Nullable
    private  V value;

    public MyTypedTuple(@Nullable V value, @Nullable Double score) {
        this.score = score;
        this.value = value;
    }
    @Nullable
    public Double getScore() {
        return this.score;
    }

    @Nullable
    public V getValue() {
        return this.value;
    }

    @Override
    public int compareTo(@NotNull ZSetOperations.TypedTuple<V> o) {
        return 0;
    }
}
