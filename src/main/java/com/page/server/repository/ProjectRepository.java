package com.page.server.repository;

import com.page.server.dao.ProjectDao;
import com.page.server.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query(
            nativeQuery = true,
            value = "SELECT DISTINCT\n" +
                    "    prj.content_no AS projectNo,\n" +
                    "    prj.content_name AS projectName,\n" +
                    "    usr.email AS managerName,\n" +
//                    "    COUNT(tk.content_no) AS ticketCount,\n" +
                    "    prj.created_at AS createdAt\n" +
                    "FROM\n" +
                    "    _project AS prj\n" +
                    "    LEFT JOIN _user AS usr ON prj.manager_no = usr.user_no\n" +
                    "    LEFT JOIN _ticket AS tk ON tk.project_no = prj.content_no"
    )
    List<ProjectDao> findProjectDaoAll();

    @Query(
            nativeQuery = true,
            value = "SELECT \n" +
                    "    prj.content_no AS projectNo,\n" +
                    "    prj.content_name AS projectName,\n" +
                    "    usr.email AS managerName,\n" +
                    "    COUNT(tk.content_no) AS ticketCount,\n" +
                    "    prj.created_at AS createdAt\n" +
                    "FROM\n" +
                    "    _project AS prj\n" +
                    "    LEFT JOIN _user AS usr ON prj.manager_no = usr.user_no\n" +
                    "    LEFT JOIN _ticket AS tk ON tk.project_no = prj.content_no" +
                    "WHERE\n" +
                    "    (prj.readable OR prj.content_no IN (?1))"

    )
    List<ProjectDao> findAllByProjectNoContains(Set<Long> projectNoList);

    @Query(value =
            "SELECT \n" +
            "    prj.content_no as projectNo,\n" +
            "    prj.content_name as projectName\n" +
            "FROM\n" +
            "    _project AS prj\n" +
            "WHERE\n" +
            "    prj.readable",
            nativeQuery = true
    )
    List<ProjectDao> findAllReadable();
}
