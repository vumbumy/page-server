package com.page.server.service;

import com.page.server.dao.CellDao;
import com.page.server.entity.Ticket;
import com.page.server.entity.data.DataCell;
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

    public List<CellDao> getTicketValues(Ticket ticket){
        return valueRepository.findAllDaoByContentNo(
                ticket.contentNo,
                typeRepository.findAllByProjectNoAndDeletedFalse(ticket.projectNo)
        );
    }

    public void add(Long contentNo, Map<Long, String> values){
        List<DataCell> valueList = new ArrayList<>();

        values.forEach((columnNo, cellValue) -> {
            valueList.add(DataCell.builder()
                    .contentNo(contentNo)
                    .columnNo(columnNo)
                    .cellValue(cellValue)
                    .build()
            );
        });

        valueRepository.saveAll(valueList);
    }

    public void put(Long contentNo, Map<Long, String> values){
        Map<Long, DataCell> valueMap = valueRepository.findAllByContentNo(contentNo).stream()
                .collect(Collectors.toMap(value -> value.columnNo, value -> value));

        values.forEach(
                (columnNo, cellValue) -> {
                    DataCell dataCell;
                    if (valueMap.containsKey(columnNo)) {
                        dataCell = valueMap.get(columnNo);

                        dataCell.columnNo = columnNo;
                        dataCell.cellValue = cellValue;
                    } else {
                        dataCell = DataCell.builder()
                                .contentNo(contentNo)
                                .columnNo(columnNo)
                                .cellValue(cellValue)
                                .build();
                    }
                    valueMap.put(columnNo, dataCell);
                }
        );

        valueRepository.saveAll(valueMap.values());
    }
}
