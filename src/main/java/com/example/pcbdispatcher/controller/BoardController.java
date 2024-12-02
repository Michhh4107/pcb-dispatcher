package com.example.pcbdispatcher.controller;

import com.example.pcbdispatcher.dto.BoardDto;
import com.example.pcbdispatcher.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public ResponseEntity<List<BoardDto>> getBoards() {
        List<BoardDto> boardDtoList = boardService.getAll();
        return ResponseEntity.ok(boardDtoList);
    }

    @PostMapping
    public ResponseEntity<BoardDto> registerBoard(@RequestBody @Validated BoardDto boardDto) {
        BoardDto board = boardService.register(boardDto);
        return new ResponseEntity<>(board, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/install")
    public ResponseEntity<BoardDto> installComponents(@PathVariable Long id) {
        BoardDto updatedBoard = boardService.install(id);
        return ResponseEntity.ok(updatedBoard);
    }

    @PostMapping("/{id}/quality")
    public ResponseEntity<BoardDto> qualityCheck(@PathVariable Long id) {
        BoardDto updatedBoard = boardService.qualityControl(id);
        return ResponseEntity.ok(updatedBoard);
    }

    @PostMapping("/{id}/repair")
    public ResponseEntity<BoardDto> repairBoard(@PathVariable Long id) {
        BoardDto updatedBoard = boardService.repair(id);
        return ResponseEntity.ok(updatedBoard);
    }

    @PostMapping("/{id}/pack")
    public ResponseEntity<BoardDto> packBoard(@PathVariable Long id) {
        BoardDto updatedBoard = boardService.pack(id);
        return ResponseEntity.ok(updatedBoard);
    }



}
