package com.page.server.service;

import com.page.server.dto.TypeDto;
import com.page.server.entity.ProjectTypeRef;
import com.page.server.entity.Type;
import com.page.server.repository.ProjectTypeRefRepository;
import com.page.server.repository.TypeRepository;
import com.page.server.support.TypeConvert;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TypeService {
    private final TypeRepository typeRepository;

    private final ProjectTypeRefRepository projectTypeRefRepository;

    private final TypeConvert typeConvert;

    public Type saveIfNotExist(TypeDto typeDto) {
        return saveIfNotExist(
                typeConvert.from(typeDto)
        );
    }

    public Type saveIfNotExist(Type type) {
        return typeRepository.findTypeByTypeNameAndDataTypeAndRequiredAndDefaultValue(type.typeName, type.dataType, type.required, type.defaultValue)
                .orElseGet(() -> typeRepository.save(type));
    }

    public List<Type> addListIfNotExist(List<TypeDto> dtoList) {
        if (CollectionUtils.isEmpty(dtoList)) return new ArrayList<>();

        List<Type> typeList = new ArrayList<>();
        dtoList.forEach(
                typeDto -> typeList.add(
                        this.saveIfNotExist(typeDto)
                )
        );

        return typeList;
    }

    @Transactional
    public List<Type> saveTypeListIfNotExist(Long projectNo, List<TypeDto> dtoList) {
        List<Type> typeList = new ArrayList<>();

        dtoList.forEach(typeDto -> {
            Type type = saveIfNotExist(typeDto);

            Optional<ProjectTypeRef> typeRefOptional = projectTypeRefRepository.findByProjectNoAndTypeNo(projectNo, type.typeNo);

            if (typeRefOptional.isPresent()) {
                ProjectTypeRef projectTypeRef = typeRefOptional.get();

                projectTypeRef.deleted = typeDto.deleted;

                projectTypeRefRepository.save(projectTypeRef);
            } else {
                projectTypeRefRepository.save(
                        ProjectTypeRef.builder()
                                .projectNo(projectNo)
                                .type(type)
                                .build()
                );
            }

            if (!typeDto.deleted) {
                typeList.add(type);
            }
        });

        return typeList;
    }

    public List<Type> getTypeList(Long projectNo) {
        return projectTypeRefRepository.findAllByProjectNoAndDeletedIsNullOrDeletedFalse(projectNo).stream()
                .map(projectTypeRef -> projectTypeRef.type)
                .collect(Collectors.toList());
    }
}
