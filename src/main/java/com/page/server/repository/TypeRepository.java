package com.page.server.repository;

import com.page.server.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findTypeByTypeNameAndDataTypeAndRequiredAndDefaultValue(String type, Type.DataType dataType, Boolean required, String defaultValue);
}
