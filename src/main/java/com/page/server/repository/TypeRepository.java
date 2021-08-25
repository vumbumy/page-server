package com.page.server.repository;

import com.page.server.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findTypeByTypeNameAndAndDataType(String type, Type.DataType dataType);
}
