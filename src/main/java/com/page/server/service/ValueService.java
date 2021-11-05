package com.page.server.service;

import com.page.server.dao.ValueDao;
import com.page.server.entity.Ticket;
import com.page.server.entity.data.DataValue;
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
        List<DataValue> valueList = new ArrayList<>();

        values.forEach((columnNo, value) -> {
            valueList.add(DataValue.builder()
                    .contentNo(contentNo)
                    .columnNo(columnNo)
                    .value(value)
                    .build()
            );
        });

        valueRepository.saveAll(valueList);
    }

    public void put(Long contentNo, Map<Long, String> values){
        Map<Long, DataValue> valueMap = valueRepository.findAllByContentNo(contentNo).stream()
                .collect(Collectors.toMap(value -> value.columnNo, value -> value));

        values.forEach(
                (columnNo, value) -> {
                    DataValue dataValue;
                    if (valueMap.containsKey(columnNo)) {
                        dataValue = valueMap.get(columnNo);

                        dataValue.columnNo = columnNo;
                        dataValue.value = value;
                    } else {
                        dataValue = DataValue.builder()
                                .contentNo(contentNo)
                                .columnNo(columnNo)
                                .value(value)
                                .build();
                    }
                    valueMap.put(columnNo, dataValue);
                }
        );

        valueRepository.saveAll(valueMap.values());
    }
}
