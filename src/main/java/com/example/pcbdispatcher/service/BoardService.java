package com.example.pcbdispatcher.service;

import com.example.pcbdispatcher.dto.BoardDto;
import com.example.pcbdispatcher.entity.Board;
import com.example.pcbdispatcher.enums.BoardStatus;
import com.example.pcbdispatcher.exception.ActionNotAllowedException;
import com.example.pcbdispatcher.exception.BoardNotFoundException;
import com.example.pcbdispatcher.mapper.BoardMapper;
import com.example.pcbdispatcher.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardMapper boardMapper;

    public List<BoardDto> getAll() {
        List<Board> boards =  boardRepository.findAll();
        return boardMapper.toBoardDtoList(boards);
    }

    public BoardDto register(BoardDto boardDto) {
        Board board = Board.builder()
                .name(boardDto.getName())
                .status(BoardStatus.REGISTRATION)
                .createdAt(LocalDateTime.now())
                .build();

        boardRepository.save(board);
        return boardMapper.toDto(board);
    }

    @Transactional
    public BoardDto install(Long id) {
        Board board = getBoardById(id);

        BoardStatus action = BoardStatus.COMPONENT_INSTALLATION;
        BoardStatus currentStatus = board.getStatus();
        BoardStatus requiredStatus = BoardStatus.REGISTRATION;

        validateAllowedAction(action, currentStatus, requiredStatus);

        board.setStatus(BoardStatus.COMPONENT_INSTALLATION);
        return boardMapper.toDto(board);
    }

    @Transactional
    public BoardDto qualityControl(Long id) {
        Board board = getBoardById(id);

        BoardStatus action = BoardStatus.QUALITY_CONTROL;
        BoardStatus currentStatus = board.getStatus();
        BoardStatus requiredStatus = BoardStatus.COMPONENT_INSTALLATION;

        validateAllowedAction(action, currentStatus, requiredStatus);

        board.setStatus(BoardStatus.QUALITY_CONTROL);
        return boardMapper.toDto(board);
    }

    @Transactional
    public BoardDto repair(Long id) {
        Board board = getBoardById(id);

        BoardStatus action = BoardStatus.REPAIR;
        BoardStatus currentStatus = board.getStatus();
        BoardStatus requiredStatus = BoardStatus.QUALITY_CONTROL;

        validateAllowedAction(action, currentStatus, requiredStatus);

        board.setStatus(BoardStatus.REPAIR);
        return boardMapper.toDto(board);
    }

    @Transactional
    public BoardDto pack(Long id) {
        Board board = getBoardById(id);

        board.setStatus(BoardStatus.PACKAGING);
        return boardMapper.toDto(board);
    }

    private void validateAllowedAction(BoardStatus action, BoardStatus currentStatus, BoardStatus requiredStatus) {
        if (!currentStatus.equals(requiredStatus)) {
            throw new ActionNotAllowedException(
                    action.getDescription() + " is allowed only in " + requiredStatus + " status. Current status: " + currentStatus
            );
        }
    }

    private Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board " + id + " not found"));
    }
}
