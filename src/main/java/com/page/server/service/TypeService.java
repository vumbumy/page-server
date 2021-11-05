package com.page.server.service;

import com.page.server.entity.data.DataColumn;
import com.page.server.repository.TypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    public DataColumn createIfNotExist(DataColumn type) {
        return typeRepository.findTypeByColumnNameAndColumnTypeAndRequiredAndDefaultValueAndDeleted(
                type.columnName.toUpperCase(),
                type.columnType,
                type.required,
                type.defaultValue,
                type.deleted
        ).orElseGet(() -> typeRepository.save(type));
    }

    public List<DataColumn> addListIfNotExist(List<DataColumn> types) {
        if (CollectionUtils.isEmpty(types)) return new ArrayList<>();

        List<DataColumn> typeList = new ArrayList<>();
        types.forEach(
                type -> typeList.add(
                        this.createIfNotExist(type)
                )
        );

        return typeList;
    }
}
