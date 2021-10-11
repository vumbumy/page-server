package com.page.server.service;

import com.page.server.dao.ValueDao;
import com.page.server.entity.Ticket;
import com.page.server.entity.Value;
import com.page.server.repository.TypeRepository;
import com.page.server.repository.ValueRepository;
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

    public List<ValueDao> getTicketValues(Ticket ticket){
        return valueRepository.findAllDaoByContentNo(
                ticket.contentNo,
                typeRepository.findAllByProjectNoAndDeletedFalse(ticket.projectNo)
        );
    }

    public void add(Long contentNo, Map<Long, String> values){
        List<Value> valueList = new ArrayList<>();

        values.forEach((typeNo, dataValue) -> {
            valueList.add(Value.builder()
                    .contentNo(contentNo)
                    .typeNo(typeNo)
                    .dataValue(dataValue)
                    .build()
            );
        });

        valueRepository.saveAll(valueList);
    }

    public void put(Long contentNo, Map<Long, String> values){
        Map<Long, Value> valueMap = valueRepository.findAllByContentNo(contentNo).stream()
                .collect(Collectors.toMap(value -> value.typeNo, value -> value));

        values.forEach(
                (typeNo, dataValue) -> {
                    Value value;
                    if (valueMap.containsKey(typeNo)) {
                        value = valueMap.get(typeNo);

                        value.typeNo = typeNo;
                        value.dataValue = dataValue;
                    } else {
                        value = Value.builder()
                                .contentNo(contentNo)
                                .typeNo(typeNo)
                                .dataValue(dataValue)
                                .build();
                    }
                    valueMap.put(typeNo, value);
                }
        );

        valueRepository.saveAll(valueMap.values());
    }
}
