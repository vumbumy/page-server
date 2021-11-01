package com.page.server.service;

import com.page.server.entity.data.Type;
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

    public Type createIfNotExist(Type type) {
        return typeRepository.findTypeByTypeNameAndDataTypeAndRequiredAndDefaultValueAndDeleted(
                type.typeName.toUpperCase(),
                type.dataType,
                type.required,
                type.defaultValue,
                type.deleted
        ).orElseGet(() -> typeRepository.save(type));
    }

    public List<Type> addListIfNotExist(List<Type> types) {
        if (CollectionUtils.isEmpty(types)) return new ArrayList<>();

        List<Type> typeList = new ArrayList<>();
        types.forEach(
                type -> typeList.add(
                        this.createIfNotExist(type)
                )
        );

        return typeList;
    }
}
