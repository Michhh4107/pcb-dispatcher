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
import java.util.Set;

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
        return changeStatus(id, Set.of(BoardStatus.REGISTRATION), BoardStatus.COMPONENT_INSTALLATION);
    }

    @Transactional
    public BoardDto qualityControl(Long id) {
        return changeStatus(id, Set.of(BoardStatus.COMPONENT_INSTALLATION, BoardStatus.REPAIR), BoardStatus.QUALITY_CONTROL);
    }

    @Transactional
    public BoardDto repair(Long id) {
        return changeStatus(id, Set.of(BoardStatus.QUALITY_CONTROL), BoardStatus.REPAIR);
    }

    @Transactional
    public BoardDto pack(Long id) {
        return changeStatus(id, Set.of(BoardStatus.QUALITY_CONTROL), BoardStatus.PACKAGING);
    }

    private BoardDto changeStatus(Long id, Set<BoardStatus> allowedStatuses, BoardStatus newStatus) {
        Board board = getBoardById(id);

        if (!allowedStatuses.contains(board.getStatus())) {
            throw new ActionNotAllowedException(
                    String.format("Transition to %s is allowed only from %s. Current status: %s",
                            newStatus, allowedStatuses, board.getStatus())
            );
        }

        board.setStatus(newStatus);
        return boardMapper.toDto(board);
    }

    private Board getBoardById(Long id) {
        return boardRepository.findById(id)
                .orElseThrow(() -> new BoardNotFoundException("Board with id " + id + " not found"));
    }
}
