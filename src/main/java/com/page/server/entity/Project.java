package com.page.server.entity;

import com.page.server.entity.base.BaseContent;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Table;

@NoArgsConstructor
@Entity
@Table(name = "_PROJECT")
public class Project extends BaseContent {

}
