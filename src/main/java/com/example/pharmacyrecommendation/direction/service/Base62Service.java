package com.example.pharmacyrecommendation.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class Base62Service {
    private final Base62 base62Instance = Base62.createInstance();

    public String encodeDirectionId(Long directionId){
        return new String(base62Instance.encode(String.valueOf(directionId).getBytes()));
    }

    public Long decodeDirectionId(String encodedDirectionId){
        String resultDirectionId = new String(base62Instance.decode(encodedDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }
}
