package com.page.server.service;

import com.page.server.dao.ValueDao;
import com.page.server.entity.Type;
import com.page.server.entity.Value;
import com.page.server.repository.TypeRepository;
import com.page.server.repository.ValueRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ValueService {
    private final TypeRepository typeRepository;
    private final ValueRepository valueRepository;

    public List<ValueDao> get(Long contentNo){
        return valueRepository.findAllDaoByContentNo(contentNo);
    }

    public void add(Long contentNo, Map<Long, String> values){
        List<Value> valueList = new ArrayList<>();

        values.forEach((typeNo, value) -> {
//            Type type = typeRepository.findById(typeNo).orElseThrow(
//                    () -> new RuntimeException("Not Fount Type: " + typeNo)
//            );

            valueList.add(Value.builder()
                    .contentNo(contentNo)
                    .typeNo(typeNo)
//                    .type(type)
                    .dataValue(value)
                    .build()
            );
        });

        valueRepository.saveAll(valueList);
    }

    public void put(Long contentNo, Map<Long, String> values){
        Map<Long, Value> valueMap = valueRepository.findAllByContentNo(contentNo).stream()
                .collect(Collectors.toMap(value -> value.valueNo, value -> value));

        values.forEach(
                (valueNo, dataValue) -> valueMap.get(valueNo).dataValue = dataValue
        );

        valueRepository.saveAll(valueMap.values());
    }
}
