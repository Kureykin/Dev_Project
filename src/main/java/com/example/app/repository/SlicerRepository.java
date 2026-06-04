package com.example.app.repository;

import com.example.app.entity.UrlData;
import lombok.Getter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public interface SlicerRepository extends JpaRepository<UrlData, String> {

}
