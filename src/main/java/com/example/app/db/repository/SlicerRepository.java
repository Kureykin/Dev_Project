package com.example.app.db.repository;

import com.example.app.db.entity.UrlData;
import com.example.app.db.entity.UserData;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SlicerRepository extends JpaRepository<UrlData, String> {
    @Transactional
    @Modifying
    @Query("update UrlData u set u.references = u.references + 1 where u.slicedUrl = :id")
    void incrementRefCount(String id);

    @Query("select u from UrlData u where u.username = :username")
    List<UrlData> showAllUsersUrl(UserData username);

    @Query("select u from UrlData u where u.username = :username and u.isActive = true")
    List<UrlData> showActiveData(UserData username);
}
