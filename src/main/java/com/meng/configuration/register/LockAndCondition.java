package com.meng.configuration.register;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LockAndCondition {
    private Lock lock;
    private Condition condition;
}
