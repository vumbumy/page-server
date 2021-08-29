package com.page.server.repository;

import com.page.server.dao.ProjectDao;
import com.page.server.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(value =
                    "SELECT \n" +
                    "    prj.content_no AS projectNo,\n" +
                    "    prj.content_name AS projectName,\n" +
                    "    usr.user_name AS managerName,\n" +
                    "    COUNT(tk.content_no) AS ticketCount,\n" +
                    "    prj.created_at AS createdAt\n" +
                    "FROM\n" +
                    "    _project AS prj\n" +
                    "    LEFT JOIN _user AS usr ON prj.manager_no = usr.user_no\n" +
                    "    LEFT JOIN _ticket AS tk ON tk.project_no = prj.content_no",
            nativeQuery = true
    )
    List<ProjectDao> findProjectDaoAll();

    @Query(value =
                    "SELECT \n" +
                    "    prj.content_no AS projectNo,\n" +
                    "    prj.content_name AS projectName,\n" +
                    "    usr.user_name AS managerName,\n" +
                    "    COUNT(tk.content_no) AS ticketCount,\n" +
                    "    prj.created_at AS createdAt\n" +
                    "FROM\n" +
                    "    _project AS prj\n" +
                    "    LEFT JOIN _user AS usr ON prj.manager_no = usr.user_no\n" +
                    "    LEFT JOIN _ticket AS tk ON tk.project_no = prj.content_no" +
                    "WHERE\n" +
                    "    (prj.shared OR prj.content_no IN (?1))",
            nativeQuery = true
    )
    List<ProjectDao> findAllByProjectNoContains(Set<Long> projectNoList);

    @Query(value =
            "SELECT \n" +
            "    prj.content_no as projectNo,\n" +
            "    prj.content_name as projectName\n" +
            "FROM\n" +
            "    _project AS prj\n" +
            "WHERE\n" +
            "    prj.shared",
            nativeQuery = true
    )
    List<ProjectDao> findAllShared();
}
