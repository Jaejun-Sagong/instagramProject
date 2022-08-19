package com.sparta.instagramProject.repository;

import com.sparta.instagramProject.model.DeletedUrlPath;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeletedUrlPathRepository extends JpaRepository<DeletedUrlPath,Long> {
}
