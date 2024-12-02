package com.example.pcbdispatcher.repository;

import com.example.pcbdispatcher.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
